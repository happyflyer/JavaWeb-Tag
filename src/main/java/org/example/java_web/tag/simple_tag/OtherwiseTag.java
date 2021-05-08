package org.example.java_web.tag.simple_tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 与父标签沟通
 *
 * @author lifei
 */
public class OtherwiseTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        JspTag parent = getParent();
        if (!(parent instanceof ChooseTag)) {
            throw new JspException("OtherwiseTag 必须置于 ChooseTag 标签中");
        }

        ChooseTag choose = (ChooseTag) parent;
        if (!choose.isMatched()) {
            this.getJspBody().invoke(null);
        }
    }
}
