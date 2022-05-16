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
import java.util.Set;

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

        for (var header : headers.entrySet()) {
            logger.info("Key  : " + header.getKey());
            logger.info("Value: " + header.getValue());
        }

        if (headers.containsKey("traceparent")) {
            logger.info("Trace ID already exists. Tagging...");

            String results[] = headers.get("traceparent").trim().split("-");
            for (var result : results)
                logger.info(result);

            tracer.currentSpanCustomizer()
                .tag("existingTraceId", results[1]);
        }

        ResponseEntity<ThirdMethodResponseModel> responseDto =
                firstMethodService.thirdMethod(requestDto);

        logger.info("Third Method is executed successfully.");

        return responseDto;
    }
}