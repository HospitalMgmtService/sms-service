package com.pnk.sms_service.dto.response;

import com.twilio.type.PhoneNumber;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsResponse {

    PhoneNumber phoneNumber; // destination

    String message;

    String sendingStatus;

    Instant sendingTime;
}
