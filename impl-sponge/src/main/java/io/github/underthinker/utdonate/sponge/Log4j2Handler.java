package io.github.underthinker.utdonate.sponge;

import org.apache.logging.log4j.Logger;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class Log4j2Handler extends Handler {
    private final Logger logger;

    public Log4j2Handler(Logger logger) {
        this.logger = logger;
        setFormatter(new SimpleFormatter());
    }

    @Override
    public void publish(LogRecord record) {
        String message = getFormatter().formatMessage(record);
        Throwable throwable = record.getThrown();
        switch (record.getLevel().getName()) {
            case "SEVERE":
                logger.error(message, throwable);
                break;
            case "WARNING":
                logger.warn(message, throwable);
                break;
            case "CONFIG":
            case "FINE":
                logger.debug(message, throwable);
                break;
            case "FINER":
            case "FINEST":
                logger.trace(message, throwable);
                break;
            default:
                logger.info(message, throwable);
                break;
        }
    }

    @Override
    public void flush() {
        // EMPTY
    }

    @Override
    public void close() throws SecurityException {
        // EMPTY
    }
}
