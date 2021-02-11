package com.example.lesson3.Calc;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Arithmetics {

    private Display display;
    private double result = 0.0;
    private double operand = 0.0;
    private boolean negativeSign = false;
    private List<Object> list = new ArrayList<>();
    private double tmp;
    private boolean equalsWasPressed = false;

    public void setDot(boolean dot) {
//        Log.d("MMM", "operand = "+ operand + "; result = " + result);
        if (list.contains(".")) return;
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

    public Arithmetics(Display display) {
        this.display = display;
    }

    public double getOperand() {
        return operand;
    }

    public void setDigitPressed(int buttonPressed) {
        if (buttonPressed == 0 && list.size() == 0) return;
        if (list.size() == 1 && list.get(0).equals(0)) list.clear();
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

    public void setValueToScreen(List list) {      // печатаем результат
        StringBuilder stringBuilder = new StringBuilder();

        if (list.contains(".")) {                    // в списке есть десятичная точка? 123457.987
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
    }

    public void setNegativeSign() {
        operand = -operand;
        negativeSign = !negativeSign;
        display.setSignDisplay(negativeSign);
    }

    public void actionPlus() {
        if (display.getActionDisplay() != '+') {        // + нажат первый раз
            result = getValueFromList();
            operand = 0;
            list.clear();
            display.setActionDisplay("+");
            equalsWasPressed = false;
        } else {                                        // + нажат не первый раз
            if(!equalsWasPressed) {
                result += operand;
                setValueToList(result);
                setValueToScreen(list);
                operand = 0;
                list.clear();
                display.setActionDisplay("+");
                equalsWasPressed = false;
            } else {
                operand = 0;
                list.clear();
                equalsWasPressed = false;
            }
        }
    }

//    public void actionPlus() {
////        Log.d("MMM", "getValueFromList = " + getValueFromList());
//        if(display.getActionDisplay() != ' ') {
////            equalsPressed();
//            operand = 0.0;
//        }
//        Log.d("MMM", "actionPlus1.result = " + result + "; operand = " + operand);
//        result += operand;
//        operand = 0;
//        list.clear();
//        Log.d("MMM", "actionPlus2.result = " + result + "; operand = " + operand);
//        display.setActionDisplay("+");
//        setResultToList(result);
//        setResultToScreen(list);
//        Log.d("MMM", "actionPlus3.result = " + result + "; operand = " + operand);
//    }

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

    public void actionMinus() {
        if(display.getActionDisplay() != ' ') operand = 0.0;
        Log.d("MMM", "actionMinus1.result = " + result + "; operand = " + operand);
        result += operand;
        operand = 0;
        list.clear();
        Log.d("MMM", "actionMinus2.result = " + result + "; operand = " + operand);
        display.setActionDisplay("-");
        setValueToList(result);
        setValueToScreen(list);
        Log.d("MMM", "actionMinus3.result = " + result + "; operand = " + operand);
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
}
