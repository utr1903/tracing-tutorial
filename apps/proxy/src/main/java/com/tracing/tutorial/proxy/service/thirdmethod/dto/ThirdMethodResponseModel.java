package com.tracing.tutorial.proxy.service.thirdmethod.dto;

import com.tracing.tutorial.proxy.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThirdMethodResponseModel extends BaseResponseDto {

    private String value;
}
