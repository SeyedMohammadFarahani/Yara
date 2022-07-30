package com.ansarbank.room.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.CNTextWatcher;
import com.ansarbank.room.Util.Utils;
import com.ansarbank.room.Util.ValueTextWatcher;
import com.ansarbank.room.widget.Text;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import java.util.ArrayList;
import java.util.List;

public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.RecycleViewHolder> {

    Context context;
    ArrayList<TransAction> transActions;
    String temp;

    public TransferAdapter(Context context, ArrayList<TransAction> transActions) {
        this.context = context;
        this.transActions = transActions;
    }

    @NonNull
    @Override
    public TransferAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transfer_row, viewGroup, false);
        TransferAdapter.RecycleViewHolder recycleViewHolder = new TransferAdapter.RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TransferAdapter.RecycleViewHolder recycleViewHolder, int i) {
        CNTextWatcher tv = new CNTextWatcher(recycleViewHolder.text2);
        recycleViewHolder.text2.addTextChangedListener(tv);
        //recycleViewHolder.text2.addTextChangedListener(new PatternedTextWatcher("#### **** **** ####"));
        recycleViewHolder.text2.setText(transActions.get(i).getStartNumber());

        if (transActions.get(i).getEndNumber().charAt(0) == '5' ||
                transActions.get(i).getEndNumber().charAt(0) == '6') {
            CNTextWatcher tv2 = new CNTextWatcher(recycleViewHolder.text2);
            recycleViewHolder.text7.addTextChangedListener(tv2);
            // recycleViewHolder.text7.addTextChangedListener(new PatternedTextWatcher("#### **** **** ####"));
            recycleViewHolder.text7.setText(transActions.get(i).getEndNumber());
        } else {
            recycleViewHolder.text7.setTextSize(23);
            recycleViewHolder.text7.setText(transActions.get(i).getEndNumber());
        }

        ValueTextWatcher valueTextWatcher = new ValueTextWatcher(recycleViewHolder.text3);
        recycleViewHolder.text3.addTextChangedListener(valueTextWatcher);
        recycleViewHolder.text3.setText(transActions.get(i).getTransActionValue());
        recycleViewHolder.text4.setText(transActions.get(i).getTransActionTime());
        recycleViewHolder.text5.setText(transActions.get(i).getTransActionDate());
        recycleViewHolder.text6.setText(transActions.get(i).getTransActionCode());

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
            text2 = itemView.findViewById(R.id.startTransaction);
            text3 = itemView.findViewById(R.id.valueTransaction);
            text4 = itemView.findViewById(R.id.timeTransaction);
            text5 = itemView.findViewById(R.id.DateTransaction);
            text6 = itemView.findViewById(R.id.codeTransaction);
            text7 = itemView.findViewById(R.id.endTransaction);
            delete = itemView.findViewById(R.id.deleteTransferTransAction);

        }
    }

}


