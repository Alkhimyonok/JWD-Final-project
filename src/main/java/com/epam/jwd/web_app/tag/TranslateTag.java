package com.epam.jwd.web_app.tag;

import com.epam.jwd.web_app.tag.util.TagUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

public class TranslateTag extends TagSupport {
    private final static Logger logger = LogManager.getLogger(TranslateTag.class);

    private String info;
    private String locale;

    public void setInfo(String info) {
        this.info = info;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() {
        String translateInfo = TagUtil.translate(locale, info);
        JspWriter out = pageContext.getOut();
        try {
            out.write(translateInfo);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
