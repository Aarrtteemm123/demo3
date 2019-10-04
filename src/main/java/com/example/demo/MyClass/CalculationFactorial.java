package com.example.demo.MyClass;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

public class CalculationFactorial extends Thread {
    private int n;
    private int indexThread;
    private int countThreads;
    private int startNumber;
    private final int sizeGroup = 2;
    private int nextNumber;
    private Phaser phaser;
    ArrayList<double[]> arrayList=new ArrayList<>(countThreads);

    public CalculationFactorial(int n, int indexThread, int countThreads, Phaser phaser,ArrayList arrayList) {
        if (n > 0 && indexThread > 0 && countThreads > 1) {
            this.n = n;
            this.indexThread = indexThread;
            this.countThreads = countThreads;
            this.nextNumber = countThreads * sizeGroup;
            this.arrayList=arrayList;
            this.phaser = phaser;
            phaser.register();
        } else {
            try {
                throw new Exception("Неправильні данні!");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void run() {
        calculationFactorial();
    }

    private void calculationFactorial() {

        //---------------------------------------------------------------------

        if (n < 2 * countThreads) {
            try {
                throw new Exception("n замале для такої кількості потоків!");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return;
        }
        startNumber = (indexThread - 1) * sizeGroup + 1;

        //---------------------------------------------------------------------

        double mainArr[] = new double[10];
        mainArr[mainArr.length - 1] = 1;
        if (n % sizeGroup == 0) {
            while (startNumber < n) {
                mainArr = multiplicationArrOnNum((startNumber * (startNumber + 1)), mainArr);
                startNumber += nextNumber;
            }
        } else {
            while (startNumber < n - 1) {
                mainArr = multiplicationArrOnNum((startNumber * (startNumber + 1)), mainArr);
                startNumber += nextNumber;
            }
            if(startNumber==n)
                mainArr = multiplicationArrOnNum(n, mainArr);
        }

        synchronized (arrayList)
        {
            arrayList.add(mainArr);
        }

        //---------------------------------------------------------------------

        phaser.arriveAndAwaitAdvance();

        if(countThreads%2==0)
        {
            int T=1;
            int countFile=countThreads/2;
            int threadName=1;
            while (countFile!=1)
            {
                for (int i = 0; i < countFile; i++)
                {
                    if(threadName==indexThread)
                    {
                        double secondArr[];
                        synchronized (arrayList)
                        {
                            mainArr=arrayList.get(threadName-1);
                            secondArr=arrayList.get(threadName+T-1);
                        }
                        mainArr=multiplicationArrOnArr(mainArr,secondArr);
                        synchronized (arrayList)
                        {
                            arrayList.add(threadName-1,mainArr);
                            arrayList.remove(threadName+T-1);
                        }
                    }
                    threadName+=2*T;
                }
                phaser.arriveAndAwaitAdvance();
                T*=2;
                countFile/=2;
                threadName=1;
            }
            if(threadName==indexThread)
            {
                double secondArr[];
                synchronized (arrayList)
                {
                    mainArr=arrayList.get(threadName-1);
                    secondArr=arrayList.get(threadName+T-1);
                }
                mainArr=multiplicationArrOnArr(mainArr,secondArr);
                synchronized (arrayList)
                {
                    arrayList.clear();
                }
                printResult(mainArr);
            }
        }
        else
        {
            System.out.println("Кількість потоків має бути кратна степені 2");
        }
    }

    private void printResult(double arr[]) {
        for (int i = 0; i < arr.length; i++)
        {
            if (i % 80 == 0 && i != 0)
                System.out.println();
            System.out.print((int) arr[i]);
        }
        System.out.println("\n\nSize(num) : " + arr.length);
        System.out.println("Lenght(m) : " + (double) arr.length * 2.5 / 1000);
    }

    private double[] multiplicationArrOnArr(double firstArr[], double secondArr[]) {

        double resultArr[] = new double[(int) (2.2 * firstArr.length)];
        if (firstArr.length >= secondArr.length)
        {
            int displacement=0;
            for (int i = secondArr.length - 1; i > -1; i--)
            {
                double bufferArr[] = new double[firstArr.length];
                for (int j = firstArr.length - 1; j > -1; j--) {
                    bufferArr[j] = firstArr[j] * secondArr[i];
                }
                bufferArr=deleteFrontZero(bufferArr);
                for (int j = 0; j < bufferArr.length; j++)
                {
                    if (resultArr.length - j - displacement - 1 < 0)
                        resultArr = arrResize(resultArr, (int) (1.3 * resultArr.length));
                    resultArr[resultArr.length - j - displacement - 1] +=
                            bufferArr[bufferArr.length - j - 1];
                }
                displacement++;

            }

            resultArr=checkOverflow(resultArr);
            resultArr = deleteFrontZero(resultArr);
            return resultArr;
        }
        return multiplicationArrOnArr(secondArr, firstArr);
    }

    private boolean arrIsClear(double arr[])
    {
        int counter=0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]==0)
                counter++;
        }
        if(counter==arr.length)
            return true;
        return false;
    }

    private double[] deleteFrontZero(double arr[]) {
        int i = 0;
        while (arr[i] == 0 && i < arr.length - 1)
            i++;
        if (i != 0)
            arr = arrResize(arr, arr.length - i);
        return arr;
    }

    private double[] multiplicationArrOnNum(long num, double arr[]) {
        for (int i = arr.length - 1; i > -1; i--)
            arr[i] *= num;
        arr = checkOverflow(arr);
        return arr;
    }

    private double[] checkOverflow(double arr[]) {
        for (int i = arr.length - 1; i > -1; i--) {
            if (arr[i] > 9 && i != 0) {
                int remainder = (int) (arr[i] % 10);
                arr[i - 1] += (arr[i] - remainder) / 10;
                arr[i] = remainder;
            }
            if (arr[i] > 9 && i == 0) {
                arr = arrResize(arr, (int) (1.3 * arr.length));
                i = arr.length - 1;
            }
        }
        return arr;
    }

    private double[] arrResize(double arr[], int newLenght) {
        if (newLenght > arr.length) {
            double bufferArr[] = new double[newLenght];
            int indexElement = bufferArr.length - 1;
            for (int i = arr.length - 1; i > -1; i--) {
                bufferArr[indexElement] = arr[i];
                indexElement--;
            }
            return bufferArr;
        }
        double bufferArr[] = new double[newLenght];
        int indexElement = arr.length - 1;
        for (int i = bufferArr.length - 1; i > -1; i--) {
            bufferArr[i] = arr[indexElement];
            indexElement--;
        }
        return bufferArr;

    }

}
