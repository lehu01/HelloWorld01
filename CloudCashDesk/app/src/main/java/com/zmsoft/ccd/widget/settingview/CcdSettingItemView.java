package com.zmsoft.ccd.widget.settingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.utils.StringUtils;


/**
 * Created by jihuo on 2016/12/12.
 */

public class CcdSettingItemView extends RelativeLayout {

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_CHECK_BOX = 3;
    private Context mContext;
    private TextView mTextTitle;
    private SwitchCompat mCheckBox;
    private TextView mTextExplain;
    private View mSubLabel;

    private int type;
    private String title = "";
    /**
     * ItemView是不是子项目
     */
    private boolean mIsSub = false;
    TypedArray typedArray;

    private OnCheckChangeListener checkChangeListener;

    public CcdSettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.CcdSettingItemView, defStyleAttr, 0);
        type = typedArray.getInt(R.styleable.CcdSettingItemView_type, TYPE_TEXT);
        title = typedArray.getString(R.styleable.CcdSettingItemView_siv_title);
        mIsSub = typedArray.getBoolean(R.styleable.CcdSettingItemView_siv_isSub, false);
        View view = inflater.inflate(R.layout.item_setting, this, true);
        initView(view);
        refreshViewByType(type);
        typedArray.recycle();
    }

    public CcdSettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void initView(View view) {

        mTextTitle = (TextView) view.findViewById(R.id.text_setting_title);
        mTextTitle.setText(title);

        mCheckBox = (SwitchCompat) view.findViewById(R.id.checkbox_setting);
        Drawable drawable = typedArray.getDrawable(R.styleable.CcdSettingItemView_siv_check_box_button);
        if (drawable != null) {
            mCheckBox.setButtonDrawable(drawable);
        }
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkChangeListener != null) {
                    checkChangeListener.checkChange(compoundButton, b, getId());
                }
            }
        });

        mTextExplain = (TextView) view.findViewById(R.id.text_setting_explain);
        mSubLabel = view.findViewById(R.id.view_sub_label);
        String explain = typedArray.getString(R.styleable.CcdSettingItemView_siv_explain);
        if (!StringUtils.isEmpty(explain)) {
            mTextExplain.setVisibility(VISIBLE);
            mTextExplain.setText(explain);
        } else {
            mTextExplain.setVisibility(GONE);
        }
        mSubLabel.setVisibility(mIsSub ? VISIBLE : GONE);
    }

    private void refreshViewByType(int type) {
        mCheckBox.setVisibility(GONE);
        switch (type) {
            case TYPE_CHECK_BOX:
                mCheckBox.setVisibility(VISIBLE);
                break;
        }
    }

    public void setChecked(boolean isChecked) {
        mCheckBox.setChecked(isChecked);
    }

    public void setOnCheckChangeListener(OnCheckChangeListener checkChangeListener) {
        this.checkChangeListener = checkChangeListener;
    }

    public interface OnCheckChangeListener {
        void checkChange(CompoundButton compoundButton, boolean isChecked, int id);
    }
}
