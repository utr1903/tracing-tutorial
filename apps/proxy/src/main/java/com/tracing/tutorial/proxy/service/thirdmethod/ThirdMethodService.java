package com.tracing.tutorial.proxy.service.thirdmethod;

import com.tracing.tutorial.proxy.service.thirdmethod.dto.ThirdMethodRequestModel;
import com.tracing.tutorial.proxy.service.thirdmethod.dto.ThirdMethodResponseModel;
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
public class ThirdMethodService {

    private final Logger logger = LoggerFactory.getLogger(ThirdMethodService.class);

    private final RestTemplate restTemplate;

    public ThirdMethodService() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public ResponseEntity<ThirdMethodResponseModel> thirdMethod(
        ThirdMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());

        ResponseEntity<ThirdMethodResponseModel> responseDtoFromThirdService;

        try {
            ThirdMethodResponseModel model = new ThirdMethodResponseModel();

            logger.info("Making a POST request to ThirdService...");

            responseDtoFromThirdService = makeRequestToThirdService(requestDto);

            HttpStatus statusCode = responseDtoFromThirdService.getStatusCode();
            logger.info("Status code: " + statusCode);
            logger.info("Message: " + responseDtoFromThirdService.getBody().getMessage());

            if (statusCode == HttpStatus.OK) {
                logger.info("Value retrieved: " + responseDtoFromThirdService.getBody().getValue());

                model.setMessage("Succeeded.");
                model.setValue(responseDtoFromThirdService.getBody().getValue());
            }
            else
                model.setMessage("Call to ThirdService has failed.");

            logger.info("POST request to ThirdService is executed successfully.");

            return new ResponseEntity(model, statusCode);
        }
        catch (Exception e) {
            logger.error(e.getMessage());

            ThirdMethodResponseModel model = new ThirdMethodResponseModel();
            model.setMessage(e.getMessage());

            return new ResponseEntity(model, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ThirdMethodResponseModel> makeRequestToThirdService(
        ThirdMethodRequestModel requestDto
    ) {
        String url = "http://third.third.svc.cluster.local:8080/third";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<ThirdMethodRequestModel> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForEntity(url, entity, ThirdMethodResponseModel.class);
    }
}
