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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
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
		
		gradeItem.setOnAction(event -> {
			borderPane.setCenter(getGradeSearchDisplay());
		});
		
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
			Label theStudentLb = new Label(getStudent(studentList, theEnrollment.getStudentID()).toString());
			VBox studentVBox = new VBox(theStudentLb);
			studentVBox.setAlignment(Pos.CENTER);
			VBox enrollVBox = addEditEnrollmentView(enrollmentList, courseList, true, masterID, -1);
			enrollVBox.setAlignment(Pos.CENTER);
			HBox itemHb = new HBox(studentVBox, enrollVBox);
			
			itemHb.setAlignment(Pos.CENTER);
			borderPane.setCenter(itemHb);
			

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
		Label message = null;
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
		Label firstLb = new Label("First Name:");
		Label lastLb = new Label("Last Name:");
		Label addressLb = new Label("Address:");
		Label cityLb = new Label("City:");
		Label stateLb = new Label("State:");
		VBox labelVx = new VBox(idLb, firstLb, lastLb, addressLb, cityLb, stateLb);
		
		Label idVal = new Label(Integer.toString(aStudent.getIdNumber()));
		Label firstVal = new Label(aStudent.getFirstName());
		Label lastVal = new Label(aStudent.getLastName());
		Label addressVal = new Label(aStudent.getAddress());
		Label cityVal = new Label(aStudent.getCity());
		Label stateVal = new Label(aStudent.getState());
		VBox ValueVx = new VBox(idVal, firstVal, lastVal, addressVal, cityVal, stateVal);
		
		return new HBox(labelVx, ValueVx);
	}
	
	public HBox courseDetails(Course aCourse) {
		Label courseIdLb = new Label("Course ID:");
		Label courseNumLb = new Label("Course Number:");
		Label courseNameLb = new Label("Course Name:");
		Label courseInstLb = new Label("Course Instructor:");
		Label courseDeptLb = new Label("Course Department:");
		VBox labelVx = new VBox(courseIdLb, courseNumLb, courseNameLb, courseInstLb, courseDeptLb);

		Label courseIdVal = new Label(Integer.toString(aCourse.getCourseID()));
		Label courseNumVal = new Label(aCourse.getCourseNumber());
		Label courseNameVal = new Label(aCourse.getCourseName());
		Label courseInstVal = new Label(aCourse.getInstructor());
		Label courseDeptVal = new Label(aCourse.getDepartment());
		VBox valueVx = new VBox(courseIdVal, courseNumVal, courseNameVal, courseInstVal, courseDeptVal);
		
		return new HBox(labelVx, valueVx);
	}
	
	public HBox enrollmentDetails(Enrollment anEnrollment, boolean withGrade) {
		VBox labelVx, valueVx;
		
		Label enrollIdLb = new Label("Enrollment ID:");
		Label courseIdLb = new Label("Course ID:");
		Label studentIdLb = new Label("Student ID:");
		Label enrollYearLb = new Label("Year:");
		Label enrollSemLb = new Label("Semester:");

		Label enrollIdVal = new Label(Integer.toString(anEnrollment.getEnrollmentID()));
		Label courseIdVal = new Label(Integer.toString(anEnrollment.getCourseID()));
		Label studentIdVal = new Label(Integer.toString(anEnrollment.getStudentID()));
		Label enrollYearVal = new Label(Integer.toString(anEnrollment.getYear()));
		Label enrollSemVal = new Label(anEnrollment.getSemester());

		if(withGrade) {
			Label enrollGradeLb = new Label("Grade:");
			Label enrollGradeVal = new Label(Character.toString(anEnrollment.getGrade()));
			labelVx = new VBox(enrollIdLb, courseIdLb, studentIdLb, enrollYearLb, enrollSemLb, enrollGradeLb);
			valueVx = new VBox(enrollIdVal, courseIdVal, studentIdVal, enrollYearVal, enrollSemVal, enrollGradeVal);
		} else {
			labelVx = new VBox(enrollIdLb, courseIdLb, studentIdLb, enrollYearLb, enrollSemLb);
			valueVx = new VBox(enrollIdVal, courseIdVal, studentIdVal, enrollYearVal, enrollSemVal);
		}
		
		return new HBox(labelVx, valueVx);
	}
	
	public VBox getDetailView(HBox item, String type, boolean afterAdd, int id) {
//		set master id for future actions
		masterID = id;
		
		Button editBtn = new Button("Edit");
		Button addBtn = null;
		HBox buttonHb;
		
		if(afterAdd) {
			addBtn = new Button("Add new " + type);
			buttonHb = new HBox(addBtn, editBtn);
		} else {
			buttonHb = new HBox(editBtn);
		}
		

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
		
		
		
		return new VBox(item, buttonHb);
		
	}
	
