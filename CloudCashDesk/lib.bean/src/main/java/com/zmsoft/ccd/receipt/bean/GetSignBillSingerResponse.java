package com.zmsoft.ccd.receipt.bean;

import java.util.List;

/**
 * 签字员工列表
 *
 * @author DangGui
 * @create 2017/6/17.
 */

public class GetSignBillSingerResponse {
    private List<SignInfoVo> signInfoVos;
    private String signUnitKindPayDetailId;

    public List<SignInfoVo> getSignInfoVos() {
        return signInfoVos;
    }

    public void setSignInfoVos(List<SignInfoVo> signInfoVos) {
        this.signInfoVos = signInfoVos;
    }

    public String getSignUnitKindPayDetailId() {
        return signUnitKindPayDetailId;
    }

    public void setSignUnitKindPayDetailId(String signUnitKindPayDetailId) {
        this.signUnitKindPayDetailId = signUnitKindPayDetailId;
    }
}
