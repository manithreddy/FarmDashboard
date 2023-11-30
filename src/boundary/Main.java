package boundary;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;

public class Main extends Application {

    private TreeView<String> farmTreeView;
    private TextArea infoLogTextArea;
    private AnimatedDrone animatedDrone;

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainLayout = new BorderPane();

        // Add your group name at the top center
        Label groupNameLabel = new Label("'Group 30' Team Members: Manith, Srujan, Arun, Manoj");
        groupNameLabel.setFont(new Font(18));
        BorderPane.setAlignment(groupNameLabel, Pos.TOP_CENTER);
        mainLayout.setTop(groupNameLabel);

        VBox centerPanel = createCenterPanel();
        mainLayout.setLeft(createLeftPanel());
        mainLayout.setRight(createDronePanel());
        mainLayout.setCenter(centerPanel);

        // Add a farm layout below the drone image
        HBox farmLayout = createFarmLayout();
        mainLayout.setBottom(farmLayout);

        // Call createFarmLayout here to populate the TreeView
        createFarmLayout();

        Scene scene = new Scene(mainLayout, 1200, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createCenterPanel() {
        VBox centerPanel = new VBox();
        centerPanel.setPadding(new Insets(8));

        TitledPane detailsPane = new TitledPane("Selection Details", createDetailsPanel());
        detailsPane.setPrefWidth(80);
        detailsPane.setMinHeight(550);

        centerPanel.getChildren().add(detailsPane);

        return centerPanel;
    }

    private VBox createLeftPanel() {
        VBox leftPanel = new VBox();
        leftPanel.setMinSize(200.0, 400.0);
        leftPanel.setPadding(new Insets(10));

        farmTreeView = new TreeView<>();
        farmTreeView.setId("farmTreeView");
        farmTreeView.setMinSize(125.0, 100.0);

        TitledPane farmTreeViewTitledPane = new TitledPane();
        farmTreeViewTitledPane.setText("Farm TreeView");
        farmTreeViewTitledPane.setContent(farmTreeView);

        ToolBar toolBar = new ToolBar();
        Button addItemBtn = new Button("Add Item");
        addItemBtn.setOnAction(e -> addItem());
        Button addItemContainerBtn = new Button("Add ItemContainer");
        addItemContainerBtn.setOnAction(e -> addItemContainer());
        Button deleteSelectionBtn = new Button("Delete Selection");
        deleteSelectionBtn.setOnAction(e -> deleteSelection());
        Button scanFarmBtn = new Button("Scan Farm");
        scanFarmBtn.setOnAction(e -> scanFarm());

        toolBar.getItems().addAll(addItemBtn, addItemContainerBtn, deleteSelectionBtn, scanFarmBtn);

        TitledPane infoLogTitledPane = createInfoLogTitledPane();

        leftPanel.getChildren().addAll(farmTreeViewTitledPane, toolBar, infoLogTitledPane);

        return leftPanel;
    }

    private TitledPane createInfoLogTitledPane() {
        TitledPane infoLogTitledPane = new TitledPane();
        infoLogTitledPane.setText("Info Log");
        infoLogTextArea = new TextArea();
        infoLogTextArea.setId("infoLog");
        infoLogTextArea.setEditable(false);
        infoLogTextArea.setMaxHeight(Double.MAX_VALUE);
        infoLogTitledPane.setContent(infoLogTextArea);
        VBox.setMargin(infoLogTitledPane, new Insets(5.0, 5.0, 5.0, 5.0));
        return infoLogTitledPane;
    }

    private VBox createDetailsPanel() {
        VBox vbox = new VBox();

        Label nameLabel = new Label("Name:");
        TextField name = new TextField();
        name.setPromptText("Name");
        name.setFont(new Font(12));

        Label locationXLabel = new Label("Location X (feet):");
        TextField locationX = new TextField();
        locationX.setPromptText("Location X");
        locationX.setFont(new Font(12));

        Label locationYLabel = new Label("Location Y (feet):");
        TextField locationY = new TextField();
        locationY.setPromptText("Location Y");
        locationY.setFont(new Font(12));

        Label lengthLabel = new Label("Length (feet):");
        TextField length = new TextField();
        length.setPromptText("Length");
        length.setFont(new Font(12));

        Label widthLabel = new Label("Width (feet):");
        TextField width = new TextField();
        width.setPromptText("Width");
        width.setFont(new Font(12));

        Label heightLabel = new Label("Height (feet):");
        TextField height = new TextField();
        height.setPromptText("Height");
        height.setFont(new Font(12));

        Label purchasePriceLabel = new Label("Purchase Price (dollars):");
        TextField purchasePrice = new TextField();
        purchasePrice.setPromptText("Purchase Price");
        purchasePrice.setFont(new Font(12));

        Label marketValueLabel = new Label("Market Value (dollars):");
        TextField marketValue = new TextField();
        marketValue.setPromptText("Market Value");
        marketValue.setFont(new Font(12));

        Label aggregatePurchasePriceLabel = new Label("Aggregate Purchase Price:");
        TextField aggregatePurchasePrice = new TextField();
        aggregatePurchasePrice.setPromptText("Aggregate Purchase Price");
        aggregatePurchasePrice.setFont(new Font(12));
        aggregatePurchasePrice.setEditable(false);

        Label aggregateMarketValueLabel = new Label("Aggregate Market Value:");
        TextField aggregateMarketValue = new TextField();
        aggregateMarketValue.setPromptText("Aggregate Market Value");
        aggregateMarketValue.setFont(new Font(12));
        aggregateMarketValue.setEditable(false);

        Button updateSelectionBtn = new Button("Update Selection");
        updateSelectionBtn.setOnAction(e -> updateSelection());

        Button deleteItemBtn = new Button("Delete Item");
        deleteItemBtn.setOnAction(e -> deleteItem());

        vbox.getChildren().addAll(
                nameLabel, name,
                locationXLabel, locationX,
                locationYLabel, locationY,
                lengthLabel, length,
                widthLabel, width,
                heightLabel, height,
                purchasePriceLabel, purchasePrice,
                marketValueLabel, marketValue,
                aggregatePurchasePriceLabel, aggregatePurchasePrice,
                aggregateMarketValueLabel, aggregateMarketValue,
                updateSelectionBtn, deleteItemBtn
        );

        nameLabel.setStyle("-fx-font-weight: bold;");

        return vbox;
    }

    private HBox createDronePanel() {
        HBox dronePanel = new HBox();
    
        animatedDrone = new AnimatedDrone("img/drone.png");
        dronePanel.getChildren().add(animatedDrone);
        dronePanel.setAlignment(Pos.TOP_RIGHT);
    
        return dronePanel;
    }
    

    private void updateSelection() {
        TreeItem<String> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            VBox detailsPanel = createDetailsPanel();
            showUpdateDialog(selectedItem.getValue(), detailsPanel);
        } else {
            addToInfoLog("No item or container selected.");
        }
    }

