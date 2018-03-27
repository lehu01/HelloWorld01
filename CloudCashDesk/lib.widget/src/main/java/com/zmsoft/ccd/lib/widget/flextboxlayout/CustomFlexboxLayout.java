package com.zmsoft.ccd.lib.widget.flextboxlayout;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zmsoft.ccd.lib.widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义FlexboxLayout，支持单选、多选
 *
 * @author DangGui
 * @create 2017/4/18.
 */

public class CustomFlexboxLayout extends FlexboxLayout {
    private Context mContext;
    /**
     * 数据源
     */
    private List<CustomFlexItem> mCustomFlexItemList;
    /**
     * 是否是单项选择
     */
    private boolean isSingleChoice = false;
    /**
     * 是否允许为空，否则至少选择一项
     */
    private boolean emptyAble = true;
    /**
     * 完整布局，默认为R.layout.item_custom_flexboxlayout
     */
    private int layoutId = -1;
    /**
     * 被选中后布局，默认R.drawable.shape_flexitem_checked_layers
     */
    private int resIdCheckedBackground = -1;

    private OnItemChangeListener mOnItemChangeListener;

    public CustomFlexboxLayout(Context context) {
        super(context);
        init(context);
    }

    public CustomFlexboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setFlexWrap(FLEX_WRAP_WRAP);
        setAlignContent(ALIGN_CONTENT_FLEX_START);
        setAlignItems(ALIGN_ITEMS_FLEX_START);
    }

    /**
     * 初始化数据源<br />
     * <p>
     * 将要展示的选项组装成CustomFlexItem放入customFlexItemList，指定是否是单选
     * </p>
     *
     * @param customFlexItemList
     * @param isSingleChoice
     */
    public void initSource(List<CustomFlexItem> customFlexItemList, boolean isSingleChoice) {
        this.mCustomFlexItemList = customFlexItemList;
        this.isSingleChoice = isSingleChoice;
        initFlexboxViewBySource();
    }

    public void initSource(List<CustomFlexItem> customFlexItemList, boolean isSingleChoice, boolean emptyAble) {
        this.mCustomFlexItemList = customFlexItemList;
        this.isSingleChoice = isSingleChoice;
        this.emptyAble = emptyAble;
        initFlexboxViewBySource();
    }

    public OnItemChangeListener getOnItemChangeListener() {
        return mOnItemChangeListener;
    }

    public void setOnItemChangeListener(OnItemChangeListener onItemChangeListener) {
        mOnItemChangeListener = onItemChangeListener;
    }

    public void setItemLayoutRes(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    public void setResIdCheckedBackground(@DrawableRes int resIdCheckedBackground) {
        this.resIdCheckedBackground = resIdCheckedBackground;
    }

    /**
     * 获取被选中的item
     *
     * @return
     */
    public List<CustomFlexItem> getCheckedItemList() {
        List<CustomFlexItem> checkedItemList = new ArrayList<>();
        if (null == mCustomFlexItemList || mCustomFlexItemList.isEmpty()) {
            return checkedItemList;
        }
        for (int i = 0; i < mCustomFlexItemList.size(); i++) {
            CustomFlexItem customFlexItem = mCustomFlexItemList.get(i);
            if (customFlexItem.isChecked()) {
                checkedItemList.add(customFlexItem);
            }
        }
        return checkedItemList;
    }

    /**
     * 根据数据源绘制FlexBoxLayout界面
     */
    private void initFlexboxViewBySource() {
        filterSource();
        if (null != mCustomFlexItemList) {
            removeAllViews();
            for (int i = 0; i < mCustomFlexItemList.size(); i++) {

                View itemView = LayoutInflater.from(mContext).inflate(
                        layoutId == -1 ? R.layout.item_custom_flexboxlayout : layoutId, null);
                TextView itemTextView = (TextView) itemView.findViewById(R.id.text_item);
                TextView itemExtraTextView = (TextView) itemView.findViewById(R.id.text_item_extra);
                CustomFlexItem flexItem = mCustomFlexItemList.get(i);
                itemView.setTag(flexItem.getId());
                itemTextView.setText(flexItem.getName());
                if (!TextUtils.isEmpty(flexItem.getExtras())) {
                    itemExtraTextView.setVisibility(VISIBLE);
                    itemExtraTextView.setText(flexItem.getExtras());
                } else {
                    itemExtraTextView.setVisibility(GONE);
                }
                checkSelectView(itemView, flexItem.isChecked());
                if (!flexItem.isUnModifyAble()) {
                    itemView.setOnClickListener(new ItemClickListener());
                } else {
                    itemView.setOnClickListener(null);
                }
                addView(itemView);
            }

        }
    }

    /**
     * 过滤数据源，如果是单项选择，但数据源却有不止1项被默认选中，则重置所有项目未选中
     */
    private void filterSource() {
        if (null == mCustomFlexItemList || mCustomFlexItemList.isEmpty() || !isSingleChoice) {
            return;
        }
        int checkedCount = 0;
        for (int i = 0; i < mCustomFlexItemList.size(); i++) {
            if (mCustomFlexItemList.get(i).isChecked()) {
                checkedCount++;
            }
        }
        if (checkedCount > 1) {
            for (int i = 0; i < mCustomFlexItemList.size(); i++) {
                mCustomFlexItemList.get(i).setChecked(false);
            }
        }
    }

    /**
     * 根据选中的view的tag来进行过滤
     *
     * @param id
     */
    public void checkSelected(String id) {
        if (getChildCount() >= mCustomFlexItemList.size()) {
            for (int i = 0; i < mCustomFlexItemList.size(); i++) {
                View itemView = getChildAt(i);
                CustomFlexItem customFlexItem = mCustomFlexItemList.get(i);
                if (null != itemView.getTag()) {
                    boolean isValid = itemView.getTag().toString().equals(id);
                    if (isSingleChoice) {
                        if (isValid) {
                            if (emptyAble || !customFlexItem.isChecked()) {
                                checkSelectView(itemView, !customFlexItem.isChecked());
                                customFlexItem.setChecked(!customFlexItem.isChecked());
                            }
                        } else {
                            checkSelectView(itemView, false);
                            customFlexItem.setChecked(false);
                        }
                    } else {
                        if (isValid) {
                            checkSelectView(itemView, !customFlexItem.isChecked());
                            customFlexItem.setChecked(!customFlexItem.isChecked());
                        }
                    }
                }
            }
        }
    }


    /**
     * 根据position选择
     * @param position
     */
    public void checkSelected(int position) {
        if (mCustomFlexItemList.size() > position) {
            CustomFlexItem item = mCustomFlexItemList.get(position);
            checkSelected(item.getId());
            if (null != mOnItemChangeListener) {
                mOnItemChangeListener.onItemChanged(getCheckedItemList());
            }
        }
    }

    public void checkSelected(CustomFlexItem item) {
        select(item.getId());
        if (null != mOnItemChangeListener) {
            mOnItemChangeListener.onItemChanged(getCheckedItemList());
        }

    }

    private void select(String id) {
        if (getChildCount() >= mCustomFlexItemList.size()) {
            for (int i = 0; i < mCustomFlexItemList.size(); i++) {
                View itemView = getChildAt(i);
                CustomFlexItem customFlexItem = mCustomFlexItemList.get(i);
                if (null != itemView.getTag()) {
                    boolean isValid = itemView.getTag().toString().equals(id);
                    if (isSingleChoice) {
                        checkSelectView(itemView, isValid);
                        customFlexItem.setChecked(isValid);
                    } else {
                        if (isValid) {
                            checkSelectView(itemView, true);
                            customFlexItem.setChecked(true);
                        }
                    }
                }
            }
        }
    }



    /**
     * 对选中/未选中的item设置背景
     *
     * @param itemView
     * @param checked
     */
    private void checkSelectView(View itemView, boolean checked) {
        if (itemView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) itemView;
            if (viewGroup.getChildCount() > 0) {
                if (viewGroup.getChildAt(0) instanceof ViewGroup) {
                    ViewGroup childViewGroup = (ViewGroup) viewGroup.getChildAt(0);
                    if (checked) {
                        childViewGroup.setBackgroundResource(resIdCheckedBackground == -1 ? R.drawable.shape_flexitem_checked_layers : resIdCheckedBackground);
                    } else {
                        childViewGroup.setBackgroundResource(R.drawable.shape_flexitem_normal);
                    }
                    if (childViewGroup.getChildCount() > 0) {
                        for (int i = 0; i < childViewGroup.getChildCount(); i++) {
                            View view = childViewGroup.getChildAt(i);
                            if (view instanceof TextView) {
                                if (checked) {
                                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.filter_button_color));
                                } else {
                                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (null != v.getTag()) {
                checkSelected(v.getTag().toString());
                if (null != mOnItemChangeListener) {
                    mOnItemChangeListener.onItemChanged(getCheckedItemList());
                }
            }
        }
    }

    public interface OnItemChangeListener {
        void onItemChanged(List<CustomFlexItem> checkedFlexItemList);
    }
}
