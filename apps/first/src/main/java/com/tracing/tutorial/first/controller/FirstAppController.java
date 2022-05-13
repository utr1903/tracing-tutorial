package com.tracing.tutorial.first.controller;

import com.tracing.tutorial.first.service.firstmethod.dto.FirstMethodRequestModel;
import com.tracing.tutorial.first.service.firstmethod.dto.FirstMethodResponseModel;
import com.tracing.tutorial.first.service.firstmethod.FirstMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("first")
public class FirstAppController {

    private final Logger logger = LoggerFactory.getLogger(FirstAppController.class);

    @Autowired
    private FirstMethodService secondService;

    @PostMapping
    public ResponseEntity<FirstMethodResponseModel> method1(
        @RequestBody FirstMethodRequestModel requestDto
    ) {
        logger.info("Method 1 is triggered...");

        ResponseEntity<FirstMethodResponseModel> responseDto =
                secondService.method1(requestDto);

        logger.info("Method 1 is executed successfully...");

        return responseDto;
    }
}