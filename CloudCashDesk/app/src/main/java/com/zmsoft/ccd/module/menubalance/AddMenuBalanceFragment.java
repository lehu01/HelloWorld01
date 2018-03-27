package com.zmsoft.ccd.module.menubalance;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.menubalance.Menu;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.module.menubalance.constant.MenuBalanceConstant;
import com.zmsoft.ccd.widget.bottomdailog.BottomDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class AddMenuBalanceFragment extends BaseFragment implements AddMenuBalanceContract.View {

    private final static String ARGUMENT_MENU = "menu";


    @BindView(R.id.sold_out_instance_name)
    TextView instanceName;
    @BindView(R.id.text_sold_out_instance_status)
    TextView mTextSoldOutInstanceStatus;
    @BindView(R.id.sold_out_no_container)
    RelativeLayout soldOutNOContainer;
    @BindView(R.id.input_instance_last_no)
    EditText inputInstanceLastNO;

    private Menu menu = new Menu();
    private BottomDialog mStatusBottomDialog;
    private String[] mDialogTextArray;
    private int mSoldOutStatus;

    private AddMenuBalancePresenter mPresenter;

    public static AddMenuBalanceFragment newInstance(Menu menu) {

        Bundle args = new Bundle();

        AddMenuBalanceFragment fragment = new AddMenuBalanceFragment();
        args.putParcelable(ARGUMENT_MENU, menu);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        if (bundle != null) {
            menu = bundle.getParcelable(ARGUMENT_MENU);
        }
    }

    private void initView() {
        mSoldOutStatus = MenuBalanceConstant.SOLD_STATUS.ALREADY_SOLD_OUT;
        instanceName.setText(menu.getMenuName());
        mDialogTextArray = getResources().getStringArray(R.array.sold_out_status);

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

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent i = new Intent();
        i.putExtra("add_result", false);
        getActivity().setResult(Activity.RESULT_FIRST_USER, i);
        getActivity().finish();
    }

    @Override
    public void addMenuBalanceSuccess() {
        EventBusHelper.post(BaseEvents.CommonEvent.EVENT_BALANCE_REFRESH);
        Intent i = new Intent();
        i.putExtra("add_result", true);
        getActivity().setResult(Activity.RESULT_FIRST_USER, i);
        getActivity().finish();
    }

    @Override
    public void addMenuBalanceFailure() {
        Toast.makeText(getContext(), getString(R.string.add_menu_balance_failure), Toast.LENGTH_LONG).show();
    }

    public void setPresenter(AddMenuBalanceContract.Presenter presenter) {
        mPresenter = (AddMenuBalancePresenter) presenter;
    }

    public void addMenuBalance() {
        Double balanceNo;
        if (MenuBalanceConstant.SOLD_STATUS.ALREADY_SOLD_OUT == mSoldOutStatus) {
            balanceNo = 0.0d;
        } else {
            String input = inputInstanceLastNO.getText().toString();
            if (StringUtils.isEmpty(input) || input.equals("0.")) {
                ToastUtils.showShortToast(getContext(), getString(R.string.menu_balance_num_input_null));
                return;
            } else {
                balanceNo = Double.parseDouble(inputInstanceLastNO.getText().toString());
                if (balanceNo > 1000d) {
                    ToastUtils.showShortToast(getContext(), R.string.menu_balance_num_input_error);
                    return;
                }
            }
        }
        mPresenter.addMenuBalance(UserHelper.getEntityId(), menu.getMenuId(),
                balanceNo, UserHelper.getUserId());
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
        inputInstanceLastNO.setText(null);
        inputInstanceLastNO.setEnabled(false);

        int delta = soldOutNOContainer.getHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(soldOutNOContainer, "translationY", delta, 0);
        animator.setDuration(300);
        animator.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_menu_balance;
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
        mPresenter.unsubscribe();
    }

    //================================================================================
    // onClick
    //================================================================================
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
                        if (mSoldOutStatus != MenuBalanceConstant.SOLD_STATUS.ALREADY_SOLD_OUT) {
                            mSoldOutStatus = MenuBalanceConstant.SOLD_STATUS.ALREADY_SOLD_OUT;
                            hideSoldOutNOContainer();
                        }
                    } else if (MenuBalanceConstant.DIALOG_ITEM_POSITION.NOT_SOLD_OUT == position) {
                        if (mSoldOutStatus != MenuBalanceConstant.SOLD_STATUS.NOT_SOLD_OUT) {
                            mSoldOutStatus = MenuBalanceConstant.SOLD_STATUS.NOT_SOLD_OUT;
                            showSoldOutNOContainer();
                        }
                    }

                    mStatusBottomDialog.dismiss();
                }
            });
        }
        if (!mStatusBottomDialog.isShowing()) {
            mStatusBottomDialog.showPopupWindow();
        }
    }
}
