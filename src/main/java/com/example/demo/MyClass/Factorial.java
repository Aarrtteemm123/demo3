package com.example.demo.MyClass;

public class Factorial {
    int number;
    double factorial[] = new double[10];

    public Factorial() {
    }

    public Factorial(int number) {
        if (number >= 0)
            this.number = number;
        factorial[factorial.length - 1] = 1;
    }

    public void calculationFactorial() {

        for (int i = 1; i <= number; i++) {
            multiplicationArrOnNum(i);
        }
        deleteFrontZero();
    }

    private double[] multiplicationArrOnNum(int num) {
        for (int i = factorial.length - 1; i > -1; i--)
            factorial[i] *= num;
        factorial = checkOverflow(factorial);
        return factorial;
    }


    private double[] checkOverflow(double arr[]) {
        for (int i = arr.length - 1; i > -1; i--) {
            if (arr[i] > 9 && i != 0) {
                int remainder = (int) (arr[i] % 10);
                arr[i - 1] += (arr[i] - remainder) / 10;
                arr[i] = remainder;
            }
            if (arr[i] > 9 && i == 0) {
                arr = arrResize(arr, (int) (1.5 * arr.length));
                i = arr.length - 1;
            }
        }
        return arr;
    }

    private double[] arrResize(double arr[], int newLenght) {
        if (newLenght > arr.length) {
            double  bufferArr[] = new double [newLenght];
            int indexElement = bufferArr.length - 1;
            for (int i = arr.length - 1; i > -1; i--) {
                bufferArr[indexElement] = arr[i];
                indexElement--;
            }
            return bufferArr;
        }
        double  bufferArr[] = new double [newLenght];
        int indexElement = arr.length - 1;
        for (int i = bufferArr.length - 1; i > -1; i--) {
            bufferArr[i] = arr[indexElement];
            indexElement--;
        }
        return bufferArr;

    }

    private void deleteFrontZero() {
        int i = 0;
        while (factorial[i] == 0 && i < factorial.length - 1)
            i++;
        if (i != 0)
            factorial = arrResize(factorial, factorial.length - i);
    }

    private void clearFactorialArr() {
        for (int i = 0; i < factorial.length; i++)
            factorial[i] = 0;
    }

    public double [] getFactorial() {
        return factorial;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number >= 0)
            this.number = number;
    }

    public void print() {
        for (int i = 0; i < factorial.length; i++) {
            if (i % 100 == 0 && i != 0)
                System.out.println();
            System.out.print((int) factorial[i]);
        }
        System.out.println("\n\nSize(num) : " + factorial.length);
        System.out.println("Lenght(m) : " + (double) factorial.length * 2.5 / 1000);
    }
}
