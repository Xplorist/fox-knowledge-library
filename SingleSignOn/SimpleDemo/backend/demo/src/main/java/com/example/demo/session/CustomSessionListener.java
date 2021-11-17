package com.example.demo.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class CustomSessionListener implements HttpSessionListener {
    //public static Map map = new HashMap();
    private CustomSessionContext csc= CustomSessionContext.getInstance();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        csc.addSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        csc.delSession(session);
    }
}
