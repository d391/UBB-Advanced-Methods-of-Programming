package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.statements.IStatement;

import java.util.HashMap;
import java.util.LinkedList;

public class SelectWindow {
    private LinkedList<IStatement> items;

    @FXML
    private ListView<String> prgList;
    @FXML
    private Button selectButton;

    @FXML
    public void initialize()
    {
        items = Interpreter.getStatements();
        LinkedList<String> stringStmts = new LinkedList<>();
        for (IStatement item : items) {
            stringStmts.add(item.toString());
        }
        prgList.setItems(FXCollections.observableArrayList(stringStmts));
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public IStatement getSelectedPrg(){
        if(prgList.getSelectionModel().getSelectedItem() == null)
            return null;
        int index = prgList.getSelectionModel().getSelectedIndex();
        return items.get(index);
    }

}
