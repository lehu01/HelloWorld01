package com.zmsoft.ccd.helper;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;

import java.util.ArrayList;
import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/17 17:33
 */
public class ReasonHelper {

    /**
     * 添加默认的选中无
     */
    public static List<Reason> getAddDefaultReasonList(List<Reason> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        Reason reason = new Reason();
        reason.setName(GlobalVars.context.getString(R.string.nix));
        data.add(0, reason);
        return data;
    }

    /**
     * 获取选中的位置
     */
    public static int getCheckIndex(String name, List<Reason> data) {
        int index = 0;
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getName().equals(name)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * 获取打印字符数
     */
    public static List<Reason> getPrintByteList() {
        String[] str = new String[]{"32", "33", "38", "40", "42", "48", "64"};
        List<Reason> list = new ArrayList<>();
        for (int i = 0; i < str.length - 1; i++) {
            Reason reason = new Reason();
            reason.setName(str[i]);
            list.add(reason);
        }
        return list;
    }
}
