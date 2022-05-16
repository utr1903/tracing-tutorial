package com.tracing.tutorial.first.controller;

import com.tracing.tutorial.first.service.firstmethod.FirstMethodService;
import com.tracing.tutorial.first.service.firstmethod.dto.FirstMethodRequestModel;
import com.tracing.tutorial.first.service.firstmethod.dto.FirstMethodResponseModel;
import com.tracing.tutorial.first.service.secondmethod.SecondMethodService;
import com.tracing.tutorial.first.service.secondmethod.dto.SecondMethodRequestModel;
import com.tracing.tutorial.first.service.secondmethod.dto.SecondMethodResponseModel;
import com.tracing.tutorial.first.service.thirdmethod.ThirdMethodService;
import com.tracing.tutorial.first.service.thirdmethod.dto.ThirdMethodRequestModel;
import com.tracing.tutorial.first.service.thirdmethod.dto.ThirdMethodResponseModel;
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
    private FirstMethodService firstMethodService;

    @Autowired
    private SecondMethodService secondMethodService;

    @Autowired
    private ThirdMethodService thirdMethodService;

    @PostMapping("method1")
    public ResponseEntity<FirstMethodResponseModel> firstMethod(
        @RequestBody FirstMethodRequestModel requestDto
    ) {
        logger.info("First method is triggered...");

        ResponseEntity<FirstMethodResponseModel> responseDto =
                firstMethodService.firstMethod(requestDto);

        logger.info("First method is executed successfully...");

        return responseDto;
    }

    @PostMapping("method2")
    public ResponseEntity<SecondMethodResponseModel> secondMethod(
            @RequestBody SecondMethodRequestModel requestDto
    ) {
        logger.info("Second method is triggered...");

        ResponseEntity<SecondMethodResponseModel> responseDto =
                secondMethodService.secondMethod(requestDto);

        logger.info("Second method is executed successfully...");

        return responseDto;
    }

    @PostMapping("method3")
    public ResponseEntity<ThirdMethodResponseModel> thirdMethod(
            @RequestBody ThirdMethodRequestModel requestDto
    ) {
        logger.info("Third method is triggered...");

        ResponseEntity<ThirdMethodResponseModel> responseDto =
                thirdMethodService.thirdMethod(requestDto);

        logger.info("Third method is executed successfully...");

        return responseDto;
    }
}
