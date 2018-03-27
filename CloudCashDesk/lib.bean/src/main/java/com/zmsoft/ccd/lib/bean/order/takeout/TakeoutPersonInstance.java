package com.zmsoft.ccd.lib.bean.order.takeout;

import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/31 11:15
 *     desc  :
 * </pre>
 */
public class TakeoutPersonInstance {

    /**
     * takeoutInstanceParams : [{"accountNum":1,"originalPrice":20,"num":1,"taste":"微辣","fee":20,"memo":"你好","kindMenuName":"面类","type":0,"specDetailName":"大份","kindMenuId":"e6e8f7a0aaec485ebc728994585d7182","modifyTime":1502955080,"ratioFee":20,"isWait":0,"children":[{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"children":[{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"children":[{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}],"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}],"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}],"price":20,"customerId":"e6e8f7a0aaec485ebc728994585d718e","menuId":"e6e8f7a0aaec485ebc728994585d7181","id":"e6e8f7a0aaec485ebc728994585d718e","isRatio":0,"kind":1,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":1,"accountUnit":"份","unit":"份","createTime":1502955080410,"name":"牛肉面","isGive":0,"isTwoAccount":0,"makeName":"红烧","fromCustomer":1,"status":105,"ratio":100}]
     * customerRegisterId : e6e8f7a0aaec485ebc728994585d718e
     * instanceNum : 1
     * orderDishTime : 1502955080410
     * fileUrl : http://noble.2dfire.net/static-noble/asset/img/logos.png
     * customerName : 小李
     */

