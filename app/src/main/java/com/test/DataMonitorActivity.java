package com.test;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.test.dao.GapResult;
import com.test.dao.GapResultDao;
import com.test.util.LongLog;
import com.test.util.MathChu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;

public class DataMonitorActivity extends AppCompatActivity {
    private PieChart pieChart;
    private CombinedChart combinedChart;
    private LineChart lineChart;
    private BarChart barChart;
    private TextView data_name,data_eat0,data_eat1,data_eat2,data_eat3;
    private TextView t_qualify,t_max,t_min,pie_avg;
    String uname;
    private Handler mainhandler;
    private GapResultDao gapResultDao;
    private MathChu mathChu;
    List<GapResult> gapResultList;
    //饼状图
    float datalow,datahei,datacom ;
    ArrayList<PieEntry> yValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_monitor);

        pieChart = findViewById(R.id.pieChar);
        combinedChart =findViewById(R.id.combin_chart);
        data_eat0=findViewById(R.id.kongfu);
        data_eat1=findViewById(R.id.eat1h);
        data_eat2=findViewById(R.id.eat2h);
        data_eat3=findViewById(R.id.eat3h);
        t_qualify=findViewById(R.id.t_qualify);
        t_max=findViewById(R.id.t_max);
        t_min=findViewById(R.id.t_min);
        pie_avg=findViewById(R.id.pie_avg);
        //数据传递
        data_name = findViewById(R.id.data1);
        Bundle bundle =getIntent().getExtras();
        uname =bundle.getString("uname");
        data_name.setText(uname);
        //获取用户信息
        gapResultDao =new GapResultDao();
        mainhandler = new Handler(getMainLooper());//获取主线程

        //获取用户数据信息
        getConsumer();
    }

    private void getConsumer() {
        uname=uname.toString().trim();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List allDate =new ArrayList();
                List listkong= new ArrayList<>();
                List list1h= new ArrayList<>();
                List list2h= new ArrayList<>();
                List list3h= new ArrayList<>();
                gapResultList= gapResultDao.findResule(uname);
                LongLog.printMsg("---gapResult-------------------"+'\n'+gapResultList.toString());
                Looper.prepare();
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DataMonitorActivity.this, "信息查询成功！", Toast.LENGTH_SHORT).show();
                        if (gapResultList!=null){
                            //num  共测量次数，用于计算达标率
                            int num=gapResultList.size();
                           LongLog.printMsg(gapResultList.size()+"--该用户共测量的记录次数--");
                           //分析返回的rs,将四种状态存入四个数组计算平均数
                            /**
                             *  达标率 空腹 4.4-8.2   餐后1h 6.7-12.7  餐后2h 5.0-11.0  餐后3h 4.4-9.9
                             *  高值   空腹 >8.2     餐后1h >12.7     餐后2h >11.0     餐后3h >9.9
                             *  低值   空腹 <4.4     餐后1h <6.7       餐后2h <5.0     餐后3h <4.4
                             */
                            int qualified=0;  //学个单词 ’合格‘  达到合格的测量记录
                            int moreHeit = 0;
                            int lessLow=0;
                           for (int i=0;i< num;i++) {
                              GapResult gapResult= gapResultList.get(i);
                               allDate.add(gapResult.getmeasurement());
                              String elp=gapResult.getMstatus();
                              switch (elp){
                                  case "空腹时":
                                      float b0=gapResult.getmeasurement();
                                      if (b0<4.4){
                                          lessLow+=1;
                                      }else if (b0>8.2){
                                          moreHeit+=1;
                                      }else {
                                          qualified+=1;
                                      }
                                      listkong.add(b0);
                                      break;
                                  case "餐后1小时":
                                      float b1=gapResult.getmeasurement();
                                      if (b1<6.7){
                                          lessLow+=1;
                                      }else if (b1>12.7){
                                          moreHeit+=1;
                                      }else {
                                          qualified+=1;
                                      }
                                      list1h.add(b1);
                                      break;
                                  case "餐后2小时":
                                      float b2=gapResult.getmeasurement();
                                      if (b2<5.0){
                                          lessLow+=1;
                                      }else if (b2>11.0){
                                          moreHeit+=1;
                                      }else {
                                          qualified+=1;
                                      }
                                      list2h.add(b2);
                                      break;
                                  case "餐后3小时":
                                      float b3=gapResult.getmeasurement();
                                      if (b3<4.4){
                                          lessLow+=1;
                                      }else if (b3>9.9){
                                          moreHeit+=1;
                                      }else {
                                          qualified+=1;
                                      }
                                      list3h.add(b3);
                                      break;
                              }
                            }
                            LongLog.printMsg(lessLow+"-lessLow-----------------");
                            LongLog.printMsg(moreHeit+"-moreHeit-----------------");
                            LongLog.printMsg(qualified+"-qualified-----------------");
                           datalow= (float) mathChu.div(lessLow,num,3);
                           datahei=(float) mathChu.div(moreHeit,num,3);
                           datacom=(float) mathChu.div(qualified,num,3);

                           //allData平均值
                            String allAvg=GapAverage(allDate);
                            pie_avg.setText(allAvg);
                           //达标率
                            LongLog.printMsg(qualified+"达标的个数-----");
                            float quali= (float) mathChu.div(qualified,num,3);
                            float tquali = quali*100;
                            LongLog.printMsg(quali+"达标率-----");
                           String dabiao=String.format("%.1f",tquali);
                           t_qualify.setText(dabiao);
                           //最高值
                            String max=GapMax(allDate);
                            t_max.setText(max);
                           //最低值
                            String min=GapMin(allDate);
                            t_min.setText(min);
                           //要返回的时间点平均值
                            String str_kong=GapAverage(listkong);
                            String str_1h=GapAverage(list1h);
                            String str_2h=GapAverage(list2h);
                            String str_3h=GapAverage(list3h);

                            data_eat0.setText(str_kong);
                            data_eat1.setText(str_1h);
                            data_eat2.setText(str_2h);
                            data_eat3.setText(str_3h);
                            /**饼状图 结合图   */
                            List list_line =new ArrayList();
                            float int_kong= Float.parseFloat(str_kong);
                            float int_1h= Float.parseFloat(str_1h);
                            float int_2h= Float.parseFloat(str_2h);
                            float int_3h= Float.parseFloat(str_3h);
                            list_line.add(int_kong);
                            list_line.add(int_1h);
                            list_line.add(int_2h);
                            list_line.add(int_3h);
                            setData();
                            setCombinChar(list_line);
                        }
                    }


                    //求平均数内置方法
                    private String GapAverage(List list) {
                        List<Float> lists = list;
                        //平均值
                        DoubleSummaryStatistics statistics = lists.stream().mapToDouble(Number::doubleValue).summaryStatistics();
                        float avg= (float) statistics.getAverage();
                        //利用字符串格式化的方式实现四舍五入,保留1位小数
                        String str_avg=String.format("%.1f",avg);
                        return str_avg;
                    }
                    //求最大值方法
                    private String GapMax(List list) {
                        List<Float> lists = list;
                        //平均值
                        DoubleSummaryStatistics statistics = lists.stream().mapToDouble(Number::doubleValue).summaryStatistics();
                        float max= (float) statistics.getMax();
                        //利用字符串格式化的方式实现四舍五入,保留1位小数
                        String str_max=String.format("%.1f",max);
                        return str_max;
                    }
                    //求最小值方法
                    private String GapMin(List list) {
                        List<Float> lists = list;
                        //平均值
                        DoubleSummaryStatistics statistics = lists.stream().mapToDouble(Number::doubleValue).summaryStatistics();
                        float min= (float) statistics.getMin();
                        //利用字符串格式化的方式实现四舍五入,保留1位小数
                        String str_min=String.format("%.1f",min);
                        return str_min;
                    }
                });
                Looper.loop();
            }
        }).start();
    }


        /**结合图*/
        private void setCombinChar(List list) {
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
//                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE
        });
        final CombinedData combinedData = new CombinedData();

        //折线图
            final Random random = new Random();
            final ArrayList<Entry> lineEntries = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                lineEntries.add(new Entry((float) i, (Float) list.get(i)));
            }
            LineDataSet lineDataSet = new LineDataSet(lineEntries, "测得平均血糖值");
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            List<Integer> colors = Arrays.asList(getResources().getColor(R.color.pei3));
            lineDataSet.setColors(colors);
            lineDataSet.setValueTextSize(15);
            lineDataSet.setValueTextColor(R.color.pei3);
            final LineData lineData = new LineData(lineDataSet);

            //折线
            //设置折线的式样   这个是圆滑的曲线（有好几种自己试试）     默认是直线
