package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.OnClickListener;
import com.tripint.intersight.common.widget.dialogplus.OnDismissListener;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.withdraw_bind_weixin)
    TextView withdrawBindWeixin;
    @Bind(R.id.withdraw_edit_coin)
    EditText withdrawEditCoin;
    @Bind(R.id.withdraw_money)
    TextView withdrawMoney;
    @Bind(R.id.withdraw_total_coin)
    TextView withdrawTotalCoin;
    @Bind(R.id.withdraw_total_money)
    TextView withdrawTotalMoney;
    @Bind(R.id.personal_info_submit)
    Button personalInfoSubmit;
    @Bind(R.id.withdraw_rule)
    TextView withdrawRule;

    private DialogPlus dialogPlus;
    public static WithdrawFragment getInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        WithdrawFragment fragment = new WithdrawFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        return view;
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("提现");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.withdraw_bind_weixin, R.id.personal_info_submit, R.id.withdraw_rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdraw_bind_weixin:
                break;
            case R.id.personal_info_submit:
                break;
            case R.id.withdraw_rule://提现规则
                initDialog();
                break;
        }
    }

    private void initDialog() {
        dialogPlus = DialogPlusUtils.Builder(mActivity)
                .setHolder(DialogPlusUtils.VIEW, new ViewHolder(R.layout.withdraw))
                .setIsHeader(false)
                .setIsFooter(false)
                .setIsExpanded(false)
                .setGravity(Gravity.CENTER)
                .showCompleteDialog();
    }
}
