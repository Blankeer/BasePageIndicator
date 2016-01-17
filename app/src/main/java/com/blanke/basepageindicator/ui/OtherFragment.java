package com.blanke.basepageindicator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OtherFragment extends Fragment {

    private TextView fragmentOtherText;
    private String title;

    public static OtherFragment newInstance(String text) {
        OtherFragment re = new OtherFragment();
        re.title = text;
        return re;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOtherText = new TextView(getContext());
        ViewGroup.LayoutParams prams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        fragmentOtherText.setLayoutParams(prams);
        fragmentOtherText.setGravity(Gravity.CENTER);
        return fragmentOtherText;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentOtherText.setText(title);
    }

}
