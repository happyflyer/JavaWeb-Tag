# [JavaWeb-Tag](https://github.com/happyflyer/JavaWeb-Tag)

## 1. Tag File 自定义标签

- 带有 `Scriptlet` 的 JSP

```java
<%
    List<String> errors = (List<String>) request.getAttribute("errors");
    if (errors != null) {
%>
<ul style="color: rgb(255, 0, 0);">
    <%
        for (String error : errors) {
    %>
    <li><%= error %>
    </li>
    <%
        }
    %>
</ul>
<%
    }
%>
```

- 使用 `JSTL` 后的 JSP

```java
<c:if test="${requestScope.errors != null}">
    <ul style="color: rgb(255, 0, 0);">
        <c:forEach var="error" items="${requestScope.errors}">
            <li>${error}</li>
        </c:forEach>
    </ul>
</c:if>
```

- 使用 Tag File，放在 `WEB-INF/tags` 目录下

```java
<%@ tag description="显示错误信息的标签" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${requestScope.errors != null}">
    <h3>请求错误</h3>
    <ul style="color: rgb(255, 0, 0);">
        <c:forEach var="error" items="${requestScope.errors}">
            <li>${error}</li>
        </c:forEach>
    </ul>
</c:if>
```

- `Tag File` 中可以使用 `JSTL`、`EL`、`Scriptlet`
- `Tag File` 基本上是给不会 Java 的网页设计人员使用的

```java
<%@ taglib prefix="html" tagdir="/WEB-INF/tags" %>
```

```java
<html:Errors/>
```

- 虽然 `tagdir` 属性可以指定 `Tag File` 的位置
- 但事实上只能放在 `WEB-INF/tags` 的子文件夹中
- 也就是说，如果以 `tagdir` 属性设置
- `Tag File` 就只能放在 `WEB-INF/tags` 或子文件夹中

`.tag` 文件会被容器转译，

- 转译为 `javax.servlet.jsp.tagext.SimpleTagSupport` 的子类
- `Errors.tag` 转译后生成的类源代码名称为 `Errors_tag.java`
- 在 `Tag File` 中可以使用
  - `out`
  - `config`
  - `request`
  - `response`
  - `session`
  - `jspContext`
- 等隐式对象
- 其中 jspContext 转译后的是 `javax.servlet.jsp.JspContext` 对（`pageContext` 的父类）

```java
package org.apache.jsp.tag.web;
public final class Errors_tag
    extends javax.servlet.jsp.tagext.SimpleTagSupport
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {
    // ...
    public void doTag() throws javax.servlet.jsp.JspException, java.io.IOException {
        javax.servlet.jsp.PageContext _jspx_page_context = (javax.servlet.jsp.PageContext)jspContext;
        javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest) _jspx_page_context.getRequest();
        javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) _jspx_page_context.getResponse();
        javax.servlet.http.HttpSession session = _jspx_page_context.getSession();
        javax.servlet.ServletContext application = _jspx_page_context.getServletContext();
        javax.servlet.ServletConfig config = _jspx_page_context.getServletConfig();
        javax.servlet.jsp.JspWriter out = jspContext.getOut();
        // ...
    }
    // ...
}
```

### 1.1. 处理标签属性和 Body

- 处理标签属性，需要在 `.tag` 文件中添加属性指令并使用

```java
<%@ tag description="Header 内容" language="java" pageEncoding="UTF-8" %>
<%@ attribute name="title" %>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
</head>
```

```java
<html:Header title="使用 Header 的用户注册"/>
```

- 到目前使用的 `Tag File` 都是没有 `Body` 的，实际上可以有 `Body` 内容的

```java
<%@ tag description="HTML 懒人标签" language="java" pageEncoding="UTF-8" %>
<%@ attribute name="title" %>
<html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
</head>
<body>
<jsp:doBody/>
</body>
</html>
```

