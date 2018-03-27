package com.zmsoft.ccd.module.menu.menu.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改商品数量对话框
 *
 * @author mantianxing
 * @create 2017/12/23.
 */

public class EditFoodNumberDialog {

    @BindView(R2.id.cancel_input_value)
    TextView mTextCancelDialog;
    @BindView(R2.id.ensure_input_value)
    TextView mTextEnsureDialog;
    @BindView(R2.id.edit_dialog_input_count)
    EditText mEditInputValue;
    @BindView(R2.id.edit_count_plus)
    ImageView mEditCountPlus;
    @BindView(R2.id.edit_count_minus)
    ImageView mEditCountMinus;

    BaseListAdapter.AdapterClick adapterClick;

    private Dialog dialog;
    private Context context;
    private DialogPositiveListener positiveListener;
    private DialogNegativeListener negativeListener;
    private double mDialogNumber;
    private OnInputExceedListener mOnInputExceedListener;
    private double selectedFoodNumber;
    private double otherFoodNum;

    public EditFoodNumberDialog(Context context) {
        super();
        this.context = context;
    }

    public void setPositiveListener(DialogPositiveListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    public void setNegativeListener(DialogNegativeListener negativeListener) {
        this.negativeListener = negativeListener;
    }

    public void setmMinValue(double minValue) {
        minValue = minValue;
    }

    public void setOnInputExceedListener(OnInputExceedListener onInputExceedListener) {
        mOnInputExceedListener = onInputExceedListener;
    }

    /**
     * @param data
     * @param startNum 起点值
     * @param number
     * @param menuName
     * @param unit     单位
     * @param from     来源
     * @return
     */
    public Dialog initDialog(final Object data, final double startNum, final double number, final String menuName, final String unit, final int from) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.module_menu_edit_count_dialog, null);
        ButterKnife.bind(this, dialogView);
        autoShowInputMethod(context);

        final AlertDialog inputDialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(dialogView)
                .create();
        inputDialog.show();
        mEditInputValue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        mEditInputValue.setMaxLines(1);
        mEditInputValue.setSingleLine(true);
        setEditText(mEditInputValue, String.valueOf(number));
        CustomViewUtil.initEditViewFocousAll(mEditInputValue);

        mDialogNumber = number;
        setmMinValue(startNum);

