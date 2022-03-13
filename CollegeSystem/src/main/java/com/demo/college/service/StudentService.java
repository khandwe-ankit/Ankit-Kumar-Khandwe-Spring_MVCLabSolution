package com.demo.college.service;

import java.util.List;

import com.demo.college.model.Student;

public interface StudentService {

    List<Student> fetchAllstudentsFromDb();

    Student findById(int id);

    void saveStudentDetails(Student student);

    void deleteStudentdetails(Student student);

    List<Student> searchStudentsInDB(String name, String department, String country);

}
