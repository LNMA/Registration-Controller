package com.louay.registration.controller.registration;

import com.louay.projects.registration.model.dao.CourseDAO;
import com.louay.projects.registration.model.dao.ScheduleDAO;
import com.louay.projects.registration.model.dao.StudentDAO;
import com.louay.projects.registration.model.entity.Course;
import com.louay.projects.registration.model.entity.Schedule;
import com.louay.projects.registration.model.entity.Student;

import java.time.LocalDate;
import java.util.List;

public class RegistrationController {

    private ScheduleDAO scheduleDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private Student student;
    private Course course;
    private Schedule schedule;

    public RegistrationController(ScheduleDAO scheduleDAO, StudentDAO studentDAO, CourseDAO courseDAO, Student student,
                                  Course course, Schedule schedule) {
        this.scheduleDAO = scheduleDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.student = student;
        this.course = course;
        this.schedule = schedule;
    }

    public void register(){
        if (!isStudentExist()){
            throw new RuntimeException("student did not existing");
        }else {
            if (!isCourseExist()){
                throw new RuntimeException("course did not exist");
            }else {
                if (!isCourseStartingDateFine()){
                    throw new RuntimeException("you are register too late");
                }else {
                    if (!isCourseHaveFreeSeat()){
                        throw new RuntimeException("this course is closed now");
                    }
                    else {
                        this.scheduleDAO.create(this.schedule);
                    }
                }
            }
        }
    }

    public boolean isStudentExist(){
        Student studentLocal = this.studentDAO.findById(this.student.getId());
        return studentLocal != null;
    }

    public boolean isCourseExist(){
        Course courseLocal = this.courseDAO.findById(this.course.getId());
        return courseLocal != null;
    }

    public boolean isCourseStartingDateFine(){
        return this.course.getStartingDate().compareTo(java.sql.Date.valueOf(LocalDate.now())) > 0;
    }

    public boolean isCourseHaveFreeSeat() {
        List<Student> list = this.scheduleDAO.findByIDCourse(course.getId());
        return course.getCapacity() > list.size();
    }

}
