package com.pnk.sms_service.service;

import com.pnk.sms_service.dto.request.SmsRequest;


public interface SmsSender {

    void sendSms(SmsRequest smsRequest);
    // or maybe void sendSms(String phoneNumber, String message);

}
