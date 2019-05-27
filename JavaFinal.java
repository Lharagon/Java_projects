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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
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
	
//	Field for tracking id of current object being modified
	private int masterID;

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
		
//		Create menu bars
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
		
		gradeItem.setOnAction(event -> {
			borderPane.setCenter(getGradeSearchDisplay());
		});
		
//		Create Report Menu
		reportMenu = new Menu("Report");
		reportItem = new MenuItem("Report");
		reportMenu.getItems().add(reportItem);
		
		reportItem.setOnAction(event -> {
			borderPane.setCenter(reportSearchView(courseList));
		});
		
//		Add menus to menu bar
		menuBar.getMenus().addAll(addMenu, searchMenu, gradeMenu, reportMenu);
		
//		Add menu to BorderPane
		borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		
//		Create Scene
		scene = new Scene(borderPane, 600, 500);
		
		scene.getStylesheets().add("styles.css");
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Student Management System");
		
		primaryStage.show();

		
	}
	
	////////////////////////
	//                    //
	//	HANDLER CLASSES   //
	//                    //
	////////////////////////
	
//	CLEARS CENTER OF BORDERPANE
	class CancelHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			borderPane.setCenter(new HBox());
		}
	}
	
//	HANDLES EDITING A STUDENT
	class EditStudentHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			VBox view = addEditStudentView(studentList, true, masterID);
			borderPane.setCenter(view);
		}
	}
	
//	HANDLES ADDING A STUDENT
	class AddStudentHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			VBox view = addEditStudentView(studentList, false, -1);
			borderPane.setCenter(view);
		}
	}
	
//	HANDLES EDITING A COURSE
	class EditCourseHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			VBox view = addEditCourseView(courseList, true, masterID);
			borderPane.setCenter(view);
		}
	}
	
//	HANDLES ADDING A COURSE
	class AddCourseHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			VBox view = addEditCourseView(courseList, false, -1);
			borderPane.setCenter(view);
		}
	}

	
//	HANDLES EDITING AN ENROLLMENT
	class EditEnrollmentHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Enrollment theEnrollment = getEnrollment(enrollmentList, masterID);
			HBox theStudentHb = studentDetails(getStudent(studentList, theEnrollment.getStudentID()));
			
			VBox studentVBox = new VBox(theStudentHb);
			studentVBox.setAlignment(Pos.CENTER);
			
			VBox enrollVBox = addEditEnrollmentView(enrollmentList, courseList, true, masterID, -1);
			enrollVBox.setAlignment(Pos.CENTER);
			HBox itemHb = new HBox(30, studentVBox, enrollVBox);
			
			itemHb.setAlignment(Pos.CENTER);
			borderPane.setCenter(itemHb);
			

		}
	}
	
//	HANDLES GET GRADE ENROLLMENT
	class SearchForGradeHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			borderPane.setCenter(getGradeSearchDisplay());
		}
	}
	
//	HANDLES ADDING AN ENROLLMENT
	class AddEnrollmentHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			VBox view = getStudentForEnrollment();
			borderPane.setCenter(view);
		}
	}

	
	////////////////////////
	//                    //
	//    DISPLAY VIEWS   //
	//                    //
	////////////////////////
	
