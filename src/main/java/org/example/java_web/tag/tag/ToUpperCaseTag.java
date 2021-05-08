package org.example.java_web.tag.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 处理 Body 运行结果
 *
 * @author lifei
 */
public class ToUpperCaseTag extends BodyTagSupport {
    @Override
    public int doEndTag() throws JspException {
        String upper = this.getBodyContent().getString().toUpperCase();
        try {
            pageContext.getOut().write(upper);
        } catch (Exception e) {
            throw new JspException();
        }
        return super.doEndTag();
    }
}
