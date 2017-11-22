package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("wc.fxml"));
            AnchorPane Pane =  loader.load();
            primaryStage = new Stage();
            primaryStage.setTitle("client");
            Scene scene = new Scene(Pane);
            primaryStage.setResizable(false);
            WindowClientController controller = loader.getController();
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(we -> {
                System.exit(0);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
