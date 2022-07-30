package com.ansarbank.room.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.Adapter.ChargeAdapter;
import com.ansarbank.room.Adapter.Pager;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Tab.ChargeTab;
import com.ansarbank.room.Tab.GhabzTab;
import com.ansarbank.room.Tab.TashilatTab;
import com.ansarbank.room.Tab.TransferTab;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.Util.MyTab;

import java.util.ArrayList;
import java.util.List;

public class DocumentFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    MyTab Tab4;
    MyTab Tab3;
    MyTab Tab2;
    MyTab Tab1;
    ChargeTab chargeTab;
    GhabzTab ghabzTab;
    TashilatTab tashilatTab;
    TransferTab transferTab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.document_fragment, container, false);
        Const.number = 0;
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title3));

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = view.findViewById(R.id.pager);

        chargeTab = new ChargeTab();
        ghabzTab = new GhabzTab();
        tashilatTab = new TashilatTab();
        transferTab = new TransferTab();

        ArrayList<MyTab> tabs = new ArrayList<>();
        Tab4 = new MyTab(chargeTab, getResources().getString(R.string.document1));
        Tab3 = new MyTab(ghabzTab, getResources().getString(R.string.document2));
        Tab2 = new MyTab(tashilatTab, getResources().getString(R.string.document3));
        Tab1 = new MyTab(transferTab, getResources().getString(R.string.document4));

        tabs.add(Tab4);
        tabs.add(Tab3);
        tabs.add(Tab2);
        tabs.add(Tab1);

        Pager pager = new Pager(getChildFragmentManager(), tabs);
        viewPager.setAdapter(pager);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

}
