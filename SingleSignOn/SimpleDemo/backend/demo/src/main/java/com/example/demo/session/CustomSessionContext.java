package com.example.demo.session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class CustomSessionContext {
    private static CustomSessionContext instance;
    private HashMap map;

    private CustomSessionContext() {
        map = new HashMap();
    }

    public static CustomSessionContext getInstance() {
        if (instance == null) {
            synchronized (CustomSessionContext.class) {
                if (instance == null) {
                    instance = new CustomSessionContext();
                }
            }
        }

        return instance;
    }

    public synchronized void addSession(HttpSession session) {
        if (session != null) {
            map.put(session.getId(), session);
        }
    }

    public synchronized void delSession(HttpSession session) {
        if (session != null) {
            map.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String session_id) {
        if (session_id == null) return null;
        return (HttpSession) map.get(session_id);
    }

}
