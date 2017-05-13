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


import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

public class HSVFragment extends Fragment {

    private int intRed;
    private int intGreen;
    private int intBlue;
    private int intWhite;
    private int IntColorPickerColor;

    private ColorPicker picker;
    private SaturationBar saturationBar;
    private ValueBar valueBar;

    private static final String TAG = "HSVFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hsv, container, false);

        picker = (ColorPicker)view.findViewById(R.id.picker);
        saturationBar = (SaturationBar)view.findViewById(R.id.saturationbar);
        valueBar = (ValueBar)view.findViewById(R.id.valuebar);

        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);
        picker.setShowOldCenterColor(false);

        loadStatus();
        picker.setColor(IntColorPickerColor);

        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int intColor) {
                IntColorPickerColor = intColor;
                intRed = ((intColor >> 16) & 0xFF);
                intGreen = ((intColor >> 8) & 0xFF);
                intBlue = ((intColor >> 0) & 0xFF);
                // TODO: implement RGB to RGBW
                //intWhite = ((intRed/3)+(intGreen/3)+(intBlue/3));

                String hexRed = Integer.toHexString(intRed).toUpperCase();
                String hexGreen = Integer.toHexString(intGreen).toUpperCase();
                String hexBlue = Integer.toHexString(intBlue).toUpperCase();
                String hexwhite = Integer.toHexString(intWhite).toUpperCase();

                String text = hexRed+":"+hexGreen+":"+hexBlue+":"+hexwhite;
                Log.d(TAG, "HexColor: "+text);
                int i = text.length();
                text = "1:4:i:4:"+i+":"+text+"\n";
                saveStatus(IntColorPickerColor,text);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("ColorPicker");
    }

    public void loadStatus(){
        Log.d(TAG, "load color for ColorPicker from shared preferences");
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        IntColorPickerColor = mSharedPreferences.getInt("colorpickercolor", 0);
    }


    public void saveStatus(int color, String string){
        Log.d(TAG, "save color: "+Integer.toHexString(color).toUpperCase()+" for ColorPicker in shared preferences");
        //haredPreferences mSharedPreferences = this.getActivity().getSharedPreferences("moodlight", Context.MODE_PRIVATE);
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("RGBW", string);
        editor.putInt("colorpickercolor", color);
        editor.commit();

    }


}