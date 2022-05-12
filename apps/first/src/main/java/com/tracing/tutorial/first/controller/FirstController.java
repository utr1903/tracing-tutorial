package com.tracing.tutorial.first.controller;

import com.tracing.tutorial.first.dto.BaseResponseDto;
import com.tracing.tutorial.first.service.second.dto.SecondRequestModel;
import com.tracing.tutorial.first.service.second.dto.SecondResponseModel;
import com.tracing.tutorial.first.service.second.SecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class FirstController {

    @Autowired
    private SecondService secondService;

    @PostMapping
    public BaseResponseDto<SecondResponseModel> method1(
            @RequestBody SecondRequestModel requestDto
    ) {
            return secondService.method1(requestDto);
    }
}
