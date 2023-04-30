package com.test.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.test.MainActivity;
import com.test.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Work_Fragment extends Fragment {
    private ImageButton imageButton;
    private BarChart barChart;
    private Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.work_fragment,null);

        barChart=view.findViewById(R.id.celiang);
        imageButton =view.findViewById(R.id.btn_find);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"请先在血糖仪上测量", Toast.LENGTH_SHORT).show();
            }
        });
        setBarChar();
        return view;
    }
//    *柱状图

    private void setBarChar() {
        final Random random = new Random();
        final List<String> lineTitles = Arrays.asList(" ", " ", " ", " "," ");
        final ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 20));
        barEntries.add(new BarEntry(1, 10));
        barEntries.add(new BarEntry(2, 15));
        barEntries.add(new BarEntry(3, 35));
        barEntries.add(new BarEntry(4, 45));
        barEntries.add(new BarEntry(5, 35));
        barEntries.add(new BarEntry(6, 49));
        barEntries.add(new BarEntry(7, 8));
        barEntries.add(new BarEntry(8, 29));
        barEntries.add(new BarEntry(9, 21));
        barEntries.add(new BarEntry(10,30));
        final BarDataSet barDataSet = new BarDataSet(barEntries, "");
        List<Integer> colors = Arrays.asList(getResources().getColor(R.color.white),
                getResources().getColor(R.color.pei2),
                getResources().getColor(R.color.home_bottom_tab_blue),
                getResources().getColor(R.color.pei3),
                getResources().getColor(R.color.pei1),
                getResources().getColor(R.color.purple_200),
                getResources().getColor(R.color.pei2),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.home_bottom_tab_blue),
                getResources().getColor(R.color.pei3),
                getResources().getColor(R.color.purple_200)
                );
        barDataSet.setColors(colors);
        final BarData barData = new BarData(barDataSet);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(lineTitles));
        barChart.setData(barData);
        barChart.invalidate();
        barChart.animateY(1000); //折线在Y轴的动画  参数是动画执行时间 毫秒为单位
        barChart.animateX(1000);
        barChart.setScaleEnabled(false);//不可缩放
        barDataSet.setValueTextSize(0);
//        barChart.setBackgroundColor(Color.parseColor("#FF03DAC5"));
        //XY轴样式

        barChart.setScaleYEnabled(false);
        //X轴
        XAxis xAxis=barChart.getXAxis();
        xAxis.setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
        xAxis.setAxisLineWidth(0);           //X轴粗细
        xAxis.setAxisMinimum(-0.5f);
//        xAxis.setAxisMaximum((float) (barEntries.size()- 0.5));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置   默认为上面
        xAxis.setGranularity(1f);//????

        //Y轴
        YAxis AxisLeft=barChart.getAxisLeft();
        AxisLeft.setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）
        AxisLeft.setAxisLineWidth(4);           //Y轴粗细

    }

}
