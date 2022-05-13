package com.tracing.tutorial.first.service.second;

import com.tracing.tutorial.first.dto.BaseResponseDto;
import com.tracing.tutorial.first.service.second.dto.SecondRequestModel;
import com.tracing.tutorial.first.service.second.dto.SecondResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SecondService {

    private Logger logger = LoggerFactory.getLogger(SecondService.class);

    private RestTemplate restTemplate;

    public SecondService() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public BaseResponseDto<SecondResponseModel> method1(
        SecondRequestModel requestDto
    ) {

        logger.info("Making a POST request to SecondService...");

        logger.info("POST request to SecondService is executed successfully.");

        SecondResponseModel model = new SecondResponseModel();

        BaseResponseDto<SecondResponseModel> responseDto = new BaseResponseDto();
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setMessage("Succeeded");
        responseDto.setData(model);

        return responseDto;
    }

//    private void makeRequestToSecondService() {
//        String url = "https://jsonplaceholder.typicode.com/posts";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//        // create a post object
//        Post post = new Post(1, "Introduction to Spring Boot",
//                "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.");
//
//        // build the request
//        HttpEntity<Post> entity = new HttpEntity<>(post, headers);
//
//        // send POST request
//        return restTemplate.postForObject(url, entity, Post.class);
//    }
}
