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

                calculateMinPerProducer(entry.getKey(), sortedList, producerMinInterval);
                calculateMaxPerProducer(entry.getKey(), sortedList, producerMaxInterval);
            }
        }

        int maxInterval = producerMaxInterval.stream().map(IndividualResult::interval).max(Integer::compareTo).get();
        int minInterval = producerMinInterval.stream().map(IndividualResult::interval).min(Integer::compareTo).get();

        SortedSet<IndividualResult> maxResult = new TreeSet<>(producerMaxInterval.stream().filter(pmi -> pmi.interval() == maxInterval).toList());
        SortedSet<IndividualResult> minResult = new TreeSet<>(producerMinInterval.stream().filter(pmi -> pmi.interval() == minInterval).toList());

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

    private void calculateMinPerProducer(String producer, List<Year> sortedList, SortedSet<IndividualResult> producerMinInterval) {
        int minInterval = calculateFirstInterval(sortedList);
        Set<IndividualResult> minIntervalResults = new HashSet<>();

        for(int i = 0; i < sortedList.size() - 1; i++) {
            Year previousYear = sortedList.get(i);
            Year followingYear = sortedList.get(i + 1);
            int interval = followingYear.getValue() - previousYear.getValue();

            if(!(interval > minInterval)) {
                if(interval < minInterval) {
                    minIntervalResults.clear();
                }
                minInterval = interval;
                minIntervalResults.add(individualResultFactory.create(producer,
                        interval,
                        previousYear,
                        followingYear));
            }
        }

        producerMinInterval.addAll(minIntervalResults);
    }

    private void calculateMaxPerProducer(String producer, List<Year> sortedList, SortedSet<IndividualResult> producerMaxInterval) {
        int maxInterval = calculateFirstInterval(sortedList);
        Set<IndividualResult> maxIntervalResults = new HashSet<>();

        for(int i = 0; i < sortedList.size() - 1; i++) {
            Year previousYear = sortedList.get(i);
            Year followingYear = sortedList.get(i + 1);
            int interval = followingYear.getValue() - previousYear.getValue();

            if(!(interval < maxInterval)) {
                if(interval > maxInterval) {
                    maxIntervalResults.clear();
                }
                maxInterval = interval;
                maxIntervalResults.add(individualResultFactory.create(producer,
                        interval,
                        previousYear,
                        followingYear));
            }
        }

        producerMaxInterval.addAll(maxIntervalResults);
    }

    private static int calculateFirstInterval(List<Year> sortedList) {
        return sortedList.get(1).getValue() - sortedList.get(0).getValue();
    }
}
