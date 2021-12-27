package com.example.test_task.business;

import com.example.test_task.DAO.RepositoryManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AssessmentController {

    private LinkedList<HistoryObject> operationsHistoryLinkedList;

    public AssessmentController(){
         this.operationsHistoryLinkedList = new LinkedList<>();
    };

    public Double assess(List<String> expressionList){

        ExpressionProcessor expressionProcessor = new ExpressionProcessor(expressionList);

        Double resultValue = expressionProcessor.processExpression(1, 0, expressionList.size() - 1 );

        String expressionString = "";

        for(int i = 0; i < expressionList.size(); i++){

            if(i < expressionList.size() - 1)
              expressionString += expressionList.get(i) + " ";
            else
              expressionString += expressionList.get(i);

        }

        this.addHistory(expressionString, resultValue.toString());

        System.out.println("result " + resultValue);

        return resultValue;

    }

    private void addHistory(String expressionString, String resultString){

       if( this.operationsHistoryLinkedList.size() >= 10 ){
           this.operationsHistoryLinkedList.poll();
       }

       this.operationsHistoryLinkedList.add(new HistoryObject(expressionString, resultString));

    }

    public LinkedList<HistoryObject> getHistory(){
        return this.operationsHistoryLinkedList;
    }

    public boolean storageData() throws SQLException {

        RepositoryManager repositoryManager = new RepositoryManager();

        boolean responseStatus = repositoryManager.storeData(this.operationsHistoryLinkedList);

        repositoryManager.closeConnection();

        return responseStatus;

    }


}
