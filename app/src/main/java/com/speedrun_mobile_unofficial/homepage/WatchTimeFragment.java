package com.speedrun_mobile_unofficial.homepage;

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
import com.speedrun_mobile_unofficial.entities.DataStorageHepler;
import com.speedrun_mobile_unofficial.entities.Enums;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing list view to
 * display "recently watched" page at homepage.
 */
public class WatchTimeFragment extends Fragment {

    View rootView;
    private LineChart mWatchTimeChart;
    private LineDataSet dataSet;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Enums.STORAGE.WATCHTIMEFORMAT);
    LocalDate lastDateOfWeek = LocalDate.now();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_watch_time, container, false);
        mWatchTimeChart = (LineChart) rootView.findViewById(R.id.watch_time_chart);
        setUpLineChart();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        rootView.findViewById(R.id.watch_previous_week_btn).setOnClickListener(v -> {
            lastDateOfWeek = lastDateOfWeek.minusWeeks(1);
            setUpLineChart();
        });

        rootView.findViewById(R.id.watch_next_week_btn).setOnClickListener(v -> {
            lastDateOfWeek = lastDateOfWeek.plusWeeks(1);
            setUpLineChart();
        });

    }

    private void setUpLineChart() {
        dataSet = new LineDataSet(fetchWeeklyUsageStats(), "Label"); // add entries to dataset
        setUpLineChartAttribute();
        mWatchTimeChart.invalidate(); // refresh
    }

    private List<Entry> fetchWeeklyUsageStats() {
        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < 7; ++i) {
            // turn your data into Entry objects
            LocalDate currentDate = lastDateOfWeek.minusDays(6 - i);
            String currentDateString = currentDate.format(formatter);
            entries.add(new Entry(i, DataStorageHepler.getStorageLong(this.getContext(), currentDateString) / 60000));
        }
        return entries;
    }

    private void setUpLineChartAttribute() {
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
    }

    public class mValueFormatter extends ValueFormatter {
        @Override
        public String getPointLabel(Entry entry) {
            return String.format("%d min", (int) entry.getY());
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            LocalDate toDisplay = lastDateOfWeek.minusDays(6 - (int) value);
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
