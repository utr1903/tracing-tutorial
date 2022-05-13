package com.tracing.tutorial.third.service.secondmethod;

import com.tracing.tutorial.third.service.secondmethod.dto.SecondMethodRequestModel;
import com.tracing.tutorial.third.service.secondmethod.dto.SecondMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SecondMethodService {

    private final Logger logger = LoggerFactory.getLogger(SecondMethodService.class);

    public SecondMethodService() {}

    public ResponseEntity<SecondMethodResponseModel> firstMethod(
            SecondMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());

        SecondMethodResponseModel model = new SecondMethodResponseModel();
        model.setMessage("Succeeded.");
        model.setValue(requestDto.getValue());

        ResponseEntity<SecondMethodResponseModel> responseDto =
            new ResponseEntity(model, HttpStatus.OK);

        return responseDto;
    }
}
