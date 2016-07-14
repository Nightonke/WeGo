package com.mini_proj.annetao.wego;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by huangweiping on 16/7/10.
 */
public class ExerciseAdapter
        extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private OnExerciseSelectListener onExerciseSelectListener;
    private Context context;
    private ArrayList<Exercise> exercises;

    public ExerciseAdapter(OnExerciseSelectListener onExerciseSelectListener, ArrayList<Exercise> exercises) {
        this.onExerciseSelectListener = onExerciseSelectListener;
        this.exercises = exercises;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exersise, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Exercise e = exercises.get(position);

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExerciseSelectListener != null) onExerciseSelectListener.onSelect(holder.getAdapterPosition());
            }
        });
        holder.name.setText(e.getName());
        holder.people.setText(e.getAttendencyNumString());
        if (e.getPic_store() != null && !"".equals(e.getPic_store())) Picasso.with(context).load(e.getPic_store()).into(holder.image);
        if (e.getTagId() != -1) holder.tagGroup.setTags(Tag.value(e.getTagId()).toString());
        holder.date.setText(e.getStart_time().substring(0, 16));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View base;
        public TextView name;
        public TextView people;
        public KenBurnsView image;
        public TagGroup tagGroup;
        public TextView date;

        public ViewHolder(View v) {
            super(v);
            base = v.findViewById(R.id.base);
            name = (TextView) v.findViewById(R.id.name);
            people = (TextView) v.findViewById(R.id.people);
            image = (KenBurnsView) v.findViewById(R.id.image);
            tagGroup = (TagGroup) v.findViewById(R.id.tag_group);
            date = (TextView) v.findViewById(R.id.date);
        }
    }

    public interface OnExerciseSelectListener {
        void onSelect(int p);
    }
}