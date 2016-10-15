package com.tripint.intersight.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.filter.ItemModel;
import com.tripint.intersight.common.widget.filter.adapter.MenuAdapter;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterDoneListener;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterItemClickListener;
import com.tripint.intersight.common.widget.filter.typeview.DoubleListView;
import com.tripint.intersight.common.widget.filter.typeview.SingleGridView;
import com.tripint.intersight.common.widget.filter.typeview.SingleListView;
import com.tripint.intersight.common.widget.filter.util.CommonUtil;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.entity.Ability;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.IndustryChild;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.model.search.FilterUrl;
import com.tripint.intersight.view.grid.BetterDoubleGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class SearchDropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;
    private SearchFilterEntity data;
    private List<Industry> industies = new ArrayList<>(); //行业数据
    private List<ItemModel> abilities = new ArrayList<>(); //职能数据
    private List<ItemModel> sorts = new ArrayList<>(); //职能数据

    private String[] sortArray = {"智能排序", "问答数量从高到低", "访谈数量从高到低", "观点数量从高到低", "被关注数量从高到低"}; //排序数据
    private Integer[] sortArrayKey = {0, 1, 2, 3, 4}; //排序数据

    public SearchDropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    public SearchDropMenuAdapter(Context context, String[] titles, SearchFilterEntity data, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.data = data;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
        for (Industry industry : this.data.getIndustry()) {
            industies.add(industry);
        }

        for (Ability ability : this.data.getAbility()) {

            abilities.add(new ItemModel(ability.getId(), ability.getName()));
        }

        int i = 0;
        for (String itemName : sortArray) {
            ItemModel itemModel = new ItemModel();
            itemModel.setKey(sortArrayKey[i]);
            itemModel.setName(itemName);
            sorts.add(itemModel);
            i++;
        }


    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {

            case 0:
                view = createDoubleListView();
                break;
            case 1:
                view = createSingleListView(abilities, 1);
                break;
            case 2:
                view = createSingleListView(sorts, 2);
                break;
//            case 2:
//                view = createSingleGridView();
//                break;
//            case 3:
//                // view = createDoubleGrid();
//                view = createBetterDoubleGrid();
//                break;
        }

        return view;
    }

    private View createSingleListView(List<ItemModel> list, final int type) {
        SimpleTextAdapter adapter = new SimpleTextAdapter<ItemModel>(null, mContext) {
            @Override
            public String provideText(ItemModel item) {
                return item.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(UIUtil.dp(mContext, 12), UIUtil.dp(mContext, 12), 0, UIUtil.dp(mContext, 12));
            }
        };
        SingleListView<ItemModel> singleListView = new SingleListView<>(mContext)
                .adapter(adapter)
                .onItemClick(new OnFilterItemClickListener<ItemModel>() {
                    @Override
                    public void onItemClick(ItemModel item) {

                        onFilterDone(type, item.getKey(), item.getName());
                    }
                });

        singleListView.setList(list, -1);

        return singleListView;
    }


    private View createDoubleListView() {
        DoubleListView<Industry, IndustryChild> comTypeDoubleListView = new DoubleListView<Industry, IndustryChild>(mContext)
                .leftAdapter(new SimpleTextAdapter<Industry>(null, mContext) {
                    @Override
                    public String provideText(Industry Industry) {
                        return Industry.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 12), UIUtil.dp(mContext, 12), 0, UIUtil.dp(mContext, 12));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<IndustryChild>(null, mContext) {
                    @Override
                    public String provideText(IndustryChild s) {
                        return s.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 12), UIUtil.dp(mContext, 12), 0, UIUtil.dp(mContext, 12));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<Industry, IndustryChild>() {
                    @Override
                    public List<IndustryChild> provideRightList(Industry item, int position) {
                        List<IndustryChild> child = item.getIndustrySub();
                        if (CommonUtil.isEmpty(child)) {

                            onFilterDone(0, item.getId(), item.getName());
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<Industry, IndustryChild>() {
                    @Override
                    public void onRightItemClick(Industry item, IndustryChild childItem) {

                        onFilterDone(0, childItem.getId(), childItem.getName());
                    }
                });


        //初始化选中.
        comTypeDoubleListView.setLeftList(industies, 1);
        comTypeDoubleListView.setRightList(industies.get(1).getIndustrySub(), -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }


    private View createSingleGridView() {
        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleGridPosition = item;

                        FilterUrl.instance().position = 2;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone(2, 2, item);

                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 20; i < 39; ++i) {
            list.add(String.valueOf(i));
        }
        singleGridView.setList(list, -1);


        return singleGridView;
    }


    private View createBetterDoubleGrid() {

        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }


        return new BetterDoubleGridView(mContext)
                .setmTopGridData(phases)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();
    }


    private void onFilterDone(int position, int key, String value) {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(position, key, value);
        }
    }

}
