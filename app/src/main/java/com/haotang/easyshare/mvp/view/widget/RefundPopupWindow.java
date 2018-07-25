package com.haotang.easyshare.mvp.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.haotang.easyshare.R;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 13:53
 */
public class RefundPopupWindow extends PopupWindow {

    public RefundPopupWindow(Activity context, View.OnClickListener OnClickListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.pop_mybalance, null);
        TextView tv_pop_mybalance = (TextView) inflate
                .findViewById(R.id.tv_pop_mybalance);
        // 设置按钮监听
        tv_pop_mybalance.setOnClickListener(OnClickListener);
        // 设置SelectPicPopupWindow的View
        this.setContentView(inflate);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        this.setWidth(width * 15 / 75);
        this.setHeight(width * 10 / 75);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(00000000);
        setBackgroundDrawable(dw);
    }
}