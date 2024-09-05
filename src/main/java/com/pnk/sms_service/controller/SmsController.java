package com.pnk.sms_service.controller;

import com.pnk.sms_service.dto.request.SmsRequest;
import com.pnk.sms_service.service.SmsService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor // injected by Constructor, no longer need of @Autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SmsController {

    private final SmsService smsService;


    @PostMapping("/send")
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        smsService.sendSms(smsRequest);
    }
}