- 使用有 `Body` 的 `Tag File` 的时候，默认不允许有 `Scriptlet`
- `tag` 指示元素的 `body-content` 属性默认为 `scriptless`

```java
<%@ tag body-content="scriptless" pageEncoding="UTF-8" %>
```

`body-content`

- `scriptless`
- `empty`：只能使用 `<html:Html/>`，在其他设置下可以有 `Body` 和无 `Body` 都使用
- `tagdependent`：`Scriptlet`、`EL`、自定义标签都当作纯文字输出

只要是有 `Body` 的 `Tag File`

- 在其中编写 `Scriptlet` 就是没有意义的
- 要不就是不允许出现，要不就是当作纯文字输出

### 1.2. TLD 文件

- 如果将 `Tag File` 的 `*.tag` 文件放在 `WEB-INF/tags` 文件夹或者其子文件夹
- 并在 JSP 中使用 `taglib` 指示元素的 `tagdir` 属性指定 `*.tag` 的位置
- 就可以使用 `Tag File` 了
- 其他人如果也想使用你的 `Tag File`
- 只需要将你的 `*.tag` 文件复制到他们自己的 `WEB-INF/tags` 文件夹下面就可以使用了

但是，

- Java 程序员一般是将自己的代码打包为一个 `*.jar` 文件一起发给别人使用
- 而且，来回拷贝 `*.tag` 文件不是一个好办法
- 这个时候，就需要 `TLD` 文件，这个文件就是你的标签的一个描述文件

使用 `TLD` 文件注意：

- `*.tag` 文件必须放在 `*.jar` 文件的 `META-INF/tags` 文件夹或者子文件夹下
- 要定义 `TLD` (`Tag Library Description`) 文件
- `TLD` 文件必须放在 `*.jar` 文件的 `META-INF/TLDS` 文件夹下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>html</short-name>
  <uri>http://127.0.0.1:8080/JavaWeb_Tag_war/html</uri>
  <tag-file>
    <name>Errors1</name>
    <path>/META-INF/tags/Errors1.tag</path>
  </tag-file>
  <tag-file>
    <name>Errors2</name>
    <path>/META-INF/tags/Errors2.tag</path>
  </tag-file>
  <tag-file>
    <name>Header</name>
    <path>/META-INF/tags/Header.tag</path>
  </tag-file>
  <tag-file>
    <name>Html</name>
    <path>/META-INF/tags/Html.tag</path>
  </tag-file>
</taglib>
```

```bash
cd src
jar cvf ../html.jar *
```

```java
<%@ taglib prefix="html" uri="http://127.0.0.1:8080/JavaWeb_Tag_war/html" %>
```

## 2. Simple Tag 自定义标签

```java
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
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>f</short-name>
  <uri>http://127.0.0.1:8080/JavaWeb_Tag_war/f</uri>
  <tag>
    <name>if</name>
    <tag-class>IfTagorg.example.java_web.tag.simple_tag.IfTag</tag-class>
    <body-content>scriptless</body-content>
    <attribute>
      <name>test</name>
      <required>true</required>
      <rtexprvalue>true</rtexprvalue>
      <type>boolean</type>
    </attribute>
  </tag>
</taglib>
```

```java
<%@ taglib prefix="f" uri="http://127.0.0.1:8080/JavaWeb_Tag_war/f" %>
```

```java
<f:if test="${param.password=='123456'}">
    <h1>你的秘密数据在此！</h1>
