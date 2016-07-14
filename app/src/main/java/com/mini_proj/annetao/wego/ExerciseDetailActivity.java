package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.mini_proj.annetao.wego.util.map.QQMapSupporter;
import com.squareup.picasso.Picasso;
import com.tencent.tauth.Tencent;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;

public class ExerciseDetailActivity extends BaseActivity implements TitleLayout.OnTitleActionListener {

    private TitleLayout titleLayout;
    private ScrollView scrollView;
    private KenBurnsView kenBurnsView;
    private TextView title;
    private TextView time;
    private View mapButton;
    private TextView average;
    private TextView people;
    private TextView detail;
    private ExpandedListView expandedListView;
    private String tagId = "-1";

    private Exercise exercise;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        position = getIntent().getIntExtra("position", -1);
        tagId = getIntent().getStringExtra("tag_id");
        if (position == -1) {
            finish();
            return;
        }
        if(tagId.equals("-1"))
            exercise = ExercisePool.getTopicPool().getAllExercise().get(position);
        else
            exercise = ExercisePool.getTopicPool().getTagExercise(tagId).get(position);

        titleLayout = findView(R.id.title_layout_top);
        titleLayout.setOnTitleActionListener(this);
        scrollView = findView(R.id.scroll_view);
        kenBurnsView = findView(R.id.image);
        Picasso.with(mContext).load(exercise.getPic_store()).into(kenBurnsView);
        title = findView(R.id.title);
        title.setText(exercise.getName());
        time = findView(R.id.time);
        time.setText(exercise.getStart_time() + " ~ " + exercise.getEnd_time());
        mapButton = findView(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        average = findView(R.id.average);
        people = findView(R.id.people);
        detail = findView(R.id.detail);
        expandedListView = findView(R.id.comment);
    }

    @Override
    public void clickTitleBack() {
        finish();
    }

    @Override
    public void doubleClickTitle() {
        scrollView.smoothScrollBy(0, 0);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    @Override
    public void clickTitleEdit() {
        Intent intent = new Intent(this, ExerciseSignUpActivity.class);
        intent.putExtra("exercise_id", exercise.getId());
        startActivity(intent);
    }

    private void openMap() {
        Intent intent = new Intent(this, TencentMapActivity.class);
        intent.putExtra("map_type", QQMapSupporter.QQ_MAP_TYPE_ONE_EXERCISE);
        intent.putExtra("tag_id", tagId);
        intent.putExtra("position",position);
        startActivity(intent);

    }
}
