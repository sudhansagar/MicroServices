package com.example.sftpclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SftpProperties.class)
public class SftpClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(SftpClientApplication.class, args);
  }
}
