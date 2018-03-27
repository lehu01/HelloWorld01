package com.zmsoft.ccd.module.menu.menu.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.widget.RoundedImageView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：同桌加入点菜
 * <br/>
 * Created by kumu on 2017/4/27.
 */
public class JoinTableTipDialog extends Dialog {


    @BindView(R2.id.image_close)
    ImageView mImageClose;
    @BindView(R2.id.image_menu_tip)
    ImageView mImageMenuTip;
    @BindView(R2.id.text_name)
    TextView mTextName;
    @BindView(R2.id.image_user_avatar)
    RoundedImageView mImageUserAvatar;

    private String mImagePath;
    private String mName;

    public JoinTableTipDialog(@NonNull Context context, String name, String imagePath) {
        super(context, R.style.ModuleMenuJoinTable);
        mImagePath = imagePath;
        mName = name;
    }

    public JoinTableTipDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected JoinTableTipDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_menu_dialog_join_table_tip);
        ButterKnife.bind(this);
        mImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTextName.setText(mName);

        ImageLoaderUtil.getInstance().loadImage(mImagePath, mImageUserAvatar);

    }
}
