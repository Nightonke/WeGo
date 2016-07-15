package com.mini_proj.annetao.wego;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mini_proj.annetao.wego.util.Utils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentMessage extends Fragment implements SwipeRefreshLayout.OnRefreshListener, UserNoticeAdapter.OnNoticeSelectListener {

    private SuperRecyclerView listView;
    private UserNoticeAdapter adapter;
    private boolean autoLoad = true;
    private boolean firstLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_message, container, false);

        listView = (SuperRecyclerView) messageLayout.findViewById(R.id.list_view);
        listView.setRefreshListener(this);
        listView.addItemDecoration(new PhoneOrderDecoration(Utils.dp2px(10)));
        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mManager);
        adapter = new UserNoticeAdapter(FragmentMessage.this, ExercisePool.notices);
        listView.setAdapter(adapter);
        listView.getSwipeToRefresh().setColorSchemeResources(R.color.colorAccent);

        return messageLayout;
    }

    public void loadMessage() {
        UserInf.getUserInf().queryMyNotice(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("Wego", e.toString() + " " + id);
                Utils.toastImmediately("加载消息失败");
                listView.getSwipeToRefresh().setRefreshing(false);
            }

            @Override
            public void onResponse(String response, int id) {
                if (firstLoad) Utils.toastImmediately("加载消息成功");
                firstLoad = false;
                listView.getSwipeToRefresh().setRefreshing(false);
                Log.d("Wego", response + " " + id);
                ExercisePool.notices.clear();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array=object.getJSONArray("data");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject jsonMsg=array.getJSONObject(i);
                        int id_=jsonMsg.getInt("id");
                        String user_id=jsonMsg.getString("user_id");
                        int exercise_id=jsonMsg.getInt("exercise_id");
                        String notice_content=jsonMsg.getString("notice_content");
                        String time=jsonMsg.getString("time");
                        int status=jsonMsg.getInt("status");
                        UserNotice notice=new UserNotice(id_,user_id,exercise_id,notice_content,time,status);
                        ExercisePool.notices.add(notice);
                    }
                    notificationHandler.sendEmptyMessage(0);
                    adapter = new UserNoticeAdapter(FragmentMessage.this, ExercisePool.notices);
                    listView.setAdapter(adapter);
                    Log.d("Wego",  "noticeArrayList size: " +  ExercisePool.notices.size());
                }
                catch(JSONException e) {
                    Log.e("WeGo",e.toString());
                }
            }
        });
    }

    private Handler notificationHandler = new NotificationHandler();

    @Override
    public void onRefresh() {
        loadMessage();
    }

    @Override
    public void onSelect(int p) {

    }

    public void autoLoadMessage() {
        if (autoLoad) {
            autoLoad = false;
            loadMessage();
        }
    }

    private class NotificationHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            if (ExercisePool.notices.size() == 0) {
                ((NotificationManager) MyApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(100);
                return;
            }

            NotificationManager mNotificationManager
                    = (NotificationManager)MyApplication.getAppContext().getSystemService(MyApplication.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyApplication.getAppContext());
            mBuilder.build().defaults = Notification.DEFAULT_ALL;

            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setOngoing(true);

            mBuilder.setPriority(Notification.PRIORITY_HIGH);
            mBuilder.setContentTitle("Wego");
            mBuilder.setContentText("您有 " + ExercisePool.notices.size() + " 条消息，请打开Wego查看。");
            mBuilder.setWhen(System.currentTimeMillis());
            mBuilder.setTicker("Wego");
            mBuilder.setSmallIcon(R.drawable.icon_launcher);
            android.app.Notification notification = mBuilder.build();
            mNotificationManager.notify(100, notification);
        }
    }
}
