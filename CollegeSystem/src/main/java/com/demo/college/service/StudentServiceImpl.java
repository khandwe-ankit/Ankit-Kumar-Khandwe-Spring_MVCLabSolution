package com.demo.college.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.college.model.Student;

@Repository
public class StudentServiceImpl implements StudentService {

//    private SessionFactory sessionfactory;
    private Session session;

    @Autowired
    public StudentServiceImpl(SessionFactory sessionfactory) {
//	this.sessionfactory = sessionfactory;
	try {
	    session = sessionfactory.getCurrentSession();
	} catch (HibernateException e) {
	    session = sessionfactory.openSession();
	}
    }

    @Transactional
    public List<Student> fetchAllstudentsFromDb() {
	Transaction tx = session.beginTransaction();
	List<Student> studentsList = session.createQuery("from Student").list();
	tx.commit();
	return studentsList;
    }

    @Transactional
    public Student findById(int id) {
	Transaction tx = session.beginTransaction();
	Student student = session.get(Student.class, id);
	tx.commit();
	return student;
    }

    @Transactional
    public void saveStudentDetails(Student student) {
	Transaction tx = session.beginTransaction();
	session.saveOrUpdate(student);
	tx.commit();

    }

    @Transactional
    public void deleteStudentdetails(Student student) {
	Transaction tx = session.beginTransaction();
	session.delete(student);
	tx.commit();

    }

    @Transactional
    public List<Student> searchStudentsInDB(String name, String department, String country) {
	String query = "";

	if (name.length() != 0 && department.length() != 0 && country.length() != 0)
	    query = "from Customer where name like'%" + name + "%' or department like'%" + department
		    + "%' or country like'%" + country + "%'";
	else if (name.length() != 0 && department.length() != 0)
	    query = "from Student where name like'%" + name + "%' or department like'%" + department + "%'";
	else if (name.length() != 0 && country.length() != 0)
	    query = "from Student where name like'%" + name + "%' or country like'%" + country + "%'";
	else if (department.length() != 0 && country.length() != 0)
	    query = "from Student where department like'%" + department + "%' or country like'%" + country + "%'";
	else if (name.length() != 0)
	    query = "from Student where name like'%" + name + "%'";
	else if (department.length() != 0)
	    query = "from Student where department like'%" + department + "%'";
	else
	    query = "from Student where country like'%" + country + "%'";
	Transaction tx = session.beginTransaction();
	List<Student> students = session.createQuery(query).list();
	tx.commit();
	return students;
    }

}
