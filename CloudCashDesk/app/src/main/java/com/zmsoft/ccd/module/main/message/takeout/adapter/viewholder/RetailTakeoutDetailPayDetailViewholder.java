package com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.message.TakeoutPayVo;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailPayDetailRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class RetailTakeoutDetailPayDetailViewholder extends RetailTakeoutDetailBaseViewholder {
    @BindView(R.id.text_takeout_pay_detail)
    TextView mTextTakeoutPayDetail;
    @BindView(R.id.text_module_takeout_pay_extra)
    TextView mTextModuleTakeoutPayExtra;
    private TakeoutDetailPayDetailRecyclerItem mPayDetailRecyclerItem;

    public RetailTakeoutDetailPayDetailViewholder(Context context, View itemView, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, TakeoutDetailRecyclerItem msgDetailRecyclerItemObj, int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        mPayDetailRecyclerItem = msgDetailRecyclerItemObj.getTakeoutDetailPayDetailRecyclerItem();
        if (null == mPayDetailRecyclerItem)
            return;
        initInfoView();
        initListener(position);
    }

    protected void initInfoView() {
        List<TakeoutPayVo> takeoutPayVoList = mPayDetailRecyclerItem.getPayVoList();
        if (null != takeoutPayVoList && !takeoutPayVoList.isEmpty()) {
            mTextTakeoutPayDetail.setVisibility(View.VISIBLE);
            SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder(context, "");
            for (int i = 0; i < takeoutPayVoList.size(); i++) {
                TakeoutPayVo takeoutPayVo = takeoutPayVoList.get(i);
                if (null != takeoutPayVo) {
                    builder.append(takeoutPayVo.getName())
                            .append(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                    , FeeHelper.getDecimalFee(takeoutPayVo.getFee())))
                            .setForegroundColor(ContextCompat.getColor(mContext, R.color.accentColor));
                }
                if (i != takeoutPayVoList.size() - 1) {
                    builder.append(context.getString(R.string.comma_separator));
                    builder.append(" ");
                }
            }
            mTextTakeoutPayDetail.setText(builder.create());
        } else {
            mTextTakeoutPayDetail.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(mPayDetailRecyclerItem.getDeliveryFee())) {
            mTextModuleTakeoutPayExtra.setVisibility(View.VISIBLE);
            mTextModuleTakeoutPayExtra.setText(mPayDetailRecyclerItem.getDeliveryFee());
        } else {
            mTextModuleTakeoutPayExtra.setVisibility(View.GONE);
        }
    }

    protected void initListener(final int position) {
    }
}
