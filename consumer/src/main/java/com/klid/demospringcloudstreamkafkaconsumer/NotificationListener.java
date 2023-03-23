package com.klid.demospringcloudstreamkafkaconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationListener {

  public void consumeMessage(Message<Notification> message) {
    log.info("Received message {}", message);

    var notification = message.getPayload();
    var correlationId = message.getHeaders().get(KafkaHeaders.CORRELATION_ID, String.class);
    var messageKey = message.getHeaders().get(KafkaHeaders.RECEIVED_KEY, String.class);
    var partition = message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION, Integer.class);
    var offset = message.getHeaders().get(KafkaHeaders.OFFSET, Long.class);
    var timestamp = message.getHeaders().get(KafkaHeaders.RECEIVED_TIMESTAMP, Long.class);

    var receivedData = """
      notification: %s,
      correlationId: %s,
      messageKey: %s,
      partition: %s,
      offset: %s,
      timestamp: %s,
      """.formatted(notification, correlationId, messageKey, partition, offset, timestamp);

    log.info("received data: {}", receivedData);

    var acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
    if (acknowledgment != null) {
      log.info("Acknowledgment provided");
      acknowledgment.acknowledge();
    }
  }
}
