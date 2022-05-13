package com.tracing.tutorial.second.service.firstmethod;

import com.tracing.tutorial.second.service.firstmethod.dto.FirstMethodRequestModel;
import com.tracing.tutorial.second.service.firstmethod.dto.FirstMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FirstMethodService {

    private final Logger logger = LoggerFactory.getLogger(FirstMethodService.class);

    public FirstMethodService() {}

    public ResponseEntity<FirstMethodResponseModel> firstMethod(
        FirstMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());

        FirstMethodResponseModel model = new FirstMethodResponseModel();
        model.setMessage("Succeeded.");
        model.setValue(requestDto.getValue());

        ResponseEntity<FirstMethodResponseModel> responseDto =
            new ResponseEntity(model, HttpStatus.OK);

        return responseDto;
    }
}
