package com.rqpw.weather.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import com.rqpw.weather.R;

/**
 * Created by Pan Jiafang on 2014/8/18.
 */
public class CustomerizeDialog extends Dialog {

    public CustomerizeDialog(Context context) {
        super(context, R.style.dialog);
        init();
    }

    private void init() {

        setContentView(R.layout.dialog_custom);

    }


}
