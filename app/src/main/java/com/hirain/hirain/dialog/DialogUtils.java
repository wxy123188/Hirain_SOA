package com.hirain.hirain.dialog;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;

import com.hirain.hirain.R;
import com.hirain.hirain.myview.Dialog;

import java.lang.ref.WeakReference;

public class DialogUtils {
    //使用弱引用防止内存泄漏


    public static void alertDialog(Context context, String positiveText,String negativeText,String title, String message,boolean isCancel, final Runnable taskPositive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        if (!TextUtils.isEmpty(title))
            builder.setMessage(message);
        builder.setCancelable(isCancel);
        if (!TextUtils.isEmpty(positiveText)) {
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (taskPositive != null) {
                        taskPositive.run();
                    }
                    dialog.dismiss();
                }
            });
        }
        if (!TextUtils.isEmpty(negativeText)) {
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
        }
        AlertDialog  alertDialog = builder.create();

        alertDialog.show();
    }


    public static void customView(Context context, View view, Runnable runTask) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        if (runTask!=null){
            runTask.run();
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public interface onClickListener{
        void leftClickListener();
        void rightClickListener(String text);
    }

    public static Dialog modeTipView(Context context, onClickListener clickListener) {
        Dialog dialog = new Dialog(context,R.style.Dialog);



        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_style, null, false);
        dialog.setContentView(inflate);
        TextView title = inflate.findViewById(R.id.dialog_title);
        TextView cancle = inflate.findViewById(R.id.dialog_cancle);
        TextView start = inflate.findViewById(R.id.dialog_start);
        //给标题和两个按钮赋值
        title.setText(R.string.start_model);
        cancle.setText(R.string.dialog_cancle);
        start.setText(R.string.dialg_confirm);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null){
                    clickListener.leftClickListener();

                }
                dialog.dismiss();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null){
                    clickListener.rightClickListener("");
                }
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }


    public static Dialog customView(Context context,int titles,int leftText,int rightText,onClickListener clickListener) {
        Dialog dialog = new Dialog(context,R.style.Dialog);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_style, null, false);
        dialog.setContentView(inflate);
        TextView title = inflate.findViewById(R.id.dialog_title);
        TextView cancle = inflate.findViewById(R.id.dialog_cancle);
        TextView start = inflate.findViewById(R.id.dialog_start);
        //给标题和两个按钮赋值
        title.setText(context.getResources().getString(titles));
        cancle.setText(context.getResources().getString(leftText));
        start.setText(context.getResources().getString(rightText));

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null){
                    clickListener.leftClickListener();

                }
                dialog.dismiss();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null){
                    clickListener.rightClickListener("");
                }
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }

    //带有输入框的弹窗
    public static Dialog customEditView(Context context,int titles,int leftText,int rightText,onClickListener clickListener) {
        Dialog dialog = new Dialog(context,R.style.Dialog);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_edit_style, null, false);
        dialog.setContentView(inflate);
        TextView title = inflate.findViewById(R.id.dialog_title);
        TextView cancle = inflate.findViewById(R.id.dialog_cancle);
        TextView start = inflate.findViewById(R.id.dialog_start);
        EditText et = inflate.findViewById(R.id.dialog_et);
        ImageView del = inflate.findViewById(R.id.dialog_et_del);
        //给标题和两个按钮赋值
        title.setText(context.getResources().getString(titles));
        cancle.setText(context.getResources().getString(leftText));
        start.setText(context.getResources().getString(rightText));


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null){
                    clickListener.leftClickListener();

                }
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et.setText("");
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null){
                    clickListener.rightClickListener(et.getText().toString());
                }
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }

}


