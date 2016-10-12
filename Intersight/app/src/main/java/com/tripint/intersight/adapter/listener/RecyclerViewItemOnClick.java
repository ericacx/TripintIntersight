package com.tripint.intersight.adapter.listener;

/**
 * 作者：wangdakuan
 * 主要功能:
 * 创建时间：2015/9/15 15:36
 */
public interface RecyclerViewItemOnClick {
    void ItemOnClick(int position, Object data);
    void ItemOnClick(int position, Object data, boolean isSelect);
}
