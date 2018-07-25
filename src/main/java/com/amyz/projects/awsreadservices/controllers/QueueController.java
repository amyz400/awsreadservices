package com.amyz.projects.awsreadservices.controllers;

import com.amyz.projects.awsreadservices.services.DocumentReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class QueueController {

    @Autowired
    private DocumentReadService documentReadService;

    @RequestMapping(method = RequestMethod.GET)
    public String healthCheck() {
        return "I am healthy!";
    }

    @RequestMapping(path = "queue", method = RequestMethod.GET)
    public String readFromQueue()  {

        try {
            return documentReadService.readFromQueue();
        } catch (IOException e) {
            return "No documents available";
        }

    }
}
