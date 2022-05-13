package com.tracing.tutorial.fifth.service.thirdmethod.dto;

import com.tracing.tutorial.fifth.dto.BaseResponseDto;

public class ThirdMethodResponseModel extends BaseResponseDto {

    private String value;

    public void setValue(String value) { this.value = value; }

    public String getValue() { return value; };
}
