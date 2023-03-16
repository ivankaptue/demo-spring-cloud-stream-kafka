package com.klid.demospringcloudstreamkafkaproducer;

import java.util.UUID;

public interface NotificationPublisher {
  UUID publishNotification(String destination, Notification notification);
}
