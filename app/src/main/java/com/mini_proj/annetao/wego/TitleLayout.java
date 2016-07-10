package com.mini_proj.annetao.wego;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Weiping on 2016/5/16.
 */
public class TitleLayout extends FrameLayout {

    private Context mContext;
    private OnTitleActionListener mListener;

    private LinearLayout backLayout;
    private TextView backText;
    private TextView title;
    private TextView edit;

    public TitleLayout(Context context) {
        super(context, null, R.style.TitleLayout);

        mContext = context;
        init(null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.style.TitleLayout);

        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (mContext instanceof OnTitleActionListener) mListener = (OnTitleActionListener) mContext;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ui_title_layout, this);

        TypedArray ta = attrs == null ?
                null : getContext().obtainStyledAttributes(attrs, R.styleable.TitleLayout);
        boolean hasBackText = true;
        boolean hasTitleText = true;
        boolean hasEditText = true;
        String backString = "";
        String titleString = "";
        String editString = "";

        if (ta != null) {
            hasBackText = ta.getBoolean(R.styleable.TitleLayout_nbHasBackText, true);
            hasTitleText = ta.getBoolean(R.styleable.TitleLayout_nbHasTitleText, true);
            hasEditText = ta.getBoolean(R.styleable.TitleLayout_nbHasEditText, true);
            backString = ta.getString(R.styleable.TitleLayout_nbBackText);
            titleString = ta.getString(R.styleable.TitleLayout_nbTitleText);
            editString = ta.getString(R.styleable.TitleLayout_nbEditText);
        }

        backLayout = (LinearLayout)findViewById(R.id.title_layout_back_layout);
        backText = (TextView)findViewById(R.id.title_layout_back_text);
        title = (TextView)findViewById(R.id.title_layout_title_text);
        edit = (TextView)findViewById(R.id.title_layout_edit_text);

        backText.setText(backString);
        title.setText(titleString);
        edit.setText(editString);

        if ("".equals(backString) || !hasBackText) backText.setVisibility(GONE);
        if ("".equals(editString) || !hasEditText) edit.setVisibility(GONE);
        if (!hasTitleText) title.setVisibility(GONE);

        backLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickTitleBack();
            }
        });

        title.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {

            }

            @Override
            public void onDoubleClick(View v) {
                if (mListener != null) mListener.doubleClickTitle();
            }
        });

        title.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int margin = Math.max(backLayout.getWidth(), edit.getWidth()) + 20;
                ((LayoutParams)title.getLayoutParams()).setMargins(margin, 0, margin, 0);
            }
        });

        title.setSelected(true);

        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickTitleEdit();
            }
        });
    }

    public void setTitle(String titleString) {
        title.setText(titleString);
    }

    public void setEdit(String editString) {
        edit.setText(editString);
    }

    public String getEdit() {
        return edit.getText().toString();
    }

    public void setBack(String backString) {
        backText.setText(backString);
    }

    public void setOnTitleActionListener(OnTitleActionListener listener) {
        mListener = listener;
    }

    public interface OnTitleActionListener {
        void clickTitleBack();
        void doubleClickTitle();
        void clickTitleEdit();
    }
}
