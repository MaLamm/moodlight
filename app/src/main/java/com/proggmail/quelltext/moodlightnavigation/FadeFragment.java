package com.proggmail.quelltext.moodlightnavigation;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;


public class FadeFragment extends Fragment{

    private Spinner fadingChannel;
    private ImageView fadingImageChannel;
    private SeekBar fadingOffset;
    private SeekBar fadingPeriod;
    private SeekBar fadingAmplitude;
    private SeekBar fadingPhaseShift;
    private Spinner fadingFunction;
    private ImageView fadingImageFunction;

    private int channel;
    private int OffsetValue;
    private int PeriodValue;
    private int AmplitudeValue;
    private int PhaseShiftValue;
    private int function;


    private static final String TAG = "FadeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fade, container, false);

        fadingImageChannel = (ImageView)view.findViewById(R.id.ImageChannel);
        fadingImageFunction = (ImageView)view.findViewById(R.id.imageViewFunction);

        fadingFunction = (Spinner)view.findViewById(R.id.spinnerFunctions);
        ArrayAdapter<String> mAdapterFunctions = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.fading_functions));

        mAdapterFunctions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fadingFunction.setAdapter(mAdapterFunctions);

        fadingFunction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "FunctionSelected:"+position+":"+id);
                function = position;
                setFunctionImage(function);
                saveStatus(StartFadingWithParameter(channel, OffsetValue, PeriodValue, AmplitudeValue, PhaseShiftValue, function));
                saveChannel(channel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "nothing selected");
            }
        });


        fadingChannel = (Spinner)view.findViewById(R.id.spinnerChannel);
        ArrayAdapter<String> mAdapterChannel = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.fading_channel));

        mAdapterChannel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fadingChannel.setAdapter(mAdapterChannel);

        fadingChannel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "ChannelSelected:"+position);
                channel = position;
                switch(channel){
                    case 0:
                        loadChannel(channel);
                        saveStatus(StartFading(channel));
                        fadingImageChannel.setImageResource(R.mipmap.channelred);
                        break;
                    case 1:
                        loadChannel(channel);
                        saveStatus(StartFading(channel));
                        fadingImageChannel.setImageResource(R.mipmap.channelgreen);
                        break;
                    case 2:
                        loadChannel(channel);
                        saveStatus(StartFading(channel));
                        fadingImageChannel.setImageResource(R.mipmap.channelblue);
                        break;
                    case 3:
                        loadChannel(channel);
                        saveStatus(StartFading(channel));
                        fadingImageChannel.setImageResource(R.mipmap.channelwhite);
                        break;
                }
                fadingOffset.setProgress(OffsetValue);
                fadingPeriod.setProgress(PeriodValue);
                fadingAmplitude.setProgress(AmplitudeValue);
                fadingPhaseShift.setProgress(PhaseShiftValue);
                fadingFunction.setSelection(function);
                setFunctionImage(function);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "nothing selected");
            }
        });



        fadingOffset = (SeekBar)view.findViewById(R.id.seekBarOffset);
        fadingOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                OffsetValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "Offset value:"+OffsetValue);
                saveStatus(StartFadingWithParameter(channel, OffsetValue, PeriodValue, AmplitudeValue, PhaseShiftValue, function));
                saveChannel(channel);
            }
        });



        fadingPeriod = (SeekBar)view.findViewById(R.id.seekBarPeriod);
        fadingPeriod.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                PeriodValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "Period value:"+PeriodValue);
                saveStatus(StartFadingWithParameter(channel, OffsetValue, PeriodValue, AmplitudeValue, PhaseShiftValue, function));
                saveChannel(channel);
            }
        });


        fadingAmplitude = (SeekBar)view.findViewById(R.id.seekBarAmplitude);
        fadingAmplitude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AmplitudeValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "Amplitude value:"+AmplitudeValue);
                saveStatus(StartFadingWithParameter(channel, OffsetValue, PeriodValue, AmplitudeValue, PhaseShiftValue, function));
                saveChannel(channel);
            }
        });


        fadingPhaseShift = (SeekBar)view.findViewById(R.id.seekBarPhaseShift);
        fadingPhaseShift.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                PhaseShiftValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "Phase shift value:"+PhaseShiftValue);
                saveStatus(StartFadingWithParameter(channel, OffsetValue, PeriodValue, AmplitudeValue, PhaseShiftValue, function));
                saveChannel(channel);
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Fade");
    }

    public void setFunctionImage(int function){
        switch(function){
            case 0:
                fadingImageFunction.setImageResource(R.drawable.sinus);
                break;
            case 1:
                fadingImageFunction.setImageResource(R.drawable.sawtoothfor);
                break;
            case 2:
                fadingImageFunction.setImageResource(R.drawable.sawtoothback);
                break;
            case 3:
                fadingImageFunction.setImageResource(R.drawable.linearramp);
                break;
            case 4:
                fadingImageFunction.setImageResource(R.drawable.parabola);
                break;
            case 5:
                fadingImageFunction.setImageResource(R.drawable.cubicpulse);
                break;
            case 6:
                fadingImageFunction.setImageResource(R.drawable.cubicperiode);
                break;
        }
    }

    public String StartFadingWithParameter(int channel, int OffsetValue, int PeriodValue, int AmplitudeValue, int PhaseShiftValue, int function){
        String text = channel+":"+Integer.toHexString(OffsetValue).toUpperCase()+":"+Integer.toHexString(PeriodValue).toUpperCase()+":"+Integer.toHexString(AmplitudeValue).toUpperCase()+":"+Integer.toHexString(PhaseShiftValue).toUpperCase()+":"+Integer.toHexString(function).toUpperCase();
        int size = text.length();
        text = "2:14:i:6:"+size+":"+text+"\n";
        Log.d(TAG, "Start fading with parameters:"+text);
        return text;
    }


    public String StartFading(int channel){
        Log.d(TAG, "Start fading");
        String text = "2:4:i:1:1:"+channel+"\n";
        return text;
    }

    public String StopFading(int channel){
        Log.d(TAG, "Stop fading");
        String text = "2:4:i:1:1:"+channel+"\n";
        return text;
    }

    public String getOffsetString(int channel){
        Log.d(TAG, "fading Offset:"+OffsetValue);
        String text = channel+":"+OffsetValue;
        text= "2:4:i:2:"+text.length()+":"+text+"\n";
        return text;
    }

    public String getPeriodeString(int channel){
        Log.d(TAG, "fading periode/speed:"+OffsetValue);
        String text = channel+":"+OffsetValue;
        text = "2:5:i:2:"+text.length()+":"+text+"\n";
        return text;
    }

    public String getAmpliduteString(int channel){
        Log.d(TAG, "fading amplitude/luminance:"+AmplitudeValue);
        String text = channel+":"+AmplitudeValue;
        text= "2:6:i:2:"+text.length()+":"+text+"\n";
        return text;
    }

    public String getPhaseShift(int channel){
        Log.d(TAG, "fading phase shift:"+PhaseShiftValue);
        String text = channel+":"+PhaseShiftValue;
        text= "2:12:i:2:"+text.length()+":"+text+"\n";
        return text;
    }

    public String getFadingFunction(int channel){
        Log.d(TAG, "fading function index:"+function);
        String text = channel+":"+function;
        text= "2:6:i:2:"+text.length()+":"+text+"\n";
        return text;
    }

    public void saveStatus(String s){
        Log.d(TAG, "save status in shared preferences");
        //haredPreferences mSharedPreferences = this.getActivity().getSharedPreferences("moodlight", Context.MODE_PRIVATE);
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("FADE", s);
        Log.d(TAG, "status:"+s);
        editor.commit();
    }

    public void saveChannel(int CH){
        Log.d(TAG, "save CH:"+CH+" in shared preferences");
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("OffsetValue"+CH, OffsetValue);
        editor.putInt("PeriodValue"+CH, PeriodValue);
        editor.putInt("AmplitudeValue"+CH, AmplitudeValue);
        editor.putInt("PhaseShiftValue"+CH, PhaseShiftValue);
        editor.putInt("function"+CH, function);
        editor.commit();
    }

    public void loadChannel(int CH){
        Log.d(TAG, "load CH:"+CH+" from shared preferences");
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        OffsetValue = mSharedPreferences.getInt("OffsetValue"+CH,0);
        PeriodValue = mSharedPreferences.getInt("PeriodValue"+CH,0);
        AmplitudeValue = mSharedPreferences.getInt("AmplitudeValue"+CH,0);
        PhaseShiftValue = mSharedPreferences.getInt("PhaseShiftValue"+CH,0);
        function = mSharedPreferences.getInt("function"+CH,0);
    }


    @Override
    public void onPause(){
        Log.d(TAG, "onStop: called.");
        super.onPause();
        StopFading(channel);

    }

}