        /**
         * EditText修改事件
         */
        mEditInputValue.addTextChangedListener(new TextWatcher() {
            boolean deleteLastChar;// 是否需要删除末尾

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    // 如果点后面有超过三位数值,则删掉最后一位
                    int length = s.length() - s.toString().lastIndexOf(".");
                    // 说明后面有三位数值
                    deleteLastChar = length >= 4;
                    mEditInputValue.setSelection(mEditInputValue.getText().length());
                } else {
                    deleteLastChar = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                handleEditTextChange(s, deleteLastChar);
            }
        });
        /**
         * 取消按钮
         */
        mTextCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (negativeListener != null) {
                    negativeListener.onClick();
                }
                hideSoft(v);
                inputDialog.dismiss();
            }
        });
        /**
         * 确定按钮
         */
        mTextEnsureDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positiveListener != null) {

                    if (from == CartHelper.DialogDateFrom.SUIT_DETAIL) {

                        MenuVO dialogMenuVo = (MenuVO) data;
                        if (!dialogMenuVo.isNoLimit()) {
                            if (checkSuitFoodLimit(dialogMenuVo) == false) {   // 检查单个菜限制
                                return;
                            }
                        }
                        if (!dialogMenuVo.getSuitGroupVO().isNoLimit()) {  // 套餐组限制
                            if (checkSuitLimit(dialogMenuVo) == false) {
                                return;
                            }
                        }
                        if (mDialogNumber != CartHelper.FoodNum.MIN_VALUE && startNum > 1 && mDialogNumber < startNum) {  // 单个菜起点值
                            ToastUtils.showShortToast(context, context.getString(R.string.module_menu_check_start_num,
                                    menuName, (int) startNum, unit));
                            return;
                        }

                        double a = dialogMenuVo.getTmpNum();
                        ((MenuVO) data).setSuitSubInputNum(mDialogNumber);
                        ((MenuVO) data).setTmpNum(dialogMenuVo.getTmpNum());
                    } else {
                        if (mDialogNumber == 0 && from == CartHelper.DialogDateFrom.CART_NORMAL_FOOD_DETAIL_SENOND_VIEW) {
                            ToastUtils.showShortToast(context,context.getString(R.string.module_menu_check_input_account_num_than_zero));
                            return;
                        }
                        if (mDialogNumber == 0 && from == CartHelper.DialogDateFrom.RETAIL_CUSTOM_FOOD_VIEW) {
                            ToastUtils.showShortToast(context,context.getString(R.string.module_menu_retail_check_input_count_must_than_zero));
                            return;
                        }
                        if (mDialogNumber != CartHelper.FoodNum.MIN_VALUE && startNum > 1 && mDialogNumber < startNum) {
                            ToastUtils.showShortToast(context, context.getString(R.string.module_menu_check_start_num,
                                    menuName, (int) startNum, unit));
                            return;
                        } else if (mDialogNumber > CartHelper.FoodNum.MAX_VALUE) {
                            ToastUtils.showShortToast(context, context.getString(R.string.module_menu_suit_sub_num_limit,
                                    menuName, String.valueOf(CartHelper.FoodNum.MAX_VALUE)));
                            mDialogNumber = CartHelper.FoodNum.MAX_VALUE;
                            return;
                        }
                    }
                }
                positiveListener.onClick(v, data, mDialogNumber);
                hideSoft(v);
                inputDialog.dismiss();
            }
        });
        /**
         * 点击减号
         */
        mEditCountMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from == CartHelper.DialogDateFrom.CART_NORMAL_FOOD_DETAIL_SENOND_VIEW) {
                    if (mDialogNumber - 1 > CartHelper.FoodNum.MIN_VALUE) {
                        mDialogNumber = mDialogNumber - 1;
                    }
                } else {
                    mDialogNumber = mDialogNumber - 1;
                    if (startNum > 1 && startNum >= mDialogNumber + 1) {
                        ToastUtils.showShortToast(context, context.getString(R.string.module_menu_check_start_num,
                                menuName, (int) startNum, unit));
                        mDialogNumber = CartHelper.FoodNum.MIN_VALUE;
                    } else if (mDialogNumber < CartHelper.FoodNum.MIN_VALUE) {
                        mDialogNumber = CartHelper.FoodNum.MIN_VALUE;
                    }
                }
                setEditText(mEditInputValue, String.valueOf(mDialogNumber));
                mEditInputValue.setSelection(mEditInputValue.getText().length());
            }
        });
        /**
         * 点击加号
         */
        mEditCountPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialogNumber = mDialogNumber + 1;
                if (from == CartHelper.DialogDateFrom.SUIT_DETAIL) {
                    MenuVO dialogMenuVo = (MenuVO) data;
                    if (!dialogMenuVo.isNoLimit()) {
                        checkSuitFoodLimit(dialogMenuVo);  // 检查单个菜限制
                    }
                    if (!dialogMenuVo.getSuitGroupVO().isNoLimit()) {  // 套餐组限制
                        if (checkSuitLimit(dialogMenuVo) == false) {
                            mDialogNumber = dialogMenuVo.getSuitGroupVO().getNum() - otherFoodNum;
                        }
                    }
                } else {
                    if (mDialogNumber >= CartHelper.FoodNum.MAX_VALUE) {
                        ToastUtils.showShortToast(context, context.getString(R.string.module_menu_suit_sub_num_limit,
                                menuName, String.valueOf(CartHelper.FoodNum.MAX_VALUE)));
                        mDialogNumber = CartHelper.FoodNum.MAX_VALUE;
                    }
                }

                setEditText(mEditInputValue, String.valueOf(mDialogNumber));
                mEditInputValue.setSelection(mEditInputValue.getText().length());
            }
        });
        /**
         *  监听键盘点击“完成/Done”代表输入完成
         */
        mEditInputValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    String editStr = mEditInputValue.getText().toString();
                    //一开始是0，输入1，就变成了01，所以要重新设置EditText的值为1
                    if (editStr.length() > 1 && editStr.startsWith("0")) {
                        setEditText(mEditInputValue, editStr.substring(1));
                        mEditInputValue.setSelection(mEditInputValue.length());
                    }
                    if (editStr.length() >= 1 && editStr.startsWith(".")) {
                        setEditText(mEditInputValue, "0" + editStr);
                        mEditInputValue.setSelection(mEditInputValue.length());
                    }
                    if (editStr.length() > 1 && editStr.endsWith(".")) {
                        setEditText(mEditInputValue, editStr.substring(0, editStr.length() - 1));
                        mEditInputValue.setSelection(mEditInputValue.length());
                    }
                    mEditInputValue.clearFocus();
                    CustomViewUtil.initEditViewFocousAll(mEditInputValue);
                    hideSoft(mEditInputValue);
                }
                return true;
            }
        });
        /**
         * 点击返回按钮收起输入法
         */
        mEditInputValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_BACK) {
                    hideSoft(v);
                }
                return false;
            }
        });
        return inputDialog;
    }

    //==========================================================
    // 输入框内容相关
    //==========================================================

    /**
     * 处理输入框内容变化
     *
     * @param
     */
    private void handleEditTextChange(Editable s, boolean deleteLastChar) {
        if (!TextUtils.isEmpty(s)) {
            String editStr = s.toString();
            if (deleteLastChar) {
                // 设置新的截取的字符串
                setEditText(mEditInputValue, editStr.substring(0, editStr.length() - 1), true);
            }

            double tmpCount;
            try {
                tmpCount = Double.parseDouble(mEditInputValue.getText().toString());
                if (CartHelper.FoodNum.MAX_VALUE != -1 && tmpCount > CartHelper.FoodNum.MAX_VALUE) {
                    if (TextUtils.isDigitsOnly(s)) {
                        setEditText(mEditInputValue, String.valueOf((int) mDialogNumber));
                    } else {
                        setEditText(mEditInputValue, String.valueOf(mDialogNumber));
                    }
                    if (null != mOnInputExceedListener) {
                        mOnInputExceedListener.OnInputExceed(CartHelper.FoodNum.MAX_VALUE);
                    }
                } else {
                    //如果是0打头，截取掉首字符0
                    if (editStr.length() > 1 && editStr.startsWith("0") && editStr.charAt(1) != '.') {
                        setEditText(mEditInputValue, editStr.substring(1));
                    } else {
                        mDialogNumber = tmpCount;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            setEditText(mEditInputValue, "0");
            mDialogNumber = 0;
        }
    }

    private void setEditText(EditText editText, String text) {
        setEditText(editText, text, false);
    }

    /**
     * 修改输入框内容
     *
     * @param editText
     * @param text
     * @param isByInput 是否是通过手动输入
     */
    private void setEditText(EditText editText, String text, boolean isByInput) {
        try {
            double doubleValue = Double.parseDouble(text);
            //如果内容是整数并且不是通过手输，转成整型值
            if (!isByInput && NumberUtils.doubleIsInteger(doubleValue)) {
                editText.setText((int) doubleValue + "");
            } else {
                editText.setText(text);
            }
            mEditInputValue.setSelection(mEditInputValue.getText().length());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            editText.setText(text);
        }
    }

    //==========================================================
    // 套餐详情限额相关
    //==========================================================

    /**
     * 点击”+“、”确定“按钮进行套餐限额判断
     *
     * @param menuVO
     * @return
     */
    private boolean checkSuitLimit(MenuVO menuVO) {
        selectedFoodNumber = menuVO.getSuitGroupVO().getCurrentNum();   // 已经选的套餐数
        otherFoodNum = selectedFoodNumber - menuVO.getTmpNum();  // 点的组内其他菜的数量
        double hopeNum = menuVO.getSuitGroupVO().getNum() - otherFoodNum;
        if (hopeNum < mDialogNumber) {
            ToastUtils.showShortToast(context,
                    context.getString(R.string.module_menu_suit_detail_group_num_limit),
                    menuVO.getSuitGroupName(), NumberUtils.trimPointIfZero(mDialogNumber - hopeNum));
            return false;
        }
        return true;
    }

    /**
     * 点击”+“、”确定“按钮进行套餐内单菜限额判断
     *
     * @param menuVO
     * @return
     */
    private boolean checkSuitFoodLimit(MenuVO menuVO) {
        if ((mDialogNumber > menuVO.getLimitNum())) {
            ToastUtils.showShortToast(context,
                    context.getString(R.string.module_menu_suit_sub_num_limit),
                    menuVO.getMenuName(), NumberUtils.trimPointIfZero(menuVO.getLimitNum()));
            mDialogNumber = menuVO.getLimitNum();
            return false;
        }
        return true;
    }

    //==========================================================
    // 输入法相关
    //==========================================================

    /**
     * 对话框中自动弹出输入法
     *
     * @param context
     */
    public static void autoShowInputMethod(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtils.toggleSoftInput(context);
            }
        }, 100);
    }

    private void hideSoft(View view) {
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //==========================================================
    // 接口
    //==========================================================

    /**
     * 监听输入数量超过最大数值
     */
    public interface OnInputExceedListener {
        void OnInputExceed(double maxValue);
    }

    public interface DialogPositiveListener {
        void onClick(View v, Object data, double number);
    }

    public interface DialogNegativeListener {
        void onClick();
    }

}
