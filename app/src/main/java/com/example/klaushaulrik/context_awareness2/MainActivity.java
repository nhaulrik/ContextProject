package com.example.klaushaulrik.context_awareness2;
/// heeeej
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import weka.core.FastVector;
import weka.core.Instances;

import weka.core.converters.ArffSaver;

//Heeeeejejejeje

public class MainActivity extends Activity implements SensorEventListener {

    //hej niko :)


    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    TextView xVal,yVal,zVal;
    List<AccObj> accObjList = new ArrayList<AccObj>();
    List<ResultObject> resultList = new ArrayList<ResultObject>();

    Statistics statsX;
    Statistics statsY;
    Statistics statsZ;
    Statistics statsEuclid;
    Boolean stop = false;
    FastVector      atts;
    Instances       data;
    double[]        vals;
    CreateARFF createARFF;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        xVal = (TextView)findViewById(R.id.xVal);
        yVal = (TextView)findViewById(R.id.yVal);
        zVal = (TextView)findViewById(R.id.zVal);
        final Button collectBtn = (Button) findViewById(R.id.collectBTN);
        final Button logButton = (Button) findViewById(R.id.logButton);
        try {
            createARFF = new CreateARFF("walking");
        } catch (IOException e) {
            e.printStackTrace();
        }


        //FastVector      atts;
        //atts = new FastVector();

        logButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.v("MyActivity", accObjList.toString() + " SIZE: " + accObjList.size());
            }
        });

        collectBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                onStartClick();
            }
        });
    }

    public void onStartClick( ) {
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            xVal.setText("X:  " + (int) sensorEvent.values[0] + "");
            yVal.setText("Y:  " + (int) sensorEvent.values[1] + "");
            zVal.setText("Z:  " + (int) sensorEvent.values[2] + "");


            AccObj bla = new AccObj(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);

            List<Double> euclidNormArray = new ArrayList();

            if (accObjList.size() < 129) {
                accObjList.add(bla);
            }

            if (accObjList.size() == 128) {

                for (int i = 0; i < accObjList.size(); i++) {

                    euclidNormArray.add(
                           Math.sqrt( Math.pow(accObjList.get(i).getX(),2) + Math.pow(accObjList.get(i).getY(),2) + Math.pow(accObjList.get(i).getZ(),2) )
                                        );
                }

            }

            statsEuclid = new Statistics((ArrayList<Double>) euclidNormArray);

            if (accObjList.size() == 128) {

/**

                Log.v("MyActivity"," MEAN: " + statsEuclid.getMean()+"");
                Log.v("MyActivity"," DEVIANCE: " + statsEuclid.getStdDev()+"");
                Log.v("MyActivity"," MIN: " + statsEuclid.getMin()+"");
                Log.v("MyActivity"," MAX: " + statsEuclid.getMax()+"");
**/

                //resultList.add(new ResultObject(statsEuclid.getMean(),statsEuclid.getStdDev(),statsEuclid.getMin(),statsEuclid.getMax()));

                createARFF.addValue(statsEuclid.getMean(),statsEuclid.getStdDev(),statsEuclid.getMin(),statsEuclid.getMax());

                createARFF.writeFile();


                for (int i = 0; i < 64; i++) {
                    accObjList.remove(0);
                }
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
