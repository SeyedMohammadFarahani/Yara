package com.ansarbank.room.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;

public class ExitDialog extends Dialog {
    Context mContext;
    Dialog dialog;

    public ExitDialog(Context context) {
        super(context);
        this.mContext = context;
    }

   public void execute() {
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.exit_dialog);
        final Btn cancel = dialog.findViewById(R.id.returnMain);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Const.number = 0;
            }
        });
        Btn delete = dialog.findViewById(R.id.exit);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((Activity) mContext).finish();
            }
        });
        dialog.show();
    }
}
