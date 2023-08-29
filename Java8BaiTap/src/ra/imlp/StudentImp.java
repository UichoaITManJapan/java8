package ra.imlp;

import ra.entity.Student;

import java.io.*;
import java.util.*;

public class StudentImp {
    public static List<Student> studentList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Đọc dữ liệu từ file listStudent.txt
        readDataFromFile(studentList);
        do {
            System.out.println("*****************************MENU************************");
            System.out.println("1. Nhập thông tin các sinh viên");
            System.out.println("2. Tính tuổi các sinh viên");
            System.out.println("3. Tính điểm trung bình và xếp loại sinh viên");
            System.out.println("4. Sắp xếp sinh viên theo tuổi tăng dần");
            System.out.println("5. Thống kê sinh viên theo xếp loại sinh viên");
            System.out.println("6. Cập nhật thông tin sinh viên theo mã sinh viên");
            System.out.println("7. Tìm kiếm sinh viên theo tên sinh viên");
            System.out.println("8. Thoát");
            System.out.println("===========================================================");
            System.out.print("Chọn chức năng :");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1:
                        addListStudent();
                        break;
                    case 2:
                        calListAge();
                        break;
                    case 3:
                        calListAvgAndRank();
                        break;
                    case 4:
                        sortListStudentASC();
                        break;
                    case 5:
                        staticListStudent();
                        break;
                    case 6:
                        updateStudent(scanner);
                        break;
                    case 7:
                        searchStudentByName();
                        break;
                    case 8:
                        writeDataToFile(studentList);
                        System.exit(0);
                    default:
                        System.err.println("Vui lòng nhập từ 1-8!");
                }
            } catch (NumberFormatException ex1){
                System.err.println("Lỗi định dạng. Vui lòng nhập lại!");
            } catch (Exception exception){
                System.err.println("Xãy ra lỗi trong quá trình nhập dữ liệu. Vui lòng liên hệ tới hệ thống!");
            }
        } while (true);
    }
    // tạo file listStudent.txt và đọc file

    public static void readDataFromFile(List<Student> studentList) {
        File file = new File("listStudent.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            if (ois.readObject() != null) {
                studentList = (List<Student>) ois.readObject();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Không tồn tại file");
        } catch (IOException e) {
            System.err.println("Lỗi IO");
        } catch (ClassNotFoundException e) {
            System.err.println("Lớp không tồn tại");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.err.println("Lỗi runtime");
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.err.println("Lỗi IO");
                }
            }
        }
    }
    //
    public static void writeDataToFile(List<Student> studentList) {
        File file = new File("listStudent.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(studentList);
            oos.flush();
        } catch (FileNotFoundException e) {
            System.err.println("Lỗi runtime");
        } catch (IOException e) {
            System.err.println("Lỗi IO");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.err.println("Lỗi runtime");
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.err.println("Lỗi IO");
                }
            }
        }

    }
    public static void addListStudent(){
        System.out.println("nhập số lượng sinh viên :");
        do {
            try {
                int n = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < n; i++) {
                    Student student = new Student();
                    student.inputData(scanner,studentList);
                    studentList.add(student);
                }
                break;
            } catch (NumberFormatException ex1){
                System.err.println("Lỗi đinh dạng!");
            } catch (Exception e){
                System.err.println("Xảy ra lỗi. Vui lòng liên hệ với hệ thống!");
            }
        } while (true);
    }
    public static void calListAge(){
        for (Student student: studentList) {
            student.calAge();
        }
        System.out.println("Đã tính xong tuổi cho tất cả sinh viên.");
    }
    public  static void calListAvgAndRank(){
        for (Student st :studentList) {
            st.calAVgMark_Rank();
        }
        System.out.println("Đã tính xong điểm trung bình và xếp loại cho tất cả sinh viên.");
    }
    public static void sortListStudentASC(){
//        Collections.sort(studentList, new Comparator<Student>() {
//            @Override
//            public int compare(Student o1, Student o2) {
//                return o1.getAge() - o2.getAge();
//            }
//        });
        studentList.stream().sorted(Comparator.comparing(Student::getAge)).forEach(Student::displayData);
    }
    public static void staticListStudent(){
        int cntYeu, cntKha, cntTB, cntGioi,cntXS;
        cntYeu = (int)studentList.stream().filter(student -> student.getRank().equals("Yếu")).count();
        cntTB = (int)studentList.stream().filter(student -> student.getRank().equals("Trung bình")).count();
        cntKha = (int)studentList.stream().filter(student -> student.getRank().equals("Khá")).count();
        cntGioi = (int)studentList.stream().filter(student -> student.getRank().equals("Giỏi")).count();
        cntXS = (int)studentList.stream().filter(student -> student.getRank().equals("Xuất sắc")).count();
        System.out.printf("Thống kê: Xuất sắc: %d - Giỏi: %d - Khá: %d - Trung bình: %d - Yếu: %d\n", cntXS, cntGioi, cntKha, cntTB, cntYeu);
    }
    public static void updateStudent(Scanner scanner) {
        System.out.println("Nhập vào mã sinh viên cần cập nhật:");
        String studentId = scanner.nextLine();
        int index = -1;
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId().equals(studentId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            //Cập nhật thông tin mới cho sinh viên
            studentList.get(index).setStudentName(Student.validateStudentName(scanner));
            studentList.get(index).setSex(Student.validateSex(scanner));
            studentList.get(index).setBrithday(Student.validateBirthday(scanner));
            studentList.get(index).setMark_html(Student.validateHtml(scanner));
            studentList.get(index).setMark_css(Student.validateCss(scanner));
            studentList.get(index).setMark_javascript(Student.validateJavascript(scanner));
            studentList.get(index).calAge();
            studentList.get(index).calAVgMark_Rank();

        } else {
            System.out.println("Không tồn tại mã sinh viên như vậy");
        }
    }

    public static void searchStudentByName() {
        System.out.println("Nhập vào tên sinh viên cần tìm: ");
        boolean isNotExist = false;
        do {
            String studentName = scanner.nextLine();
            boolean isExist = studentList.stream().anyMatch(student ->
                    student.getStudentName().toLowerCase().contains(studentName.toLowerCase()));
            studentList.stream().filter(student ->
                    student.getStudentName().toLowerCase().contains(studentName.toLowerCase())).forEach(Student::displayData);
            if (!isExist){
                System.err.println("tên sinh viên cần tìm không tồn tại. Vui lòng nhập lại!");
            } else{
               isNotExist = true;
            }
        } while (!isNotExist);
    }
}