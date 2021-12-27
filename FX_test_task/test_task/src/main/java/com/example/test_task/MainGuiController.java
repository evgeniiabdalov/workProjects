package com.example.test_task;

import com.example.test_task.business.AssessmentController;
import com.example.test_task.business.HistoryObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainGuiController {

    AssessmentController assessmentController = new AssessmentController();

    //-------------------------------------------------------------------------------------------

    @FXML
    private Button oneButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button sevenButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button nineButton;

    @FXML
    private Button zeroButton;

    @FXML
    private Button sumButton;

    @FXML
    private Button subtractButton;

    @FXML
    private Button multiplyButton;

    @FXML
    private Button divideButton;

    @FXML
    private Button expButton;

    @FXML
    private Button sqrtButton;

    @FXML
    private Button pointButton;

    @FXML
    private Button getResultButton;

    @FXML
    private TextArea evaluationTextArea;

    @FXML
    private Button showHistoryButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button removeAllButton;


    //-------------------------------------------------------------------------------------------

     private List<String> expressionList = new ArrayList<>();

     private String expressionString = "";
     private String numericalString = "";

     private boolean pointPresent = false;

     private boolean isOperation = false;
     private boolean isNumber = true;
     private boolean isSqrt = true;
     private boolean newExpression = true;

    //-------------------------------------------------------------------------------------------

    @FXML
    public void setValueString(ActionEvent event){

        if( this.newExpression ) {
            this.evaluationTextArea.clear();
            this.newExpression = false;
        }


        if( this.isNumber ) {

            this.isOperation = true;

            Node node = (Node) event.getSource();
            String valueString = (String) node.getUserData();


            if (valueString.equals(".")) {
                if (!this.pointPresent)
                    this.pointPresent = true;
                else
                    return;
            }


            this.numericalString += valueString;
            this.isSqrt = false;


            this.evaluationTextArea.appendText(valueString);


        }


    }

    @FXML
    public void setOperationString(ActionEvent event){

        if( this.isOperation ) {

            Node node = (Node) event.getSource();
            String operationString = (String) node.getUserData();


            if (!numericalString.equals(""))
                this.expressionList.add(numericalString);

            this.expressionList.add(operationString);

            evaluationTextArea.appendText(" " + operationString + " ");

            this.pointPresent = false;
            this.isOperation = false;
            this.isNumber = true;
            this.isSqrt = true;
            this.numericalString = "";



        }

    }

    @FXML
    public void setSqrtOperationString(ActionEvent event){

        if( this.newExpression ) {
            this.evaluationTextArea.clear();
            this.newExpression = false;
        }

        Node node = (Node) event.getSource();
        String operationSqrtString = (String) node.getUserData();


        if( this.isSqrt ){
            numericalString += operationSqrtString + " ";
            evaluationTextArea.appendText(operationSqrtString + " ");
        }

    }

    @FXML
    public void getAssesmentResult(){

        this.expressionList.add(numericalString);

        Double result = this.assessmentController.assess(this.expressionList);

        this.evaluationTextArea.appendText(" = " + result);

        //---------------------------------------------------------

        this.isOperation = false;
        this.isNumber = true;
        this.isSqrt = true;
        this.pointPresent = false;
        this.numericalString = "";

        this.expressionList.clear();
        this.newExpression = true;

    }

    @FXML
    public void showHistory() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpressionsHistoryGui.fxml"));

        Parent root = loader.load();

        Stage expressionsHistoryStage = new Stage();
        expressionsHistoryStage.setTitle("showing history");
        expressionsHistoryStage.setScene(new Scene(root));
        expressionsHistoryStage.show();

        ExpressionsHistoryGuiController expressionsHistoryGuiController = loader.getController();
        expressionsHistoryGuiController.setMainGuiController(this);

    }

    public LinkedList<HistoryObject> getHistory(){
        return this.assessmentController.getHistory();
    }

    @FXML
    public void removeLastPartExpression(){

        this.remove();

        this.resetEvaluationTextArea();

    }

    @FXML
    public void removeWholeExpression(){

        while( this.expressionList.size() > 0 || !numericalString.equals("") ){
            this.remove();
        }

        this.resetEvaluationTextArea();

        this.isOperation = false;
        this.pointPresent = false;

    }

    private void remove(){

        if( numericalString.equals("") ) {

            if( this.expressionList.size() == 0 )
                return;

            if( this.checkOperation( this.expressionList.get(this.expressionList.size() - 1) ) ) {
                this.isOperation = true;
                this.isNumber = false;
                this.isSqrt = false;
            }
            else {
                this.isOperation = false;
                this.isNumber = true;
                this.isSqrt = true;
            }

            this.expressionList.remove(this.expressionList.size() - 1);

        }
        else{
            this.numericalString = "";
            this.isOperation = false;
            this.isNumber = true;
            this.isSqrt = true;
        }

    }

    private void resetEvaluationTextArea(){

        this.evaluationTextArea.clear();

        for(int i = 0; i < this.expressionList.size(); i++){
            this.evaluationTextArea.appendText(this.expressionList.get(i) + " ");
        }

    }

    private boolean checkOperation(String expressionString){

        if( expressionString.equals("+") ){
            return true;
        }
        else if( expressionString.equals("-") ){
            return true;
        }
        else if( expressionString.equals("*") ){
            return true;
        }
        else if( expressionString.equals("/") ){
            return true;
        }
        else if( expressionString.equals("exp") ){
            return true;
        }

        return false;
    }


    public boolean save() throws SQLException {
        return this.assessmentController.storageData();
    }
}