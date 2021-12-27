package com.example.test_task;


import com.example.test_task.business.HistoryObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

public class ExpressionsHistoryGuiController {

    private MainGuiController mainGuiController;

    private LinkedList<HistoryObject> operationsHistoryLinkedList;

    @FXML
    public TextArea expressionsHistoryTextArea;

    @FXML
    public Button saveToDatabaseButton;

    public TextArea databaseMessageTextArea;

    public void setMainGuiController(MainGuiController mainGuiController){

        this.mainGuiController = mainGuiController;

        this.setOperationsHistoryLinkedList(this.mainGuiController.getHistory());

    }

    private void setOperationsHistoryLinkedList(LinkedList<HistoryObject> operationsHistoryLinkedList){

        this.operationsHistoryLinkedList = operationsHistoryLinkedList;

        Iterator<HistoryObject> iteratorLinkedList = this.operationsHistoryLinkedList.descendingIterator();

        while (iteratorLinkedList.hasNext()){
            this.expressionsHistoryTextArea.appendText(iteratorLinkedList.next() + "\n");
        }



    }

    public void saveToDatabase() throws SQLException {

        if( this.mainGuiController.save() )
            this.databaseMessageTextArea.appendText("current data is stored in database successfully");
        else
            this.databaseMessageTextArea.appendText("current data is not stored in database");

    }


}
