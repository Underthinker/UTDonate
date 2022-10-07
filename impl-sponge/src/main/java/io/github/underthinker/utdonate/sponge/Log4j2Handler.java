package io.github.underthinker.utdonate.sponge;

import org.apache.logging.log4j.Logger;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Log4j2Handler extends Handler {
    private final Logger logger;

    public Log4j2Handler(Logger logger) {
        this.logger = logger;
        setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                StringBuilder builder = new StringBuilder();
                builder.append(formatMessage(record));
                Throwable throwable = record.getThrown();
                if (throwable != null) {
                    builder.append("\n");
                    builder.append(throwable.getClass().getName()).append(": ").append(throwable.getMessage()).append("\n");
                    for (StackTraceElement element : throwable.getStackTrace()) {
                        builder.append("  at ")
                                .append(element.getClassName())
                                .append(" ")
                                .append(element.getFileName())
                                .append(":")
                                .append(element.getLineNumber())
                                .append("\n");
                    }
                }
                return builder.toString();
            }
        });
    }

    @Override
    public void publish(LogRecord record) {
        String message = getFormatter().format(record);
        switch (record.getLevel().getName()) {
            case "SEVERE":
                logger.error(message);
                break;
            case "WARNING":
                logger.warn(message);
                break;
            case "CONFIG":
            case "FINE":
                logger.debug(message);
                break;
            case "FINER":
            case "FINEST":
                logger.trace(message);
                break;
            default:
                logger.info(message);
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
