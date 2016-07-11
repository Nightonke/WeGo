package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mini_proj.annetao.wego.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Locale;

public class UserInformationActivity extends BaseActivity
        implements
        View.OnClickListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private EditText name;
    private TextView birthday;
    private RadioGroup sex;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        name = findView(R.id.name);
        birthday = findView(R.id.birthday);
        sex = findView(R.id.sex);

        findView(R.id.name_layout).setOnClickListener(this);
        findView(R.id.clear_name).setOnClickListener(this);
        findView(R.id.birthday_layout).setOnClickListener(this);
        findView(R.id.next).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name_layout:
                name.post(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showKeyboard(name, UserInformationActivity.this);
                    }
                });
                break;
            case R.id.clear_name:
                name.setText("");
                onClick(findView(R.id.name_layout));
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
            case R.id.next:
                // 调用网络接口将数据写入服务器，如果成功，再写入本地
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
        birthday.setText(this.year + "-" + String.format(Locale.getDefault(), "%02d", month) + String.format(Locale.getDefault(), "%02d", day));
    }
}
