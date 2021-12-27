package com.example.test_task.business;


import java.util.List;

public class ExpressionProcessor {

    private List<String> expressionList;

    public ExpressionProcessor(List<String> expressionList){
        this.expressionList = expressionList;
    }

    public Double  processExpression(int priority, int startValueIndex, int endValueIndex){


        System.out.println("-----------------------------------");

        System.out.println("priority " + priority );

        System.out.println(priority + " start value index " + startValueIndex);
        System.out.println(priority + " end value index " + endValueIndex);


        for(int i = startValueIndex; i <= endValueIndex; i++){
            System.out.println(this.expressionList.get(i));
        }

        System.out.println("-----------------------------------");

        int firstValueIndex = startValueIndex;
        int secondValueIndex = -1;

        Double resultValue = 0.0;
        Double currentValue = 0.0;


        for(int i = startValueIndex; i <= endValueIndex; i++) {

            if( this.checkOperationCondition(priority, this.expressionList.get(i), i, startValueIndex ) ){

                secondValueIndex = i - 1;

                if( firstValueIndex == secondValueIndex )
                {
                    if( expressionList.get(secondValueIndex).contains("sqrt") )
                    {
                        currentValue = this.assessSqrt( expressionList.get(secondValueIndex) );
                    }
                    else
                       currentValue = Double.parseDouble(expressionList.get(secondValueIndex));

                }
                else
                {
                    currentValue = this.processExpression(priority + 1, firstValueIndex, secondValueIndex);
                }

                resultValue = this.assessExpression(priority, resultValue, currentValue, firstValueIndex, startValueIndex);

                firstValueIndex = i + 1;

            }
            else if( i == endValueIndex ){

                secondValueIndex = i;

                if( firstValueIndex == secondValueIndex )
                {
                    if( expressionList.get(secondValueIndex).contains("sqrt") )
                    {
                        currentValue = this.assessSqrt( expressionList.get(secondValueIndex) );
                    }
                    else
                        currentValue = Double.parseDouble(expressionList.get(secondValueIndex));
                }
                else
                {
                    if( priority == 3 )
                        currentValue = this.processExpression(priority, firstValueIndex, secondValueIndex);
                    else
                        currentValue = this.processExpression(priority + 1, firstValueIndex, secondValueIndex);
                }

                resultValue = this.assessExpression(priority, resultValue, currentValue, firstValueIndex, startValueIndex);


            }


        }

        return resultValue;

    }

    private boolean checkOperationCondition(int priority, String expressionString, int currentValueIndex, int startValueIndex){

        if( priority == 1 ){
            if( expressionString.equals("+") || expressionString.equals("-") )
                return true;
        }
        else if( priority == 2 ){
             if( expressionString.equals("*") || expressionString.equals("/") )
                return true;
        }
        else if( priority == 3 ){
            if( (currentValueIndex - 1) == startValueIndex  )
                return true;
        }

        return false;

    }


    private Double assessExpression(int priority, Double resultValue, Double currentValue, int valueIndex, int startIndex){

        if( priority == 1 ){
           resultValue = this.assessFirstPriority(resultValue, currentValue, valueIndex);
        }
        else if ( priority == 2 ){
           resultValue = this.assessSecondPriority(resultValue, currentValue, valueIndex, startIndex);
        }
        else if ( priority == 3 ){
           resultValue = this.assessThirdPriority(resultValue, currentValue, valueIndex, startIndex);
        }

        return resultValue;

    }

    private Double assessFirstPriority(Double resultValue, Double currentValue, int valueIndex){

        if( valueIndex != 0 ) {
            if (expressionList.get(valueIndex - 1).equals("+"))
                resultValue += currentValue;
            else if (expressionList.get(valueIndex - 1).equals("-"))
                resultValue -= currentValue;
        }
        else
            resultValue = currentValue;

        return resultValue;


    }

    private Double assessSecondPriority(Double resultValue, Double currentValue, int valueIndex, int startIndex){

        if( valueIndex != startIndex ) {
            if (expressionList.get(valueIndex - 1).equals("*"))
                resultValue *= currentValue;
            else if (expressionList.get(valueIndex - 1).equals("/"))
                resultValue /= currentValue;
        }
        else
            resultValue = currentValue;

        return resultValue;


    }

    private Double assessThirdPriority(Double resultValue, Double currentValue, int valueIndex, int startIndex){

        if( valueIndex != startIndex )
          resultValue = Math.pow(resultValue, currentValue);
        else
          resultValue = currentValue;


        return resultValue;

    }

    private Double assessSqrt(String sqrtSequence){

        Double resultValue = 1.0;
        String numericalString = "";

        int sqrtAmountOperation = 0;


        for(int i = sqrtSequence.length() - 1; i >= 0; i--){

            if( (sqrtSequence.charAt(i) <= '9' && sqrtSequence.charAt(i) >= '0') || sqrtSequence.charAt(i) == '.' ){

                String tmpNumericalString = "";
                tmpNumericalString += sqrtSequence.charAt(i);

                tmpNumericalString += numericalString;

                numericalString = tmpNumericalString;

            }
            else{
                resultValue = Double.parseDouble(numericalString);
                System.out.println("sqrt result value " + resultValue);
                sqrtAmountOperation = (i+1)/5;
                break;
            }

        }

        for(int i = 0; i < sqrtAmountOperation; i++)
           resultValue = Math.sqrt(resultValue);

        return resultValue;

    }








}
