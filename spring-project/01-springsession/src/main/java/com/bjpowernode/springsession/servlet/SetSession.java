package com.bjpowernode.springsession.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:SetSession
 * Package:com.bjpowernode.springsession.servlet
 * Description:
 *
 * @date:2018/9/29 11:22
 * @author:bjpowernode.com
 */
@WebServlet("/set")
public class SetSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("sessionKey","Session Data!");
        resp.getWriter().print("Session Set OK!");
    }
}