</f:if>
```

1. 编写标签类，继承 `SimpleTagSupport` 类，重写 `doTag()` 方法
2. 定义 `TLD` 文件
3. 使用 `taglib` 指示元素

### 2.1. API 架构与生命周期

- 所有的 JSP 自定义标签都实现了 `JspTag` 接口
- `JspTag` 只是一个标示接口，本身没有定义任何方法

```java
public interface JspTag {
}
```

- `SimpleTag` 接口继承了 `JspTag`
- 定义了 `Simple Tag` 开发时所需的基本行为
- 开发 `Simple Tag` 标签处理器时必须实现 `SimpleTag` 接口

```java
public interface SimpleTag extends JspTag {
    void doTag() throws JspException, IOException;
    void setParent(JspTag var1);
    JspTag getParent();
    void setJspContext(JspContext var1);
    void setJspBody(JspFragment var1);
}
```

- 不过通常继承 `SimpleTagSupport` 类
- `SimpleTagSupport` 类对 `SimpleTag` 接口的所有方法做了基本实现
- 自定义标签的类只需要重新定义感兴趣的方法（`doTag()` 方法）

```java
public class SimpleTagSupport implements SimpleTag {
    private JspTag parentTag;
    private JspContext jspContext;
    private JspFragment jspBody;
    public SimpleTagSupport() {}
    public void doTag() throws JspException, IOException {}
    public void setParent(JspTag parent) { ... }
    public JspTag getParent() { ... }
    public void setJspContext(JspContext pc) { ... }
    protected JspContext getJspContext() { ... }
    public void setJspBody(JspFragment jspBody) { ... }
    protected JspFragment getJspBody() { ... }
    public static final JspTag findAncestorWithClass(JspTag from, Class<?> klass) { ... }
}
```

`Simple Tag` 自定义标签的生命周期：

1. 创建自定义标签处理器实例
2. 调用标签处理器的 `setJspContext()` 方法设置 `pageContext` 实例
3. 如果是嵌套标签中的内层标签，还会调用 `setParent()` 方法，并传入外层标签处理器的实例
4. 设置标签处理器的属性(例如 `IfTag` 类中的 `setTest()` 方法设置)
5. 调用标签处理器的 `setJspBody()` 方法设置 `JspFragment` 实例
6. 嗲用标签处理器的 `doTag()` 方法
7. 销毁标签处理器实例

- 每一次的请求都会创建新的标签处理器实例，而在执行 `doTag()` 后就销毁实例
- 所以 `Simple Tag` 的实现中，建议不要有一些耗资源的动作，如庞大的对象、连线的获取等
- 正如 `Simple Tag` 名称所表示的
- 这并不仅代表它实现上比较简单（相较于 `Tag` 的实现方式）
- 也代表着它最好用来做一些简单的事务

同样的道理，

- 由于 `Tag File` 转译后会成为继承 `SimpleTagSupport` 的类
- 所以在 `Tag File` 中，也建议不要有一些耗资源的操作

标签处理器中设置了 `pageContext`

- 可以用它来取得 JSP 页面的所有对象
- 进行所有在 JSP 页面 `Scriptlet` 中可以执行的内容

`JspFragment` 就如其名称所示，是个 JSP 页面中的片段内容

- 在 JSP 中使用自定义标签时若包括 `Body`，将会转译为一个 `JspFragment` 类
- 而 `Body` 内容将会在 `invoke()` 方法中处理
- 在 `<f:if>` 例子中 `Body` 内容将转译为以下的 `JspFragment` 实现类（一个内部类）

```java
private class Helper
      extends org.apache.jasper.runtime.JspFragmentHelper
  {
    // ...
    public boolean invoke0( javax.servlet.jsp.JspWriter out )
      throws java.lang.Throwable
    {
      out.write("\n");
      out.write("    <h1>你的秘密数据在此！</h1>\n");
      return false;
    }
    public void invoke( java.io.Writer writer )
      throws javax.servlet.jsp.JspException
    {
      // ...
      if( writer != null ) {
        out = this.jspContext.pushBody(writer);
      } else {
        out = this.jspContext.getOut();
      }
      try {
        // ...
        invoke0( out );
        // ...
      }
      catch( java.lang.Throwable e ) {
        if (e instanceof javax.servlet.jsp.SkipPageException)
            throw (javax.servlet.jsp.SkipPageException) e;
        throw new javax.servlet.jsp.JspException( e );
      }
      finally {
        if( writer != null ) {
          this.jspContext.popBody();
        }
      }
    }
  }
