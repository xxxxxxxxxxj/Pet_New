package com.haotang.easyshare.mvp.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haotang.easyshare.R;

public class LoadingProgressDailog extends Dialog {

    public LoadingProgressDailog(Context context) {
        super(context);
    }

    public LoadingProgressDailog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String message;
        private boolean isShowMessage = true;
        private boolean isCancelable = false;
        private boolean isCancelOutside = false;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         *
         * @param message
         * @return
         */

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置是否显示提示信息
         *
         * @param isShowMessage
         * @return
         */
        public Builder setShowMessage(boolean isShowMessage) {
            this.isShowMessage = isShowMessage;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         *
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         *
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        public LoadingProgressDailog create() {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_loading, null);
            LoadingProgressDailog loadingProgressDailog = new LoadingProgressDailog(context, R.style.MyDialogStyle);
            TextView msgText = (TextView) view.findViewById(R.id.tipTextView);
            if (isShowMessage) {
                msgText.setText(message);
            } else {
                msgText.setVisibility(View.GONE);
            }
            loadingProgressDailog.setContentView(view);
            loadingProgressDailog.setCancelable(isCancelable);
            loadingProgressDailog.setCanceledOnTouchOutside(isCancelOutside);
            return loadingProgressDailog;
        }
    }
}
