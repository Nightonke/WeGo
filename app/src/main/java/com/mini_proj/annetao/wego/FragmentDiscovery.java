package com.mini_proj.annetao.wego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentDiscovery extends Fragment {

    private boolean shownMapView = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_discovery, container, false);



        return messageLayout;
    }

    public void toggleView() {
        shownMapView = !shownMapView;
    }

}
