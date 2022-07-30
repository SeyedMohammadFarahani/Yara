package com.ansarbank.room.Tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ansarbank.room.Adapter.CardAdapter;
import com.ansarbank.room.Adapter.TashilatAdapter;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.widget.Text;

import java.util.ArrayList;
import java.util.List;

public class TashilatTab extends Fragment {

    List<TransAction> allTransActions;
    ArrayList<TransAction> selectTransActions;
    RecyclerView transActionList;
    TashilatAdapter tashilatAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tashilat_tab, container, false);

        transActionList = view.findViewById(R.id.tashilatList);
        swipeRefreshLayout = view.findViewById(R.id.tashilatRefresh);
        selectTransActions = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                allTransActions = AppDatabase.getAppDatabase(getContext()).actionDao().getAllTransAction();
                for (int i = 0; i < allTransActions.size(); i++) {
                    if (allTransActions.get(i).getUserId() != Const.ID) {
                        allTransActions.remove(allTransActions.get(i));
                    }
                }
                for (TransAction t : allTransActions) {
                    if (t.getTransactionName().contains(getResources().getString(R.string.action4))) {
                        selectTransActions.add(t);
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateView();
                    }
                });
            }
        }).start();


        updateView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                selectTransActions.removeAll(selectTransActions);
                allTransActions = AppDatabase.getAppDatabase(getContext()).actionDao().getAllTransAction();
                for (int i = 0; i < allTransActions.size(); i++) {
                    if (allTransActions.get(i).getUserId() != Const.ID) {
                        allTransActions.remove(allTransActions.get(i));
                    }
                }
                for (TransAction t : allTransActions) {
                    if (t.getTransactionName().contains(getResources().getString(R.string.action4))) {
                        selectTransActions.add(t);
                    }
                }
                updateView();
            }
        });

        return view;
    }

    public void updateView() {
        tashilatAdapter = new TashilatAdapter(getContext(), (ArrayList<TransAction>) selectTransActions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        transActionList.setLayoutManager(layoutManager);
        transActionList.setAdapter(tashilatAdapter);
    }
}
