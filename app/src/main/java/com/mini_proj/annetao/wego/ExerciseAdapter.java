package com.mini_proj.annetao.wego;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by huangweiping on 16/7/10.
 */
public class ExerciseAdapter
        extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private OnExerciseSelectListener onExerciseSelectListener;
    private Context context;

    public ExerciseAdapter(OnExerciseSelectListener onExerciseSelectListener) {
        this.onExerciseSelectListener = onExerciseSelectListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exersise, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Exercise e = ExercisePool.getTopicPool().getTestExercises().get(position);

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExerciseSelectListener != null) onExerciseSelectListener.onSelect(holder.getAdapterPosition());
            }
        });
        holder.name.setText(e.getName());
        holder.people.setText("200人参与");
        Picasso.with(context).load(e.getPic_store()).into(holder.image);
        holder.tagGroup.setTags("标签一", "标签二");
    }

    @Override
    public int getItemCount() {
        return ExercisePool.getTopicPool().getTestExercises().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View base;
        public TextView name;
        public TextView people;
        public ImageView image;
        public TagGroup tagGroup;

        public ViewHolder(View v) {
            super(v);
            base = v.findViewById(R.id.base);
            name = (TextView) v.findViewById(R.id.name);
            people = (TextView) v.findViewById(R.id.people);
            image = (ImageView) v.findViewById(R.id.image);
            tagGroup = (TagGroup) v.findViewById(R.id.tag_group);
        }
    }

    public interface OnExerciseSelectListener {
        void onSelect(int p);
    }
}