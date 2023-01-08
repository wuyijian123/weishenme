package cn.edu.lzzy.mypractices.web.admin;
import cn.edu.lzzy.mypractices.constant.ApiConstant;
import cn.edu.lzzy.mypractices.constant.Messages;
import cn.edu.lzzy.mypractices.entity.User;
import cn.edu.lzzy.mypractices.service.UserService;
import cn.edu.lzzy.mypractices.util.*;
import cn.edu.lzzy.mypractices.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author lzzy on 2022/11/14.
 * Description:
 */
//@Api(tags = "系统管理员业务")
@CrossOrigin
@RestController
@RequestMapping(ApiConstant.ROUTE_USER_ROOT)
public class UserApiController {
    private final UserService service;

    @Autowired
    public UserApiController(UserService service){
        this.service = service;
    }

//    @ApiOperation("查询所有用户")
    @GetMapping(ApiConstant.ROUTE_USERS_ALL)
    public ApiResult getUsers(@RequestParam String token){
        List<VmUser> users = new ArrayList<>();
        service.get().forEach(user -> users.add(VmUser.create(user, true)));
        return new ApiResult(true, users.size() + "", users);
    }

//    @ApiOperation("分页查询所有用户")
    @GetMapping(ApiConstant.ROUTE_USERS_PAGE)
    public ApiResult getPagedUsers(@RequestParam int page, @RequestParam String token, @RequestParam int size){
        List<VmUser> users = new ArrayList<>();
        service.get(page, size).forEach(user -> users.add(VmUser.create(user, true)));
        return new ApiResult(true, service.count() + "", users);
    }

//    @ApiOperation("根据关键词查询用户")
    @GetMapping(ApiConstant.ROUTE_USERS_SEARCH)
    public ApiResult search(@RequestParam String token, @PathVariable String kw) {
        List<VmUser> users = new ArrayList<>();
        service.search(kw).forEach(user -> users.add(VmUser.create(user, true)));
        return new ApiResult(true, users.size() + "", users);
    }

//    @ApiOperation("查看正在申请教师的用户")
    @GetMapping(ApiConstant.ROUTE_USERS_APPLYING)
    public ApiResult getApplyingTeacher(@RequestParam String token){
        List<VmUser> users = new ArrayList<>();
        service.getByApplyTeacher(true).forEach(user -> users.add(VmUser.create(user, true)));
        return new ApiResult(true, users.size() + "", users);
    }

//    @ApiOperation("审批用户申请")
    @PostMapping(ApiConstant.ROUTE_USERS_APPROVE)
    public ApiResult approve(@RequestParam String token, @RequestParam UUID id){
        User user = service.approveTeacher(id);
        if (user == null){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
        return new ApiResult(true, "", VmUser.create(user, true));
    }

    //@ApiIgnore
//    @ApiOperation("拒绝用户申请")
    @PostMapping(ApiConstant.ROUTE_USERS_DECLINE)
    public ApiResult decline(@RequestParam String token, @RequestParam UUID id){
        User user = service.declineTeacher(id);
        if (user == null){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
        return new ApiResult(true, "", VmUser.create(user, true));
    }

//    @ApiOperation("禁止用户访问")
    @PostMapping(ApiConstant.ROUTE_USERS_BAN)
    public ApiResult ban(@RequestParam String token, @RequestParam UUID id){
        User user = service.banUser(id);
        if (user == null){
            return new ApiResult(false, Messages.WRONG_ID.toString(), null);
        }
        return new ApiResult(true, "", VmUser.create(user, true));
    }
}
