package com.tracing.tutorial.fourth.controller;

import com.tracing.tutorial.fourth.service.thirdmethod.ThirdMethodService;
import com.tracing.tutorial.fourth.service.thirdmethod.dto.ThirdMethodRequestModel;
import com.tracing.tutorial.fourth.service.thirdmethod.dto.ThirdMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fourth")
public class FourthAppController {

    private final Logger logger = LoggerFactory.getLogger(FourthAppController.class);

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