package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.util.Util;
import com.mini_proj.annetao.wego.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FilterActivity extends BaseActivity
        implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener,
        TitleLayout.OnTitleActionListener,
        View.OnClickListener {

    private TitleLayout titleLayout;
    private TextView startTime;
    private TextView endTime;
    private TextView minPeople;
    private TextView maxPeople;
    private TextView credit;
    private TextView distance;

    private boolean isEndTime = false;
    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();

    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        titleLayout = findView(R.id.title_layout_top);
        titleLayout.setOnTitleActionListener(this);
        startTime = findView(R.id.start);
        endTime = findView(R.id.end);
        minPeople = findView(R.id.min_people);
        maxPeople = findView(R.id.max_people);
        credit = findView(R.id.credit);
        distance = findView(R.id.distance);

        findView(R.id.start_layout).setOnClickListener(this);
        findView(R.id.end_layout).setOnClickListener(this);
        findView(R.id.min_people_layout).setOnClickListener(this);
        findView(R.id.max_people_layout).setOnClickListener(this);
        findView(R.id.credit_layout).setOnClickListener(this);
        findView(R.id.distance_layout).setOnClickListener(this);

        setText();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (isEndTime) {
            endCalendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            endCalendar.add(Calendar.SECOND, 0);
        } else {
            startCalendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            startCalendar.add(Calendar.SECOND, 0);
        }
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.setTitle(isEndTime ? "结束时间" : "开始时间");
        timePickerDialog.show(getFragmentManager(), "");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (isEndTime) {
            endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, second);
            endCalendar.add(Calendar.SECOND, 0);
        } else {
            startCalendar.set(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, second);
            startCalendar.add(Calendar.SECOND, 0);
        }
        setText();
    }

    @Override
    public void clickTitleBack() {
        finish();
    }

    @Override
    public void doubleClickTitle() {

    }

    @Override
    public void clickTitleEdit() {
        // 如果需要，在这里加筛选逻辑
        if (!startCalendar.before(endCalendar)) {
            Utils.toastImmediately("活动开始时间必须早于活动结束时间");
            return;
        }
        if (!"".equals(minPeople.getText().toString()) && !"".equals(maxPeople.getText().toString())) {
            int min = Integer.valueOf(minPeople.getText().toString());
            int max = Integer.valueOf(maxPeople.getText().toString());
            if (min > max) {
                Utils.toastImmediately("最少参与人数不能大于最多参与人数");
                return;
            }
        }
        dialog = new MaterialDialog.Builder(mContext)
                .title("筛选中")
                .content("请稍候...")
                .cancelable(false)
                .progress(true, 0)
                .show();
        titleLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) dialog.dismiss();
                dialog = new MaterialDialog.Builder(mContext)
                        .title("筛选成功")
                        .content("筛选成功！")
                        .cancelable(false)
                        .positiveText("确定")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        })
                        .show();
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_layout:
            case R.id.end_layout:
                isEndTime = v.getId() == R.id.end_layout;
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        this,
                        isEndTime ? endCalendar.get(Calendar.YEAR) : startCalendar.get(Calendar.YEAR),
                        isEndTime ? endCalendar.get(Calendar.MONTH) : startCalendar.get(Calendar.MONTH),
                        isEndTime ? endCalendar.get(Calendar.DAY_OF_MONTH) : startCalendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setTitle(isEndTime ? "结束时间" : "开始时间");
                datePickerDialog.show(getFragmentManager(), "");
                break;
            case R.id.min_people_layout:
                new MaterialDialog.Builder(mContext)
                        .title("最少参与人数")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                minPeople.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("最少参与人数", minPeople.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.max_people_layout:
                new MaterialDialog.Builder(mContext)
                        .title("最多参与人数")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                maxPeople.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("最多参与人数", maxPeople.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.credit_layout:
                new MaterialDialog.Builder(mContext)
                        .title("信用要求")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                credit.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("最多参与人数", credit.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.distance_layout:
                new MaterialDialog.Builder(mContext)
                        .title("信用要求")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                distance.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("距离（米）", distance.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
        }
    }

    private void setText() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        if (isEndTime) endTime.setText(simpleDateFormat.format(endCalendar.getTime()));
        else startTime.setText(simpleDateFormat.format(startCalendar.getTime()));
    }

}
