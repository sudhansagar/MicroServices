package com.example.sftpclient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class ConnectionTestController {
  private final ConnectionTestService testService;

  public ConnectionTestController(ConnectionTestService testService) {
    this.testService = testService;
  }

  @PostMapping("/test")
  public ResponseEntity<ConnectionTestResult> testConnection() {
    ConnectionTestResult result = testService.testConnection();
    HttpStatus status = result.isSuccess() ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
    return ResponseEntity.status(status).body(result);
  }
}
