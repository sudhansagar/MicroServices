package com.example.sftpclient;

import com.jcraft.jsch.Logger;
import org.slf4j.LoggerFactory;

public class JschLogger implements Logger {
  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JschLogger.class);

  @Override
  public boolean isEnabled(int level) {
    return switch (level) {
      case DEBUG -> LOGGER.isDebugEnabled();
      case INFO -> LOGGER.isInfoEnabled();
      case WARN -> LOGGER.isWarnEnabled();
      case ERROR, FATAL -> LOGGER.isErrorEnabled();
      default -> false;
    };
  }

  @Override
  public void log(int level, String message) {
    switch (level) {
      case DEBUG -> LOGGER.debug(message);
      case INFO -> LOGGER.info(message);
      case WARN -> LOGGER.warn(message);
      case ERROR, FATAL -> LOGGER.error(message);
      default -> LOGGER.trace(message);
    }
  }
}
