package com.example.lesson3.Counters;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DataBucket implements Parcelable {
//public class DataBucket implements Serializable {

    private int counter1;
    private int counter2;
    private int counter3;

    public DataBucket() {
        counter1 = 0;
        counter2 = 0;
        counter3 = 0;
    }

    protected DataBucket(Parcel in) {
        counter1 = in.readInt();
        counter2 = in.readInt();
        counter3 = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(counter1);
        dest.writeInt(counter2);
        dest.writeInt(counter3);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataBucket> CREATOR = new Creator<DataBucket>() {
        @Override
        public DataBucket createFromParcel(Parcel in) {
            return new DataBucket(in);
        }

        @Override
        public DataBucket[] newArray(int size) {
            return new DataBucket[size];
        }
    };

    public void incrementCounter1() {
        counter1++;
    }

    public void incrementCounter2() {
        counter2++;
    }

    public void incrementCounter3() {
        counter3++;
    }

       public int getCounter1() {
        return counter1;
    }

    public void setCounter1(int counter1) {
        this.counter1 = counter1;
    }

    public int getCounter2() {
        return counter2;
    }

    public void setCounter2(int counter2) {
        this.counter2 = counter2;
    }

    public int getCounter3() {
        return counter3;
    }

    public void setCounter3(int counter3) {
        this.counter3 = counter3;
    }

}
