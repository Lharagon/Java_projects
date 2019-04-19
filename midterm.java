import java.util.Scanner;
import java.util.ArrayList;

public class midterm {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>();
		ArrayList<Student> studentList = new ArrayList<Student>();
		ArrayList<Enrollment> enrollmentList = new ArrayList<Enrollment>;
		int choice, gradeChoice;;
		
		do {
			showMainMenu();
			choice = validChoice(keyboard);
			System.out.println();
			
			switch(choice) {
			case 1:
				System.out.println("Creating New Student")
				Student newStudent = createStudent(keyboard);
				studentList.add(newStudent);
				break;
			case 2:
				System.out.println("Creating New Course");
				Course newCourse = createCourse(keyboard);
				courseList.add(newCourse);
				break;
			case 3:
				// Enrollment newEnrollment = createEnrollment(keyboard);
				System.out.println("Creating New Enrollment");
				break;
			case 4:
				System.out.println("Edit Student");
				break;
			case 5:
				System.out.println("Edit Course");
				break;
			case 6:
				System.out.println("Edit Enrollment");
				break;
			case 7:
				System.out.println("Student List");
				displayStudents();				
				break;
			case 8:
				System.out.println("Course List");
				displayCourses();
				break;
			case 9:
				displayEnrollments();
				break;
			case 10:
				do {
					showGradesSubMenu();
					gradeChoice = validChoice(keyboard);
					switch(gradeChoice) {
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 0:
						break;
					default:
						tryAgain();
					}
				} while(gradeChoice != 0);
				
				break;
			case 0:
				break;
			default:
				tryAgain();
			}
			
		} while(choice != 0);
		
		System.out.println("BYE BYE");
		keyboard.close();
	}
	
	public static void showMainMenu() {
		System.out.println("Welcome to University Enrollment");
		System.out.println("1. Create Student");
		System.out.println("2. Create Course");
		System.out.println("3. Create Enrollment");
		System.out.println("4. Edit Student");
		System.out.println("5. Edit Course");
		System.out.println("6. Edit Enrollment");
		System.out.println("7. Display Student(s)");
		System.out.println("8. Display Course(s)");
		System.out.println("9. Display Enrollment(s)");
		System.out.println("10. Grades Sub Menu");
		System.out.println("0. --- Quit ---");
		System.out.println("Please enter a valid choice (1-10, 0 to Quit):");
	}
	
	public static void showGradesSubMenu() {
		System.out.println("Grades Menu");
		System.out.println("1. View Grades by Student");
		System.out.println("2. View Grades by Course");
		System.out.println("3. Edit Grades by Student");
		System.out.println("4. Edit Grades by Course");
		System.out.println("0. -- Exit to Menu --");
		System.out.println("Please enter a valid choice (1-4, 0 to Exit):");
	}
	
	public static int validChoice(Scanner theScanner) {
		try {
			return Integer.parseInt(theScanner.nextLine());
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static void tryAgain() {
		System.out.println("Invalid choice. Please try again.");
	}
	
	public static Student createStudent(Scanner keyboard) {
		String first, last;
		
		System.out.print("First Name: ");
		first = keyboard.nextLine();
		System.out.print("Last Name: ");
		last = keyboard.nextLine();
		
		return new Student(first, last);
	}
	
	public static Course createCourse(Scanner keyboard) {
		String id, name, instr, dept;
		
		System.out.print("Course ID: ");
		id = keyboard.nextLine();
		System.out.print("Course Name: ");
		name = keyboard.nextLine();
		System.out.print("Instructor: ");
		instr = keyboard.nextLine();
		System.out.print("Department: ");
		dept = keyboard.nextLine();
		
		return new Course(id, name, instr, dept);
		
	}

	public static void editStudent(Scanner keyboard) {
		// TODO: logic for editing student
	}

	public static void displayStudents(ArrayList<Student> studentList) {
		for(int i = 0; i < studenttList.size(); i++) {
			System.out.println(studentList[i]);
		}
	}

	public static void displayCourses(ArrayList<Course> courseList) {
		for(int i = 0; i < courseList.size(); i++) {
			System.out.println(couseList[i]);
		}
	}

	public static void displayEnrollments(ArrayList<Enrollment> enrollemntList) {
		for(int i = 0; i < enrollmentList.size(); i++) {
			System.out.println(enrollmentList[i]);
		}
	}

}

class Student {
	
	private static int counter = 0;
	
	final private int idNumber;
	private String firstName;
	private String lastName;
	
	public Student(String first, String last) {
		this.idNumber = ++counter;
		setFirstName(first);
		setLastName(last);
	}
	
	public void setFirstName(String first) {
		this.firstName = first;
	}
	
	public void setLastName(String last) {
		this.lastName = last;
	}
	
	public int getIdNumber() {
		return idNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
}

class Course {
	
	private static int counter = 0;
	
	final private int classNumber;
	private String classID;
	private String className;
	private String instructor;
	private String department;
	
	
	public Course(String id, String name, String instructor, String dept) {
		classNumber = ++counter;
		setClassID(id);
		setClassName(name);
		setInstructor(instructor);
		setDepartment(dept);
	}
	
	public int getClassNumber() {
		return classNumber;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}
	
}

class Enrollment {
	
	private int year;
	private String semester;
	private char grade;
	
	public Enrollment(int year, String semester) {
		this.setYear(year);
		this.setSemester(semester);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}
	
	
}






