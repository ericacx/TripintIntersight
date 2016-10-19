package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.entity.SearchArticleEntity;
import com.tripint.intersight.entity.discuss.CommentResultEntity;
import com.tripint.intersight.entity.discuss.CreateDiscussResponseEntity;
import com.tripint.intersight.entity.discuss.DiscussDetailEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.discuss.InterviewEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lirichen on 2016/9/21.
 */

public class DiscussDataHttpRequest extends HttpRequest {

    private final String FLITER_TYPE_INTERVIEWER = "interviewer";
    private final String FLITER_TYPE_ARTICLES = "articles";

    public static final String ACTION_CREATE = "create";
    public static final String ACTION_DELETE = "delete";

    public static final String TYPE_COMMENT = "comment";
    public static final String TYPE_SUB_COMMENT = "subcomment";
    public static final String TYPE_PRAISES = "praises";
    public static final String TYPE_UNPRAISES = "unpraises";
    public static final String TYPE_FOLLOW = "follows";
    public static final String TYPE_UNFOLLOW = "unfollows";
    public static final String TYPE_REPORT = "report"; //举报
//    'action':'create',
//            'type':'comment',
//            'content':'评论的内容'
//
//            // 创建二级评论
//            'action':'create',
//            'type':'comment',
//            'content':'评论的内容',
//            'commentId':1 // 上级评论的Id
//            'toNickname':'刘进' // 上级评论的用户昵称
//
//            // 点赞
//            'action':'create',
//            'type':'praises'
//
//            // 取消点赞
//            'action':'delete',
//            'type':'unpraises'
//
//            // 关注
//            'action':'create',
//            'type':'follows'
//
//            // 取消关注
//            'type':'unfollows'
//            'action':'delete',

    private DiscussDataService service;

    private DiscussDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(DiscussDataService.class);

    }

    private static DiscussDataHttpRequest instants;


    public static DiscussDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new DiscussDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 用于取得行业数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param tab        0 问答首页接口  1,我关注的问答  2,精选
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getDiscusses(Subscriber<BasePageableResponse<DiscussEntiry>> subscriber, int tab, int start, int count) {

        Observable observable = null;
        if (tab == 0) {
            observable = service.getDiscuss(start, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());
        } else if (tab == 1) {
            observable = service.getDiscuss(start, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());
        } else if (tab == 2) {
            observable = service.getDiscuss(start, DEFAULT_PAGE_SIZE)
                    .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());
        }

        toSubscribe(observable, subscriber);
    }

    /**
     * 用得问答详情数据。
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param
     */
    public void getDiscussDetail(Subscriber<DiscussDetailEntity> subscriber, int discussId) {

        Observable observable
                = service.getDiscussDetail(discussId)
                .map(new HttpResultFunc<DiscussDetailEntity>());


        toSubscribe(observable, subscriber);
    }

    /**
     * 用于取得行业数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getDiscusses(Subscriber<BasePageableResponse<DiscussEntiry>> subscriber, int start, int count) {

        Observable observable = service.getDiscuss(start, DEFAULT_PAGE_SIZE)
                .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());


        toSubscribe(observable, subscriber);
    }

    /**
     * 创建问答评论
     * */
    public void createComment(Subscriber<CommentResultEntity> subscriber, int disscussId, String content) {

        Observable observable = service.createComment(disscussId, ACTION_CREATE, TYPE_COMMENT, content)
                .map(new HttpResultFunc<CommentResultEntity>());


        toSubscribe(observable, subscriber);
    }


    /**
     * 创建问答子评论
     * */
    public void createSubComment(Subscriber<CommentResultEntity> subscriber, int disscussId, String content, int commentId, String nickName) {

        Observable observable = service.createSubComment(disscussId, ACTION_CREATE, TYPE_COMMENT, content, commentId, nickName)
                .map(new HttpResultFunc<CommentResultEntity>());


        toSubscribe(observable, subscriber);
    }


    /**
     * 创建问答赞
     * */
    public void createParises(Subscriber<CommentResultEntity> subscriber, int disscussId) {

        Observable observable = service.createPraises(disscussId, ACTION_CREATE, TYPE_PRAISES)
                .map(new HttpResultFunc<CommentResultEntity>());


        toSubscribe(observable, subscriber);
    }

    /**
     * 取消问答赞
     * */
    public void deleteParises(Subscriber<CommentResultEntity> subscriber, int disscussId) {

        Observable observable = service.deletePraises(disscussId, ACTION_DELETE, TYPE_UNPRAISES)
                .map(new HttpResultFunc<CommentResultEntity>());


        toSubscribe(observable, subscriber);
    }

    /**
     * 创建问答关注
     * */
    public void createFollow(Subscriber<CommentResultEntity> subscriber, int disscussId) {

        Observable observable = service.createFollow(disscussId, ACTION_CREATE, TYPE_FOLLOW)
                .map(new HttpResultFunc<CommentResultEntity>());


        toSubscribe(observable, subscriber);
    }

    /**
     * 取消问答关注
     * */
    public void deleteFollow(Subscriber<CommentResultEntity> subscriber, int disscussId) {

        Observable observable = service.deleteFollow(disscussId, ACTION_CREATE, TYPE_UNFOLLOW)
                .map(new HttpResultFunc<CommentResultEntity>());


        toSubscribe(observable, subscriber);
    }


    /**
     * 用于搜索画面 找人画面
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param start      起始位置
     */
    public void searchSpecialLists(Subscriber<BasePageableResponse<InterviewEntity>> subscriber, int start, String industry,
                                   String position,
                                   String company) {

        Observable observable = service.getInterview(start, DEFAULT_PAGE_SIZE, industry, position, company)
                .map(new HttpResultFunc<BasePageableResponse<InterviewEntity>>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 搜索文章内容
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param start      起始位置
     */
    public void searchArticles(Subscriber<BasePageableResponse<SearchArticleEntity>> subscriber, int start, String keyword, String industry,
                               String ability,
                               String order) {

        Observable observable = service.getSearchArticles(start, DEFAULT_PAGE_SIZE, keyword, industry, ability, order)
                .map(new HttpResultFunc<BasePageableResponse<SearchArticleEntity>>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 提交问答
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param
     */
    public void createDiscusses(Subscriber<CreateDiscussResponseEntity> subscriber, String content, int industryId, int toUid) {

        Observable observable = service.createDiscuss(content, industryId, toUid)
                .map(new HttpResultFunc<CreateDiscussResponseEntity>());


        toSubscribe(observable, subscriber);
    }

}
