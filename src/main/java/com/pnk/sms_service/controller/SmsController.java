package com.pnk.sms_service.controller;

import com.pnk.sms_service.dto.request.SmsRequest;
import com.pnk.sms_service.dto.response.ApiResponse;
import com.pnk.sms_service.dto.response.SmsResponse;
import com.pnk.sms_service.service.TwilioSmsSenderService;
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

    final TwilioSmsSenderService twilioSmsSenderService;


    @PostMapping("/send")
    public ApiResponse<SmsResponse> postSms(@Valid @RequestBody SmsRequest smsRequest) {
        log.info(">> postSms >> Sending out smsRequest: {}", smsRequest);

        return ApiResponse.<SmsResponse>builder()
                .result(twilioSmsSenderService.sendSms(smsRequest))
                .build();
    }
}
