package com.tripint.intersight.fragment.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tripint.intersight.R;
import com.tripint.intersight.common.fragmentation.SupportFragment;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.mine.message.NewMessageFragment;
import com.tripint.intersight.helper.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by YoKeyword on 16/2/3.
 */
public class BaseFragment extends SupportFragment {
    private static final String TAG = "Fragmentation";

    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.home);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_message:
                        EventBus.getDefault().post(new StartFragmentEvent(NewMessageFragment.newInstance()));
                        break;
                }
                return true;
            }
        });
    }

    protected void showProgressDialog(){
        ProgressDialogUtils.getInstants(mActivity).show();
    }

    protected void dismissProgressDialog(){
        ProgressDialogUtils.getInstants(mActivity).dismiss();
    }

}
