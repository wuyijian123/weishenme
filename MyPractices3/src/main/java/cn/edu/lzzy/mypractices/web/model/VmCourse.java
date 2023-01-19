package cn.edu.lzzy.mypractices.web.model;

import lombok.Getter;
import lombok.Setter;
import cn.edu.lzzy.mypractices.entity.Course;

import java.util.Date;
import java.util.UUID;

/**
 * @author lzzy_gxy on 2021/11/19.
 * Description:
 */
@Getter
@Setter
public class VmCourse {
    private UUID id;
    private String name;
    private String desc;
    private String cover;
    private Date createTime;
    private Date updateTime;
    private boolean open;
    private UUID teacherId;
    private String teacherName;
    private String teacherAvatar;
    private int countStudents;
    private int countChapters;

    private VmCourse(){}

    public static VmCourse create(Course course, boolean teacher){
        VmCourse vm = new VmCourse();
        vm.setId(course.getId());
        vm.setName(course.getName());
        vm.setDesc(course.getDescription());
        vm.setCover(course.getCover());
        vm.setCreateTime(course.getCreateTime());
        vm.setUpdateTime(course.getUpdateTime());
        vm.setOpen(course.isOpen());
        vm.setTeacherAvatar(course.getTeacher().getAvatar());
        vm.setTeacherName(course.getTeacher().getNickName());
        vm.setCountStudents(course.getStudents().size());
        if (teacher){
            vm.setTeacherId(course.getTeacher().getId());
//            vm.setCountChapters(course.getChapters().size());
        } else {
//            vm.setCountChapters((int) course.getChapters().stream().filter(Chapter::isOpen).count());
        }
        return vm;
    }
}