```

- 所以在 `doTag()` 方法中使用 `getJspBody()` 取得 `JspFragment` 实例
- 且调用其 `invoke()` 方法时传入 `null`
- 这表示将使用 `PageContext` 取得默认的 `JspWriter` 对象来作输出响应（而并非不作响应）
- 接着进行 `Body` 内容的输出
- 如果 `Body` 内容中包括 `EL` 或内层标签，则会先处理（在 `<body-content>` 设置为 `script1ess` 的情况下）
- 在上面的简单范例中，只是将 `<f:if>` `Body` 的 JSP 片段直接输出（也就是 `invoke0()` 的执行内容）

如果调用 `JspFragment` 的 `invoke()` 时传入了一个 `Writer` 实例，

- 则表示要将 `Body` 内容的运行结果以所设置的 `Writer` 实例输出
- 这就是处理标签的 `Body`
- 这个后面再讨论

如果执行 `doTag()` 的过程的某些条件下必须中断接下来页面的处理或输出，

- 则可以抛出 `javax.servlet.jsp.SkipPageException` 异常
- 这个异常对象会在 JSP 转译后的 `_jspService()` 中进行处理

如果抛出其他类型的异常，

- 则在 `PageContext` 的 `handlePageException()` 中会看看有无设置错误处理的相关机制
- 并尝试进行页面转发和包含的动作
- 否则就封装为 `ServletException` 并丢给容器做默认处理，就是 `Http Status 500` 错误

### 2.2. 处理标签属性和 Body

#### 2.2.1. forEach

```java
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
```

- 在 `doTag()` 方法中每调用一次 `invoke()` ，就会执行一次 `Body` 内容
- `<f:forEach>` 标签的 `Body` 内容必须执行多次是通过多次调用 `invoke()` 完成的

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>f</short-name>
  <uri>http://127.0.0.1:8080/JavaWeb_Tag_war/f</uri>
  <tag>
    <name>forEach</name>
    <tag-class>ForEachTagorg.example.java_web.tag.simple_tag.ForEachTag</tag-class>
    <body-content>scriptless</body-content>
    <attribute>
      <name>var</name>
      <required>true</required>
      <type>java.lang.String</type>
    </attribute>
    <attribute>
      <name>items</name>
      <required>true</required>
      <rtexprvalue>true</rtexprvalue>
      <type>java.util.Collection</type>
    </attribute>
  </tag>
</taglib>
```

```java
<f:forEach items="<%=list%>" var="s">
    <h1>${s}</h1>
</f:forEach>
```

#### 2.2.2. toUpperCase

```java
public class ToUpperCaseTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        StringWriter writer = new StringWriter();
        this.getJspBody().invoke(writer);
        String upper = writer.toString().toUpperCase();
        this.getJspContext().getOut().print(upper);
    }
}
```

- 这里就是调用 `JspFragment` 的 `invoke()` 时传入了一个 `Writer`
- 标签 `Body` 执行的结果输出至 `StringWriter` 对象
- 此时再调用 `StringWriter` 对象的 `toString()` 取得输出的字符串结果
- 并调用 `toUpperCase()` 方法将结果转为大写
- 如果这个转换为大写后的字符串要输出至用户浏览器
- 则再通过 `PageContext` 的 `getOut()` 取得 `JspWriter` 对象
- 而后调用 `print()` 方法输出结果

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>f</short-name>
  <uri>http://127.0.0.1:8080/JavaWeb_Tag_war/f</uri>
  <tag>
    <name>toUpperCase</name>
    <tag-class>ToUpperCaseTagorg.example.java_web.tag.simple_tag.ToUpperCaseTag</tag-class>
    <body-content>scriptless</body-content>
  </tag>
</taglib>
```

```java
<f:toUpperCase>
    <h1>zhangsan</h1>
