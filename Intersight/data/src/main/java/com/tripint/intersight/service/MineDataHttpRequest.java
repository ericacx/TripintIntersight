package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.PersonalUserInfoEntity;
import com.tripint.intersight.entity.discuss.CreateInterviewResponseEntity;
import com.tripint.intersight.entity.mine.AccountDetailEntity;
import com.tripint.intersight.entity.mine.AskAnswerEntity;
import com.tripint.intersight.entity.mine.FocusEntity;
import com.tripint.intersight.entity.mine.InterviewDetailEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.entity.mine.PersonalUserHomeEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.entity.mine.worker.AllResoucesEntity;
import com.tripint.intersight.entity.mine.worker.EditUserEntity;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 个人中心接口
 * Created by lirichen on 2016/9/21.
 */

public class MineDataHttpRequest extends HttpRequest {

    private final int option_type_my = 0;

    private final int option_type_my_follow = 1;

    private static MineDataHttpRequest instants;

    private MineDataService service;

    private MineDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(MineDataService.class);

    }


    public static MineDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new MineDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 提交用户反馈信息
     */
    public void postFeedback(Subscriber<List<String>> subscriber, String content) {

        Observable observable = service.postFeedback(content)
                .map(new HttpResultFunc<List<String>>());


        toSubscribe(observable, subscriber);
    }

    /**
     * 用于获取个人中心主页
     *
     * @param subscriber
     */
    public void getUserHome(Subscriber<UserHomeEntity> subscriber) {
        Observable observable = service.getUserHome()
                .map(new HttpResultFunc<UserHomeEntity>());
        toSubscribe(observable, subscriber);
    }


    /**
     * 用于获取向他提问的接口
     *
     * @param subscriber
     */
    public void postOtherQuestion(Subscriber<CodeDataEntity> subscriber,int from_uid,String content) {
        Observable observable = service.postOtherQuestion(from_uid,content)
                .map(new HttpResultFunc<CodeDataEntity>());
        toSubscribe(observable, subscriber);
    }


    /**
     * 用于获取约他访谈的接口
     *
     * @param subscriber
     */
    public void postOtherInterview(Subscriber<CreateInterviewResponseEntity> subscriber
            ,PersonalUserInfoEntity personalUserInfo) {
        Observable observable = service.postOtherInterview(personalUserInfo)
                .map(new HttpResultFunc<CreateInterviewResponseEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 个人----我的观点接口
     *
     * @param subscriber
     */
    public void getMyFollowPoint(Subscriber<BasePageableResponse<MineFollowPointEntity>> subscriber, int type, int page) {
        if (type == option_type_my) {
            Observable observable = service.getMyPointList(page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<MineFollowPointEntity>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {
            Observable observable = service.getMyFollowPointList(page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<MineFollowPointEntity>>());
            toSubscribe(observable, subscriber);
        }
    }

    /**
     * 个人---我的关注接口
     *
     * @param subscriber
     */
    public void getMyFocus(Subscriber<List<FocusEntity>> subscriber, int type, int page) {
        if (type == option_type_my) {//关注
            Observable observable = service.getMyFollow(page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<FocusEntity>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {//被关注
            Observable observable = service.getMyByFollow(page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<FocusEntity>>());
            toSubscribe(observable, subscriber);
        }
    }


    /**
     * 个人---我的问答接口
     *
     * @param subscriber
     */
    public void getMyAskAnswer(Subscriber<BasePageableResponse<AskAnswerEntity>> subscriber, int type, int page) {
        if (type == option_type_my) {//关注
            Observable observable = service.getMyAskAnswer(page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<AskAnswerEntity>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {//被关注
            Observable observable = service.getMyFocusedAskAnswer(page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<AskAnswerEntity>>());
            toSubscribe(observable, subscriber);
        }
    }

    /**
     * 个人---我的访谈接口
     *
     * @param subscriber
     */
    public void getMyInterview(Subscriber<BasePageableResponse<InterviewEntity>> subscriber, int page) {
        Observable observable = service.getMyInterview(page, DEFAULT_PAGE_SIZE)
                .map(new HttpResultFunc<BasePageableResponse<InterviewEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 个人---账户明细接口
     *
     * @param subscriber
     */
    public void getAccountDetail(Subscriber<BasePageableResponse<AccountDetailEntity>> subscriber, int page) {
        Observable observable = service.getAccountDetail(page, DEFAULT_PAGE_SIZE)
                .map(new HttpResultFunc<BasePageableResponse<AccountDetailEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 用得我的访谈详情数据。
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param
     */
    public void getInterviewDetail(Subscriber<InterviewDetailEntity> subscriber, int discussId) {

        Observable observable
                = service.getInterviewDetail(discussId)
                .map(new HttpResultFunc<InterviewDetailEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 修改手机
     * @param subscriber
     * @param mobile
     * @param code
     */
    public void postChangeMobile(Subscriber<CodeDataEntity> subscriber, String mobile,int code){
        Observable observable = service.postChangeMobile(mobile,code)
                .map(new HttpResultFunc<CodeDataEntity>());

        toSubscribe(observable,subscriber);
    }

    /**
     * 修改邮箱
     * @param subscriber
     * @param email
     * @param code
     */
    public void postChangeEmail(Subscriber<CodeDataEntity> subscriber, String email,int code){
        Observable observable = service.postChangeEmail(email,code)
                .map(new HttpResultFunc<CodeDataEntity>());

        toSubscribe(observable,subscriber);
    }

    /**
     * 修改密码
     * @param subscriber
     * @param oldPassword
     * @param password
     * @param repassword
     */
    public void postChangePassword(Subscriber<CodeDataEntity> subscriber, String oldPassword,String password,String repassword){
        Observable observable = service.postChangePassword(oldPassword,password,repassword)
                .map(new HttpResultFunc<CodeDataEntity>());

        toSubscribe(observable,subscriber);
    }

    /**
     * 修改个人资料
     * @param subscriber
     * @param editUserEntity
     */
    public void postEditUser(Subscriber<CodeDataEntity> subscriber, EditUserEntity editUserEntity){
        Observable observable = service.postEditUser(editUserEntity)
                .map(new HttpResultFunc<CodeDataEntity>());

        toSubscribe(observable,subscriber);
    }


    /***
     * 用于获取邮箱验证码数据
     * @param subscriber
     * @param email
     */
    public void getEmailCode(Subscriber<CodeDataEntity> subscriber, String email){

        Observable observable = service.getSendEmail(email)
                .map(new HttpResultFunc<CodeDataEntity>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 用得职员，学生信息。
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param
     */
    public void getAllResources(Subscriber<AllResoucesEntity> subscriber, int type) {

        Observable observable = service.getAllResources(type)
                .map(new HttpResultFunc<AllResoucesEntity>());
        toSubscribe(observable, subscriber);
    }
}
