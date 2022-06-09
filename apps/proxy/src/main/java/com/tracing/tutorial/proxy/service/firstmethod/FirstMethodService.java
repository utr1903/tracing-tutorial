package com.tracing.tutorial.proxy.service.firstmethod;

import com.tracing.tutorial.proxy.service.firstmethod.dto.FirstMethodRequestModel;
import com.tracing.tutorial.proxy.service.firstmethod.dto.FirstMethodResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FirstMethodService {

    private final Logger logger = LoggerFactory.getLogger(FirstMethodService.class);

    private final RestTemplate restTemplate;

    public FirstMethodService() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public ResponseEntity<FirstMethodResponseModel> firstMethod(
        FirstMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());

        ResponseEntity<FirstMethodResponseModel> responseDtoFromFirstService;

        try {
            var model = new FirstMethodResponseModel();

            logger.info("Making a POST request to FirstService...");

            responseDtoFromFirstService = makeRequestToFirstService(requestDto);

            var statusCode = responseDtoFromFirstService.getStatusCode();
            logger.info("Status code: " + statusCode);
            logger.info("Message: " + responseDtoFromFirstService.getBody().getMessage());

            if (statusCode == HttpStatus.OK) {
                logger.info("Value retrieved: " + responseDtoFromFirstService.getBody().getValue());

                model.setMessage("Succeeded.");
                model.setValue(responseDtoFromFirstService.getBody().getValue());
            }
            else
                model.setMessage("Call to FirstService has failed.");

            logger.info("POST request to FirstService is executed successfully.");

            return new ResponseEntity(model, statusCode);
        }
        catch (Exception e) {
            logger.error(e.getMessage());

            var model = new FirstMethodResponseModel();
            model.setMessage(e.getMessage());

            return new ResponseEntity(model, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<FirstMethodResponseModel> makeRequestToFirstService(
        FirstMethodRequestModel requestDto
    ) {
        var url = "http://first.first.svc.cluster.local:8080/first";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        var entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForEntity(url, entity, FirstMethodResponseModel.class);
    }
}