//	Returns View according to menu item selected by user
	public VBox getAddDisplay(String option) {
		VBox vBox;
		
		if(option.contentEquals("Student")) {
			vBox = addEditStudentView(studentList, false, -1);
			vBox.setAlignment(Pos.CENTER);
			return vBox;
		} else if(option.contentEquals("Course")) {
			vBox = addEditCourseView(courseList, false, -1);
			vBox.setAlignment(Pos.CENTER);
			return vBox;
		} else {
			vBox = getStudentForEnrollment();
			vBox.setAlignment(Pos.CENTER);
		}
		
		return vBox;
	}
	
	public HBox studentDetails(Student aStudent) {

		Label idLb = new Label("Student ID:");
		idLb.getStyleClass().add("detail-label");
		Label firstLb = new Label("First Name:");
		firstLb.getStyleClass().add("detail-label");
		Label lastLb = new Label("Last Name:");
		lastLb.getStyleClass().add("detail-label");
		Label addressLb = new Label("Address:");
		addressLb.getStyleClass().add("detail-label");
		Label cityLb = new Label("City:");
		cityLb.getStyleClass().add("detail-label");
		Label stateLb = new Label("State:");
		stateLb.getStyleClass().add("detail-label");
		VBox labelVx = new VBox(15, idLb, firstLb, lastLb, addressLb, cityLb, stateLb);
		
		
		Label idVal = new Label(Integer.toString(aStudent.getIdNumber()));
		Label firstVal = new Label(aStudent.getFirstName());
		Label lastVal = new Label(aStudent.getLastName());
		Label addressVal = new Label(aStudent.getAddress());
		Label cityVal = new Label(aStudent.getCity());
		Label stateVal = new Label(aStudent.getState());
		stateVal.getStyleClass().add("detail-value");
		VBox ValueVx = new VBox(15, idVal, firstVal, lastVal, addressVal, cityVal, stateVal);
		ValueVx.getStyleClass().add("detail-value-VBox");
		
		
		return new HBox(15, labelVx, ValueVx);
	}
	
	public HBox courseDetails(Course aCourse) {
		Label courseIdLb = new Label("Course ID:");
		courseIdLb.getStyleClass().add("detail-label");
		Label courseNumLb = new Label("Course Number:");
		courseNumLb.getStyleClass().add("detail-label");
		Label courseNameLb = new Label("Course Name:");
		courseNameLb.getStyleClass().add("detail-label");
		Label courseInstLb = new Label("Course Instructor:");
		courseInstLb.getStyleClass().add("detail-label");
		Label courseDeptLb = new Label("Course Department:");
		courseDeptLb.getStyleClass().add("detail-label");
		VBox labelVx = new VBox(15, courseIdLb, courseNumLb, courseNameLb, courseInstLb, courseDeptLb);

		Label courseIdVal = new Label(Integer.toString(aCourse.getCourseID()));
		Label courseNumVal = new Label(aCourse.getCourseNumber());
		Label courseNameVal = new Label(aCourse.getCourseName());
		Label courseInstVal = new Label(aCourse.getInstructor());
		Label courseDeptVal = new Label(aCourse.getDepartment());
		VBox valueVx = new VBox(15, courseIdVal, courseNumVal, courseNameVal, courseInstVal, courseDeptVal);
		
		return new HBox(15, labelVx, valueVx);
	}
	
	public HBox enrollmentDetails(Enrollment anEnrollment, boolean withGrade) {
		VBox labelVx, valueVx;
		
		Label enrollIdLb = new Label("Enrollment ID:");
		enrollIdLb.getStyleClass().add("detail-label");
		Label courseIdLb = new Label("Course ID:");
		courseIdLb.getStyleClass().add("detail-label");
		Label studentIdLb = new Label("Student ID:");
		studentIdLb.getStyleClass().add("detail-label");
		Label enrollYearLb = new Label("Year:");
		enrollYearLb.getStyleClass().add("detail-label");
		Label enrollSemLb = new Label("Semester:");
		enrollSemLb.getStyleClass().add("detail-label");

		Label enrollIdVal = new Label(Integer.toString(anEnrollment.getEnrollmentID()));
		Label courseIdVal = new Label(Integer.toString(anEnrollment.getCourseID()));
		Label studentIdVal = new Label(Integer.toString(anEnrollment.getStudentID()));
		Label enrollYearVal = new Label(Integer.toString(anEnrollment.getYear()));
		Label enrollSemVal = new Label(anEnrollment.getSemester());

		if(withGrade) {
			Label enrollGradeLb = new Label("Grade:");
			Label enrollGradeVal = new Label(Character.toString(anEnrollment.getGrade()));
			labelVx = new VBox(15, enrollIdLb, courseIdLb, studentIdLb, enrollYearLb, enrollSemLb, enrollGradeLb);
			valueVx = new VBox(15, enrollIdVal, courseIdVal, studentIdVal, enrollYearVal, enrollSemVal, enrollGradeVal);
		} else {
			labelVx = new VBox(15, enrollIdLb, courseIdLb, studentIdLb, enrollYearLb, enrollSemLb);
			valueVx = new VBox(15, enrollIdVal, courseIdVal, studentIdVal, enrollYearVal, enrollSemVal);
		}
		
		return new HBox(15, labelVx, valueVx);
	}
	
	public VBox reportSearchView(ArrayList<Course> cList) {
		
		Label errorMessage = new Label("");
		errorMessage.getStyleClass().add("error-label");
		
		Label courseIdLb = new Label("Course:");
		Label yearLb = new Label("Year:");
		
		ComboBox<String> courseCb = new ComboBox<>();
		courseCb.getItems().addAll(getListOfCourses(cList));
		
		ComboBox<String> yearCb = new ComboBox<>();
		yearCb.getItems().addAll("2019", "2020", "2021", "2022");
		
		Button searchBtn = new Button("Search");
		
		VBox labelVb = new VBox(20, courseIdLb, yearLb);
		VBox valueVb = new VBox(15, courseCb, yearCb);
		
		searchBtn.setOnAction(event -> {
			String cValue = courseCb.getValue();
			String yValue = yearCb.getValue();
			System.out.println("this is c: " + cValue + " and y " + yValue);
			String[] checkArray = {cValue, yValue};
			
			if(everythingIsValid(checkArray)) {
				
				int courseIn = getCourseIdFromString(cValue);
				int yearIn = toValidInt(yValue);
				ListView<Enrollment> matchList = new ListView<>();
				matchList.getItems().addAll(matchingEnrollments(enrollmentList, courseIn, yearIn));
				Label reportTitle = new Label(String.format("Report For Course %s Year %s (*: grade unsigned)", courseCb.getValue(), yearIn));
				reportTitle.setAlignment(Pos.CENTER);
				Button againBtn = new Button("Search Again");
				againBtn.setAlignment(Pos.CENTER);
				
				againBtn.setOnAction(event2 -> {
					borderPane.setCenter(reportSearchView(cList));
				});
				
				VBox theVbox = new VBox(10, reportTitle, matchList, againBtn);
				theVbox.setAlignment(Pos.CENTER);
				
				borderPane.setCenter(theVbox);
			} else {
				errorMessage.setText("Please fill out all fields");
			}
			
		});
		
		HBox formHb = new HBox(15, labelVb, valueVb);
		formHb.setAlignment(Pos.CENTER);
		
		HBox btnHb = new HBox(searchBtn);
		
		VBox theV = new VBox(15, errorMessage, formHb, searchBtn, btnHb);
		theV.setAlignment(Pos.CENTER);
		
		return theV;
		
	}
	
	public VBox getSetGradeView(Enrollment enroll) {
		Label title = new Label("Set Enrollment Grade");
		title.getStyleClass().add("title-label");
		HBox enrollmentBox = enrollmentDetails(enroll, true);
		enrollmentBox.setAlignment(Pos.CENTER);
		Label gradeLb = new Label("Set New Grade: ");
		ComboBox<Character> gradeCb = new ComboBox<>();
		gradeCb.getItems().addAll('A', 'B', 'C', 'D', 'F');
		if(enroll.getGrade() != '*') {
			gradeCb.setValue(enroll.getGrade());
		} else {
			gradeCb.setValue('F');
		}
		
		HBox gradeBx = new HBox(25, gradeLb, gradeCb);
		gradeBx.setAlignment(Pos.CENTER);
		
		Button saveBtn = new Button("Save Grade");
		Button cancelBtn = new Button("Cancel");
		Button searchBtn = new Button("Search Again");
		
		searchBtn.setOnAction(new SearchForGradeHandler());
		
		cancelBtn.setOnAction(new CancelHandler());
		
		saveBtn.setOnAction(event -> {
			enroll.setGrade(gradeCb.getValue());
			
			try {
				binaryFiles.writeToFile(enroll, false);
				System.out.println("In the try binaryFile should have saved.");
				HBox newHBox = enrollmentDetails(enroll, true);
				newHBox.setAlignment(Pos.CENTER);
				VBox aVBox = new VBox(15, newHBox, searchBtn);
				aVBox.setAlignment(Pos.CENTER);
				borderPane.setCenter(aVBox);
			} catch (IOException e) {
				System.out.println("Exception: " + e);
				e.printStackTrace();
			}
		});
		HBox btnHb = new HBox(15, saveBtn, cancelBtn);
		btnHb.setAlignment(Pos.CENTER);
		VBox resultVb = new VBox(15, title, enrollmentBox, gradeBx, btnHb);
		resultVb.setAlignment(Pos.CENTER);
		return resultVb;
		
	}
	
	public VBox getGradeView(ObservableList<Enrollment> results) {
		Label title = new Label("Enrollments Matching Criteria");
		title.getStyleClass().add("title-label");
		Label warning = new Label("No Matches Found");
		warning.getStyleClass().add("error-label");
		Button setGradeBtn = new Button("Manage Grade");
		Button searchBtn = new Button("Search Again");
		ListView<Enrollment> matches = new ListView<>();
		VBox enrollVb;
		
		searchBtn.setOnAction(new SearchForGradeHandler());
		
		setGradeBtn.setOnAction(event -> {
			if(matches.getSelectionModel().getSelectedIndex() != -1) {
				Enrollment anEnrollment = matches.getSelectionModel().getSelectedItem();
				borderPane.setCenter(getSetGradeView(anEnrollment));
			} else {
				System.out.println("Nothing is Selected.");
			}

		});
		
		matches.getItems().addAll(results);
		
		HBox titleHb = new HBox(title);
		titleHb.setAlignment(Pos.CENTER);
		HBox warningHb = new HBox(warning);
		warningHb.setAlignment(Pos.CENTER);
		HBox matchesHb = new HBox(matches);
		matchesHb.setAlignment(Pos.CENTER);
		HBox btnHb;

		if(results.size() == 0) {
			btnHb = new HBox(searchBtn);
			enrollVb = new VBox(
					10,
					titleHb, 
					warningHb, 
					matchesHb,
					btnHb
					);
		} else {
			btnHb = new HBox(15, setGradeBtn, searchBtn);
			enrollVb = new VBox(
					10,
					titleHb,
					matchesHb,
					btnHb
					);
		}
		
		btnHb.setAlignment(Pos.CENTER);
		enrollVb.setAlignment(Pos.CENTER);
		
		return enrollVb;
	}
	
	public VBox getDetailView(HBox item, String type, boolean afterAdd, int id) {
//		set master id for future actions
		masterID = id;
		
		Label titleLb = new Label(type + " DETAILS");
		titleLb.getStyleClass().add("title-label");
		Button editBtn = new Button("Edit");
		Button addBtn = null;
		HBox buttonHb;
		
		if(afterAdd) {
			addBtn = new Button("Add new " + type);
			buttonHb = new HBox(15, addBtn, editBtn);
		} else {
			buttonHb = new HBox(15, editBtn);
		}
		
		buttonHb.setAlignment(Pos.CENTER);
		

		switch(type)
		{
		case "Student":
			editBtn.setOnAction(new EditStudentHandler());
			if(afterAdd) {
				addBtn.setOnAction(new AddStudentHandler());
			}
			break;
		case "Course":
			editBtn.setOnAction(new EditCourseHandler());
			if(afterAdd) {
				addBtn.setOnAction(new AddCourseHandler());
			}
			break;
		case "Enrollment":
			editBtn.setOnAction(new EditEnrollmentHandler());
			if(afterAdd) {
				addBtn.setOnAction(new AddEnrollmentHandler());
			}
			break;
		default:
			System.out.print("Not a valid option, what happened?");
		}
		
		VBox view = new VBox(15, titleLb, item, buttonHb);
		view.setAlignment(Pos.CENTER);
		
		return view;
		
	}
	
