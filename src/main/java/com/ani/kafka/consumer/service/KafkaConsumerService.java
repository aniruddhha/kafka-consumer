package com.ani.kafka.consumer.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@AllArgsConstructor
@Service
public class KafkaConsumerService {

    private final TransferManager mgr;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "car-topic",groupId = "group_id")
    public void consumeMessage(String crStr) throws JsonProcessingException {

        final var arr = mapper.writeValueAsBytes(crStr);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(arr.length);

        mgr.upload(
                "", // never upload bucket names to VCS
                "car.json",
                new ByteArrayInputStream(arr),
                metadata
        );
    }
}
