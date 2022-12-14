package com.ansarbank.room.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Utils;
import com.ansarbank.room.Util.ValueTextWatcher;
import com.ansarbank.room.widget.Text;

import java.util.ArrayList;

public class GhabzAdapter extends RecyclerView.Adapter<GhabzAdapter.RecycleViewHolder> {

    Context context;
    ArrayList<TransAction> transActions;
    String temp;


    public GhabzAdapter(Context context, ArrayList<TransAction> transActions) {
        this.context = context;
        this.transActions = transActions;
    }

    @NonNull
    @Override
    public GhabzAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        temp = "";
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ghabz_row, viewGroup, false);
        GhabzAdapter.RecycleViewHolder recycleViewHolder = new GhabzAdapter.RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GhabzAdapter.RecycleViewHolder recycleViewHolder, final int i) {

        recycleViewHolder.text2.setText(transActions.get(i).getTransactionName());

        ValueTextWatcher valueTextWatcher = new ValueTextWatcher(recycleViewHolder.text3);
        recycleViewHolder.text3.addTextChangedListener(valueTextWatcher);

        recycleViewHolder.text3.setText(transActions.get(i).getTransActionValue());
        recycleViewHolder.text4.setText(transActions.get(i).getTransActionTime());
        recycleViewHolder.text5.setText(transActions.get(i).getTransActionDate());
        recycleViewHolder.text6.setText(transActions.get(i).getTransActionCode());
        recycleViewHolder.text7.setText(transActions.get(i).getGhabzSerialTransAction());
        recycleViewHolder.text8.setText(transActions.get(i).getPaySerialTransAction());

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
        Text text8;
        ImageButton delete;

        public RecycleViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.findViewById(R.id.chargeRow);
            text2 = itemView.findViewById(R.id.ghabzName);
            text3 = itemView.findViewById(R.id.valueGhabz);
            text4 = itemView.findViewById(R.id.timeGhabz);
            text5 = itemView.findViewById(R.id.DateGhabz);
            text6 = itemView.findViewById(R.id.codeGhabz);
            text7 = itemView.findViewById(R.id.serialTransaction1);
            text8 = itemView.findViewById(R.id.serialTransaction2);
            delete = itemView.findViewById(R.id.deleteGhabzTransAction);

        }
    }
}


