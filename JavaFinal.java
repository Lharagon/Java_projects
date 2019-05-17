import java.io.IOException;
import java.util.ArrayList;

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
	
	private ArrayList<Course> courseList = new ArrayList<Course>();
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private ArrayList<Enrollment> enrollmentList = new ArrayList<Enrollment>();
	
//	create BinaryFiles object to handle binary files
	private BinaryFiles binaryFiles;
	
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
	public void start(Stage primaryStage) throws IOException
	{
		binaryFiles = new BinaryFiles();
		
//		Load students, courses, and enrollments from files
		loadStudents(studentList, binaryFiles);
		loadCourses(courseList, binaryFiles);
		loadEnrollments(enrollmentList, binaryFiles);
		
//		Create menu barS
		menuBar = new MenuBar();
		
//		Create Add Menu
		addMenu = new Menu("_Add");
		addStudentItem = new MenuItem("Student");
		addCourseItem = new MenuItem("Course");
		addEnrollmentItem = new MenuItem("Enrollment");
		addMenu.getItems().addAll(addStudentItem, addCourseItem, addEnrollmentItem);
		
//		Add Menu Items on Actions
		addStudentItem.setOnAction(event -> {
			borderPane.setCenter(getAddDisplay("Student"));
		});
		
		addCourseItem.setOnAction(event -> {
			borderPane.setCenter(getAddDisplay("Course"));
		});
		
		addEnrollmentItem.setOnAction(event -> {
			borderPane.setCenter(getAddDisplay("Enrollment"));
		});
		
//		Create Search Menu
		searchMenu = new Menu("_Search");
		searchStudentItem = new MenuItem("Student");
		searchCourseItem = new MenuItem("Course");
		searchEnrollmentItem = new MenuItem("Enrollment");
		searchMenu.getItems().addAll(searchStudentItem, searchCourseItem, searchEnrollmentItem);
		
//		Search Menu Items on Actions
		searchStudentItem.setOnAction(event -> {
			borderPane.setCenter(getSearchDisplay("Student"));
		});
		
		searchCourseItem.setOnAction(event -> {
			borderPane.setCenter(getSearchDisplay("Course"));
		});
		
		searchEnrollmentItem.setOnAction(event -> {
			borderPane.setCenter(getSearchDisplay("Enrollment"));
		});
		
//		Create Grade Menu
		gradeMenu = new Menu("_Grade Management");
		gradeItem = new MenuItem("Manage Grades");
		gradeMenu.getItems().add(gradeItem);
		
		gradeItem.setOnAction(new CancelHandler());
		
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
		scene = new Scene(borderPane, 800, 500);
		
//		myLabel = new Label("Click the button to see a message");
//		Button myButton = new Button("Click Me");
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Student Management System");
		
		primaryStage.show();
	}
	
//	CLEARS CENTER OF BORDERPANE
	class CancelHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new HBox());
			}
	}
	
	public VBox getAddDisplay(String option) {
		Label message;
		VBox vBox;
		
		if(option.contentEquals("Student")) {
			vBox = addStudentView(studentList);
			vBox.setAlignment(Pos.CENTER);
			return vBox;
		} else if(option.contentEquals("Course")) {
			message = new Label("You clicked add COURSE");
		} else {
			message = new Label("You clicked add ENROLLMENT");
		}

		vBox = new VBox(20, message);
		vBox.setAlignment(Pos.CENTER);
		
		return vBox;
	}
	
//	SEARCH BY STUDENT, COURSE, ENROLLMENT BY ID
	public VBox getSearchDisplay(String option) {
		Label errorMessage = new Label("Not a valid " + option + " ID.");
		
		Label message = new Label("Please enter the " + option + " ID: ");
		TextField idTx = new TextField();
		HBox firstHb = new HBox(message, idTx);
		
		Button searchButton = new Button(option + " Search");
		HBox buttonHb = new HBox(searchButton);
		
		
		searchButton.setOnAction(event -> {
			int num = toValidInt(idTx.getText());
			boolean wasValid = false;
			Label item = null;
			HBox itemHb;
			
			switch(option)
			{
			case "Student":
				if(isValidStudent(studentList, num)) {
					item = new Label(studentList.get(num - 1).toString());
					wasValid = true;
				}
				break;
			case "Course":
				if(isValidCourse(courseList, num)) {
					item = new Label(courseList.get(num - 1).toString());
					wasValid = true;
				} 
				break;
			case "Enrollment":
				if(isValidEnrollment(enrollmentList, num)) {
					item = new Label(enrollmentList.get(num - 1).toString());
					wasValid = true;
				}
				break;
			default:
				System.out.print("Not a valid option, what happened?");
			}
			
			if(!wasValid) {
				item = new Label("Not a valid option, what happened?");
			}
			
			itemHb = new HBox(item);
			itemHb.setAlignment(Pos.CENTER);
			borderPane.setCenter(itemHb);
			
		});

		VBox vBox = new VBox(20, firstHb, buttonHb);
		vBox.setAlignment(Pos.CENTER);
		
		return vBox;
	}
	
