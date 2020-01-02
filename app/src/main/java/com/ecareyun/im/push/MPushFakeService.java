/*
 * (C) Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     ohun@live.cn (夜色)
 */


package com.ecareyun.im.push;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

/**
 * 双Service提高进程优先级,降低被系统杀死机率
 * <p>
 * Created by yxx on 2016/2/15.
 *
 * @author ohun@live.cn
 */
public final class MPushFakeService extends Service {
    public static final int NOTIFICATION_ID = 1001;

    public static void startForeground(Service service) {
        try {
            service.startService(new Intent(service, MPushFakeService.class));

            service.startForeground(NOTIFICATION_ID, getNotification(service));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Notification getNotification(Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        //设置Notification的ChannelID,否则不能正常显示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("channelId");
        }
        Notification notification = builder.build();
        return notification;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MPushEngine", "MPushFakeService onStartCommand");
        startForeground(NOTIFICATION_ID, getNotification(this));
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        Log.e("MPushEngine", "MPushFakeService onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
