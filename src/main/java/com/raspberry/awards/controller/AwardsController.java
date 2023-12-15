package com.raspberry.awards.controller;

import com.raspberry.awards.model.CompleteResult;
import com.raspberry.awards.service.AwardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwardsController {

    @Autowired
    private AwardsService service;

    @GetMapping("/awards")
    public CompleteResult calculateResult() {
        return service.calculateResult();
    }
}
