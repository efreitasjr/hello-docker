package br.com.byteup.hellodocker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class SqsEventProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqsEventProducerService.class);

    @Autowired
    private AmazonSQS amazonSQS;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishEvent(JsonNode message) {
        LOGGER.info("Generating event : {}", message);
        SendMessageRequest sendMessageRequest = null;
        try {
            sendMessageRequest = new SendMessageRequest().withQueueUrl("http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/teste")
                    .withMessageBody(objectMapper.writeValueAsString(message))
                 ;
            amazonSQS.sendMessage(sendMessageRequest);
            LOGGER.info("Evento publicado");
        } catch (JsonProcessingException e) {
            LOGGER.error("Erro ao converter o json : {} a stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }

    }
}
