package com.zmsoft.ccd.module.takeout;

import com.zmsoft.ccd.module.takeout.order.source.dagger.DaggerTakeoutSourceComponent;
import com.zmsoft.ccd.module.takeout.order.source.dagger.TakeoutSourceComponent;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class DaggerCommentManager {

    private static DaggerCommentManager instance;

    private TakeoutSourceComponent takeoutSourceComponent;

    private DaggerCommentManager() {
    }

    public static synchronized DaggerCommentManager get() {
        if (instance == null) {
            instance = new DaggerCommentManager();
        }
        return instance;
    }


    public TakeoutSourceComponent getTakeoutSourceComponent() {
        if (takeoutSourceComponent == null) {
            takeoutSourceComponent = DaggerTakeoutSourceComponent.builder().build();
        }
        return takeoutSourceComponent;
    }

}
