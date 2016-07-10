package com.mini_proj.annetao.wego.util.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.mini_proj.annetao.wego.R;

import org.apache.http.Header;

import java.util.List;

public class SearchAddrForMapActivity extends AppCompatActivity {
    private EditText etSuggestion;
    private ListView lvSuggesion;
    private SuggestionAdapter suggestionAdapter;
    private static final int MSG_SUGGESTION = 100000;
    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_SUGGESTION:
                    showAutoComplet((SuggestionResultObject)msg.obj);
                    break;

                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_addr_for_map);
        initView();
    }

    private void initView() {
        etSuggestion = (EditText) findViewById(R.id.text_addr);
        lvSuggesion = (ListView) findViewById(R.id.list_addr);

        final TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                suggestion(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        };
        etSuggestion.addTextChangedListener(textWatcher);
        lvSuggesion.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                etSuggestion.removeTextChangedListener(textWatcher);
                SuggestionResultObject.SuggestionData sd=
                        (SuggestionResultObject.SuggestionData)suggestionAdapter.getItem(position);

                CharSequence cs =
                        ((TextView)view.findViewById(R.id.addr_search_suggestion_label)).getText();
                etSuggestion.setText(cs);
                etSuggestion.setSelection(etSuggestion.getText().length());
                lvSuggesion.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.putExtra("wego_location_str",new WeGoLocation(sd).toString());

//                intent.putExtra("result", sd);
                //设置返回数据
                SearchAddrForMapActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                SearchAddrForMapActivity.this.finish();
            }
        });
    }


    protected void suggestion(String keyword) {
        if (keyword.trim().length() == 0) {
            lvSuggesion.setVisibility(View.GONE);
            return;
        }
        TencentSearch tencentSearch = new TencentSearch(this);
        SuggestionParam suggestionParam = new SuggestionParam().keyword(keyword);
        //suggestion也提供了filter()方法和region方法
        //具体说明见文档，或者官网的webservice对应接口
        tencentSearch.suggestion(suggestionParam, new HttpResponseListener() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, BaseObject arg2) {
                // TODO Auto-generated method stub
                if (arg2 == null ||
                        etSuggestion.getText().toString().trim().length() == 0) {
                    lvSuggesion.setVisibility(View.GONE);
                    return;
                }

                Message msg = new Message();
                msg.what = MSG_SUGGESTION;
                msg.obj = arg2;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                // TODO Auto-generated method stub
                printResult(arg2);
            }
        });
    }

    protected void printResult(final String result) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(SearchAddrForMapActivity.this,result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void showAutoComplet(SuggestionResultObject obj) {
        if (obj.data.size() == 0) {
            lvSuggesion.setVisibility(View.GONE);
            return;
        }
        if (suggestionAdapter == null) {
            suggestionAdapter = new SuggestionAdapter(obj.data);
            lvSuggesion.setAdapter(suggestionAdapter);
        } else {
            suggestionAdapter.setDatas(obj.data);
            suggestionAdapter.notifyDataSetChanged();
        }
        lvSuggesion.setVisibility(View.VISIBLE);
    }

    class SuggestionAdapter extends BaseAdapter {

        List<SuggestionResultObject.SuggestionData> mSuggestionDatas;

        public SuggestionAdapter(List<SuggestionResultObject.SuggestionData> suggestionDatas) {
            // TODO Auto-generated constructor stub
            setDatas(suggestionDatas);
        }

        public void setDatas(List<SuggestionResultObject.SuggestionData> suggestionDatas) {
            mSuggestionDatas = suggestionDatas;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mSuggestionDatas.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mSuggestionDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(SearchAddrForMapActivity.this,
                        R.layout.item_addr_search_suggestion, null);
                viewHolder = new ViewHolder();
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.addr_search_suggestion_label);
                viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.addr_search_suggestion_desc);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvTitle.setText(mSuggestionDatas.get(position).title);
            viewHolder.tvAddress.setText(mSuggestionDatas.get(position).address);
            return convertView;
        }

        private class ViewHolder{
            TextView tvTitle;
            TextView tvAddress;
        }
    }
}
