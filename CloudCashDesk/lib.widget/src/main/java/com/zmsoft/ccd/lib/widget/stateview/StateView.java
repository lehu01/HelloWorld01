package com.zmsoft.ccd.lib.widget.stateview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zmsoft.ccd.lib.widget.R;

/**
 * 界面加载状态的view
 * eg.loading、empty、error等状态
 * <p>
 * 设置自定义视图:
 * <p>
 * 全局设置办法:在自己项目的layout下新建, 名字跟StateView默认layout一样即可(也不用代码设置).
 * 默认layout的名字:base_empty/base_retry/base_loading.
 * <p>
 * 单页面设置:layout名字不一样, 然后再代码设置.
 * <p>
 * setEmptyResource(@LayoutRes int emptyResource)
 * <p>
 * setRetryResource(@LayoutRes int retryResource)
 * <p>
 * setLoadingResource(@LayoutRes int loadingResource)
 * </p>
 *
 * @author DangGui
 * @create 2017/3/1.
 */
public class StateView extends View {

    private int mEmptyResource;
    private int mRetryResource;
    private int mLoadingResource;

    private View mEmptyView;
    private View mRetryView;
    private View mLoadingView;
    private ImageView mLoadingViewImage;
    private LayoutInflater mInflater;
    private OnRetryClickListener mRetryClickListener;

    private RelativeLayout.LayoutParams mLayoutParams;

    /**
     * 注入到activity中
     *
     * @param activity Activity
     * @return StateView
     */
    public static StateView inject(@NonNull Activity activity) {
        return inject(activity, false);
    }

    /**
     * 注入到activity中
     *
     * @param activity     Activity
     * @param hasActionBar 是否有actionbar/toolbar
     * @return StateView
     */
    public static StateView inject(@NonNull Activity activity, boolean hasActionBar) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        return inject(rootView, hasActionBar);
    }

    /**
     * 注入到ViewGroup中
     *
     * @param parent extends ViewGroup
     * @return StateView
     */
    public static StateView inject(@NonNull ViewGroup parent) {
        return inject(parent, false);
    }

    /**
     * 注入到ViewGroup中
     *
     * @param parent       extends ViewGroup
     * @param hasActionBar 是否有actionbar/toolbar,
     *                     true: 会setMargin top, margin大小是actionbarSize
     *                     false: not set
     * @return StateView
     */
    public static StateView inject(@NonNull ViewGroup parent, boolean hasActionBar) {
        /*
          因为 LinearLayout/ScrollView/AdapterView 的特性
           为了 StateView 能正常显示，自动再套一层（开发的时候就不用额外的工作量了）
        */
        int screenHeight = 0;
        if (parent instanceof LinearLayout ||
                parent instanceof ScrollView ||
                parent instanceof AdapterView) {
            ViewParent viewParent = parent.getParent();
            if (viewParent == null) {
                FrameLayout wrapper = new FrameLayout(parent.getContext());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                wrapper.setLayoutParams(layoutParams);

                if (parent instanceof LinearLayout) {
                    LinearLayout wrapLayout = new LinearLayout(parent.getContext());
                    wrapLayout.setLayoutParams(parent.getLayoutParams());
                    wrapLayout.setOrientation(((LinearLayout) parent).getOrientation());

                    for (int i = 0, childCount = parent.getChildCount(); i < childCount; i++) {
                        View childView = parent.getChildAt(0);
                        parent.removeView(childView);
                        wrapLayout.addView(childView);
                    }
                    wrapper.addView(wrapLayout);
                } else if (parent instanceof ScrollView) {
                    if (parent.getChildCount() != 1) {
                        throw new IllegalStateException("the scrollView does not have one direct child");
                    }
                    View directView = parent.getChildAt(0);
                    parent.removeView(directView);
                    wrapper.addView(directView);

                    WindowManager wm = (WindowManager) parent.getContext()
                            .getSystemService(Context.WINDOW_SERVICE);
                    DisplayMetrics metrics = new DisplayMetrics();
                    wm.getDefaultDisplay().getMetrics(metrics);
                    screenHeight = metrics.heightPixels;
                } else {
//                    throw new IllegalStateException("the view does not have parent, view = "
//                            + parent.toString());
                    View view = parent.findViewById(R.id.stateview);
                    if (null != view && view instanceof StateView) {
                        StateView stateView = (StateView) view;
                        if (hasActionBar) {
                            stateView.setTopMargin();
                        }
                        return stateView;
                    } else {
                        return null;
                    }
                }
                // parent add wrapper
                parent.addView(wrapper);
                // StateView will be added to wrapper
                parent = wrapper;
            } else {
                FrameLayout root = new FrameLayout(parent.getContext());
                root.setLayoutParams(parent.getLayoutParams());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                parent.setLayoutParams(layoutParams);

                if (viewParent instanceof ViewGroup) {
                    ViewGroup rootGroup = (ViewGroup) viewParent;
                    // 把 parent 从它自己的父容器中移除
                    rootGroup.removeView(parent);
                    // 然后替换成新的
                    rootGroup.addView(root);
                }
                root.addView(parent);
                parent = root;
            }
        }
        StateView stateView = new StateView(parent.getContext());
        if (screenHeight > 0) {
            // let StateView be shown in the center
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    hasActionBar ? screenHeight - stateView.getActionBarHeight() : screenHeight);
            parent.addView(stateView, params);
        } else {
            parent.addView(stateView);
        }
        if (hasActionBar) {
            stateView.setTopMargin();
        }
        return stateView;
    }

