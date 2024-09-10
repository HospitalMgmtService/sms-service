package com.pnk.sms_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnk.sms_service.configuration.twilio.TwilioConfiguration;
import com.pnk.sms_service.dto.request.SmsRequest;
import com.pnk.sms_service.dto.response.SmsResponse;
import com.pnk.sms_service.exception.AppException;
import com.pnk.sms_service.exception.ErrorCode;
import com.pnk.sms_service.service.TwilioSmsSenderService;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SmsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private TwilioConfiguration twilioConfiguration;

    @MockBean
    private TwilioSmsSenderService twilioSmsSenderService;

    SmsRequest smsRequest;
    SmsResponse smsResponse;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        smsRequest = SmsRequest.builder()
                .phoneNumber("0123456789")
                .message("Test with valid phone number")
                .build();

        smsResponse = SmsResponse.builder()
                .phoneNumber(new PhoneNumber(smsRequest.getPhoneNumber()))
                .message(smsRequest.getMessage())
                .sendingStatus("SENT")
                .sendingTime(Instant.now())
                .build();
    }


    @Test
    void testPostSms_validPhoneNum_success() throws Exception {
        String content = objectMapper.writeValueAsString(smsRequest);

        when(twilioSmsSenderService.sendSms(smsRequest)).thenReturn(smsResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/send")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result.phoneNumber.endpoint").value("0123456789"))
                .andExpect(jsonPath("result.message").value("Test with valid phone number"))
                .andExpect(jsonPath("result.sendingStatus").value("SENT"))
                .andDo(print());
    }


    @Test
    void testPostSms_invalidPhoneNum_AppException() throws Exception {
        // Invalid input
        SmsRequest invalidSmsRequest = SmsRequest.builder()
                .phoneNumber("invalid phone num 01234567891012")
                .message("Test with an invalid phone number")
                .build();

        when(twilioSmsSenderService.sendSms(invalidSmsRequest))
                .thenThrow(new AppException(ErrorCode.INVALID_PHONE_NUM));

        mockMvc.perform(MockMvcRequestBuilders.post("/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSmsRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(1012))
                .andExpect(jsonPath("message").value("Invalid recipient phone number"))
        ;
    }
}


/*
{
    "code": 1000,
    "result": {
        "phoneNumber": {
            "endpoint": "0123456789"
        },
        "message": "Hello! This is an SMS was sent from SMS-service in Hospital Mgmt project.",
        "sendingStatus": "SENT",
        "sendingTime": "2024-09-06T19:45:56.963732500Z"
    }
}

{
    "code": 1012,
    "message": "Invalid recipient phone number"
}
* */