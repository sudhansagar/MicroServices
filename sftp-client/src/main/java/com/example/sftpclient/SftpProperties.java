package com.example.sftpclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {
  private String host;
  private int port = 22;
  private String username;
  private String password;
  private String privateKeyPath;
  private String privateKeyPassphrase;
  private int timeoutMs = 10000;
  private int sessionTimeoutMs = 10000;
  private int serverAliveIntervalMs = 10000;
  private int serverAliveCountMax = 1;
  private String strictHostKeyChecking = "no";
  private Protocol protocol = Protocol.SFTP;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPrivateKeyPath() {
    return privateKeyPath;
  }

  public void setPrivateKeyPath(String privateKeyPath) {
    this.privateKeyPath = privateKeyPath;
  }

  public String getPrivateKeyPassphrase() {
    return privateKeyPassphrase;
  }

  public void setPrivateKeyPassphrase(String privateKeyPassphrase) {
    this.privateKeyPassphrase = privateKeyPassphrase;
  }

  public int getTimeoutMs() {
    return timeoutMs;
  }

  public void setTimeoutMs(int timeoutMs) {
    this.timeoutMs = timeoutMs;
  }

  public int getSessionTimeoutMs() {
    return sessionTimeoutMs;
  }

  public void setSessionTimeoutMs(int sessionTimeoutMs) {
    this.sessionTimeoutMs = sessionTimeoutMs;
  }

  public int getServerAliveIntervalMs() {
    return serverAliveIntervalMs;
  }

  public void setServerAliveIntervalMs(int serverAliveIntervalMs) {
    this.serverAliveIntervalMs = serverAliveIntervalMs;
  }

  public int getServerAliveCountMax() {
    return serverAliveCountMax;
  }

  public void setServerAliveCountMax(int serverAliveCountMax) {
    this.serverAliveCountMax = serverAliveCountMax;
  }

  public String getStrictHostKeyChecking() {
    return strictHostKeyChecking;
  }

  public void setStrictHostKeyChecking(String strictHostKeyChecking) {
    this.strictHostKeyChecking = strictHostKeyChecking;
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }

  public enum Protocol {
    FTP,
    SFTP
  }
}
