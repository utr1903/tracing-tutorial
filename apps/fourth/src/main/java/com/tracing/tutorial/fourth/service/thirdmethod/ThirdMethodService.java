package com.tracing.tutorial.fourth.service.thirdmethod;

import com.tracing.tutorial.fourth.service.thirdmethod.dto.ThirdMethodRequestModel;
import com.tracing.tutorial.fourth.service.thirdmethod.dto.ThirdMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ThirdMethodService {

    private final Logger logger = LoggerFactory.getLogger(ThirdMethodService.class);

    public ThirdMethodService() {}

    public ResponseEntity<ThirdMethodResponseModel> thirdMethod(
        ThirdMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());

        ThirdMethodResponseModel model = new ThirdMethodResponseModel();
        model.setMessage("Succeeded.");
        model.setValue(requestDto.getValue());

        ResponseEntity<ThirdMethodResponseModel> responseDto =
            new ResponseEntity(model, HttpStatus.OK);

        return responseDto;
    }
}