    private String customerRegisterId;
    private int instanceNum;
    private long orderDishTime;
    private String fileUrl;
    private String customerName;
    private List<TakeoutInstanceParamsBean> takeoutInstanceParams;

    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }

    public int getInstanceNum() {
        return instanceNum;
    }

    public void setInstanceNum(int instanceNum) {
        this.instanceNum = instanceNum;
    }

    public long getOrderDishTime() {
        return orderDishTime;
    }

    public void setOrderDishTime(long orderDishTime) {
        this.orderDishTime = orderDishTime;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<TakeoutInstanceParamsBean> getTakeoutInstanceParams() {
        return takeoutInstanceParams;
    }

    public void setTakeoutInstanceParams(List<TakeoutInstanceParamsBean> takeoutInstanceParams) {
        this.takeoutInstanceParams = takeoutInstanceParams;
    }

    public static class TakeoutInstanceParamsBean {
        /**
         * accountNum : 1
         * originalPrice : 20
         * num : 1
         * taste : 微辣
         * fee : 20
         * memo : 你好
         * kindMenuName : 面类
         * type : 0
         * specDetailName : 大份
         * kindMenuId : e6e8f7a0aaec485ebc728994585d7182
         * modifyTime : 1502955080
         * ratioFee : 20
         * isWait : 0
         * children : [{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"children":[{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"children":[{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}],"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}],"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}]
         * price : 20
         * customerId : e6e8f7a0aaec485ebc728994585d718e
         * menuId : e6e8f7a0aaec485ebc728994585d7181
         * id : e6e8f7a0aaec485ebc728994585d718e
         * isRatio : 0
         * kind : 1
         * optionalType : 0
         * isChangePrice : 0
         * isBuyNumberChanged : 1
         * accountUnit : 份
         * unit : 份
         * createTime : 1502955080410
         * name : 牛肉面
         * isGive : 0
         * isTwoAccount : 0
         * makeName : 红烧
         * fromCustomer : 1
         * status : 105
         * ratio : 100
         */

        private int accountNum;
        private int originalPrice;
        private int num;
        private String taste;
        private int fee;
        private String memo;
        private String kindMenuName;
        private int type;
        private String specDetailName;
        private String kindMenuId;
        private int modifyTime;
        private int ratioFee;
        private int isWait;
        private int price;
        private String customerId;
        private String menuId;
        private String id;
        private int isRatio;
        private int kind;
        private int optionalType;
        private int isChangePrice;
        private int isBuyNumberChanged;
        private String accountUnit;
        private String unit;
        private long createTime;
        private String name;
        private int isGive;
        private int isTwoAccount;
        private String makeName;
        private int fromCustomer;
        private int status;
        private int ratio;
        private List<ChildrenBeanXX> children;

        public int getAccountNum() {
            return accountNum;
        }

        public void setAccountNum(int accountNum) {
            this.accountNum = accountNum;
        }

        public int getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(int originalPrice) {
            this.originalPrice = originalPrice;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTaste() {
            return taste;
        }

        public void setTaste(String taste) {
            this.taste = taste;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getKindMenuName() {
            return kindMenuName;
        }

        public void setKindMenuName(String kindMenuName) {
            this.kindMenuName = kindMenuName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getSpecDetailName() {
            return specDetailName;
        }

        public void setSpecDetailName(String specDetailName) {
            this.specDetailName = specDetailName;
        }

        public String getKindMenuId() {
            return kindMenuId;
        }

        public void setKindMenuId(String kindMenuId) {
            this.kindMenuId = kindMenuId;
        }

        public int getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(int modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getRatioFee() {
            return ratioFee;
        }

        public void setRatioFee(int ratioFee) {
            this.ratioFee = ratioFee;
        }

        public int getIsWait() {
            return isWait;
        }

        public void setIsWait(int isWait) {
            this.isWait = isWait;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsRatio() {
            return isRatio;
        }

        public void setIsRatio(int isRatio) {
            this.isRatio = isRatio;
        }

        public int getKind() {
            return kind;
        }

        public void setKind(int kind) {
            this.kind = kind;
        }

        public int getOptionalType() {
            return optionalType;
        }

        public void setOptionalType(int optionalType) {
            this.optionalType = optionalType;
        }

        public int getIsChangePrice() {
            return isChangePrice;
        }

        public void setIsChangePrice(int isChangePrice) {
            this.isChangePrice = isChangePrice;
        }

        public int getIsBuyNumberChanged() {
            return isBuyNumberChanged;
        }

        public void setIsBuyNumberChanged(int isBuyNumberChanged) {
            this.isBuyNumberChanged = isBuyNumberChanged;
        }

        public String getAccountUnit() {
            return accountUnit;
        }

        public void setAccountUnit(String accountUnit) {
            this.accountUnit = accountUnit;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIsGive() {
            return isGive;
        }

        public void setIsGive(int isGive) {
            this.isGive = isGive;
        }

        public int getIsTwoAccount() {
            return isTwoAccount;
        }

        public void setIsTwoAccount(int isTwoAccount) {
            this.isTwoAccount = isTwoAccount;
        }

        public String getMakeName() {
            return makeName;
        }

        public void setMakeName(String makeName) {
            this.makeName = makeName;
        }

        public int getFromCustomer() {
            return fromCustomer;
        }

        public void setFromCustomer(int fromCustomer) {
            this.fromCustomer = fromCustomer;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRatio() {
            return ratio;
        }

        public void setRatio(int ratio) {
            this.ratio = ratio;
        }

        public List<ChildrenBeanXX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanXX> children) {
            this.children = children;
        }

        public static class ChildrenBeanXX {
            /**
             * accountNum : 0
             * originalPrice : 0
             * num : 0
             * fee : 0
             * type : 0
             * modifyTime : 0
             * ratioFee : 0
             * isWait : 0
             * children : [{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"children":[{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}],"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}]
             * price : 0
             * isRatio : 0
             * kind : 0
             * optionalType : 0
             * isChangePrice : 0
             * isBuyNumberChanged : 0
             * createTime : 0
             * isGive : 0
             * isTwoAccount : 0
             * fromCustomer : 0
             * status : 0
             * ratio : 0
             */

            private int accountNum;
            private int originalPrice;
            private int num;
            private int fee;
            private int type;
            private int modifyTime;
            private int ratioFee;
            private int isWait;
            private int price;
            private int isRatio;
            private int kind;
            private int optionalType;
            private int isChangePrice;
            private int isBuyNumberChanged;
            private int createTime;
            private int isGive;
            private int isTwoAccount;
            private int fromCustomer;
            private int status;
            private int ratio;
            private List<ChildrenBeanX> children;

            public int getAccountNum() {
                return accountNum;
            }

            public void setAccountNum(int accountNum) {
                this.accountNum = accountNum;
            }

            public int getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(int originalPrice) {
                this.originalPrice = originalPrice;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getFee() {
                return fee;
            }

            public void setFee(int fee) {
                this.fee = fee;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(int modifyTime) {
                this.modifyTime = modifyTime;
            }

            public int getRatioFee() {
                return ratioFee;
            }

            public void setRatioFee(int ratioFee) {
                this.ratioFee = ratioFee;
            }

            public int getIsWait() {
                return isWait;
            }

            public void setIsWait(int isWait) {
                this.isWait = isWait;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getIsRatio() {
                return isRatio;
            }

            public void setIsRatio(int isRatio) {
                this.isRatio = isRatio;
            }

            public int getKind() {
                return kind;
            }

            public void setKind(int kind) {
                this.kind = kind;
            }

            public int getOptionalType() {
                return optionalType;
            }

            public void setOptionalType(int optionalType) {
                this.optionalType = optionalType;
            }

            public int getIsChangePrice() {
                return isChangePrice;
            }

            public void setIsChangePrice(int isChangePrice) {
                this.isChangePrice = isChangePrice;
            }

            public int getIsBuyNumberChanged() {
                return isBuyNumberChanged;
            }

            public void setIsBuyNumberChanged(int isBuyNumberChanged) {
                this.isBuyNumberChanged = isBuyNumberChanged;
            }

            public int getCreateTime() {
                return createTime;
            }

            public void setCreateTime(int createTime) {
                this.createTime = createTime;
            }

            public int getIsGive() {
                return isGive;
            }

            public void setIsGive(int isGive) {
                this.isGive = isGive;
            }

            public int getIsTwoAccount() {
                return isTwoAccount;
            }

            public void setIsTwoAccount(int isTwoAccount) {
                this.isTwoAccount = isTwoAccount;
            }

            public int getFromCustomer() {
                return fromCustomer;
            }

            public void setFromCustomer(int fromCustomer) {
                this.fromCustomer = fromCustomer;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getRatio() {
                return ratio;
            }

            public void setRatio(int ratio) {
                this.ratio = ratio;
            }

            public List<ChildrenBeanX> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBeanX> children) {
                this.children = children;
            }

            public static class ChildrenBeanX {
                /**
                 * accountNum : 0
                 * originalPrice : 0
                 * num : 0
                 * fee : 0
                 * type : 0
                 * modifyTime : 0
                 * ratioFee : 0
                 * isWait : 0
                 * children : [{"accountNum":0,"originalPrice":0,"num":0,"fee":0,"type":0,"modifyTime":0,"ratioFee":0,"isWait":0,"price":0,"isRatio":0,"kind":0,"optionalType":0,"isChangePrice":0,"isBuyNumberChanged":0,"createTime":0,"isGive":0,"isTwoAccount":0,"fromCustomer":0,"status":0,"ratio":0}]
                 * price : 0
                 * isRatio : 0
                 * kind : 0
                 * optionalType : 0
                 * isChangePrice : 0
                 * isBuyNumberChanged : 0
                 * createTime : 0
                 * isGive : 0
                 * isTwoAccount : 0
                 * fromCustomer : 0
                 * status : 0
                 * ratio : 0
                 */

                private int accountNum;
                private int originalPrice;
                private int num;
                private int fee;
                private int type;
                private int modifyTime;
                private int ratioFee;
                private int isWait;
                private int price;
                private int isRatio;
                private int kind;
                private int optionalType;
                private int isChangePrice;
                private int isBuyNumberChanged;
                private int createTime;
                private int isGive;
                private int isTwoAccount;
                private int fromCustomer;
                private int status;
                private int ratio;
                private List<ChildrenBean> children;

                public int getAccountNum() {
                    return accountNum;
                }

                public void setAccountNum(int accountNum) {
                    this.accountNum = accountNum;
                }

                public int getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(int originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public int getFee() {
                    return fee;
                }

                public void setFee(int fee) {
                    this.fee = fee;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getModifyTime() {
                    return modifyTime;
                }

                public void setModifyTime(int modifyTime) {
                    this.modifyTime = modifyTime;
                }

                public int getRatioFee() {
                    return ratioFee;
                }

                public void setRatioFee(int ratioFee) {
                    this.ratioFee = ratioFee;
                }

                public int getIsWait() {
                    return isWait;
                }

                public void setIsWait(int isWait) {
                    this.isWait = isWait;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public int getIsRatio() {
                    return isRatio;
                }

                public void setIsRatio(int isRatio) {
                    this.isRatio = isRatio;
                }

                public int getKind() {
                    return kind;
                }

                public void setKind(int kind) {
                    this.kind = kind;
                }

                public int getOptionalType() {
                    return optionalType;
                }

                public void setOptionalType(int optionalType) {
                    this.optionalType = optionalType;
                }

                public int getIsChangePrice() {
                    return isChangePrice;
                }

                public void setIsChangePrice(int isChangePrice) {
                    this.isChangePrice = isChangePrice;
                }

                public int getIsBuyNumberChanged() {
                    return isBuyNumberChanged;
                }

                public void setIsBuyNumberChanged(int isBuyNumberChanged) {
                    this.isBuyNumberChanged = isBuyNumberChanged;
                }

                public int getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(int createTime) {
                    this.createTime = createTime;
                }

                public int getIsGive() {
                    return isGive;
                }

                public void setIsGive(int isGive) {
                    this.isGive = isGive;
                }

                public int getIsTwoAccount() {
                    return isTwoAccount;
                }

                public void setIsTwoAccount(int isTwoAccount) {
                    this.isTwoAccount = isTwoAccount;
                }

                public int getFromCustomer() {
                    return fromCustomer;
                }

                public void setFromCustomer(int fromCustomer) {
                    this.fromCustomer = fromCustomer;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getRatio() {
                    return ratio;
                }

                public void setRatio(int ratio) {
                    this.ratio = ratio;
                }

                public List<ChildrenBean> getChildren() {
                    return children;
                }

                public void setChildren(List<ChildrenBean> children) {
                    this.children = children;
                }

                public static class ChildrenBean {
                    /**
                     * accountNum : 0
                     * originalPrice : 0
                     * num : 0
                     * fee : 0
                     * type : 0
                     * modifyTime : 0
                     * ratioFee : 0
                     * isWait : 0
                     * price : 0
                     * isRatio : 0
                     * kind : 0
                     * optionalType : 0
                     * isChangePrice : 0
                     * isBuyNumberChanged : 0
                     * createTime : 0
                     * isGive : 0
                     * isTwoAccount : 0
                     * fromCustomer : 0
                     * status : 0
                     * ratio : 0
                     */

                    private int accountNum;
                    private int originalPrice;
                    private int num;
                    private int fee;
                    private int type;
                    private int modifyTime;
                    private int ratioFee;
                    private int isWait;
                    private int price;
                    private int isRatio;
                    private int kind;
                    private int optionalType;
                    private int isChangePrice;
                    private int isBuyNumberChanged;
                    private int createTime;
                    private int isGive;
                    private int isTwoAccount;
                    private int fromCustomer;
                    private int status;
                    private int ratio;

                    public int getAccountNum() {
                        return accountNum;
                    }

                    public void setAccountNum(int accountNum) {
                        this.accountNum = accountNum;
                    }

                    public int getOriginalPrice() {
                        return originalPrice;
                    }

                    public void setOriginalPrice(int originalPrice) {
                        this.originalPrice = originalPrice;
                    }

                    public int getNum() {
                        return num;
                    }

                    public void setNum(int num) {
                        this.num = num;
                    }

                    public int getFee() {
                        return fee;
                    }

                    public void setFee(int fee) {
                        this.fee = fee;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public int getModifyTime() {
                        return modifyTime;
                    }

                    public void setModifyTime(int modifyTime) {
                        this.modifyTime = modifyTime;
                    }

                    public int getRatioFee() {
                        return ratioFee;
                    }

                    public void setRatioFee(int ratioFee) {
                        this.ratioFee = ratioFee;
                    }

                    public int getIsWait() {
                        return isWait;
                    }

                    public void setIsWait(int isWait) {
                        this.isWait = isWait;
                    }

                    public int getPrice() {
                        return price;
                    }

                    public void setPrice(int price) {
                        this.price = price;
                    }

                    public int getIsRatio() {
                        return isRatio;
                    }

                    public void setIsRatio(int isRatio) {
                        this.isRatio = isRatio;
                    }

                    public int getKind() {
                        return kind;
                    }

                    public void setKind(int kind) {
                        this.kind = kind;
                    }

                    public int getOptionalType() {
                        return optionalType;
                    }

                    public void setOptionalType(int optionalType) {
                        this.optionalType = optionalType;
                    }

                    public int getIsChangePrice() {
                        return isChangePrice;
                    }

                    public void setIsChangePrice(int isChangePrice) {
                        this.isChangePrice = isChangePrice;
                    }

                    public int getIsBuyNumberChanged() {
                        return isBuyNumberChanged;
                    }

                    public void setIsBuyNumberChanged(int isBuyNumberChanged) {
                        this.isBuyNumberChanged = isBuyNumberChanged;
                    }

                    public int getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(int createTime) {
                        this.createTime = createTime;
                    }

                    public int getIsGive() {
                        return isGive;
                    }

                    public void setIsGive(int isGive) {
                        this.isGive = isGive;
                    }

                    public int getIsTwoAccount() {
                        return isTwoAccount;
                    }

                    public void setIsTwoAccount(int isTwoAccount) {
                        this.isTwoAccount = isTwoAccount;
                    }

                    public int getFromCustomer() {
                        return fromCustomer;
                    }

                    public void setFromCustomer(int fromCustomer) {
                        this.fromCustomer = fromCustomer;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public int getRatio() {
                        return ratio;
                    }

                    public void setRatio(int ratio) {
                        this.ratio = ratio;
                    }
                }
            }
        }
    }
}
