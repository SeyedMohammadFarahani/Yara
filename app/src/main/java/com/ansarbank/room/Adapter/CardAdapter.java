package com.ansarbank.room.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.CNTextWatcher;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.widget.Btn;
import com.ansarbank.room.widget.Credit;
import com.ansarbank.room.widget.Text;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.RecycleViewHolder> {
    Context context;
    ArrayList<Card> cards;
    String bankType;
    String temp;

    public CardAdapter(Context context, ArrayList<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row2, viewGroup, false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view1);
        return recycleViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder recycleViewHolder, int i) {

        recycleViewHolder.text.setText(cards.get(i).getCardName());

        CNTextWatcher tv = new CNTextWatcher(recycleViewHolder.text2);
        recycleViewHolder.text2.addTextChangedListener(tv);
        recycleViewHolder.text2.setText(cards.get(i).getCardNumber());
        recycleViewHolder.text3.setText(cards.get(i).getCcv2());
        recycleViewHolder.text4.setText(cards.get(i).getDate());

        recycleViewHolder.myView.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onLongClick(View v) {
                recycleViewHolder.cancel = v.findViewById(R.id.cancle);
                temp = recycleViewHolder.text2.getText().toString().trim();
                recycleViewHolder.select.setBackgroundColor(R.color.blue);
                if (Const.selectCards.contains(temp)) {
                } else {
                    Const.selectCards.add(temp);
                }
                Const.selectView.add(recycleViewHolder.select);
                Const.cancels.add(recycleViewHolder.cancel);
                recycleViewHolder.cancel.setVisibility(View.VISIBLE);
                return false;
            }
        });

        recycleViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                temp = recycleViewHolder.text2.getText().toString().trim();
                View childView = (View) v1.getParent();
                View mainView = (View) childView.getParent();
                recycleViewHolder.select.setBackground(mainView.getBackground());
                recycleViewHolder.cancel.setVisibility(View.GONE);
                Const.cancels.remove(recycleViewHolder.cancel);
                Const.selectCards.remove(temp);
            }
        });

        bankType = recycleViewHolder.text2.getText().toString().trim().substring(0, 6);
        switch (bankType) {
            case "502229":
                recycleViewHolder.card.setBackgroundResource(R.drawable.pasargad);
                recycleViewHolder.text.setTextColor(context.getResources().getColor(R.color.white));
                recycleViewHolder.text2.setTextColor(context.getResources().getColor(R.color.white));
                recycleViewHolder.text3.setTextColor(context.getResources().getColor(R.color.white));
                recycleViewHolder.text4.setTextColor(context.getResources().getColor(R.color.white));
                recycleViewHolder.text5.setTextColor(context.getResources().getColor(R.color.white));
                recycleViewHolder.text6.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "639347":
                recycleViewHolder.card.setBackgroundResource(R.drawable.pasargad);
                break;
            case "502806":
                recycleViewHolder.card.setBackgroundResource(R.drawable.shahr);
                break;
            case "504706":
                recycleViewHolder.card.setBackgroundResource(R.drawable.shahr);
                break;
            case "502908":
                recycleViewHolder.card.setBackgroundResource(R.drawable.toseetaavon);
                break;
            case "502938":
                recycleViewHolder.card.setBackgroundResource(R.drawable.deybank);
                break;
            case "505416":
                recycleViewHolder.card.setBackgroundResource(R.drawable.gardeshgari);
                break;
            case "505785":
                recycleViewHolder.card.setBackgroundResource(R.drawable.iranzamin);
                break;
            case "589210":
                recycleViewHolder.card.setBackgroundResource(R.drawable.sepah);
                break;
            case "589463":
                recycleViewHolder.card.setBackgroundResource(R.drawable.refah);
                break;
            case "599999":
                recycleViewHolder.card.setBackgroundResource(R.drawable.markazi);
                break;
            case "636795":
                recycleViewHolder.card.setBackgroundResource(R.drawable.markazi);
                break;
            case "603769":
                recycleViewHolder.card.setBackgroundResource(R.drawable.saderat);
                break;
            case "603770":
                recycleViewHolder.card.setBackgroundResource(R.drawable.keshavarzi);
                break;
            case "639217":
                recycleViewHolder.card.setBackgroundResource(R.drawable.keshavarzi);
                break;
            case "603799":
                recycleViewHolder.card.setBackgroundResource(R.drawable.melli);
                break;
            case "606373":
                recycleViewHolder.card.setBackgroundResource(R.drawable.mehriran);
                break;
            case "610433":
                recycleViewHolder.card.setBackgroundResource(R.drawable.mellat);
                break;
            case "621986":
                recycleViewHolder.card.setBackgroundResource(R.drawable.saman);
                break;
            case "622106":
                recycleViewHolder.card.setBackgroundResource(R.drawable.parsian);
                break;
            case "627353":
                recycleViewHolder.card.setBackgroundResource(R.drawable.tejarat);
                break;
            case "627381":
                recycleViewHolder.card.setBackgroundResource(R.drawable.bankk);
                break;
            case "627412":
                recycleViewHolder.card.setBackgroundResource(R.drawable.egtesadnovin);
                break;
            case "627488":
                recycleViewHolder.card.setBackgroundResource(R.drawable.karafarin);
                break;
            case "627648":
                recycleViewHolder.card.setBackgroundResource(R.drawable.toseesaderat);
                break;
            case "627760":
                recycleViewHolder.card.setBackgroundResource(R.drawable.postbank);
                break;
            case "627961":
                recycleViewHolder.card.setBackgroundResource(R.drawable.sanatomadan);
                break;
            case "628023":
                recycleViewHolder.card.setBackgroundResource(R.drawable.maskan);
                break;
            case "628157":
                recycleViewHolder.card.setBackgroundResource(R.drawable.tosee);
                break;
            case "636214":
                recycleViewHolder.card.setBackgroundResource(R.drawable.ayande);
                break;
            case "636949":
                recycleViewHolder.card.setBackgroundResource(R.drawable.hekmat);
                break;
            case "639346":
                recycleViewHolder.card.setBackgroundResource(R.drawable.sina);
                break;
            case "639607":
                recycleViewHolder.card.setBackgroundResource(R.drawable.sarmaye);
                break;
            case "936450":
                recycleViewHolder.card.setBackgroundResource(R.drawable.bankk);
                break;
            case "639370":
                recycleViewHolder.card.setBackgroundResource(R.drawable.mehregtesad);
                break;
            case "639599":
                recycleViewHolder.card.setBackgroundResource(R.drawable.gavamin);
                break;
            case "505801":
                recycleViewHolder.card.setBackgroundResource(R.drawable.kosar);
                break;
            default:
                recycleViewHolder.card.setBackgroundResource(R.drawable.card_back);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        ImageButton cancel;
        CardView card;
        Text text;
        TextView text5;
        Text text6;
        Credit text2;
        TextView text3;
        TextView text4;
        View myView;
        ConstraintLayout select;

        public RecycleViewHolder(@NonNull final View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.MyCard);
            card = itemView.findViewById(R.id.MyCard);
            text = itemView.findViewById(R.id.cardOwner);
            text2 = itemView.findViewById(R.id.numberOfCard);
            text3 = itemView.findViewById(R.id.ccv2Ofcard);
            text4 = itemView.findViewById(R.id.dateOfCard);
            text5 = itemView.findViewById(R.id.ccv2ID);
            text6 = itemView.findViewById(R.id.calenderID);
            cancel = itemView.findViewById(R.id.cancle);
            select = itemView.findViewById(R.id.bank);

        }
    }
}




