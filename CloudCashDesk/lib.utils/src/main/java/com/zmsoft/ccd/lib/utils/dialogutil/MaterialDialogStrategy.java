package com.zmsoft.ccd.lib.utils.dialogutil;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.zmsoft.ccd.lib.utils.R;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.ListCallbackSingleChoice;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;

import java.util.List;

/**
 * MaterialDialog加载策略(默认策略)
 *
 * @author DangGui
 * @create 2017/2/6.
 */

public class MaterialDialogStrategy implements BaseDialogStrategy {
    private Context mContext;

    public MaterialDialogStrategy(Context context) {
        mContext = context;
    }

    @Override
    public Dialog showDialog(int contentTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).content(contentTextRes).positiveText(R.string.material_dialog_sure)
                .negativeText(R.string.material_dialog_cancel).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor)).cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).content(contentText).positiveText(R.string.material_dialog_sure)
                .negativeText(R.string.material_dialog_cancel).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor)).cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(int titleTextRes, int contentTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes).content(contentTextRes)
                .positiveText(R.string.material_dialog_sure).negativeText(R.string.material_dialog_cancel).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor)).cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(int titleTextRes, String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes).content(contentText)
                .positiveText(R.string.material_dialog_sure).negativeText(R.string.material_dialog_cancel).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor)).cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(String titleText, String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleText).content(contentText)
                .positiveText(R.string.material_dialog_sure).negativeText(R.string.material_dialog_cancel).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor)).cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(int contentTextRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).content(contentTextRes).positiveText(positiveTextRes)
                .negativeText(negativeTextRes).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor)).cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(int titleTextRes, int contentTextRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes).content(contentTextRes).positiveText(positiveTextRes)
                .negativeText(negativeTextRes).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor)).cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(int titleTextRes, String contentText, int positiveTextRes, int negativeTextRes,
                             boolean cancelable, final SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes).
                content(contentText).positiveText(positiveTextRes).negativeText(negativeTextRes)
                .negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(int titleTextRes, int contentTextRes, int positiveTextRes, int negativeTextRes, int neutralTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes).content(contentTextRes).positiveText(positiveTextRes)
                .negativeText(negativeTextRes).neutralText(neutralTextRes).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showDialog(int titleTextRes, String contentText, int positiveTextRes, int negativeTextRes, int neutralTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes).content(contentText).positiveText(positiveTextRes)
                .negativeText(negativeTextRes).neutralText(neutralTextRes).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showIndeterminateProgressDialog(boolean cancelable) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).progress(true, 0).progressIndeterminateStyle(false).cancelable(cancelable);
        return builder.show();
    }

    @Override
    public Dialog showIndeterminateProgressDialog(int contentTextRes, boolean cancelable) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).content(contentTextRes).progress(true, 0)
                .progressIndeterminateStyle(false).cancelable(cancelable);
        return builder.show();
    }

    @Override
    public Dialog showIndeterminateProgressDialog(String contentTextRes, boolean cancelable) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).content(contentTextRes).progress(true, 0)
                .progressIndeterminateStyle(false).cancelable(cancelable);
        return builder.show();
    }

    @Override
    public Dialog showIndeterminateProgressDialog(boolean horizontal, int contentTextRes, boolean cancelable) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).content(contentTextRes).progress(true, 0)
                .progressIndeterminateStyle(horizontal).cancelable(cancelable);
        return builder.show();
    }

    @Override
    public Dialog showNoticeDialog(int titleTextRes, String contentText, int positiveTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes).content(contentText)
                .positiveText(positiveTextRes).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showSingleChoiceDialog(int titleTextRes, String[] items, int selectedIndex, final ListCallbackSingleChoice listCallbackSingleChoice, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes)
                .items(items)
                .negativeText(R.string.material_dialog_cancel)
                .negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .alwaysCallSingleChoiceCallback().cancelable(cancelable);
        if (null != listCallbackSingleChoice) {
            builder.itemsCallbackSingleChoice(selectedIndex,
                    new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            listCallbackSingleChoice.onSelection(which, text);
                            return true;
                        }
                    });
        }
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showSingleChoiceDialog(int titleTextRes, List<String> items, int selectedIndex, final ListCallbackSingleChoice listCallbackSingleChoice, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes)
                .items(items)
                .negativeText(R.string.material_dialog_cancel)
                .negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .alwaysCallSingleChoiceCallback().cancelable(cancelable);
        if (null != listCallbackSingleChoice) {
            builder.itemsCallbackSingleChoice(selectedIndex,
                    new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            listCallbackSingleChoice.onSelection(which, text);
                            return true;
                        }
                    });
        }
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showSelectListDialog(int itemsRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).items(itemsRes).cancelable(cancelable)
                .negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showSelectListDialog(int titleTextRes, int itemsRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes).items(itemsRes)
                .cancelable(cancelable).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showStackedDialog(int titleTextRes, int contentTextRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes)
                .content(contentTextRes).positiveText(positiveTextRes)
                .negativeText(negativeTextRes).btnStackedGravity(GravityEnum.END).forceStacking(false).cancelable(cancelable)
                .negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public Dialog showCustomViewDialog(int customViewLayoutRes, boolean cancelable) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).customView(customViewLayoutRes, false)
                .cancelable(cancelable).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        return builder.show();
    }

    @Override
    public Dialog showCustomViewDialog(View view, String title, boolean cancelable) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).customView(view, true)
                .title(title).cancelable(cancelable).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        return builder.show();
    }

    @Override
    public Dialog showCustomViewDialog(View view, boolean cancelable) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).customView(view, true)
                .cancelable(cancelable).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        return builder.show();
    }

    @Override
    public Dialog showCustomViewDialog(int titleTextRes, int customViewLayoutRes, boolean cancelable) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).title(titleTextRes)
                .customView(customViewLayoutRes, false)
                .cancelable(cancelable).negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        return builder.show();
    }

    @Override
    public MaterialDialog showCustomViewDialog(int titleTextRes, int customViewLayoutRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext).customView(customViewLayoutRes, false)
                .title(titleTextRes)
                .positiveText(positiveTextRes)
                .negativeText(negativeTextRes)
                .negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public MaterialDialog showCustomViewDialog(int titleTextRes, View view, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .title(titleTextRes)
                .customView(view, false)
                .positiveText(positiveTextRes)
                .negativeText(negativeTextRes)
                .negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    @Override
    public MaterialDialog showCustomViewDialog(int titleTextRes, View view, int positiveTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .title(titleTextRes)
                .customView(view, false)
                .positiveText(positiveTextRes)
                .autoDismiss(false)
                .negativeColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor))
                .cancelable(cancelable);
        if (null != singleButtonCallback) {
            handleCallback(builder, singleButtonCallback);
        }
        return builder.show();
    }

    private void handleCallback(MaterialDialog.Builder builder, final SingleButtonCallback singleButtonCallback) {
        builder.onAny(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                switch (which) {
                    case POSITIVE:
                        singleButtonCallback.onClick(DialogUtilAction.POSITIVE);
                        break;
                    case NEGATIVE:
                        singleButtonCallback.onClick(DialogUtilAction.NEGATIVE);
                        break;
                    case NEUTRAL:
                        singleButtonCallback.onClick(DialogUtilAction.NEUTRAL);
                        break;
                }
            }
        });
    }
}
