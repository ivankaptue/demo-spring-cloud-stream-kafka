package com.klid.demospringcloudstreamkafkaconsumer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Configuration
public class NotificationListenerConfig {

  private final NotificationListener notificationListener;

  @Bean
  public Consumer<Message<Notification>> notificationConsumer() {
    return notificationListener::consumeMessage;
  }
}
