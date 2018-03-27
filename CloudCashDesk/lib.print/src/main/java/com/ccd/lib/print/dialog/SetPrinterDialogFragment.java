package com.ccd.lib.print.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ccd.lib.print.R;
import com.ccd.lib.print.helper.PrintChooseHelper;
import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.utils.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/7/27 14:04
 *     desc  : 打印机配置引导弹窗
 * </pre>
 */
public class SetPrinterDialogFragment extends DialogFragment {

    public static final String FRAGMENT_TAG = SetPrinterDialogFragment.class.getSimpleName();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DialogUtil dialogUtil = new DialogUtil(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_setting_printer, null);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrintChooseHelper.saveNoPrompt(isChecked);
            }
        });
        return dialogUtil.showCustomViewDialog(R.string.prompt
                , view
                , R.string.go_setting
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            MRouter.getInstance().build(RouterPathConstant.PrintConfig.PATH)
                                    .navigation(getActivity());
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }
}
