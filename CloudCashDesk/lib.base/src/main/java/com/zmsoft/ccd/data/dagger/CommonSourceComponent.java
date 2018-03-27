package com.zmsoft.ccd.data.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.repository.CommonRepository;

import dagger.Component;

/**
 * @author DangGui
 * @create 2017/5/4.
 */
@ModelScoped
@Component(modules = {CommonSourceModule.class})
public interface CommonSourceComponent {

    CommonRepository getBaseRepository();
}