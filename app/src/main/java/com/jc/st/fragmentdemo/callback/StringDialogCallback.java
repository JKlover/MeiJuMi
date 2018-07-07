
package com.jc.st.fragmentdemo.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Window;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jc.st.fragmentdemo.R;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　       	██ ━██  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * Created by st on 2018/1/17.
 */

public abstract class StringDialogCallback extends StringCallback {

    private MaterialDialog materialDialog;
    public StringDialogCallback(Activity activity) {
        materialDialog=new MaterialDialog.Builder(activity)
                .title("稍等片刻")
                .content("正在卖力搜索中...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();


    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
//        if (dialog != null && !dialog.isShowing()) {
//            dialog.show();
//        }

    }

    @Override
    public void onFinish() {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
      materialDialog.dismiss();
    }
}
