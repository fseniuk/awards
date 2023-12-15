package com.raspberry.awards.service;

import com.raspberry.awards.factory.CompleteResultFactory;
import com.raspberry.awards.factory.IndividualResultFactory;
import com.raspberry.awards.model.CompleteResult;
import com.raspberry.awards.model.IndividualResult;
import com.raspberry.awards.model.Movie;
import com.raspberry.awards.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;

@Service
public class AwardsService {

    private static final String WINNER_STRING = "yes";

    @Autowired
    private MoviesRepository moviesRepo;
    @Autowired
    private IndividualResultFactory individualResultFactory;
    @Autowired
    private CompleteResultFactory completeResultFactory;

    public CompleteResult calculateResult() {
        Map<String, SortedSet<Year>> producerYearSetMap = createProducerYearSetMap(moviesRepo.findAll());

        SortedSet<IndividualResult> producerMaxInterval = new TreeSet<>();
        SortedSet<IndividualResult> producerMinInterval = new TreeSet<>();

        for(Map.Entry<String, SortedSet<Year>> entry : producerYearSetMap.entrySet()) {
            if(entry.getValue().size() > 1) {
                List<Year> yearList = new ArrayList<>(entry.getValue());
                List<Year> sortedList = yearList.stream().sorted().toList();

                calculateMaxPerProducer(entry.getKey(), sortedList, producerMaxInterval);
                calculateMinPerProducer(entry.getKey(), sortedList, producerMinInterval);
            }
        }

        int maxInterval = producerMaxInterval.stream().map(IndividualResult::getInterval).max(Integer::compareTo).get();
        int minInterval = producerMinInterval.stream().map(IndividualResult::getInterval).min(Integer::compareTo).get();

        SortedSet<IndividualResult> maxResult = new TreeSet<>(producerMaxInterval.stream().filter(pmi -> pmi.getInterval() == maxInterval).toList());
        SortedSet<IndividualResult> minResult = new TreeSet<>(producerMinInterval.stream().filter(pmi -> pmi.getInterval() == minInterval).toList());

        return completeResultFactory.create(minResult, maxResult);
    }

    private static Map<String, SortedSet<Year>> createProducerYearSetMap(List<Movie> allMovies) {
        Map<String, SortedSet<Year>> producerYearSetMap = new HashMap<>();
        for(Movie movieRow : allMovies) {
            if(WINNER_STRING.equals(movieRow.getWinner())) {
                for (String producer : movieRow.getProducers()) {
                    if (!producerYearSetMap.containsKey(producer)) {
                        producerYearSetMap.put(producer, new TreeSet<>(Collections.singletonList(movieRow.getYear())));
                    }
                    producerYearSetMap.get(producer).add(movieRow.getYear());
                }
            }
        }

        return producerYearSetMap;
    }

    private void calculateMaxPerProducer(String producer, List<Year> sortedList, SortedSet<IndividualResult> producerMaxInterval) {
        Year previousYear = sortedList.get(0);
        Year followingYear = sortedList.get(sortedList.size() - 1);

        producerMaxInterval.add(individualResultFactory.create(producer,
                followingYear.getValue() - previousYear.getValue(),
                previousYear,
                followingYear));
    }

    private void calculateMinPerProducer(String producer, List<Year> sortedList, SortedSet<IndividualResult> producerMinInterval) {
        Integer minInterval = null;
        Year finalMinPreviousYear = sortedList.get(0);
        Year finalMinFollowingYear = sortedList.get(1);

        for(int i = 0; i < sortedList.size() - 1; i++) {
            Year minPreviousYear = sortedList.get(i);
            Year minFollowingYear = sortedList.get(i + 1);
            int interval = minFollowingYear.getValue() - minPreviousYear.getValue();

            if(minInterval == null || interval < minInterval) {
                minInterval = interval;
                finalMinPreviousYear = minPreviousYear;
                finalMinFollowingYear = minFollowingYear;
            }
        }

        producerMinInterval.add(individualResultFactory.create(producer,
                minInterval,
                finalMinPreviousYear,
                finalMinFollowingYear));
    }
}
