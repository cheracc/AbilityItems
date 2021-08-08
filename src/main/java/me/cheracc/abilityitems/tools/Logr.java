package me.cheracc.abilityitems.tools;

import java.util.logging.Logger;

public class Logr {
    private final Logger logger;
    private final Translator trans;
    private boolean showDebugMessages;

    public Logr(Logger logger, Translator trans) {
        this.logger = logger;
        this.trans = trans;
        this.showDebugMessages = false;
    }

    public void setShowDebugMessages(boolean value) {
        showDebugMessages = value;
    }

    public void info(String message) {
        logger.info(trans.late(message));
    }

    public void info(String message, Object... args) {
        logger.info(String.format(trans.late(message), args));
    }

    public void warn(String message) {
        logger.warning(trans.late(message));
    }

    public void warn(String message, Object... args) {
        logger.warning(String.format(trans.late(message), args));
    }

    public void error(String message) {
        logger.severe(trans.late(message));
    }

    public void error(String message, Object... args) {
        logger.severe(String.format(trans.late(message), args));
    }

    public void debug(String message) {
        if (showDebugMessages)
            logger.info("Debug> " + trans.late(message));
    }

    public void debug(String message, Object... args) {
        if (showDebugMessages)
            logger.info("Debug> " + String.format(trans.late(message), args));
    }
}
