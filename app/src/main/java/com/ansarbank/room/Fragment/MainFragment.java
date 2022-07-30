package com.ansarbank.room.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;

public class MainFragment extends Fragment implements View.OnClickListener {

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton button5;
    ImageButton button6;
    ImageButton button7;
    ImageButton button8;
    ImageButton button9;

    static ChargeFragment chargeFragment;
    static GhabzFragment ghabzFragment;
    static CardManagerFragment cardManegerFragment;
    static TransferFragment transferFragment;
    static InternetFragment internetFragment;
    static TashilatFragment tashilatFragment;
    static KheiriehFragment kheiriehFragment;
    static DrivingFineFragment drivingFineFragment;
    static DocumentFragment documentFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.main_fragment, container, false);
        Const.number = 1;

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.mainTitle));

        chargeFragment = new ChargeFragment();
        ghabzFragment = new GhabzFragment();
        cardManegerFragment = new CardManagerFragment();
        transferFragment = new TransferFragment();
        internetFragment = new InternetFragment();
        tashilatFragment = new TashilatFragment();
        documentFragment = new DocumentFragment();
        drivingFineFragment = new DrivingFineFragment();
        kheiriehFragment = new KheiriehFragment();

        button1 = view.findViewById(R.id.mainButton1);
        button1.setOnClickListener(this);
        button2 = view.findViewById(R.id.mainButton2);
        button2.setOnClickListener(this);
        button3 = view.findViewById(R.id.mainButton3);
        button3.setOnClickListener(this);
        button4 = view.findViewById(R.id.mainButton4);
        button4.setOnClickListener(this);
        button5 = view.findViewById(R.id.mainButton5);
        button5.setOnClickListener(this);
        button6 = view.findViewById(R.id.mainButton6);
        button6.setOnClickListener(this);
        button7 = view.findViewById(R.id.mainButton7);
        button7.setOnClickListener(this);
        button8 = view.findViewById(R.id.mainButton8);
        button8.setOnClickListener(this);
        button9 = view.findViewById(R.id.mainButton9);
        button9.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainButton1:
                ((MainActivity) getActivity()).changeFrag(chargeFragment);
                break;
            case R.id.mainButton2:
                ((MainActivity) getActivity()).changeFrag(ghabzFragment);
                break;
            case R.id.mainButton3:
                ((MainActivity) getActivity()).changeFrag(cardManegerFragment);
                break;
            case R.id.mainButton4:
                ((MainActivity) getActivity()).changeFrag(transferFragment);
                break;
            case R.id.mainButton5:
                ((MainActivity) getActivity()).changeFrag(internetFragment);
                break;
            case R.id.mainButton6:
                ((MainActivity) getActivity()).changeFrag(tashilatFragment);
                break;
            case R.id.mainButton7:
                ((MainActivity) getActivity()).changeFrag(documentFragment);
                break;
            case R.id.mainButton8:
                ((MainActivity) getActivity()).changeFrag(drivingFineFragment);
                break;
            case R.id.mainButton9:
                ((MainActivity) getActivity()).changeFrag(kheiriehFragment);
                break;

        }
    }


}
