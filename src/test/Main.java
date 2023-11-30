import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font; 
import javafx.stage.Stage;

public class Main extends Application {

  private BorderPane rootLayout;
  
  @Override
  public void start(Stage primaryStage) {

    rootLayout = new BorderPane();
    
    HBox dashboard = createDashboard();
    rootLayout.setCenter(dashboard);
    
    Scene scene = new Scene(rootLayout, 1200, 800);
    
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private HBox createDashboard() {
   
    HBox hbox = new HBox();
    hbox.setMinSize(1200, 800);

    // Left Panel 
    VBox leftPanel = new VBox();
    leftPanel.setMinSize(600, 800);
    leftPanel.setPadding(new Insets(10));

    TreeView<String> treeView = new TreeView<>();
    treeView.setMinSize(400, 500);

    ToolBar toolBar = new ToolBar(); 
    Button addItemBtn = new Button("Add Item");
    toolBar.getItems().addAll(addItemBtn);

    leftPanel.getChildren().addAll(treeView, toolBar); 

    // Right Panel
    TitledPane detailsPane = new TitledPane("Selection Details", createDetailsPanel());
    detailsPane.setMinHeight(550);

    hbox.getChildren().addAll(leftPanel, detailsPane);

    return hbox;
  }

  private VBox createDetailsPanel() {
    
    VBox vbox = new VBox();

    Label nameLabel = new Label("Name:");
    TextField name = new TextField(); 
    name.setPromptText("Name");
    name.setFont(new Font(12));

    vbox.getChildren().addAll(nameLabel, name); 

    return vbox;

  }

  public static void main(String[] args) {
    launch(args);
  }

}