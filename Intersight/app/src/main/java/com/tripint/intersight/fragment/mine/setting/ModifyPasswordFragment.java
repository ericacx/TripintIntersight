package com.tripint.intersight.fragment.mine.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置---帐号信息---修改密码
 * A simple {@link Fragment} subclass.
 */
public class ModifyPasswordFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bind_email_image)
    ImageView bindEmailImage;
    @Bind(R.id.bind_email_textview)
    TextView bindEmailTextview;
    @Bind(R.id.bind_phone_et_phoneNumber)
    EditText bindPhoneEtPhoneNumber;
    @Bind(R.id.bind_email_et_input_new_password)
    EditText bindEmailEtInputNewPassword;
    @Bind(R.id.bind_email_et_input_twice_new_password)
    EditText bindEmailEtInputTwiceNewPassword;
    @Bind(R.id.bind_phone_complete)
    Button bindPhoneComplete;

    public static ModifyPasswordFragment newInstance() {
        Bundle args = new Bundle();
        ModifyPasswordFragment fragment = new ModifyPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initToolbarNav(toolbar);
        toolbar.setTitle("修改密码");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.bind_phone_complete)
    public void onClick() {
    }
}
