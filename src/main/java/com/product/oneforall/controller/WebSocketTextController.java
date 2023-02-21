package com.product.oneforall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketTextController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseEntity<HelloMessage> greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}