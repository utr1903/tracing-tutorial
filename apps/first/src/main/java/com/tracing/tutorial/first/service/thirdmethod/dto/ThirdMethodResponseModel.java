package com.tracing.tutorial.first.service.thirdmethod.dto;

import com.tracing.tutorial.first.dto.BaseResponseDto;

public class ThirdMethodResponseModel extends BaseResponseDto {

    private String value;

    public void setValue(String value) { this.value = value; }

    public String getValue() { return value; };
}
