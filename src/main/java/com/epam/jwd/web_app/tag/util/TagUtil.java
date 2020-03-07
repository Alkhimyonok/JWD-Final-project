package com.epam.jwd.web_app.tag.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;

import com.epam.jwd.web_app.dao.impl.AdminDaoImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagUtil {
    private final static Logger logger = LogManager.getLogger(AdminDaoImpl.class);

    public static String translate(String locale, String info) {
        if (locale.equals("en")) {
            return info;
        }
        byte out[] = ("text=" + info).getBytes();
        if (out.length > 10000) {
            logger.error("Error. Text too long");
            return "";
        }
        String key = "trnsl.1.1.20200301T210346Z.142cab1fc1cc76db.6641ea221e7eb5d7e249ec6e68ce95a9eee39a61";
        String baseUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate";
        String finalUrl = baseUrl + "?lang=" + locale + "&key=" + key;
        try {
            URL url = new URL(finalUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//создание канала
            connection.setDoOutput(true);//соединение для вывода
            connection.setRequestMethod("POST");
            //добавляем параметры в заголовок
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//формат body
            connection.setRequestProperty("Content-Length", out.length + "");//размер в байтах
            connection.setRequestProperty("Accept", "*/*");//допустимый формат
            connection.setRequestProperty("Charset", "utf-8");//кодировка для представления пользователю
            connection.getOutputStream().write(out);
            if (connection.getResponseCode() == 200) {
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream(), "utf-8");
                JsonObject jobj = new JsonParser().parse(inputStream).getAsJsonObject();
                JsonArray jarr = jobj.get("text").getAsJsonArray();
                return jarr.get(0).getAsString();
            } else {
                logger.error("Error. Site response non 200");
                return "";
            }
        } catch (IOException e) {
            logger.error("Error");
            return "";
        }
    }
}
