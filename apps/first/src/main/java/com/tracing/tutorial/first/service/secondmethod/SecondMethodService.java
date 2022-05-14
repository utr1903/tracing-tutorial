package com.tracing.tutorial.first.service.secondmethod;

import com.tracing.tutorial.first.service.secondmethod.dto.SecondMethodRequestModel;
import com.tracing.tutorial.first.service.secondmethod.dto.SecondMethodResponseModel;
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
public class SecondMethodService {

    private final Logger logger = LoggerFactory.getLogger(SecondMethodService.class);

    private final RestTemplate restTemplate;

    public SecondMethodService() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public ResponseEntity<SecondMethodResponseModel> secondMethod(
        SecondMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());
        logger.info("Tag provided: " + requestDto.getTag());

        ResponseEntity<SecondMethodResponseModel> responseDtoFromThirdService;

        try {
            SecondMethodResponseModel model = new SecondMethodResponseModel();

            logger.info("Making a POST request to ThirdService...");

            responseDtoFromThirdService = makeRequestToThirdService(requestDto);

            HttpStatus statusCode = responseDtoFromThirdService.getStatusCode();
            logger.info("Status code: " + statusCode);
            logger.info("Message: " + responseDtoFromThirdService.getBody().getMessage());

            if (statusCode == HttpStatus.OK) {
                logger.info("Value retrieved: " + responseDtoFromThirdService.getBody().getValue());
                logger.info("Tag retrieved: " + responseDtoFromThirdService.getBody().getTag());

                model.setMessage("Succeeded.");
                model.setValue(responseDtoFromThirdService.getBody().getValue());
                model.setTag(responseDtoFromThirdService.getBody().getTag());
            }
            else
                model.setMessage("Call to ThirdService has failed.");

            logger.info("POST request to ThirdService is executed successfully.");

            return new ResponseEntity(model, statusCode);
        }
        catch (Exception e) {
            logger.error(e.getMessage());

            SecondMethodResponseModel model = new SecondMethodResponseModel();
            model.setMessage(e.getMessage());

            return new ResponseEntity(model, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<SecondMethodResponseModel> makeRequestToThirdService(
        SecondMethodRequestModel requestDto
    ) {
        String url = "http://third.third.svc.cluster.local:8080/third";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<SecondMethodRequestModel> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForEntity(url, entity, SecondMethodResponseModel.class);
    }
}
