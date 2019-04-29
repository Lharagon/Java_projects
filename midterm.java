import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class midterm {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>();
		ArrayList<Student> studentList = new ArrayList<Student>();
		ArrayList<Enrollment> enrollmentList = new ArrayList<Enrollment>();
		int choice, gradeChoice, num, num2, year, possID;
		String text, text2, sem;
		
		System.out.println("Welcome to University Enrollment");
		do {
			showMainMenu();
			choice = toValidInt(keyboard.nextLine());
			System.out.println();
			
			switch(choice) {
			case 1:
				System.out.println("Creating New Student");
				Student newStudent = createStudent(keyboard);
				System.out.println("New Student");
				System.out.println("-----------------------------------");
				System.out.println(newStudent);
				System.out.println("-----------------------------------");
				studentList.add(newStudent);
				break;
			case 2:
				System.out.println("Creating New Course");
				Course newCourse = createCourse(keyboard);
				System.out.println("New Course");
				System.out.println("-----------------------------------");
				System.out.println(newCourse);
				System.out.println("-----------------------------------");
				courseList.add(newCourse);
				break;
			case 3:
				System.out.println("Creating New Enrollment");
				if(canCreateEnrollment(studentList, courseList)) {
					System.out.println("Please enter a Student id number:");
					int stid = toValidInt(keyboard.nextLine());
					System.out.println("Please enter a Course id number: ");
					int ctid = toValidInt(keyboard.nextLine());
					if(isValidStudent(studentList, stid) && isValidCourse(courseList, ctid)) {
						System.out.println("Student: \n" + getStudent(studentList, stid));
						System.out.println("Course: \n" + getCourse(courseList, ctid));
						System.out.println("Please enter an enrollment year: ");
						year = toValidInt(keyboard.nextLine());
						System.out.println("Please enter a sememster: ");
						sem = keyboard.nextLine();
						Enrollment newEnrollment = createEnrollment(year, sem, stid, ctid);
						System.out.println("New Enrollment");
						System.out.println("-----------------------------------");
						System.out.println(newEnrollment);
						System.out.println("-----------------------------------");
						enrollmentList.add(newEnrollment);
					} else {
						System.out.println("Not a valid student id and/or course id.");
					}
				} else {
					System.out.println("There are not enought students and/or courses for enrollment.");
				}

				break;
			case 4:
				System.out.println("Editing Student");
				System.out.println("Please enter the Student id number: ");
				int sID = toValidInt(keyboard.nextLine());
				if(isValidStudent(studentList, sID)) {
					editStudent(keyboard, getStudent(studentList, sID));
				} else {
					tryAgain();
				}
				break;
			case 5:
				System.out.println("Editing Course");
				System.out.println("Please enter the Course id number: ");
				int cID = toValidInt(keyboard.nextLine());
				if(isValidCourse(courseList, cID)) {
					editCourse(keyboard, getCourse(courseList, cID));
				} else {
					tryAgain();
				}
				break;
			case 6:
				System.out.println("Editing Enrollment");
				System.out.println("Please enter the Enrollment id number: ");
				int eID = toValidInt(keyboard.nextLine());
				if(isValidEnrollment(enrollmentList, eID)) {
					editEnrollment(keyboard, getEnrollment(enrollmentList, eID));
				} else {
					tryAgain();
				}
				break;
			case 7:
				System.out.println("Enter Student id Number or leave blank to display all students: ");
				text = keyboard.nextLine();
				if (text.length() == 0) {
					System.out.println("Student List");
					displayStudents(studentList);						
				} else {
					num = toValidInt(text);
					if(isValidStudent(studentList, num)) {
						System.out.println(getStudent(studentList, num));
					} else {
						System.out.println("Not a valid student ID number.");
					}
				}
				break;
			case 8:
				System.out.println("Enter Course id Number or leave blank to display all courses: ");
				text = keyboard.nextLine();
				if (text.length() == 0) {
					System.out.println("Course List");
					displayCourses(courseList);					
				} else {
					num = toValidInt(text);
					if(isValidCourse(courseList, num)) {
						System.out.println(getCourse(courseList, num));
					} else {
						System.out.println("Not a valid course ID number.");
					}
				}

				break;
			case 9:
				System.out.println("Enter Enrollment id Number or leave blank to display all enrollments: ");
				text = keyboard.nextLine();
				if (text.length() == 0) {
					System.out.println("Enrollment List");
					displayEnrollments(enrollmentList);					
				} else {
					num = toValidInt(text);
					if(isValidEnrollment(enrollmentList, num)) {
						System.out.println(getEnrollment(enrollmentList, num));
					} else {
						System.out.println("Not a valid enrollment ID number.");
					}
				}
				break;
			case 10:
				do {
					showGradesSubMenu();
					gradeChoice = toValidInt(keyboard.nextLine());
					switch(gradeChoice) {
					case 1:
						System.out.println("Enter Student id Number: ");
						text = keyboard.nextLine();
						num = toValidInt(text);
						if(isValidStudent(studentList, num)) {
							displayGradesByStudent(enrollmentList, num);
						} else {
							System.out.println("Not a valid student ID number.");
						}
						
						break;
					case 2:
						System.out.println("Enter Course id Number: ");
						text = keyboard.nextLine();
						num = toValidInt(text);
						if(isValidCourse(courseList, num)) {
							displayGradesByCourse(enrollmentList, num);
						} else {
							System.out.println("Not a valid course ID number.");
						}
						break;
					case 3:
						System.out.println("Enter Student id Number: ");
						text = keyboard.nextLine();
						num = toValidInt(text);
						System.out.println("Enter Course id Number: ");
						text2 = keyboard.nextLine();
						num2 = toValidInt(text2);
						System.out.println("Please enter an enrollment year: ");
						year = toValidInt(keyboard.nextLine());
						System.out.println("Please enter a sememster: ");
						sem = keyboard.nextLine();
						
						possID = isValidEnrollmentByStudent(enrollmentList, num2, num, year, sem);
						if(possID != -1) {
							Enrollment theEnroll = getEnrollment(enrollmentList, possID);
							System.out.println(theEnroll);
							setValidGrade(keyboard, theEnroll);
						} else {
							tryAgain();
						}	
						break;
					case 4:
						System.out.println("Enter Enrollment id Number ");
						text = keyboard.nextLine();
						num = toValidInt(text);
						if(isValidEnrollment(enrollmentList, num)) {
							Enrollment theEnroll = getEnrollment(enrollmentList, num);
							System.out.println(theEnroll);
							setValidGrade(keyboard, theEnroll);
						} else {
							tryAgain();
						}	
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
		System.out.println();
		System.out.println("MAIN MENU");
		System.out.println("-----------------------------------");
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
		System.out.println();
		System.out.println("GRADES MENU");
		System.out.println("-----------------------------------");
		System.out.println("1. View Grades by Student");
		System.out.println("2. View Grades by Course");
		System.out.println("3. Edit Grades by Student");
		System.out.println("4. Edit Grades by Enrollment");
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
	
	public static void setValidGrade(Scanner keyboard, Enrollment enroll) {
		int gradeNum;
		boolean gradeWasSet; 
		do {
			gradeWasSet = true;
			System.out.println("Please choose a Grade for this enrollment: ");
			System.out.println("1. A");
			System.out.println("2. B");
			System.out.println("3. C");
			System.out.println("4. D");
			System.out.println("5. F");
			System.out.println("0. Cancel");
			gradeNum = toValidInt(keyboard.nextLine());
			switch(gradeNum) {
			case 1:
				enroll.setGrade('A');
				break;
			case 2:
				enroll.setGrade('B');
				break;
			case 3:
				enroll.setGrade('C');
				break;
			case 4:
				enroll.setGrade('D');
				break;
			case 5:
				enroll.setGrade('F');
				break;
			case 0:
				break;
			default:
				gradeWasSet = false;
				tryAgain();
			}

		} while(gradeNum != 0 && !gradeWasSet);

	}
	
	public static boolean isValidCourse (ArrayList<Course> courseList, int courseID) {
		for(int i = 0; i < courseList.size(); i++) {
			if(courseList.get(i).getCourseID() == courseID) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isValidEnrollment(ArrayList<Enrollment> enrollList, int enrollID) {
		for(int i = 0; i < enrollList.size(); i++) {
			if(enrollList.get(i).getEnrollmentID() == enrollID) {
				return true;
			}
		}
		return false;
	}
	
	public static int isValidEnrollmentByStudent(ArrayList<Enrollment> enrollList, int cID, int sID, int year, String semester) {
		Enrollment enroll;
		for(int i = 0; i < enrollList.size(); i++) {
			enroll = enrollList.get(i);
			
			if(enroll.getStudentID() == sID && enroll.getCourseID() == cID && enroll.getSemester().equalsIgnoreCase(semester) && enroll.getYear() == year) {
				return enroll.getEnrollmentID();
			}
		}
		return -1;
	}
	
	public static boolean canCreateEnrollment(ArrayList<Student> studentList, ArrayList<Course> courseList) {
		if(studentList.size() > 0 && courseList.size() > 0) {
			return true;
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
		String num, name, instr, dept;
		
		System.out.print("Course Number: ");
		num = keyboard.nextLine();
		System.out.print("Course Name: ");
		name = keyboard.nextLine();
		System.out.print("Instructor: ");
		instr = keyboard.nextLine();
		System.out.print("Department: ");
		dept = keyboard.nextLine();
		
		return new Course(num, name, instr, dept);
		
	}
	
	public static Enrollment createEnrollment(int year, String semester, int stid, int coid) {
		return new Enrollment(year, semester, stid, coid);
	}

	public static void editStudent(Scanner keyboard, Student stu) {
		int choiceEdit;
		String name;
		System.out.println("Student Information:");
		System.out.println(stu);
		do {
			System.out.println("MENU");
			System.out.println("-----------------------------------");
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
	
	public static void editCourse(Scanner keyboard, Course cour) {
		System.out.println("editing course");
		int choiceEdit;
		String text;
		System.out.println("Course Information:");
		System.out.println(cour);
		do {
			System.out.println("MENU");
			System.out.println("-----------------------------------");
			System.out.println("1. Edit Course Name");
			System.out.println("2. Edit Course Instructor");
			System.out.println("3. Edit Course Department");
			System.out.println("0. Done");
			choiceEdit = toValidInt(keyboard.nextLine());
			
			switch(choiceEdit) {
			case 1:
				System.out.print("Course Name: ");
				text = keyboard.nextLine();
				cour.setCourseName(text);
				break;
			case 2:
				System.out.print("Course Instructor: ");
				text = keyboard.nextLine();
				cour.setInstructor(text);
				break;
			case 3:
				System.out.print("Course Department: ");
				text = keyboard.nextLine();
				cour.setDepartment(text);
				break;
			case 0:
				break;
			default:
				tryAgain();
				
			}
		} while(choiceEdit != 0);
	}
	
	public static void editEnrollment(Scanner keyboard, Enrollment enroll) {
		String text;
		int choiceEdit;
		int num;
		System.out.println("Enrollment Information:");
		System.out.println(enroll);
		do {
			System.out.println("MENU");
			System.out.println("-----------------------------------");
			System.out.println("1. Edit Enrollment Year");
			System.out.println("2. Edit Enrollment Semester");
			System.out.println("0. Done");
			choiceEdit = toValidInt(keyboard.nextLine());
			switch(choiceEdit) {
			case 1:
				System.out.println("Enrollment Year: ");
				num = toValidInt(keyboard.nextLine());
				if(num > 0) {
					enroll.setYear(num);
				} else {
					System.out.println("Not a valid year");
				}
				break;
			case 2:
				System.out.println("Enrollment Semester: ");
				text = keyboard.nextLine();
				enroll.setSemester(text);
				break;
			case 0:
				break;
			default:
				tryAgain();
			}
		} while(choiceEdit != 0);
	}
	
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
		System.out.println("-----------------------------------");
		for(int i = 0; i < enrollmentList.size(); i++) {
			if(enrollmentList.get(i).getStudentID() == studentID) {
				System.out.println(enrollmentList.get(i));
				System.out.println("-----------------------------------");	
			}
		}
	}
	
	public static void displayGradesByCourse(ArrayList<Enrollment> enrollmentList, int courseID) {
		System.out.println("-----------------------------------");
		for(int i = 0; i < enrollmentList.size(); i++) {
			if(enrollmentList.get(i).getCourseID() == courseID) {
				System.out.println(enrollmentList.get(i));
				System.out.println("-----------------------------------");
			}
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
	
	final private int courseID;
	private String courseNumber;
	private String courseName;
	private String instructor;
	private String department;
	
	
	public Course(String number, String name, String instructor, String dept) {
		courseID = ++counter;
		setCourseNumber(number);
		setCourseName(name);
		setInstructor(instructor);
		setDepartment(dept);
	}
	
	public int getCourseID() {
		return courseID;
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

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	
	public String toString() {
		return String.format(
				"Course ID: %s\n"
				+ "Course Number: %s\n"
				+ "Course Name: %s\n"
				+ "Course Instructor: %s\n"
				+ "Course Department: %s\n",
				getCourseID(),
				getCourseNumber(),
				getCourseName(),
				getInstructor(),
				getDepartment());
	}
}

class Enrollment {
	private static int counter = 0;
	
	final private int enrollmentID;
	private int year;
	private String semester;
	private int studentID;
	private int courseID;
	private char grade = '*';
	
	public Enrollment(int year, String semester, int student, int course) {
		enrollmentID = ++counter;
		
		this.setYear(year);
		this.setSemester(semester);
		this.setStudentID(student);
		this.setCourseID(course);
	}
	
	public int getEnrollmentID() {
		return enrollmentID;
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

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	
	public String toString() {
		return String.format(
				"Enrollment ID: %s\n"
				+ "Course ID: %s\n"
				+ "Student ID: %s\n"
				+ "Year: %s\n"
				+ "Semester: %s\n"
				+ "Grade: %s\n",
				getEnrollmentID(),
				getCourseID(),
				getStudentID(),
				getYear(),
				getSemester(),
				getGrade());
	}
	
}






