package com.example.sftpclient;

public class ConnectionTestResult {
  private final boolean success;
  private final SftpProperties.Protocol protocol;
  private final String message;

  public ConnectionTestResult(boolean success, SftpProperties.Protocol protocol, String message) {
    this.success = success;
    this.protocol = protocol;
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public SftpProperties.Protocol getProtocol() {
    return protocol;
  }

  public String getMessage() {
    return message;
  }
}
