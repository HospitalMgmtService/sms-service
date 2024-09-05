package com.pnk.sms_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SmsRequest {

    @NotBlank
    @JsonProperty("phoneNumber")
    private String phoneNumber; // destination

    @NotBlank
    @JsonProperty("message")
    private String message;
}
