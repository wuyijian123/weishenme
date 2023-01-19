package cn.edu.lzzy.mypractices.web.model;

import cn.edu.lzzy.mypractices.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * @author lzzy on 2022/11/10.
 * Description:封装控制器返回的消息
 */
public class ApiResult {
    private final boolean success;
    private final String message;
    private final Object data;

    public ApiResult(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    /**
     * @author lzzy on 2021/11/19.
     * Description:
     */
    @Getter
    @Setter
    public static class VmCourse {
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
}
