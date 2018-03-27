package com.zmsoft.ccd.data.source.systemdircode;

import com.zmsoft.ccd.data.source.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 15:03
 */
@Singleton
public class SystemDirCodeSourceRepository implements ISystemDirCodeSource {

    private final ISystemDirCodeSource mISystemDirCodeSource;

    @Inject
    public SystemDirCodeSourceRepository(@Remote ISystemDirCodeSource iSystemDirCodeSource) {
        mISystemDirCodeSource = iSystemDirCodeSource;
    }

}
