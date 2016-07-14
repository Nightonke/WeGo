package com.mini_proj.annetao.wego;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huangweiping on 16/7/10.
 */
public class UserNoticeAdapter
        extends RecyclerView.Adapter<UserNoticeAdapter.ViewHolder> {

    private OnNoticeSelectListener onNoticeSelectListener;
    private Context context;
    private ArrayList<UserNotice> notices;

    public UserNoticeAdapter(OnNoticeSelectListener onNoticeSelectListener, ArrayList<UserNotice> notices) {
        this.onNoticeSelectListener = onNoticeSelectListener;
        this.notices = notices;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final UserNotice n = notices.get(position);
        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoticeSelectListener != null) onNoticeSelectListener.onSelect(holder.getAdapterPosition());
            }
        });
        holder.content.setText(n.getNotice_content());
        holder.position.setText((position + 1) + "");
        holder.date.setText(n.getTime());
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View base;
        public TextView content;
        public TextView position;
        public TextView date;

        public ViewHolder(View v) {
            super(v);
            base = v.findViewById(R.id.base);
            content = (TextView) v.findViewById(R.id.content);
            position = (TextView) v.findViewById(R.id.position);
            date = (TextView) v.findViewById(R.id.date);
        }
    }

    public interface OnNoticeSelectListener {
        void onSelect(int p);
    }
}