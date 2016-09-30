package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.mine.FocusEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;

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

    private MineDataService service;

    private MineDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(MineDataService.class);

    }

    private static MineDataHttpRequest instants;


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
     * 用于获取观点页面的banner
     *
     * @param subscriber
     */
    public void getUserHome(Subscriber<UserHomeEntity> subscriber) {
        Observable observable = service.getUserHome()
                .map(new HttpResultFunc<UserHomeEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 个人----我的观点接口
     *
     * @param subscriber
     */
    public void getMyFollowPoint(Subscriber<List<MineFollowPointEntity>> subscriber, int type, int page, int size) {
        if (type == option_type_my) {
            Observable observable = service.getMyPointList(page, size)
                    .map(new HttpResultFunc<List<MineFollowPointEntity>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {
            Observable observable = service.getMyFollowPointList(page, size)
                    .map(new HttpResultFunc<List<MineFollowPointEntity>>());
            toSubscribe(observable, subscriber);
        }
    }

    /**
     * 个人---我的关注接口
     *
     * @param subscriber
     */
    public void getMyFocus(Subscriber<List<FocusEntity>> subscriber, int type, int page, int size) {
        if (type == option_type_my) {//关注
            Observable observable = service.getMyFollow(page, size)
                    .map(new HttpResultFunc<List<FocusEntity>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {//被关注
            Observable observable = service.getMyByFollow(page, size)
                    .map(new HttpResultFunc<List<FocusEntity>>());
            toSubscribe(observable, subscriber);
        }
    }


    /**
     * 个人---我的问答接口
     *
     * @param subscriber
     */
    public void getMyAskAnswer(Subscriber<List<DiscussEntiry>> subscriber, int type, int page, int size) {
        if (type == option_type_my) {//关注
            Observable observable = service.getMyAskAnswer(page, size)
                    .map(new HttpResultFunc<List<DiscussEntiry>>());
            toSubscribe(observable, subscriber);
        } else if (type == option_type_my_follow) {//被关注
            Observable observable = service.getMyFocusedAskAnswer(page, size)
                    .map(new HttpResultFunc<List<DiscussEntiry>>());
            toSubscribe(observable, subscriber);
        }
    }

    /**
     * 个人---我的访谈接口
     *
     * @param subscriber
     */
    public void getMyInterview(Subscriber<List<InterviewEntity>> subscriber, int page, int size) {
        Observable observable = service.getMyInterview(page, size)
                .map(new HttpResultFunc<List<InterviewEntity>>());
        toSubscribe(observable, subscriber);

    }


}
