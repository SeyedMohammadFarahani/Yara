package com.ansarbank.room.Tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ansarbank.room.Adapter.TransferAdapter;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;

import java.util.ArrayList;
import java.util.List;

public class TransferTab extends Fragment {

    List<TransAction> allTransActions;
    ArrayList<TransAction> selectTransActions;
    RecyclerView transActionList;
    TransferAdapter transferAdapter;
    SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.transfer_tab, container, false);

        transActionList = view.findViewById(R.id.transferList);
        swipeRefreshLayout = view.findViewById(R.id.transferRefresh);
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
                    if (t.getTransactionName().contains(getResources().getString(R.string.action5))) {
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
                    if (t.getTransactionName().contains(getResources().getString(R.string.action5))) {
                        selectTransActions.add(t);
                    }
                }
                updateView();
            }
        });

        return view;
    }

    public void updateView() {
        transferAdapter = new TransferAdapter(getContext(), (ArrayList<TransAction>) selectTransActions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        transActionList.setLayoutManager(layoutManager);
        transActionList.setAdapter(transferAdapter);
    }
}
