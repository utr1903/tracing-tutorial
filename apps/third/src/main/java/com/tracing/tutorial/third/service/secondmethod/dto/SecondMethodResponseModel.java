package com.tracing.tutorial.third.service.secondmethod.dto;

import com.tracing.tutorial.third.dto.BaseResponseDto;

public class SecondMethodResponseModel extends BaseResponseDto {

    private String value;
    private String tag;

    public void setValue(String value) { this.value = value; }

    public String getValue() { return value; }

    public void setTag(String tag) { this.tag = tag; }

    public String getTag() { return tag; }
}
