package com.tripint.intersight.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.TakePhotoUtil;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.common.widget.filter.ItemModel;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterItemClickListener;
import com.tripint.intersight.common.widget.filter.typeview.DoubleListView;
import com.tripint.intersight.common.widget.filter.typeview.SingleListView;
import com.tripint.intersight.common.widget.filter.util.CommonUtil;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.entity.Ability;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.IndustryChild;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.entity.mine.worker.AllResoucesEntity;
import com.tripint.intersight.entity.mine.worker.EditUserEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑个人资料
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personal_info_submit)
    Button personalInfoSubmit;//提交

    //    @Bind(R.id.personal_info_next_one)
//    ImageView personalInfoNextOne;
    @Bind(R.id.personal_info_avatar)
    CircleImageView personalInfoAvatar;
    @Bind(R.id.personal_info_rl_avatar)
    RelativeLayout personalInfoRlAvatar;//头像

    @Bind(R.id.personal_info_text_view_phone)
    TextView personalInfoTextViewPhone;
    @Bind(R.id.personal_info_phone)
    EditText personalInfoPhone;//手机

    @Bind(R.id.personal_info_textview_email)
    TextView personalInfoTextviewEmail;
    @Bind(R.id.personal_info_email)
    EditText personalInfoEmail;//邮箱

    @Bind(R.id.personal_info_textview_nickname)
    TextView personalInfoTextviewNickname;
    @Bind(R.id.personal_info_nickname)
    EditText personalInfoNickname;//昵称

    @Bind(R.id.personal_info_textview_companyname)
    TextView personalInfoTextviewCompanyname;
    @Bind(R.id.personal_info_company)
    EditText personalInfoCompany;//公司名称

    @Bind(R.id.personal_info_next)
    ImageView personalInfoNext;
    @Bind(R.id.personal_info_company_logo)
    CircleImageView personalInfoCompanyLogo;//公司logo

    @Bind(R.id.personal_info_trade_down)
    ImageView personalInfoTradeDown;
    @Bind(R.id.personal_info_trade)
    TextView personalInfoTrade;//行业

    @Bind(R.id.personal_info_title_down)
    ImageView personalInfoTitleDown;
    @Bind(R.id.personal_info_position)
    TextView personalInfoPosition;
    @Bind(R.id.personal_info_rl_position)
    RelativeLayout personalInfoRlPosition;//职能

    @Bind(R.id.personal_info_textview_title)
    TextView personalInfoTextviewTitle;
    @Bind(R.id.personal_info_title)
    EditText personalInfoTitle;//职位

    @Bind(R.id.personal_info_experience_down)
    ImageView personalInfoExperienceDown;
    @Bind(R.id.personal_info_experience)
    TextView personalInfoExperience;
    @Bind(R.id.personal_info_rl_experience)
    RelativeLayout personalInfoRlExperience;//工作年限

    @Bind(R.id.personal_info_personalInfo)
    EditText personalInfoPersonalInfo;//个人简介

    private CodeDataEntity responseEntity;
    private UserHomeEntity userEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriber;
    private PageDataSubscriberOnNext<SearchFilterEntity> filterSubscriber;

    private SearchFilterEntity searchFilterEntity; //搜索过滤条件数据


    public static final String ARG_DATA = "arg_user_data";

    private List<Industry> industies = new ArrayList<>(); //行业数据
    private List<Ability> abilities = new ArrayList<>(); //职能数据
    private List<ItemModel> work = new ArrayList<>(); //职能数据

    private String[] workArray = {"1年内", "1-3年", "3-5年", "5-10年", "10年以上"}; //排序数据
    private Integer[] workArrayKey = {0, 1, 2, 3, 4}; //排序数据

    //画面编辑的数据

    private int currentIndestry;//行业
    private int currentAbility;//职能
    private int currentSpecialities;//专业
    private int currentQualifications;//学历


    private PageDataSubscriberOnNext<AllResoucesEntity> subscriberAllResource;

    public static PersonalInfoFragment newInstance(UserHomeEntity entity) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, entity);
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userEntity = (UserHomeEntity) bundle.getSerializable(ARG_DATA);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        ButterKnife.bind(this, view);

        initToolbarNav(toolbar);
        toolbar.setTitle("个人资料");
        httpRequestData();
        return view;
    }

    private void httpRequestData() {
        //完成按钮
        subscriber = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                responseEntity = entity;
                pop();
            }
        };

        filterSubscriber = new PageDataSubscriberOnNext<SearchFilterEntity>() {
            @Override
            public void onNext(SearchFilterEntity entity) {
                //接口请求成功后处理
                searchFilterEntity = entity;
                abilities = searchFilterEntity.getAbility();
                industies = searchFilterEntity.getIndustry();
                initView();
            }
        };

        BaseDataHttpRequest.getInstance(mActivity).getSearchFilterArticles(new ProgressSubscriber(filterSubscriber, mActivity));
    }

    private void initView() {


        Glide.with(mActivity).load(userEntity.getAvatar())
                .crossFade()
                .fitCenter()
                .placeholder(R.mipmap.ic_avatar)
                .into(personalInfoAvatar);
        currentAbility = userEntity.getAbilityId();
        currentIndestry = userEntity.getIndustryId();
        currentQualifications = userEntity.getQualifications();
        currentSpecialities = userEntity.getSpecialitiesId();
        personalInfoPhone.setText(userEntity.getMobile());
        personalInfoEmail.setText(userEntity.getEmail());
        personalInfoNickname.setText(userEntity.getNickname());
        personalInfoCompany.setText(userEntity.getCompanyName());
        personalInfoTrade.setText(userEntity.getIndustryName());
        personalInfoPosition.setText(userEntity.getAbilityName());
        personalInfoTitle.setText(userEntity.getJobName());
        personalInfoExperience.setText(userEntity.getExperience());
        personalInfoPersonalInfo.setText(userEntity.getDesc());

        Glide.with(mActivity).load(userEntity.getAvatar())
                .crossFade()
                .fitCenter()
                .placeholder(R.mipmap.ic_avatar)
                .into(personalInfoCompanyLogo);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.personal_info_submit, R.id.personal_info_rl_avatar, R.id.personal_info_rl_position, R.id.personal_info_rl_experience, R.id.personal_info_trade})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_info_submit://提交

                EditUserEntity editUserEntity = createEditUserData();
                MineDataHttpRequest
                        .getInstance(mActivity)
                        .postEditUser(new ProgressSubscriber<CodeDataEntity>(subscriber, mActivity), editUserEntity);

                break;
            case R.id.personal_info_rl_avatar://头像

                break;
            case R.id.personal_info_rl_position://职能

                DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createSingleListView()))
                        .setTitleName("请选择职能")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.BOTTOM)
                        .showCompleteDialog();
                break;
            case R.id.personal_info_rl_experience://工作年限

                int i = 0;
                for (String itemName : workArray) {
                    ItemModel itemModel = new ItemModel();
                    itemModel.setKey(workArrayKey[i]);
                    itemModel.setName(itemName);
                    work.add(itemModel);
                    i++;
                }
                DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createExperSingleListView(work)))
                        .setTitleName("请选择支付方式")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.BOTTOM)
                        .showCompleteDialog();
                break;
            case R.id.personal_info_trade:
                DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createDoubleListView()))
                        .setTitleName("请选择行业")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.BOTTOM)
                        .showCompleteDialog();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = TakePhotoUtil.dealActivityResult(this, requestCode, resultCode, data, true);
        personalInfoAvatar.setImageBitmap(bitmap);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //职能
    private View createSingleListView() {
        SimpleTextAdapter adapter = new SimpleTextAdapter<Ability>(null, mActivity) {
            @Override
            public String provideText(Ability item) {
                return item.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
            }
        };
        SingleListView<Ability> singleListView = new SingleListView<>(mActivity)
                .adapter(adapter)
                .onItemClick(new OnFilterItemClickListener<Ability>() {
                    @Override
                    public void onItemClick(Ability item) {

//                        onFilterDone(type, item.getKey(), item.getName());
                    }
                });

        singleListView.setList(searchFilterEntity.getAbility(), -1);

        return singleListView;
    }

    //职能
    private View createExperSingleListView(List<ItemModel> list) {
        SimpleTextAdapter adapter = new SimpleTextAdapter<ItemModel>(null, mActivity) {
            @Override
            public String provideText(ItemModel item) {
                return item.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
            }
        };
        SingleListView<ItemModel> singleListView = new SingleListView<>(mActivity)
                .adapter(adapter)
                .onItemClick(new OnFilterItemClickListener<ItemModel>() {
                    @Override
                    public void onItemClick(ItemModel item) {

//                        onFilterDone(type, item.getKey(), item.getName());
                    }
                });

        singleListView.setList(list, -1);

        return singleListView;
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

//                        onFilterDone(0, childItem.getId(), childItem.getName());
                    }
                });


        //初始化选中.
        comTypeDoubleListView.setLeftList(industies, 1);
        comTypeDoubleListView.setRightList(industies.get(1).getIndustrySub(), -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mActivity.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }

    private EditUserEntity createEditUserData() {
        EditUserEntity editUserEntity = new EditUserEntity(EditUserEntity.USER_TYPE_STUDENT);
        editUserEntity.setMobile(personalInfoPhone.getText().toString());
        editUserEntity.setEmail(personalInfoEmail.getText().toString());
        editUserEntity.setNickname(personalInfoNickname.getText().toString());
        editUserEntity.setCompany(personalInfoCompany.getText().toString());
        editUserEntity.setExperience(personalInfoTrade.getText().toString());

//        editUserEntity.setSpecialities(personalInfoPosition.getText().toString());
        editUserEntity.setJob(personalInfoTitle.getText().toString());
        editUserEntity.setExperience(personalInfoExperience.getText().toString());
        editUserEntity.setDesc(personalInfoPersonalInfo.getText().toString());
        editUserEntity.setSpecialities(currentSpecialities);
        editUserEntity.setQualifications(currentQualifications);
        editUserEntity.setAbility_id(currentAbility);
        editUserEntity.setIndustry_id(currentIndestry);
        return editUserEntity;
    }
}
