package com.zmsoft.ccd.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/10.
 */

public class ShopSpecialityView extends LinearLayout {

    private static final int IMAGE_CHILI = 1;
    private static final int IMAGE_RECOMMENDATION = 2;

    private int mTextRes = -1, mTextColor = -1, mTextSize = -1;
    private int mChiliRate = -1, mRecommendationRate = -1;
    private int mChiliRateImage = -1, mRecommendationRateImage = -1;

    private int mCurrentChiliCount, mCurrentRecommendationCount;

    private TextView textView;


    public ShopSpecialityView(Context context) {
        super(context);
        initViews(context, null);
    }

    public ShopSpecialityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public ShopSpecialityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private TextView createTextView() {
        TextView textView = new TextView(getContext());
        LayoutParams textLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textLp.setMargins(5, 0, 0, 0);
        textView.setLayoutParams(textLp);
        if (mTextRes != -1) {
            textView.setText(mTextRes);
        }
        textView.setBackgroundResource(R.drawable.module_menu_shape_shop_specialty);
        if (mTextSize != -1) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        }
        if (mTextColor != -1) {
            textView.setTextColor((mTextColor));
        }
        return textView;
    }

    private void initViews(Context context, @Nullable AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Module_Menu_ShopSpecialityView);
            mTextColor = typedArray.getColor(R.styleable.Module_Menu_ShopSpecialityView_module_menu_shop_speciality_text_color, -1);
            mTextRes = typedArray.getResourceId(R.styleable.Module_Menu_ShopSpecialityView_module_menu_shop_speciality_text, -1);
            mTextSize = typedArray.getDimensionPixelOffset(R.styleable.Module_Menu_ShopSpecialityView_module_menu_shop_speciality_text_size, -1);
            mChiliRate = typedArray.getInt(R.styleable.Module_Menu_ShopSpecialityView_module_menu_shop_speciality_chili_rate, -1);
            mRecommendationRate = typedArray.getInt(R.styleable.Module_Menu_ShopSpecialityView_module_menu_shop_speciality_recommendation_rate, -1);
            mRecommendationRateImage = typedArray.getResourceId(R.styleable.Module_Menu_ShopSpecialityView_module_menu_shop_speciality_recommendation_image, -1);
            mChiliRateImage = typedArray.getResourceId(R.styleable.Module_Menu_ShopSpecialityView_module_menu_shop_speciality_chili_image, -1);
            typedArray.recycle();
        }

        if (mTextRes != -1) {
            textView = createTextView();
            addView(textView, -1);
        }

        if (mChiliRateImage != -1 && mChiliRate > 0) {
            loopImage(mChiliRate, mChiliRateImage, IMAGE_CHILI);
        }

        if (mRecommendationRateImage != -1 && mRecommendationRate > 0) {
            loopImage(mRecommendationRate, mRecommendationRateImage, IMAGE_RECOMMENDATION);
        }
    }

    public void setData(int chiliRate, int recommendRate, String tag) {

        if (chiliRate <= 0 && recommendRate <= 0 && TextUtils.isEmpty(tag)) {
            setVisibility(GONE);
            return;
        }

        setVisibility(VISIBLE);

        removeAllViews();
        if (chiliRate > 0 && mChiliRateImage != -1) {
            for (int i = 0; i < chiliRate; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(mChiliRateImage);
                addView(imageView);
            }
        }

        if (recommendRate > 0 && mRecommendationRateImage != -1) {
            for (int i = 0; i < recommendRate; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(mRecommendationRateImage);
                addView(imageView);
            }
        }

        if (!TextUtils.isEmpty(tag)) {
            TextView textView = createTextView();
            textView.setText(tag);
            addView(textView);
        }

    }

    @Deprecated
    public void setRecommendRate(int rate) {
        if (mRecommendationRateImage == -1) {
            Log.e("ShopSpecialityView", "Not set recommendation rate image");
            return;
        }
        if (rate > 0) {
            loopImage(rate, mRecommendationRateImage, IMAGE_RECOMMENDATION);
        }
    }

    @Deprecated
    public void setChiliRate(int rate) {
        if (mChiliRateImage == -1) {
            Log.e("ShopSpecialityView", "Not set chili rate image");
            return;
        }
        if (rate > 0) {
            loopImage(rate, mChiliRateImage, IMAGE_CHILI);
        }
    }

    private void loopImage(int rate, @DrawableRes int imageResId, int type) {

        switch (type) {
            case IMAGE_CHILI:
                if (rate > mCurrentChiliCount) {//增加
                    int diff = rate - mCurrentChiliCount;
                    int insertIndex = getInsertIndex(type);
                    for (int i = 0; i < diff; i++) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setImageResource(imageResId);
                        addView(imageView, insertIndex);
                        imageView.setTag(type);
                        mCurrentChiliCount++;
                    }
                } else if (rate < mCurrentChiliCount) {
                    int diff = mCurrentChiliCount - rate;
                    int deleteCount = 0;
                    for (int i = 0; i < getChildCount(); i++) {
                        View imageView = getChildAt(i);
                        if (imageView.getTag() != null && imageView.getTag().equals(type)) {
                            imageView.setTag(null);
                            removeView(imageView);
                            mCurrentChiliCount--;
                            if ((++deleteCount) == diff) {
                                return;
                            }
                        }
                    }
                }
                break;
            case IMAGE_RECOMMENDATION:
                if (rate > mCurrentRecommendationCount) {//增加
                    int diff = rate - mCurrentRecommendationCount;
                    int insertIndex = getInsertIndex(type);
                    for (int i = 0; i < diff; i++) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setImageResource(imageResId);
                        addView(imageView, insertIndex);
                        imageView.setTag(type);
                        mCurrentRecommendationCount++;
                    }
                } else if (rate < mCurrentRecommendationCount) {//减少
                    int diff = mCurrentRecommendationCount - rate;
                    int deleteCount = 0;
                    for (int i = 0; i < getChildCount(); i++) {
                        View imageView = getChildAt(i);
                        if (imageView.getTag() != null && imageView.getTag().equals(type)) {
                            imageView.setTag(null);
                            removeView(imageView);
                            mCurrentRecommendationCount--;
                            if ((++deleteCount) == diff) {
                                return;
                            }
                        }
                    }
                }
                break;
        }

