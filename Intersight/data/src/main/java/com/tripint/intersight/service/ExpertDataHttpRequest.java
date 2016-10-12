package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.mine.InterviewDetailEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
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

    private MineDataService service;

    private ExpertDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(MineDataService.class);

    }

    public static ExpertDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new ExpertDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 用于获取他人主页的首页
     *
     * @param subscriber
     */
    public void getHisPersonalPage(Subscriber<UserHomeEntity> subscriber) {
        Observable observable = service.getUserHome()
                .map(new HttpResultFunc<UserHomeEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 个人----他的观点接口
     *
     * @param subscriber
     */
    public void getHisFollowPoint(Subscriber<List<MineFollowPointEntity>> subscriber, int type, int page, int size) {
        if (type == option_type_my) {//他的观点
            Observable observable = service.getMyPointList(page, size)
                    .map(new HttpResultFunc<List<MineFollowPointEntity>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {//他关注的观点
            Observable observable = service.getMyFollowPointList(page, size)
                    .map(new HttpResultFunc<List<MineFollowPointEntity>>());
            toSubscribe(observable, subscriber);
        }
    }



    /**
     * 个人---他的问答接口
     *
     * @param subscriber
     */
    public void getHisAskAnswer(Subscriber<List<DiscussEntiry>> subscriber, int type, int page, int size) {
        if (type == option_type_my) {//问答
            Observable observable = service.getMyAskAnswer(page, size)
                    .map(new HttpResultFunc<List<DiscussEntiry>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {//关注的问答
            Observable observable = service.getMyFocusedAskAnswer(page, size)
                    .map(new HttpResultFunc<List<DiscussEntiry>>());
            toSubscribe(observable, subscriber);
        }
    }

    /**
     * 个人---他的访谈接口
     *
     * @param subscriber
     */
    public void getHisInterview(Subscriber<List<InterviewEntity>> subscriber, int page, int size) {
        Observable observable = service.getMyInterview(page, size)
                .map(new HttpResultFunc<List<InterviewEntity>>());
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
                = service.getInterviewDetail(discussId)
                .map(new HttpResultFunc<InterviewDetailEntity>());
        toSubscribe(observable, subscriber);
    }
}
