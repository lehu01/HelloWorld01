package com.zmsoft.ccd.lib.bean.order.takeout;

import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/31 11:34
 *     desc  : 外卖统计分类
 * </pre>
 */
public class TakeoutKindMenuInfo {

    /**
     * num : 1
     * takeoutKindMenuParams : [{"num":2,"name":"热菜"}]
     */

    private int num;
    private List<TakeoutKindMenu> takeoutKindMenuParams;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<TakeoutKindMenu> getTakeoutKindMenuParams() {
        return takeoutKindMenuParams;
    }

    public void setTakeoutKindMenuParams(List<TakeoutKindMenu> takeoutKindMenuParams) {
        this.takeoutKindMenuParams = takeoutKindMenuParams;
    }

    public static class TakeoutKindMenu {
        /**
         * num : 2
         * name : 热菜
         */
        private int num;
        private String name;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
