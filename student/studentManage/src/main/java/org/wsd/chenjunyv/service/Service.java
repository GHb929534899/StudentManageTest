package org.wsd.chenjunyv.service;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.wsd.chenjunyv.entity.Student;

import java.util.List;

/**
 * 业务逻辑定义（接口定义）
 */
public interface Service {

    public List<Student> getAllStudents();

    public Student findStudentById(int id);

    public Student insertStudent(Student student);

    public Student deleteStudentById(int id);

    public Student updateStudent(Student student);

    public List<Student> studentListPage(int startLine,int pageSize);
}
