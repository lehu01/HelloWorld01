package com.zmsoft.ccd.lib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * 编辑购物车商品数量控件
 *
 * @author mantianxing
 * @create 2017/12/25.
 */

public class FoodNumberTextView extends LinearLayout {

    public static final int CLICK_LEFT = 1;
    public static final int CLICK_RIGHT = 2;
    public static final int CLICK_MIDDLE = 3;

    private static final int TEXT_MARGIN_DP = 10;

    private double mCount;
    private int mUnitStringRes;

    private TextView mTextCount;
    private TextView mTextUnit;

    private OnEditViewClick mOnEditViewClick;
    private Context mContext;
    private OnClickEdit mOnClickEdit;

    public FoodNumberTextView(Context context) {
        super(context);
        init(context, null);
    }

    public FoodNumberTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FoodNumberTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnEditViewClick(OnEditViewClick onEditViewClick) {
        this.mOnEditViewClick = onEditViewClick;
    }

    public void setOnClickEdit(OnClickEdit onClickEdit) {
        mOnClickEdit = onClickEdit;
    }

    public void setNumberText(double number) {
        if (number <= mMaxValue && number >= mMinValue) {
            mCount = number;
        } else if (number > mMaxValue) {
            mCount = mMaxValue;
        } else if (number < mMinValue) {
            mCount = mMinValue;
        }
        setEditText(mTextCount, FeeHelper.getDecimalFee(mCount));
    }

    public void setUnitText(@StringRes int resId) {
        if (mTextUnit == null) {
            mTextUnit = createUnitText();
        }
        mTextUnit.setText(resId);
    }

    public void setUnitText(String unit) {
        if (mTextUnit == null) {
            mTextUnit = createUnitText();
        }
        mTextUnit.setText(unit);
    }

