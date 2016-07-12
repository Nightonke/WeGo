package com.mini_proj.annetao.wego;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mini_proj.annetao.wego.util.Utils;
import com.tencent.connect.common.Constants;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;

public class UserInformationActivity extends BaseActivity
        implements
        View.OnClickListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private TextView name;
    private TextView birthday;
    private TextView tagsText;
    private RadioGroup sex;
    private String sexString = "2";

    private int year;
    private int month;
    private int day;

    private HashSet<Integer> selectedId = new HashSet<>();

    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        name = findView(R.id.name);
        name.setSelected(true);
        birthday = findView(R.id.birthday);
        tagsText = findView(R.id.tags);
        tagsText.setSelected(true);
        sex = findView(R.id.sex);

        findView(R.id.name_layout).setOnClickListener(this);
        findView(R.id.birthday_layout).setOnClickListener(this);
        findView(R.id.tags_layout).setOnClickListener(this);
        findView(R.id.next).setOnClickListener(this);

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        sexString = "0";
                        break;
                    case R.id.female:
                        sexString = "1";
                        break;
                    case R.id.unknown:
                        sexString = "2";
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name_layout:
                new MaterialDialog.Builder(mContext)
                        .title("昵称")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .positiveText("确认")
                        .negativeText("取消")
                        .inputRange(1, 20, Color.RED)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                name.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("活动标题", name.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.birthday_layout:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setTitle("生日");
                datePickerDialog.show(getFragmentManager(), "");
                break;
            case R.id.tags_layout:
                Integer[] ids = new Integer[selectedId.size()];
                int index = 0;
                for (Integer id : selectedId) {
                    ids[index++] = id;
                }
                new MaterialDialog.Builder(this)
                        .title("兴趣标签")
                        .items(Tag.getAllTagName())
                        .itemsCallbackMultiChoice(ids, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                /**
                                 * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                                 * returning false here won't allow the newly selected check box to actually be selected.
                                 * See the limited multi choice dialog example in the sample project for details.
                                 **/
                                return true;
                            }
                        })
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (dialog.getSelectedIndices() != null) {
                                    for (Integer id : dialog.getSelectedIndices()) {
                                        selectedId.add(id);
                                    }
                                    String tags = "";
                                    boolean isFirst = true;
                                    for (Integer id : selectedId) {
                                        if (!isFirst) tags += ", ";
                                        isFirst = false;
                                        tags += Tag.getAllTagName().get(id);
                                    }
                                    tagsText.setText(tags);
                                }
                            }
                        })
                        .positiveText("确定")
                        .show();
                break;
            case R.id.next:
                // 调用网络接口将数据写入服务器，如果成功，再写入本地
                if ("".equals(name.getText().toString())) {
                    Utils.toastImmediately("昵称不能为空");
                } else if ("生日".equals(birthday.getText().toString())) {
                    Utils.toastImmediately("生日不能为空");
                } else if (selectedId.size() == 0) {
                    Utils.toastImmediately("兴趣标签不能为空");
                } else {
                    dialog = new MaterialDialog.Builder(mContext)
                            .title("提交信息中")
                            .content("请耐心等候")
                            .cancelable(false)
                            .progress(true, 0)
                            .show();
                    UserInf.getUserInf().doRegister(User.getInstance().getOpenId(), name.getText().toString(), sexString, birthday.getText().toString(), new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.d("Wego", e.toString());
                            if (dialog != null) dialog.dismiss();
                            dialog = new MaterialDialog.Builder(mContext)
                                    .title("提交失败")
                                    .content("提交信息失败。")
                                    .positiveText("确定")
                                    .show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.d("Wego", response.toString());
                            if (dialog != null) dialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if ("200".equals(jsonObject.getString("result"))) {
                                    Utils.toastImmediately("提交信息成功，欢迎你，" + name.getText().toString());
                                    setResult(Constants.REQUEST_API + 1);
                                    finish();
                                } else {
                                    dialog = new MaterialDialog.Builder(mContext)
                                            .title("提交失败")
                                            .content("提交信息失败。")
                                            .positiveText("确定")
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
        birthday.setText(this.year + "-" + String.format(Locale.getDefault(), "%02d", month) + "-" + String.format(Locale.getDefault(), "%02d", day));
    }
}
