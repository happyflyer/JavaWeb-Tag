package org.example.java_web.tag.simple_tag;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 处理标签属性与 Body
 *
 * @author lifei
 */
public class ForEachTag extends SimpleTagSupport {
    private String var;
    private Collection<String> items;

    public void setVar(String var) {
        this.var = var;
    }

    public void setItems(Collection<String> items) {
        this.items = items;
    }

    @Override
    public void doTag() throws JspException, IOException {
        for (Object o : items) {
            // 设置标签 Body 可用的 EL 名称
            this.getJspContext().setAttribute(var, o);
            // 在循环中调用 invoke 方法
            this.getJspBody().invoke(null);
            this.getJspContext().removeAttribute(var);
        }
    }
}
