package com.zmsoft.ccd.module.menubalance;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.module.menubalance.constant.MenuBalanceConstant;
import com.zmsoft.ccd.widget.bottomdailog.BottomDialog;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class EditMenuBalanceFragment extends BaseFragment implements EditMenuBalanceContract.View {

    @BindView(R.id.sold_out_instance_name)
    TextView tvSoldoutInstanceName;

    @BindView(R.id.text_sold_out_instance_status)
    TextView mTextSoldOutInstanceStatus;

    @BindView(R.id.sold_out_no_container)
    RelativeLayout soldOutNOContainer;

    @BindView(R.id.input_instance_last_no)
    EditText inputInstanceLastNO;

    @BindView(R.id.bt_cancel_sold_out)
    Button btCancelSoldOut;

    private boolean isOriginStatusSoldOut;          // 进入页面前的沽清状况
    private boolean isNowStatusSoldOut;             // 当前的沽清状况

    private MenuBalanceVO balanceVO;

    private BottomDialog mStatusBottomDialog;
    private String[] mDialogTextArray;

    private MenuItemCallback menuItemCallback;


    @Inject
    EditMenuBalancePresenter presenter;

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_menu_balance;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        presenter.unsubscribe();
    }

    //================================================================================
    // EditMenuBalanceContract.View
    //================================================================================
    @Override
    public void cancelMenuBalanceSuccess() {
        EventBusHelper.post(BaseEvents.CommonEvent.EVENT_BALANCE_REFRESH);
        getActivity().finish();
    }

    @Override
    public void cancelMenuBalanceFailure() {
        ToastUtils.showShortToast(getContext(), R.string.cancel_menu_balance_failure);
    }

    @Override
    public void updateMenuBalanceSuccess() {
        EventBusHelper.post(BaseEvents.CommonEvent.EVENT_BALANCE_REFRESH);
        getActivity().finish();
    }

    @Override
    public void updateMenuBalanceFailure() {
        ToastUtils.showShortToast(getContext(), R.string.update_menu_balance_failure);
    }

    public void setPresenter(EditMenuBalanceContract.Present presenter) {
        this.presenter = (EditMenuBalancePresenter) presenter;
    }

    //================================================================================
    // init
    //================================================================================
    private void initView() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        balanceVO = bundle.getParcelable("menu_balance");
        if (balanceVO == null) {
            return;
        }
        tvSoldoutInstanceName.setText(balanceVO.getMenuName());

        isOriginStatusSoldOut = balanceVO.getBalanceNum() == 0;
        isNowStatusSoldOut = isOriginStatusSoldOut;

        mDialogTextArray = getResources().getStringArray(R.array.sold_out_status);

        if (!isOriginStatusSoldOut) {
            inputInstanceLastNO.setText(balanceVO.getBalanceNum() + "");

            Observable.timer(100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            showSoldOutNOContainer();
                        }
                    });
        } else {
            mTextSoldOutInstanceStatus.setText(mDialogTextArray[MenuBalanceConstant.DIALOG_ITEM_POSITION.ALREADY_SOLD_OUT]);
        }

        inputInstanceLastNO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        inputInstanceLastNO.setText(s);
                        inputInstanceLastNO.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    inputInstanceLastNO.setText(s);
                    inputInstanceLastNO.setSelection(2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (StringUtils.isEmpty(s1) || s1.equals(".")) {
                    return;
                }
                double input = Double.parseDouble(s1);

                if (menuItemCallback != null) {
                    menuItemCallback.setMenuItemVisible(!isOriginStatusSoldOut || balanceVO.getBalanceNum() != input);
                }
            }
        });
    }

    //================================================================================
    // update view
    //================================================================================
    public void updateMenuBalance() {
        double lastNO;
        if (isNowStatusSoldOut) {
            lastNO = 0.0;
        } else {
            String input = inputInstanceLastNO.getText().toString();
            if (StringUtils.isEmpty(input) || input.equals(".")) {
                ToastUtils.showShortToast(getContext(), getString(R.string.menu_balance_num_input_null));
                return;
            } else {
                lastNO = Double.parseDouble(input);
                if (lastNO > 1000d) {
                    ToastUtils.showShortToast(getContext(), R.string.menu_balance_num_input_error);
                    return;
                }
            }
        }
        presenter.updateMenuBalance(UserHelper.getEntityId(), balanceVO.getMenuId(),
                lastNO, UserHelper.getUserId());
    }

    private void showSoldOutNOContainer() {
        mTextSoldOutInstanceStatus.setText(mDialogTextArray[MenuBalanceConstant.DIALOG_ITEM_POSITION.NOT_SOLD_OUT]);
        soldOutNOContainer.setVisibility(View.VISIBLE);
        inputInstanceLastNO.setEnabled(true);

        int delta = soldOutNOContainer.getHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(soldOutNOContainer, "translationY", 0, delta);
        animator.setDuration(300);
        animator.start();
    }

    private void hideSoldOutNOContainer() {
        mTextSoldOutInstanceStatus.setText(mDialogTextArray[MenuBalanceConstant.DIALOG_ITEM_POSITION.ALREADY_SOLD_OUT]);
        inputInstanceLastNO.setEnabled(false);

        int delta = soldOutNOContainer.getHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(soldOutNOContainer, "translationY", delta, 0);
        animator.setDuration(300);
        animator.start();
    }

    //================================================================================
    // onClick
    //================================================================================
    @OnClick(R.id.bt_cancel_sold_out)
    void buttonClicked() {
        presenter.cancelMenuBalance(UserHelper.getEntityId(), balanceVO.getMenuId(),
                UserHelper.getUserId());
    }

    @OnClick(R.id.text_sold_out_instance_status)
    public void onClickSoldOutInstanceStatus() {
        if (null == mStatusBottomDialog) {
            mStatusBottomDialog = new BottomDialog(getActivity());
            // 设置内容
            mStatusBottomDialog.setItems(mDialogTextArray);
            // 设置点击事件
            mStatusBottomDialog.setItemClickListener(new BottomDialog.PopupWindowItemClickListener(){
                @Override
                public void onItemClick(int position) {
                    if (MenuBalanceConstant.DIALOG_ITEM_POSITION.ALREADY_SOLD_OUT == position) {
                        if (!isNowStatusSoldOut) {
                            isNowStatusSoldOut = true;
                            hideSoldOutNOContainer();
                        }
                    } else if (MenuBalanceConstant.DIALOG_ITEM_POSITION.NOT_SOLD_OUT == position) {
                        if (isNowStatusSoldOut) {
                            isNowStatusSoldOut = false;
                            showSoldOutNOContainer();
                        }
                    }

                    if (menuItemCallback != null) {
                        menuItemCallback.setMenuItemVisible(isNowStatusSoldOut != isOriginStatusSoldOut);
                    }
                    mStatusBottomDialog.dismiss();
                }
            });
        }
        if (!mStatusBottomDialog.isShowing()) {
            mStatusBottomDialog.showPopupWindow();
        }
    }

    //================================================================================
    // setter
    //================================================================================
    public void setMenuItemCallback(MenuItemCallback callback) {
        menuItemCallback = callback;
    }
}
