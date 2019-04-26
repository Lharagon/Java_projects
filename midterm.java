import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class midterm {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>();
		ArrayList<Student> studentList = new ArrayList<Student>();
		ArrayList<Enrollment> enrollmentList = new ArrayList<Enrollment>();
		int choice, gradeChoice;;
		
		do {
			showMainMenu();
			choice = toValidInt(keyboard.nextLine());
			System.out.println();
			
			switch(choice) {
			case 1:
				System.out.println("Creating New Student");
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
				System.out.println("Editing Student");
				System.out.println("Please enter the Student id number.");
				int sID = toValidInt(keyboard.nextLine());
				if(isValidStudent(studentList, sID)) {
					editStudent(keyboard, studentList.get(sID - 1));
				} else {
					tryAgain();
				}
				break;
			case 5:
				System.out.println("Edit Course");
				System.out.println("Please enter the Course id number.");
				String cID = keyboard.nextLine();
				if(isValidCourse(courseList, cID)) {
//					editCourse(courseList.get(cID));
				} else {
					tryAgain();
				}
				break;
			case 6:
				System.out.println("Edit Enrollment");
				break;
			case 7:
				System.out.println("Student List");
				displayStudents(studentList);				
				break;
			case 8:
				System.out.println("Course List");
				displayCourses(courseList);
				break;
			case 9:
				displayEnrollments(enrollmentList);
				break;
			case 10:
				do {
					showGradesSubMenu();
					gradeChoice = toValidInt(keyboard.nextLine());
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
	
	public static int toValidInt(String aString) {
		try {
			return Integer.parseInt(aString);
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static void tryAgain() {
		System.out.println("Invalid choice. Please try again.");
	}
	
	public static boolean isValidStudent (ArrayList<Student> studentList, int studentID) {
		
		for(int i = 0; i < studentList.size(); i++) {
			if(studentList.get(i).getIdNumber() == studentID) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isValidCourse (ArrayList<Course> courseList, String courseID) {
		for(int i = 0; i < courseList.size(); i++) {
			if(courseList.get(i).getCourseID() == courseID) {
				return true;
			}
		}
		return false;
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

	public static void editStudent(Scanner keyboard, Student stu) {
		int choiceEdit;
		String name;
		System.out.println("Student Information:");
		System.out.println(stu);
		do {
			System.out.println("1. Edit First Name");
			System.out.println("2. Edit Last Name");
			System.out.println("0. Done");
			choiceEdit = toValidInt(keyboard.nextLine());
			
			switch(choiceEdit) {
			case 1:
				System.out.print("First Name: ");
				name = keyboard.nextLine();
				stu.setFirstName(name);
				break;
			case 2:
				System.out.print("Last name: ");
				name = keyboard.nextLine();
				stu.setLastName(name);
				break;
			case 0:
				break;
			default:
				tryAgain();
			}
		} while (choiceEdit != 0);
		
		
	}
	
	public static void editCourse(Course cour) {
		//	TODO: logic for editing Course
		System.out.println("editing course");
	}

	public static void displayStudents(ArrayList<Student> studentList) {
		System.out.println("-----------------------------------");
		for(int i = 0; i < studentList.size(); i++) {
			System.out.println(studentList.get(i));
			System.out.println("-----------------------------------");
		}

	}

	public static void displayCourses(ArrayList<Course> courseList) {
		System.out.println("-----------------------------------");
		for(int i = 0; i < courseList.size(); i++) {
			System.out.println(courseList.get(i));
			System.out.println("-----------------------------------");
		}

	}

	public static void displayEnrollments(ArrayList<Enrollment> enrollmentList) {
		System.out.println("-----------------------------------");
		for(int i = 0; i < enrollmentList.size(); i++) {
			System.out.println(enrollmentList.get(i));
			System.out.println("-----------------------------------");
		}

	}
	
	public static void displayGradesByStudent(ArrayList<Enrollment> enrollmentList, int studentID) {
		
	}
	
	public static void displayGradesByCourse(ArrayList<Enrollment> enrollmentList, String courseID) {
		
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
	
	public String toString() {
		return String.format(
				
				"ID Number: %s\n"
				+ "First Name: %s\n"
				+ "Last Name: %s\n",
				getIdNumber(), 
				getFirstName(), 
				getLastName()); 
	}
}

class Course {
	
	private static int counter = 0;
	
	final private int courseNumber;
	private String courseID;
	private String courseName;
	private String instructor;
	private String department;
	
	
	public Course(String id, String name, String instructor, String dept) {
		courseNumber = ++counter;
		setCourseID(id);
		setCourseName(name);
		setInstructor(instructor);
		setDepartment(dept);
	}
	
	public String toString() {
		return String.format(
				"Course Number: %s\n"
				+ "Course ID: %s\n"
				+ "Course Name: %s\n"
				+ "Course Instructor: %s\n"
				+ "Course Department: %s\n",
				getCourseNumber(),
				getCourseID(),
				getCourseName(),
				getInstructor(),
				getDepartment());
	}
	
	public int getCourseNumber() {
		return courseNumber;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
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






