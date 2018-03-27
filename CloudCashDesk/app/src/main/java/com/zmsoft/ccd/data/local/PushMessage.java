package com.zmsoft.ccd.data.local;

import org.litepal.crud.DataSupport;

/**
 * 接收到的推送消息<br />
 * 由于云收银同时接入了极光推送和内部的TCP通道推送，需要将消息缓存到内存以及本地，做去重处理
 *
 * @author DangGui
 * @create 2017/6/10.
 */
public class PushMessage extends DataSupport {
    /**
     * 数据库主键字段ID,其中id这个字段可写可不写，因为即使不写这个字段，LitePal也会在表中自动生成一个id列。
     */
    private int id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 消息的唯一标示
     */
    private String pushMsgId;

    /**
     * 消息的创建时间
     */
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPushMsgId() {
        return pushMsgId;
    }

    public void setPushMsgId(String pushMsgId) {
        this.pushMsgId = pushMsgId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
