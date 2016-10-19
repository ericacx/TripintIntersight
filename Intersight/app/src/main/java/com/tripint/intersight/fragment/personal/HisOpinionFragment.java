package com.tripint.intersight.fragment.personal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.PersonalUserInfoEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.ExpertDataHttpRequest;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 他的观点----页面
 * A simple {@link Fragment} subclass.
 */
public class HisOpinionFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    public static final String ARG_USER_ID = "arg_user_id";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_my_common_header_left)
    TextView btnMyCommonHeaderLeft;
    @Bind(R.id.btn_my_common_header_right)
    TextView btnMyCommonHeaderRight;
    @Bind(R.id.hisAskAnswerVG)
    LinearLayout hisAskAnswerVG;
    @Bind(R.id.his_opinion_ask)
    Button hisOpinionAsk;
    @Bind(R.id.his_opinion_interview)
    Button hisOpinionInterview;
    @Bind(R.id.hisAskAnswerLL)
    LinearLayout hisAskAnswerLL;
    @Bind(R.id.his_opinion_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;

    private int TOTAL_COUNTER = 0;

    private int mCurrentCounter = 0;

    List<MineMultipleItemModel> models = new ArrayList<>();

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<MineFollowPointEntity>> subscriber;

    private BasePageableResponse<MineFollowPointEntity> data;

    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriberCode;

    private int tab;
    private int uid = 0;

    public static HisOpinionFragment newInstance(int uid) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, uid);
        HisOpinionFragment fragment = new HisOpinionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getInt(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_his_opinion, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        setTab(0);
        return view;
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("他的观点");
    }


    /**
     * 请求不同的关键字 精选自由行、省心国内游、品质出境游
     *
     * @param tab
     */
    private void setTab(int tab) {
        this.tab = tab;
        btnMyCommonHeaderLeft.setSelected(tab == 0);
        btnMyCommonHeaderRight.setSelected(tab == 1);
        httpRequestData(tab);
    }

    private void httpRequestData(int type) {

        subscriberCode = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                codeDataEntity = entity;
                Log.e("hisOpinion",entity.getFlg());
            }
        };

        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<MineFollowPointEntity>>() {
            @Override
            public void onNext(BasePageableResponse<MineFollowPointEntity> entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);
                initAdapter(tab);
            }
        };

        ExpertDataHttpRequest.getInstance(mActivity).getHisFollowPoint(new ProgressSubscriber(subscriber, mActivity), type, 26,1);
    }


    private void initAdapter(int tab) {

        initData();
        mAdapter = new MineCommonMultipleAdapter(models);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
//                DiscussEntiry entity = (DiscussEntiry) adapter.getItem(position);
//                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity)));
            }
        });
        mAdapter.setLoadingView(getLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected void initView(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onRefresh() {
        initData();
        mAdapter.setNewData(models);
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.removeAllFooterView();
        mCurrentCounter = PAGE_SIZE;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    mAdapter.loadComplete();
                } else {
                    initData();
                    mAdapter.addData(models);
                    mCurrentCounter = mAdapter.getData().size();
                }
            }
        }, 200);
    }

    private View getLoadMoreView() {
        final View customLoading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, (ViewGroup) mRecyclerView.getParent(), false);
        return customLoading;
    }

    private void initData(){
        int type = tab == 0 ? MineMultipleItemModel.HIS_OPTION : MineMultipleItemModel.HIS_OPTION_FOLLOW;
        for (MineFollowPointEntity entiry : data.getLists()) {

            models.add(new MineMultipleItemModel(type, entiry));
        }
    }

    @OnClick({R.id.his_opinion_ask, R.id.his_opinion_interview, R.id.btn_my_common_header_left, R.id.btn_my_common_header_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_my_common_header_left://他的观点
                if (!btnMyCommonHeaderLeft.isSelected()) {
                    setTab(0);
                }
                break;
            case R.id.btn_my_common_header_right://他关注的观点
                if (!btnMyCommonHeaderRight.isSelected()) {
                    setTab(1);
                }
                break;
            case R.id.his_opinion_ask:
                final DialogPlus dialogPlus = DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.VIEW, new ViewHolder(R.layout.question_layout))
                        .setIsHeader(false)
                        .setIsFooter(true)
                        .setIsExpanded(false)
                        .setCloseName("取消")
                        .setOnCloseListener(new DialogPlusUtils.OnCloseListener() {
                            @Override
                            public void closeListener(DialogPlus dialog, View view) {
                                dialog.dismiss();
                            }
                        })
                        .setConfirmName("确认")
                        .setOnConfirmListener(new DialogPlusUtils.OnConfirmListener() {
                            @Override
                            public void confirmListener(DialogPlus dialog, View view) {

                                EditText editText = ((EditText) dialog.findViewById(R.id.dialog_question_edit));
                                if (TextUtils.isEmpty(editText.getText().toString().trim())){
                                    ToastUtil.showToast(mActivity,"输入的内容不能为空");
                                } else {
                                    MineDataHttpRequest.getInstance(mActivity).postOtherQuestion(
                                            new ProgressSubscriber(subscriberCode, mActivity)
                                            ,uid,editText.getText().toString().trim()
                                    );
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setGravity(Gravity.BOTTOM)
                        .showCompleteDialog();
                break;
            case R.id.his_opinion_interview:
                final DialogPlus dialog = DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.VIEW, new ViewHolder(R.layout.interview_layout))
                        .setIsHeader(false)
                        .setIsFooter(true)
                        .setIsExpanded(false)
                        .setCloseName("取消")
                        .setOnCloseListener(new DialogPlusUtils.OnCloseListener() {
                            @Override
                            public void closeListener(DialogPlus dialog, View view) {
                                dialog.dismiss();
                            }
                        })
                        .setConfirmName("确认")
                        .setOnConfirmListener(new DialogPlusUtils.OnConfirmListener() {
                            @Override
                            public void confirmListener(DialogPlus dialog, View view) {

                                EditText nickname = ((EditText) dialog.findViewById(R.id.dialog_interview_nickname));
                                EditText email = ((EditText) dialog.findViewById(R.id.dialog_interview_email));
                                EditText phone = ((EditText) dialog.findViewById(R.id.dialog_interview_phone));
                                EditText company = ((EditText) dialog.findViewById(R.id.dialog_interview_company));
                                EditText theme = ((EditText) dialog.findViewById(R.id.dialog_interview_theme));
                                EditText editor = ((EditText) dialog.findViewById(R.id.dialog_interview_edit));
                                if (TextUtils.isEmpty(nickname.getText().toString().trim())){
                                    ToastUtil.showToast(mActivity,"输入的姓名不能为空");
                                } else if (TextUtils.isEmpty(email.getText().toString().trim())){
                                    ToastUtil.showToast(mActivity,"输入的邮箱不能为空");
                                }else if (phone.getText().toString().trim().length() != 11){
                                    ToastUtil.showToast(mActivity,"输入的手机不正确");
                                }else if (TextUtils.isEmpty(company.getText().toString().trim())){
                                    ToastUtil.showToast(mActivity,"输入的公司不能为空");
                                }else if (TextUtils.isEmpty(theme.getText().toString().trim())){
                                    ToastUtil.showToast(mActivity,"输入的主题不能为空");
                                }else if (TextUtils.isEmpty(editor.getText().toString().trim())){
                                    ToastUtil.showToast(mActivity,"输入的提纲不能为空");
                                }else {
                                    PersonalUserInfoEntity personalUserInfoEntity = new PersonalUserInfoEntity(
                                            uid,nickname.getText().toString().trim(),company.getText().toString().trim(),
                                            phone.getText().toString().trim(),email.getText().toString().trim(),
                                            theme.getText().toString().trim(),editor.getText().toString().trim()
                                    );
                                    MineDataHttpRequest.getInstance(mActivity).postOtherInterview(
                                            new ProgressSubscriber(subscriberCode, mActivity)
                                            ,personalUserInfoEntity
                                    );
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setGravity(Gravity.BOTTOM)
                        .showCompleteDialog();
                break;
        }
    }

}