//	SEARCH FOR AN ENROLLMENT FOR GRADE MANAGEMENT
	public VBox getGradeSearchDisplay() {
		
		Label studentLb, yearLb, semesterLb;
		ComboBox<String> yearCb, semesterCb;
		TextField studentTx;
		HBox formBx, buttonBx;
		VBox view;
		Button submitButton, cancelButton;
		
		studentLb = new Label("Enter Student ID: ");
		studentTx = new TextField();
		
		yearLb = new Label("Year: ");
		yearCb = new ComboBox<>();
		yearCb.getItems().addAll("2019", "2020", "2021", "2022");
		
		semesterLb = new Label("Semester: ");
		semesterCb = new ComboBox<>();
		semesterCb.getItems().addAll("Spring", "Summer", "Fall", "Winter");
		
		VBox labelVx = new VBox(23, studentLb, yearLb, semesterLb);
		VBox valueVx = new VBox(15, studentTx, yearCb, semesterCb);
		formBx = new HBox(15, labelVx, valueVx);
		formBx.setAlignment(Pos.CENTER);
		
		submitButton = new Button("Search");
		cancelButton = new Button("Cancel");
		
		cancelButton.setOnAction(new CancelHandler());
		submitButton.setOnAction(event -> {
			int studentId = toValidInt(studentTx.getText());
			int yr = toValidInt(yearCb.getValue());
			String sem = semesterCb.getValue();
			
			VBox enrollVb = getGradeView(matchingEnrollments(enrollmentList, studentId, yr, sem));
			borderPane.setCenter(enrollVb);

		});
		buttonBx = new HBox(15, submitButton, cancelButton);
		buttonBx.setAlignment(Pos.CENTER);
		
		view = new VBox(15, formBx, buttonBx);
		view.setAlignment(Pos.CENTER);
		return view;
	}
	
