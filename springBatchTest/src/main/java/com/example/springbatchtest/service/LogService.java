package com.example.springbatchtest.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    public void sendMessageToClient(String destination, String message) {
        log.info("message : {}" , message);
        simpMessagingTemplate.convertAndSend(destination, message);
    }
}
