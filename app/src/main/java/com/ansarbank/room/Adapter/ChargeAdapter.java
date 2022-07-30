package com.ansarbank.room.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Utils;
import com.ansarbank.room.Util.ValueTextWatcher;
import com.ansarbank.room.widget.Text;

import java.util.ArrayList;

public class ChargeAdapter extends RecyclerView.Adapter<ChargeAdapter.RecycleViewHolder> {

    Context context;
    ArrayList<TransAction> transActions;
    String temp;


    public ChargeAdapter(Context context, ArrayList<TransAction> transActions) {
        this.context = context;
        this.transActions = transActions;
    }

    @NonNull
    @Override
    public ChargeAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.charge_row, viewGroup, false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChargeAdapter.RecycleViewHolder recycleViewHolder, int i) {
        recycleViewHolder.text2.setText(transActions.get(i).getTransactionName());
        if (transActions.get(i).getTransActionValue().contains("تومان")) {
            recycleViewHolder.text3.setWidth(1000);
            if (transActions.get(i).getTransActionValue().contains("3 گیگابایت 3ساعته(8 صبح تا 2 صبح)4000 تومان")
                    || transActions.get(i).getTransActionValue().contains("2 گیگابایت 2ساعته(8 صبح تا 2 صبح)3000 تومان")
                    || transActions.get(i).getTransActionValue().contains("1 گیگابایت 1 ساعته(8صبح تا2صبح)2000 تومان")
            )
                recycleViewHolder.text3.setTextSize(16);
            else
                recycleViewHolder.text3.setTextSize(18);
        } else {
            recycleViewHolder.text3.setWidth(170);
            ValueTextWatcher valueTextWatcher = new ValueTextWatcher(recycleViewHolder.text3);
            recycleViewHolder.text3.addTextChangedListener(valueTextWatcher);
        }
        recycleViewHolder.text3.setText(transActions.get(i).getTransActionValue());
        recycleViewHolder.text4.setText(transActions.get(i).getTransActionTime());
        recycleViewHolder.text5.setText(transActions.get(i).getTransActionDate());
        recycleViewHolder.text6.setText(transActions.get(i).getTransActionCode());
        recycleViewHolder.text7.setText(transActions.get(i).getTransActionPhone());
        recycleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = recycleViewHolder.text6.getText().toString();
                View childView = (View) v.getParent();
                View mainView = (View) childView.getParent();
                TransAction transAction = AppDatabase.getAppDatabase(context).actionDao().findTransActionByCode(temp);
                if (transAction != null) {
                    Utils.deleteActionDialog(mainView, temp, context);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return transActions.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        Text text2;
        Text text3;
        Text text4;
        Text text5;
        Text text6;
        Text text7;
        ImageButton delete;

        public RecycleViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.findViewById(R.id.chargeRow);
            text2 = itemView.findViewById(R.id.chargeName);
            text3 = itemView.findViewById(R.id.valueCharge);
            text4 = itemView.findViewById(R.id.timeCharge);
            text5 = itemView.findViewById(R.id.DateCharge);
            text6 = itemView.findViewById(R.id.codeCharge);
            text7 = itemView.findViewById(R.id.phoneCharge);
            delete = itemView.findViewById(R.id.deleteChargeTransAction);

        }
    }

}






