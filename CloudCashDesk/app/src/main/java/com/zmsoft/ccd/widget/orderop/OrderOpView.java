package com.zmsoft.ccd.widget.orderop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmsoft.ccd.R;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/14 09:42
 */
public class OrderOpView implements View.OnClickListener {

    private Context mContext;
    private View mView;
    private View mViewOpOrderBg;
    private TextView mTextPrintDishesOrder;
    private TextView mTextPrintAccountOrder;
    private TextView mTextChangeOrder;
    private TextView mTextCancelOrder;
    private TextView mTextPushOrder;
    private OnItemClickListener mOnItemClickListener;

    public OrderOpView(Context context, ViewGroup viewGroup) {
        this.mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_order_op, viewGroup, false);
        mViewOpOrderBg = mView.findViewById(R.id.view_op_order_bg);
        mTextPrintDishesOrder = (TextView) mView.findViewById(R.id.text_print_dishes_order);
        mTextPrintAccountOrder = (TextView) mView.findViewById(R.id.text_print_account_order);
        mTextChangeOrder = (TextView) mView.findViewById(R.id.text_change_order);
        mTextCancelOrder = (TextView) mView.findViewById(R.id.text_cancel_order);
        mTextPushOrder = (TextView) mView.findViewById(R.id.text_push_order);
        viewGroup.addView(mView);
        mView.setVisibility(View.GONE);
        initListener();
    }

    private void initListener() {
        mViewOpOrderBg.setOnClickListener(this);
        mTextPrintDishesOrder.setOnClickListener(this);
        mTextPrintAccountOrder.setOnClickListener(this);
        mTextChangeOrder.setOnClickListener(this);
        mTextCancelOrder.setOnClickListener(this);
        mTextPushOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mTextPrintDishesOrder) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.printDishesOrder();
            }
        } else if (view == mTextPrintAccountOrder) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.printAccountOrder();
            }
        } else if (view == mTextChangeOrder) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.changeOrder();
            }
        } else if (view == mTextCancelOrder) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.cancelOrder();
            }
        } else if (view == mTextPushOrder) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.pushOrder();
            }
        }
        hide();
    }

    public void show() {
        mView.setVisibility(View.VISIBLE);
    }

    private void hide() {
        mView.setVisibility(View.GONE);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void printDishesOrder();

        void printAccountOrder();

        void changeOrder();

        void cancelOrder();

        void pushOrder();
    }

}
