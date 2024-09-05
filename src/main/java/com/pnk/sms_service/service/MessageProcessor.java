package com.pnk.sms_service.service;

import com.pnk.sms_service.dto.MessageCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;


@Component("messageProcessor")
@Slf4j
public class MessageProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        MessageCreationRequest msg = exchange.getIn().getBody(MessageCreationRequest.class);
        // do something with the msg and/or exchange here
        log.info("process >> msg: {}", msg);

    }
}
