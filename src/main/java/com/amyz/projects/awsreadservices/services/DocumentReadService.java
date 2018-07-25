package com.amyz.projects.awsreadservices.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amyz.projects.awsreadservices.clients.DocumentS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;


@Service
public class DocumentReadService {

    @Autowired
    private DocumentS3Client documentS3Client;
    @Autowired
    private AmazonSQS amazonSQS;

    @Value("${sqs.queueName}")
    private String queueName;

    private ReceiveMessageRequest receiveMessageRequest;

    @PostConstruct
    public void init() {
        receiveMessageRequest = new ReceiveMessageRequest();
        receiveMessageRequest.setQueueUrl(amazonSQS.getQueueUrl(queueName).getQueueUrl());
        receiveMessageRequest.setMaxNumberOfMessages(1);
    }
    public String readFromQueue() throws IOException{
        List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
        if (messages.size() > 0) {
            return documentS3Client.getDocument(extractKey(messages.get(0)));
        } else {
            return null;
        }
    }

    private String extractKey(Message message) {
        String retValue = null;
        String body = message.getBody();
        if (!body.isEmpty()) {
            JSONObject jsonObject = new JSONObject(body);
            JSONArray recordJsonArray = jsonObject.getJSONArray("Records");

            if (null != recordJsonArray) {
                  retValue =  recordJsonArray.getJSONObject(0)
                    .getJSONObject("s3").getString("key");
            }
        }
        System.out.println(retValue);
        return retValue;
    }
}
