package org.example.java_web.tag.simple_tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Simple Tag
 *
 * @author lifei
 */
public class IfTag extends SimpleTagSupport {
    private boolean test;

    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public void doTag() throws JspException, IOException {
        if (test) {
            // 取得 JspFragment 调用 invoke()
            getJspBody().invoke(null);
        }
    }
}