    private void updateProperties(String itemName, VBox content) {
        TreeItem<String> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            selectedItem.setValue(itemName);
            selectedItem.getChildren().clear();
            selectedItem.getChildren().add(new TreeItem<>("Type: Static Update"));
        } else {
            addToInfoLog("No item or container selected for update.");
        }
    }

    private void deleteItem() {
        TreeItem<String> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !selectedItem.isLeaf()) {
            farmTreeView.getRoot().getChildren().remove(selectedItem);
            addToInfoLog("Deleted item: " + selectedItem.getValue());
        } else {
            addToInfoLog("No item selected for deletion.");
        }
    }

    private void deleteSelection() {
        TreeItem<String> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.getParent() != null) {
                selectedItem.getParent().getChildren().remove(selectedItem);
                addToInfoLog("Deleted selection: " + selectedItem.getValue());
            } else {
                farmTreeView.setRoot(null);
                addToInfoLog("Deleted root selection: " + selectedItem.getValue());
            }
        } else {
            addToInfoLog("No selection to delete.");
        }
    }

    private void addItem() {
        TreeItem<String> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
        addItemToContainer(selectedItem, "New Item");
        addItemContainer();
    }

    private void addItemToContainer(TreeItem<String> parentItem, String itemName) {
        if (parentItem != null && !parentItem.isLeaf()) {
            TreeItem<String> newItem = new TreeItem<>(itemName);
            parentItem.getChildren().add(newItem);
            addToInfoLog("Added item: " + itemName + " to container: " + parentItem.getValue());
        } else {
            addToInfoLog("No container selected to add the item container.");
        }
    }

    private void addItemContainer() {
        TreeItem<String> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TreeItem<String> newContainer = new TreeItem<>("New Container");
            selectedItem.getChildren().add(newContainer);
            addToInfoLog("Added container: New Container to container: " + selectedItem.getValue());
        } else {
            addToInfoLog("No item or container selected.");
        }
    }

    private void addToInfoLog(String message) {
        infoLogTextArea.appendText(message + "\n");
    }

    private void showUpdateDialog(String itemName, VBox content) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update " + itemName);
        dialog.setHeaderText("Update " + itemName);

        dialog.getDialogPane().setContent(content);

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        Button updateButton = (Button) dialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.setDefaultButton(true);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                updateProperties(itemName, content);
            }
            return null;
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == updateButtonType) {
            updateProperties(itemName, content);
        }
    }

    private void scanFarm() {
        addToInfoLog("Scanning farm...");

        // Create a farm layout with actors (drone and farmer)
        createFarmLayout();

        // Perform the drone scanning animation
        animatedDrone.scanFarm();
    }

    private HBox createFarmLayout() {

  HBox farmLayout = new HBox();
  
  farmLayout.setAlignment(Pos.CENTER);
  farmLayout.setPadding(new Insets(10));
  farmLayout.setSpacing(20);

  // Crop Area 
  VBox cropAreaContainer = new VBox();
  
  BorderStroke borderStroke = new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2));
  
  cropAreaContainer.setBorder(new Border(borderStroke));
  cropAreaContainer.setEffect(new DropShadow());

  Pane cropAreaPane = createCropArea();
  cropAreaContainer.getChildren().add(cropAreaPane);

  // Cattle Area
  VBox cattleAreaContainer = new VBox();
  
  cattleAreaContainer.setBorder(new Border(borderStroke));
  cattleAreaContainer.setEffect(new DropShadow());

  Pane cattleAreaPane = createCattleArea(); 
  cattleAreaContainer.getChildren().add(cattleAreaPane);
	 populateFarmLayout();
  farmLayout.getChildren().addAll(cropAreaContainer, cattleAreaContainer);
  
  return farmLayout;
}

