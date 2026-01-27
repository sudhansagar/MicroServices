package com.example.sftpclient;

import org.springframework.stereotype.Service;

@Service
public class ConnectionTestService {
  private final SftpProperties properties;

  public ConnectionTestService(SftpProperties properties) {
    this.properties = properties;
  }

  public ConnectionTestResult testConnection() {
    FileTransferClient client = new FtpWrapper(properties).createClient();
    boolean success = client.testConnection();
    String message =
        success
            ? "Connectivity test succeeded."
            : "Connectivity test failed. Check logs for details.";
    return new ConnectionTestResult(success, properties.getProtocol(), message);
  }
}