</f:toUpperCase>
```

```java
<f:toUpperCase>
    <f:forEach items="${requestScope.names}" var="s">
        <h1>${s}</h1>
    </f:forEach>
</f:toUpperCase>
```

若标签 `Body` 内容中还有内层标签

- 通过 `getOut()` 取得的就是所设置的 `Writer` 对象 `pushBody()` 返回的是 `BodyContent` 对象
- （除非内层标签在调用 `invoke()` 时，也设置了自己的 `Writer` 对象）
- 为 `JspWriter` 的子类，封装了所传入的 `Writer` 对象
- 因为 `BodyContent` 实例被 `out` 引用，而运行结果都通过 `out` 所引用的对象输出
- 所以最后 `BodyContent` 将会包括所有标签 `Body` 的运行结果（包括内层标签）
- 而这些结果，将再写入 `BodyContent` 所封装的 `Writer` 对象
- 在 `invoke()` 结束前会调用 `pageContext` 的 `popBody` 方法
- 从堆栈中恢复原本 `getOut()` 所应返回的 `JspWriter` 对象

```java
    public void invoke(java.io.Writer writer) {
        JspWriter out = null;
        if (writer != null) {
            out = this.jspContext().pushBody(writer);
        } else {
            out = this.jspContext().getOut();
        }
        try {
            // ...
        } catch (java.lang.Throwable e) {
            if (e instanceof javax.servlet.jsp.SkipPageException)
                throw (javax.servlet.jsp.SkipPageException) e;
            throw new javax.servlet.jsp.JspException(e);
        } finally {
            if (writer != null) {
                this.jspContext.popBody();
            }
        }
    }
```

- 如果调用 `invoke()` 时传入了 `Writer` 对象，则标签 `Body` 运行结果将输出至所设置的 `Writer` 对象

### 2.3. 与父标签沟通

```java
public class ChooseTag extends SimpleTagSupport {
    private boolean matched;
    public boolean isMatched() {
        return matched;
    }
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    @Override
    public void doTag() throws JspException, IOException {
        this.getJspBody().invoke(null);
    }
}
```

```java
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
```

```java
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
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>f</short-name>
  <uri>http://127.0.0.1:8080/JavaWeb_Tag_war/f</uri>
  <tag>
    <name>choose</name>
    <tag-class>ChooseTagorg.example.java_web.tag.simple_tag.ChooseTag</tag-class>
    <body-content>scriptless</body-content>
  </tag>
  <tag>
    <name>when</name>
    <tag-class>WhenTagorg.example.java_web.tag.simple_tag.WhenTag</tag-class>
    <body-content>scriptless</body-content>
    <attribute>
      <name>test</name>
      <required>true</required>
      <rtexprvalue>true</rtexprvalue>
      <type>boolean</type>
    </attribute>
  </tag>
  <tag>
    <name>otherwise</name>
    <tag-class>OtherwiseTagorg.example.java_web.tag.simple_tag.OtherwiseTag</tag-class>
    <body-content>scriptless</body-content>
  </tag>
</taglib>
```

```java
<f:choose>
    <f:when test="${param.user == 'zhangsan'}">
        <h1>${param.user}登录成功！</h1>
    </f:when>
    <f:otherwise>
        <h1>登录失败</h1>
    </f:otherwise>
