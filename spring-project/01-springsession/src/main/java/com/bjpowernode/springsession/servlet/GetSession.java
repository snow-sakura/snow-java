package com.bjpowernode.springsession.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:GetSession
 * Package:com.bjpowernode.springsession.servlet
 * Description:
 *
 * @date:2018/9/29 11:24
 * @author:bjpowernode.com
 */
@WebServlet("/get")
public class GetSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str= (String) req.getSession().getAttribute("sessionKey");

        resp.getWriter().println(str);
    }
}
