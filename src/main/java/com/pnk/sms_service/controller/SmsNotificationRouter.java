package com.pnk.sms_service.controller;

import com.pnk.sms_service.dto.MessageCreationRequest;
import com.pnk.sms_service.service.MessageProcessor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor // injected by Constructor, no longer need of @Autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SmsNotificationRouter extends RouteBuilder {

    private final MessageProcessor messageProcessor; // Constructor-injected field

    @Override
    public void configure() throws Exception {
        // Configure REST component to use servlet and set JSON binding mode
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        // Define REST endpoint to receive message creation requests
        rest("/message")
                .post("/add")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(MessageCreationRequest.class)
                .outType(MessageCreationRequest.class)
                .to("seda:newMessage");

        // Configure route for sending messages
        from("seda:newMessage?concurrentConsumers=20")
                .routeId("smpp-sender")
                .process(messageProcessor)
                .setHeader("CamelSmppDestAddr", simple("94${in.body.sender}"))
                .setBody(simple("${in.body.messageBody}"))
                .to("smpp://{{smpp.tr.systemid}}@{{smpp.tr.host}}:{{smpp.tr.port}}"
                        + "?password={{smpp.tr.password}}"
                        + "&enquireLinkTimer=3000"
                        + "&transactionTimer=5000"
                        + "&sourceAddrTon={{smpp.source.addr.ton}}"
                        + "&sourceAddrNpi={{smpp.source.addr.npi}}"
                        + "&destAddrTon={{smpp.dest.addr.ton}}"
                        + "&destAddrNpi={{smpp.dest.addr.npi}}"
                        + "&sourceAddr={{smpp.source.address}}");
    }
}
