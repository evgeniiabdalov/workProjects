package com.example.test_task.business;

public class HistoryObject {

    private String expressionString;
    private String resultString;

    public HistoryObject(String expressionString, String resultString){

        this.expressionString = expressionString;
        this.resultString = resultString;

    }

    public String getExpressionString() {
        return expressionString;
    }

    public String getResultString() {
        return resultString;
    }

    public String toString(){

        String returnString = this.expressionString + " = " + this.resultString;

        return returnString;

    }


}
