/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aviara.controller;

import com.aviara.bean.Deduction;
import com.aviara.bean.EmpAllowance;
import static com.aviara.controller.Emp_deductionController.eid;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author comp2
 */
public class EmpAllowanceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Query query = new Query();
    static String eid;
    private ObservableList data;
    @FXML
    private ScrollPane deduct;
    @FXML
    private AnchorPane employee;
    @FXML
    private ComboBox d_id;
    @FXML
    private Label e_id;
    @FXML
    private Button edit;
    @FXML
    private Button save;
    @FXML
    private Button back_btn;
    @FXML
    private Button next;
    @FXML
    private Label close_btn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buildData();
        
        if(AllEmployeeController.flag)
        {
            eid=AllEmployeeController.emp_id;
            e_id.setText(eid);
            save.setVisible(false);
            back_btn.setVisible(false);
            next.setVisible(false);
            edit.setVisible(true);
        }
        else
        {
            e_id.setText(Emp_deductionController.eid);
            save.setVisible(true);
            back_btn.setVisible(true);
            next.setVisible(true);
            edit.setVisible(false);
        }
    }    
    private void buildData()
    {
        try{
            Pane p=new Pane();
        //System.out.println(qtable.getColumns());
        List<Map> q=query.getAllowances();
         data=FXCollections.observableArrayList();
          int count=1,j=0,a=0;
          String [] ids=new String[100];
          String [] ids1=new String[100];
          String[] name=new String[100];;
          String[] percentage=new String[100];
        for (int i = 0; i < q.size(); i++) 
        {
             count++;j++;
             ids[i]=q.get(i).get("allowance_id").toString();
             name[i]=q.get(i).get("allow_name").toString();
             percentage[i]=q.get(i).get("value").toString();
            
        }
        List<Map> q1=query.getAllowancesByEmpId(AllEmployeeController.emp_id);
             for (int i = 0; i < q1.size(); i++) 
             {
              a++;   
              ids1[i]=q1.get(i).get("allowance_id").toString();
                       
            }
        int x=30,y=20;
        for(int i=0;i<j;i++)
        {
            
            CheckBox check=new CheckBox();
            check.setLayoutX(x);
            check.setLayoutY(y);
            check.setId(ids[i]);
            String temp=ids[i];
            check.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    //label.setText("Accepted");
                     if(check.isSelected())
                     d_id.getItems().add(temp);
                    else
                     d_id.getItems().remove(temp);  
                    
                }
            });
            Label n=new Label(name[i]);
            n.setLayoutX(check.getLayoutX()+20);
            n.setLayoutY(y);
            Label per=new Label(percentage[i]+" %");
            per.setLayoutX(n.getLayoutX()+n.getPrefWidth()+200);
            per.setLayoutY(y);
            p.getChildren().addAll(check,n,per);
            y=y+30;
            for(int k=0;k<a;k++)
            {
                if(temp.equals(ids1[k]))
                {
                    check.setSelected(true);
                    d_id.getItems().add(temp);
                    break;
                }
                else
                {
                    check.setSelected(false);
                }
            }
            
        }
        
        deduct.setContent(p);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
            
        }
    @FXML
    private void saveAllowance(ActionEvent evt)
    {
        
              save();
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Message");
              alert.setContentText("Done!!");
              alert.showAndWait(); 
              close(evt);
              openLeaveForm(evt);
         
    }
    @FXML
    public void openLeaveForm(ActionEvent e) 
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/view/EmpLeaves.fxml"));
            AnchorPane root1 = (AnchorPane) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Admin");
            Scene scene=new Scene(root1);
            stage.setScene(scene);  
            Pane main_pane = (Pane)employee.getParent();
            //((Label)root1.lookup("#e_id")).setText(eid);
            root1.setLayoutX((main_pane.getPrefWidth() - root1.getPrefWidth()) / 2);
            root1.setLayoutY((main_pane.getPrefHeight() - root1.getPrefHeight()) / 2);
            main_pane.getChildren().removeAll(main_pane.getChildren());
            main_pane.getChildren().add(root1);
           
           
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    @FXML
    private void close(ActionEvent evt)
    {
        employee.setVisible(false);
    }
    @FXML
    public void openDeduction(ActionEvent e) 
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/view/Emp_Deduction.fxml"));
            AnchorPane root1 = (AnchorPane) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Admin");
            Scene scene=new Scene(root1);
            stage.setScene(scene);  
            Pane main_pane = (Pane)employee.getParent();
            //((Label)root1.lookup("#e_id")).setText(eid);
            root1.setLayoutX((main_pane.getPrefWidth() - root1.getPrefWidth()) / 2);
            root1.setLayoutY((main_pane.getPrefHeight() - root1.getPrefHeight()) / 2);
            main_pane.getChildren().removeAll(main_pane.getChildren());
            main_pane.getChildren().add(root1);
           
           
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void save()
    {
        for(int i=0;i<d_id.getItems().size();i++)
        {
            EmpAllowance dbean=new EmpAllowance();
            dbean.setEmp_id(e_id.getText());
            dbean.setAllowance_id(Integer.parseInt(d_id.getItems().get(i).toString()));
            query.saveAllowance(dbean);
        }
    }
    @FXML
    private void edit(ActionEvent evt)
   {
       query.deleteAllowances(eid);
       save();
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Message");
       alert.setContentText("Allowance updated!!");
       alert.showAndWait(); 
       query.openAllEmployee(employee);
       
   }
    @FXML
    private void handleClose(MouseEvent evt)
    {
        if(AllEmployeeController.flag)
        {
            query.openAllEmployee(employee);
        }
        else
        {
           query.close(employee);
        }
    }
    @FXML
    private void rotate(MouseEvent event)  {
          //close_btn.setRotate(90);
       query.rotate(close_btn);
        //System.out.println("rotate...");
    }
}
