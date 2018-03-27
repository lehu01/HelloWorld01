package com.zmsoft.ccd.widget.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.zmsoft.ccd.R;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 14:46
 */
public class DropDownMenu extends FrameLayout implements DropDownTab.OnDropDownTabStateChangeListener {

    private boolean isShowing;

    private DropDownTab dropDownTab;
    private List<? extends View> popupViews;

    private View maskView;
    private FrameLayout popupViewContainer;
    private OnShowListener onShowListener;
    private OnHideListener onHideListener;

    public DropDownMenu(Context context) {
        this(context, null);
    }

    public DropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
        int maskColor = 0x88888888;
        maskColor = a.getColor(R.styleable.DropDownMenu_dropDownMenuMaskColor, maskColor);
        a.recycle();

        maskView = new FrameLayout(getContext());
        maskView.setBackgroundColor(maskColor);
        maskView.setVisibility(View.GONE);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
        addView(maskView, new LayoutParams(-1, -1));

        popupViewContainer = new FrameLayout(getContext());
        popupViewContainer.setVisibility(View.GONE);
        addView(popupViewContainer, new LayoutParams(-1, -1));
    }

    public void setDropDownMenu(@NonNull DropDownTab dropDownTab, @NonNull List<? extends View> popupViews) {
        if (dropDownTab.getTabSize() != popupViews.size()) {
            throw new IllegalArgumentException("params not match, dropDownTab.getTabSize() should be equal popupViews.size()");
        }

        this.dropDownTab = dropDownTab;
        this.dropDownTab.setOnDropDownTabStateChangeListener(this);

        this.popupViews = popupViews;
        popupViewContainer.removeAllViews();
        for (View popupView : popupViews) {
            popupViewContainer.addView(popupView);
        }
    }

    @Override
    public void onDropDownTabStateChange(int currentTabPosition) {
        if (currentTabPosition == -1) {
            hide();
        } else {
            show(currentTabPosition);
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void show(int position) {
        for (int i = 0; i < popupViews.size(); i++) {
            View popupView = popupViews.get(i);
            if (i == position) {
                popupView.setVisibility(View.VISIBLE);
            } else {
                popupView.setVisibility(View.GONE);
            }
        }
        if (!isShowing) {
            popupViewContainer.setVisibility(View.VISIBLE);
            popupViewContainer.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
            maskView.setVisibility(VISIBLE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
            isShowing = true;
            if (onShowListener != null) {
                onShowListener.onShow(position);
            }
        }
    }

    public void hide() {
        if (isShowing) {
            popupViewContainer.setVisibility(View.GONE);
            popupViewContainer.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
            dropDownTab.reset();
            isShowing = false;
            if (onHideListener != null) {
                onHideListener.onHide();
            }
        }
    }

    public void setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
    }

    public void setOnHideListener(OnHideListener onHideListener) {
        this.onHideListener = onHideListener;
    }

    public interface OnShowListener {
        void onShow(int position);
    }

    public interface OnHideListener {
        void onHide();
    }

}
