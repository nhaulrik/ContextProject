package com.example.klaushaulrik.context_awareness2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Example from: http://stackoverflow.com/questions/7988486/how-do-you-calculate-the-variance-median-and-standard-deviation-in-c-or-java
 */
public class Statistics {

    ArrayList<Double> data;
    int size;

    public Statistics(ArrayList<Double> data)
    {
        this.data = data;
        size = data.size();
    }

    double getMean()
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    double getVariance()
    {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return temp/size;
    }

    double getStdDev()
    {
        return Math.sqrt(getVariance());
    }

    public double median()
    {
        Collections.sort(data);

        if (data.size() % 2 == 0)
        {
            return ((data.size() / 2) - 1 + data.size() / 2) / 2.0;
        }
        else
        {
            return data.size() / 2;
        }
    }

    public double getMin() {
        double min = 10000;

        for(double a : data) {
            if (a < min) {
                min = a;

            }
        }
        return min;
    }

    public double getMax() {
        double max = -10000;

        for(double a : data) {
            if (a > max) {
                max = a;

            }
        }
        return max;
    }

    public double euclidNorm() {
        double result;
        double sum = 0.0;


        for (double a : data) {
           sum += a * a ;
        }

       result = Math.sqrt(sum);


        return result;
    }

}
