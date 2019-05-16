import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class JavaFinal extends Application{
	
//	Fields for primary stage
	private Scene scene;
	
//	Fields for borderPane
	private BorderPane borderPane;

//	Fields for menu components
	private MenuBar menuBar;
	private Menu addMenu;
	private Menu searchMenu;
	private Menu gradeMenu;
	private Menu reportMenu;
	private MenuItem addStudentItem;
	private MenuItem addCourseItem;
	private MenuItem addEnrollmentItem;
	private MenuItem searchStudentItem;
	private MenuItem searchCourseItem;
	private MenuItem searchEnrollmentItem;
	private MenuItem gradeItem;
	private MenuItem reportItem;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
//		Create menu barS
		menuBar = new MenuBar();
		
//		Create Add Menu
		addMenu = new Menu("_Add");
		addStudentItem = new MenuItem("Student");
		addCourseItem = new MenuItem("Course");
		addEnrollmentItem = new MenuItem("Enrollment");
		addMenu.getItems().addAll(addStudentItem, addCourseItem, addEnrollmentItem);
		
//		Create Search Menu
		searchMenu = new Menu("_Search");
		searchStudentItem = new MenuItem("Student");
		searchCourseItem = new MenuItem("Course");
		searchEnrollmentItem = new MenuItem("Enrollment");
		searchMenu.getItems().addAll(searchStudentItem, searchCourseItem, searchEnrollmentItem);
		
//		Create Grade Menu
		gradeMenu = new Menu("_Grade Management");
		gradeItem = new MenuItem("Manage Grades");
		gradeMenu.getItems().add(gradeItem);
		
//		Create Report Menu
		reportMenu = new Menu("Report");
		reportItem = new MenuItem("Report");
		reportMenu.getItems().add(reportItem);
		
//		Add menus to menu bar
		menuBar.getMenus().addAll(addMenu, searchMenu, gradeMenu, reportMenu);
		
//		Add menu to BorderPane
		borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		
//		Create Scene
		scene = new Scene(borderPane, 300, 200);
		
		
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Student Management System");
		
		primaryStage.show();
	}
	
	class ButtonClickHandler implements EventHandler<ActionEvent>
	     {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
			}
	}

}
