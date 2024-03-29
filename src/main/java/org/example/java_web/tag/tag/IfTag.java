package org.example.java_web.tag.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag
 *
 * @author lifei
 */
public class IfTag extends TagSupport {
    private boolean test;

    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public int doStartTag() throws JspException {
        if (test) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
