package com.zmsoft.ccd.module.mistake;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.spdata.MistakeDataManager;
import com.zmsoft.ccd.lib.bean.mistakes.Mistake;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;

/**
 * Created by jihuo on 2017/2/17.
 */
public class MistakeToast {

    private Toast toast;
    private TextView textData;
    private TextView textId;
    private TextView textSeatCode;
    private TextView textMistakeContent;

    public void show(Context context, Mistake mistake) {
        if (context == null) {
            return;
        }
        MistakeDataManager.saveMistakeData(context, mistake);

        // 控件
        View view = LayoutInflater.from(context).inflate(R.layout.mistake_toast, null);
        textData = (TextView) view.findViewById(R.id.text_time);
        textId = (TextView) view.findViewById(R.id.text_id);
        textSeatCode = (TextView) view.findViewById(R.id.text_seat_code);
        textMistakeContent = (TextView) view.findViewById(R.id.text_mistake_content);

        // 赋值
        if (!StringUtils.isEmpty(StringUtils.appendStr(mistake.getOrderNO()))) {
            textId.setText(StringUtils.appendStr("NO.", mistake.getOrderNO()));
        }
        if (!StringUtils.isEmpty(mistake.getSeatName())) {
            textSeatCode.setText(context.getString(R.string.desk_msg_deskid, mistake.getSeatName()));
        }
        textData.setText(TimeUtils.getTimeStr(mistake.getMistakeTime(), TimeUtils.FORMAT_TIME_SECONDS));
        textMistakeContent.setText(mistake.getMistakeContent());

        // 显示
        toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
