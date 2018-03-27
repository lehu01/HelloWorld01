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
 * 用来标记model层的component 例如{@link com.zmsoft.ccd.data.UserComponent}
 *
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelScoped {
}
