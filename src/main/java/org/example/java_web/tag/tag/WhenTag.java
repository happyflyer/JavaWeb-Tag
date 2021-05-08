package org.example.java_web.tag.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 与父标签沟通
 *
 * @author lifei
 */
public class WhenTag extends TagSupport {
    private boolean test;

    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public int doStartTag() throws JspException {
        JspTag parent = getParent();
        if (!(parent instanceof ChooseTag)) {
            throw new JspException("WhenTag 必须置于 ChooseTag 标签中");
        }

        ChooseTag choose = (ChooseTag) parent;
        if (choose.isMatched() || !test) {
            return SKIP_BODY;
        }
        choose.setMatched(true);
        return EVAL_BODY_INCLUDE;
    }
}
