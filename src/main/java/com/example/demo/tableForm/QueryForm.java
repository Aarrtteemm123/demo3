package com.example.demo.tableForm;

public class QueryForm {
    private String string;
    private String string2;
    private String string3;
    private int num;
    private int num2;
    private int num3;


    public QueryForm(String string, String string2, String string3, int num, int num2, int num3) {
        this.string = string;
        this.string2 = string2;
        this.string3 = string3;
        this.num = num;
        this.num2 = num2;
        this.num3 = num3;
    }

    public int getNum3() {
        return num3;
    }

    public void setNum3(int num3) {
        this.num3 = num3;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public QueryForm() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
