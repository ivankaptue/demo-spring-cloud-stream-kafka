package com.klid.demospringcloudstreamkafkaproducer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final NotificationPublisher notificationPublisher;

  @PostMapping
  public ResponseEntity<Map<String, UUID>> post(@Valid @RequestBody Notification notification) {
    log.info("publish notification : " + notification);

    var notificationId = notificationPublisher.publishNotification("notification-channel", notification);

    log.info("notification published with notificationId {}", notificationId);

    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("notificationId", notificationId));
  }
}
