package com.epam.jwd.web_app;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        String password = "yaAdmin";
        String md5Hex = DigestUtils.md5Hex(password);
        logger.error(md5Hex);
    }
}
