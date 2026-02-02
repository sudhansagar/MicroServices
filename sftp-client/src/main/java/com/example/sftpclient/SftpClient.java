package com.example.sftpclient;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.net.SocketTimeoutException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SftpClient implements FileTransferClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(SftpClient.class);

  private final SftpProperties properties;

  public SftpClient(SftpProperties properties) {
    this.properties = properties;
  }

  @Override
  public ConnectionTestResult testConnection() {
    JSch jsch = new JSch();
    Session session = null;
    try {
      configureIdentity(jsch);

      session = jsch.getSession(properties.getUsername(), properties.getHost(), properties.getPort());
      if (properties.getPassword() != null && !properties.getPassword().isBlank()) {
        session.setPassword(properties.getPassword());
      }
      session.setConfig("StrictHostKeyChecking", properties.getStrictHostKeyChecking());
      session.setTimeout(properties.getSessionTimeoutMs());
      session.setServerAliveInterval(properties.getServerAliveIntervalMs());
      session.setServerAliveCountMax(properties.getServerAliveCountMax());
      session.connect(properties.getTimeoutMs());

      LOGGER.info("SFTP connectivity test succeeded. Session connected: {}", session.isConnected());
      return new ConnectionTestResult(true, properties.getProtocol(), "Connectivity test succeeded.");
    } catch (JSchException ex) {
      LOGGER.error("SFTP connectivity test failed: {}", ex.getMessage(), ex);
      return new ConnectionTestResult(
          false, properties.getProtocol(), buildErrorMessage(ex));
    } finally {
      if (session != null && session.isConnected()) {
        session.disconnect();
        LOGGER.info("SFTP session disconnected.");
      }
    }
  }

  private void configureIdentity(JSch jsch) throws JSchException {
    if (properties.getPrivateKeyPath() == null || properties.getPrivateKeyPath().isBlank()) {
      return;
    }

    if (Objects.requireNonNullElse(properties.getPrivateKeyPassphrase(), "").isBlank()) {
      jsch.addIdentity(properties.getPrivateKeyPath());
      return;
    }

    jsch.addIdentity(properties.getPrivateKeyPath(), properties.getPrivateKeyPassphrase());
  }

  private String buildErrorMessage(JSchException ex) {
    if (isTimeout(ex)) {
      return "SFTP connectivity test failed: read timed out. "
          + "Increase sftp.timeout-ms or sftp.session-timeout-ms, "
          + "and verify network/firewall access.";
    }
    return "SFTP connectivity test failed: " + ex.getMessage();
  }

  private boolean isTimeout(JSchException ex) {
    if (ex.getCause() instanceof SocketTimeoutException) {
      return true;
    }
    String message = ex.getMessage();
    return message != null && message.contains("Read timed out");
  }
}