//	SEARCH FOR AN ENROLLMENT FOR GRADE MANAGEMENT
	public VBox getGradeSearchDisplay() {
		Label errorMessage = new Label("Not a valid Enrollment");
		
		Label studentLb, yearLb, semesterLb, courseLb;
		ComboBox<String> yearCb, semesterCb;
		TextField studentTx, courseTx;
		HBox studentBx, courseBx, yearBx, semesterBx, buttonBx;
		VBox view;
		Button submitButton, cancelButton;
		
		studentLb = new Label("Enter Student ID: ");
		studentTx = new TextField();
		studentBx = new HBox(studentLb, studentTx);
		
		courseLb = new Label("Enter Course ID: ");
		courseTx = new TextField();
		courseBx = new HBox(courseLb, courseTx);
		
		yearLb = new Label("Year: ");
		yearCb = new ComboBox<>();
		yearCb.getItems().addAll("2019", "2020", "2021", "2022");
		yearBx = new HBox(yearLb, yearCb);
		
		semesterLb = new Label("Semester: ");
		semesterCb = new ComboBox<>();
		semesterCb.getItems().addAll("Spring", "Summer", "Fall", "Winter");
		semesterBx = new HBox(semesterLb, semesterCb);
		
		submitButton = new Button("Search");
		cancelButton = new Button("Cancel");
		
		cancelButton.setOnAction(new CancelHandler());
		submitButton.setOnAction(event -> {
			int studentId = toValidInt(studentTx.getText());
			int courseId = toValidInt(courseTx.getText());
			int yr = toValidInt(yearCb.getValue());
			String sem = semesterCb.getValue();
			
			int EnrollmentID = indexOfMatchingEnrollmentWith(enrollmentList, studentId, courseId, yr, sem);
			if(EnrollmentID == -1) {
				borderPane.setCenter(new Label("WRONG!"));
			} else {
				VBox enrollVb = getDetailView(enrollmentDetails(getEnrollment(enrollmentList, EnrollmentID), true), "Enrollment", false, EnrollmentID);
				borderPane.setCenter(enrollVb);
			}
		});
		buttonBx = new HBox(submitButton, cancelButton);
		
		view = new VBox(studentBx, courseBx, yearBx, semesterBx, buttonBx);
		return view;
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
				item = new Label("Not a valid option, what happened?");
				vBox = new VBox(item);
			}
			
			itemHb = new HBox(vBox);
			itemHb.setAlignment(Pos.CENTER);
			borderPane.setCenter(itemHb);
			
		});

		VBox vBox = new VBox(20, firstHb, buttonHb);
		vBox.setAlignment(Pos.CENTER);
		
		return vBox;
	}
	
