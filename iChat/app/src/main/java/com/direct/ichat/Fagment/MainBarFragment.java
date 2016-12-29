package com.direct.ichat.Fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.direct.ichat.R;

import butterknife.BindView;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class MainBarFragment extends Fragment{
    private float maxAlpha = 1.0f;

    @BindView(R.id.btn_recent_message)
    ImageButton btnMessage;
    @BindView(R.id.btn_friends)
    ImageButton btnFriends;
    @BindView(R.id.btn_feeling)
    ImageButton btnFeeling;
    @BindView(R.id.btn_setting)
    ImageButton btnSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_bar, container, false);

    }
}
