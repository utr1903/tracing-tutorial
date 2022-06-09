package com.tracing.tutorial.proxy.controller;

import com.tracing.tutorial.proxy.dto.RequestDto;
import com.tracing.tutorial.proxy.dto.ResponseDto;
import com.tracing.tutorial.proxy.service.firstmethod.FirstMethodService;
import com.tracing.tutorial.proxy.service.secondmethod.SecondMethodService;
import com.tracing.tutorial.proxy.service.thirdmethod.ThirdMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("proxy")
public class ProxyAppController {

    private final Logger logger = LoggerFactory.getLogger(ProxyAppController.class);

    @Autowired
    private FirstMethodService firstMethodService;

    @Autowired
    private SecondMethodService secondMethodService;

    @Autowired
    private ThirdMethodService thirdMethodService;

    @GetMapping("default")
    public ResponseEntity<String> defaultMethod(
            @RequestParam String name
    ) {
        logger.info("Default method is triggered...");
        logger.info(" -> Name: " + name);

        ResponseEntity<String> responseDto;

        if (name.equalsIgnoreCase("bug")) {
            responseDto = new ResponseEntity<>(
                "Failed",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
            logger.error("Default method is failed.");
        }
        else {
            responseDto = new ResponseEntity<>(
                "Succeeded",
                HttpStatus.OK
            );
        }

        logger.info("Default method is executed.");

        return responseDto;
    }

    @PostMapping("method1")
    public ResponseEntity<ResponseDto> firstMethod(
        @RequestBody RequestDto requestDto
    ) {
        logger.info("First method is triggered...");

        var responseDto = firstMethodService.firstMethod(requestDto);

        logger.info("First method is executed.");

        return responseDto;
    }

    @PostMapping("method2")
    public ResponseEntity<ResponseDto> secondMethod(
            @RequestBody RequestDto requestDto
    ) {
        logger.info("Second method is triggered...");

        var responseDto = secondMethodService.secondMethod(requestDto);

        logger.info("Second method is executed.");

        return responseDto;
    }

    @PostMapping("method3")
    public ResponseEntity<ResponseDto> thirdMethod(
            @RequestBody RequestDto requestDto
    ) {
        logger.info("Third method is triggered...");

        var responseDto = thirdMethodService.thirdMethod(requestDto);

        logger.info("Third method is executed.");

        return responseDto;
    }
}
