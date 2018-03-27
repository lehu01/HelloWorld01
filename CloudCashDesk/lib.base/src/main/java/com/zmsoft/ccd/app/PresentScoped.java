package com.zmsoft.ccd.app;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 08/03/2017.
 *
 * 标记展现层的component 例如
 * {@link com.zmsoft.ccd.module.login.LoginComponent}
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PresentScoped {
}