//	SEARCH BY STUDENT, COURSE, ENROLLMENT BY ID
	public VBox getSearchDisplay(String option) {
		Label errorMessage = new Label("");
		errorMessage.getStyleClass().add("error-label");
		
		Label message = new Label("Please enter the " + option + " ID: ");
		TextField idTx = new TextField();
		HBox firstHb = new HBox(message, idTx);
		firstHb.setAlignment(Pos.CENTER);
		
		Button searchButton = new Button(option + " Search");
		HBox buttonHb = new HBox(searchButton);
		buttonHb.setAlignment(Pos.CENTER);
		
		
		searchButton.setOnAction(event -> {
			int num = toValidInt(idTx.getText());
			boolean wasValid = false;
			VBox vBox = null;
			HBox itemHb;
			
			switch(option)
			{
			case "Student":
				if(isValidStudent(studentList, num)) {
					Student aStudent = getStudent(studentList, num);
					vBox = getDetailView(studentDetails(aStudent), option, false, num);
					wasValid = true;
				}
				break;
			case "Course":
				if(isValidCourse(courseList, num)) {
					Course aCourse = getCourse(courseList, num);
					vBox = getDetailView(courseDetails(aCourse), option, false, num);
					wasValid = true;
				} 
				break;
			case "Enrollment":
				if(isValidEnrollment(enrollmentList, num)) {
					Enrollment anEnrollment = getEnrollment(enrollmentList, num);
					vBox = getDetailView(enrollmentDetails(anEnrollment, true), option, false, num);
					wasValid = true;
				}
				break;
			default:
				System.out.print("Not a valid option, what happened?");
			}
			
			if(!wasValid) {
				errorMessage.setText("Not a valid " + option + " ID.");
			} else {
				itemHb = new HBox(vBox);
				itemHb.setAlignment(Pos.CENTER);
				borderPane.setCenter(itemHb);
			}
			
		});

		VBox vBox = new VBox(20, errorMessage, firstHb, buttonHb);
		vBox.setAlignment(Pos.CENTER);
		
		return vBox;
	}
	
