package com.tracing.tutorial.first.service.second;

import com.tracing.tutorial.first.dto.BaseResponseDto;
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

    public BaseResponseDto<FirstMethodResponseModel> method1(
        FirstMethodRequestModel requestDto
    ) {

        logger.info("Value provided: " + requestDto.getValue());

        logger.info("Making a POST request to SecondService...");

        BaseResponseDto<FirstMethodResponseModel> responseDtoFromSecondService =
            makeRequestToSecondService(requestDto);

        logger.info("POST request to SecondService is executed successfully.");

        logger.info("Value retrieved: " + responseDtoFromSecondService.getData().getValue());

        FirstMethodResponseModel model = new FirstMethodResponseModel();

        BaseResponseDto<FirstMethodResponseModel> responseDto = new BaseResponseDto();
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setMessage("Succeeded");
        responseDto.setData(model);

        return responseDto;
    }

    private BaseResponseDto<FirstMethodResponseModel> makeRequestToSecondService(
        FirstMethodRequestModel requestDto
    ) {
        String url = "http://second.second.svc.cluster.local:8080/second";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<FirstMethodRequestModel> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForObject(url, entity, BaseResponseDto.class);
    }
}
