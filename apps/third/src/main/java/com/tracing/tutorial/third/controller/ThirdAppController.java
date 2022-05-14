package com.tracing.tutorial.third.controller;

import com.tracing.tutorial.third.service.secondmethod.SecondMethodService;
import com.tracing.tutorial.third.service.secondmethod.dto.SecondMethodRequestModel;
import com.tracing.tutorial.third.service.secondmethod.dto.SecondMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("third")
public class ThirdAppController {

    private final Logger logger = LoggerFactory.getLogger(ThirdAppController.class);

    @Autowired
    private SecondMethodService secondMethodService;

    @PostMapping
    public ResponseEntity<SecondMethodResponseModel> secondMethod(
            @RequestBody SecondMethodRequestModel requestDto
    ) {
        logger.info("Second Method is triggered...");

        ResponseEntity<SecondMethodResponseModel> responseDto =
                secondMethodService.secondMethod(requestDto);

        logger.info("Second Method is executed successfully...");

        return responseDto;
    }
}
