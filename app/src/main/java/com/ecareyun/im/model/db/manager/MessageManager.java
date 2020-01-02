package com.ecareyun.im.model.db.manager;

import android.content.Context;
import android.util.Log;

import com.ecareyun.im.model.db.dao.DaoSession;
import com.ecareyun.im.model.db.dao.MessageEntityDao;
import com.ecareyun.im.model.db.entity.MessageEntity;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 针对用户备注进行操作的管理类 ， 操作对象：MessageEntity
 */
public class MessageManager {

    private DaoManager manager;
    private DaoSession session;
    private ReentrantLock lock = new ReentrantLock();

    public MessageManager(Context context) {
        manager = DaoManager.getInstance(context);
        session = manager.getDaoSession();
    }

    /**
     * 插入单条数据
     *
     * @param entity
     * @return
     */
    public void insertMessageEntity(final MessageEntity entity) {
        lock.lock();
        try {
            session.runInTx(new Runnable() {
                @Override
                public void run() {
                    boolean flag = session.insertOrReplace(entity) != -1 ? true : false;
                    Log.e("MessageManager", "message insertOrReplace : " + flag);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 插入多条数据
     *
     * @param messageEntityList
     * @return
     */
    public boolean insertListMessage(final List<MessageEntity> messageEntityList) {
        boolean flag = false;
        try {
            session.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (MessageEntity entity : messageEntityList) {
                        session.insertOrReplace(entity);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除与某人的聊天记录
     *
     * @param otherId
     * @return
     */
    public boolean deleteMessageByUserId(String otherId) {
        boolean flag = false;
        try {
            session.queryBuilder(MessageEntity.class).whereOr(MessageEntityDao.Properties.SendId.eq(otherId),
                    MessageEntityDao.Properties.ReceiveId.eq(otherId)).buildDelete().executeDeleteWithoutDetachingEntities();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除与某人的聊天记录
     *
     * @param msgId
     * @return
     */
    public boolean deleteMessageByMsgId(long msgId) {
        boolean flag = false;
        try {
            session.queryBuilder(MessageEntity.class).where(MessageEntityDao.Properties.MsgId.eq(msgId)).buildDelete().executeDeleteWithoutDetachingEntities();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 清空用户备注数据
     *
     * @return
     */
    public boolean deleteAllMessage() {
        boolean flag = false;
        try {
            session.deleteAll(MessageEntity.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据用户ID查找单条数据
     *
     * @param msgId
     * @return
     */
    public MessageEntity queryMessage(long msgId) {
        MessageEntity entity = session.queryBuilder(MessageEntity.class)
                .where(MessageEntityDao.Properties.MsgId.eq(msgId)).unique();
        return entity;
    }

    public void updateMessageEntity(long oldMsgId, long newMsgId, int sType) {
        try {
            MessageEntity entity = queryMessage(oldMsgId);
            entity.setMsgId(newMsgId);
            entity.setSendStatus(sType);
            session.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateVoiceMessageReadStatus(long msgId, byte isRead) {
        try {
            MessageEntity entity = queryMessage(msgId);
            entity.setIsRead(isRead);
            session.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param uniqueId 与当前聊天用户的ID
     * @return
     */
    public List<MessageEntity> queryMessageList(String uniqueId, int limit, long time) {
        try {
            List<MessageEntity> list = null;
            QueryBuilder<MessageEntity> qb = session.queryBuilder(MessageEntity.class);
            qb.orderDesc(MessageEntityDao.Properties.SendDate);
            if (time == 0) {
                qb.where(MessageEntityDao.Properties.UniqueId.eq(uniqueId));
            } else {
                qb.where(MessageEntityDao.Properties.UniqueId.eq(uniqueId), MessageEntityDao.Properties.SendDate.lt(time));
            }
            qb.limit(limit);
            list = qb.build().list();
            Collections.reverse(list);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param uniqueId 与当前聊天用户的ID大于某个时间段的所有数据
     * @return
     */
    public List<MessageEntity> queryMessageList(String uniqueId, long time) {
        try {
            List<MessageEntity> list = null;
            QueryBuilder<MessageEntity> qb = session.queryBuilder(MessageEntity.class);
            qb.orderDesc(MessageEntityDao.Properties.SendDate);
            qb.where(MessageEntityDao.Properties.UniqueId.eq(uniqueId), MessageEntityDao.Properties.SendDate.ge(time));
            list = qb.build().list();
            Collections.reverse(list);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param fId 当前用户的ID
     * @param tId 会话对方的ID
     * @return
     */
    public List<MessageEntity> queryMessageList(String fId, String tId) {
        try {
            List<MessageEntity> list = null;
            QueryBuilder<MessageEntity> qb = session.queryBuilder(MessageEntity.class);
            qb.whereOr(qb.and(MessageEntityDao.Properties.SendId.eq(fId), MessageEntityDao.Properties.ReceiveId.eq(tId)),
                    qb.and(MessageEntityDao.Properties.SendId.eq(tId), MessageEntityDao.Properties.ReceiveId.eq(fId)));
            list = qb.build().list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<MessageEntity> queryMessageListByContent(String fId, String tId, String key) {
        try {
            List<MessageEntity> list = null;
            QueryBuilder<MessageEntity> qb = session.queryBuilder(MessageEntity.class);
            qb.where(MessageEntityDao.Properties.Content.like("%" + key + "%"),
                    qb.or(qb.and(MessageEntityDao.Properties.SendId.eq(fId), MessageEntityDao.Properties.ReceiveId.eq(tId)),
                            qb.and(MessageEntityDao.Properties.SendId.eq(tId), MessageEntityDao.Properties.ReceiveId.eq(fId))));
            list = qb.build().list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MessageEntity> queryMessageListByContent(String otherId, String key) {
        try {
            List<MessageEntity> list = null;
            QueryBuilder<MessageEntity> qb = session.queryBuilder(MessageEntity.class);
            qb.where(MessageEntityDao.Properties.UniqueId.eq(otherId), MessageEntityDao.Properties.Content.like("%" + key + "%"));
            list = qb.build().list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