//            lineDataSet.setColor(Color.YELLOW);  //折线的颜色
            lineDataSet.setLineWidth(4);        //折线的粗细
            //是否画折线点上的空心圆  false表示直接画成实心圆
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setCircleHoleRadius(3);  //空心圆的圆心半径
            //圆点的颜色     可以实现超过某个值定义成某个颜色的功能   这里先不讲 后面单独写一篇
            lineDataSet.setCircleColor(Color.RED);
            lineDataSet.setCircleRadius(3);      //圆点的半径



            /**柱状图*/
            final ArrayList<BarEntry> barEntries = new ArrayList<>();
            barEntries.add(new BarEntry((float) 0, (float) 7.7));
            barEntries.add(new BarEntry((float) 1, (float) 11.3));
            barEntries.add(new BarEntry((float) 2, (float) 10.0));
            barEntries.add(new BarEntry((float) 3, (float) 9.1));
            final BarDataSet barDataSet = new BarDataSet(barEntries, "标准血糖值");
            List<Integer> colors1 = Arrays.asList(getResources().getColor(R.color.pei1));
            barDataSet.setColors(colors1);
            barDataSet.setValueTextSize(15);/////
            barDataSet.setValueTextColor(Color.GREEN);

            final BarData barData = new BarData(barDataSet);

            final List<String> lineTitles = Arrays.asList("空腹", "饭后1h", "饭后2h", "饭后3h");
            //设置轴上数据
            combinedChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(lineTitles));

            //折线图背景
            combinedChart.getXAxis().setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
            combinedChart.getAxisLeft().setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）
            combinedChart.getDescription().setTextSize(25);                    //字体大小
            combinedChart.getDescription().setTextColor(Color.GREEN);             //字体颜色

            //取消自带描述
            combinedChart.getDescription().setEnabled(false);
            //XY轴样式
