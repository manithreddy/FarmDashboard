import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) {
    
    // Create UI elements
    VBox root = new VBox(10);
    root.setPadding(new Insets(10));
    
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll("option 1", "option 2");
    
    Button selectButton = new Button("Select");
    Button deleteButton = new Button("Delete");
    
    TextArea textArea = new TextArea();
    
    // Add functionality
    selectButton.setOnAction(e -> {
      String selection = comboBox.getValue();
      textArea.appendText(selection + " selected\n"); 
    });
    
    deleteButton.setOnAction(e -> {
      String selection = comboBox.getValue();
      textArea.appendText(selection + " deleted\n");
    });
    
    // Layout
    VBox dropdownBox = new VBox(10, comboBox, selectButton, deleteButton);
    
    root.getChildren().addAll(dropdownBox, textArea);
    
    // Show stage
    Scene scene = new Scene(root, 300, 250);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}