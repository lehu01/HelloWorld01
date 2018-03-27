package com.zmsoft.ccd.helper;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.constant.SPConstants;
import com.zmsoft.ccd.lib.utils.NetworkUtils;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.missile.MissileConsoles;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 推送设置辅助类
 *
 * @author DangGui
 * @create 2017/3/30.
 */

public class PushSettingHelper {
    /**
     * alias所需前缀
     */
    public static final String ALIAS_PREFIX = "NB_";
    private static final int MSG_SET_TAGS_ALIAS = 1001;

    /**
     * 设置别名
     *
     * @param alias 别名
     */
    public static void setTagAndAlias(String tag, String alias, boolean forceRegister) {
        String sPtags = SPUtils.getInstance(GlobalVars.context).getString(SPConstants.PushSetting.JPUSH_TAG);
        String sPalias = SPUtils.getInstance(GlobalVars.context).getString(SPConstants.PushSetting.JPUSH_ALIAS);
        if (!forceRegister && !TextUtils.isEmpty(sPtags) && !TextUtils.isEmpty(sPalias)) {
            Logger.d("tags and alias has exist,dont need set.");
            return;
        }
        if (TextUtils.isEmpty(tag) && TextUtils.isEmpty(alias)) {
            return;
        }
        if (!isValidTagAndAlias(tag) && !isValidTagAndAlias(alias)) {
            return;
        }
        Set<String> tagSet = null;
        if (!TextUtils.isEmpty(tag)) {
            // ","隔开的多个 转换成 Set
            String[] sArray = tag.split(",");
            tagSet = new LinkedHashSet<>();
            for (String sTagItme : sArray) {
                if (!isValidTagAndAlias(sTagItme)) {
                    return;
                }
                tagSet.add(sTagItme);
            }
        }
        TagAndAlias tagAndAlias = new TagAndAlias(tagSet, alias);
        //调用JPush API设置Tag and Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS_ALIAS, tagAndAlias));
    }

    /**
     * 反注册，用在退出登录时,将tag和alias都置为空
     */
    public static void unRegisterPush() {
        TagAndAlias tagAndAlias = new TagAndAlias(new LinkedHashSet<String>(), "");
        MissileConsoles.instance().shutDown();
        //调用JPush API设置Tag and Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS_ALIAS, tagAndAlias));
    }

    /**
     * 设置通知提示方式 - 基础属性
     */
    public static void setStyleBasic(Context context) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        builder.statusBarDrawable = R.drawable.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    public static void setAddActionsStyle(Context context) {
        MultiActionsNotificationBuilder builder = new MultiActionsNotificationBuilder(context);
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(10, builder);
    }

    private static Handler mHandler = new SettingHandler();

    private static class SettingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_TAGS_ALIAS:
                    Logger.d("Set tags and alias in handler.");
                    if (msg.obj instanceof TagAndAlias) {
                        TagAndAlias tagAndAlias = (TagAndAlias) msg.obj;
                        JPushInterface.setAliasAndTags(GlobalVars.context, tagAndAlias.getAlias()
                                , tagAndAlias.getTagSet(), mTagsAndAliasCallback);
                    } else {
                        JPushInterface.setAliasAndTags(GlobalVars.context, (String) msg.obj, null, mTagsAndAliasCallback);
                    }
                    break;
                default:
                    Logger.i("Unhandled msg - " + msg.what);
            }
        }
    }

    /**
     * 设置tags的回调
     */
    private static TagAliasCallback mTagsAndAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    if (TextUtils.isEmpty(alias) && (null == tags || tags.isEmpty())) {
                        logs = "unregister tag and alias success";
                        Logger.i(logs);
                        return;
                    }
                    if (null != tags) {
                        SPUtils.getInstance(GlobalVars.context).putString(SPConstants.PushSetting.JPUSH_TAG, tags.toString());
                    }
                    if (!TextUtils.isEmpty(alias)) {
                        SPUtils.getInstance(GlobalVars.context).putString(SPConstants.PushSetting.JPUSH_ALIAS, alias);
                    }
                    logs = "Set tag and alias success";
                    Logger.i(logs);
                    break;
                case 6002:
                    logs = "Failed to set  tags due to timeout. Try again after 60s.";
                    Logger.i(logs);
                    if (NetworkUtils.isConnected(GlobalVars.context)) {
                        TagAndAlias tagAndAlias = new TagAndAlias(tags, alias);
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS_ALIAS, tagAndAlias), 1000 * 60);
                    } else {
                        Logger.i("No network");
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Logger.e(logs);
            }
        }
    };

    //

    /**
     * 校验Tag Alias 只能是数字,英文字母和中文
     *
     * @param s Tag Alias
     * @return 是否合法
     */
    private static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    private static class TagAndAlias {
        private Set<String> tagSet;
        private String alias;

        private TagAndAlias(Set<String> tagSet, String alias) {
            this.tagSet = tagSet;
            this.alias = alias;
        }

        private Set<String> getTagSet() {
            return tagSet;
        }

        private void setTagSet(Set<String> tagSet) {
            this.tagSet = tagSet;
        }

        private String getAlias() {
            return alias;
        }

        private void setAlias(String alias) {
            this.alias = alias;
        }
    }
}
