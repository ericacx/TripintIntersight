package com.tripint.intersight.fragment.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tripint.intersight.R;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.mine.message.NewMessageFragment;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by YoKeyword on 16/2/7.
 */
public class BaseBackFragment extends BaseFragment {
    public static final String TAG = "Fragmentation";

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });

//        initToolbarMenu(toolbar);
    }
}
