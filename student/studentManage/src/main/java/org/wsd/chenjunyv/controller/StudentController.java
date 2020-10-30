package org.wsd.chenjunyv.controller;

import org.springframework.web.bind.annotation.*;
import org.wsd.chenjunyv.entity.ResponseData;
import org.wsd.chenjunyv.entity.Student;
import org.wsd.chenjunyv.service.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {

    //@Autowired
    @Resource
    private Service service;

    @GetMapping("/getAll")
    public ResponseData<?> getAllStudents(){
        List<Student> students = service.getAllStudents();
        if(students.size()>0) {
            return new ResponseData<>(200,"查询成功",students);
        }
        else {
            return new ResponseData<>(400,"数据库中未查询到学生",students);
        }
    }

    @GetMapping("/getFirst")
    public ResponseData<?> getFirstStudent(){
        List<Student> students = service.getAllStudents();
        if(students.size()>0) {
            return new ResponseData<>(200,"查询成功",students.get(0));
        }
        else {
            return new ResponseData<>(400,"数据库中未查询到学生",new Student());
        }
    }

    @PostMapping("/insert")
    public ResponseData<?> insertStudent(@RequestBody() Student student){
        Student responseStudent = service.insertStudent(student);
        if(responseStudent.getId() == 0){
            if(student.getName()==null){
                return new ResponseData<>(400,"添加失败，学生姓名不能为空",student);
            } else if(student.getName().equals("")) {
                return new ResponseData<>(400,"添加失败，学生姓名不能为空",student);
            } else if(student.getScore()<0) {
                return new ResponseData<>(400,"添加失败，学生分数不能为负数",student);
            } else if(student.getScore()>100) {
                return new ResponseData<>(400,"添加失败，学生分数最大值为100",student);
            }
            return new ResponseData<>(400,"添加失败，未知错误",student);
        } else {
            return new ResponseData<>(200,"添加成功",responseStudent);
        }
        /*if(student.getName()==null){
            return new Student();
        }
        else if (student.getName().equals("")){
            return new Student();
        }
        else {
            if (student.getScore() >= 0 && student.getScore() <= 100) {
                service.insertStudent(student);
                List<Student> students = service.getAllStudents();
                return students.get(students.size() - 1);
            } else {
                return new Student();
            }
        }*/
    }

    /**
     * 通过传入的 id 删除数据库中的指定条目，删除成功返回被删除学生对象，若指定 id 并不存在对应条目，则返回（new Student()）
     * @param id 数据库主键，学生标识 id
     * @return 返回已删除的 student 对象
     */
    @DeleteMapping("/delete")
    public ResponseData<?> deleteStudentById(Integer id){
        Student responseStudent = service.deleteStudentById(id);
        if(responseStudent.getId() == 0){
            if(id<=0){
                return new ResponseData<>(400,"删除失败，学生id应为正数",new Student(id));
            }
            return new ResponseData<>(400,"删除失败，数据库中没有该学生",new Student(id));
        } else {
            return new ResponseData<>(200,"删除成功",responseStudent);
        }
        /*if(id > 0) {
            Student deleteStudent = service.findStudentById(id);
            if (deleteStudent == null) {
                return new Student();
            } else {
                service.deleteStudentById(id);
                return deleteStudent;
            }
        } else {
            return new Student();
        }*/
    }

    /**
     * 通过 id 修改指定条目的数据，修改成功返回修改后的学生对象,若指定 id 对应学生对象不存在，则返回（new Student()）
     *
     * 首先判断 id 是否大于0（数值合法），然后判断字符串是否为null或""（new student()），最后判断score是否在[0,100]区间内（数值合法），修改成功后返回修改后的学生对象，否则返回（new student()）
     * @param student 要修改的学生对象
     * @return Student 修改后的学生对象
     */
    @PutMapping("/update")
    public ResponseData<?> updateStudent(@RequestBody() Student student){
        Student responseStudent = service.updateStudent(student);
        if(responseStudent.getId() == 0){
            if(student.getId()<=0){
                return new ResponseData<>(400,"更改失败，学生id应为正数",student);
            } else if(student.getName()==null){
                return new ResponseData<>(400,"更改失败，学生姓名不能为空",student);
            } else if(student.getName().equals("")) {
                return new ResponseData<>(400,"更改失败，学生姓名不能为空",student);
            } else if(student.getScore()<0) {
                return new ResponseData<>(400,"更改失败，学生分数不能为负数",student);
            } else if(student.getScore()>100) {
                return new ResponseData<>(400,"更改失败，学生分数最大值为100",student);
            }
            return new ResponseData<>(400,"更改失败，数据库中没有查询到该学生",student);
        } else {
            return new ResponseData<>(200,"更改成功",responseStudent);
        }
        /*if(student.getId() > 0) {
            if (service.findStudentById(student.getId()) == null) {
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
                        service.updateStudent(student);
                        return service.findStudentById(student.getId());
                    }
                    else {
                        return new Student();
                    }
                }
            }
        }
        else {
            return new Student();
        }*/
    }

    /**
     * 通过第几页和页面显示数据条数来访问数据库的数据并返回生成的 List 列表，若访问数据已经超过所有数据数量，则返回空List列表（逻辑行为），若参数不合法则返回一个空List列表
     * //舍弃->包含（new Student()）的List列表
     * @param currentPage 当前访问页数，（currentPage >= 1）
     * @param pageSize 单页数据量，（pageSize >= 1）
     * @return List<Student>
     */
    @GetMapping("/getPage")
    public ResponseData<?> studentListPage(int currentPage,int pageSize){
        List<Student> students = service.studentListPage(currentPage,pageSize);
        if(students.size()>0) {
            return new ResponseData<>(200,"查询成功",students);
        }
        else {
            return new ResponseData<>(400,"指定范围内未查询到学生",students);
        }
        /*if(currentPage>0&&pageSize>0) {
            return service.studentListPage((currentPage - 1) * pageSize, pageSize);
        }
        return new ArrayList<Student>() {};*/
    }
}
