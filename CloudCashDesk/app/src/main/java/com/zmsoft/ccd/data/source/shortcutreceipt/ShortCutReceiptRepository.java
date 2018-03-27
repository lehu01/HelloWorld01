package com.zmsoft.ccd.data.source.shortcutreceipt;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.shortcutreceipt.ShortCutReceiptResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author DangGui
 * @create 2017/8/2.
 */
@Singleton
public class ShortCutReceiptRepository implements IShortCutReceiptSource {

    private final IShortCutReceiptSource mReceiptSource;

    @Inject
    ShortCutReceiptRepository(@Remote IShortCutReceiptSource remoteSource) {
        this.mReceiptSource = remoteSource;
    }

    @Override
    public void shortCutReceipt(int fee, Callback<ShortCutReceiptResponse> callback) {
        mReceiptSource.shortCutReceipt(fee, callback);
    }
}
