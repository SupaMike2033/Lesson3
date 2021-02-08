package com.example.lesson3.Calc;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Arithmetics {

    private Display display;
    private double result = 0.0;
    private double operand = 0.0;
    private int buttonPressed;
    private List<Object> list = new ArrayList<>();

    public void setDot(boolean dot) {
        if (buttonPressed == 0 && list.size() == 0) {        // если первая нажатая кнопка - точка
            list.add(0);
        }
        list.add(".");
        Log.d("MMM", "Поставили точку: " + list.toString());
        setResultToScreen(list);
    }


    public Arithmetics(Display display) {
        this.display = display;
    }

    public double getOperand() {
        return operand;
    }

    public void setButtonPressed(int buttonPressed) {
        if (buttonPressed == 0 && list.size() == 0) return;
        list.add(buttonPressed);
        setResultToScreen(list);
    }

    public void setResultToScreen(List list) {      // печатаем результат
        StringBuilder stringBuilder = new StringBuilder();

        if (list.contains(".")) {                    // в списке есть десятичная точка 123457.987
            StringBuilder intPart = new StringBuilder();
            StringBuilder fractPart = new StringBuilder();
            int dotIndex = list.indexOf(".");
            double tmpFractPart = 0.0;

            for (int i = 0; i < dotIndex; i++) {
                intPart.append(list.get(i));
            }
            operand = Double.parseDouble(intPart.toString());   // = 123457
            stringBuilder.append(intPart);
            Log.d("MMM", "Целая часть: " + intPart.toString());

            stringBuilder.append(".");
            //-----------------------------------------------------
            // если только что нажали точку и дробной части ещё нет
            if(dotIndex == (list.size() - 1)) {
                tmpFractPart = 0.0;
//                stringBuilder.append(".");
            } else {
                for (int i = dotIndex + 1; i < list.size(); i++) {
                    fractPart.append(list.get(i));
                }                                                   // = 987
//                stringBuilder.append(".");
                stringBuilder.append(fractPart);
                Log.d("MMM", "Дробная часть: " + fractPart.toString());

                tmpFractPart = Double.parseDouble(fractPart.toString()) / Math.pow(10, fractPart.length());
            }
            operand += tmpFractPart;                            // 123457.987

        } else {                                    // в списке нет десятичной точки
            for (Object digit : list) {
                stringBuilder.append(digit);
            }
            operand = Double.parseDouble(stringBuilder.toString());
        }


        Log.d("MMM", String.valueOf(operand));

        display.setDisplayText(stringBuilder.toString());
    }

    public void eraseAll() {
        result = 0.0;
        operand = 0.0;
//        setResultToScreen(result);
    }
}
