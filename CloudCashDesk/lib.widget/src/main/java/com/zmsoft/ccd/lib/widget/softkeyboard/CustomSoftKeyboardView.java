package com.zmsoft.ccd.lib.widget.softkeyboard;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.lib.widget.R;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * 自定义数字键盘
 *
 * @author DangGui
 * @create 2017/7/29.
 */
public class CustomSoftKeyboardView extends RelativeLayout {
    private Context mContext;
    private LinearLayout mLayoutKeyboard;
    private RecyclerView mRecyclerView;
    private SoftKeyboardAdapter mSoftKeyboardAdapter;
    private ArrayList<KeyboardModel> mValueList;
    private RelativeLayout mLayoutBack;
    private RelativeLayout mLayoutDelete;
    private RelativeLayout mLayoutConfirm;
    private TextView mConfirmTextView;
    private Animation mEnterAnim;
    private Animation mExitAnim;

    private int mConfirmTextRes = -1;
    private int mConfirmBcgRes = -1;
    private int mConfirmTextColor = -1;
    private boolean mHideAble = false;

    private OnKeyboardClickListener mOnKeyboardClickListener;

    public CustomSoftKeyboardView(Context context) {
        this(context, null);
    }

    public CustomSoftKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttrs(context, attrs);
        View view = View.inflate(context, R.layout.layout_softkeyboard, null);
        mValueList = new ArrayList<>();
        mLayoutKeyboard = (LinearLayout) view.findViewById(R.id.layout_keyboard);
        mLayoutBack = (RelativeLayout) view.findViewById(R.id.layoutBack);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_keybord);
        mLayoutDelete = (RelativeLayout) view.findViewById(R.id.layout_delete);
        mLayoutConfirm = (RelativeLayout) view.findViewById(R.id.layout_confirm);
        mConfirmTextView = (TextView) view.findViewById(R.id.text_confirm);
        mEnterAnim = AnimationUtils.loadAnimation(context, R.anim.softkeyboard_push_bottom_in);
        mExitAnim = AnimationUtils.loadAnimation(context, R.anim.softkeyboard_push_bottom_out);
        initView();
        initValueList();
        addView(view);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSoftKeyboardView);
            mConfirmTextRes = typedArray.getResourceId(R.styleable.CustomSoftKeyboardView_confirm_text, R.string.softkeyboard_confirm);
            mConfirmBcgRes = typedArray.getResourceId(R.styleable.CustomSoftKeyboardView_confirm_bcg, -1);
            mConfirmTextColor = typedArray.getColor(R.styleable.CustomSoftKeyboardView_confirm_text_color
                    , ContextCompat.getColor(context, R.color.primaryTextColor));
            mHideAble = typedArray.getBoolean(R.styleable.CustomSoftKeyboardView_hideable, false);
            typedArray.recycle();
        }
    }

    private void initView() {
        if (mConfirmBcgRes != -1) {
            mLayoutConfirm.setBackgroundResource(mConfirmBcgRes);
        }
        if (mConfirmTextColor != -1) {
            mConfirmTextView.setTextColor(mConfirmTextColor);
        } else {
            mConfirmTextView.setTextColor(ContextCompat.getColor(mContext, R.color.primaryTextColor));
        }
        if (mConfirmTextRes == -1) {
            mConfirmTextView.setText(R.string.softkeyboard_confirm);
        } else {
            mConfirmTextView.setText(mConfirmTextRes);
        }
        mLayoutKeyboard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                mLayoutKeyboard.post(new Runnable() {
//                    public void run() {
                ViewGroup.LayoutParams deleteLayoutParams = mLayoutDelete.getLayoutParams();
                deleteLayoutParams.height = mLayoutKeyboard.getHeight() / 2;
                deleteLayoutParams.width = mLayoutKeyboard.getWidth() / 4;
                mLayoutDelete.setLayoutParams(deleteLayoutParams);

                ViewGroup.LayoutParams confirmLayoutParams = mLayoutConfirm.getLayoutParams();
                confirmLayoutParams.height = mLayoutKeyboard.getHeight() / 2;
                confirmLayoutParams.width = mLayoutKeyboard.getWidth() / 4;
                mLayoutConfirm.setLayoutParams(confirmLayoutParams);

//                    }
//                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mLayoutKeyboard.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mLayoutKeyboard.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    public void setOnKeyboardClickListener(OnKeyboardClickListener onKeyboardClickListener) {
        mOnKeyboardClickListener = onKeyboardClickListener;
    }

    public void init(Activity activity, final EditText editText) {
        if (null == editText) {
            return;
        }
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);

        mLayoutConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnKeyboardClickListener) {
                    mOnKeyboardClickListener.onConfimClicked();
                }
            }
        });
        mLayoutDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    StringBuilder stringBuilder = new StringBuilder(editText.getText().toString().trim());
                    editText.setText(stringBuilder.substring(0, stringBuilder.length() - 1));
                    editText.setSelection(editText.getText().length());
                }
            }
        });
        mLayoutDelete.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setText("");
                return true;
            }
        });
        mSoftKeyboardAdapter.setOnItemClickListener(new KeyboardBaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (null != data && data instanceof KeyboardModel) {
                    KeyboardModel keyboardModel = (KeyboardModel) data;
                    if (keyboardModel.getType() == KeyboardModel.ITEM_TYPE_NORMAL) {
                        if (!keyboardModel.getValue().equals(".")) {
                            editText.setText(editText.getText().toString().trim() + mValueList.get(position).getValue());
                            editText.setSelection(editText.getText().length());
                        } else {
                            StringBuilder stringBuilder = new StringBuilder(editText.getText().toString().trim());
                            if (!stringBuilder.toString().contains(".")) {
                                stringBuilder.append(mValueList.get(position).getValue());
                                editText.setText(stringBuilder.toString());
                                editText.setSelection(editText.getText().length());
                            }
                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
        // 设置不调用系统键盘
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                    boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLayoutBack.setVisibility(mHideAble ? VISIBLE : GONE);
        mLayoutBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation(mExitAnim);
                setVisibility(View.GONE);
            }
        });

        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.VISIBLE);
            }
        });
    }

    private void initValueList() {
        // 初始化按钮上应该显示的数字
        for (int i = 0; i < 12; i++) {
            KeyboardModel keyboardModel = new KeyboardModel(KeyboardModel.ITEM_TYPE_NORMAL, i);
            switch (i) {
                case 0:
                    keyboardModel.setValue("7");
                    break;
                case 1:
                    keyboardModel.setValue("8");
                    break;
                case 2:
                    keyboardModel.setValue("9");
                    break;
                case 3:
                    keyboardModel.setValue("4");
                    break;
                case 4:
                    keyboardModel.setValue("5");
                    break;
                case 5:
                    keyboardModel.setValue("6");
                    break;
                case 6:
                    keyboardModel.setValue("1");
                    break;
                case 7:
                    keyboardModel.setValue("2");
                    break;
                case 8:
                    keyboardModel.setValue("3");
                    break;
                case 9:
                    keyboardModel.setValue(".");
                    break;
                case 10:
                    keyboardModel.setValue("0");
                    break;
                case 11:
                    keyboardModel.setValue("00");
                    break;
                default:
                    keyboardModel.setValue("0");
                    break;
            }
            mValueList.add(keyboardModel);
        }
        mSoftKeyboardAdapter = new SoftKeyboardAdapter(mContext, mRecyclerView, mValueList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.addItemDecoration(new GridItemDividerDecoration(1, ContextCompat.getColor(mContext, R.color.softkeyboard_divider)));
        mRecyclerView.setAdapter(mSoftKeyboardAdapter);
    }

    public interface OnKeyboardClickListener {
        void onConfimClicked();
    }
}
