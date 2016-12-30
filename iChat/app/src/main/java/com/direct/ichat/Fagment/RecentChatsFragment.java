package com.direct.ichat.Fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.direct.ichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class RecentChatsFragment extends Fragment {

    // ------ them vao
    @BindView(R.id.srv_message)
    SearchView srvMessage;
    @BindView(R.id.rcv_recent_chats)
    RecyclerView rcvRecentChats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent_chats, container, false);
    }
}
