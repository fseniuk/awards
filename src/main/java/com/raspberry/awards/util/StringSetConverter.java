package com.raspberry.awards.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    private static final String COMMA_DELIMITER = ",";
    private static final String AND_DELIMITER = " and ";

    @Override
    public String convertToDatabaseColumn(Set<String> stringSet) {
        return stringSet != null ? String.join(COMMA_DELIMITER, stringSet) : "";
    }

    @Override
    public Set<String> convertToEntityAttribute(String s) {
        if(s == null) return Collections.emptySet();

        Set<String> tempSet = new HashSet<>();
        String[] commaSplit = s.split(COMMA_DELIMITER);
        for(String cs : commaSplit) {
            Collections.addAll(tempSet, cs.split(AND_DELIMITER));
        }

        Set<String> finalSet = new HashSet<>();
        for(String cs : tempSet) {
            String str = cs.trim();
            if(!str.isEmpty()) {
                finalSet.add(str);
            }
        }

        return finalSet;
    }
}
