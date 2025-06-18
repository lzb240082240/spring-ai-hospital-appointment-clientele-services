package com.example.springai.hospitalappointment.clienteleservices.controller;

import com.example.springai.hospitalappointment.clienteleservices.assistant.HospitalAppointmentAssistant;
import com.example.springai.hospitalappointment.clienteleservices.entity.UserChat;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Resource
    private HospitalAppointmentAssistant hospitalAppointmentAssistant;

    @PostMapping(path = "/userChat")
    public String userChat(@RequestBody UserChat userChat) {
        return hospitalAppointmentAssistant.chat(userChat.getId(), userChat.getMessage());
    }

    @PostMapping(path = "/userChat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> userChatStream(@RequestBody UserChat userChat, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return hospitalAppointmentAssistant.chatStream(userChat.getId(), userChat.getMessage());
    }
}
