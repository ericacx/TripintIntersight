package com.tripint.intersight.fragment.create;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.typeview.DoubleListView;
import com.tripint.intersight.common.widget.filter.util.CommonUtil;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.IndustryChild;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateDiscussFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.dialog_question_edit)
    EditText dialogQuestionEdit;
    @Bind(R.id.choose_industry)
    TextView chooseIndustry;
    @Bind(R.id.create_discuss_ll_choose)
    LinearLayout createDiscussLlChoose;
    @Bind(R.id.create_discuss_send)
    Button createDiscussSend;

    private List<Industry> industies = new ArrayList<>(); //行业数据
    private int currentIndestry;//行业

    private DialogPlus dialogPlus;

    private SearchFilterEntity searchFilterEntity; //搜索过滤条件数据

    private PageDataSubscriberOnNext<SearchFilterEntity> subscriber;

    public static CreateDiscussFragment newInstance() {
        // Required empty public constructor
        CreateDiscussFragment fragment = new CreateDiscussFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_discuss, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        httpRequestData();
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<SearchFilterEntity>() {
            @Override
            public void onNext(SearchFilterEntity entity) {
                //接口请求成功后处理
                searchFilterEntity = entity;
                industies = searchFilterEntity.getIndustry();
            }
        };

        BaseDataHttpRequest.getInstance(mActivity).getSearchFilterArticles(new ProgressSubscriber(subscriber, mActivity));
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("向他提问");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.create_discuss_ll_choose, R.id.create_discuss_send})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_discuss_ll_choose:
                dialogPlus = DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createDoubleListView()))
                        .setTitleName("请选择行业")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.TOP)
                        .showCompleteDialog();
                break;
            case R.id.create_discuss_send:

                break;
        }

    }

    //行业
    private View createDoubleListView() {
        DoubleListView<Industry, IndustryChild> comTypeDoubleListView = new DoubleListView<Industry, IndustryChild>(mActivity)
                .leftAdapter(new SimpleTextAdapter<Industry>(null, mActivity) {
                    @Override
                    public String provideText(Industry Industry) {
                        return Industry.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<IndustryChild>(null, mActivity) {
                    @Override
                    public String provideText(IndustryChild s) {
                        return s.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<Industry, IndustryChild>() {
                    @Override
                    public List<IndustryChild> provideRightList(Industry item, int position) {
                        List<IndustryChild> child = item.getIndustrySub();
                        if (CommonUtil.isEmpty(child)) {

//                            onFilterDone(0, item.getId(), item.getName());
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<Industry, IndustryChild>() {
                    @Override
                    public void onRightItemClick(Industry item, IndustryChild childItem) {
                        currentIndestry = childItem.getId();
                        chooseIndustry.setText(childItem.getName());
                        dialogPlus.dismiss();
//                        onFilterDone(0, childItem.getId(), childItem.getName());
                    }
                });


        //初始化选中.
        comTypeDoubleListView.setLeftList(industies, 1);
        comTypeDoubleListView.setRightList(industies.get(1).getIndustrySub(), -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mActivity.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }

}
