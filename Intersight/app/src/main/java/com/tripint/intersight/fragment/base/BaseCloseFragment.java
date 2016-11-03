package com.tripint.intersight.fragment.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tripint.intersight.R;


/**
 * Created by YoKeyword on 16/2/7.
 */
public class BaseCloseFragment extends BaseFragment {
    private static final String TAG = "Intersight";

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.iconfont_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

    }

//    protected void initToolbarMenu(Toolbar toolbar) {
//        toolbar.inflateMenu(R.menu.home);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_hierarchy:
//                        mActivity.showFragmentStackHierarchyView();
//                        mActivity.logFragmentStackHierarchy(TAG);
//                        break;
//                }
//                return true;
//            }
//        });
//    }
}
