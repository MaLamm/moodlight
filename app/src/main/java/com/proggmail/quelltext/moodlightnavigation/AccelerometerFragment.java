package com.proggmail.quelltext.moodlightnavigation;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class AccelerometerFragment extends Fragment implements SensorEventListener {

    private int intRed;
    private int intGreen;
    private int intBlue;

    private TextView xText,yText,zText,textViewCommand;
    private Sensor mSensor;
    private SensorManager mSensorManager;

    private static final String TAG = "AccelerometerFragment";

    @Override
    public void onSensorChanged(SensorEvent event) {
            XYZtoRGB(event.values[0],event.values[1],event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //not used
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accelerometer, container, false);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        if (mSensor == null) {
            // Use the accelerometer.
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(getActivity(), "Sorry, there are no accelerometers on your device",Toast.LENGTH_LONG).show();
            }
        }

        xText = (TextView)view.findViewById(R.id.textViewX);
        yText = (TextView)view.findViewById(R.id.textViewY);
        zText = (TextView)view.findViewById(R.id.textViewZ);

        textViewCommand = (TextView)view.findViewById(R.id.textViewCommand);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Accelerometer");
    }


    public void saveStatus(String string){
        Log.d(TAG, "save status in shared preferences");
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("ACC", string);
        editor.commit();

    }

    public void XYZtoRGB(float x, float y, float z){
        // TODO: implement an arithmetic mean
        intRed = Math.round(((x+12)/23)*255);
        intGreen = Math.round(((y+12)/23)*255);
        intBlue = Math.round(((z+12)/23)*255);

        xText.setText("X: "+intRed);
        zText.setText("Y: "+intGreen);
        yText.setText("Z: "+intBlue);

        String text = Integer.toHexString(intRed).toUpperCase()+":"+Integer.toHexString(intGreen).toUpperCase()+":"+Integer.toHexString(intBlue).toUpperCase();
        int size = text.length();
        text = "2:14:i:6:"+size+":"+text+"\n";
        textViewCommand.setText(""+text);
        saveStatus(text);

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
