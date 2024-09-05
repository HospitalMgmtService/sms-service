package com.pnk.sms_service.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;


@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {

    String id;

    String sender;

    String recipient;

    String messageBody;
}
