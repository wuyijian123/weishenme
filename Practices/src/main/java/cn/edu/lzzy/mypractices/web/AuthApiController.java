package cn.edu.lzzy.mypractices.web;

import com.alibaba.fastjson.JSONObject;
import cn.edu.lzzy.mypractices.constant.ApiConstant;
import cn.edu.lzzy.mypractices.constant.Messages;
import cn.edu.lzzy.mypractices.entity.User;
import cn.edu.lzzy.mypractices.service.UserService;
import cn.edu.lzzy.mypractices.util.*;
import cn.edu.lzzy.mypractices.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author lzzy on 2022/11/10.
 * Description:
 */
//@Api(tags = "用户认证")
//@Name(name = "身份认证控制器")
@CrossOrigin
@RestController
@RequestMapping(ApiConstant.ROUTE_AUTH_ROOT)
public class AuthApiController {
    private final UserService service;
 //文件上传业务
//    @Value("${user.pic}")
//    private String imgPath;

    @Autowired
    public AuthApiController(UserService service){
        this.service = service;
    }

//    @ApiOperation("用户登录")
//    @Name(name = "登录")
    @PostMapping(value = ApiConstant.ROUTE_AUTH_LOGIN, produces = ApiConstant.API_PRODUCES)
    public ApiResult login(@RequestBody JSONObject json) {
        //用户名提取
        String userName = json.getString(ApiConstant.KEY_LOGIN_USER_NAME);
        //根据用户名，提取盐
        String salt = service.getSalt(userName);
        //校验用户
        User user = service.check(userName,
                StringUtils.md5Encode(json.getString(ApiConstant.KEY_LOGIN_PASSWORD), salt));
        //校验失败返回错误提示
        if (user == null) {
            return new ApiResult(false, Messages.WRONG_PASSWORD.toString(), null);
        }
        //如果用户被禁用，返回提示
        if (user.getType() < 0){
            return new ApiResult(false, Messages.USER_FORBIDDEN.toString(), null);
        }
        //将User封装为vmUser,记录到服务器内存。
        VmUser vmUser = VmUser.apiLogin(user);
        //封装返回结果
        return new ApiResult(true, "登录成功", vmUser);
    }

//    @ApiOperation("用户注销")
//    @Name(name = "注销")
    @PostMapping(ApiConstant.ROUTE_AUTH_LOGOUT)
    public ApiResult logout(@RequestParam String token){
        return new ApiResult(true, AuthUtils.logout(token), null);
    }

//    @ApiOperation("查询用户是否存在")
//    @Name(name = "查询用户存在")
    @GetMapping(ApiConstant.ROUTE_AUTH_EXISTS)
    public ApiResult isUserExists(@PathVariable String user){
        boolean exists = service.isUserNameOccupied(user);
        return new ApiResult(true, exists ? Messages.USER_EXISTS.toString() : "", exists);
    }

//    @ApiOperation("注册新用户")
//    @Name(name = "注册用户")
    @PostMapping(value = ApiConstant.ROUTE_AUTH_REGISTER, produces = ApiConstant.API_PRODUCES)
    public ApiResult register(@RequestBody JSONObject json){
        String nick = json.getString(ApiConstant.KEY_REGISTER_NICK_NAME);
        String user = json.getString(ApiConstant.KEY_REGISTER_USER_NAME);
        String phone = json.getString(ApiConstant.KEY_REGISTER_PHONE);
        String email = json.getString(ApiConstant.KEY_REGISTER_EMAIL);
        String avatar = json.getString(ApiConstant.KEY_REGISTER_AVATAR);
        String password = json.getString(ApiConstant.KEY_REGISTER_PASSWORD);
        boolean applyTeacher = json.getBoolean(ApiConstant.KEY_REGISTER_APPLY_TEACHER);

        //用随机UUI设置加密salt
        String salt = UUID.randomUUID().toString();
       //如果昵称没有，填用户名
        if (StringUtils.isEmpty(nick)){
            nick = user;
        }
        //判属性是否为空，如果有一个为空，返回错误信息。
        if (StringUtils.isEmpty(nick) ||
                StringUtils.isEmpty(avatar) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(email)) {
            return new ApiResult(false, Messages.INCOMPLETE_INFO.toString(), null);
        }
        //校验邮箱
        if (!StringUtils.validMail(email)){
            return new ApiResult(false, Messages.INVALID_FORMAT.toString(), null);
        }
        //校验电话号码
        if (!StringUtils.validPhone(phone)){
            return new ApiResult(false, Messages.INVALID_FORMAT.toString(), null);
        }
        //校验用户是否唯一
        if (service.isUserNameOccupied(user)) {
            return new ApiResult(false,
                    Messages.USER_EXISTS.toString(), null);
        }

        //校验成功，存入数据库
        User usr = new User();
        usr.setAvatar(avatar);
        usr.setApplyTeacher(applyTeacher);
        usr.setEmail(email);
        usr.setUserName(user);
        usr.setNickName(nick);
        //密码加盐加密
        usr.setPassword(StringUtils.md5Encode(password, salt));
        usr.setPhone(phone);
        //默认用户是学生用户
        usr.setType(2);
        //存放盐
        usr.setSalt(salt);
        //存入数据库，ervice.register(usr)
        //VmUser.apiLogin服务器用户内存列表登记
        VmUser vmUser = VmUser.apiLogin(service.register(usr));
        //封装结果返回
        return new ApiResult(true, "注册成功", vmUser);
    }

////    @ApiOperation("上传文件")
////    @Name(name = "上传文件")
//    @PostMapping(ApiConstant.ROUTE_FILE_UPLOAD)
//    public ApiResult upload(@RequestParam(ApiConstant.PARAM_UPLOAD_FILE_NAME) MultipartFile file) {
//        JSONObject result = FileUtils.uploadWithRandomName(file, imgPath);
//        return new ApiResult(result.getBoolean(FileUtils.UPLOAD_PARAM_STATUS),
//                result.getString(FileUtils.UPLOAD_PARAM_MESSAGE),
//                result.getString(FileUtils.UPLOAD_PARAM_URL));
//    }
}
