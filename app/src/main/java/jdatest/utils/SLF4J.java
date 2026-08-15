package jdatest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4J {
  public enum logModes {
    INFO("INFO"),
    WARN("WARN"),
    ERROR("ERROR"),
    DEBUG("DEBUG");

    private final String val;

    logModes(String string) {
      this.val = string;
    }

    public String getValue() {
      return this.val;
    }
    
  }
  public static void Log(Object text, logModes modes) {
    Class<?> clazz = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
    final Logger logger = LoggerFactory.getLogger(clazz); 
    switch (modes.getValue()) {
      case "INFO":
        logger.info(text.toString());
        break;
      case "ERROR":
        logger.error(text.toString());
        break;
      case "WARN":
        logger.warn(text.toString());
        break;
      case "DEBUG":
        logger.debug(text.toString());
        break;
      default:
        break;
    }
  }
}
