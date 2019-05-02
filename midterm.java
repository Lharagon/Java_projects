import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class midterm {

	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>();
		ArrayList<Student> studentList = new ArrayList<Student>();
		ArrayList<Enrollment> enrollmentList = new ArrayList<Enrollment>();
		int choice, gradeChoice, num, num2, year, possID;
		String text, text2, sem;
		
//		create BinaryFiles object to handle binary files
		BinaryFiles binaryFiles = new BinaryFiles();
		
//		Load students, courses, and enrollments from files
		loadStudents(studentList, binaryFiles);
		loadCourses(courseList, binaryFiles);
		loadEnrollments(enrollmentList, binaryFiles);
		
//		Use switch to show menu
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
				binaryFiles.writeToFile(newStudent, true); // write new student to studentFile
				break;
			case 2:
				System.out.println("Creating New Course");
				Course newCourse = createCourse(keyboard);
				System.out.println("New Course");
				System.out.println("-----------------------------------");
				System.out.println(newCourse);
				System.out.println("-----------------------------------");
				courseList.add(newCourse);
				binaryFiles.writeToFile(newCourse, true); // write new course to courseFile
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
						System.out.println("Please enter a semester: ");
						sem = keyboard.nextLine();
						Enrollment newEnrollment = createEnrollment(year, sem, stid, ctid);
						System.out.println("New Enrollment");
						System.out.println("-----------------------------------");
						System.out.println(newEnrollment);
						System.out.println("-----------------------------------");
						enrollmentList.add(newEnrollment);
						binaryFiles.writeToFile(newEnrollment, true); // write new enrollment to enrollmentFile
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
					Student aStudent = getStudent(studentList, sID);
					editStudent(keyboard, aStudent);
					binaryFiles.writeToFile(aStudent, false); // replace previous record with new record
				} else {
					tryAgain();
				}
				break;
			case 5:
				System.out.println("Editing Course");
				System.out.println("Please enter the Course id number: ");
				int cID = toValidInt(keyboard.nextLine());
				if(isValidCourse(courseList, cID)) {
					Course aCourse = getCourse(courseList, cID);
					editCourse(keyboard, aCourse);
					binaryFiles.writeToFile(aCourse, false); // replace previous record with new record
				} else {
					tryAgain();
				}
				break;
			case 6:
				System.out.println("Editing Enrollment");
				System.out.println("Please enter the Enrollment id number: ");
				int eID = toValidInt(keyboard.nextLine());
				if(isValidEnrollment(enrollmentList, eID)) {
					Enrollment anEnrollment = getEnrollment(enrollmentList, eID);
					editEnrollment(keyboard, anEnrollment);
					binaryFiles.writeToFile(anEnrollment, false); // replace previous record with new record
				} else {
					tryAgain();
				}
				break;
			case 7:
//				Display student(s)
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
//				Display course(s)
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
//				Display enrollments
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
//				Grade menu
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
							binaryFiles.writeToFile(theEnroll, false); // replace previous record with new record  
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
							binaryFiles.writeToFile(theEnroll, false);  // replace previous record with new record
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
				binaryFiles.closeAll();
				break;
			default:
				tryAgain();
			}
			
		} while(choice != 0);
		
		System.out.println("BYE BYE");
		keyboard.close();
	}

//	Shows main menu
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
	
//	Shows grade sub menu
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
	
//	returns an int either by parsing or -1 if parsing was not possible
	public static int toValidInt(String aString) {
		try {
			return Integer.parseInt(aString);
		} catch (Exception e) {
			return -1;
		}
	}
	
