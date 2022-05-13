package com.tracing.tutorial.second.controller;

import com.tracing.tutorial.second.service.firstmethod.FirstMethodService;
import com.tracing.tutorial.second.service.firstmethod.dto.FirstMethodRequestModel;
import com.tracing.tutorial.second.service.firstmethod.dto.FirstMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondAppController {

    private final Logger logger = LoggerFactory.getLogger(SecondAppController.class);

    @Autowired
    private FirstMethodService firstMethodService;

    @PostMapping
    public ResponseEntity<FirstMethodResponseModel> firstMethod(
        @RequestBody FirstMethodRequestModel requestDto
    ) {
        logger.info("First Method is triggered...");

        ResponseEntity<FirstMethodResponseModel> responseDto =
                firstMethodService.firstMethod(requestDto);

        logger.info("First Method is executed successfully...");

        return responseDto;
    }
}
