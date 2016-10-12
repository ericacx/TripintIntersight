package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageDetailCommentAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.entity.discuss.CommentEntity;
import com.tripint.intersight.entity.mine.InterviewDetailEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * \
 * 我的约访 我被约访 详情页面
 * A simple {@link Fragment} subclass.
 */
public class MyInterviewDetailFragment extends BaseBackFragment {

    public static final String ARG_INTERVIEW_ID = "arg_interview_id";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.my_interview_status)
    TextView myInterviewStatus;
    @Bind(R.id.my_interview_type)
    TextView myInterviewType;
    @Bind(R.id.my_interview_code)
    TextView myInterviewCode;
    @Bind(R.id.my_interview_time)
    TextView myInterviewTime;//时间
    @Bind(R.id.my_interview_head)
    TextView myInterviewHead;//标题
    @Bind(R.id.my_interview_content)
    TextView myInterviewContent;//内容
    @Bind(R.id.my_interview_people)
    TextView myInterviewPeople;//约访人
    @Bind(R.id.my_interview_name)
    TextView myInterviewName;//名字
    @Bind(R.id.my_interview_company)
    TextView myInterviewCompany;//公司
    @Bind(R.id.my_interview_title)
    TextView myInterviewTitle;//职位
    @Bind(R.id.my_interview_detail_avatar)
    CircleImageView myInterviewDetailAvatar;
    @Bind(R.id.my_interview_detail_look)
    TextView myInterviewDetailLook;
    @Bind(R.id.my_interview_detail_kefu)
    Button myInterviewDetailKefu;
    @Bind(R.id.my_interview_detail_twiceInterview)
    Button myInterviewDetailTwiceInterview;
    @Bind(R.id.my_interview_detail_ask)
    Button myInterviewDetailAsk;
    @Bind(R.id.my_interview_detail_recyclerView)
    RecyclerView myInterviewDetailRecyclerView;

    private int mInterviewId;

    private PageDataSubscriberOnNext<InterviewDetailEntity> subscriber;

    private InterviewDetailEntity data;

    private AskAnswerPageDetailCommentAdapter mAdapter;

    public static MyInterviewDetailFragment newInstance(InterviewEntity entity) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(MyInterviewDetailFragment.ARG_INTERVIEW_ID, entity.getId());
        MyInterviewDetailFragment fragment = new MyInterviewDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mInterviewId = bundle.getInt(ARG_INTERVIEW_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_interview_detail, container, false);
        ButterKnife.bind(this, view);
        httpRequestData();
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<InterviewDetailEntity>() {
            @Override
            public void onNext(InterviewDetailEntity entity) {
                //接口请求成功后处理
                data = entity;
//                ToastUtil.showToast(mActivity, entity.getAbility().toString() +"");
                initView(null);
                initCommentAdapter();
            }
        };

        MineDataHttpRequest.getInstance(mActivity).getInterviewDetail(new ProgressSubscriber(subscriber, mActivity), mInterviewId);
    }

    private void initView(View view) {
        if (data.getInterviewEntity() != null){
            int type = data.getInterviewEntity().getType();
            if (type == 0){
                toolbar.setTitle("我的约访");
                myInterviewPeople.setText("受访者");
            } else if (type ==1){
                toolbar.setTitle("我被约访");
                myInterviewPeople.setText("约访人");
                myInterviewDetailTwiceInterview.setVisibility(View.GONE);
            }

            int status = data.getInterviewEntity().getStatus();
            if (status == 0){
                myInterviewStatus.setText("联系中");
            }else if (status == 1){
                myInterviewStatus.setText("访谈成功");
            }

            myInterviewCode.setText(data.getInterviewEntity().getCode());
            myInterviewTime.setText(data.getInterviewEntity().getCreateAt());
            myInterviewHead.setText(data.getInterviewEntity().getSubject());
            myInterviewContent.setText(data.getInterviewEntity().getDescription());
            myInterviewName.setText(data.getInterviewEntity().getNickname());
            myInterviewCompany.setText(data.getInterviewEntity().getCompanyNmae());
            myInterviewTitle.setText(data.getInterviewEntity().getAbilityName());

            Glide.with(mActivity).load(data.getInterviewEntity().getAvatar())
                    .crossFade()
//                    .placeholder(R.mipmap.ic_avatar)
                    .transform(new GlideCircleTransform(mActivity))
                    .into(myInterviewDetailAvatar);
        }
        initToolbarNav(toolbar);
        initToolbarMenu(toolbar);
    }

    private void initCommentAdapter() {



        mAdapter = new AskAnswerPageDetailCommentAdapter(data.getCommentEntityList());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mAdapter.openLoadAnimation();
        myInterviewDetailRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                CommentEntity status = (CommentEntity) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.image_ask_profile:
                        content = "img:" + status.getContent();
                        break;
                    case R.id.textView_item_ask_title:
                        content = "name:" + status.getCreateAt();
                        break;
                }
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });

        myInterviewDetailRecyclerView.setLayoutManager(layoutManager);
        myInterviewDetailRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.my_interview_detail_avatar, R.id.my_interview_detail_look, R.id.my_interview_detail_kefu, R.id.my_interview_detail_twiceInterview, R.id.my_interview_detail_ask})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_interview_detail_avatar:
                break;
            case R.id.my_interview_detail_look:
                break;
            case R.id.my_interview_detail_kefu:
                break;
            case R.id.my_interview_detail_twiceInterview:
                break;
            case R.id.my_interview_detail_ask:
                break;
        }
    }
}
