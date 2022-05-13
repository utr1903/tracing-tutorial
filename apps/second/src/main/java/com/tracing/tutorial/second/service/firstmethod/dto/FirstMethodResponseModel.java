package com.tracing.tutorial.second.service.firstmethod.dto;

import com.tracing.tutorial.second.dto.BaseResponseDto;

public class FirstMethodResponseModel extends BaseResponseDto {

    private String value;

    public void setValue(String value) { this.value = value; }

    public String getValue() { return value; };
}
