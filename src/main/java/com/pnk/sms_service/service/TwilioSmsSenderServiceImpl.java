package com.pnk.sms_service.service;

import com.pnk.sms_service.configuration.twilio.TwilioConfiguration;
import com.pnk.sms_service.dto.request.SmsRequest;
import com.pnk.sms_service.dto.response.SmsResponse;
import com.pnk.sms_service.exception.AppException;
import com.pnk.sms_service.exception.ErrorCode;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service("twilio")
@RequiredArgsConstructor // injected by Constructor, no longer need of @Autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TwilioSmsSenderServiceImpl implements TwilioSmsSenderService {

    private final TwilioConfiguration twilioConfiguration;


    @PreAuthorize("hasRole('ADMIN')")       // authorize before the method called
    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        if (!isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            log.info(">> dispatchSms >> Phone number {} is invalid", smsRequest.getPhoneNumber());
            throw new AppException(ErrorCode.INVALID_PHONE_NUM);
        }

        log.info(">> dispatchSms >> Dispatching smsRequest: {}", smsRequest);

        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = smsRequest.getMessage();
        MessageCreator messageCreator = Message.creator(to, from, message);
        messageCreator.create();

        return SmsResponse.builder()
                .phoneNumber(to)
                .message(message)
                .sendingStatus("SENT")
                .sendingTime(Instant.now())
                .build();
    }


    /*
     * U.S. phone numbers formats
     *   (123) 456-7890
     *   123-456-7890
     *   123.456.7890
     *   1234567890
     *   +1 123-456-7890
     * */
    private boolean isPhoneNumberValid(String phoneNumber) {
        String regex = "^(\\+1\\s?)?(\\(\\d{3}\\)|\\d{3})[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";

        return phoneNumber != null && phoneNumber.matches(regex);
    }
}
