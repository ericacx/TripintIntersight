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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.TakePhotoUtil;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.common.widget.filter.ItemModel;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterDoneListener;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterItemClickListener;
import com.tripint.intersight.common.widget.filter.typeview.DoubleListView;
import com.tripint.intersight.common.widget.filter.typeview.SingleListView;
import com.tripint.intersight.common.widget.filter.util.CommonUtil;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.entity.mine.student.QualificationsNameEntity;
import com.tripint.intersight.entity.mine.student.SchoolNameEntity;
import com.tripint.intersight.entity.mine.student.SpecialitiesNameEntity;
import com.tripint.intersight.entity.mine.student.SpecialitiesSubEntity;
import com.tripint.intersight.entity.mine.worker.AbilityNameEntity;
import com.tripint.intersight.entity.mine.worker.AllResoucesEntity;
import com.tripint.intersight.entity.mine.worker.EditUserEntity;
import com.tripint.intersight.entity.mine.worker.IndustryNameEntity;
import com.tripint.intersight.entity.mine.worker.IndustrySubEntity;
import com.tripint.intersight.event.PersonalEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

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
    TextView personalInfoTextviewCompanyname;//公司名称,大学名称

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

    @Bind(R.id.personal_info_avatar)
    CircleImageView personalInfoAvatar;

    @Bind(R.id.personal_info_textview_company_logo)
    TextView personalInfoTextviewCompanyLogo;//公司logo   大学logo
    @Bind(R.id.personal_info_textview_trade)
    TextView personalInfoTextviewTrade;//所在行业、专业名称
    @Bind(R.id.personal_info_textview_position)
    TextView personalInfoTextviewPosition;//所属职能、学历

    @Bind(R.id.personal_info_ll)
    LinearLayout personalInfoLl;
    @Bind(R.id.personal_info_rl_trade)
    RelativeLayout personalInfoRlTrade;

    private CodeDataEntity responseEntity;
    private UserHomeEntity userEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriber;

    private AllResoucesEntity allResoucesEntity; //搜索过滤条件数据

    public static final String ARG_DATA = "arg_user_data";

    private List<SchoolNameEntity> schools = new ArrayList<>();//学校
    private List<SpecialitiesNameEntity> specialities = new ArrayList<>();//专业
    private List<QualificationsNameEntity> qualificationst = new ArrayList<>();//学历

    private List<IndustryNameEntity> industies = new ArrayList<>(); //行业数据
    private List<AbilityNameEntity> abilities = new ArrayList<>(); //职能数据
    private List<ItemModel> work = new ArrayList<>(); //职能数据

    private String[] workArray = {"1年内", "1-3年", "3-5年", "5-10年", "10年以上"}; //排序数据
    private Integer[] workArrayKey = {0, 1, 2, 3, 4}; //排序数据

    private DialogPlus dialogPlus;
    //画面编辑的数据

    private int currentIndestry;//行业
    private int currentAbility;//职能
    private int currentSpecialities;//专业
    private int currentQualifications;//学历

    private OnFilterDoneListener onFilterDoneListener;

    private PageDataSubscriberOnNext<AllResoucesEntity> filterSubscriber;

    private int role;

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
            role = userEntity.getRole();
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
                EventBus.getDefault().post(new PersonalEvent());
                pop();
            }
        };

        filterSubscriber = new PageDataSubscriberOnNext<AllResoucesEntity>() {
            @Override
            public void onNext(AllResoucesEntity entity) {
                //接口请求成功后处理
                allResoucesEntity = entity;
                abilities = allResoucesEntity.getAbilityName();
                industies = allResoucesEntity.getIndustryName();
                specialities = allResoucesEntity.getSpecialitiesName();
                qualificationst = allResoucesEntity.getQualificationsName();
                schools = allResoucesEntity.getSchoolName();
                initView();
            }
        };

        MineDataHttpRequest.getInstance(mActivity).getAllResources(new ProgressSubscriber(filterSubscriber, mActivity), role);
    }

    private void initView() {

        if (role == 1) {//职员
            Glide.with(mActivity).load(userEntity.getAvatar())
                    .crossFade()
                    .fitCenter()
                    .placeholder(R.mipmap.ic_avatar)
                    .into(personalInfoAvatar);
            personalInfoPhone.setText(userEntity.getMobile());
            personalInfoEmail.setText(userEntity.getEmail());
            personalInfoNickname.setText(userEntity.getNickname());
            personalInfoPersonalInfo.setText(userEntity.getDesc());
            personalInfoTextviewCompanyname.setText("公司名称");
            personalInfoTextviewCompanyLogo.setText("公司Logo");
            personalInfoTextviewTrade.setText("所在行业");
            personalInfoTextviewPosition.setText("所属职能");
            personalInfoLl.setVisibility(View.VISIBLE);

            currentAbility = userEntity.getAbilityId();
            currentIndestry = userEntity.getIndustryId();

            personalInfoCompany.setText(userEntity.getCompanyName());
            personalInfoTrade.setText(userEntity.getIndustryName());
            personalInfoPosition.setText(userEntity.getAbilityName());
            personalInfoTitle.setText(userEntity.getJobName());
            personalInfoExperience.setText(userEntity.getExperience());

            Glide.with(mActivity).load(userEntity.getCompanyLogo())
                    .crossFade()
                    .fitCenter()
                    .placeholder(R.mipmap.ic_avatar)
                    .into(personalInfoCompanyLogo);

        } else if (role == 2) {//学生
            Glide.with(mActivity).load(userEntity.getAvatar())
                    .crossFade()
                    .fitCenter()
                    .placeholder(R.mipmap.ic_avatar)
                    .into(personalInfoAvatar);
            personalInfoPhone.setText(userEntity.getMobile());
            personalInfoEmail.setText(userEntity.getEmail());
            personalInfoNickname.setText(userEntity.getNickname());
            personalInfoPersonalInfo.setText(userEntity.getDesc());
            personalInfoTextviewCompanyname.setText("大学名称");
            personalInfoTextviewCompanyLogo.setText("大学Logo");
            personalInfoTextviewTrade.setText("专业");
            personalInfoTextviewPosition.setText("学历");
            personalInfoLl.setVisibility(View.GONE);

            currentQualifications = userEntity.getQualifications();
            currentSpecialities = userEntity.getSpecialitiesId();

            personalInfoCompany.setText(userEntity.getSchoolName());
            personalInfoTrade.setText(userEntity.getSpecialitiesName());
            personalInfoPosition.setText(userEntity.getQualificationsName());

            Glide.with(mActivity).load(userEntity.getCompanyLogo())
                    .crossFade()
                    .fitCenter()
                    .placeholder(R.mipmap.ic_avatar)
                    .into(personalInfoCompanyLogo);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.personal_info_submit, R.id.personal_info_rl_avatar, R.id.personal_info_rl_position, R.id.personal_info_rl_experience, R.id.personal_info_rl_trade})
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

                if (role == 1) {
                    dialogPlus = DialogPlusUtils.Builder(mActivity)
                            .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createSingleListView()))
                            .setTitleName("请选择职能")
                            .setIsHeader(true)
                            .setIsFooter(false)
                            .setIsExpanded(false)
                            .setGravity(Gravity.BOTTOM)
                            .showCompleteDialog();
                } else if (role == 2) {
                    dialogPlus = DialogPlusUtils.Builder(mActivity)
                            .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createStudentSingleListView()))
                            .setTitleName("请选择学历")
                            .setIsHeader(true)
                            .setIsFooter(false)
                            .setIsExpanded(false)
                            .setGravity(Gravity.BOTTOM)
                            .showCompleteDialog();
                }

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
                dialogPlus = DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createExperSingleListView(work)))
                        .setTitleName("请选择工作年限")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.BOTTOM)
                        .showCompleteDialog();
                break;
            case R.id.personal_info_rl_trade:

                if (role == 1) {//职员
                    dialogPlus = DialogPlusUtils.Builder(mActivity)
                            .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createDoubleListView()))
                            .setTitleName("请选择行业")
                            .setIsHeader(true)
                            .setIsFooter(false)
                            .setIsExpanded(false)
                            .setGravity(Gravity.BOTTOM)
                            .showCompleteDialog();
                } else if (role == 2) {//学生
                    dialogPlus = DialogPlusUtils.Builder(mActivity)
                            .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createStudentDoubleListView()))
                            .setTitleName("请选择专业")
                            .setIsHeader(true)
                            .setIsFooter(false)
                            .setIsExpanded(false)
                            .setGravity(Gravity.BOTTOM)
                            .showCompleteDialog();
                }

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
        SimpleTextAdapter adapter = new SimpleTextAdapter<AbilityNameEntity>(null, mActivity) {
            @Override
            public String provideText(AbilityNameEntity item) {
                return item.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
            }
        };
        SingleListView<AbilityNameEntity> singleListView = new SingleListView<>(mActivity)
                .adapter(adapter)
                .onItemClick(new OnFilterItemClickListener<AbilityNameEntity>() {
                    @Override
                    public void onItemClick(AbilityNameEntity item) {
                        currentAbility = item.getId();
                        personalInfoPosition.setText(item.getName());
                        dialogPlus.dismiss();
//                        onFilterDone(type, item.getKey(), item.getName());
                    }
                });

        singleListView.setList(allResoucesEntity.getAbilityName(), -1);

        return singleListView;
    }

    //学历
    private View createStudentSingleListView() {
        SimpleTextAdapter adapter = new SimpleTextAdapter<QualificationsNameEntity>(null, mActivity) {
            @Override
            public String provideText(QualificationsNameEntity item) {
                return item.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
            }
        };
        SingleListView<QualificationsNameEntity> singleListView = new SingleListView<>(mActivity)
                .adapter(adapter)
                .onItemClick(new OnFilterItemClickListener<QualificationsNameEntity>() {
                    @Override
                    public void onItemClick(QualificationsNameEntity item) {
                        currentQualifications = item.getId();
                        personalInfoPosition.setText(item.getName());
                        dialogPlus.dismiss();
//                        onFilterDone(type, item.getKey(), item.getName());
                    }
                });

        singleListView.setList(allResoucesEntity.getQualificationsName(), -1);

        return singleListView;
    }

    //工作年限
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
                        personalInfoExperience.setText(item.getName());
                        dialogPlus.dismiss();
