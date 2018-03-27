package com.zmsoft.ccd.module.main.order.hangup.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.bean.order.hangup.HangUpOrder;
import com.zmsoft.ccd.module.main.order.hangup.RetailHangUpOrderListActivity;
import com.zmsoft.ccd.module.main.order.hangup.adapter.HangUpOrderListAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/17 10:56
 *     desc  : 挂单起订单fragment
 * </pre>
 */
public class RetailHangUpOrderListFragment extends BaseListFragment implements RetailHangUpOrderListContract.View {

    @BindView(R.id.linear_content)
    LinearLayout mLinearContent;
    @BindView(R.id.text_hang_order_two_hours)
    TextView mTextHangOrderTwoHours;

    private RetailHangUpOrderListPresenter mPresenter;
    private HangUpOrderListAdapter mAdapter;

    public static RetailHangUpOrderListFragment newInstance() {
        RetailHangUpOrderListFragment fragment = new RetailHangUpOrderListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hang_up_order;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        disableAutoRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (data instanceof HangUpOrder) {
                    HangUpOrder hangUpOrder = (HangUpOrder) data;
                    gotoShopCarActivity(hangUpOrder);
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mLinearContent.setVisibility(View.GONE);
        showLoadingView();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        mLinearContent.setVisibility(View.GONE);
        showLoadingView();
        getHangUpOrderList();
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    public void getHangUpOrderList() {
        mPresenter.getHangUpOrderList(UserHelper.getEntityId());
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    protected void loadListData() {
        getHangUpOrderList();
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mAdapter = new HangUpOrderListAdapter(getActivity(), null);
        return mAdapter;
    }

    @Override
    public void setPresenter(RetailHangUpOrderListContract.Presenter presenter) {
        mPresenter = (RetailHangUpOrderListPresenter) presenter;
    }

    @Override
    public void getHangUpOrderListSuccess(List<HangUpOrder> data) {
        mLinearContent.setVisibility(View.VISIBLE);
        mTextHangOrderTwoHours.setVisibility(View.VISIBLE);
        if (getPageIndex() == 1) {
            if (data == null || data.size() == 0) {
                mTextHangOrderTwoHours.setVisibility(View.GONE);
            }
        }
        showContentView();
        cleanAll();
        renderListData(data);
    }

    @Override
    public void showLoadErrorView(String errorMessage) {
        mLinearContent.setVisibility(View.GONE);
        showErrorView(errorMessage);
    }

    private void gotoShopCarActivity(HangUpOrder hangUpOrder) {
        OrderParam orderParam = new OrderParam();
        orderParam.setSeatCode(hangUpOrder.getSeatCode());
        MRouter.getInstance().build(RouterPathConstant.RetailCart.PATH_CART)
                .putSerializable(RouterPathConstant.RetailCart.EXTRA_CREATE_ORDER_PARAM, orderParam)
                .putInt(RouterPathConstant.RetailCart.EXTRA_FROM, RouterPathConstant.RetailCart.EXTRA_FROM_HANG_UP_ORDER)
                .navigation(getActivity(), RetailHangUpOrderListActivity.RESULT_CART);
    }
}
