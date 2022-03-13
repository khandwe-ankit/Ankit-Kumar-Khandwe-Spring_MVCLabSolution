package com.demo.college.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.college.model.Student;
import com.demo.college.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping("/list")
	public String fetchAllStudents(Model model) {
		List<Student> students = studentService.fetchAllstudentsFromDb();
		model.addAttribute("Students", students);
		return "student-list";
	}

	@RequestMapping("/add")
	public String addStudent(Model model) {

		// create model attribute to bind form data
		Student student = new Student();

		model.addAttribute("Student", student);

		return "college-form";
	}

	@RequestMapping("/save")
	public String saveStudent(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("country") String country) {

		// create model attribute to bind form data
		Student student;
		if (id != 0) {
			student = studentService.findById(id);
			student.setName(name);
			student.setDepartment(department);
			student.setCountry(country);
		} else
			student = new Student(name, department, country);
		studentService.saveStudentDetails(student);

		return "redirect:/students/list";
	}

	@RequestMapping("/update")
	public String updateStudentDetails(@RequestParam("id") int id, Model model) {

		Student student = studentService.findById(id);
		model.addAttribute("Student", student);

		return "college-form";
	}

	@RequestMapping("/delete")
	public String deleteStudentDetails(@RequestParam("id") int id) {

		Student student = studentService.findById(id);
		studentService.deleteStudentdetails(student);

		return "redirect:/students/list";
	}

	@RequestMapping("/search")
	public String searchStudentDetails(@RequestParam("name") String name, @RequestParam("department") String department,
			@RequestParam("country") String country, Model model) {
		if (name.trim().isEmpty() && department.trim().isEmpty() && country.trim().isEmpty()) {
			return "redirect:/students/list";
		} else {
			List<Student> students = studentService.searchStudentsInDB(name, department, country);
			Student searchedStudentData = new Student(name, department, country);
			model.addAttribute("searchedStudentData", searchedStudentData);
			model.addAttribute("Students", students);
			return "student-list";
		}

	}
}
