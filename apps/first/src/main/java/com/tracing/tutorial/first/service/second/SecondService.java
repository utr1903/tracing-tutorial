package com.tracing.tutorial.first.service.second;

import com.tracing.tutorial.first.dto.BaseResponseDto;
import com.tracing.tutorial.first.service.second.dto.SecondRequestModel;
import com.tracing.tutorial.first.service.second.dto.SecondResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SecondService {

    public BaseResponseDto<SecondResponseModel> method1(SecondRequestModel requestDto) {

        SecondResponseModel model = new SecondResponseModel();

        BaseResponseDto<SecondResponseModel> responseDto = new BaseResponseDto();
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setMessage("Succeeded");
        responseDto.setData(model);

        return responseDto;
    }
}