</f:choose>
```

- 如果一个标签在多个嵌套标签中，想要直接获取某个指定类型的外层标签
- 可以使用 `SimpleTagSupport` 的 `findAncestorWithClass()` 静态方法

```java
SomeTag ancester = (SomeTag) findAncestorWithClass(this, SomeTag.class);
```

### 2.4. TLD 文件

与 `Tag File` 的 `TLD` 文件的区别

- `*.tld` 不一定放在 `META-INF/TLDS` 文件夹下，只要在 `META-INF` 文件夹或子文件夹下

1. `.jar` 文件根目录下放置编译好的类（包含类的包对应的文件夹）
2. `.jar` 文件 `META-INF` 文件夹或子文件夹下放置 `TLD` 文件

```bash
cd src
jar cvf ../fake.jar *
```

## 3. Tag 自定义标签

- 使用 `Simple Tag` 实现自定义标签非常简单
- 所有要实现的内容都是在 `doTag()` 方法中进行
- 绝大多数情况下，使用 `Simple Tag` 能满足自定义标签的需求

然而，

- `Simple Tag` 是从 JSP 2.0 之后才加入到标准中
- 在 JSP 2.0 之前实现的自定义标签同构 `Tag` 接口相关类的实现来完成
- 学习 `Tag` 自定义标签的目的在于：
- 了解和维护 JSP 2.0 之前实现出来的自定义标签（如 `JSTL`）
- 使用 JSP 2.0 以上的环境很少使用 `Tag` 自定义标签

```java
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
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>t</short-name>
  <uri>http://127.0.0.1:8080/JavaWeb_Tag_war/t</uri>
  <tag>
    <name>if</name>
    <tag-class>IfTagorg.example.java_web.tag.tag.IfTag</tag-class>
    <body-content>JSP</body-content>
    <attribute>
      <name>test</name>
      <required>true</required>
      <rtexprvalue>true</rtexprvalue>
      <type>boolean</type>
    </attribute>
  </tag>
</taglib>
```

```java
<%@ taglib prefix="t" uri="http://127.0.0.1:8080/JavaWeb_Tag_war/t" %>
```

```java
<t:if test="${param.password=='123456'}">
    <h1>你的秘密数据在此！</h1>
</t:if>
```

### 3.1. API 架构与生命周期

```java
public interface JspTag {
}
```

```java
public interface Tag extends JspTag {
    int SKIP_BODY = 0;
    int EVAL_BODY_INCLUDE = 1;
    int SKIP_PAGE = 5;
    int EVAL_PAGE = 6;
    void setPageContext(PageContext var1);
    void setParent(Tag var1);
    Tag getParent();
    int doStartTag() throws JspException;
    int doEndTag() throws JspException;
    void release();
}
```

```java
public interface IterationTag extends Tag {
    int EVAL_BODY_AGAIN = 2;
    int doAfterBody() throws JspException;
}
```

```java
public class TagSupport implements IterationTag, Serializable {
    private Tag parent;
    private Hashtable<String, Object> values;
    protected String id;
    protected PageContext pageContext;
    public static final Tag findAncestorWithClass(Tag from, Class klass) { ... }
    public TagSupport() {}
    public int doStartTag() throws JspException { ... }
    public int doEndTag() throws JspException { ... }
    public int doAfterBody() throws JspException { ... }
    public void release() { ... }
    public void setParent(Tag t) { ... }
    public Tag getParent() { ... }
    public void setId(String id) { ... }
    public String getId() { ... }
    public void setPageContext(PageContext pageContext) { ... }
    public void setValue(String k, Object o) { ... }
    public Object getValue(String k) { ... }
    public void removeValue(String k) { ... }
    public Enumeration<String> getValues() { ... }
}
```

当 JSP 遇到 `TagSupport` 自定义标签时，会进行以下动作：

1. 尝试从标签池（`Tag Pool`）找到可用的标签对象，如果找到就直接使用，如果没找到就创建先的标签对象
2. 调用标签处理器的 `setPageContext()` 方法设置 `PageContext` 实例
3. 如果时嵌套标签中的内层标签，调用标签处理器的 `setParent()` 方法，传入外层标签处理器的实例
4. 设置标签处理器的属性
5. 调用标签处理器的 `doStartTag()` 方法，并依据不同的返回值决定是否执行 `Body` 或调用 `doAfterBody()`、`doEndBody()` 方法
6. 将标签处理器实例置入标签池中以便再次使用

`Tag` 实例是可以重复使用的（`Simple Tag` 实例则是每次请求都创建新对象，用完就销毁回收）。

使用自定义 `Tag` 类，

- 要注意对象状态是否会被保留下来
- 在必要的时候，可以在 `doStartTag()` 方法中对对象的状态进行重置

`release()` 方法只会在标签实例真正被销毁回收前被调用。

![标签处理流程图1](https://cdn.jsdelivr.net/gh/happyflyer/picture-bed@main/2020/标签处理流程图1.7h2wkh5pc9k0.png)

### 3.2. 重复执行标签 Body

```java
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
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>t</short-name>
  <uri>http://127.0.0.1:8080/JavaWeb_Tag_war/t</uri>
  <tag>
    <name>forEach</name>
    <tag-class>ForEachTagorg.example.java_web.tag.tag.ForEachTag</tag-class>
    <body-content>JSP</body-content>
    <attribute>
      <name>var</name>
      <required>true</required>
      <type>java.lang.String</type>
    </attribute>
    <attribute>
      <name>items</name>
      <required>true</required>
      <rtexprvalue>true</rtexprvalue>
      <type>java.util.Collection</type>
    </attribute>
  </tag>
