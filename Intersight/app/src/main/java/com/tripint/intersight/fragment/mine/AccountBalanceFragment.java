package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.entity.mine.BalanceEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户余额
 * A simple {@link Fragment} subclass.
 */
public class AccountBalanceFragment extends BaseBackFragment {


    @Bind(R.id.toolbar_right)
    TextView toolbarRight;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.account_balance_money)
    TextView accountBalanceMoney;
    @Bind(R.id.account_balance_withdraw)
    Button accountBalanceWithdraw;
    @Bind(R.id.payment_one_text_one)
    TextView paymentOneTextOne;
    @Bind(R.id.payment_one_text_two)
    TextView paymentOneTextTwo;
    @Bind(R.id.payment_one)
    LinearLayout paymentOne;
    @Bind(R.id.payment_two_text_one)
    TextView paymentTwoTextOne;
    @Bind(R.id.payment_two_text_two)
    TextView paymentTwoTextTwo;
    @Bind(R.id.payment_two)
    LinearLayout paymentTwo;
    @Bind(R.id.payment_three_text_one)
    TextView paymentThreeTextOne;
    @Bind(R.id.payment_three_text_two)
    TextView paymentThreeTextTwo;
    @Bind(R.id.payment_three)
    LinearLayout paymentThree;
    @Bind(R.id.payment_four_text_one)
    TextView paymentFourTextOne;
    @Bind(R.id.payment_four_text_two)
    TextView paymentFourTextTwo;
    @Bind(R.id.payment_four)
    LinearLayout paymentFour;
    @Bind(R.id.payment_five_text_one)
    TextView paymentFiveTextOne;
    @Bind(R.id.payment_five_text_two)
    TextView paymentFiveTextTwo;
    @Bind(R.id.payment_five)
    LinearLayout paymentFive;
    @Bind(R.id.payment_six_text_one)
    TextView paymentSixTextOne;
    @Bind(R.id.payment_six_text_two)
    TextView paymentSixTextTwo;
    @Bind(R.id.payment_six)
    LinearLayout paymentSix;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;

    private BalanceEntity data = new BalanceEntity();
    private PageDataSubscriberOnNext<BalanceEntity> subscriber;

    public static AccountBalanceFragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        AccountBalanceFragment fragment = new AccountBalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_balance, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        httpRequestData();
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<BalanceEntity>() {
            @Override
            public void onNext(BalanceEntity balanceEntity) {
                data = balanceEntity;
                initView();
            }
        };

        MineDataHttpRequest.getInstance(mActivity).postUserBalance(new ProgressSubscriber<BalanceEntity>(subscriber, mActivity));
    }

    private void initView() {
        accountBalanceMoney.setText(data.getBalance() + "");//余额
        paymentOneTextOne.setText("￥" + data.getPayment1());
        paymentTwoTextOne.setText("￥" + data.getPayment2());
        paymentThreeTextOne.setText("￥" + data.getPayment3());
        paymentFourTextOne.setText("￥" + data.getPayment4());
        paymentFiveTextOne.setText("￥" + data.getPayment5());
        paymentSixTextOne.setText("￥" + data.getPayment6());

        paymentOneTextTwo.setText("P.coin x " + (data.getPayment1() * 10));
        paymentTwoTextTwo.setText("P.coin x " + (data.getPayment2() * 10));
        paymentThreeTextTwo.setText("P.coin x " + (data.getPayment3() * 10));
        paymentFourTextTwo.setText("P.coin x " + (data.getPayment4() * 10));
        paymentFiveTextTwo.setText("P.coin x " + (data.getPayment5() * 10));
        paymentSixTextTwo.setText("P.coin x " + (data.getPayment6() * 10));
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbarTitle.setText("账户余额");
        toolbarRight.setText("明细");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.account_balance_withdraw, R.id.payment_one, R.id.payment_two, R.id.payment_three,
            R.id.payment_four, R.id.payment_five, R.id.payment_six,R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_balance_withdraw://提现
                EventBus.getDefault().post(new StartFragmentEvent(WithdrawFragment.getInstance()));
                break;
            case R.id.toolbar_right://账户明细
                EventBus.getDefault().post(new StartFragmentEvent(AccountDetailFragment.newInstance()));
                break;
            case R.id.payment_one:
                break;
            case R.id.payment_two:
                break;
            case R.id.payment_three:
                break;
            case R.id.payment_four:
                break;
            case R.id.payment_five:
                break;
            case R.id.payment_six:
                break;
        }
    }
}
