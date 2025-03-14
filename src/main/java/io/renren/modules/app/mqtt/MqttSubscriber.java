package io.renren.modules.app.mqtt;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MqttSubscriber {

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(@Payload String payload, @Header String topic) {
        System.out.println("Received message from topic '" + topic + "': " + payload);
    }
}
