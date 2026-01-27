package com.example.sftpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SftpConnectivityRunner implements CommandLineRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(SftpConnectivityRunner.class);

  private final SftpProperties properties;

  public SftpConnectivityRunner(SftpProperties properties) {
    this.properties = properties;
  }

  @Override
  public void run(String... args) {
    LOGGER.info(
        "Attempting {} connection to {}:{}",
        properties.getProtocol(),
        properties.getHost(),
        properties.getPort());

    FileTransferClient client = new FtpWrapper(properties).createClient();
    boolean success = client.testConnection();
    if (!success) {
      LOGGER.warn("Connectivity test failed using protocol {}", properties.getProtocol());
    }
  }
}