//    public static StateView inject(@NonNull ViewGroup parent, boolean hasActionBar) {
//        /*
//           因为 LinearLayout/ScrollView/AdapterView 的特性
//           为了 StateView 能正常显示，自动再套一层（开发的时候就不用额外的工作量了）
//         */
//        if (parent instanceof LinearLayout ||
//                parent instanceof ScrollView ||
//                parent instanceof AdapterView) {
////            FrameLayout root = new FrameLayout(parent.getContext());
////            root.setLayoutParams(parent.getLayoutParams());
////            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
////                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////            parent.setLayoutParams(layoutParams);
////            ViewParent viewParent = parent.getParent();
////            if (viewParent instanceof ViewGroup) {
////                ViewGroup rootGroup = (ViewGroup) viewParent;
////                // 把 parent 从它自己的父容器中移除
////                rootGroup.removeView(parent);
////                // 然后替换成新的
////                rootGroup.addView(root);
////            }
////            root.addView(parent);
////            parent = root;
//            View view = parent.findViewById(R.id.stateview);
//            if (null != view && view instanceof StateView) {
//                StateView stateView = (StateView) view;
//    if (hasActionBar) {
//        stateView.setTopMargin();
//    }
//    return stateView;
//            }
//        } else {
//            StateView stateView = new StateView(parent.getContext());
//            parent.addView(stateView);
//            if (hasActionBar) {
//                stateView.setTopMargin();
//            }
//            return stateView;
//        }
//        return null;
//    }

    /**
     * 注入到View中
     *
     * @param view instanceof ViewGroup
     * @return StateView
     */
    public static StateView inject(@NonNull View view) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            return inject(parent);
        } else {
            throw new ClassCastException("view must be ViewGroup");
        }
    }

    /**
     * 注入到View中
     *
     * @param view         instanceof ViewGroup
     * @param hasActionBar 是否有actionbar/toolbar
     * @return StateView
     */
    public static StateView inject(@NonNull View view, boolean hasActionBar) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            return inject(parent, hasActionBar);
        } else {
            throw new ClassCastException("view must be ViewGroup");
        }
    }

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateView);
        mEmptyResource = a.getResourceId(R.styleable.StateView_emptyResource, 0);
        mRetryResource = a.getResourceId(R.styleable.StateView_retryResource, 0);
        mLoadingResource = a.getResourceId(R.styleable.StateView_loadingResource, 0);
        a.recycle();

        if (mEmptyResource == 0) {
            mEmptyResource = R.layout.base_empty;
        }
        if (mRetryResource == 0) {
            mRetryResource = R.layout.base_retry;
        }
        if (mLoadingResource == 0) {
            mLoadingResource = R.layout.base_loading;
        }

        if (attrs == null) {
            mLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            mLayoutParams = new RelativeLayout.LayoutParams(context, attrs);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
    }

    @Override
    public void setVisibility(int visibility) {
        setVisibility(mEmptyView, visibility);
        setVisibility(mRetryView, visibility);
        setVisibility(mLoadingView, visibility);
    }

    private void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public void showContent() {
        setVisibility(GONE);
    }

    public View showEmpty() {
        if (mEmptyView == null) {
            mEmptyView = inflate(mEmptyResource);
        }

        showView(mEmptyView);
        return mEmptyView;
    }

    /**
     * 需要base_empty view里的子控件处理重试
     *
     * @param childResId
     * @return
     */
    public View showEmpty(int childResId) {
        if (mEmptyView == null) {
            mEmptyView = inflate(mEmptyResource);
            final View childView = mEmptyView.findViewById(childResId);
            if (null != childView) {
                clickEmptyView(childView);
            }
        }
        showView(mEmptyView);
        return mEmptyView;
    }

    private void clickEmptyView(final View emptyView) {
        if (null == emptyView) {
            return;
        }
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRetryClickListener != null) {
                    emptyView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRetryClickListener.onEmptyClick();
                        }
                    }, 200);
                }
            }
        });
    }

    /**
     * base_retry view直接处理重试，点击界面上任何位置都可以触发重试
     *
     * @return
     */
    public View showRetry() {
        if (mRetryView == null) {
            mRetryView = inflate(mRetryResource);
            mRetryView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRetryClickListener != null) {
                        showLoading();
                        mRetryView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mRetryClickListener.onRetryClick();
                            }
                        }, 200);
                    }
                }
            });
        }

        showView(mRetryView);
        return mRetryView;
    }

    /**
     * 需要base_retry view里的子控件处理重试
     *
     * @param childResId
     * @return
     */
    public View showRetry(int childResId, int childTextResId, String errorMessage) {
        if (mRetryView == null) {
            mRetryView = inflate(mRetryResource);
            final View childView = mRetryView.findViewById(childResId);
            final View childTextView = mRetryView.findViewById(childTextResId);
            if (null != childView) {
                clickRetryView(childView);
            } else {
                clickRetryView(mRetryView);
            }
            if (null != childTextView && childTextView instanceof TextView) {
                TextView mTextView = (TextView) childTextView;
                if (!TextUtils.isEmpty(errorMessage)) {
                    mTextView.setText(errorMessage);
                }
            }
        }
        showView(mRetryView);
        return mRetryView;
    }

    private void clickRetryView(final View retryView) {
        if (null == retryView) {
            return;
        }
        retryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRetryClickListener != null) {
                    showLoading();
                    retryView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRetryClickListener.onRetryClick();
                        }
                    }, 200);
                }
            }
        });
    }

    public View showLoading() {
        if (mLoadingView == null) {
            mLoadingView = inflate(mLoadingResource);
        }
        if (null != mLoadingView && null == mLoadingViewImage) {
            View view = mLoadingView.findViewById(R.id.image_loading);
            if (null != view) {
                mLoadingViewImage = (ImageView) view;
            }
        }
        if (null != mLoadingViewImage) {
            if (mLoadingViewImage.getDrawable() instanceof AnimationDrawable) {
                ((AnimationDrawable) mLoadingViewImage.getDrawable()).start();
            }
        }
        showView(mLoadingView);
        return mLoadingView;
    }

    private void showView(View view) {
        setVisibility(view, VISIBLE);
        if (mEmptyView == view) {
            setVisibility(mLoadingView, GONE);
            setVisibility(mRetryView, GONE);
            if (null != mLoadingView) {
                mLoadingViewImage.clearAnimation();
            }
        } else if (mLoadingView == view) {
            setVisibility(mEmptyView, GONE);
            setVisibility(mRetryView, GONE);
        } else {
            setVisibility(mEmptyView, GONE);
            setVisibility(mLoadingView, GONE);
            if (null != mLoadingView) {
                mLoadingViewImage.clearAnimation();
            }
        }
    }

    public View inflate(@LayoutRes int layoutResource) {
        final ViewParent viewParent = getParent();

        if (viewParent != null && viewParent instanceof ViewGroup) {
            if (layoutResource != 0) {
                final ViewGroup parent = (ViewGroup) viewParent;
                final LayoutInflater factory;
                if (mInflater != null) {
                    factory = mInflater;
                } else {
                    factory = LayoutInflater.from(getContext());
                }
                final View view = factory.inflate(layoutResource, parent, false);

                final int index = parent.indexOfChild(this);

                view.setClickable(false);

                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams != null) {
                    if (parent instanceof RelativeLayout) {
                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutParams;
                        mLayoutParams.setMargins(lp.leftMargin, lp.topMargin,
                                lp.rightMargin, lp.bottomMargin);

                        parent.addView(view, index, mLayoutParams);
                    } else {
                        parent.addView(view, index, layoutParams);
                    }
                } else {
                    parent.addView(view, index);
                }

                if (mLoadingView != null && mRetryView != null && mEmptyView != null) {
                    parent.removeViewInLayout(this);
                }

                return view;
            } else {
                throw new IllegalArgumentException("StateView must have a valid layoutResource");
            }
        } else {
            throw new IllegalStateException("StateView must have a non-null ViewGroup viewParent");
        }
    }

    /**
     * 设置topMargin, 当有actionbar/toolbar的时候
     */
    public void setTopMargin() {
//        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//        layoutParams.topMargin = getActionBarHeight();
    }

    /**
     * @return actionBarSize
     */
    private int getActionBarHeight() {
        int height = 0;
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return height;
    }

    /**
     * 设置emptyView的自定义Layout
     *
     * @param emptyResource emptyView的layoutResource
     */
    public void setEmptyResource(@LayoutRes int emptyResource) {
        this.mEmptyResource = emptyResource;
    }

    /**
     * 设置retryView的自定义Layout
     *
     * @param retryResource retryView的layoutResource
     */
    public void setRetryResource(@LayoutRes int retryResource) {
        this.mRetryResource = retryResource;
    }

    public boolean isLoadingViewShowing() {
        return null != mLoadingView && mLoadingView.getVisibility() == VISIBLE;
    }

    public boolean isRetryViewShowing() {
        return null != mRetryView && mRetryView.getVisibility() == VISIBLE;
    }

    /**
     * 设置loadingView的自定义Layout
     *
     * @param loadingResource loadingView的layoutResource
     */
    public void setLoadingResource(@LayoutRes int loadingResource) {
        mLoadingResource = loadingResource;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    /**
     * 监听重试
     *
     * @param listener {@link OnRetryClickListener}
     */
    public void setOnRetryClickListener(OnRetryClickListener listener) {
        this.mRetryClickListener = listener;
    }

    public interface OnRetryClickListener {
        void onRetryClick();

        void onEmptyClick();
    }
}
