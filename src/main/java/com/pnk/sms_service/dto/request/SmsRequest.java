package com.pnk.sms_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsRequest {

    @NotBlank
    @JsonProperty("phoneNumber")
    String phoneNumber; // destination

    @NotBlank
    @JsonProperty("message")
    String message;
}
