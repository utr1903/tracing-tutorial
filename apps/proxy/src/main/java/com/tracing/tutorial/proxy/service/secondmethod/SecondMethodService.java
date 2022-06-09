package com.tracing.tutorial.proxy.service.secondmethod;

import com.tracing.tutorial.proxy.service.secondmethod.dto.SecondMethodRequestModel;
import com.tracing.tutorial.proxy.service.secondmethod.dto.SecondMethodResponseModel;
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

        ResponseEntity<SecondMethodResponseModel> responseDtoFromSecondService;

        try {
            SecondMethodResponseModel model = new SecondMethodResponseModel();

            logger.info("Making a POST request to SecondService...");

            responseDtoFromSecondService = makeRequestToSecondService(requestDto);

            HttpStatus statusCode = responseDtoFromSecondService.getStatusCode();
            logger.info("Status code: " + statusCode);
            logger.info("Message: " + responseDtoFromSecondService.getBody().getMessage());

            if (statusCode == HttpStatus.OK) {
                logger.info("Value retrieved: " + responseDtoFromSecondService.getBody().getValue());
                logger.info("Tag retrieved: " + responseDtoFromSecondService.getBody().getTag());

                model.setMessage("Succeeded.");
                model.setValue(responseDtoFromSecondService.getBody().getValue());
                model.setTag(responseDtoFromSecondService.getBody().getTag());
            }
            else
                model.setMessage("Call to SecondService has failed.");

            logger.info("POST request to SecondService is executed successfully.");

            return new ResponseEntity(model, statusCode);
        }
        catch (Exception e) {
            logger.error(e.getMessage());

            SecondMethodResponseModel model = new SecondMethodResponseModel();
            model.setMessage(e.getMessage());

            return new ResponseEntity(model, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<SecondMethodResponseModel> makeRequestToSecondService(
        SecondMethodRequestModel requestDto
    ) {
        String url = "http://second.second.svc.cluster.local:8080/second";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<SecondMethodRequestModel> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForEntity(url, entity, SecondMethodResponseModel.class);
    }
}
