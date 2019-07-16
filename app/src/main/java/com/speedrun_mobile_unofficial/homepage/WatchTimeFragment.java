package com.speedrun_mobile_unofficial.homepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.speedrun_mobile_unofficial.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A placeholder fragment containing list view to
 * display "recently watched" page at homepage.
 */
public class WatchTimeFragment extends Fragment {

    private LineChart mWatchTimeChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_watch_time, container, false);
        mWatchTimeChart = (LineChart) rootView.findViewById(R.id.watchtimechart);
        System.out.println(mWatchTimeChart);
        lineChartSetup();

        return rootView;
    }

    private void lineChartSetup() {
        int[] times = {29, 56, 50, 125, 86, 126, 91};
        List<WatchTimeModel> dataObjects = new ArrayList<>();

        int month = 7;
        int day = 10;
        for(int i = 0; i < times.length; ++i) {
            Map<String, Object> map = new HashMap<>();
            LocalDate date = LocalDate.of(2019, month, day + i);
            map.put("date", date);
            map.put("timeInMinutes", times[i]);
            dataObjects.add(new WatchTimeModel(map));
        }

        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < dataObjects.size(); ++i) {
            // turn your data into Entry objects
            entries.add(new Entry(i, dataObjects.get(i).getTimeInMinutes()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
//        dataSet.setColor(...);
//        dataSet.setValueTextColor(...); // styling, ...

        LineData lineData = new LineData(dataSet);
        mWatchTimeChart.setData(lineData);
        mWatchTimeChart.setScaleEnabled(false);
        mWatchTimeChart.getXAxis().setDrawLabels(false);
        mWatchTimeChart.getAxisLeft().setDrawLabels(false);
        mWatchTimeChart.getAxisRight().setDrawLabels(false);
        mWatchTimeChart.getLegend().setEnabled(false);
        mWatchTimeChart.getDescription().setEnabled(false);
        mWatchTimeChart.invalidate(); // refresh
    }
}
