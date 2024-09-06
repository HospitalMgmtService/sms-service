package com.pnk.sms_service.configuration.twilio;

import com.twilio.Twilio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class TwilioInitializer {
    private final TwilioConfiguration twilioConfiguration;


    @Autowired
    public TwilioInitializer(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(
                twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthToken()
        );
        log.info(">> TwilioInitializer >> Twilio initialized ... with account sid {} ", twilioConfiguration.getAccountSid());
    }
}
