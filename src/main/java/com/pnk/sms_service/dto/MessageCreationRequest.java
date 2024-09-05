package com.pnk.sms_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageCreationRequest {

    String id;

    String sender;

    String recipient;

    String messageBody;
}
