package org.example.java_web.tag.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 与父标签沟通
 *
 * @author lifei
 */
public class ChooseTag extends TagSupport {
    private boolean matched;

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    @Override
    public int doStartTag() throws JspException {
        matched = false;
        return EVAL_BODY_INCLUDE;
    }
}
