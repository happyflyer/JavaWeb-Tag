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
public class WhenTag extends SimpleTagSupport {
    private boolean test;

    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspTag parent = getParent();
        if (!(parent instanceof ChooseTag)) {
            throw new JspException("WhenTag 必须置于 ChooseTag 标签中");
        }

        ChooseTag choose = (ChooseTag) parent;
        if (!choose.isMatched() && test) {
            choose.setMatched(true);
            this.getJspBody().invoke(null);
        }
    }
}
