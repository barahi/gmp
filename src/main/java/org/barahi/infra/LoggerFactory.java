package org.barahi.infra;

import java.util.logging.Logger;

public class LoggerFactory {
    public static Logger createLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }
}
