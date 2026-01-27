package com.example.sftpclient;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpClient implements FileTransferClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(FtpClient.class);

  private final SftpProperties properties;

  public FtpClient(SftpProperties properties) {
    this.properties = properties;
  }

  @Override
  public boolean testConnection() {
    FTPClient ftpClient = new FTPClient();
    try {
      ftpClient.setConnectTimeout(properties.getTimeoutMs());
      ftpClient.connect(properties.getHost(), properties.getPort());
      boolean loggedIn = ftpClient.login(properties.getUsername(), properties.getPassword());
      if (!loggedIn) {
        LOGGER.error("FTP login failed for user {}", properties.getUsername());
        return false;
      }
      LOGGER.info("FTP connectivity test succeeded. Connected: {}", ftpClient.isConnected());
      return true;
    } catch (IOException ex) {
      LOGGER.error("FTP connectivity test failed: {}", ex.getMessage(), ex);
      return false;
    } finally {
      if (ftpClient.isConnected()) {
        try {
          ftpClient.logout();
          ftpClient.disconnect();
          LOGGER.info("FTP session disconnected.");
        } catch (IOException ex) {
          LOGGER.warn("Failed to close FTP session: {}", ex.getMessage(), ex);
        }
      }
    }
  }
}
