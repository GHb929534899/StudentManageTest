package org.wsd.chenjunyv.mapper;

import org.apache.ibatis.annotations.*;
import org.wsd.chenjunyv.entity.Student;

import java.util.List;

/**
 * @Mapper 标记这是一个操作数据库的mapper
 */
//@Mapper
public interface StudentMapper {

    @Insert("INSERT INTO student (name,score) VALUES(#{name},#{score})")
    public void insertStudent(Student student);

    @Delete("DELETE FROM student WHERE id=#{id}")
    public void deleteStudentById(int id);

    @Update("update student set name=#{name},score=#{score} where id=#{id}")
    public void updateStudent(Student student);

    @Select("select * from student where id=#{id}")
    public Student findStudentById(int id);

    @Select("SELECT * FROM student")
    public List<Student> getAllStudents();

    @Select("SELECT * FROM student WHERE id >= (SELECT id FROM student LIMIT #{startLine},1) LIMIT #{pageSize}")
    public List<Student> getPage(int startLine,int pageSize);
}
