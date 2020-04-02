package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.statements.IStatement;
import view.ProgramWindow;
import view.SelectWindow;

public class Main extends Application{

    private Stage window;
    private Scene selectionScene, programScene;
    private SelectWindow selectionController;
    private ProgramWindow programController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        //Layout 1
        FXMLLoader selectLoader = new FXMLLoader(getClass().getResource("SelectWindow.fxml"));
        selectionScene = new Scene(selectLoader.load(), 600, 700);
        selectionController = selectLoader.getController();
        selectionController.getSelectButton().setOnAction(e -> switchToPrg());

        //Layout 2
        FXMLLoader prgLoader = new FXMLLoader(getClass().getResource("ProgramWindow.fxml"));
        programScene = new Scene(prgLoader.load(), 900, 476);
        programController = prgLoader.getController();

        //---------
        window.setTitle("Toy Language GUI");
        window.setScene(selectionScene);
        window.show();
    }

    private void switchToPrg(){
        IStatement selectedCtrl= selectionController.getSelectedPrg();
        programController.initialize(selectedCtrl);

        window.hide();
        window.setScene(programScene);
        window.centerOnScreen();
        window.show();
    }

    private void switchToSelect(){
        //programController.shutDown();
        //selectionController.removeSelectedCtrl();

        window.hide();
        window.setScene(selectionScene);
        window.centerOnScreen();
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
