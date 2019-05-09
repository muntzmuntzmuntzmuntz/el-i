package com.mememe.processors;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.mememe.R;
import com.mememe.models.User;

public class Profile {
    User user;
    Dialog dialog;

    public void showPopup(){
        dialog.setContentView(R.layout.login_popup_window);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
