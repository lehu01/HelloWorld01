package com.zmsoft.ccd.module.menu.menu.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：零售称重商品对话框
 * <br/>
 * Created by kumu on 2017/11/1.
 */

public class RetailWeighDialog extends DialogFragment {

    @BindView(R2.id.frame_image_menu_icon)
    ImageView mImageMenuIcon;
    @BindView(R2.id.text_weigh_food_flag)
    TextView mTextWeighFoodFlag;
    @BindView(R2.id.text_food_name)
    TextView mTextFoodName;
    @BindView(R2.id.layout_menu_name)
    LinearLayout mLayoutMenuName;
    @BindView(R2.id.text_bar_code)
    TextView mTextBarCode;
    @BindView(R2.id.text_food_unit_price)
    TextView mTextFoodUnitPrice;
    @BindView(R2.id.view_divider)
    View mViewDivider;
    @BindView(R2.id.text_weigh_label)
    TextView mTextWeighLabel;
    @BindView(R2.id.edit_weigh)
    EditText mEditWeigh;
    @BindView(R2.id.text_weigh_unit)
    TextView mTextWeighUnit;

    @BindView(R2.id.text_add_to_cart)
    TextView mTextAddToCart;
    @BindView(R2.id.image_close)
    ImageView mImageClose;

    @BindView(R2.id.rl_top)
    View mRlTop;
    @BindView(R2.id.rl_left)
    View mRlLeft;

    public static String RETAIL_WEIGH_MENU = "menu";
    public static String RETAIL_WEIGH_TYPE = "type";
    public static String RETAIL_WEIGH_INDEX = "index";
    public static String RETAIL_WEIGH_NUM = "num";

    private Menu mMenu;
    private int type;
    private String index;
    private double num;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.module_menu_dialog_retail_weigh_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        //mRlTop.getBackground().setAlpha((int) (0.9 * 255));
        //mRlLeft.getBackground().setAlpha((int) (0.9 * 255));

        ButterKnife.bind(this, view);


        mEditWeigh.setText("");
        CustomViewUtil.initEditViewFocousAll(mEditWeigh);

        mImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null && (mMenu = bundle.getParcelable("menu")) != null) {
            type = bundle.getInt("type");
            index = bundle.getString("index");
            mTextFoodName.setText(mMenu.getName());
            //条形码
            if (TextUtils.isEmpty(mMenu.getCode())) {
                mTextBarCode.setText(getString(R.string.module_menu_retail_no_barcode_label));
            } else {
                mTextBarCode.setText(mMenu.getCode());
            }
            //加载商品图片，圆角
            ImageLoaderUtil.getInstance().loadImage(mMenu.getMenuPicUrl(), mImageMenuIcon
                    , ImageLoaderOptionsHelper.getCcdGoodsRoundCornerOptions());
            //设置价格
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(String.format(getContext().getResources().getString(R.string.module_menu_list_placeholder_price_unit)
                    , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , NumberUtils.getDecimalFee(mMenu.getPrice(), 2))
                    , mMenu.getAccount()));

            int divide = builder.toString().indexOf('/');
            ForegroundColorSpan redSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.module_menu_price_red));
            builder.setSpan(redSpan, 0, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan graySpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.module_menu_black));
            builder.setSpan(graySpan, divide, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTextFoodUnitPrice.setText(builder);

            mTextAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickDone();
                }
            });

            mEditWeigh.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        clickDone();
                        return true;
                    }
                    return false;
                }
            });

            mTextWeighUnit.setText(mMenu.getAccount());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null) {
            num = bundle.getDouble("num");
//            mEditWeigh.setText("");
//            String hintStr = NumberUtils.getDecimalFee(num, 2);
//            SpannableString ss =  new SpannableString(hintStr);
//            AbsoluteSizeSpan ass = new AbsoluteSizeSpan(10, true);
//            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            mEditWeigh.setHint(new SpannedString(ss));
            mEditWeigh.setText("");
        }
    }

    private void clickDone() {
        if (mEditWeigh.getText().length() == 0) {
            ToastUtils.showLongToast(getContext(), R.string.module_menu_retail_please_enter_weigh);
        } else {
            Fragment f = getParentFragment();
            if (f instanceof RetailMenuListFragment) {
                ((RetailMenuListFragment) f).addWeightMenu(mMenu, Double.valueOf(mEditWeigh.getText().toString()) + num, type, index);
            }
        }
    }
}
