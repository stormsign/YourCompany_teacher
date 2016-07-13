package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.factory.FragmentFactory;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.MainPageAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrdersFragment;
import com.miuhouse.yourcompany.teacher.view.widget.CantScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/13.
 */
public class OrdersFragment extends BaseFragment implements IOrdersFragment, View.OnClickListener {

    private RelativeLayout mSquare;
    private RelativeLayout mMyOrder;
    private ImageView ivSquare;
    private ImageView ivMyOrder;
    private TextView mTvSquare;
    private TextView mTvMyOrder;
    private CantScrollViewPager contentViewPager;
    private LinearLayout top;
    private TextView orderCount;

    private int squareCount = 0;
    private int myOrderCount = 0;
    private boolean isAllList = true;
    private TextView textTop;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_order;
    }

    @Override
    public void getViews(View view) {
        mSquare = (RelativeLayout) view.findViewById(R.id.square);
        mMyOrder = (RelativeLayout) view.findViewById(R.id.myOrder);
        ivSquare = (ImageView) view.findViewById(R.id.iconSquare);
        ivMyOrder = (ImageView) view.findViewById(R.id.iconMyOrder);
        mTvSquare = (TextView) view.findViewById(R.id.textSquare);
        mTvMyOrder = (TextView) view.findViewById(R.id.textMyOrder);
        top = (LinearLayout) view.findViewById(R.id.top);
        contentViewPager = (CantScrollViewPager) view.findViewById(R.id.contentViewPager);
        orderCount = (TextView) view.findViewById(R.id.orderCount);
        textTop = (TextView) view.findViewById(R.id.textOrderListTop);
    }

    @Override
    public void setupView() {
        mSquare.setOnClickListener(this);
        mMyOrder.setOnClickListener(this);

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(FragmentFactory.getFragment(BaseFragment.ORDERSSQUARE));
        fragmentList.add(FragmentFactory.getFragment(BaseFragment.MYORDERS));
        MainPageAdapter adapter = new MainPageAdapter(getChildFragmentManager(), fragmentList);
        contentViewPager.setAdapter(adapter);
        changeListToggle(isAllList);
    }

    @Override
    public View getOverrideParentView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.square:
                isAllList = true;
                changeListToggle(isAllList);
                break;
            case R.id.myOrder:
                isAllList = false;
                changeListToggle(isAllList);
                break;
        }
    }

    @Override
    public void changeListToggle(boolean isAllList) {
//        page = 1;
        if (isAllList) {
            ivSquare.setImageResource(R.mipmap.ico_orderlist_s);
            mTvSquare.setTextColor(getResources().getColor(R.color.themeColor));
            ivMyOrder.setImageResource(R.mipmap.ico_myorder_n);
            mTvMyOrder.setTextColor(getResources().getColor(R.color.textDarktwo));
            setSquareTop(squareCount);
            contentViewPager.setCurrentItem(0);
//            orderListPresenter.getAllList(page);
        } else {
            ivSquare.setImageResource(R.mipmap.ico_orderlist_n);
            mTvSquare.setTextColor(getResources().getColor(R.color.textDarktwo));
            ivMyOrder.setImageResource(R.mipmap.ico_myorder_s);
            mTvMyOrder.setTextColor(getResources().getColor(R.color.themeColor));
            setMyOrdersTop(myOrderCount);
            contentViewPager.setCurrentItem(1);
//            orderListPresenter.getMyList(page);
        }
    }

    public int getSquareCount() {
        return squareCount;
    }

    public void setSquareCount(int squareCount) {
        this.squareCount = squareCount;
    }

    public int getMyOrderCount() {
        return myOrderCount;
    }

    public void setMyOrderCount(int myOrderCount) {
        this.myOrderCount = myOrderCount;
    }

    @Override
    public void setSquareTop(int count) {
        if (count > 0) {
            orderCount.setText(count + "");
        } else {
            orderCount.setText("0");
        }
        textTop.setText(context.getResources().getString(R.string.orderlist_top_square));
    }

    @Override
    public void setMyOrdersTop(int myOrdersTop) {
        if (myOrdersTop > 0) {
            orderCount.setText(myOrdersTop + "");
        } else {
            orderCount.setText("0");
        }
        textTop.setText(context.getResources().getString(R.string.orderlist_top_myorder));
    }
}
