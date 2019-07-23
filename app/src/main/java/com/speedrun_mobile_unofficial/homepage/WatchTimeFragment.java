package com.speedrun_mobile_unofficial.homepage;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.speedrun_mobile_unofficial.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        lineChartSetup();
        displayUsageStats();

        return rootView;
    }

    private void lineChartSetup() {
        int[] times = {29, 56, 50, 125, 86, 126, 91};
        List<WatchTimeModel> dataObjects = new ArrayList<>();

        LocalDate today = LocalDate.now();
        for(int i = 0; i < times.length; ++i) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", today.minusDays(6 - i));
            map.put("timeInMinutes", times[i]);
            dataObjects.add(new WatchTimeModel(map));
        }

        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < dataObjects.size(); ++i) {
            // turn your data into Entry objects
            entries.add(new Entry(i, dataObjects.get(i).getTimeInMinutes()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setLineWidth(3);
        dataSet.setCircleRadius((float)4);
        dataSet.setColors(new int[]{R.color.colorWatchTimeChartLine}, getContext());
        dataSet.setValueTextSize((float) 12.5);
        dataSet.setValueFormatter(new mValueFormatter());

        LineData lineData = new LineData(dataSet);
        mWatchTimeChart.setData(lineData);
        mWatchTimeChart.setMarker(new mMarkerView(getContext(), R.layout.fragment_watch_time_chart_marker_view));

        mWatchTimeChart.setBackgroundColor(getResources().getColor(R.color.colorWatchTimeChartBackground));
        mWatchTimeChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mWatchTimeChart.getXAxis().setTextSize(13);
        mWatchTimeChart.getXAxis().setYOffset(-3);
        mWatchTimeChart.getXAxis().setValueFormatter(new mValueFormatter());

        mWatchTimeChart.setScaleEnabled(false);
        mWatchTimeChart.getAxisLeft().setDrawLabels(false);
        mWatchTimeChart.getAxisRight().setDrawLabels(false);
        mWatchTimeChart.getLegend().setEnabled(false);
        mWatchTimeChart.getDescription().setEnabled(false);
        mWatchTimeChart.setDrawGridBackground(false);

        mWatchTimeChart.getXAxis().setDrawGridLines(false);
        mWatchTimeChart.getAxisLeft().setDrawGridLines(false);
        mWatchTimeChart.getAxisRight().setDrawGridLines(false);

        mWatchTimeChart.getXAxis().setDrawAxisLine(false);
        mWatchTimeChart.getAxisLeft().setDrawAxisLine(false);
        mWatchTimeChart.getAxisRight().setDrawAxisLine(false);

        mWatchTimeChart.invalidate(); // refresh
    }

    private void displayUsageStats() {
        UsageStatsManager usageStats = (UsageStatsManager) getActivity().getSystemService(getActivity().getBaseContext().USAGE_STATS_SERVICE);
        final long weekTimeMillis = 1000 * 60 * 60 * 24 * 7;
        UsageEvents events = usageStats.queryEventsForSelf(System.currentTimeMillis() - weekTimeMillis, System.currentTimeMillis());

        List<UsageStats> usageStatsList = usageStats.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, System.currentTimeMillis() - weekTimeMillis, System.currentTimeMillis());
        System.out.println(usageStatsList.size());
        for(UsageStats stats : usageStatsList) {
            if(stats.getPackageName().equals("com.speedrun_mobile_unofficial")) {
                System.out.println(stats.getTotalTimeInForeground());
            }
        }

    }

    public class mValueFormatter extends ValueFormatter {
        private LocalDate today = LocalDate.now();

        @Override
        public String getPointLabel(Entry entry) {
            return String.format("%d m", (int) entry.getY());
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            LocalDate toDisplay = today.minusDays(6 - (int) value);
            return String.format("%d/%d", toDisplay.getMonthValue(), toDisplay.getDayOfMonth());
        }

    }

    public class mMarkerView extends MarkerView {
        private TextView timeText;
        private MPPointF mOffset;

        public mMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);

            timeText = findViewById(R.id.chart_marker);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            timeText.setText(String.format("%d", (int) e.getY()));
        }

        @Override
        public MPPointF getOffset() {
            if(mOffset == null) {
                // center the marker horizontally and vertically
                mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
            }
            return mOffset;
        }
    }
}