//	print error message
	public static void tryAgain() {
		System.out.println("Invalid choice. Please try again.");
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
	
//	Menu to choose a valid grade for an enrollment
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
	
//	checks if enrollment with user input exists
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
	
//	checks whether there are students and courses
	public static boolean canCreateEnrollment(ArrayList<Student> studentList, ArrayList<Course> courseList) {
		if(studentList.size() > 0 && courseList.size() > 0) {
			return true;
		}
		return false;
	}
	
//	creates a new student
	public static Student createStudent(Scanner keyboard) {
		String first, last, address, city, state;
		
		System.out.print("First Name: ");
		first = keyboard.nextLine();
		System.out.print("Last Name: ");
		last = keyboard.nextLine();
		System.out.print("Address: ");
		address = keyboard.nextLine();
		System.out.print("City: ");
		city = keyboard.nextLine();
		System.out.print("State: ");
		state = keyboard.nextLine();
		
		return new Student(first, last, address, city, state);
	}
	
//	creates a new course
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
	
//	creates a new enrollment
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
			System.out.println("3. Edit Address");
			System.out.println("4. Edit City");
			System.out.println("5. Edit State");
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
			case 3:
				System.out.print("Address: ");
				name = keyboard.nextLine();
				stu.setAddress(name);
				break;
			case 4:
				System.out.print("City: ");
				name = keyboard.nextLine();
				stu.setCity(name);
				break;
			case 5:
				System.out.print("State: ");
				name = keyboard.nextLine();
				stu.setState(name);
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

	public static void displayStudents(ArrayList<Student> studentList) {
		System.out.println("-----------------------------------");
		for(Student student: studentList) {
			System.out.println(student);
			System.out.println("-----------------------------------");
		}
	}

	public static void displayCourses(ArrayList<Course> courseList) {
		System.out.println("-----------------------------------");
		for(Course course: courseList) {
			System.out.println(course);
			System.out.println("-----------------------------------");
		}

	}

	public static void displayEnrollments(ArrayList<Enrollment> enrollmentList) {
		System.out.println("-----------------------------------");
		for(Enrollment enroll: enrollmentList) {
			System.out.println(enroll);
			System.out.println("-----------------------------------");
		}

	}
	
	public static void displayGradesByStudent(ArrayList<Enrollment> enrollmentList, int studentID) {
		System.out.println("-----------------------------------");
		for(Enrollment enroll: enrollmentList) {
			if(enroll.getStudentID() == studentID) {
				System.out.println(enroll);
				System.out.println("-----------------------------------");	
			}
		}
	}
	
	public static void displayGradesByCourse(ArrayList<Enrollment> enrollmentList, int courseID) {
		System.out.println("-----------------------------------");
		for(Enrollment enroll: enrollmentList) {
			if(enroll.getCourseID() == courseID) {
				System.out.println(enroll);
				System.out.println("-----------------------------------");
			}
		}
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

//Class handles binary files used in main
class BinaryFiles {
	private final int STUDENT_RECORD_SIZE = 204;
	private final int COURSE_RECORD_SIZE = 164;
	private final int ENROLL_RECORD_SIZE = 58;
	private final int MAX_STR_LENGTH = 20;
	private RandomAccessFile studentFile;
	private RandomAccessFile courseFile;
	private RandomAccessFile enrollmentFile;
	
	public BinaryFiles() throws FileNotFoundException {
		studentFile = new RandomAccessFile("Students.dat", "rw");
		courseFile = new RandomAccessFile("Courses.dat", "rw");
		enrollmentFile = new RandomAccessFile("Enrollments.dat", "rw");
	}
	
//	Overloaded mehtods for finding start location of record
	public long getStartByte(Student student) {
		return STUDENT_RECORD_SIZE * (student.getIdNumber() - 1);
	}
	
	public long getStartByte(Course course) {
		return COURSE_RECORD_SIZE * (course.getCourseID() - 1);
	}
	
	public long getStartByte(Enrollment enroll) {
		return ENROLL_RECORD_SIZE * (enroll.getEnrollmentID() - 1);
	}
	
//	Methods for reading Students, Courses, Enrollment records from file
	public Student readStudentFromFile() throws IOException {
		int idNum;
		String first, last, address, city, state;
		
		idNum = studentFile.readInt();
		first = readAString(studentFile);
		last = readAString(studentFile);
		address = readAString(studentFile);
		city = readAString(studentFile);
		state = readAString(studentFile);
		
		return new Student(idNum, first, last, address, city, state);
	}
	
	public Course readCourseFromFile() throws IOException {
		String num, name, inst, dept;
		int idNum;
		idNum = courseFile.readInt();
		num = readAString(courseFile);
		name = readAString(courseFile);
		inst = readAString(courseFile);
		dept = readAString(courseFile);
		
		return new Course(idNum, num, name, inst, dept);
	}
	
	public Enrollment readEnrollmentFromFile() throws IOException {
		int Eid, Cid, Sid, year;
		String sem;
		char grade;
		
		Eid = enrollmentFile.readInt();
		Cid = enrollmentFile.readInt();
		Sid = enrollmentFile.readInt();
		year = enrollmentFile.readInt();
		sem = readAString(enrollmentFile);
		grade = enrollmentFile.readChar();
		
		return new Enrollment(Eid, year, sem, Sid, Cid, grade);
	}
	
//	Methods to write Student, Course, and Enrollment records to files
	public void writeToFile(Student student, boolean adding) throws IOException {
		if(adding) {
			studentFile.seek(studentFile.length());
		} else {
			studentFile.seek(getStartByte(student));
		}
		
		studentFile.writeInt(student.getIdNumber());
		writeString(studentFile, student.getFirstName());
		writeString(studentFile, student.getLastName());
		writeString(studentFile, student.getAddress());
		writeString(studentFile, student.getCity());
		writeString(studentFile, student.getState());
		
	}
	
//	Overloaded methods for writing Courses, Students, and Enrollments
	public void writeToFile(Course course, boolean adding) throws IOException {
		if(adding) {
			courseFile.seek(courseFile.length());
		} else {
			courseFile.seek(getStartByte(course));
		}

		courseFile.writeInt(course.getCourseID());
		writeString(courseFile, course.getCourseNumber());
		writeString(courseFile, course.getCourseName());
		writeString(courseFile, course.getInstructor());
		writeString(courseFile, course.getDepartment());
		
	}
	
	public void writeToFile(Enrollment enrollment, boolean adding) throws IOException {
		if(adding) {
			enrollmentFile.seek(enrollmentFile.length());
		} else {
			enrollmentFile.seek(getStartByte(enrollment));
		}
		
		enrollmentFile.writeInt(enrollment.getEnrollmentID());
		enrollmentFile.writeInt(enrollment.getCourseID());
		enrollmentFile.writeInt(enrollment.getStudentID());
		enrollmentFile.writeInt(enrollment.getYear());
		writeString(enrollmentFile, enrollment.getSemester());
		enrollmentFile.writeChar(enrollment.getGrade());
	}
	
//	General method for writing consistently sized strings
	public void writeString(RandomAccessFile theFile, String theString) throws IOException {
		if (theString.length() > MAX_STR_LENGTH) {
			for(int i = 0; i < MAX_STR_LENGTH; i++) {
				theFile.writeChar(theString.charAt(i));
			}
		} else {
			theFile.writeChars(theString);
			
			for (int i = 0; i < (MAX_STR_LENGTH - theString.length()); i++)
				theFile.writeChar(' ');
		}
	}
	
//	General method for reading consistently sized strings
	public String readAString(RandomAccessFile theFile) throws IOException {
		
		char[] charArray = new char[MAX_STR_LENGTH];
		
		for(int i = 0; i < MAX_STR_LENGTH; i++) {
			charArray[i] = theFile.readChar();
		}
		
		String theString = new String(charArray);
		
		return theString.trim();
	}
	
	public long getNumberOfStudents() throws IOException {
		return studentFile.length() / STUDENT_RECORD_SIZE;
	}
	
	public long getNumberOfCourses() throws IOException {
		return courseFile.length() / COURSE_RECORD_SIZE;
	}
	
	public long getNumberOfEnrollments() throws IOException {
		return enrollmentFile.length() / ENROLL_RECORD_SIZE;
	}
	
	public void closeAll() throws IOException {
		studentFile.close();
		courseFile.close();
		enrollmentFile.close();
		
	}
	
}

//Student class
class Student {
	
	private static int counter = 0;
	
	final private int idNumber;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	
//	Used when creating new Student
	public Student(String first, String last, String address, String city, String state) {
		this.idNumber = ++counter;
		setFirstName(first);
		setLastName(last);
		setAddress(address);
		setCity(city);
		setState(state);
	}
	
//	Used when loading students
	public Student(int id, String first, String last, String address, String city, String state) {
		++counter;
		this.idNumber = id;
		this.firstName = first;
		this.lastName = last;
		this.address = address;
		this.city = city;
		this.state = state;
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
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String toString() {
		return String.format(
				
				"ID Number: %s\n"
				+ "First Name: %s\n"
				+ "Last Name: %s\n"
				+ "Address: %s\n"
				+ "City: %s\n"
				+ "State: %s\n",
				getIdNumber(), 
				getFirstName(), 
				getLastName(),
				getAddress(),
				getCity(),
				getState()); 
	}

}

class Course {
	
	private static int counter = 0;
	
	final private int courseID;
	private String courseNumber;
	private String courseName;
	private String instructor;
	private String department;
	
//	Used when creating new course
	public Course(String number, String name, String instructor, String dept) {
		courseID = ++counter;
		setCourseNumber(number);
		setCourseName(name);
		setInstructor(instructor);
		setDepartment(dept);
	}
	
//	Used when loading courses
	public Course(int id, String number, String name, String instructor, String dept) {
		++counter;
		courseID = id;
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
	
//	Used when creating new enrollment
	public Enrollment(int year, String semester, int student, int course) {
		enrollmentID = ++counter;
		
		this.setYear(year);
		this.setSemester(semester);
		this.setStudentID(student);
		this.setCourseID(course);
	}
	
//	Used when loading enrollments
	public Enrollment(int id, int year, String semester, int student, int course, char grade) {
		++counter;
		
		this.enrollmentID = id;
		this.setYear(year);
		this.setSemester(semester);
		this.setStudentID(student);
		this.setCourseID(course);
		this.setGrade(grade);
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






