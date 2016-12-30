package com.direct.ichat.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.direct.ichat.R;

import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/30/2016.
 */

public class SettingProfileActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_setting_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {

    }
}
