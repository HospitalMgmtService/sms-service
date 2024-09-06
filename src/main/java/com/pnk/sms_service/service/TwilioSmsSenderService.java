package com.pnk.sms_service.service;

import com.pnk.sms_service.dto.request.SmsRequest;
import com.pnk.sms_service.dto.response.SmsResponse;


public interface TwilioSmsSenderService {

    SmsResponse sendSms(SmsRequest smsRequest);

}
