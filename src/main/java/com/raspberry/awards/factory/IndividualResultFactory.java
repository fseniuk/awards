package com.raspberry.awards.factory;

import com.raspberry.awards.model.IndividualResult;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class IndividualResultFactory {

    public IndividualResult create(String producer, int interval, Year previousWin, Year followingWin) {
        return new IndividualResult(producer, interval, previousWin.getValue(), followingWin.getValue());
    }
}
