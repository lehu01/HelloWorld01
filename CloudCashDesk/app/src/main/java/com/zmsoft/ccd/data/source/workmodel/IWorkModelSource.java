package com.zmsoft.ccd.data.source.workmodel;

import com.zmsoft.ccd.data.Callback;

import java.util.List;
import java.util.Map;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/31 10:13
 */
public interface IWorkModelSource {

    /**
     * 获取工作模式
     *
     * @param entityId
     * @param codeList
     * @param callback
     */
    void getWorkModel(String entityId, List<String> codeList, Callback<Map<String, String>> callback);

    /**
     * 保存工作模式
     *
     * @param entityId
     * @param openCloudCash
     * @param useLocalCash
     * @param callback
     */
    void saveWorkModelConfig(String entityId, boolean openCloudCash, boolean useLocalCash, String userId, Callback<Boolean> callback);

}
