package com.tracing.tutorial.first.service.second;

import com.tracing.tutorial.first.service.second.dto.FirstMethodRequestModel;
import com.tracing.tutorial.first.service.second.dto.FirstMethodResponseModel;
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
public class SecondService {

    private final Logger logger = LoggerFactory.getLogger(SecondService.class);

    private RestTemplate restTemplate;

    public SecondService() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public ResponseEntity<FirstMethodResponseModel> method1(
        FirstMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());

        ResponseEntity<FirstMethodResponseModel> responseDtoFromSecondService;

        try {
            FirstMethodResponseModel model = new FirstMethodResponseModel();

            logger.info("Making a POST request to SecondService...");

            responseDtoFromSecondService = makeRequestToSecondService(requestDto);

            HttpStatus statusCode = responseDtoFromSecondService.getStatusCode();
            logger.info("Status code: " + statusCode);
            logger.info("Message: " + responseDtoFromSecondService.getBody().getMessage());

            if (statusCode == HttpStatus.OK) {
                logger.info("Value retrieved: " + responseDtoFromSecondService.getBody().getValue());

                model.setMessage("Succeeded.");
                model.setValue(responseDtoFromSecondService.getBody().getValue());
            }
            else
                model.setMessage("Failed.");

            logger.info("POST request to SecondService is executed successfully.");

            ResponseEntity<FirstMethodResponseModel> responseDto =
                new ResponseEntity(model, statusCode);

            return responseDto;
        }
        catch (Exception e) {
            logger.error(e.getMessage());

            FirstMethodResponseModel model = new FirstMethodResponseModel();

            ResponseEntity<FirstMethodResponseModel> responseDto =
                new ResponseEntity(model, HttpStatus.INTERNAL_SERVER_ERROR);

            return responseDto;
        }
    }

    private ResponseEntity<FirstMethodResponseModel> makeRequestToSecondService(
        FirstMethodRequestModel requestDto
    ) {
        // String url = "http://second.second.svc.cluster.local:8080/second";
        String url = "http://localhost:8081/second";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<FirstMethodRequestModel> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForEntity(url, entity, FirstMethodResponseModel.class);
    }
}
