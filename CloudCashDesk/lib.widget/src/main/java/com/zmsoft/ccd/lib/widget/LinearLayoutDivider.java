package com.zmsoft.ccd.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description：有分隔线的LinearLayout
 * <br/>
 * Created by kumu on 2017/4/17.
 */
public class LinearLayoutDivider extends LinearLayoutCompat {

    private Drawable tDivider;
    private int tOrientation;
    private int tDividerWidth;
    private int tDividerHeight;
    private int tDividerPadding_Start;
    private int tDividerPadding_End;

    public LinearLayoutDivider(Context context) {
        this(context, null);
    }

    public LinearLayoutDivider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutDivider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tOrientation = getOrientation();
        tDivider = getDividerDrawable();
        tDividerWidth = getDividerWidth();
        tDividerHeight = tDivider != null ? tDivider.getIntrinsicHeight() : 0;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LinearLayoutDivider, defStyleAttr, 0);
        tDividerPadding_Start = a.getDimensionPixelSize(R.styleable.LinearLayoutDivider_dividerPadding_Start, 0);
        tDividerPadding_End = a.getDimensionPixelSize(R.styleable.LinearLayoutDivider_dividerPadding_End, 0);
        a.recycle();

        int padding = super.getDividerPadding();
        if (padding != 0) {
            tDividerPadding_Start = padding;
            tDividerPadding_End = padding;
        }
    }

    @Override
    public void setDividerPadding(int padding) {
        tDividerPadding_Start = padding;
        tDividerPadding_End = padding;
    }

    public void setDividerPadding(int padding_start, int padding_end) {
        tDividerPadding_Start = padding_start;
        tDividerPadding_End = padding_end;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (tDivider == null) {
            return;
        }

        if (tOrientation == VERTICAL) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        final int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getVirtualChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int top = child.getTop() - lp.topMargin - tDividerHeight;
                    drawHorizontalDivider(canvas, top);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getVirtualChildAt(count - 1);
            int bottom = 0;
            if (child == null) {
                bottom = getHeight() - getPaddingBottom() - tDividerHeight;
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                bottom = child.getBottom() + lp.bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        final int count = getVirtualChildCount();
        final boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            final View child = getVirtualChildAt(i);

            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int position;
                    if (isLayoutRtl) {
                        position = child.getRight() + lp.rightMargin;
                    } else {
                        position = child.getLeft() - lp.leftMargin - tDividerWidth;
                    }
                    drawVerticalDivider(canvas, position);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getVirtualChildAt(count - 1);
            int position;
            if (child == null) {
                if (isLayoutRtl) {
                    position = getPaddingLeft();
                } else {
                    position = getWidth() - getPaddingRight() - tDividerWidth;
                }
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position = child.getLeft() - lp.leftMargin - tDividerWidth;
                } else {
                    position = child.getRight() + lp.rightMargin;
                }
            }
            drawVerticalDivider(canvas, position);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        tDivider.setBounds(getPaddingLeft() + tDividerPadding_Start, top,
                getWidth() - getPaddingRight() - tDividerPadding_End, top + tDividerHeight);
        tDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        tDivider.setBounds(left, getPaddingTop() + tDividerPadding_Start,
                left + tDividerWidth, getHeight() - getPaddingBottom() - tDividerPadding_End);
        tDivider.draw(canvas);
    }

    View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }
}