package com.ansarbank.room.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.CNTextWatcher;
import com.ansarbank.room.Util.Utils;
import com.ansarbank.room.widget.Text;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import java.util.ArrayList;
import java.util.List;

public class TashilatAdapter extends RecyclerView.Adapter<TashilatAdapter.RecycleViewHolder> {

    Context context;
    ArrayList<TransAction> transActions;
    String temp;

    public TashilatAdapter(Context context, ArrayList<TransAction> transActions) {
        this.context = context;
        this.transActions = transActions;
    }

    @NonNull
    @Override
    public TashilatAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tashilat_row, viewGroup, false);
        TashilatAdapter.RecycleViewHolder recycleViewHolder = new TashilatAdapter.RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TashilatAdapter.RecycleViewHolder recycleViewHolder, int i) {

        CNTextWatcher tv = new CNTextWatcher(recycleViewHolder.text2);
        recycleViewHolder.text2.addTextChangedListener(tv);
        // recycleViewHolder.text2.addTextChangedListener(new PatternedTextWatcher("#### **** **** ####"));
        recycleViewHolder.text2.setText(transActions.get(i).getEndNumber());

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
        ImageButton delete;

        public RecycleViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.findViewById(R.id.chargeRow);
            text2 = itemView.findViewById(R.id.tashilatTransaction);
            text3 = itemView.findViewById(R.id.valueTashilat);
            text4 = itemView.findViewById(R.id.timeTashilat);
            text5 = itemView.findViewById(R.id.DateTashilat);
            text6 = itemView.findViewById(R.id.codeTashilat);
            delete = itemView.findViewById(R.id.deleteTashilatTransAction);

        }
    }

}


