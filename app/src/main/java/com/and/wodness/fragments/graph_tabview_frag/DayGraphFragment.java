package com.and.wodness.fragments.graph_tabview_frag;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.and.wodness.R;
import com.and.wodness.LineView;
import java.util.ArrayList;

/**
 * Created by Dacer on 11/15/13.
 */
public class DayGraphFragment extends Fragment {
    int randomint = 1;
    public DayGraphFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daygraph, container, false);
        final LineView lineView = (LineView) rootView.findViewById(R.id.line_view);


        initLineView(lineView);
        randomSet(lineView, lineView);
        return rootView;
    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[] {
                Color.parseColor("#6dcff6"), Color.parseColor("#acd373")
        });
       // lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    private void randomSet(LineView lineView, LineView lineViewFloat) {
        ArrayList<Integer> dataList = new ArrayList<>();
        float random = (float) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataList.add((int) (Math.random() * random));
        }

        ArrayList<Integer> dataList2 = new ArrayList<>();
        random = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataList2.add((int) (Math.random() * random));
        }
        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList);
        dataLists.add(dataList2);

        lineView.setDataList(dataLists);

//        ArrayList<Float> dataListF = new ArrayList<>();
//        float randomF = (float) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataListF.add((float) (Math.random() * randomF));
//        }
//
//        ArrayList<Float> dataListF2 = new ArrayList<>();
//        randomF = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataListF2.add((float) (Math.random() * randomF));
//        }
//
//
//        ArrayList<ArrayList<Float>> dataListFs = new ArrayList<>();
//        dataListFs.add(dataListF);
//        dataListFs.add(dataListF2);
//
//        lineViewFloat.setFloatDataList(dataListFs);
    }
}