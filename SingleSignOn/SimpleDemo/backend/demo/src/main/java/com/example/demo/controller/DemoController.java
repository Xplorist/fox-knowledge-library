package com.example.demo.controller;

import com.example.demo.model.ResultDTO;
import com.example.demo.session.CustomSessionContext;
import com.example.demo.util.TokenUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/demo")
public class DemoController {
    // 查詢結果
    @CrossOrigin
    @RequestMapping("/queryResult")
    public ResultDTO queryResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getSession().getId();
        Cookie ssoCookie = new Cookie("SsoSessionId", id);
        ssoCookie.setDomain("10.244.231.103");
        response.addCookie(ssoCookie);
        log.info(id);
        //response.sendRedirect("http://metalworking.efoxconn.com/bid/login");

        return new ResultDTO("1", "查詢結果", id);
    }

    // jsonp測試方法
    @RequestMapping("/jsonpTest")
    public JSONPObject jsonpTest(String callback, HttpServletRequest request, HttpServletResponse response) {
        //String id = request.getSession().getId();
        CustomSessionContext csc = CustomSessionContext.getInstance();
        HttpSession session = null;
        String sessionId = null;
        String token = null;
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        boolean flag = false;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                String cName = c.getName();
                if ("JSESSIONID".equals(cName)) {
                    cookie = c;
                    sessionId = cookie.getValue();
                    session = csc.getSession(sessionId);
                    if (session != null) {
                        //log.info("csc.getSession(sessionId)獲取的sessionId為：" + session.getId());
                        System.out.println("csc.getSession(sessionId)獲取的sessionId為：" + session.getId());
                        session = request.getSession();
                        token = (String) session.getAttribute("token");
                    } else {
                        System.out.println("此sessionId=[" + sessionId + "]的session不存在");
                        // 清除cookie
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);

                        return new JSONPObject(callback, new ResultDTO("-1", "cookie失效，刪除cookie=" + sessionId, null));
                    }

                    flag = true;
                    break;
                }
            }
        }

        ResultDTO result = null;
        if (!flag) {
            session = request.getSession();
            sessionId = session.getId();
            String username = "xxx";
            String password = "yyyy";
            token = TokenUtil.getToken(sessionId, username, password);
            cookie = new Cookie("token", token);
            session.setAttribute("token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            result = new ResultDTO("0", "新增Cookie=" + sessionId, token);
        } else {
            result = new ResultDTO("1", "已有Cookie=" + sessionId, token);
        }
        JSONPObject jsonp = new JSONPObject(callback, result);

        return jsonp;
    }
}
