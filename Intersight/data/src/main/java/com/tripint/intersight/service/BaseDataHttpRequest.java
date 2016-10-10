package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.ForgetPasswordEntity;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.entity.UserInfoEntity;
import com.tripint.intersight.entity.article.ArticleBannerEntity;
import com.tripint.intersight.entity.common.BannerEntity;
import com.tripint.intersight.entity.user.ChooseEntity;
import com.tripint.intersight.entity.user.LoginEntity;
import com.tripint.intersight.entity.user.RegisterEntity;
import com.tripint.intersight.entity.user.User;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lirichen on 2016/9/21.
 */

public class BaseDataHttpRequest extends HttpRequest {

    private final String FLITER_TYPE_INTERVIEWER = "interviewer";
    private final String FLITER_TYPE_ARTICLES = "articles";

    private BaseDataService baseDataService;

    private BaseDataHttpRequest(Context context) {
        super(context);
        baseDataService = retrofit.create(BaseDataService.class);

    }

    private static BaseDataHttpRequest instants;


    public static BaseDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new BaseDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 用于取得行业数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getIndustry(Subscriber<List<Industry>> subscriber, int start, int count) {

        Observable observable = baseDataService.getIndustry(start, count)
                .map(new HttpResultFunc<List<Industry>>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 用于获取搜索的过滤条件数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getSearchFilterInterviewer(Subscriber<SearchFilterEntity> subscriber) {

        Observable observable = baseDataService.getSearchFilter(FLITER_TYPE_INTERVIEWER)
                .map(new HttpResultFunc<SearchFilterEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 用于获取搜索的过滤条件数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getSearchFilterArticles(Subscriber<SearchFilterEntity> subscriber) {

        Observable observable = baseDataService.getSearchFilter(FLITER_TYPE_ARTICLES)
                .map(new HttpResultFunc<SearchFilterEntity>());

        toSubscribe(observable, subscriber);
    }

    /***
     * 用于获取登录数据
     * @param subscriber
     */
    public void postLogin(Subscriber<LoginEntity> subscriber, User user){

        Observable observable = baseDataService.postLogin(user)
                .map(new HttpResultFunc<LoginEntity>());

        toSubscribe(observable, subscriber);
    }


//    public void getLogin(Subscriber<LoginEntity.UserInfoBean> subscriber, String email,String password){
//        Observable observable = baseDataService.getLogin(email,password)
//                .map(new HttpResultFunc<LoginEntity.UserInfoBean>());
//
//        toSubscribe(observable, subscriber);
//    }

    /***
     * 用于获取注册数据
     * @param subscriber
     */
    public void postRegister(Subscriber<RegisterEntity> subscriber, User user){

        Observable observable = baseDataService.postRegister(user)
                .map(new HttpResultFunc<RegisterEntity>());

        toSubscribe(observable, subscriber);
    }

    /***
     * 用于获取手机验证码数据
     * @param subscriber
     */
    public void getCode(Subscriber<CodeDataEntity> subscriber, String mobile){

        Observable observable = baseDataService.getCode(mobile)
                .map(new HttpResultFunc<CodeDataEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 用于获取忘记密码数据
     * @param subscriber
     * @param mobile
     */
    public void postForgetpassword(Subscriber<ForgetPasswordEntity> subscriber, String mobile){

        Observable observable = baseDataService.postForgetpassword(mobile)
                .map(new HttpResultFunc<ForgetPasswordEntity>());

        toSubscribe(observable, subscriber);
    }

    public void postResetpassword(Subscriber<CodeDataEntity> subscriber,User user){
        Observable observable = baseDataService.postResetpassword(user)
                .map(new HttpResultFunc<CodeDataEntity>());

        toSubscribe(observable,subscriber);
    }
    /**
     * 用于获取个人信息数据
     * @param subscriber
     * @param userInfoEntity
     */
    public void postUserInfo(Subscriber<UserInfoEntity> subscriber, UserInfoEntity userInfoEntity){

        Observable observable = baseDataService.postUserInfo(userInfoEntity)
                .map(new HttpResultFunc<UserInfoEntity>());

        toSubscribe(observable, subscriber);
    }

    /***
     * 用于获取选择角色数据
     * @param subscriber
     */
    public void postChooseRole(Subscriber<ChooseEntity> subscriber, String role){

        Observable observable = baseDataService.postChooseRole(role)
                .map(new HttpResultFunc<ChooseEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 用于获取选择关注的行业数据
     * @param subscriber
     * @param strings
     */
    public void postInsterestIndustry(Subscriber<ChooseEntity> subscriber, String strings){
        Observable observable = baseDataService.postInterestIndustry(strings)
                .map(new HttpResultFunc<ChooseEntity>());
        toSubscribe(observable,subscriber);
    }


    /**
     * 用于获取观点页面的banner
     * @param subscriber
     * @param type
     */
    public void getArticleBanner(Subscriber<ArticleBannerEntity> subscriber,int type){
        Observable observable = baseDataService.getArticleBanner(type)
                .map(new HttpResultFunc<ArticleBannerEntity>());
        toSubscribe(observable,subscriber);
    }

    /**
     * 用于获取观点页面的banner
     * @param subscriber
     * @param type
     */
    public void getBanner(Subscriber<ArticleBannerEntity> subscriber, int type){
        Observable observable = baseDataService.getBanner(type)
                .map(new HttpResultFunc<ArticleBannerEntity>());
        toSubscribe(observable,subscriber);
    }
}
