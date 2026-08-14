package jdatest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SL4J {
  public enum logModes {
    INFO("INFO"),
    WARN("WARN"),
    ERROR("ERROR");

    private final String val;

    logModes(String string) {
      this.val = string;
    }

    public String getValue() {
      return this.val;
    }
    
  }
  public static void Log(String text, logModes modes ,Class<?> clazz) {
    final Logger logger = LoggerFactory.getLogger(clazz);
    switch (modes.getValue()) {
      case "INFO":
        logger.info(text);
        break;
      case "ERROR":
        logger.error(text);
        break;
      case "WARN":
        logger.warn(text);
        break;

      default:
        break;
    }
  }
}
