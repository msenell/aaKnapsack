package com.org.aAnaliz;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mustafa on 21.03.2015.
 */
public class cReadToFile
{
    ArrayList<cItem> items = new ArrayList<cItem>();
    int maxWeight = 4000;
    double Time;
    public void ReadToTxt()  //Dosyadan okuma yapan method.
    {
        items.clear();
        cSelectFile cs = new cSelectFile();
        String filePath = cs.SelectATextFile();
        String read="";
        if(filePath != null)
        {
            try {
                BufferedReader fr = new BufferedReader(new FileReader(filePath));
                String rLine="";
                while( (rLine = fr.readLine()) != null )
                {
                    splitLine(rLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //printArray();
        bruteForce();
    }

    private void splitLine(String read)
    {
        String[] sRead = read.split(" ");
        int size = sRead.length;
        cItem temp = new cItem();
        temp.iValue = Integer.parseInt(sRead[--size]);
        temp.iWeight = Integer.parseInt(sRead[--size]);
        for(int i = 0; i<size; i++)
        {
            temp.iName = sRead[i] + " ";
        }
        items.add(temp);
    }

    private void printArray()
    {
        int i = 0;
        for(cItem ci : items)
        {
            i++;
            System.out.print(i + " " + ci.iName + " " + ci.iWeight + " " + ci.iValue);
            System.out.println();
        }
    }

    private void bruteForce()
    {
        long startTime = System.currentTimeMillis();
        ArrayList<String> last = new ArrayList<String>();
        ArrayList<String> neu = new ArrayList<String>();
        String find="";
        int optValue=0;
        int optWeight = 0;
        int tempV, tempW;
        int size = items.size();
        for(int i = 0; i<size; i++) {
            last.add(String.valueOf(i));
        }
        for(int i = 1; i<=size; i++)
        {
            String str="";
            for(int j = 0; j<last.size(); j++)
            {
                String[] lst = last.get(j).split(",");
                int k = Integer.parseInt(lst[lst.length - 1])+1;
                for(; k<size; k++)
                {
                    str = String.valueOf(last.get(j))+","+k;
                    neu.add(str);
                    int[] vw = calculateThis(str);
                    if(vw[1] <= maxWeight && vw[0] > optValue)
                    {
                        optValue = vw[0];
                        optWeight = vw[1];
                        find = str;
                    }
                }
            }
            last.clear();
            for(int t = 0; t<neu.size(); t++) {
                last.add(neu.get(t));
            }
            neu.clear();
        }
        long endTime = System.currentTimeMillis();
        Time = (endTime - startTime)/1000.0;
        printResult(optValue, optWeight, find);
    }

    private int[] calculateThis(String str)
    {
        int[] vw = new int[2];
        String[] in = str.split(",");
        vw[0] = 0;
        vw[1] = 0;
        for(int i = 0; i<in.length; i++)
        {
            vw[0] += items.get(Integer.parseInt(in[i])).iValue;
            vw[1] += items.get(Integer.parseInt(in[i])).iWeight;
        }
        return vw;
    }

    private void printResult(int opV, int opW, String it)
    {
        System.out.println("Optimum Result : ");
        System.out.println("Weight :" + opW + "*, Value : " + opV);
        System.out.println("*Max. Weight : " + maxWeight);
        System.out.println("Operation Time : " + Time + " seconds");
        int maxName = 0, maxW = 0, maxV = 0;
        int i = 0;
        String[] itm = it.split(",");
        for(i = 0; i<itm.length; i++)
        {
            if(items.get(Integer.parseInt(itm[i])).iName.length() > maxName)
                maxName = items.get(Integer.parseInt(itm[i])).iName.length();
            if(String.valueOf(items.get(Integer.parseInt(itm[i])).iWeight).length() > maxW)
                maxW = String.valueOf(items.get(Integer.parseInt(itm[i])).iWeight).length();
            if(String.valueOf(items.get(Integer.parseInt(itm[i])).iValue).length() > maxV)
                maxV = String.valueOf(items.get(Integer.parseInt(itm[i])).iValue).length();
        }

        System.out.println();
        System.out.print("Item");
        i = 3;
        while(i<maxName)
        {
            System.out.print(" ");
            i++;
        }
        System.out.println(" Weight  Value");
        System.out.println("---------------------------------");
        for(int j = 0; j<itm.length; j++)
        {
            System.out.print(items.get(Integer.parseInt(itm[j])).iName);
            i = items.get(Integer.parseInt(itm[j])).iName.length();
            while(i<maxName)
            {
                System.out.print(" ");
                i++;
            }
            System.out.print("   ");
            i = String.valueOf(items.get(Integer.parseInt(itm[j])).iWeight).length();
            while(i<maxW)
            {
                System.out.print(" ");
                i++;
            }
            System.out.print(items.get(Integer.parseInt(itm[j])).iWeight);
            i = String.valueOf(items.get(Integer.parseInt(itm[j])).iValue).length();
            while(i<maxV)
            {
                System.out.print(" ");
                i++;
            }
            System.out.print("    ");
            System.out.print(items.get(Integer.parseInt(itm[j])).iValue);
            System.out.println();
        }

    }
}
