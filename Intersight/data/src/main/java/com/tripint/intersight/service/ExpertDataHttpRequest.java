package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.mine.AskAnswerEntity;
import com.tripint.intersight.entity.mine.InterviewDetailEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.entity.mine.PersonalUserHomeEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 他的个人中心接口
 * Created by Eric on 16/10/11.
 */

public class ExpertDataHttpRequest extends HttpRequest{

    private final int option_type_my = 0;

    private final int option_type_my_follow = 1;

    private static ExpertDataHttpRequest instants;

    private ExpertDataService service;

    private ExpertDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(ExpertDataService.class);

    }

    public static ExpertDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new ExpertDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 用于获取他的个人中心主页
     *
     * @param subscriber
     */
    public void getPersonalUserHome(Subscriber<PersonalUserHomeEntity> subscriber, int uid) {
        Observable observable = service.getPersonalUserHome(uid)
                .map(new HttpResultFunc<PersonalUserHomeEntity>());
        toSubscribe(observable, subscriber);
    }
    /**
     * 个人----他的观点接口
     *
     * @param subscriber
     */
    public void getHisFollowPoint(Subscriber<BasePageableResponse<MineFollowPointEntity>> subscriber, int type, int uid,int page) {
        if (type == option_type_my) {//他的观点
            Observable observable = service.getHisPointList(uid,page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<MineFollowPointEntity>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {//他关注的观点
            Observable observable = service.getHisFollowPointList(uid,page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<MineFollowPointEntity>>());
            toSubscribe(observable, subscriber);
        }
    }



    /**
     * 个人---他的问答接口
     *
     * @param subscriber
     */
    public void getHisAskAnswer(Subscriber<BasePageableResponse<AskAnswerEntity>> subscriber, int type, int uid, int page) {
        if (type == option_type_my) {//问答
            Observable observable = service.getHisAskAnswer(uid,page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<AskAnswerEntity>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {//关注的问答
            Observable observable = service.getHisFocusedAskAnswer(uid,page, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<AskAnswerEntity>>());
            toSubscribe(observable, subscriber);
        }
    }

    /**
     * 个人---他的访谈接口
     *
     * @param subscriber
     */
    public void getHisInterview(Subscriber<BasePageableResponse<InterviewEntity>> subscriber, int uid,int page) {
        Observable observable = service.getHisInterview(uid,page, DEFAULT_PAGE_SIZE)
                .map(new HttpResultFunc<BasePageableResponse<InterviewEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 用得他的访谈详情数据。
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param
     */
    public void getHisInterviewDetail(Subscriber<InterviewDetailEntity> subscriber, int discussId) {

        Observable observable
                = service.getHisInterviewDetail(discussId)
                .map(new HttpResultFunc<InterviewDetailEntity>());
        toSubscribe(observable, subscriber);
    }
}
