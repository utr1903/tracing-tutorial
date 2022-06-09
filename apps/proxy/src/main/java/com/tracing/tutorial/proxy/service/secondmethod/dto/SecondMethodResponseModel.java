package com.tracing.tutorial.proxy.service.secondmethod.dto;

import com.tracing.tutorial.proxy.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecondMethodResponseModel extends BaseResponseDto {

    private String value;
    private String tag;
}