//	VIEW FOR SEARCH STUDENT FOR ENROLLMENT
	public VBox getStudentForEnrollment() {
		Label errorMessage = new Label("");
		errorMessage.getStyleClass().add("error-label");
		
		Label message = new Label("Please enter the Student ID: ");
		TextField idTx = new TextField();
		HBox firstHb = new HBox(15, message, idTx);
		firstHb.setAlignment(Pos.CENTER);
		
		Button searchButton = new Button("Student Search");
		HBox buttonHb = new HBox(searchButton);
		buttonHb.setAlignment(Pos.CENTER);
		
		
		searchButton.setOnAction(event -> {
			int num = toValidInt(idTx.getText());
			VBox studentVBox, enrollVBox;
			HBox itemHb;
			
			if(isValidStudent(studentList, num)) {
				
				HBox studentHbox = studentDetails(getStudent(studentList, num));
				studentHbox.setAlignment(Pos.CENTER);
				studentVBox  = new VBox(studentHbox);
				studentVBox.setAlignment(Pos.CENTER);
				enrollVBox = addEditEnrollmentView(enrollmentList, courseList, false, -1, num);
				enrollVBox.setAlignment(Pos.CENTER);
				itemHb = new HBox(30, studentVBox, enrollVBox);
				
				itemHb.setAlignment(Pos.CENTER);
				borderPane.setCenter(itemHb);
			} else {
				errorMessage.setText("Not a valid Student ID.");
			}
			
		});

		VBox vBox = new VBox(20, errorMessage, firstHb, buttonHb);
		vBox.setAlignment(Pos.CENTER);
		
		return vBox;
	}
	
