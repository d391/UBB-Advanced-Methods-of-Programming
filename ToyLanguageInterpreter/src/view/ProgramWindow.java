package view;

import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import model.MyException;
import model.PrgState;
import model.adt.*;
import model.statements.IStatement;
import model.value.StringValue;
import model.value.Value;
import repository.IRepo;
import repository.Repo;

import java.io.IOException;
import java.util.*;

public class ProgramWindow {
    private Controller ctrl;
    private List<PrgState> prgList;
    private int currSelection = 0;

    @FXML
    public TableView<HashMap.Entry<String, Value>> symTableView;
    @FXML
    public TableView<HashMap.Entry<Integer, Value>> heapTableView;
    @FXML
    public ListView<StringValue> fileListView;
    @FXML
    public ListView<Value> outListView;
    @FXML
    public ListView<IStatement> exeListView;
    @FXML
    public Button oneStepButton;
    @FXML
    public Button returnButton;
    @FXML
    public TextField prgCount;
    @FXML
    public ComboBox<Integer> idList;

    Button getReturnButton(){
        return this.returnButton;
    }

    void initialize(IStatement stmt){
        PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable<>(), stmt, new Heap<>());
        IRepo repo = new Repo(prgState, "logFile.txt");
        ctrl = new Controller(repo);

        idList.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldInt, Integer newInt) {
                if(newInt == null || oldInt == null || oldInt.equals(newInt))
                    return;

                currSelection = newInt;
                selectNewProgram();
            }
        });

        initSymTable();
        initHeapTable();
        currSelection = 1;
        update();
    }

    private void initSymTable(){
        //Symbol table
        TableColumn<HashMap.Entry<String, Value>, String> varColumn = new TableColumn<>("Variable name");
        varColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, Value>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Value>, String> key) {
                return new SimpleStringProperty(key.getValue().getKey());
            }
        });

        TableColumn<HashMap.Entry<String, Value>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, Value>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Value>, String> value) {
                return new SimpleStringProperty(value.getValue().getValue().toString());
            }
        });

        symTableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        varColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 40 );
        valueColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 60 );

        symTableView.getColumns().add(varColumn);
        symTableView.getColumns().add(valueColumn);
    }

    private void initHeapTable(){
        //Symbol table
        TableColumn<HashMap.Entry<Integer, Value>, String> addrColumn = new TableColumn<>("Address");
        addrColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap.Entry<Integer, Value>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Value>, String> key) {
                return new SimpleStringProperty(Integer.toString(key.getValue().getKey()));
            }
        });

        TableColumn<HashMap.Entry<Integer, Value>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap.Entry<Integer, Value>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Value>, String> value) {
                return new SimpleStringProperty(value.getValue().getValue().toString());
            }
        });

        heapTableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        addrColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 40 );
        valueColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 60 );

        heapTableView.getColumns().add(addrColumn);
        heapTableView.getColumns().add(valueColumn);
    }

    private void updateCount(){
        prgCount.setText(Integer.toString(prgList.size()));
    }

    private void updateFileView(){
        fileListView.getItems().clear();
        fileListView.getItems().addAll(prgList.get(0).getFileList());
    }

    private void updateOutView(){
        outListView.getItems().clear();
        outListView.getItems().addAll(prgList.get(0).getOut().getList());
    }

    private void updateHeapView(){
        if(currSelection == 0)
            return;

        heapTableView.getItems().clear();
        for(PrgState p : prgList){
            if(p.getId() == currSelection){
                ObservableList<Map.Entry<Integer, Value>> items = FXCollections.observableArrayList(p.getHeap().getHeap().entrySet());
                heapTableView.setItems(items);
            }
        }
    }

    private void updateExeView(){
        exeListView.getItems().clear();
        if(currSelection == 0)
            return;

        for(PrgState p : prgList){
            if(p.getId() == currSelection){
                MyIStack<IStatement> stack = p.getExeStack();
                for(int i = stack.size()-1; i>=0; i--){
                    exeListView.getItems().add(stack.getStack().get(i));
                }
            }
        }
    }

    private void updateSymView(){
        if(currSelection == 0)
            return;

        symTableView.getItems().clear();
        for(PrgState p : prgList){
            if(p.getId() == currSelection){
                ObservableList<Map.Entry<String, Value>> items = FXCollections.observableArrayList(p.getSymTable().getDictionary().entrySet());
                symTableView.setItems(items);
            }
        }
    }

    private void updateIdList(){
        idList.getItems().clear();
        for(PrgState p : prgList){
            idList.getItems().add(p.getId());
        }

        if(idList.getItems().contains(currSelection)){
            idList.setValue(currSelection);
        }
        else{
            if(idList.getItems().isEmpty()){
                currSelection = 0;
            }
            else{
                currSelection = idList.getItems().get(0);
                idList.setValue(currSelection);
            }
        }
    }

    private void update(){
        prgList = ctrl.getPrgList();
        if(prgList.size() == 0){
            updateCount();
            prgList = new ArrayList<PrgState>();
            prgList.add(ctrl.getCompletedPrg());
            exeListView.getItems().clear();
            symTableView.getItems().clear();
            idList.getItems().clear();
            updateFileView();
            updateHeapView();
            updateOutView();
            //switchButtons(true);
            return;
        }

        updateIdList();
        updateCount();
        updateExeView();
        updateFileView();
        updateHeapView();
        updateOutView();
        updateSymView();
    }

    public void runOneStep() throws InterruptedException, IOException, MyException {
        if(ctrl.isDone())
            oneStepButton.setDisable(true);
        else
        {
            update();
            ctrl.oneStep();
            update();
        }

    }

    private void selectNewProgram() {
        updateExeView();
        updateSymView();
    }
}
