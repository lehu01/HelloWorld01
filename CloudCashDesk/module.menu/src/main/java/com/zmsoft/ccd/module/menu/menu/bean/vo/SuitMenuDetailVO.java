package com.zmsoft.ccd.module.menu.menu.bean.vo;

import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenu;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/6/19.
 */

public class SuitMenuDetailVO {

    private BaseMenuVo baseMenuVo;

    private SuitMenu suitMenu;

    private List<SuitMenuHitRule> suitMenuHitRules;

    private boolean isSuitMenuSellOut;

    public SuitMenuDetailVO() {
    }

    public SuitMenuDetailVO(BaseMenuVo baseMenuVo, List<SuitMenuHitRule> suitMenuHitRules) {
        this.baseMenuVo = baseMenuVo;
        this.suitMenuHitRules = suitMenuHitRules;
    }

    public boolean isSuitMenuSellOut() {
        return isSuitMenuSellOut;
    }

    public void setSuitMenuSellOut(boolean suitMenuSellOut) {
        isSuitMenuSellOut = suitMenuSellOut;
    }

    public BaseMenuVo getBaseMenuVo() {
        return baseMenuVo;
    }

    public void setBaseMenuVo(BaseMenuVo baseMenuVo) {
        this.baseMenuVo = baseMenuVo;
    }

    public List<SuitMenuHitRule> getSuitMenuHitRules() {
        return suitMenuHitRules;
    }

    public void setSuitMenuHitRules(List<SuitMenuHitRule> suitMenuHitRules) {
        this.suitMenuHitRules = suitMenuHitRules;
    }

    public SuitMenu getSuitMenu() {
        return suitMenu;
    }

    public void setSuitMenu(SuitMenu suitMenu) {
        this.suitMenu = suitMenu;
    }
}
