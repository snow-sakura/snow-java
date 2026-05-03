package com.bjpowernode.p2padmin.user.interceptor;

import com.bjpowernode.p2padmin.user.model.UserInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

/**
 * ClassName:PermissionCheckInterceptor
 * Package:com.bjpowernode.p2padmin.user.interceptor
 * Description:
 *
 * @date:2018/10/29 9:17
 * @author:bjpowernode.com
 */
public class PermissionCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserInfo userInfo= (UserInfo) request.getSession().getAttribute("userSession");
        if(userInfo==null){
            System.out.println("用户没有登录返回登录页面！");
            return false;
        }


        //获取用户请求的路径
        String uri=request.getRequestURI();
        Enumeration<String> paramNames= request.getParameterNames();
        String urlParams="";
        while(paramNames.hasMoreElements()){//拼接求请求中参数列表
            String paramName=paramNames.nextElement();
            if(urlParams.length()==0){
                urlParams="?"+paramName+"="+request.getParameter(paramName);
            }else{
                urlParams+="&"+paramName+"="+request.getParameter(paramName);
            }

        }
        Map urlMap=userInfo.getUrlMap();
        if(urlMap.get(uri+urlParams)==null){
            System.out.println("用户没有操作权限!");
            response.sendRedirect("/onPermission");
            return false;
        }

        return true;
    }
}
