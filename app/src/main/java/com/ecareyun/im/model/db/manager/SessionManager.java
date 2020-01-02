package com.ecareyun.im.model.db.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ecareyun.im.model.db.dao.DaoSession;
import com.ecareyun.im.model.db.dao.SessionEntityDao;
import com.ecareyun.im.model.db.entity.SessionEntity;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SessionManager {

    private DaoManager manager;
    private DaoSession session;
    private ReentrantLock lock = new ReentrantLock();

    public SessionManager(Context context) {
        manager = DaoManager.getInstance(context);
        session = manager.getDaoSession();
    }

    public void insertSessionEntity(final SessionEntity entity) {
        lock.lock();
        try {
            session.runInTx(new Runnable() {
                @Override
                public void run() {
                    boolean flag = session.insertOrReplace(entity) != -1 ? true : false;
                    Log.i("SessionManager", "session insertOrReplace : " + flag);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public SessionEntity querySessionById(int receiveId) {
        lock.lock();
        SessionEntity entity = null;
        try {
            if (session.queryBuilder(SessionEntity.class).where(
                    SessionEntityDao.Properties.ReceiveId.eq(receiveId)).count() > 1) {
                entity = session.queryBuilder(SessionEntity.class).where(
                        SessionEntityDao.Properties.ReceiveId.eq(receiveId)).build().list().get(0);
            } else {
                entity = session.queryBuilder(SessionEntity.class).where(
                        SessionEntityDao.Properties.ReceiveId.eq(receiveId)).build().unique();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return entity;
    }

    public void updateShowTopByReceiveId(final int receiveId, final byte showTop) {
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                try {
                    SessionEntity entity = querySessionById(receiveId);
                    entity.setShowTop(showTop);
                    insertSessionEntity(entity);
                } catch (Exception error) {
                    Log.e("ChatSessionManager", error.toString());
                }
            }
        });
    }

    public void updateRelationByReceiveId(final int receiveId, final int relationType) {
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                try {
                    SessionEntity entity = querySessionById(receiveId);
                    if (entity != null) {
                        entity.setRelationType(relationType);
                        insertSessionEntity(entity);
                    }
                } catch (Exception error) {
                    Log.e("ChatSessionManager", error.toString());
                }
            }
        });
    }

    public void updateNickNameById(final int id, final String name) {
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                try {
                    SessionEntity entity = querySessionById(id);
                    if (entity != null) {
                        entity.setReceiveName(name);
                        insertSessionEntity(entity);
                    }
                } catch (Exception error) {
                    Log.e("ChatSessionManager", error.toString());
                }
            }
        });
    }


    public List<SessionEntity> querySessionList(int relationType) {
        List<SessionEntity> sessionEntityList = session.queryBuilder(SessionEntity.class)
                .where(SessionEntityDao.Properties.RelationType.eq(relationType))
                .orderDesc(SessionEntityDao.Properties.ShowTop)
                .orderDesc(SessionEntityDao.Properties.SendData).build().list();
        return sessionEntityList;
    }

    public List<SessionEntity> querySessionEntityByName(int type, String key) {
        try {
            List<SessionEntity> list = null;
            QueryBuilder<SessionEntity> qb = session.queryBuilder(SessionEntity.class);
            qb.where(SessionEntityDao.Properties.RelationType.eq(type),
                    SessionEntityDao.Properties.ReceiveName.like("%" + key + "%"));
            list = qb.build().list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean deleteSession(SessionEntity entity) {
        boolean flag = false;
        try {
            session.delete(entity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void deleteSessionByUserId(int otherId) {
        SessionEntity entity = session.queryBuilder(SessionEntity.class).where(SessionEntityDao.Properties.ReceiveId.eq(otherId)).build().unique();
        deleteSession(entity);
    }

    public void cleanUpdateSessionEntity(final int otherId) {

        session.runInTx(new Runnable() {
            @Override
            public void run() {
                try {
                    SessionEntity entity = querySessionById(otherId);
                    if (entity != null) {
                        entity.setMessageId(0);
                        entity.setMessageType(0);
                        entity.setSendData(0L);
                        entity.setContent(null);
                        entity.setUnReadNum(0);
                        insertSessionEntity(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int getUnReadTotal() {
        int total = 0;
        Cursor cursor = null;
        try {
            String sql = "select sum(UN_READ_NUM) as 'TotalUnReadNum' from " + SessionEntityDao.TABLENAME;
            cursor = session.getDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                total = cursor.getInt(0);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return total;

    }

    public HashMap getUnReadTotalGroupBy() {
        HashMap<String, Integer> map = new HashMap<>();
        String sql = "select RELATION_TYPE, sum(UN_READ_NUM) as 'TotalUnReadNum' from " +
                SessionEntityDao.TABLENAME + " group by RELATION_TYPE";
        Cursor cursor = null;
        try {
            cursor = session.getDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                map.put(String.valueOf(cursor.getInt(0)), cursor.getInt(1));
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return map;
    }
}