package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mini_proj.annetao.wego.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

import okhttp3.Call;

public class SettingsActivity extends BaseActivity
        implements
        TitleLayout.OnTitleActionListener,
        View.OnClickListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{
    private TextView name;
    private TextView birthday;
    private TextView tagsText;
    private RadioGroup sex;
    private String sexString = "2";
    private int resultScore = 0;

    private int year;
    private int month;
    private int day;

    private HashSet<Integer> selectedId = new HashSet<>();

    private MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name = findView(R.id.name);
        name.setSelected(true);
        String defaultName = User.getInstance().getName();
        if(defaultName!=null&&!defaultName.equals(""))
            name.setText(defaultName);
        birthday = findView(R.id.birthday);
        tagsText = findView(R.id.tags);
        tagsText.setSelected(true);
        sex = findView(R.id.sex);

        findView(R.id.name_layout).setOnClickListener(this);
        findView(R.id.tags_layout).setOnClickListener(this);

        name.setText(User.getInstance().getName());
        switch (User.getInstance().getGender()) {
            case 0:
                sex.check(R.id.male);
                break;
            case 1:
                sex.check(R.id.female);
                break;
            case 2:
                sex.check(R.id.unknown);
                break;
        }
        year = User.getInstance().getYear();
        month = User.getInstance().getMonth();
        day = User.getInstance().getDay();
        birthday.setText(User.getInstance().getYear()+"-"+User.getInstance().getMonth()+"-"+User.getInstance().getDay());
        tagsText.setText(User.getInstance().getTagShowString());


        String[] ids = User.getInstance().getTagString().split(",");
        int[] idsint = new int[ids.length];
        Log.e("wego_getTagShowString",User.getInstance().getTagString());
        for (int i = 0; i < ids.length; i++) {
            Log.e("wego_getTagShowString"+i,ids[i]);
            try {
                selectedId.add(Integer.valueOf(ids[i]));
            } catch (NumberFormatException n) {

            }
        }








        findViewById(R.id.male).setEnabled(false);
        findViewById(R.id.female).setEnabled(false);
        findViewById(R.id.unknown).setEnabled(false);


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

        }
    }

    private void updateUserInfo() {
        //TODO 注册成功信息存入本地
        User.getInstance().setName(name.getText()+"");
        User.getInstance().setGender(Integer.parseInt(sexString));
        String tagStr = selectedId.toString().replaceAll("\\[|\\]| ","");
        Log.e("wego_tagStr",tagStr);
        User.getInstance().setTagString(tagStr);
        User.getInstance().setYear(year);
        User.getInstance().setMonth(month);
        User.getInstance().setDay(day);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
        birthday.setText(this.year + "-" + String.format(Locale.getDefault(), "%02d", month) + "-" + String.format(Locale.getDefault(), "%02d", day));
    }

    @Override
    public void clickTitleBack() {
        onBackPressed();

    }

    @Override
    public void doubleClickTitle() {

    }

    @Override
    public void clickTitleEdit() {
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
            UserInf.getUserInf().updateUserTag(User.getInstance().getOpenId(),new JSONArray(selectedId), new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.d("Wego_update_user_tag", e.toString());
                    if (dialog != null) dialog.dismiss();
                    dialog = new MaterialDialog.Builder(mContext)
                            .title("提交失败")
                            .content("提交信息失败。")
                            .positiveText("确定")
                            .show();
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.d("Wego_update_user_tag", response.toString());
                    if (dialog != null) dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if ("200".equals(jsonObject.getString("result"))) {

                            resultScore++;
                            if(resultScore==2)success();

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

            UserInf.getUserInf().doChangeName(User.getInstance().getOpenId(),name.getText().toString(), new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.d("Wego_chg_name", e.toString());
                    if (dialog != null) dialog.dismiss();
                    dialog = new MaterialDialog.Builder(mContext)
                            .title("提交失败")
                            .content("提交信息失败。")
                            .positiveText("确定")
                            .show();
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.d("Wego_chg_name", response.toString());
                    if (dialog != null) dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if ("200".equals(jsonObject.getString("result"))) {
                            resultScore++;
                            if(resultScore==2)success();
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




    }

    public void success(){
        updateUserInfo();
        Utils.toastImmediately("提交信息成功" + name.getText().toString());
        setResult(RESULT_OK);
        finish();
    }

}
