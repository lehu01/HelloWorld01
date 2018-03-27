package com.zmsoft.ccd.module.receipt.receipt.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;

import dagger.Component;

/**
 * @author DangGui
 * @create 2017/5/24.
 */
@ModelScoped
@Component(modules = {ReceiptSourceModule.class})
public interface ReceiptSourceComponent {

    ReceiptRepository getReceiptRepository();
}