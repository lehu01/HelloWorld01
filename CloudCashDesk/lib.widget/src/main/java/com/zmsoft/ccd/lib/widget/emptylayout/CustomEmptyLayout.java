package com.zmsoft.ccd.lib.widget.emptylayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.lib.widget.R;

/**
 * @author DangGui
 * @create 2017/8/10.
 */

public class CustomEmptyLayout extends LinearLayout {
    private int mImageLogoRes = -1;
    private int mTextDescRes = -1;
    private int mButtonContentRes = -1;
    private boolean mButtonVisiable = false;

    public CustomEmptyLayout(Context context) {
        super(context);
        init(context);
    }

    public CustomEmptyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    public CustomEmptyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEmptyLayout);
            mImageLogoRes = typedArray.getResourceId(R.styleable.CustomEmptyLayout_image_logo, -1);
            mTextDescRes = typedArray.getResourceId(R.styleable.CustomEmptyLayout_text_desc, -1);
            mButtonContentRes = typedArray.getColor(R.styleable.CustomEmptyLayout_button_content, -1);
            mButtonVisiable = typedArray.getBoolean(R.styleable.CustomEmptyLayout_button_visiable, false);
            typedArray.recycle();
        }
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.base_empty, null);
        ImageView imageLogo = (ImageView) view.findViewById(R.id.image_logo);
        TextView testDesc = (TextView) view.findViewById(R.id.text_desc);
        Button buttonAdd = (Button) view.findViewById(R.id.button_add);
        if (mImageLogoRes != -1) {
            imageLogo.setImageResource(mImageLogoRes);
        } else {
            imageLogo.setVisibility(INVISIBLE);
        }
        if (mTextDescRes != -1) {
            testDesc.setText(mTextDescRes);
        } else {
            testDesc.setVisibility(INVISIBLE);
        }
        buttonAdd.setVisibility(mButtonVisiable ? VISIBLE : INVISIBLE);
        if (mButtonVisiable && mButtonContentRes != -1) {
            buttonAdd.setText(mButtonContentRes);
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view, layoutParams);
    }

}
