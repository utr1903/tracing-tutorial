package com.tracing.tutorial.fifth.service.zipkin;

import com.tracing.tutorial.fifth.service.zipkin.model.ZipkinTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class ZipkinProcessor implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ZipkinProcessor.class);

    private static final int INTERVAL = 5000;

    @Autowired
    private RestTemplate restTemplate;

    private long lastFetchTimestamp;

    @Override
    public void run(String... args) throws InterruptedException {

        logger.info("Starting to process Zipkin traces...");

        while (true) {

            List<List<ZipkinTrace>> allTraces = fetchZipkinTraces();
            for (List<ZipkinTrace> traces : allTraces) {
                for (ZipkinTrace trace : traces) {
                    logger.info("Trace ID: " + trace.getTraceId());
                    logger.info("Span ID : " + trace.getId());
                }
            }

            Thread.sleep(INTERVAL);
        }
    }

    private List<List<ZipkinTrace>> fetchZipkinTraces() {

        logger.info("Fetching Zipkin traces...");

        String url = "http://zipkin.zipkin.svc.cluster.local:9411/api/v2/traces";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<List<List<ZipkinTrace>>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<List<ZipkinTrace>>>() {
                });

        logger.info("Zipkin traces are fetched successfully.");

        return response.getBody();
    }

    private List<List<ZipkinTrace>> sendZipkinTracesToNewrelic() {

        logger.info("Sending Zipkin traces to Newrelic...");

        String url = "https://trace-api.newrelic.com/trace/v1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // headers.set("ApiKey", env.get("NEWRELIC_LICENSE_KEY"));
        // headers.set("Data-Format", "zipkin");
        // headers.set("Data-Format-Version", "2");

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<List<List<ZipkinTrace>>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<List<ZipkinTrace>>>() {
                });

        logger.info("Zipkin traces are fetched successfully.");

        return response.getBody();
    }
}
