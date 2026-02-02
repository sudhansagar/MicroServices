package com.example.sftpclient;

public class FtpWrapper {
  private final SftpProperties properties;

  public FtpWrapper(SftpProperties properties) {
    this.properties = properties;
  }

  public FileTransferClient createClient() {
    if (properties.getProtocol() == SftpProperties.Protocol.FTP) {
      return new FtpClient(properties);
    }
    return new SftpClient(properties);
  }
}
