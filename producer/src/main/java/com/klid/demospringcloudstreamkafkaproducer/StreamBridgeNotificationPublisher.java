package com.klid.demospringcloudstreamkafkaproducer;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StreamBridgeNotificationPublisher implements NotificationPublisher {

  private final StreamBridge streamBridge;

  @Override
  public UUID publishNotification(String destination, Notification notification) {
    var notificationId = UUID.randomUUID();
    var sendResult = sendNotification(destination, notification, notificationId);
    if (!sendResult) {
      throw new NotificationException("Cannot send message to %s".formatted(destination));
    }
    return notificationId;
  }

  private boolean sendNotification(String destination, Notification notification, UUID notificationId) {
    try {
      var message = MessageBuilder.withPayload(notification)
        .setHeader(KafkaHeaders.KEY, notification.toEmail())
        .setHeader(KafkaHeaders.CORRELATION_ID, notificationId.toString())
        .build();
      return streamBridge.send(destination, message);
    } catch (Exception ex) {
      throw new NotificationException("Cannot send message to %s".formatted(destination), ex);
    }
  }
}