//	VIEW FOR SEARCH STUDENT FOR ENROLLMENT
	public VBox getStudentForEnrollment() {
		Label errorMessage = new Label("Not a valid Student ID.");
		
		Label message = new Label("Please enter the Student ID: ");
		TextField idTx = new TextField();
		HBox firstHb = new HBox(message, idTx);
		
		Button searchButton = new Button("Student Search");
		HBox buttonHb = new HBox(searchButton);
		
		
		searchButton.setOnAction(event -> {
			int num = toValidInt(idTx.getText());
			Label item = null;
			VBox vBox, studentVBox, enrollVBox;
			HBox itemHb;
			
			if(isValidStudent(studentList, num)) {
				
				Label studentStr = new Label(getStudent(studentList, num).toString());
				studentVBox  = new VBox(studentStr);
				studentVBox.setAlignment(Pos.CENTER);
				enrollVBox = addEditEnrollmentView(enrollmentList, courseList, false, -1, num);
				enrollVBox.setAlignment(Pos.CENTER);
				itemHb = new HBox(studentVBox, enrollVBox);
			} else {
				item = new Label("Not a valid option, what happened?");
				vBox = new VBox(item);
				itemHb = new HBox(vBox);
			}
			

			itemHb.setAlignment(Pos.CENTER);
			borderPane.setCenter(itemHb);
			
		});

		VBox vBox = new VBox(20, firstHb, buttonHb);
		vBox.setAlignment(Pos.CENTER);
		
		return vBox;
	}
	
//	VIEW FOR ADDING/EDITING ENROLLMENT
	public VBox addEditEnrollmentView(ArrayList<Enrollment> enrollmentList, ArrayList<Course> cList, boolean editing, int Eid, int Sid) {
		Label courseLb, yearLb, semesterLb;
		ComboBox<String> yearCb, courseCb, semesterCb;
		HBox courseBx, yearBx, semesterBx, buttonBx;
		VBox view;
		Button submitButton, cancelButton;
		
		courseLb = new Label("Course: ");
		courseCb = new ComboBox<>();
		courseCb.getItems().addAll(getListOfCourses(cList));
		courseBx = new HBox(courseLb, courseCb);
		
		yearLb = new Label("Year: ");
		yearCb = new ComboBox<>();
		yearCb.getItems().addAll("2019", "2020", "2021", "2022");
		yearBx = new HBox(yearLb, yearCb);
		
		semesterLb = new Label("Semester: ");
		semesterCb = new ComboBox<>();
		semesterCb.getItems().addAll("Spring", "Summer", "Fall", "Winter");
		semesterBx = new HBox(semesterLb, semesterCb);
		
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
		} else {
			submitButton = new Button("Create Enrollment");
		}
		
		submitButton.setOnAction(event -> {
			String semesterIn;
			int courseIn, yearIn, studentIn;
			Enrollment theEnrollment;
			
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
				borderPane.setCenter(new HBox(newVBox));
			} catch (IOException e) {
				System.out.println("Exception: " + e);
				e.printStackTrace();
			}
			
		});
		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new CancelHandler());
		buttonBx = new HBox(submitButton, cancelButton);
		
		
		view = new VBox(20, courseBx, yearBx, semesterBx, buttonBx);
		
		return view;
	}
	
