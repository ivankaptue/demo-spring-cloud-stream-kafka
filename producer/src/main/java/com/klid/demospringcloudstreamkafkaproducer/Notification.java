package com.klid.demospringcloudstreamkafkaproducer;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record Notification(
  @NotBlank @Length(max = 60) String fromEmail,
  @NotBlank @Length(max = 60) String toEmail,
  @NotBlank @Length(max = 1024) String message
) {
}
