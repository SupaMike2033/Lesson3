package com.example.lesson3.Calc;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Arithmetics {

    private Display display;
    private double result = 0.0;
    private double operand = 0.0;
    private List<Object> list = new ArrayList<>();

    public void setDot(boolean dot) {
        if (list.contains(".")) return;
        if (list.size() == 0) {        // если первая нажатая кнопка - точка
            list.add(0);
        }
        list.add(".");
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
        if (list.size() == 1 && list.get(0).equals(0)) list.clear();
        list.add(buttonPressed);
        setResultToScreen(list);
    }

    public void setResultToScreen(List list) {      // печатаем результат
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
        list.clear();
        display.setDisplayText("0");
    }

    public void eraseLast() {
        if(list.size() == 0) return;;
        if(list.size() == 1) {
            operand = 0.0;
            list.clear();
            display.setDisplayText("0");
        } else {
            list.remove(list.size() - 1);
            setResultToScreen(list);
        }
    }
}
