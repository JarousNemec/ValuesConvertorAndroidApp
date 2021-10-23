package com.strejda_jara.valuesconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Spinner spinnerQuantities;
    Spinner spinnerFrom;
    EditText numberToConvert;
    Spinner spinnerTo;
    TextView labelResult;
    Button btnConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerQuantities = findViewById(R.id.spinnerPhysicalQuantity);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        numberToConvert = findViewById(R.id.numberToConvert);
        spinnerTo = findViewById(R.id.spinnerTo);
        labelResult = findViewById(R.id.labelResult);
        btnConvert = findViewById(R.id.btnConvert);

        setupQuantitiesSpinner();

        spinnerQuantities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setFromAndToUnits(parentView, selectedItemView, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void setupQuantitiesSpinner() {

        String[] operatorsArray = getResources().getStringArray(R.array.physicalQuantities);

        List list = new ArrayList(Arrays.asList(operatorsArray));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerQuantities.setAdapter(dataAdapter);


    }

    public void setFromAndToUnits(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        String[] units = new String[]{};
        String item = (String) spinnerQuantities.getSelectedItem();
        switch (item) {
            case "Distance":
                units = getResources().getStringArray(R.array.distanceUnits);
                break;
            case "Area":
                units = getResources().getStringArray(R.array.areaUnits);
                break;
            case "Cubic":
                units = getResources().getStringArray(R.array.cubicUnits);
                break;
        }
        List listUnits = new ArrayList(Arrays.asList(units));
        ArrayAdapter<String> unitsDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listUnits);

        unitsDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(unitsDataAdapter);
        spinnerTo.setAdapter(unitsDataAdapter);

    }

    public double getFromNumberBaseValue(String quantities, double fromNumber, String from){
         double temp = 0;
        if(quantities.equals("Distance")){
            switch (from) {
                case "mm":
                    temp = fromNumber / 1000;
                    break;
                case "cm":
                    temp = fromNumber / 100;
                    break;
                case "dm":
                    temp = fromNumber / 10;
                    break;
                case "m":
                    temp = fromNumber;
                    break;
                case "km":
                    temp = fromNumber * 1000;
                    break;
            }
        }
        else if(quantities.equals("Area")){
            switch (from) {
                case "mm2":
                    temp = fromNumber / 1000000;
                    break;
                case "cm2":
                    temp = fromNumber / 10000;
                    break;
                case "dm2":
                    temp = fromNumber / 100;
                    break;
                case "m2":
                    temp = fromNumber;
                    break;
                case "km2":
                    temp = fromNumber * 1000000;
                    break;
            }
        }
        else if(quantities.equals("Cubic")){
            switch (from) {
                case "mm3":
                    temp = fromNumber / 1000000000;
                    break;
                case "cm3":
                    temp = fromNumber / 1000000;
                    break;
                case "dm3":
                    temp = fromNumber / 1000;
                    break;
                case "m3":
                    temp = fromNumber;
                    break;
                case "km3":
                    temp = fromNumber * 1000000000;
                    break;
            }
        }
        return temp;
    }

    public double setNumberToRequestedValue(String quantities, double temp, String to){
        double result = 0;
        if(quantities.equals("Distance")){
            switch (to) {
                case "mm":
                    result = temp * 1000;
                    break;
                case "cm":
                    result = temp * 100;
                    break;
                case "dm":
                    result = temp * 10;
                    break;
                case "m":
                    result = temp;
                    break;
                case "km":
                    result = temp / 1000;
                    break;
            }
        }
        else if(quantities.equals("Area")){
            switch (to) {
                case "mm2":
                    result = temp * 1000000;
                    break;
                case "cm2":
                    result = temp * 10000;
                    break;
                case "dm2":
                    result = temp * 100;
                    break;
                case "m2":
                    result = temp;
                    break;
                case "km2":
                    result = temp / 1000000;
                    break;
            }
        }
        else if(quantities.equals("Cubic")){
            switch (to) {
                case "mm3":
                    result = temp * 1000000000;
                    break;
                case "cm3":
                    result = temp * 1000000;
                    break;
                case "dm3":
                    result = temp * 1000;
                    break;
                case "m3":
                    result = temp;
                    break;
                case "km3":
                    result = temp / 1000000000;
                    break;
            }
        }

        return result;
    }

    public void convert(View view) {
        double fromNumber = 0;
        String from = "mm";
        String to = "mm";
        String quantities = "Distance";
        double result = 0;
        double temp = 0;

        try {
            fromNumber = Double.parseDouble(numberToConvert.getText().toString());
            quantities = (String) spinnerQuantities.getSelectedItem();
            from = (String) spinnerFrom.getSelectedItem();
            to = (String) spinnerTo.getSelectedItem();


            temp = getFromNumberBaseValue(quantities,fromNumber,from);
            result = setNumberToRequestedValue(quantities,temp,to);

            NumberFormat nf = new DecimalFormat("#.###############");
            labelResult.setText(nf.format(result));

        } catch (NumberFormatException e) {
            labelResult.setText("Chybné zadání...");
        }


    }

}