package com.tracing.tutorial.fifth.service.zipkin;

import com.fasterxml.jackson.databind.util.JSONPObject;
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

    @Override
    public void run(String... args) throws InterruptedException {

        logger.info("Starting to process Zipkin traces...");

        while (true) {

            List<List<ZipkinTrace>> allTraces = fetchZipkinTraces();
            for (List<ZipkinTrace> traces : allTraces) {
                for (ZipkinTrace trace : traces) {
                    logger.info(" -> Trace ID: " + trace.getTraceId());
                    logger.info(" -> Span ID : " + trace.getId());
                }
            }

            if (!allTraces.isEmpty())
                sendZipkinTracesToNewrelic(allTraces);

            logger.info(" -> Waiting for " + INTERVAL + "ms till next fetch...");
            Thread.sleep(INTERVAL);
        }
    }

    private List<List<ZipkinTrace>> fetchZipkinTraces() {

        logger.info("Fetching Zipkin traces...");

        String url = "http://zipkin.zipkin.svc.cluster.local:9411/api/v2/traces?lookback="
            + INTERVAL;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ResponseEntity<List<List<ZipkinTrace>>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<List<List<ZipkinTrace>>>() {}
        );

        logger.info(" -> Zipkin traces are fetched successfully.");

        return response.getBody();
    }

    private void sendZipkinTracesToNewrelic(
        List<List<ZipkinTrace>> allTraces
    ) {

        try {
            logger.info("Sending Zipkin traces to Newrelic...");

            String url = "https://trace-api.eu.newrelic.com/trace/v1";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("Api-Key", System.getenv("NEWRELIC_API_KEY"));
            headers.set("Data-Format", "zipkin");
            headers.set("Data-Format-Version", "2");

            for (List<ZipkinTrace> traces : allTraces) {

                String traceId = traces.get(0).getTags().containsKey("existingTraceId") ?
                    traces.get(0).getTags().get("existingTraceId") :
                    traces.get(0).getTraceId();

                traces.stream().forEach(x -> x.setTraceId(traceId));

                logger.info(" -> Sending trace with trace ID: " + traceId + "...");

                HttpEntity<List<ZipkinTrace>> entity = new HttpEntity<>(traces, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                if (response.getStatusCode() == HttpStatus.OK ||
                        response.getStatusCode() == HttpStatus.CREATED) {
                    logger.info(" -> Zipkin traces are sent successfully.");
                    logger.info("Response: " + response.getBody());
                }
                else {
                    logger.error(" -> Zipkin traces could not be sent.");
                    logger.error("Error: " + response.getBody());
                }
            }
        }
        catch (Exception e) {
            logger.error(" -> Zipkin traces could not be sent.");
            logger.error("Error: " + e.getMessage());
        }
    }
}