//	VIEW FOR ADDING/EDITING A STUDENT
	public VBox addEditStudentView(ArrayList<Student> studentList, boolean editing, int Sid) {
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
		
		if(editing) {
			Student existingStudent = getStudent(studentList, Sid);
			firstTx.setText(existingStudent.getFirstName());
			lastTx.setText(existingStudent.getLastName());
			addressTx.setText(existingStudent.getAddress());
			cityTx.setText(existingStudent.getCity());
			stateTx.setText(existingStudent.getState());
			submitButton = new Button("Save Changes");
		} else {
			submitButton = new Button("Create Student");
		}
		
		submitButton.setOnAction(event -> {
			String firstIn, lastIn, addressIn, cityIn, stateIn;
			Student theStudent;
			
			firstIn = firstTx.getText();
			lastIn = lastTx.getText();
			addressIn = addressTx.getText();
			cityIn = cityTx.getText();
			stateIn = stateTx.getText();
			
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
				borderPane.setCenter(new HBox(newVBox));
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
	
//	VIEW FOR ADDING/EDITING A COURSE
	public VBox addEditCourseView(ArrayList<Course> courseList, boolean editing, int Sid) {
		
		Label courseNumberLb, courseNameLb, instructorLb, departmentLb;
		TextField courseNumberTx, courseNameTx;
		ComboBox<String> instructorCb, departmentCb;
		HBox courseNumberBx, courseNameBx, instuctorBx, departmentBx, buttonBx;
		VBox view;
		Button submitButton, cancelButton;
		
		courseNumberLb = new Label("Course Number: ");
		courseNumberTx = new TextField();
		courseNumberBx = new HBox(courseNumberLb, courseNumberTx);
		
		courseNameLb = new Label("Course Name: ");
		courseNameTx = new TextField();
		courseNameBx = new HBox(courseNameLb, courseNameTx);

		instructorLb = new Label("Instructor: ");
		instructorCb = new ComboBox<>();
		instructorCb.getItems().addAll("Professor 1", "Professor 2", "Professor 3", "Me");
		instuctorBx = new HBox(instructorLb, instructorCb);
		
		departmentLb = new Label("Department: ");
		departmentCb = new ComboBox<>();
		departmentCb.getItems().addAll("English", "Computer Science", "Biology", "Math", "Chemistry");
		departmentBx = new HBox(departmentLb, departmentCb);
		
		if(editing) {
			Course aCourse = getCourse(courseList, Sid);
			courseNumberTx.setText(aCourse.getCourseNumber());
			courseNameTx.setText(aCourse.getCourseName());
			instructorCb.setValue(aCourse.getInstructor());
			departmentCb.setValue(aCourse.getDepartment());
			submitButton = new Button("Save Changes");
		} else {
			submitButton = new Button("Create Course");
		}
		
		submitButton.setOnAction(event -> {
			String courseNumberIn, courseNameIn, instructorIn, departmentIn;
			Course theCourse;
			
			courseNumberIn = courseNumberTx.getText();
			courseNameIn = courseNameTx.getText();
			instructorIn = instructorCb.getValue();
			departmentIn = departmentCb.getValue();
			
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
				borderPane.setCenter(new HBox(newVBox));
			} catch (IOException e) {
				System.out.println("Exception: " + e);
				e.printStackTrace();
			}
			
		});
		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new CancelHandler());
		buttonBx = new HBox(submitButton, cancelButton);
		
		
		view = new VBox(20, courseNumberBx, courseNameBx, instuctorBx, departmentBx, buttonBx);
		
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
	
	public static int indexOfMatchingEnrollmentWith(ArrayList<Enrollment> eList, int Sid, int Cid, int yr, String sem) {
		for(Enrollment enroll: eList) {
			if(enroll.getStudentID() == Sid && enroll.getCourseID() == Cid) {
				if(enroll.getYear() == yr && enroll.getSemester().contains(sem)) {
					return enroll.getEnrollmentID();
				}
			}
		}
		
		return -1;
	}
	
	public static ObservableList<String> getListOfCourses(ArrayList<Course> cList) {
		String[] courseIdName = new String[cList.size()];
		
		int i = 0;
		for(Course course: cList) {
			courseIdName[i] =  String.format("%s    %s",
											 course.getCourseID(),
											 course.getCourseNumber());
		}
		
		return FXCollections.observableArrayList(courseIdName);
		
	}
	
	public static int getCourseIdFromString(String option) {
		return Integer.parseInt(option.split("    ")[0]);
	}
	
	public static void editStudent(Student aStudent, String fname, String lname, String address, String city, String state) {
		aStudent.setFirstName(fname);
		aStudent.setLastName(lname);
		aStudent.setAddress(address);
		aStudent.setCity(city);
		aStudent.setState(state);
	}
	
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
