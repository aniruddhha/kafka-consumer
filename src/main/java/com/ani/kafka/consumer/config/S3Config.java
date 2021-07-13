package com.ani.kafka.consumer.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public TransferManager transferManager() {
        return TransferManagerBuilder.standard().withS3Client(s3()).build();
    }

    @Bean
    public AmazonS3 s3() {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(
                "", ""
        ); // never never commit keys to VCS

        return AmazonS3ClientBuilder
                .standard()
                .withRegion("")  //This is the code i added to fix
                .withCredentials(
                        new AWSStaticCredentialsProvider(awsCreds)
                )
                .build();
    }
}