    private TextView createUnitText() {
        TextView textView = new TextView(getContext());
        textView.setText(mUnitStringRes);
        if (mMiddleTextSize != -1)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMiddleTextSize);
        if (mMiddleTextColor != -1)
            textView.setTextColor(mMiddleTextColor);

        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        return textView;
    }


    //ImageView  TextView ImageView

    private double mMaxValue = -1, mMinValue = -1;

    private int mMiddleTextColor = -1, mPrefixImageRes = -1,
            mLeftImageRes = -1, mRightImageRes = -1, mMiddleTextSize = -1;

    private int mMiddleHintColor = -1, mMiddleHintText = -1;

    private int mTextMargin = 10;

    private void init(final Context context, AttributeSet attrs) {
        mContext = context;
        mTextMargin = (int) (TEXT_MARGIN_DP * context.getResources().getDisplayMetrics().density + 0.5f);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Module_Menu_EditFoodNumberView);

            mLeftImageRes = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_left_image, -1);
            mPrefixImageRes = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_prefix_image, -1);


            mMiddleHintColor = typedArray.getColor(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_hint_color, -1);
            mMiddleHintText = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_hint_text, -1);


            mMaxValue = typedArray.getFloat(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_max_value, -1);
            mMinValue = typedArray.getFloat(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_min_value, -1);
            mRightImageRes = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_right_image, -1);

            mMiddleTextColor = typedArray.getColor(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_text_color, -1);
            mMiddleTextSize = typedArray.getDimensionPixelSize(R.styleable.Module_Menu_EditFoodNumberView_module_menu_middle_text_size, -1);

            mUnitStringRes = typedArray.getResourceId(R.styleable.Module_Menu_EditFoodNumberView_module_menu_unit, -1);

            typedArray.recycle();
        }

        //Prefix Image
        if (mPrefixImageRes != -1) {
            ImageView prefixImage = new ImageView(getContext());
            prefixImage.setImageResource(mPrefixImageRes);
            LayoutParams prefixLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            prefixLp.setMargins(0, 0, 5, 0);
            prefixImage.setLayoutParams(prefixLp);
            addView(prefixImage);
            prefixImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnEditViewClick != null) {
                        mOnEditViewClick.onClick(CLICK_LEFT, mCount);
                    }
                }
            });
        }

        if (mLeftImageRes != -1) {
            ImageView leftImage = new ImageView(getContext());
            LayoutParams imageLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            leftImage.setLayoutParams(imageLp);
            leftImage.setImageResource(mLeftImageRes);
            addView(leftImage);
            RxView.clicks(leftImage).throttleFirst(100, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (mOnEditViewClick != null) {
                                hideSoft(mTextCount);
                                mOnEditViewClick.onClick(CLICK_LEFT, mCount);
                            }
                        }
                    });
        }

        //数量
        mTextCount = new TextView(getContext());

        if (mMiddleHintColor != -1) {
            mTextCount.setHintTextColor(mMiddleHintColor);
        }

        mTextCount.setBackgroundResource(0);
        if (mMiddleTextSize != -1)
            mTextCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMiddleTextSize);
        if (mMiddleTextColor != -1)
            mTextCount.setTextColor(mMiddleTextColor);
        mTextCount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        mTextCount.setMaxLines(1);
        mTextCount.setSingleLine(true);
        if (mMiddleHintText != -1) {
            mTextCount.setHint(mMiddleHintText);
        } else {
            setEditText(mTextCount, String.valueOf(mCount));
        }
        mTextCount.setLongClickable(false);
        mTextCount.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mTextCount.setFocusable(false);
        mTextCount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickEdit.onClickEdit(mCount);
            }
        });

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        mTextCount.setLayoutParams(lp);
        addView(mTextCount);

        //单位
        if (mUnitStringRes != -1) {
            mTextUnit = createUnitText();
            addView(mTextUnit);

            mTextUnit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTextCount.requestFocus();
                    if (mOnEditViewClick != null) {
                        mOnEditViewClick.onClick(CLICK_MIDDLE, mCount);
                    }
                    InputMethodManager inputManager =
                            (InputMethodManager) mTextCount.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(mTextCount, 0);
                }
            });
        }

        if (mRightImageRes != -1) {
            ImageView rightImage = new ImageView(getContext());
            rightImage.setImageResource(mRightImageRes);
            LayoutParams rightLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            rightImage.setLayoutParams(rightLp);
            addView(rightImage);
            RxView.clicks(rightImage).throttleFirst(100, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (mOnEditViewClick != null) {
                                hideSoft(mTextCount);
                                mOnEditViewClick.onClick(CLICK_RIGHT, mCount);
                            }
                        }
                    });
        }

        mTextCount.setPadding(
                mLeftImageRes != -1 ? mTextMargin : 0,
                0,
                mRightImageRes != -1 && mUnitStringRes == -1 ? mTextMargin : 0,
                0);

        if (mRightImageRes != -1 && mTextUnit != null) {
            mTextUnit.setPadding(0, 0, mTextMargin, 0);
        }
    }

    public void setInputType(int inputType) {
        mTextCount.setInputType(inputType);
    }

    public boolean hasInput() {
        if (mTextCount == null) {
            return false;
        }
        return mTextCount.getText().toString().trim().length() != 0;
    }

    public TextView getEditText() {
        return mTextCount;
    }

    public double getNumber() {
        return mCount;
    }

    private void setEditText(TextView textView, String text) {
        setEditText(textView, text, false);
    }

    /**
     * 修改输入框内容
     *
     * @param textView
     * @param text
     * @param isByInput 是否是通过手动输入
     */
    private void setEditText(TextView textView, String text, boolean isByInput) {
        try {
            double doubleValue = Double.parseDouble(text);
            //如果内容是整数并且不是通过手输，转成整型值
            if (!isByInput && NumberUtils.doubleIsInteger(doubleValue)) {
                textView.setText((int) doubleValue + "");
            } else {
                textView.setText(text);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            textView.setText(text);
        }
    }


    private void hideSoft(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public interface OnEditViewClick {
        void onClick(int which, double numberValue);
    }

    public interface OnClickEdit {
        void onClickEdit(double numberValue);
    }
}