package org.example.java_web.tag.tag_file;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Tag File
 *
 * @author lifei
 */
@WebServlet("/tag_file/register.do")
public class RegisterServlet extends HttpServlet {
    /**
     * 已经注册的用户
     */
    private final Map<String, String> users = new HashMap<>();

    public RegisterServlet() {
        users.put("zhangsan", "123456");
        users.put("lisi", "000000");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String redirectView = "register_with_html.jsp";
        String registerView = "/tag_file/register_with_html.jsp";
        String successView = "/tag_file/success.jsp";
        if ("".equals(username) || username == null || "".equals(password) || password == null) {
            response.sendRedirect(redirectView);
        } else if (users.containsKey(username)) {
            List<String> errors = new ArrayList<>();
            errors.add("用户名已存在");
            request.setAttribute("errors", errors);
            getServletContext().getRequestDispatcher(registerView).forward(request, response);
        } else {
            request.setAttribute("username", username);
            getServletContext().getRequestDispatcher(successView).forward(request, response);
        }
    }
}