//                        onFilterDone(type, item.getKey(), item.getName());
                    }
                });

        singleListView.setList(list, -1);

        return singleListView;
    }


    //行业
    private View createDoubleListView() {
        DoubleListView<IndustryNameEntity, IndustrySubEntity> comTypeDoubleListView = new DoubleListView<IndustryNameEntity, IndustrySubEntity>(mActivity)
                .leftAdapter(new SimpleTextAdapter<IndustryNameEntity>(null, mActivity) {
                    @Override
                    public String provideText(IndustryNameEntity Industry) {
                        return Industry.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<IndustrySubEntity>(null, mActivity) {
                    @Override
                    public String provideText(IndustrySubEntity s) {
                        return s.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<IndustryNameEntity, IndustrySubEntity>() {
                    @Override
                    public List<IndustrySubEntity> provideRightList(IndustryNameEntity item, int position) {
                        List<IndustrySubEntity> child = item.getIndustrySub();
                        if (CommonUtil.isEmpty(child)) {

//                            onFilterDone(0, item.getId(), item.getName());
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<IndustryNameEntity, IndustrySubEntity>() {
                    @Override
                    public void onRightItemClick(IndustryNameEntity item, IndustrySubEntity childItem) {
                        currentIndestry = childItem.getId();
                        personalInfoTrade.setText(childItem.getName());
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

    //专业
    private View createStudentDoubleListView() {
        DoubleListView<SpecialitiesNameEntity, SpecialitiesSubEntity> comTypeDoubleListView = new DoubleListView<SpecialitiesNameEntity, SpecialitiesSubEntity>(mActivity)
                .leftAdapter(new SimpleTextAdapter<SpecialitiesNameEntity>(null, mActivity) {
                    @Override
                    public String provideText(SpecialitiesNameEntity Industry) {
                        return Industry.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<SpecialitiesSubEntity>(null, mActivity) {
                    @Override
                    public String provideText(SpecialitiesSubEntity s) {
                        return s.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<SpecialitiesNameEntity, SpecialitiesSubEntity>() {
                    @Override
                    public List<SpecialitiesSubEntity> provideRightList(SpecialitiesNameEntity item, int position) {
                        List<SpecialitiesSubEntity> child = item.getSpecialitiesSub();
                        if (CommonUtil.isEmpty(child)) {

                        }
                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<SpecialitiesNameEntity, SpecialitiesSubEntity>() {
                    @Override
                    public void onRightItemClick(SpecialitiesNameEntity item, SpecialitiesSubEntity childItem) {
                        currentSpecialities = childItem.getId();
                        personalInfoTrade.setText(childItem.getName());
                        dialogPlus.dismiss();
                    }
                });


        //初始化选中.
        comTypeDoubleListView.setLeftList(specialities, 1);
        comTypeDoubleListView.setRightList(specialities.get(1).getSpecialitiesSub(), -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mActivity.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }

    private EditUserEntity createEditUserData() {
        EditUserEntity editUserEntity = new EditUserEntity(role);
        if (role == 1) {
            editUserEntity.setType(role);
            editUserEntity.setAvatar("");
            editUserEntity.setMobile(personalInfoPhone.getText().toString());//手机
            editUserEntity.setEmail(personalInfoEmail.getText().toString());//邮箱
            editUserEntity.setNickname(personalInfoNickname.getText().toString());//昵称
            editUserEntity.setCompany(personalInfoCompany.getText().toString());//公司名称
            editUserEntity.setLogo("");
            editUserEntity.setJob(personalInfoTitle.getText().toString());//职位
            editUserEntity.setIndustry_id(currentIndestry);//行业
            editUserEntity.setAbility_id(currentAbility);//职能
            editUserEntity.setExperience(personalInfoExperience.getText().toString());//工作年限
            editUserEntity.setDesc(personalInfoPersonalInfo.getText().toString());//个人简介
            return editUserEntity;
        } else if (role == 2) {
            editUserEntity.setType(role);
            editUserEntity.setAvatar("");
            editUserEntity.setMobile(personalInfoPhone.getText().toString());//手机
            editUserEntity.setEmail(personalInfoEmail.getText().toString());//邮箱
            editUserEntity.setNickname(personalInfoNickname.getText().toString());//昵称
            editUserEntity.setSchool(personalInfoCompany.getText().toString());//学校
            editUserEntity.setLogo("");
            editUserEntity.setSpecialities(currentSpecialities);
            editUserEntity.setQualifications(currentQualifications);
            editUserEntity.setDesc(personalInfoPersonalInfo.getText().toString());
            return editUserEntity;
        }
        return editUserEntity;
    }

}
