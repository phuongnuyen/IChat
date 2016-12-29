package com.direct.ichat.Fagment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.direct.ichat.R;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class SettingBarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_bar, container, false);
    }
}
