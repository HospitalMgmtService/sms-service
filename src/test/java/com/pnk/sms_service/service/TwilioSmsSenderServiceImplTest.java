package com.pnk.sms_service.service;

import com.pnk.sms_service.configuration.twilio.TwilioConfiguration;
import com.pnk.sms_service.dto.request.SmsRequest;
import com.pnk.sms_service.dto.response.SmsResponse;
import com.pnk.sms_service.exception.AppException;
import com.pnk.sms_service.exception.ErrorCode;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TwilioSmsSenderServiceImplTest {

    @Mock
    private TwilioConfiguration twilioConfiguration;

    @InjectMocks
    private TwilioSmsSenderServiceImpl twilioSmsSenderServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSendSms_ValidPhoneNumber() {
        SmsRequest smsRequest = SmsRequest.builder()
                .phoneNumber("0123456789")
                .message("This is a test with valid phone number.")
                .build();

        when(twilioConfiguration.getTrialNumber())
                .thenReturn("+0987654321");

        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = smsRequest.getMessage();
        MessageCreator messageCreatorMock = mock(MessageCreator.class);
        mockStatic(Message.class);

        when(Message.creator(to, from, message))
                .thenReturn(messageCreatorMock);

        SmsResponse smsResponse = twilioSmsSenderServiceImpl.sendSms(smsRequest);
        assertNotNull(smsResponse);
        assertEquals(smsRequest.getPhoneNumber(), smsResponse.getPhoneNumber().getEndpoint());
        assertEquals(smsRequest.getMessage(), smsResponse.getMessage());
        assertEquals("SENT", smsResponse.getSendingStatus());

        // Verify message was created
        verify(messageCreatorMock).create();
    }


    @Test
    void testSendSms_InvalidPhoneNumber() {
        SmsRequest smsRequest = SmsRequest.builder()
                .phoneNumber("invalid phone num 01234567891012")
                .message("Test with an invalid phone number")
                .build();

        AppException appException = assertThrows(AppException.class,
                () -> twilioSmsSenderServiceImpl.sendSms(smsRequest));

        assertEquals(ErrorCode.INVALID_PHONE_NUM, appException.getErrorCode());
    }
}