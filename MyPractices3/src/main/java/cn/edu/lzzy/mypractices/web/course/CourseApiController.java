package cn.edu.lzzy.mypractices.web.course;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.edu.lzzy.mypractices.constant.ApiConstant;
import cn.edu.lzzy.mypractices.constant.Messages;
import cn.edu.lzzy.mypractices.entity.Course;
import cn.edu.lzzy.mypractices.entity.User;

import cn.edu.lzzy.mypractices.service.CourseService;
import cn.edu.lzzy.mypractices.service.UserService;
import cn.edu.lzzy.mypractices.util.AuthUtils;
//import cn.edu.lzzy.mypractices.util.Name;
import cn.edu.lzzy.mypractices.util.StringUtils;
import cn.edu.lzzy.mypractices.web.model.ApiResult;
import cn.edu.lzzy.mypractices.web.model.VmCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author lzzy  2021/11/18.
 * Description:
 */
@Api(tags = "课程相关业务")
@CrossOrigin
//@Name(name = "课程控制器")
@RestController
@RequestMapping(ApiConstant.ROUTE_COURSE_ROOT)
public class CourseApiController {
    //课程业务
    private final CourseService service;
    //用户业务
    private final UserService uService;
//    private final AllocationService aService;

    //构造函数依赖注入课程业务、用户业务
    @Autowired
    public CourseApiController(CourseService service, UserService uService){
        this.service = service;
        this.uService = uService;
//        this.aService = aService;
    }

    //swagger方法级别的说明
    @ApiOperation("查询所有课程")
//    @Name(name = "查询所有课程")
    @GetMapping(ApiConstant.ROUTE_COURSE_ALL)
    public ApiResult getCourses(@RequestParam String token){
        //创建vmcourse列表，一开始是空的
        List<ApiResult.VmCourse> courses = new ArrayList<>();
        //service.get()查询所有的课程--数据库的course
        //forEach（）对每个课程处理 -数据库的course
        //course -> courses.add（）   对数据库的course 都添加到VMcourse lIST
        //ApiResult.VmCourse.create(course, false) 数据库course->一个不是老师形式下的vmcourse,
        service.get().forEach(course -> courses.add(ApiResult.VmCourse.create(course, false)));

        //封装结果返回
        return new ApiResult(true, courses.size() + "", courses);
    }

    @ApiOperation("分页查询所有课程")
//    @Name(name = "分页查询所有课程")
    @GetMapping(ApiConstant.ROUTE_COURSE_PAGE)
    public ApiResult getPagedCourses(@RequestParam String token, @RequestParam int page, @RequestParam int size){
        List<ApiResult.VmCourse> courses = new ArrayList<>();
        service.get(page, size).forEach(course -> courses.add(ApiResult.VmCourse.create(course, false)));
        return new ApiResult(true, service.count() + "", courses);
    }

    @ApiOperation("获取某教师自己的课程")
    @GetMapping(ApiConstant.ROUTE_COURSE_TEACHER)
    public ApiResult getTeachersCourses(@RequestParam String token, @RequestParam(name = "id") UUID teacherId){
        List<ApiResult.VmCourse> courses = new ArrayList<>();
        service.getTeachersCourses(teacherId).forEach(course -> courses.add(ApiResult.VmCourse.create(course, true)));
        return new ApiResult(true, courses.size() + "", courses);
    }

    @ApiOperation("获取某学生注册的课程")
    @GetMapping(ApiConstant.ROUTE_COURSE_STUDENT)
    public ApiResult getStudentsCourses(@RequestParam String token, @RequestParam(name = "id") UUID studentId){
        List<ApiResult.VmCourse> courses = new ArrayList<>();
        service.getStudentsCourses(studentId).forEach(course -> courses.add(ApiResult.VmCourse.create(course, false)));
        return new ApiResult(true, courses.size() + "", courses);
    }

