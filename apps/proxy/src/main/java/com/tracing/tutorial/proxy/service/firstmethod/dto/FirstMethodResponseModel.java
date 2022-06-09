package com.tracing.tutorial.proxy.service.firstmethod.dto;

import com.tracing.tutorial.proxy.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FirstMethodResponseModel extends BaseResponseDto {

    private String value;
}