private Pane createCropArea() {

  Pane pane = new Pane();

  Rectangle cropRect = new Rectangle(300, 200);
  cropRect.setFill(Color.PALEGREEN);
  
  Label label = new Label("Crop Area");
  label.setFont(new Font(16));

  pane.getChildren().addAll(cropRect, label);
  
  return pane; 
}

private Pane createCattleArea() {
    Pane pane = new Pane();
  
    Rectangle cattleRect = new Rectangle(300, 200);
    cattleRect.setFill(Color.LIGHTSALMON);
  
    Label label = new Label("Cattle Area");
    label.setFont(new Font(16));
  
    pane.getChildren().addAll(cattleRect, label);
  
    return pane;
  }
  
     private void populateFarmLayout() {
        farmTreeView.setRoot(null);

        TreeItem<String> root = new TreeItem<>("Farm Layout");
        farmTreeView.setRoot(root);

        TreeItem<String> farmerItem = new TreeItem<>("Farmer");
        addActorToLayout(farmerItem, "Manith Reddy", 0, 0);

        TreeItem<String> droneItem = new TreeItem<>("Drone");
        addActorToLayout(droneItem, "Farm Drone", 100, 100);

        addToInfoLog("Farm layout created.");
    }

    private void addActorToLayout(TreeItem<String> actorItem, String actorName, double x, double y) {
        actorItem.getChildren().add(new TreeItem<>("Name: " + actorName));
        actorItem.getChildren().add(new TreeItem<>("Location: (" + x + ", " + y + ")"));

        Circle actorCircle = new Circle(10, Color.BLUE);
        actorItem.setGraphic(actorCircle);

        farmTreeView.getRoot().getChildren().add(actorItem);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class AnimatedDrone extends StackPane {

    private ImageView droneImageView;
    private SequentialTransition scanAnimation;

    public AnimatedDrone(String imagePath) {
        initializeDrone(imagePath);
        initializeScanAnimation();
    }

    public void scanFarm() {
        scanAnimation.play();
    }

    private void initializeDrone(String imagePath) {
        droneImageView = new ImageView();
        try {
            File file = new File(imagePath);
            Image droneImage = new Image(file.toURI().toString());
            droneImageView.setFitWidth(100);
            droneImageView.setFitHeight(100);
            droneImageView.setImage(droneImage);
        } catch (Exception e) {
            System.out.println("Failed to load the drone image.");
        }

        getChildren().add(droneImageView);
    }

    private void initializeScanAnimation() {
        KeyValue scaleValue1 = new KeyValue(droneImageView.scaleXProperty(), 1);
        KeyValue scaleValue2 = new KeyValue(droneImageView.scaleYProperty(), 1);
        KeyValue scaleValue3 = new KeyValue(droneImageView.scaleXProperty(), 1.2);
        KeyValue scaleValue4 = new KeyValue(droneImageView.scaleYProperty(), 1.2);
        KeyValue scaleValue5 = new KeyValue(droneImageView.scaleXProperty(), 1);
        KeyValue scaleValue6 = new KeyValue(droneImageView.scaleYProperty(), 1);

        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(0), scaleValue1);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(500), scaleValue2);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(1000), scaleValue3);
        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(1500), scaleValue4);
        KeyFrame keyFrame5 = new KeyFrame(Duration.millis(2000), scaleValue5);
        KeyFrame keyFrame6 = new KeyFrame(Duration.millis(2500), scaleValue6);

        Timeline timeline = new Timeline();
    timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4, keyFrame5, keyFrame6);

    SequentialTransition sequentialTransition = new SequentialTransition();
    sequentialTransition.getChildren().add(timeline);
    sequentialTransition.setCycleCount(Animation.INDEFINITE);
    sequentialTransition.setAutoReverse(true);

    scanAnimation = sequentialTransition;
}
}
