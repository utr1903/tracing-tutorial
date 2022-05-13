package com.tracing.tutorial.first.controller;

import com.tracing.tutorial.first.dto.BaseResponseDto;
import com.tracing.tutorial.first.service.second.dto.FirstMethodRequestModel;
import com.tracing.tutorial.first.service.second.dto.FirstMethodResponseModel;
import com.tracing.tutorial.first.service.second.SecondService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("first")
public class FirstController {

    private final Logger logger = LoggerFactory.getLogger(FirstController.class);

    @Autowired
    private SecondService secondService;

    @PostMapping
    public BaseResponseDto<FirstMethodResponseModel> method1(
        @RequestBody FirstMethodRequestModel requestDto
    ) {
        logger.info("Method 1 is triggered...");

        BaseResponseDto<FirstMethodResponseModel> responseDto =
                secondService.method1(requestDto);

        logger.info("Method 1 is executed successfully...");

        return responseDto;
    }
}