//        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        lineChart.getAxisRight().setEnabled(false);
            combinedChart.setScaleYEnabled(false);
            //X轴
            XAxis xAxis=combinedChart.getXAxis();
            xAxis.setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
            xAxis.setAxisLineWidth(4);           //X轴粗细
            xAxis.setAxisMinimum(-0.5f);
            xAxis.setAxisMaximum((float) (barEntries.size()- 0.5));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置   默认为上面
            xAxis.setGranularity(1f);//????

            //Y轴
            YAxis AxisLeft=combinedChart.getAxisLeft();
            AxisLeft.setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）
            AxisLeft.setAxisLineWidth(4);           //Y轴粗细


            combinedData.setData(lineData);
            combinedData.setData(barData);
            combinedChart.getDescription().setEnabled(false);
            combinedChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            combinedChart.getAxisRight().setEnabled(false);
            combinedChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(lineTitles));
            combinedChart.setData(combinedData);
            //数据更新
            combinedChart.notifyDataSetChanged();
            combinedChart.invalidate();
            //动画（如果使用了动画可以则省去更新数据的那一步）
            combinedChart.animateY(1000); //折线在Y轴的动画  参数是动画执行时间 毫秒为单位

    }


    private void setData() {    //饼状图
        float data1 = datalow;
        float data2 = datahei;
        float data3 = datacom;
        LongLog.printMsg(data1+"-datalow-----------------");
        LongLog.printMsg(data2+"-datahei-----------------");
        LongLog.printMsg(data3+"-datacom-----------------");
        yValues.add(new PieEntry(data1,""));
        yValues.add(new PieEntry(data2,""));
        yValues.add(new PieEntry(data3,""));

        pieChart.getDescription().setEnabled(false);
        PieDataSet pieDataSet =new PieDataSet(yValues,"");
        pieDataSet.setValueLineColor(Color.BLACK);
        pieDataSet.setValueTextSize(20f);
        pieDataSet.setSliceSpace(5f);
        List<Integer> colors = Arrays.asList(getResources().getColor(R.color.pei3),getResources().getColor(R.color.pei2),getResources().getColor(R.color.pei1));
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}

/*

    */
/**柱状图*//*

    private void setBarChar() {
        final Random random = new Random();
        final List<String> lineTitles = Arrays.asList("7.7", "11.3", "10.0", "9.1","12.5");
        final ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            barEntries.add(new BarEntry(i, random.nextInt(100)));
        }
        final BarDataSet barDataSet = new BarDataSet(barEntries, "测得血糖值");
        List<Integer> colors = Arrays.asList(getResources().getColor(R.color.pei1));
        barDataSet.setColors(colors);
        final BarData barData = new BarData(barDataSet);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(lineTitles));
        barChart.setData(barData);
        barChart.invalidate();
    }*/
