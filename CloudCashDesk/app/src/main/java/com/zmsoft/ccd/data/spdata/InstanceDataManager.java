package com.zmsoft.ccd.data.spdata;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.local.OpInstanceVo;
import com.zmsoft.ccd.helper.InstanceHelper;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/23 14:45
 *     desc  : 菜肴数据处理
 * </pre>
 */
public class InstanceDataManager {

    /**
     * 获取普通菜肴操作vo
     *
     * @param instance 菜肴instance
     * @return list
     */
    public static List<OpInstanceVo> getDialogByOpNormalInstance(Instance instance) {
        if (instance == null) {
            return null;
        }
        List<OpInstanceVo> list = new ArrayList<>();
        /**
         * 1.催菜
         */
        OpInstanceVo pushInstance = new OpInstanceVo();
        pushInstance.setOpName(GlobalVars.context.getString(R.string.push_instance));
        list.add(pushInstance);
        /**
         * 2.退菜
         */
        OpInstanceVo cancelInstance = new OpInstanceVo();
        cancelInstance.setOpName(GlobalVars.context.getString(R.string.cancel_instance));
        list.add(cancelInstance);
        /**
         * 3.赠菜
         */
        if (InstanceHelper.isOpInsGive(instance)) {
            OpInstanceVo giveInstance = new OpInstanceVo();
            giveInstance.setOpName(GlobalVars.context.getString(R.string.give_this_instance));
            list.add(giveInstance);
        }
        /**
         * 4.修改价格
         */
        if (Base.INT_TRUE == instance.getIsChangePrice()) {
            OpInstanceVo updatePriceInstance = new OpInstanceVo();
            updatePriceInstance.setOpName(GlobalVars.context.getString(R.string.update_price));
            list.add(updatePriceInstance);
        }
        /**
         * 5.修改重量
         */
        if (Base.INT_TRUE == instance.getIsTwoAccount()) {
            OpInstanceVo updateWeightPrice = new OpInstanceVo();
            updateWeightPrice.setOpName(GlobalVars.context.getString(R.string.update_weight));
            list.add(updateWeightPrice);
        }
        /**
         * 6.补打标签纸
         */
        if (BaseSpHelper.isUseLabelPrinter(GlobalVars.context)) {
            OpInstanceVo reprintLabel = new OpInstanceVo();
            reprintLabel.setOpName(GlobalVars.context.getString(R.string.reprint_label_paper));
            list.add(reprintLabel);
        }
        return list;
    }

    /**
     * 获取套菜子菜的操作vo
     *
     * @param instance 菜肴
     * @return list
     */
    public static List<OpInstanceVo> getDialogByOpSuitChildInstance(Instance instance) {
        if (instance == null) {
            return null;
        }
        List<OpInstanceVo> list = new ArrayList<>();
        /**
         * 1.催菜
         */
        OpInstanceVo pushInstance = new OpInstanceVo();
        pushInstance.setOpName(GlobalVars.context.getString(R.string.push_instance));
        list.add(pushInstance);
        /**
         * 2.退菜
         */
        OpInstanceVo cancelInstance = new OpInstanceVo();
        cancelInstance.setOpName(GlobalVars.context.getString(R.string.cancel_instance));
        list.add(cancelInstance);
        /**
         * 3.修改重量
         */
        if (Base.INT_TRUE == instance.getIsTwoAccount()) {
            OpInstanceVo updateWeightPrice = new OpInstanceVo();
            updateWeightPrice.setOpName(GlobalVars.context.getString(R.string.update_weight));
            list.add(updateWeightPrice);
        }
        /**
         * 4.补打标签纸
         */
        if (BaseSpHelper.isUseLabelPrinter(GlobalVars.context)) {
            OpInstanceVo reprintLabel = new OpInstanceVo();
            reprintLabel.setOpName(GlobalVars.context.getString(R.string.reprint_label_paper));
            list.add(reprintLabel);
        }
        return list;
    }

    /**
     * 获取普通菜肴操作string
     *
     * @param list
     * @return list<string>
     */
    public static List<String> getDialogByOpInstance(List<OpInstanceVo> list) {
        if (list == null) {
            return null;
        }
        List<String> resultList = new ArrayList<>();
        for (OpInstanceVo opInstanceVo : list) {
            resultList.add(opInstanceVo.getOpName());
        }
        return resultList;
    }
}
