package com.example.lesson3.Calc;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;

public class Display implements Parcelable {
    private double displayValue = 0.0;
    private String displayText = "";
    private MaterialTextView display;

    public Display(MaterialTextView display) {
        this.display = display;
    }

    public double getDisplayValue() {
        return displayValue;
    }

    public void setDisplayText(String text) {
        display.setText(text);
    }

    public void setDisplayText(double result, boolean isDot) {
        StringBuilder stringBuilder = new StringBuilder();
        if(!isDot) {
            stringBuilder.append(String.format("%.0f", result));
        } else {
            double tmp = result - (int) result;
            if((result % 1) == 0) {                             // точка только что нажата (дробной части ещё нет)
                stringBuilder.append(String.format("%.0f", result));
                stringBuilder.append(".");
            } else {
                stringBuilder.append(result);
            }
        }
        setDisplayText(stringBuilder.toString());
    }



    protected Display(Parcel in) {
    }

    public static final Creator<Display> CREATOR = new Creator<Display>() {
        @Override
        public Display createFromParcel(Parcel in) {
            return new Display(in);
        }

        @Override
        public Display[] newArray(int size) {
            return new Display[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