//	VIEW FOR ADDING A STUDENT
	public VBox addStudentView(ArrayList<Student> studentList) {
		Label firstLb, lastLb, addressLb, cityLb, stateLb;
		TextField firstTx, lastTx, addressTx, cityTx, stateTx;
		HBox firstBx, lastBx, addressBx, cityBx, stateBx, buttonBx;
		VBox view;
		Button submitButton, cancelButton;
		
		firstLb = new Label("First Name: ");
		firstTx = new TextField();
		firstBx = new HBox(firstLb, firstTx);
		
		lastLb = new Label("Last Name: ");
		lastTx = new TextField();
		lastBx = new HBox(lastLb, lastTx);

		addressLb = new Label("Address: ");
		addressTx = new TextField();
		addressBx = new HBox(addressLb, addressTx);
		
		cityLb = new Label("City: ");
		cityTx = new TextField();
		cityBx = new HBox(cityLb, cityTx);
		
		stateLb = new Label("State: ");
		stateTx = new TextField();
		stateBx = new HBox(stateLb, stateTx);
		
		submitButton = new Button("Create Student");
		submitButton.setOnAction(event -> {
			String firstIn, lastIn, addressIn, cityIn, stateIn;
			
			firstIn = firstTx.getText();
			lastIn = lastTx.getText();
			addressIn = addressTx.getText();
			cityIn = cityTx.getText();
			stateIn = stateTx.getText();
			
			Student newStudent = new Student(firstIn, lastIn, addressIn, cityIn, stateIn);
			studentList.add(newStudent);
			try {
				binaryFiles.writeToFile(newStudent, true);
				System.out.println("In the try binaryFile should have saved.");
				borderPane.setCenter(new HBox());
			} catch (IOException e) {
				System.out.println("Exception: " + e);
				e.printStackTrace();
			}
			
		});
		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new CancelHandler());
		buttonBx = new HBox(submitButton, cancelButton);
		
		
		view = new VBox(20, firstBx, lastBx, addressBx, cityBx, stateBx, buttonBx);
		
		return view;
		
	}
	
	////////////////////////
		
	//	HELPER FUNCTIONS  //
		
	///////////////////////
	
//	returns an int either by parsing or -1 if parsing was not possible
	public static int toValidInt(String aString) {
		try {
			return Integer.parseInt(aString);
		} catch (Exception e) {
			return -1;
		}
	}
	
//	Checks if studentID is valid
	public static boolean isValidStudent (ArrayList<Student> studentList, int studentID) {
		
		for(int i = 0; i < studentList.size(); i++) {
			if(studentList.get(i).getIdNumber() == studentID) {
				return true;
			}
		}
		return false;
	}
	
//	checks if courseID is valid
	public static boolean isValidCourse (ArrayList<Course> courseList, int courseID) {
		for(int i = 0; i < courseList.size(); i++) {
			if(courseList.get(i).getCourseID() == courseID) {
				return true;
			}
		}
		return false;
	}
	
//	checks if enrollmentID is valid
	public static boolean isValidEnrollment(ArrayList<Enrollment> enrollList, int enrollID) {
		for(int i = 0; i < enrollList.size(); i++) {
			if(enrollList.get(i).getEnrollmentID() == enrollID) {
				return true;
			}
		}
		return false;
	}
	
//	Reads student records from binary studentsFile
	public static void loadStudents(ArrayList<Student> studentList, BinaryFiles theFiles) throws IOException {
		long number = theFiles.getNumberOfStudents();
		for(int i = 0; i < number; i++) {
			Student aStudent = theFiles.readStudentFromFile();
			studentList.add(aStudent);
		}
	}
	
//	Reads enrollment records from binary enrollmentFile
	private static void loadEnrollments(ArrayList<Enrollment> enrollmentList, BinaryFiles theFiles) throws IOException {
		long number = theFiles.getNumberOfEnrollments();
		for(int i = 0; i < number; i++) {
			Enrollment anEnrollment = theFiles.readEnrollmentFromFile();
			enrollmentList.add(anEnrollment);
		}
		
	}

//	Reads course records from binary courseFil
	private static void loadCourses(ArrayList<Course> courseList, BinaryFiles theFiles) throws IOException {
		long number = theFiles.getNumberOfCourses();
		
		for(int i = 0; i < number; i++) {
			Course aCourse = theFiles.readCourseFromFile();
			courseList.add(aCourse);
		}
	}

}