    private ApiResult addOrUpdate(UUID id, JSONObject json){
        //获取json字符串，提取每个属性的值
        String token = json.getString(ApiConstant.KEY_TOKEN);
        String name = json.getString(ApiConstant.KEY_COURSE_NAME);
        String desc = json.getString(ApiConstant.KEY_COURSE_DESC);
        String cover = json.getString(ApiConstant.KEY_COURSE_COVER);
        boolean open = json.getBoolean(ApiConstant.KEY_COURSE_OPEN);
        String teacherId = json.getString(ApiConstant.KEY_COURSE_TEACHER_ID);
        //校验每个属性是否为空 ，否则返回错误消息
        if (StringUtils.isEmpty(name) ||
                StringUtils.isEmpty(desc) ||
                StringUtils.isEmpty(cover) ||
                StringUtils.isEmpty(teacherId)){
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        //通过老师ID查询数据库，结果存入teacher
        User teacher = uService.getById(UUID.fromString(teacherId));
        //teacher如果为空，返回错误提示
        if (teacher == null){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
        //创建课程变量
        Course course;
        //新增和更新一起在这个方法中做，新增是没有id+需要设置教师，更新是指定ID课程找出来
        boolean create = id == null;
        if (create){
            //新增
            course = new Course();
            //设置老师
            course.setTeacher(teacher);
        } else {
            //更新
            //根据id获取数据库的指定课程
            course = service.getById(id);
            //课程如果为空则封装错误消息返回
            if (course == null){
                return new ApiResult(false, Messages.WRONG_ID.toString(), null);
            }
        }
        //设置以下属性值
        course.setOpen(open);
        course.setCover(cover);
        course.setName(name);
        course.setDescription(desc);

        //新增和更新分别调用不同的业务层函数
        if (create){
            return new ApiResult(true, "", ApiResult.VmCourse.create(service.add(course), true));
        } else {
            Course updated = service.update(course, token);
            if (updated == null){
                return new ApiResult(false, Messages.NO_PERMISSION.toString(), null);
            }
            return new ApiResult(true, "", ApiResult.VmCourse.create(service.update(course, token), true));
        }
    }

    @ApiOperation("添加新课程")
//    @Name(name = "添加新课程")
    @PostMapping(value = ApiConstant.ROUTE_COURSE_ADD, produces = ApiConstant.API_PRODUCES)
    public ApiResult add(@RequestBody JSONObject json){
        return addOrUpdate(null, json);
    }

    @ApiOperation("修改课程信息")
//    @Name(name = "修改课程信息")
    @PutMapping(value = ApiConstant.ROUTE_COURSE_PUT, produces = ApiConstant.API_PRODUCES)
    public ApiResult update(@PathVariable UUID id, @RequestBody JSONObject json){
        String token = json.getString(ApiConstant.KEY_TOKEN);
////        Allocation allocation = aService.findAllocation("/api/v1/course/put/{id}");
////        int[] roles = AuthUtils.getRoles(allocation.getRoles()).stream().mapToInt(r -> r).toArray();
////        if (AuthUtils.illegalUser(token, roles)){
//            return new ApiResult(false, Messages.NO_PERMISSION.toString(), "limit from custom access control");
//        }
        return addOrUpdate(id, json);
    }

    @ApiOperation("删除课程")
   // @Name(name = "删除课程")
    @DeleteMapping(ApiConstant.ROUTE_COURSE_REMOVE)
    public ApiResult delete(@RequestBody JSONObject json){
        String token = json.getString(ApiConstant.KEY_TOKEN);
        String strCourseId = json.getString(ApiConstant.KEY_COURSE_ID);
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(strCourseId)) {
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        try {
            UUID courseId = UUID.fromString(strCourseId);
            Messages msg = service.remove(courseId, token);
            return new ApiResult(msg == Messages.SUCCESS, msg.toString(), null);
        } catch (Exception e) {
            return new ApiResult(false, Messages.WRONG_ID.toString(), e.getMessage());
        }
    }
}
