package com.example.klaushaulrik.context_awareness2;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Klaus Haulrik on 03-12-2015.
 */
public class CreateARFF {

    FastVector attributes;
    private BufferedWriter bufferedWriter;
    Instances data;
    private File file;
    String moveType;
    FastVector movementType;
    private File path;

    public CreateARFF(String type) throws IOException {
        moveType = type;

        file = new File("/mnt/sdcard/data.arff");

        attributes = new FastVector();

        attributes.addElement(new Attribute("mean"));
        attributes.addElement(new Attribute("stdDeviation"));
        attributes.addElement(new Attribute("min"));
        attributes.addElement(new Attribute("max"));
        movementType = new FastVector();
        movementType.addElement("walking");
        movementType.addElement("running");
        attributes.addElement(new Attribute("movementType", movementType));
        data = new Instances("detectMovementType", attributes, 0);


    }

    public void addValue(double mean, double stdDev,double min, double max)
    {
        double dMovementType = movementType.indexOf(moveType);
        data.add(new Instance(1.0D, new double[] {
                mean, stdDev, min, max, dMovementType
        }));
    }


    public void writeFile()
    {
        try
        {
            if (file.exists())          {
                file.delete();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(data.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            return;
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

}
