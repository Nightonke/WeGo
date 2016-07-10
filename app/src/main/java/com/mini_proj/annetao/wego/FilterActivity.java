package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    private TextView people;
    private TextView credit;
    private TextView distance;

    private boolean isEndTime = false;
    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        titleLayout = findView(R.id.title_layout_top);
        titleLayout.setOnTitleActionListener(this);
        startTime = findView(R.id.start);
        endTime = findView(R.id.end);
        people = findView(R.id.people);
        credit = findView(R.id.credit);
        distance = findView(R.id.distance);

        findView(R.id.start_layout).setOnClickListener(this);
        findView(R.id.end_layout).setOnClickListener(this);
        findView(R.id.people_layout).setOnClickListener(this);
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
        setText();
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
            case R.id.people_layout:

                break;
            case R.id.credit_layout:

                break;
            case R.id.distance_layout:

                break;
        }
    }

    private void setText() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        startTime.setText(simpleDateFormat.format(startCalendar.getTime()));
        endTime.setText(simpleDateFormat.format(endCalendar.getTime()));
    }

}
