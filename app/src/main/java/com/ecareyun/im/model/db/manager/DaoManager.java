
package com.ecareyun.im.model.db.manager;

import android.content.Context;
import com.ecareyun.im.AppConst;
import com.ecareyun.im.model.db.dao.DaoMaster;
import com.ecareyun.im.model.db.dao.DaoSession;
import org.greenrobot.greendao.query.QueryBuilder;

public class DaoManager {
    public static final String TAG = DaoManager.class.getSimpleName();
    public volatile static DaoManager manager;//多线程访问
    private static DaoMaster.DevOpenHelper helper;
    private static DaoMaster master;
    private static DaoSession session;

    public DaoManager(Context context) {
        session = getMaster(context).newSession();
    }

    /**
     * 使用单例模式获取操作数据库的对象
     *
     * @return
     */
    public static DaoManager getInstance(Context context) {
        if (manager == null) {
            synchronized (DaoManager.class) {
                if (manager == null) {
                    manager = new DaoManager(context);
                }
            }
        }
        return manager;
    }

    /**
     * 判断是否存在数据库，如果没有则创建数据库
     *
     * @param context
     * @return
     */
    public DaoMaster getMaster(Context context) {
        if (master == null) {
            helper = new DaoMaster.DevOpenHelper(context, AppConst.DB_NAME, null);
                master = new DaoMaster(helper.getWritableDatabase());
        }
        return master;
    }

    /**
     * 获取session表
     *
     * @return
     */
    public DaoSession getDaoSession() {
        return session;
    }

    /**
     * 打开输出日志的操作，默认是关闭的
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

    public void closeSession() {
        if (session != null) {
            session.clear();
            session = null;
        }
    }

    /**
     * 关闭所有操作，使用完毕必须要关闭
     */
    public void closeConnection() {
        closeHelper();
        closeSession();
        master = null;
        manager = null;
    }
}