//	VIEW FOR ADDING/EDITING ENROLLMENT
	public VBox addEditEnrollmentView(ArrayList<Enrollment> enrollmentList, ArrayList<Course> cList, boolean editing, int Eid, int Sid) {
		Label errorMessage, titleLb, courseLb, yearLb, semesterLb;
		ComboBox<String> yearCb, courseCb, semesterCb;
		HBox formBx, buttonBx;
		VBox view;
		Button submitButton, cancelButton;
		
		errorMessage = new Label("");
		errorMessage.getStyleClass().add("error-label");
		
		courseLb = new Label("Course: ");
		courseCb = new ComboBox<>();
		courseCb.getItems().addAll(getListOfCourses(cList));
		
		yearLb = new Label("Year: ");
		yearCb = new ComboBox<>();
		yearCb.getItems().addAll("2019", "2020", "2021", "2022");
		
		semesterLb = new Label("Semester: ");
		semesterCb = new ComboBox<>();
		semesterCb.getItems().addAll("Spring", "Summer", "Fall", "Winter");
		
		VBox labelVx = new VBox(23, courseLb, yearLb, semesterLb);
		VBox valueVx = new VBox(15, courseCb, yearCb, semesterCb);
		formBx = new HBox(15, labelVx, valueVx);
		formBx.setAlignment(Pos.CENTER);
		
		if(editing) {
			Enrollment existingEnrollment = getEnrollment(enrollmentList, Eid);
			Course aCourse = getCourse(cList, existingEnrollment.getCourseID());
			
			String idNumberStr = String.format("%s    %s", 
											   aCourse.getCourseID(),
											   aCourse.getCourseNumber());
			courseCb.setValue(idNumberStr);
			yearCb.setValue(Integer.toString(existingEnrollment.getYear()));
			semesterCb.setValue(existingEnrollment.getSemester());
			
			submitButton = new Button("Save Changes");
			titleLb = new Label("EDIT AN ENROLLMENT");
		} else {
			submitButton = new Button("Create Enrollment");
			titleLb = new Label("CREATE AN ENROLLMENT");
		}
		
		titleLb.getStyleClass().add("title-label");
		
		submitButton.setOnAction(event -> {
			String semesterIn;
			int courseIn, yearIn, studentIn;
			Enrollment theEnrollment;

			String[] checkArray = {semesterCb.getValue(), courseCb.getValue(), yearCb.getValue()};

			if(everythingIsValid(checkArray)) {
				
				semesterIn = semesterCb.getValue();
				courseIn = getCourseIdFromString(courseCb.getValue());
				yearIn = Integer.parseInt(yearCb.getValue());
				studentIn = Sid;
			
				if(editing) {
					theEnrollment = getEnrollment(enrollmentList, Eid);
					theEnrollment.setCourseID(courseIn);
					theEnrollment.setSemester(semesterIn);
					theEnrollment.setYear(yearIn);
				} else {			
					theEnrollment = new Enrollment(yearIn, semesterIn, studentIn, courseIn);
					enrollmentList.add(theEnrollment);
				}
	
				try {
					binaryFiles.writeToFile(theEnrollment, !editing);
					System.out.println("In the try binaryFile should have saved.");
					VBox newVBox = getDetailView(enrollmentDetails(theEnrollment, true), "Enrollment", !editing, theEnrollment.getEnrollmentID());
					newVBox.setAlignment(Pos.CENTER);
					HBox anHBox = new HBox(newVBox);
					anHBox.setAlignment(Pos.CENTER);
					borderPane.setCenter(anHBox);
				} catch (IOException e) {
					System.out.println("Exception: " + e);
					e.printStackTrace();
				}
			} else {
				errorMessage.setText("Please fill out all fields");
			}
			
		});
		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new CancelHandler());
		buttonBx = new HBox(15, submitButton, cancelButton);
		
		
		view = new VBox(20, errorMessage, titleLb, formBx, buttonBx);
		view.setAlignment(Pos.CENTER);
		
		return view;
	}
	
//	VIEW FOR ADDING/EDITING A STUDENT
	public VBox addEditStudentView(ArrayList<Student> studentList, boolean editing, int Sid) {
		Label errorMessage, titleLb, firstLb, lastLb, addressLb, cityLb, stateLb;
		TextField firstTx, lastTx, addressTx, cityTx, stateTx;
		HBox buttonBx, titleBx, formBx;
		VBox view;
		Button submitButton, cancelButton;
		
		errorMessage = new Label("");
		errorMessage.getStyleClass().add("error-label");
		
		firstLb = new Label("First Name: ");
		firstTx = new TextField();
		
		lastLb = new Label("Last Name: ");
		lastTx = new TextField();

		addressLb = new Label("Address: ");
		addressTx = new TextField();
		
		cityLb = new Label("City: ");
		cityTx = new TextField();
		
		stateLb = new Label("State: ");
		stateTx = new TextField();

		
		VBox labelVx = new VBox(23, firstLb, lastLb, addressLb, cityLb, stateLb);
		VBox valueVx = new VBox(15, firstTx, lastTx, addressTx, cityTx, stateTx);
		formBx = new HBox(15, labelVx, valueVx);
		formBx.setAlignment(Pos.CENTER);
		
		if(editing) {
			Student existingStudent = getStudent(studentList, Sid);
			firstTx.setText(existingStudent.getFirstName());
			lastTx.setText(existingStudent.getLastName());
			addressTx.setText(existingStudent.getAddress());
			cityTx.setText(existingStudent.getCity());
			stateTx.setText(existingStudent.getState());
			submitButton = new Button("Save Changes");
			titleLb = new Label("EDIT A STUDENT");
		} else {
			submitButton = new Button("Create Student");
			titleLb = new Label("ADD A NEW STUDENT");
		}
		
		titleLb.getStyleClass().add("title-label");
		
		submitButton.setOnAction(event -> {
			String firstIn, lastIn, addressIn, cityIn, stateIn;
			Student theStudent;
			
			firstIn = firstTx.getText();
			lastIn = lastTx.getText();
			addressIn = addressTx.getText();
			cityIn = cityTx.getText();
			stateIn = stateTx.getText();
			String[] checkArray = {firstIn, lastIn, addressIn, cityIn, stateIn};
			
			if(everythingIsValid(checkArray)) {
				
				if(editing) {
					theStudent = getStudent(studentList, Sid);
					theStudent.setFirstName(firstIn);
					theStudent.setLastName(lastIn);
					theStudent.setAddress(addressIn);
					theStudent.setCity(cityIn);
					theStudent.setState(stateIn);
				} else {			
					theStudent = new Student(firstIn, lastIn, addressIn, cityIn, stateIn);
					studentList.add(theStudent);
				}
				
				try {
					binaryFiles.writeToFile(theStudent, !editing);
					System.out.println("In the try binaryFile should have saved.");
					VBox newVBox = getDetailView(studentDetails(theStudent), "Student", !editing, theStudent.getIdNumber());
					newVBox.setAlignment(Pos.CENTER);
					HBox anHBox = new HBox(newVBox);
					anHBox.setAlignment(Pos.CENTER);
					borderPane.setCenter(anHBox);
				} catch (IOException e) {
					System.out.println("Exception: " + e);
					e.printStackTrace();
				}
			} else {
				errorMessage.setText("Please fill out all fields");
			}
			
		});
		
		titleBx = new HBox(titleLb);
		titleBx.setAlignment(Pos.CENTER);
		
		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new CancelHandler());
		buttonBx = new HBox(15, submitButton, cancelButton);
		buttonBx.setAlignment(Pos.CENTER);
		
		view = new VBox(15, errorMessage, titleBx, formBx, buttonBx);
		view.setAlignment(Pos.CENTER);
		return view;
		
	}
	
