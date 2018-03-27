package com.zmsoft.ccd.lib.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/10/16.
 */

public class NoMoveTextView extends AppCompatTextView {

    public NoMoveTextView(Context context) {
        super(context);
    }

    public NoMoveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoMoveTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return false;
        }
        return super.dispatchTouchEvent(event);
    }
}
