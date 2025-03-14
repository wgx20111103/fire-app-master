package io.renren.modules.app.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisher {

    @Autowired
    private MessageChannel mqttOutboundChannel;

    public void publishToMqtt(String payload, String topic) {
        Message<String> message = MessageBuilder.withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, topic)
                .build();
        mqttOutboundChannel.send(message);
    }
}
