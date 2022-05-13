package com.tracing.tutorial.second.service.firstmethod;

import com.tracing.tutorial.second.dto.BaseResponseDto;
import com.tracing.tutorial.second.service.firstmethod.dto.FirstMethodRequestModel;
import com.tracing.tutorial.second.service.firstmethod.dto.FirstMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FirstMethodService {

    private final Logger logger = LoggerFactory.getLogger(FirstMethodService.class);

    public FirstMethodService() {}

    public BaseResponseDto<FirstMethodResponseModel> firstMethod(
            FirstMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());

        FirstMethodResponseModel model = new FirstMethodResponseModel();
        model.setValue(requestDto.getValue());

        BaseResponseDto<FirstMethodResponseModel> responseDto = new BaseResponseDto();
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setMessage("Succeeded");
        responseDto.setData(model);

        return responseDto;
    }
}