//	VIEW FOR ADDING/EDITING A COURSE
	public VBox addEditCourseView(ArrayList<Course> courseList, boolean editing, int Sid) {
		
		Label errorMessage, titleLb, courseNumberLb, courseNameLb, instructorLb, departmentLb;
		TextField courseNumberTx, courseNameTx;
		ComboBox<String> instructorCb, departmentCb;
		HBox formBx, titleBx, buttonBx;
		VBox view;
		Button submitButton, cancelButton;
		
		errorMessage = new Label("");
		errorMessage.getStyleClass().add("error-label");
		
		courseNumberLb = new Label("Course Number: ");
		courseNumberTx = new TextField();
		
		courseNameLb = new Label("Course Name: ");
		courseNameTx = new TextField();

		instructorLb = new Label("Instructor: ");
		instructorCb = new ComboBox<>();
		instructorCb.getItems().addAll("Professor 1", "Professor 2", "Professor 3", "Me");
		
		departmentLb = new Label("Department: ");
		departmentCb = new ComboBox<>();
		departmentCb.getItems().addAll("English", "Computer Science", "Biology", "Math", "Chemistry");
		
		VBox labelVx = new VBox(23, courseNumberLb, courseNameLb, instructorLb, departmentLb);
		VBox valueVx = new VBox(15, courseNumberTx, courseNameTx, instructorCb, departmentCb);
		formBx = new HBox(15, labelVx, valueVx);
		formBx.setAlignment(Pos.CENTER);
		
		if(editing) {
			Course aCourse = getCourse(courseList, Sid);
			courseNumberTx.setText(aCourse.getCourseNumber());
			courseNameTx.setText(aCourse.getCourseName());
			instructorCb.setValue(aCourse.getInstructor());
			departmentCb.setValue(aCourse.getDepartment());
			submitButton = new Button("Save Changes");
			titleLb = new Label("EDIT A COURSE");
		} else {
			submitButton = new Button("Create Course");
			titleLb = new Label("ADD A NEW COURSE");
		}
		
		titleLb.getStyleClass().add("title-label");
		
		submitButton.setOnAction(event -> {
			String courseNumberIn, courseNameIn, instructorIn, departmentIn;
			Course theCourse;
			
			courseNumberIn = courseNumberTx.getText();
			courseNameIn = courseNameTx.getText();
			instructorIn = instructorCb.getValue();
			departmentIn = departmentCb.getValue();
			String[] checkArray = {courseNumberIn, courseNameIn, instructorIn, departmentIn};
			
			if(everythingIsValid(checkArray)) {
				if(editing) {
					theCourse = getCourse(courseList, Sid);
					theCourse.setCourseNumber(courseNumberIn);
					theCourse.setCourseName(courseNameIn);
					theCourse.setInstructor(instructorIn);
					theCourse.setDepartment(departmentIn);
				} else {
					theCourse = new Course(courseNumberIn, courseNameIn, instructorIn, departmentIn);
					courseList.add(theCourse);
				}
	
				try {
					binaryFiles.writeToFile(theCourse, !editing);
					System.out.println("In the try binaryFile should have saved.");
					VBox newVBox = getDetailView(courseDetails(theCourse), "Course", !editing, theCourse.getCourseID());
					newVBox.setAlignment(Pos.CENTER);
					HBox anHBox = new HBox(newVBox);
					anHBox.setAlignment(Pos.CENTER);
					borderPane.setCenter(anHBox);
				} catch (IOException e) {
					System.out.println("Exception: " + e);
					e.printStackTrace();
				}
			} else {
				errorMessage.setText("Please fill out all fields");
			}
		});
		
		titleBx = new HBox(titleLb);
		titleBx.setAlignment(Pos.CENTER);
		
		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new CancelHandler());
		buttonBx = new HBox(15, submitButton, cancelButton);
		buttonBx.setAlignment(Pos.CENTER);
		
		view = new VBox(15, errorMessage, titleBx, formBx, buttonBx);
		view.setAlignment(Pos.CENTER);
		
		return view;
		
	}
	
	
	
	////////////////////////
	//					  //
	//	HELPER FUNCTIONS  //
	//					  //
	////////////////////////
	
