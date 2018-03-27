package com.zmsoft.ccd.module.menu.menu.ui;

import android.widget.EditText;

import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuGroupVO;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/10/31.
 */

public interface IMenuListContract {

    void showContentByLetter(String letter);

    void showContentByCategory(MenuGroupVO groupVO);

    boolean isShowImage();

    boolean isShowCategory();

    void setFilterData(List<MenuGroupVO> categoryList, List<String> letterList);

    EditText getSearchEditText();

}
