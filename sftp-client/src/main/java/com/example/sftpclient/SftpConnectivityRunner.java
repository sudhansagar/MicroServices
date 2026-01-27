package com.example.sftpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SftpConnectivityRunner implements CommandLineRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(SftpConnectivityRunner.class);

  private final SftpProperties properties;
  private final ConnectionTestService testService;

  public SftpConnectivityRunner(SftpProperties properties, ConnectionTestService testService) {
    this.properties = properties;
    this.testService = testService;
  }

  @Override
  public void run(String... args) {
    LOGGER.info(
        "Attempting {} connection to {}:{}",
        properties.getProtocol(),
        properties.getHost(),
        properties.getPort());

    ConnectionTestResult result = testService.testConnection();
    if (!result.isSuccess()) {
      LOGGER.warn("Connectivity test failed using protocol {}", properties.getProtocol());
    }
  }
}
