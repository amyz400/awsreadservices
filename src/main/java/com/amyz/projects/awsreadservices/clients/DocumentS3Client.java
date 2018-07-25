package com.amyz.projects.awsreadservices.clients;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amyz.projects.awsreadservices.services.Document;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
public class DocumentS3Client {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${s3.bucketName}")
    private String s3BucketName;

    @Autowired
    private AmazonS3 awsS3Client;

    public String getDocument(String key) throws AmazonClientException, IOException {

        GetObjectRequest objectRequest = new GetObjectRequest(s3BucketName, key);
        InputStream stream =  awsS3Client.getObject(objectRequest).getObjectContent();

        String result =  IOUtils.toString(stream, "UTF-8");

        return result;
    }

}
