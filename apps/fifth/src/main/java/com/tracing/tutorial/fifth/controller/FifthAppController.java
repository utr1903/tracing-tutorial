package com.tracing.tutorial.fifth.controller;

import com.tracing.tutorial.fifth.service.thirdmethod.ThirdMethodService;
import com.tracing.tutorial.fifth.service.thirdmethod.dto.ThirdMethodRequestModel;
import com.tracing.tutorial.fifth.service.thirdmethod.dto.ThirdMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FifthAppController {

    private final Logger logger = LoggerFactory.getLogger(FifthAppController.class);

    @Autowired
    private ThirdMethodService firstMethodService;

    @PostMapping
    public ResponseEntity<ThirdMethodResponseModel> firstMethod(
            @RequestBody ThirdMethodRequestModel requestDto
    ) {
        logger.info("First Method is triggered...");

        ResponseEntity<ThirdMethodResponseModel> responseDto =
                firstMethodService.firstMethod(requestDto);

        logger.info("First Method is executed successfully...");

        return responseDto;
    }
}