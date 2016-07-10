package com.mini_proj.annetao.wego;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mini_proj.annetao.wego.util.Utils;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentDiscovery extends Fragment {

    private TagGroup tagGroup;
    private RecyclerView recyclerView;
    private boolean shownMapView = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_discovery, container, false);

        tagGroup = (TagGroup) messageLayout.findViewById(R.id.tag_group);
        tagGroup.setTags(Tag.getAllTagName());
        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                for (int i = 0; i < tagGroup.getTags().length; i++) {
                    if (tag.equals(tagGroup.getTags()[i])) {
                        tagGroup.getChildAt(i).setBackgroundResource(R.drawable.shape_rounded_tag);
                        tagGroup.getChildAt(i).setPadding(Utils.dp2px(20), Utils.dp2px(2), Utils.dp2px(20), Utils.dp2px(2));
                        ((TextView)tagGroup.getChildAt(i)).setTextColor(Color.WHITE);
                        ColorStateList csl = ContextCompat.getColorStateList(getContext(), R.color.tag_select_text_color);
                        if (csl != null) ((TextView)tagGroup.getChildAt(i)).setTextColor(csl);
                    } else {
                        tagGroup.getChildAt(i).setBackgroundResource(0);
                        ((TextView)tagGroup.getChildAt(i)).setTextColor(ContextCompat.getColor(getContext(), R.color.tag));
                    }
                }
                chooseTag(tag);
            }
        });
        for (int i = 0; i < tagGroup.getTags().length; i++) {
            tagGroup.getChildAt(i).setBackgroundResource(0);
            ((TextView)tagGroup.getChildAt(i)).setTextColor(ContextCompat.getColor(getContext(), R.color.tag));
        }

        return messageLayout;
    }

    public void toggleView() {
        shownMapView = !shownMapView;
    }

    public void chooseTag(String tagName) {
        Tag tag = Tag.value(tagName);
        Utils.toast(tag.toString());


    }

}
