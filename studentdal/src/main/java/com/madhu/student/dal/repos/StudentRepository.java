package com.madhu.student.dal.repos;

import org.springframework.data.repository.CrudRepository;

import com.madhu.student.dal.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
