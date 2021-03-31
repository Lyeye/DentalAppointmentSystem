package com.lyeye.dentalappointmentsystem.util;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlertDialogUtil {
    public static SweetAlertDialog sweetAlertDialog;

    public static void show(Context context, int alertType, String title, String content, String confirmText, SweetAlertDialog.OnSweetClickListener confirmOnClick, String cancelText, SweetAlertDialog.OnSweetClickListener cancelOnClick) {
        sweetAlertDialog = new SweetAlertDialog(context, alertType);
        sweetAlertDialog
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(confirmText)
                .setConfirmClickListener(confirmOnClick)
                .setCancelText(cancelText)
                .setCancelClickListener(cancelOnClick)
                .show();
    }
}
