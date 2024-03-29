package org.example.java_web.tag.tag;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 重复执行标签 Body
 *
 * @author lifei
 */
public class ForEachTag extends TagSupport {
    private String var;
    private Iterator<?> iterator;

    public void setVar(String var) {
        this.var = var;
    }

    public void setItems(Collection<?> items) {
        this.iterator = items.iterator();
    }

    @Override
    public int doStartTag() throws JspException {
        if (iterator.hasNext()) {
            this.pageContext.setAttribute(var, iterator.next());
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (iterator.hasNext()) {
            this.pageContext.setAttribute(var, iterator.next());
            return EVAL_BODY_AGAIN;
        }
        this.pageContext.removeAttribute(var);
        return SKIP_BODY;
    }
}
