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
    return client.testConnection();
  }
}
