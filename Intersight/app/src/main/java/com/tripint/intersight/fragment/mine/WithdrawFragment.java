package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.tripint.intersight.entity.payment.ReflectEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.PaymentDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

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
    private PageDataSubscriberOnNext<ReflectEntity> subscriber;
    private ReflectEntity data = new ReflectEntity();
    private PageDataSubscriberOnNext<ReflectEntity> subscriberBalance;
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
        httpRequestData();
        return view;
    }

    private void httpRequestData() {
        subscriberBalance = new PageDataSubscriberOnNext<ReflectEntity>() {
            @Override
            public void onNext(ReflectEntity reflectEntity) {
                data = reflectEntity;

                withdrawTotalCoin.setText(data.getTotalPcoin()+ "");
                String totalMoney =
                        "<font color='#BABABA' size='12'>" + "P.coin(合" + "</font>"
                                + "<font color='#ED5564' size='12'>" + "￥" + data.getTotalMoney() + "</font>"
                                + "<font color='#BABABA' size='12'>" + ")" + "</font>";
                withdrawTotalMoney.setText(Html.fromHtml(totalMoney));
            }
        };

        subscriber = new PageDataSubscriberOnNext<ReflectEntity>() {
            @Override
            public void onNext(ReflectEntity reflectEntity) {
                data = reflectEntity;
                initView();
            }
        };

        PaymentDataHttpRequest.getInstance(mActivity).postBalance(new ProgressSubscriber<ReflectEntity>(subscriberBalance,mActivity));
    }

    private void initView() {

        withdrawEditCoin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                float withdraw = Integer.parseInt(withdrawEditCoin.getText().toString()) / 10;
                String money =
                        "<font color='#BABABA' size='12'>" + "(合" + "</font>"
                                + "<font color='#ED5564' size='12'>" + "￥" + withdraw + "</font>"
                                + "<font color='#BABABA' size='12'>" + ")" + "</font>";

                withdrawMoney.setText(Html.fromHtml(money));
                return false;
            }
        });


        withdrawTotalCoin.setText(data.getTotalPcoin()+"");
        String totalMoney =
                "<font color='#BABABA' size='12'>" + "P.coin(合" + "</font>"
                + "<font color='#ED5564' size='12'>" + "￥" + data.getTotalMoney() + "</font>"
                + "<font color='#BABABA' size='12'>" + ")" + "</font>";
        withdrawTotalMoney.setText(Html.fromHtml(totalMoney));
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
