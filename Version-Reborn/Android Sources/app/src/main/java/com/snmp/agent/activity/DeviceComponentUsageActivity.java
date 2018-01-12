package com.snmp.agent.activity;

import android.app.ActivityManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.snmp.agent.R;
//import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DeviceComponentUsageActivity extends AppCompatActivity implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private PieChart mChart;
    private PieChart mChart2;
    private PieChart mChart3;

    private String[] mMemoryUsage = new String[] {
            "0", "0"
    };
    private String[] mCpuUsage = new String[] {
            "100", "0"
    };

    private String[] mDiskUsage = new String[] {
            "80", "20"
    };

    final int[] MEMORY_COLOR = {
            Color.rgb(210,0,0),
            Color.rgb(38,196,17),
    };

    final int[] CPU_COLOR = {
            Color.rgb(98,0,161),
            //Color.rgb(255,200,1),
            Color.rgb(210,0,0)
    };

    final int[] DISK_COLOR = {
            Color.rgb(38,160,218),
            //Color.rgb(255,200,1),
            Color.rgb(210,0,0)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_usage);

        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        //mChart.getDescription().setEnabled(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setCenterText(generateCenterSpannableText());
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setOnChartValueSelectedListener(this);


        //mChart.isDrawEntryLabelsEnabled();
        mChart.setDrawEntryLabels(false);



        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double availableMegs = mi.availMem / 0x100000L;

        double percentAvail = mi.availMem / (double)mi.totalMem * 100.0;

        DecimalFormat df = new DecimalFormat("0");

        Log.v("memory",String.valueOf(mi.totalMem/ 1048576L));


        mMemoryUsage[0] = String.valueOf(df.format(100-percentAvail));
        mMemoryUsage[1] = String.valueOf(df.format(percentAvail));



        setData(2, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);




        // entry label styling
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(12f);













        mChart2 = (PieChart) findViewById(R.id.chart2);
        mChart2.setUsePercentValues(true);
        mChart2.getDescription().setEnabled(false);
        mChart2.setExtraOffsets(5, 10, 5, 5);
        mChart2.setDragDecelerationFrictionCoef(0.95f);
        mChart2.setCenterText(generateCenterSpannableText2());
        mChart2.setDrawHoleEnabled(true);
        mChart2.setHoleColor(Color.WHITE);
        mChart2.setTransparentCircleColor(Color.WHITE);
        mChart2.setTransparentCircleAlpha(110);
        mChart2.setHoleRadius(58f);
        mChart2.setTransparentCircleRadius(61f);
        mChart2.setDrawCenterText(true);
        mChart2.setRotationAngle(0);
        mChart2.setRotationEnabled(true);
        mChart2.setHighlightPerTapEnabled(true);
        mChart2.setOnChartValueSelectedListener(this);

        setData2(2, 100);

        mChart2.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l2 = mChart2.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l2.setOrientation(Legend.LegendOrientation.VERTICAL);
        l2.setDrawInside(false);
        l2.setXEntrySpace(7f);
        l2.setYEntrySpace(0f);
        l2.setYOffset(0f);

        // entry label styling
        mChart2.setEntryLabelColor(Color.WHITE);
        mChart2.setEntryLabelTextSize(12f);






        mChart3 = (PieChart) findViewById(R.id.chart3);
        mChart3.setUsePercentValues(true);
        mChart3.getDescription().setEnabled(false);
        mChart3.setExtraOffsets(5, 10, 5, 5);
        mChart3.setDragDecelerationFrictionCoef(0.95f);
        mChart3.setCenterText(generateCenterSpannableText3());
        mChart3.setDrawHoleEnabled(true);
        mChart3.setHoleColor(Color.WHITE);
        mChart3.setTransparentCircleColor(Color.WHITE);
        mChart3.setTransparentCircleAlpha(110);
        mChart3.setHoleRadius(58f);
        mChart3.setTransparentCircleRadius(61f);
        mChart3.setDrawCenterText(true);
        mChart3.setRotationAngle(0);
        mChart3.setRotationEnabled(true);
        mChart3.setHighlightPerTapEnabled(true);
        mChart3.setOnChartValueSelectedListener(this);


        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long totalDisk = totalBlocks * blockSize;

        long availableBlocks = stat.getAvailableBlocks();
        long useDisk = availableBlocks * blockSize;

        totalDisk = totalDisk/1024/1024;
        useDisk = useDisk/1024/1024;

        double percentDiskAvail = (useDisk * 100) / totalDisk;

        Log.v("disk",String.valueOf(((totalBlocks * blockSize)/1024)/1024));
        Log.v("disk2",String.valueOf(((useDisk)/1024)/1024));
        Log.v("disk2",String.valueOf(df.format(percentDiskAvail)));

        mDiskUsage[1] = String.valueOf(df.format(100-percentDiskAvail));
        mDiskUsage[0] = String.valueOf(df.format(percentDiskAvail));


        setData3(2, 100);

        mChart3.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l3 = mChart3.getLegend();
        l3.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l3.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l3.setOrientation(Legend.LegendOrientation.VERTICAL);
        l3.setDrawInside(false);
        l3.setXEntrySpace(7f);
        l3.setYEntrySpace(0f);
        l3.setYOffset(0f);
        l3.setTextSize(12f);



        // entry label styling
        mChart3.setEntryLabelColor(Color.WHITE);
        mChart3.setEntryLabelTextSize(12f);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawIcons(!set.isDrawIconsEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.actionTogglePercent:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
            case R.id.actionToggleSpin: {
                mChart.spin(1000, mChart.getRotationAngle(), mChart.getRotationAngle() + 360, Easing.EasingOption
                        .EaseInCubic);
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) (Float.parseFloat(mMemoryUsage[i]))));
        }




        PieDataSet dataSet = new PieDataSet(entries, "Mémoire RAM Usage");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);


        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : MEMORY_COLOR)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());




        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        //data.setValueTypeface(mTfLight);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();



    }

    private void setData2(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries2 = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries2.add(new PieEntry((float) (Float.parseFloat(mCpuUsage[i]))));
        }


        PieDataSet dataSet2 = new PieDataSet(entries2, "CPU Usage");

        dataSet2.setDrawIcons(false);

        dataSet2.setSliceSpace(3f);
        dataSet2.setIconsOffset(new MPPointF(0, 40));
        dataSet2.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : CPU_COLOR)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet2.setColors(colors);
        //dataSet.setSelectionShift(0f);


        PieData data2 = new PieData(dataSet2);
        data2.setValueFormatter(new PercentFormatter());
        data2.setValueTextSize(11f);
        data2.setValueTextColor(Color.BLACK);
        mChart2.setData(data2);
        mChart2.highlightValues(null);
        mChart2.invalidate();


    }

    private void setData3(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries3 = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries3.add(new PieEntry((float) (Float.parseFloat(mDiskUsage[i]))));
        }


        PieDataSet dataSet3 = new PieDataSet(entries3, "Disk Usage");

        dataSet3.setDrawIcons(false);

        dataSet3.setSliceSpace(3f);
        dataSet3.setIconsOffset(new MPPointF(0, 40));
        dataSet3.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : DISK_COLOR)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet3.setColors(colors);
        //dataSet.setSelectionShift(0f);


        PieData data3 = new PieData(dataSet3);
        data3.setValueFormatter(new PercentFormatter());
        data3.setValueTextSize(11f);
        data3.setValueTextColor(Color.BLACK);
        mChart3.setData(data3);
        mChart3.highlightValues(null);
        mChart3.invalidate();


    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Mémoire RAM\nDisponible / Utilisée");
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 0, s.length(), 0);
        return s;
    }


    private SpannableString generateCenterSpannableText2() {

        SpannableString s = new SpannableString("CPU\nDisponible / Utilisée");
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 0, s.length(), 0);
        return s;
    }

    private SpannableString generateCenterSpannableText3() {

        SpannableString s = new SpannableString("Espace Disque\nDisponible / Utilisée");
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 0, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
}