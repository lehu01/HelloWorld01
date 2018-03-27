package com.zmsoft.ccd.module.personal.profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.utils.language.LanguageUtil;
import com.zmsoft.ccd.module.personal.profile.modifypwd.ModifyPwdActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zmsoft.ccd.module.personal.profile.ProfileActivity.PATH_PROFILE_ACTIVITY;

/**
 * 个人中心模块——个人信息页面
 *
 * @author DangGui
 * @create 2016/12/15.
 */
@Route(path = PATH_PROFILE_ACTIVITY)
public class ProfileActivity extends ToolBarActivity {

    public static final String PATH_PROFILE_ACTIVITY = "/main/profile";

    @BindView(R.id.image_userhead)
    ImageView mImageUserhead;
    @BindView(R.id.text_phone_number)
    TextView mTextPhoneNumber;
    @BindView(R.id.text_name)
    TextView mTextName;
    @BindView(R.id.text_sex)
    TextView mTextSex;
    @BindView(R.id.linear_modify_pwd)
    LinearLayout mLinearModifyPwd;
    /**
     * 登录用户
     */
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mUser = UserLocalPrefsCacheSource.getUser();
        if (null != mUser) {
            if (!TextUtils.isEmpty(mUser.getPicFullPath())) {
                ImageLoaderUtil.getInstance().loadImage(mUser.getPicFullPath(), mImageUserhead,
                        ImageLoaderOptionsHelper.getCcdAvatarOptions());
            } else {
                ImageLoaderUtil.getInstance().loadImage(R.drawable.icon_user_default
                        , mImageUserhead, ImageLoaderOptionsHelper.getCcdAvatarOptions());
            }
            if (!TextUtils.isEmpty(mUser.getMobile())) {
                mTextPhoneNumber.setText(mUser.getMobile());
            }
            if (!TextUtils.isEmpty(mUser.getMemberName())) {
                mTextName.setText(mUser.getMemberName());
            }
            if (!TextUtils.isEmpty(mUser.getSex())) {
                mTextSex.setText(translateSex(mUser.getSex()));
            }
        }
    }

    @OnClick(R.id.linear_modify_pwd)
    public void onClick() {
        MRouter.getInstance().build(ModifyPwdActivity.PATH_MODIFY_PWD).navigation(this);
    }


    /**
     * 翻译性别文案
     * 场景:未国际化版本升级到国际化版本后，本地存储sex仍旧为中文，在此处处理成英文
     * 在英文环境下登录或切店，可以返回正确的英文。所以只是临时使用，预计在2018-06后可以移除
     * @time 2017-12-22
     */
    private interface SEX_CHINESE {
        String UNKNOWN = "未知";
        String MALE = "男";
        String FEMALE = "女";
    }

    private static String translateSex(String origin) {
        if (LanguageUtil.isChineseGroup()) {
            return origin;
        }
        if (SEX_CHINESE.UNKNOWN.equals(origin)) {
            return GlobalVars.context.getString(R.string.unknown);
        } else if (SEX_CHINESE.MALE.equals(origin)) {
            return GlobalVars.context.getString(R.string.male);
        } else if (SEX_CHINESE.FEMALE.equals(origin)) {
            return GlobalVars.context.getString(R.string.female);
        }
        return origin;
    }

}
