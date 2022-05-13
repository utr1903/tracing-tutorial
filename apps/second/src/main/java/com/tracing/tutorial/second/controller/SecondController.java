package com.tracing.tutorial.second.controller;

import com.tracing.tutorial.second.dto.BaseResponseDto;
import com.tracing.tutorial.second.service.firstmethod.FirstMethodService;
import com.tracing.tutorial.second.service.firstmethod.dto.FirstMethodRequestModel;
import com.tracing.tutorial.second.service.firstmethod.dto.FirstMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("second")
public class SecondController {

    private final Logger logger = LoggerFactory.getLogger(SecondController.class);

    @Autowired
    private FirstMethodService firstMethodService;

    @PostMapping
    public BaseResponseDto<FirstMethodResponseModel> firstMethod(
        @RequestBody FirstMethodRequestModel requestDto
    ) {
        logger.info("First Method is triggered...");

        BaseResponseDto<FirstMethodResponseModel> responseDto =
                firstMethodService.firstMethod(requestDto);

        logger.info("First Method is executed successfully...");

        return responseDto;
    }
}
