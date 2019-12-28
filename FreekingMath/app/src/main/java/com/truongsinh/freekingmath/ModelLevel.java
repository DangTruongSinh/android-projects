package com.truongsinh.freekingmath;

public class ModelLevel {
    public  int difficultLevel=1;
    // operators
    public static final int ADD =0;
    public static final int SUB =1;
    public static final int MUL =2;
    public static final int DIV =3;
    // operators text
    public static final String ADD_TXT ="+";
    public static final String SUB_TXT ="-";
    public static final String MUL_TXT ="X";
    public static final String DIV_TXT ="/";
    public static final String[] arrOperator={ADD_TXT,SUB_TXT,MUL_TXT,DIV_TXT};

    // values
    public int x=0;
    public int y=0;
    public String EQUAL="=";
    public int result=0;
    public int operator=0;
    public boolean correctWrong;
    public String operator_txt;
    public String resul_txt;
    //max value of operator depend on level: easy, medium, hard
    public static final int MAX_OPERATOR_EASY = 5;
    public static final int MAX_OPERATOR_MEDIUM = 15;
    public static final int MAX_OPERATOR_HARD = 50;
    public static final int MAX_OPERATOR_SUPPER = 5;

    public static final int[] arrMaxOperator ={MAX_OPERATOR_EASY,MAX_OPERATOR_MEDIUM,MAX_OPERATOR_HARD,MAX_OPERATOR_SUPPER};
}