//        if (rate > mCurrentRecommendationCount || rate > mCurrentChiliCount) {//增加
//            int diff = rate - (type == IMAGE_CHILI ? mCurrentChiliCount : mCurrentRecommendationCount);
//            int insertIndex = getInsertIndex(type);
//            for (int i = 0; i < diff; i++) {
//                ImageView imageView = new ImageView(getContext());
//                imageView.setImageResource(imageResId);
//                addView(imageView, insertIndex);
//                imageView.setTag(type);
//                if (type == IMAGE_RECOMMENDATION) {
//                    mCurrentRecommendationCount++;
//                } else {
//                    mCurrentChiliCount++;
//                }
//            }
//        } else if (rate < mCurrentRecommendationCount || rate < mCurrentChiliCount) {//减少
//            int diff = (type == IMAGE_CHILI ? mCurrentChiliCount : mCurrentRecommendationCount) - rate;
//            int deleteCount = 0;
//
//            for (int i = 0; i < getChildCount(); i++) {
//                View imageView = getChildAt(i);
//                if (imageView.getTag() != null && imageView.getTag().equals(type)) {
//                    imageView.setTag(null);
//                    removeView(imageView);
//                    if (type == IMAGE_RECOMMENDATION) {
//                        mCurrentRecommendationCount--;
//                    } else {
//                        mCurrentChiliCount--;
//                    }
//                    if ((++deleteCount) == diff) {
//                        return;
//                    }
//                }
//            }
//        }
    }

    @Deprecated
    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            if (textView != null) {
                removeView(textView);
                return;
            }
        }
        if (textView == null) {
            textView = createTextView();
            textView.setText(text);
            addView(textView, -1);
        } else {
            textView.setText(text);
            if (indexOfChild(textView) == -1) {
                addView(textView, -1);
            }
        }
    }

    public void setText(@StringRes int textRes) {
        if (textView == null) {
            textView = createTextView();
            textView.setText(textRes);
            addView(textView, -1);
            //invalidate();
        } else {
            textView.setText(textRes);
        }
    }


    private int getInsertIndex(int type) {
        //辣椒放前面
        if (type == IMAGE_CHILI) {
            return 0;
        }

        if (type == IMAGE_RECOMMENDATION) {
            return (mCurrentChiliCount > 0) ? mCurrentChiliCount : 0;
        }
        return -1;
    }
}
