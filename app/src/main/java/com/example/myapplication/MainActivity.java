package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Model.*;
import com.example.myapplication.Database.database;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


//

    RecyclerView recyclerViewBar,recyclerViewLine;


    LineChart lineChart;
    List<Double> lineChartList = new ArrayList<Double>();

    //

    EditText editText1,editText2,editText3;
    Button Savebutton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intFunction();
        intLisiner();
        addDataToGraph();

    }



    //Data get and set



    //Set on Click Save Button

    private void intLisiner() {

        Savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                SaveToDatabase();
                Toast.makeText(MainActivity.this, "work Succefully", Toast.LENGTH_SHORT).show();
                editText1 .setText("");
                editText2 .setText("");
                editText3 .setText("");
                lineChart.notifyDataSetChanged();

            }
        });
    }





    //Bar chart



    private void intFunction()
    {

        lineChart = findViewById(R.id.linechart);


        Savebutton = findViewById(R.id.savebutton);

        editText1 = findViewById(R.id.editvalue1);
        editText2 = findViewById(R.id.editvalue2);
        editText3 = findViewById(R.id.editvalue3);



        recyclerViewBar = findViewById(R.id.RecycleBarchart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBar.setLayoutManager(layoutManager);

    }



    private database db;

    public void addDataToGraph()
    {



        lineChartList.clear();
        db = new database(this);

        for (int i = 0; i < db.queryBarData().size(); i++)
        {

            com.example.myapplication.Model.BarChart barChart = db.queryBarData().get(i);
            Double value = (Double.valueOf(Integer.parseInt(barChart.getY_data1()) +Integer.parseInt( barChart.getY_data2())
                    + Integer.parseInt(barChart.getY_data1()))) / 3 ;

            lineChartList.add(value);




        }


        showChartData();

    }

    private void showChartData() {


        MyAdeptarBarchart adeptarBarchart = new MyAdeptarBarchart(this,db.queryBarData());

        recyclerViewBar.setAdapter(adeptarBarchart);
        lineChartFunction();

    }




    private void lineChartFunction()
    {
        //Data


        ArrayList<Entry> Values = new ArrayList<>();

        for(int i = 0;i< lineChartList.size(); i++)
        {
            Values.add(new Entry(   i      ,      Float.parseFloat(String.valueOf(lineChartList.get(i)))));
        }

        //Set Data


        LineDataSet set1 = new LineDataSet(Values,"Data Set 1");
        set1.setFillAlpha(0);

        set1.setValueTextSize(8f);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setValueTextColor(Color.BLUE);





        ArrayList<ILineDataSet>dataSets = new ArrayList<>();

        dataSets.add(set1);

        LineData datas = new LineData(dataSets);



        //Data Set Show in Line Chart
        lineChart.setData(datas);






    }
    public void SaveToDatabase()
    {
        db = new database(this);

        String yvalue1 = editText1.getText().toString();
        String yvalue2 = editText2.getText().toString();
        String yvalue3 = editText3.getText().toString();

        db.saveData(new com.example.myapplication.Model.BarChart(yvalue1,yvalue2,yvalue3));

        addDataToGraph();


        db.close();




    }



}

