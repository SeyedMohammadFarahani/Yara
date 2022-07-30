package com.ansarbank.room.Fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.Adapter.CardAdapter;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.Database.User.User;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.callback.ViewUpdate;
import com.ansarbank.room.widget.Btn;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

public class CardManagerFragment extends BaseFragment implements ViewUpdate {
    int id = Const.ID;
    ConstraintLayout placeholder;
    List<Card> cards;
    List<Card> temp;
    ImageButton plus, plus1, deleteCards;
    RecyclerView cardList;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.cards_fragment, container, false);

        Const.number = 0;

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title1));

        Const.selectCards.removeAll(Const.selectCards);

        final Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "isans.ttf");

        cards = new ArrayList<>();

        placeholder = view.findViewById(R.id.placeholder);
        plus = view.findViewById(R.id.plus);
        plus1 = view.findViewById(R.id.plus2);
        deleteCards = view.findViewById(R.id.deleteCard);
        cardList = view.findViewById(R.id.cardList);

        updateView();

        /*if (AppDatabase.getAppDatabase(getContext()).userDao().findUserById(id).getUserCards().size() == 0) {
            placeholder.setVisibility(View.VISIBLE);
        }*/

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCardDialog(view);
            }
        });

        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.selectCards.removeAll(Const.selectCards);
                createCardDialog(view);
            }
        });

        deleteCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Const.selectCards.size() != 0)
                    deleteCardDialog();
                else {
                    Alerter.create(getActivity())
                            .setText(getResources().getString(R.string.delete_error))
                            .setTextTypeface(typeface)
                            .setTextAppearance(20)
                            .setBackgroundColorRes(R.color.pink)
                            .setIcon(R.drawable.error)
                            .show();
                }
            }
        });

        return view;
    }

    private void deleteCardDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.delete_card_dialog);
        //TODO
        final Btn cancel = dialog.findViewById(R.id.cancleCardDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ConstraintLayout c : Const.selectView) {
                    View view = (View) c.getParent();
                    c.setBackground(view.getBackground());
                }
                for (ImageButton c : Const.cancels) {
                    c.setVisibility(View.GONE);
                }
                Const.selectCards.removeAll(Const.selectCards);
                Const.selectView.removeAll(Const.selectView);
                Const.cancels.removeAll(Const.cancels);
                dialog.dismiss();
            }
        });
        Btn delete = dialog.findViewById(R.id.deleteCardDialog);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < Const.selectCards.size(); i++) {
                    if (AppDatabase.getAppDatabase(getContext()).cardDao().findByCardNumber(Const.selectCards.get(i)) != null) {
                        AppDatabase.getAppDatabase(getContext()).cardDao().deleteCard(AppDatabase.getAppDatabase(getActivity()).cardDao().findByCardNumber(Const.selectCards.get(i)));
                    }
                }
                Const.selectCards.removeAll(Const.selectCards);
                Const.selectView.removeAll(Const.selectView);
                updateView();

               /* if (AppDatabase.getAppDatabase(getContext()).userDao().findUserById(id).getUserCards().size() == 0) {
                    placeholder.setVisibility(View.VISIBLE);
                }*/
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void update(View view) {
        placeholder = view.findViewById(R.id.placeholder);
        cardList = view.findViewById(R.id.cardList);
        placeholder.setVisibility(View.GONE);
        updateView();
    }

    public void updateView() {

        cards = AppDatabase.getAppDatabase(getContext()).cardDao().getAllCards();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getUser() != Const.ID) {
                cards.remove(cards.get(i));
            }
        }
        if (/*AppDatabase.getAppDatabase(getContext()).cardDao().getAllCards().size()*/ cards.size() == 0) {
            placeholder.setVisibility(View.VISIBLE);
        }
        CardAdapter cardAdapter = new CardAdapter(getContext(), (ArrayList<Card>) cards);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        cardList.setLayoutManager(layoutManager);
        cardList.setAdapter(cardAdapter);

    }

}
