package com.zmsoft.ccd.widget.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 14:46
 */
public class DropDownTab extends FrameLayout {
    //当前tab位置
    private int currentTabPosition = -1;
    //分割线颜色
    private int tabDividerColor = 0xffcccccc;
    //分割线宽度
    private int tabDividerWidth = 1;
    //分割线高度
    private int tabDividerHeight = -1;
    //tab字体大小
    private int tabTextSize = 28;
    //tab选中颜色
    private int tabTextSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int tabTextUnselectedColor = 0xff111111;
    //tab选中图标
    private int tabSelectedIcon;
    //tab未选中图标
    private int tabUnselectedIcon;
    // icon和文字距离
    private int tabIconPadding;

    private LinearLayout tabContainer;
    private List<TextView> tabTextViews = new ArrayList<>();

    private OnDropDownTabStateChangeListener onDropDownTabStateChangeListener;

    public DropDownTab(Context context) {
        this(context, null);
    }

    public DropDownTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int tabBackgroundColor = 0xffffffff;
        int tabUnderlineColor = 0xffcccccc;
        int tabUnderlineHeight = 1;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownTab);
        tabBackgroundColor = a.getColor(R.styleable.DropDownTab_tabBackgroundColor, tabBackgroundColor);
        tabDividerColor = a.getColor(R.styleable.DropDownTab_tabDividerColor, tabDividerColor);
        tabDividerWidth = a.getDimensionPixelSize(R.styleable.DropDownTab_tabDividerWidth, tabDividerWidth);
        tabDividerHeight = a.getDimensionPixelSize(R.styleable.DropDownTab_tabDividerHeight, tabDividerHeight);
        tabUnderlineColor = a.getColor(R.styleable.DropDownTab_tabUnderlineColor, tabUnderlineColor);
        tabUnderlineHeight = a.getDimensionPixelSize(R.styleable.DropDownTab_tabUnderlineHeight, tabUnderlineHeight);
        tabTextSize = a.getDimensionPixelSize(R.styleable.DropDownTab_tabTextSize, tabTextSize);
        tabTextSelectedColor = a.getColor(R.styleable.DropDownTab_tabTextSelectedColor, tabTextSelectedColor);
        tabTextUnselectedColor = a.getColor(R.styleable.DropDownTab_tabTextUnselectedColor, tabTextUnselectedColor);
        tabSelectedIcon = a.getResourceId(R.styleable.DropDownTab_tabSelectedIcon, tabSelectedIcon);
        tabUnselectedIcon = a.getResourceId(R.styleable.DropDownTab_tabUnselectedIcon, tabUnselectedIcon);
        tabIconPadding = a.getDimensionPixelSize(R.styleable.DropDownTab_tabIconPadding, tabIconPadding);
        a.recycle();

        tabContainer = new LinearLayout(getContext());
        tabContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabContainer.setGravity(Gravity.CENTER_VERTICAL);
        tabContainer.setBackgroundColor(tabBackgroundColor);
        tabContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(tabContainer);

        View underLine = new View(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tabUnderlineHeight);
        lp.gravity = Gravity.BOTTOM;
        underLine.setLayoutParams(lp);
        underLine.setBackgroundColor(tabUnderlineColor);
        addView(underLine, 1);
    }

    public OnDropDownTabStateChangeListener getOnDropDownTabStateChangeListener() {
        return onDropDownTabStateChangeListener;
    }

    public void setOnDropDownTabStateChangeListener(OnDropDownTabStateChangeListener onDropDownTabStateChangeListener) {
        this.onDropDownTabStateChangeListener = onDropDownTabStateChangeListener;
    }

    public int getCurrentTabPosition() {
        return currentTabPosition;
    }

    public void setTabs(List<String> tabTexts) {
        tabContainer.removeAllViews();
        tabTextViews.clear();
        if (tabTexts != null) {
            int size = tabTexts.size();
            for (int i = 0; i < size; i++) {
                addTab(tabTexts.get(i), i, size);
            }
        }
    }

    public void setTabText(String text) {
        if (currentTabPosition > 0 && currentTabPosition < getTabSize()) {
            tabTextViews.get(currentTabPosition).setText(text);
        }
    }

    public void setTabText(int position, String text) {
        tabTextViews.get(position).setText(text);
    }

    public int getTabSize() {
        return tabTextViews.size();
    }

    private void addTab(String text, int position, int total) {
        TextView tabTextView = new TextView(getContext());
        tabTextView.setSingleLine();
        tabTextView.setEllipsize(TextUtils.TruncateAt.END);
        tabTextView.setGravity(Gravity.CENTER);
        tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
        tabTextView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tabTextView.setTextColor(tabTextUnselectedColor);
        tabTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(tabUnselectedIcon), null);
        tabTextView.setCompoundDrawablePadding(tabIconPadding);
        tabTextView.setText(text);
        tabTextView.setPadding(dpTpPx(5), dpTpPx(12), dpTpPx(5), dpTpPx(12));
        tabTextViews.add(tabTextView);

        LinearLayout tabTextViewWrap = new LinearLayout(getContext());
        tabTextViewWrap.setTag(position);
        tabTextViewWrap.setGravity(Gravity.CENTER);
        tabTextViewWrap.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        //添加点击事件
        tabTextViewWrap.setOnClickListener(onClickListener);
        tabTextViewWrap.addView(tabTextView);
        tabContainer.addView(tabTextViewWrap);

        if (position < total - 1) {
            View divider = new View(getContext());
            divider.setBackgroundColor(tabDividerColor);
            divider.setLayoutParams(new LinearLayout.LayoutParams(tabDividerWidth, tabDividerHeight));
            tabContainer.addView(divider);
        }
    }

    public void reset() {
        this.currentTabPosition = -1;
        updateTabText();
    }

    private void updateTabText() {
        for (int i = 0; i < tabTextViews.size(); i++) {
            TextView tabTextView = tabTextViews.get(i);
            if (i == currentTabPosition) {
                tabTextView.setTextColor(tabTextSelectedColor);
                tabTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(tabSelectedIcon), null);
            } else {
                tabTextView.setTextColor(tabTextUnselectedColor);
                tabTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(tabUnselectedIcon), null);
            }
        }
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            currentTabPosition = currentTabPosition == position ? -1 : position;
            updateTabText();
            if (onDropDownTabStateChangeListener != null) {
                onDropDownTabStateChangeListener.onDropDownTabStateChange(currentTabPosition);
            }
        }
    };

    private int dpTpPx(float value) {
        return DropDownUtil.dpTpPx(getContext(), value);
    }

    public interface OnDropDownTabStateChangeListener {
        void onDropDownTabStateChange(int currentTabPosition);
    }
}
