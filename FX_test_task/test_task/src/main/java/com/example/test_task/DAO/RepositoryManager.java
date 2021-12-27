package com.example.test_task.DAO;


import com.example.test_task.business.HistoryObject;

import java.sql.*;
import java.util.LinkedList;


public class RepositoryManager {

    String url = "jdbc:mysql://localhost:3306/assessments";
    String userName = "";//insert your username for testing
    String password = "";//insert your password for testing

    private  Connection con;

    public RepositoryManager(){

        try{
            this.con = DriverManager.getConnection(url, userName, password);
        }
        catch(SQLException e){
           e.printStackTrace();
        }

    }

    private int insertData(int id, String expression, String result) throws SQLException {

        String insertSql = "INSERT INTO assessments_table(id, expression, result) " + "VALUES(?,?,?)";

        PreparedStatement insertData = this.con.prepareStatement(insertSql);

        insertData.setInt(1, id);
        insertData.setString(2, expression);
        insertData.setString(3, result);

        int response = insertData.executeUpdate();

        return response;

    }

    private int getCurrentId() throws SQLException {

        int currentIdCounter = 0;

        String selectCountSql = "SELECT COUNT(*) FROM assessments_table";

        Statement st = this.con.createStatement();

        ResultSet rs = st.executeQuery(selectCountSql);

        while(rs.next()){
            currentIdCounter = rs.getInt(1);
        }

        return currentIdCounter;
    }


    public boolean storeData(LinkedList<HistoryObject> operationsHistoryLinkedList) throws SQLException {

        boolean dataIsStored = true;

        int idCounter = this.getCurrentId();

        for(HistoryObject historyObject: operationsHistoryLinkedList){

            int response = this.insertData(idCounter++, historyObject.getExpressionString(), historyObject.getResultString());

            if( response < 1 )
                dataIsStored = false;

        }

        return dataIsStored;

    }

    public void closeConnection() throws SQLException {
        this.con.close();
    }

}
