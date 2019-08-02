package unsw.dungeon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonFullApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");

        // DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("advanced_more.json");
        // DungeonController controller = dungeonLoader.loadController();
        
        // In this case, there is no loader needed
        // The controller will load the dungeon as needed
        // Check Game Of Life controller as reference
        List<String> dungeons = new ArrayList<>();
        
        File folder = new File("dungeons");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                dungeons.add(file.getName());
            }
        }
        
        
        DungeonFullController controller = new DungeonFullController(dungeons);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        controller.setStage(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