</taglib>
```

```java
<t:forEach items="${requestScope.names}" var="s">
    <h1>${s}</h1>
</t:forEach>
```

### 3.3. 处理 Body 运行结果

```java
public interface BodyTag extends IterationTag {
    /** @deprecated */
    int EVAL_BODY_TAG = 2;
    int EVAL_BODY_BUFFERED = 2;
    void setBodyContent(BodyContent var1);
    void doInitBody() throws JspException;
}
```

```java
public class BodyTagSupport extends TagSupport implements BodyTag {
    protected BodyContent bodyContent;
    public BodyTagSupport() {}
    public int doStartTag() throws JspException { ... }
    public int doEndTag() throws JspException { ... }
    public void setBodyContent(BodyContent b) { ... }
    public void doInitBody() throws JspException {}
    public int doAfterBody() throws JspException { ... }
    public void release() { ... }
    public BodyContent getBodyContent() { ... }
    public JspWriter getPreviousOut() { ... }
}
```

![标签处理流程图2](https://cdn.jsdelivr.net/gh/happyflyer/picture-bed@main/2020/标签处理流程图2.5w0y1g3lqus0.png)

### 3.4. 与父标签沟通

```java
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
```

```java
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
```

```java
public class OtherwiseTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        JspTag parent = getParent();
        if (!(parent instanceof ChooseTag)) {
            throw new JspException("OtherwiseTag 必须置于 ChooseTag 标签中");
        }
        ChooseTag choose = (ChooseTag) parent;
        if (choose.isMatched()) {
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>t</short-name>
  <uri>http://127.0.0.1:8080/JavaWeb_Tag_war/t</uri>
  <tag>
    <name>choose</name>
    <tag-class>ChooseTagorg.example.java_web.tag.tag.ChooseTag</tag-class>
    <body-content>JSP</body-content>
  </tag>
  <tag>
    <name>when</name>
    <tag-class>WhenTagorg.example.java_web.tag.tag.WhenTag</tag-class>
    <body-content>JSP</body-content>
    <attribute>
      <name>test</name>
      <required>true</required>
      <rtexprvalue>true</rtexprvalue>
      <type>boolean</type>
    </attribute>
  </tag>
  <tag>
    <name>otherwise</name>
    <tag-class>OtherwiseTagorg.example.java_web.tag.tag.OtherwiseTag</tag-class>
    <body-content>JSP</body-content>
  </tag>
</taglib>
```

```java
<t:choose>
    <t:when test="${param.user == 'zhangsan'}">
        <h1>${param.user}登录成功！</h1>
    </t:when>
    <t:otherwise>
        <h1>登录失败</h1>
    </t:otherwise>
</t:choose>
```
