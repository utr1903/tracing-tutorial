package com.tracing.tutorial.fourth.controller;

import brave.Tracer;
import brave.propagation.TraceContext;
import com.tracing.tutorial.fourth.service.thirdmethod.ThirdMethodService;
import com.tracing.tutorial.fourth.service.thirdmethod.dto.ThirdMethodRequestModel;
import com.tracing.tutorial.fourth.service.thirdmethod.dto.ThirdMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("fourth")
public class FourthAppController {

    private final Logger logger = LoggerFactory.getLogger(FourthAppController.class);

    @Autowired
    private ThirdMethodService firstMethodService;

    @Autowired
    private Tracer tracer;

    @PostMapping
    public ResponseEntity<ThirdMethodResponseModel> thirdMethod(
            @RequestHeader Map<String, String> headers,
            @RequestBody ThirdMethodRequestModel requestDto
    ) {
        logger.info("Third Method is triggered...");

        if (headers.containsKey("traceId")) {
            logger.info("Trace ID already exists. Tagging...");
            tracer.currentSpanCustomizer()
                .tag("existingTraceId", headers.get("traceId"));
        }

        ResponseEntity<ThirdMethodResponseModel> responseDto =
                firstMethodService.thirdMethod(requestDto);

        logger.info("Third Method is executed successfully.");

        return responseDto;
    }
}