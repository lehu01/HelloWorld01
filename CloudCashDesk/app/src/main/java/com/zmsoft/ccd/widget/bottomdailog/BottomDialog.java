package com.zmsoft.ccd.widget.bottomdailog;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.widget.LinearLayoutDivider;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/8 14:27
 */
public class BottomDialog {

    //上下文对象
    private Context mContext;
    //Title文字
    private String mTitle;
    //PopupWindow对象
    private PopupWindow mPopupWindow;
    //选项的文字
    private String[] mOptions;
    //选项的颜色
    private int[] mColors;
    //点击事件
    private PopupWindowItemClickListener mItemClickListener;

    /**
     * 一个参数的构造方法，用于弹出无标题的PopupWindow
     *
     * @param context
     */
    public BottomDialog(Context context) {
        this.mContext = context;
    }

    /**
     * 2个参数的构造方法，用于弹出有标题的PopupWindow
     *
     * @param context
     * @param title   标题
     */
    public BottomDialog(Context context, String title) {
        this.mContext = context;
        this.mTitle = title;
    }

    /**
     * 设置选项的点击事件
     *
     * @param itemClickListener
     */
    public void setItemClickListener(PopupWindowItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * 设置选项文字
     */
    public void setItemText(String... items) {
        mOptions = items;
    }

    public void setItems(String[] items) {
        mOptions = items;
    }

    /**
     * 设置选项文字颜色，必须要和选项的文字对应
     */
    public void setmColors(int... color) {
        mColors = color;
    }

    /**
     * 设置弹窗标题
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * 添加子View
     */
    private void addView(View v) {
        LinearLayoutDivider linearLayout = (LinearLayoutDivider) v.findViewById(R.id.layout_popup_add);
        //Title
        TextView textTitle = (TextView) v.findViewById(R.id.text_popup_title);
        //取消按钮
        Button buttonCancel = (Button) v.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (mTitle != null) {
            textTitle.setText(mTitle);
        } else {
            textTitle.setVisibility(View.GONE);
        }
        if (mOptions != null && mOptions.length > 0) {
            for (int i = 0; i < mOptions.length; i++) {
                View item = LayoutInflater.from(mContext).inflate(R.layout.item_popup_option, null);
                Button optionItem = (Button) item.findViewById(R.id.button_popup_option);
                optionItem.setText(mOptions[i]);
                if (mColors != null && mColors.length == mOptions.length) {
                    optionItem.setTextColor(mColors[i]);
                }
                final int finalI = i;
                optionItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onItemClick(finalI);
                        }
                    }
                });
                linearLayout.addView(item);
            }
        }
    }

    /**
     * 弹出Popupwindow
     */
    public void showPopupWindow() {
        View popupWindowView = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_dailog, null);
        addView(popupWindowView);
        mPopupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(popupWindowView);
    }

    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 动态设置Activity背景透明度
     *
     * @param isOpen
     */
    public void setWindowAlpa(boolean isOpen) {
        final Window window = ((Activity) mContext).getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isOpen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                params.alpha = alpha;
                window.setAttributes(params);
            }
        });
        animator.start();
    }


    /**
     * 点击事件选择回调
     */
    public interface PopupWindowItemClickListener {
        void onItemClick(int position);
    }

    public boolean isShowing() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            return true;
        }
        return false;
    }
}
