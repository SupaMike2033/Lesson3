package com.example.lesson3.Calc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Arithmetics extends AppCompatActivity {

    private Display display;
    private Activity activity;
    private double result = 0.0;
    private double operand = 0.0;
    private boolean negativeSign = false;
    private List<Object> list = new ArrayList<>();
    private double tmp;
    private boolean equalsWasPressed = false;

    public Arithmetics(Display display, Activity activity) {
        this.display = display;
        this.activity = activity;
    }

    public double getOperand() {
        return operand;
    }

    public void setDot(boolean dot) {
        if (list.contains(".")) return;
        if (!negativeSign) {
            negativeSign = false;
            display.setSignDisplay(false);
        }
        if (list.size() == 0) {        // если первая нажатая кнопка - точка
            list.add(0);
        }
        if (result == operand && (int) display.getActionDisplay() != ' ') {
            negativeSign = false;
            display.setSignDisplay(false);
            operand = 0;
            display.setDisplayText("");
            list.clear();
            list.add(0);
        }
        list.add(".");
        setValueToScreen(list);
    }

    public void setDigitPressed(int buttonPressed) {
        if (!negativeSign) {
            negativeSign = false;
            display.setSignDisplay(false);
        }
        if (buttonPressed == 0 && list.size() == 0) return;
        if (list.size() == 1 && list.get(0).equals(0)) {
            list.clear();
        }
        if (result == operand && (int) display.getActionDisplay() != ' ') {
            negativeSign = false;
            display.setSignDisplay(false);
            operand = 0;
            display.setDisplayText("");
            list.clear();
        }
        list.add(buttonPressed);
        setValueToScreen(list);
    }

    public void setValueToScreen(List list) {       // печатаем результат
        StringBuilder stringBuilder = new StringBuilder();

        if (list.contains(".")) {                   // в списке есть десятичная точка? 123457.987
            StringBuilder intPart = new StringBuilder();
            StringBuilder fractPart = new StringBuilder();
            int dotIndex = list.indexOf(".");
            double tmpFractPart = 0.0;

            for (int i = 0; i < dotIndex; i++) {    // выделяем целую часть числа
                intPart.append(list.get(i));
            }
            operand = Double.parseDouble(intPart.toString());   // = 123457
            stringBuilder.append(intPart);

            stringBuilder.append(".");              // раз точка есть, добавим её в билдер
            if(dotIndex == (list.size() - 1)) {     // если точку нажали только что и ещё не успели нажать что-то ещё...
                tmpFractPart = 0.0;
            } else {                                // если точка нажата ранее, а сейчас уже идёт ввод дробной части
                for (int i = dotIndex + 1; i < list.size(); i++) {
                    fractPart.append(list.get(i));
                }                                                   // = 987
                stringBuilder.append(fractPart);    // выделили дробную часть и добавили её к общему билдеру

                tmpFractPart = Double.parseDouble(fractPart.toString()) / Math.pow(10, fractPart.length());
            }
            operand += tmpFractPart;                            // 123457.987
        } else {                                    // в списке нет десятичной точки
            for (Object digit : list) {
                stringBuilder.append(digit);
            }
            operand = Double.parseDouble(stringBuilder.toString());
        }
        display.setDisplayText(stringBuilder.toString());
    }

    public void eraseAll() {
        result = 0.0;
        operand = 0.0;
        negativeSign = false;
        list.clear();
        display.setSignDisplay(negativeSign);
        display.setDisplayText("0");
        display.setActionDisplay(" ");
    }

    public void eraseLast() {
        if(list.size() == 0) return;;
        if(list.size() == 1) {
            operand = 0.0;
            list.clear();
            display.setDisplayText("0");
        } else {
            list.remove(list.size() - 1);
            setValueToScreen(list);
        }
        display.setActionDisplay(" ");
    }

    public void setNegativeSign() {
        operand = -operand;
        negativeSign = !negativeSign;
        display.setSignDisplay(negativeSign);
    }

    public void actionPlus() {
        double tmpCoef = 1.0;
        if (negativeSign) tmpCoef = -1.0;
        if (display.getActionDisplay() != '+') {        // + нажат первый раз
            result = getValueFromList() * tmpCoef;
            operand = 0;
//            list.clear();
            display.setActionDisplay("+");
        } else {                                        // + нажат не первый раз
            if(!equalsWasPressed) {
                result += operand * tmpCoef;
                Log.d("MMM", Double.toString(result));
                setValueToList(result);
                setValueToScreen(list);
                operand = 0;
//                list.clear();
                display.setActionDisplay("+");
            } else {                                    // + нажат первый раз после клавиши "=", на дисплее всё ещё горит "+"
                operand = 0;
//                list.clear();
            }
            negativeSign = false;
        }
        equalsWasPressed = false;
        negativeSign = false;
        list.clear();
    }

    public void actionMinus() {
        if (display.getActionDisplay() != '-') {        // - нажат первый раз
            result = getValueFromList();
            operand = 0;
            list.clear();
            display.setActionDisplay("-");
        } else {                                        // + нажат не первый раз
            if(!equalsWasPressed) {
                result += operand;
                setValueToList(result);
                setValueToScreen(list);
                operand = 0;
                list.clear();
                display.setActionDisplay("+");
            } else {                                    // + нажат первый раз после клавиши "=", на дисплее всё ещё горит "+"
                operand = 0;
                list.clear();
            }
            negativeSign = false;
            display.setSignDisplay(false);
        }
        equalsWasPressed = false;
    }

    public void equalsPressed() {
        equalsWasPressed = true;
        if(negativeSign) operand = -Math.abs(operand);
        switch (display.getActionDisplay()) {
            case '+':
                Log.d("MMM", "equalsPressed1.result = " + result + "; operand = " + operand);
                result += operand;
                break;
        }
        Log.d("MMM", "equalsPressed2.result = " + result + "; operand = " + operand);
        pushOperand();
        checkForNegative(result);
        setValueToList(result);
        setValueToScreen(list);
        popOperand();
        Log.d("MMM", "equalsPressed3.result = " + result + "; operand = " + operand);
    }

    private void checkForNegative(double result) {
        if(result < 0) {
            display.setSignDisplay(true);
        } else {
            display.setSignDisplay(false);
        }
    }

    private void setValueToList(double result) {
        list.clear();
        String tmp = Double.toString(Math.abs(result));
        int dotIndex = tmp.indexOf(".");
        int tmpCounter = 0;

        for (int i = 0; i < dotIndex; i++) {                        // выделяем целую часть до точки
            list.add(Character.getNumericValue(tmp.charAt(i)));
        }

        for (int i = dotIndex + 1; i < tmp.length(); i++) {
            if(tmp.charAt(i) != '0') tmpCounter++;
        }
        if(tmpCounter == 0) {                                       // после точки - одни нули
            return;
        }

        list.add(".");

        for (int i = dotIndex + 1; i < tmp.length(); i++) {
            list.add(Character.getNumericValue(tmp.charAt(i)));
        }
    }

    public double getValueFromList() {
        double numberToGetFromList = 0.0;
        StringBuilder stringBuilder = new StringBuilder();

        if (list.contains(".")) {                    // в списке есть десятичная точка? 123457.987
            StringBuilder tmpStrBldr = new StringBuilder();
            int dotIndex = list.indexOf(".");

            for (int i = 0; i < dotIndex; i++) {    // выделяем целую часть числа
                tmpStrBldr.append(list.get(i));
            }
            stringBuilder.append(tmpStrBldr);

            stringBuilder.append(".");              // раз точка есть, добавим её в билдер
            if(dotIndex == (list.size() - 1)) {     // если точку нажали только что и ещё не успели нажать что-то ещё, т.е. точка стоит на последнем месте...
                stringBuilder.append("0");
            } else {                                // если точка нажата ранее, а сейчас уже идёт ввод дробной части
                tmpStrBldr.setLength(0);            // очищаем стрингбилдер
                for (int i = dotIndex + 1; i < list.size(); i++) {
                    tmpStrBldr.append(list.get(i));
                }                                                   // = 987
                stringBuilder.append(tmpStrBldr);    // выделили дробную часть и добавили её к общему билдеру
            }

        } else {                                    // в списке нет десятичной точки
            for (Object digit : list) {
                stringBuilder.append(digit);
            }
        }
        numberToGetFromList = Double.valueOf(stringBuilder.toString());
        Log.d("MMM", "getValueFromList = " + numberToGetFromList);
        return numberToGetFromList;
    }

    private String trimFractZero(Double value) {
        String tmpStr;
        setValueToList(value);
        tmpStr = Double.toString(getValueFromList());
        list.clear();
        return tmpStr;
    }

    private void popOperand() {
        operand = tmp;
    }

    private void pushOperand() {
        tmp = operand;
    }

    public void menuPressedShort(Context context) {
        Constants.currentTheme++;
        if(Constants.currentTheme == Constants.themeNames.length) {
            Constants.currentTheme = 0;
        }

//        String str = resources.getString(Constants.themeNames[Constants.currentTheme]);   // это только для тестов
//        Log.d("MMM", str + " = " + Constants.currentTheme);                               // это только для тестов

        ThemeUtils.setCalcTheme(activity, Constants.currentTheme);
        Toast.makeText(activity.getApplicationContext(), "Удерживайте для выбора", Toast.LENGTH_SHORT).show();
    }

}
