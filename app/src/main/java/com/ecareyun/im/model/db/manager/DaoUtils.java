package com.ecareyun.im.model.db.manager;

import android.content.Context;
import android.util.Log;
public class DaoUtils {

    private static DaoUtils daoUtils;

    private SessionManager sessionManager;
    private MessageManager messageManager;
    public Context context;

    public DaoUtils(Context context) {
        this.context = context;
    }

    public static synchronized DaoUtils getInstance(Context context) {
        if (daoUtils == null) {
            daoUtils = new DaoUtils(context);
        }
        return daoUtils;
    }

    public SessionManager getSessionManager() {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
            Log.e("DaoUtils", "getSessionManager");
        }
        return sessionManager;
    }

    public MessageManager getMessageManager() {
        if (messageManager == null) {
            messageManager = new MessageManager(context);
            Log.e("DaoUtils", "getMessageManager");
        }
        return messageManager;
    }


    public void resetNull() {
        sessionManager = null;
        messageManager = null;
        DaoManager.getInstance(context).closeConnection();
    }
}
