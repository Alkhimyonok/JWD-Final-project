package com.epam.jwd.web_app.dao.util;

import org.apache.commons.codec.digest.DigestUtils;

public class UserUtil {
    //Apache Common Codec
    public static String passwordHash(String password) {
        return DigestUtils.md5Hex(password);
    }
}
