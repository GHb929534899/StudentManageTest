package org.wsd.chenjunyv.service;

import org.springframework.web.bind.annotation.*;
import org.wsd.chenjunyv.entity.Student;
import org.wsd.chenjunyv.mapper.StudentMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口函数进行实现
 */
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    //@Autowired
    @Resource
    private StudentMapper studentMapper;

    /**
     * 获取所有数据库的学生，放在List列表中返回
     * @return List<Student>，该列表是不为 null 的，可通过长度（size）判断是否有成员
     */
    public List<Student> getAllStudents(){
        return studentMapper.getAllStudents();
    }

    /**
     * 获取getAll返回的列表，如果列表中有学生元素（size>0），输出第一个学生，否则输出一个新建的学生对象（new Student()）
     * @return Student，若返回对象的id=0，name=null，score=0，则说明该对象为（new Student()），即数据库中没有学生，下面注释将不再解释（new Student()）
     */
    /*public Student getFirstStudent(){
        List<Student> students = getAllStudents();
        if(students.size()>0) {
            return students.get(0);
        }
        else {
            return new Student();
        }
    }*/

    public Student findStudentById(int id){
        return studentMapper.findStudentById(id);
    }

    /**
     * 对传入的 Student 对象进行判断，如果 name==null 不允许添加，如果score范围不在[0,100]区间内，不允许添加
     * @param student 需要添加的学生对象
     * @return Student 若添加成功，返回添加后的 Student 对象，该对象是从数据库重新获取的，会带上新的 id 值，若添加失败，会返回（new Student()）
     */
    public Student insertStudent(@RequestBody() Student student){
        if(student.getName()==null){
            return new Student();
        }
        else if (student.getName().equals("")){
            return new Student();
        }
        else {
            if (student.getScore() >= 0 && student.getScore() <= 100) {
                studentMapper.insertStudent(student);
                List<Student> students = getAllStudents();
                return students.get(students.size() - 1);
            } else {
                return new Student();
            }
        }
    }

    /**
     * 通过传入的 id 删除数据库中的指定条目，删除成功返回被删除学生对象，若指定 id 并不存在对应条目，则返回（new Student()）
     * @param id 数据库主键，学生标识 id
     * @return 返回已删除的 student 对象
     */
    public Student deleteStudentById(int id){
        if(id > 0) {
            Student deleteStudent = studentMapper.findStudentById(id);
            if (deleteStudent == null) {
                return new Student();
            } else {
                studentMapper.deleteStudentById(id);
                return deleteStudent;
            }
        } else {
            return new Student();
        }
    }

    /**
     * 通过 id 修改指定条目的数据，修改成功返回修改后的学生对象,若指定 id 对应学生对象不存在，则返回（new Student()）
     *
     * 首先判断 id 是否大于0（数值合法），然后判断字符串是否为null或""（new student()），最后判断score是否在[0,100]区间内（数值合法），修改成功后返回修改后的学生对象，否则返回（new student()）
     * @param student 要修改的学生对象
     * @return Student 修改后的学生对象
     */
    public Student updateStudent(Student student){
        if(student.getId() > 0) {
            if (studentMapper.findStudentById(student.getId()) == null) {
                return new Student();
            }
            else {
                if(student.getName() == null){
                    return new Student();
                }
                else if (student.getName().equals("")){
                    return new Student();
                }
                else {
                    if(student.getScore() >= 0&&student.getScore() <= 100) {
                        studentMapper.updateStudent(student);
                        return studentMapper.findStudentById(student.getId());
                    }
                    else {
                        return new Student();
                    }
                }
            }
        }
        else {
            return new Student();
        }
    }

    /**
     * 通过第几页和页面显示数据条数来访问数据库的数据并返回生成的 List 列表，若访问数据已经超过所有数据数量，则返回空List列表（逻辑行为），若参数不合法则返回一个空List列表
     * //舍弃->包含（new Student()）的List列表
     * @param currentPage 当前访问页数，（currentPage >= 1）
     * @param pageSize 单页数据量，（pageSize >= 1）
     * @return List<Student>
     */
    public List<Student> studentListPage(int currentPage,int pageSize){
        if(currentPage>0&&pageSize>0) {
            return studentMapper.getPage((currentPage - 1) * pageSize, pageSize);
        }
        return new ArrayList<Student>() {};
    }
}
