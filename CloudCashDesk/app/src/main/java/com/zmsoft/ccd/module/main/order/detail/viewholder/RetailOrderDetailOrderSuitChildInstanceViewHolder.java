package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.helper.InstanceHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：套菜子菜
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/22 09:55
 */
public class RetailOrderDetailOrderSuitChildInstanceViewHolder extends RetailOrderDetailBaseViewHolder {

    @BindView(R.id.image_instance_state)
    ImageView mImageInstanceState;
    @BindView(R.id.text_instance_name)
    TextView mTextInstanceName;
    @BindView(R.id.text_instance_specification)
    TextView mTextInstanceSpecification;
    @BindView(R.id.linear_instance)
    LinearLayout mLinearInstance;
    @BindView(R.id.text_instance_count)
    TextView mTextInstanceCount;

    public RetailOrderDetailOrderSuitChildInstanceViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (bean != null && bean instanceof OrderDetailItem) {
            OrderDetailItem item = (OrderDetailItem) bean;
            fillView(item.getInstance());
        }
    }

    private void fillView(Instance instance) {
        if (instance != null) {
            /**
             * 优先处理
             * 1.菜肴是否不能操作
             */
            boolean isReject = InstanceHelper.isRejectInstance(instance.getStatus());

            // 双单位菜肴
            if (Base.INT_FALSE == instance.getIsTwoAccount()) { // 非双单位
                itemView.setBackgroundColor(GlobalVars.context.getResources().getColor(R.color.white));
                if (isReject) {
                    mTextInstanceCount.setText(SpannableStringUtils.getBuilder(getContext()
                            , StringUtils.appendStr(NumberUtils.doubleToStr(instance.getAccountNum())
                                    , instance.getAccountUnit()))
                            .setStrikethrough()
                            .create());
                } else {
                    mTextInstanceCount.setText(StringUtils.appendStr(NumberUtils.doubleToStr(instance.getAccountNum()), instance.getAccountUnit()));
                }
            } else {  // 双单位菜肴
                if (instance.isDoubleSwitch()) { // 掌柜上设置了
                    if (Base.SHORT_TRUE == instance.getIsBuyNumberChanged()) {
                        itemView.setBackgroundColor(GlobalVars.context.getResources().getColor(R.color.white));
                    } else {
                        if(InstanceHelper.isRejectInstance(instance.getStatus())){
                            itemView.setBackgroundColor(GlobalVars.context.getResources().getColor(R.color.white));
                        } else {
                            itemView.setBackgroundColor(GlobalVars.context.getResources().getColor(R.color.filter_text_bg));
                        }
                    }
                } else {
                    itemView.setBackgroundColor(GlobalVars.context.getResources().getColor(R.color.white));
                }

                // 设置中划线
                if (isReject) {
                    mTextInstanceCount.setText(SpannableStringUtils.getBuilder(getContext()
                            , StringUtils.appendStr(NumberUtils.doubleToStr(instance.getNum())
                                    , instance.getUnit()
                                    , getContext().getString(R.string.slash)
                                    , NumberUtils.doubleToStr(instance.getAccountNum())
                                    , instance.getAccountUnit()))
                            .setStrikethrough()
                            .create());
                } else {
                    mTextInstanceCount.setText(StringUtils.appendStr(NumberUtils.doubleToStr(instance.getNum())
                            , instance.getUnit()
                            , getContext().getString(R.string.slash)
                            , NumberUtils.doubleToStr(instance.getAccountNum())
                            , instance.getAccountUnit()));
                }
            }

            // 待菜
            if (Base.SHORT_TRUE == instance.getIsWait()) {
                // 设置中划线
                if (isReject) {
                    mTextInstanceName.setText(SpannableStringUtils.getBuilder(getContext()
                            , StringUtils.appendStr(getContext().getString(R.string.wait)
                                    , instance.getName()))
                            .setStrikethrough()
                            .create());
                } else {
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder builder = new SpannableStringBuilder(StringUtils.appendStr(getContext().getString(R.string.wait), instance.getName()));
                    builder.setSpan(redSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTextInstanceName.setText(builder);
                }
            } else {
                // 设置中划线
                if (isReject) {
                    mTextInstanceName.setText(SpannableStringUtils.getBuilder(getContext(), instance.getName())
                            .setStrikethrough()
                            .create());
                } else {
                    mTextInstanceName.setText(instance.getName());
                }
            }

            mImageInstanceState.setVisibility(View.VISIBLE);
            InstanceHelper.setInstanceStateImage(instance.getStatus(), mImageInstanceState);


            /**显示菜肴规格，做法，加料，备注等信息**/
            List<Instance> instanceList = instance.getChildInstanceList();
            StringBuilder sb = new StringBuilder();
            if (!StringUtils.isEmpty(instance.getSpecDetailName())) {
                sb.append(instance.getSpecDetailName());
                sb.append(getContext().getString(R.string.comma));
            }
            if (!StringUtils.isEmpty(instance.getMakeName())) {
                sb.append(instance.getMakeName());
                sb.append(getContext().getString(R.string.comma));
            }
            if (instanceList != null) {
                for (Instance instance1 : instanceList) {
                    sb.append(instance1.getName());
                    sb.append(NumberUtils.doubleToStr(instance1.getAccountNum()));
                    sb.append(instance1.getUnit());
                    sb.append(getContext().getString(R.string.comma));
                }
            }
            if (!StringUtils.isEmpty(instance.getTaste())) {
                sb.append(instance.getTaste());
                sb.append(getContext().getString(R.string.comma));
            }

            String specDetailStr = sb.toString().length() > 0 ? sb.toString().substring(0, sb.length() - 1) : sb.toString();
            if (StringUtils.isEmpty(specDetailStr)) {
                mTextInstanceSpecification.setVisibility(View.GONE);
            } else {
                mTextInstanceSpecification.setVisibility(View.VISIBLE);
                // 设置中划线
                if (isReject) {
                    mTextInstanceSpecification.setText(SpannableStringUtils.getBuilder(getContext(), specDetailStr)
                            .setStrikethrough()
                            .create());
                } else {
                    mTextInstanceSpecification.setText(specDetailStr);
                }
            }
        }
    }
}
