package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private ArrayList<UserNotice> noticeArrayList = new ArrayList<>();
    private SuperRecyclerView listView;
    private UserNoticeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_message, container, false);
        noticeArrayList=new ArrayList<>();
        /*
        dialog = new MaterialDialog.Builder(getActivity())
                .title("sdfsdf")
                .content("dsfsdf")
                .progress(true, 0)
                .cancelable(false)
                .show();
        */

        listView = (SuperRecyclerView) messageLayout.findViewById(R.id.list_view);
        listView.setRefreshListener(this);
        listView.addItemDecoration(new PhoneOrderDecoration(Utils.dp2px(10)));
        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mManager);
        adapter = new UserNoticeAdapter(FragmentMessage.this, noticeArrayList);
        listView.setAdapter(adapter);

        loadMessage();

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
                Utils.toastImmediately("加载消息成功");
                listView.getSwipeToRefresh().setRefreshing(false);
                Log.d("Wego", response + " " + id);
                noticeArrayList.clear();
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
                        noticeArrayList.add(notice);
                    }
                    adapter = new UserNoticeAdapter(FragmentMessage.this, noticeArrayList);
                    listView.setAdapter(adapter);
                    Log.d("Wego",  "noticeArrayList size: " + noticeArrayList.size());
                }
                catch(JSONException e)
                {
                    Log.e("WeGo",e.toString());
                }

            }
        });
    }

    @Override
    public void onRefresh() {
        loadMessage();
    }

    @Override
    public void onSelect(int p) {

    }
}