//	returns an int either by parsing or -1 if parsing was not possible
	public static int toValidInt(String aString) {
		try {
			return Integer.parseInt(aString);
		} catch (Exception e) {
			return -1;
		}
	}
	
//	Checks whether form has been completely filled out
	public static boolean everythingIsValid(String[] inputs) {
		for(String answer: inputs) {
			if(answer == null || answer.length() == 0) {
				return false;
			}
		}
		return true;
	}
	
//	Returns enrollments with matching properties, includes semester
	public static ObservableList<Enrollment> matchingEnrollments(ArrayList<Enrollment> eList, int Sid, int yr, String sem) {
		ArrayList<Enrollment> matches = new ArrayList<Enrollment>();
		
		for(Enrollment enroll: eList) {
			if(enroll.getStudentID() == Sid) {
				if(enroll.getYear() == yr && enroll.getSemester().contains(sem)) {
					matches.add(enroll);
				}
			}
		}
		
		return FXCollections.observableArrayList(matches);
	}
	
//	Returns enrollments with matching properties
	public static ObservableList<Enrollment> matchingEnrollments(ArrayList<Enrollment> eList, int Cid, int yr) {
		ArrayList<Enrollment> matches = new ArrayList<Enrollment>();
		
		for(Enrollment enroll: eList) {
			if(enroll.getCourseID() == Cid) {
				if(enroll.getYear() == yr) {
					matches.add(enroll);
				}
			}
		}
		
		return FXCollections.observableArrayList(matches);
	}
	
//	gets list of Strings in 'id course#' format for combo box
	public static ObservableList<String> getListOfCourses(ArrayList<Course> cList) {
		String[] courseIdName = new String[cList.size()];
		
		int i = 0;
		for(Course course: cList) {
			courseIdName[i] =  String.format("%s    %s",
											 course.getCourseID(),
											 course.getCourseNumber());
			i++;
		}
		
		return FXCollections.observableArrayList(courseIdName);
		
	}
	
//	Reads String format for CB combo box format
	public static int getCourseIdFromString(String option) {
		return Integer.parseInt(option.split("    ")[0]);
	}
	
//	Edits a student object with new values
	public static void editStudent(Student aStudent, String fname, String lname, String address, String city, String state) {
		aStudent.setFirstName(fname);
		aStudent.setLastName(lname);
		aStudent.setAddress(address);
		aStudent.setCity(city);
		aStudent.setState(state);
	}
	
//	Edits a course object with new values
	public static void editCourse(Course aCourse, String name, String inst, String dept) {
			
		aCourse.setCourseName(name);
		aCourse.setInstructor(inst);
		aCourse.setDepartment(dept);

	}
	
//	Gets student with studentID
	public static Student getStudent(ArrayList<Student> studentList, int studentID) {
		int index = -1;
		for(int i = 0; i < studentList.size(); i++) {
			if(studentList.get(i).getIdNumber() == studentID) {
				index = i;
				break;
			}
		}
		return studentList.get(index);
	}
	
//	returns course with courseID
	public static Course getCourse(ArrayList<Course> courseList, int courseID) {
		int index = -1;
		for(int i = 0; i < courseList.size(); i++) {
			if(courseList.get(i).getCourseID() == courseID) {
				index = i;
				break;
			}
		}
		return courseList.get(index);
	}
	
//	return enrollment with enrollmentID
	public static Enrollment getEnrollment(ArrayList<Enrollment> enrollList, int enrollID) {
		int index = -1;
		for(int i = 0; i < enrollList.size(); i++) {
			if(enrollList.get(i).getEnrollmentID() == enrollID) {
				index = i;
				break;
			}
		}
		return enrollList.get(index);
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
