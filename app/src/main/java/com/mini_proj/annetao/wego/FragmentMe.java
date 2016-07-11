package com.mini_proj.annetao.wego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentMe extends Fragment implements View.OnClickListener{

    private TextView signOutBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_me, container, false);
        signOutBtn = (TextView) messageLayout.findViewById(R.id.sign_out);
        signOutBtn.setOnClickListener(this);




        return messageLayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out:
                //TODO 登出数据处理
                Toast.makeText(getActivity(),"signout",Toast.LENGTH_SHORT).show();
                User.getInstance().setLogin(false);
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                break;

        }
    }

}
