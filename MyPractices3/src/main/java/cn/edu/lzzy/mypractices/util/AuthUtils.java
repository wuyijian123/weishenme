package cn.edu.lzzy.mypractices.util;

import cn.edu.lzzy.mypractices.entity.User;
import cn.edu.lzzy.mypractices.web.model.VmUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author lzzy on 2022/11/10.
 * Description:自定义安全机制工具
 * 使用@Component 注解配置为组件，到可在其读取配置文件
 */
@Component
public class AuthUtils {
    //在内存中保存登录用户列表
    public static final List<VmUser> USERS = new ArrayList<>();

    //以token验证用户是否合法
    public static boolean illegalUser(String token, int[] allowedRoles) {
        VmUser vmUser = USERS.stream()
                .filter(user -> user.getToken().equals(token))
                .findAny()
                .orElse(null);
        if (vmUser == null || isOverTime(vmUser)) {
            return true;
        }
        vmUser.setLastLogin(new Date());
        return Arrays.stream(allowedRoles).noneMatch(r -> r == vmUser.getType());
    }

    //以token获取登录用户信息
    public static VmUser getUser(String token) {
        return USERS.stream()
                .filter(user -> user.getToken().equals(token))
                .findAny()
                .orElse(null);
    }

    /**
     * 验证执行操作的是否本人，防止用户id泄露造成的风险
     * @param userId 通过接口发送的操作用户id
     * @param token 通过接口发送的实际用户id
     * @return 二者是否同一个用户
     */
    public static boolean invalid(UUID userId, String token){
        VmUser user = getUser(token);
        if (user == null || isOverTime(user)){
            return true;
        }
        boolean valid = user.getId().equals(userId);
        if (valid){
            user.setLastLogin(new Date());
        }
        return !valid;
    }

    //退出：移除用户
    public static String logout(String token){
        VmUser vmUser = USERS.stream()
                .filter(user -> user.getToken().equals(token))
                .findAny()
                .orElse(null);
        if (vmUser == null){
            return "注销失败，未发现登录记录";
        }
        USERS.remove(vmUser);
        return "注销成功！";
    }

    /**
     * 静态字段不同于实例字段，无法直接使用Value注入
     * 必须在Component注解的类中使用setter，且对setter进行Value注解
     */
    private static int minutes;
    //获取application.yml中定义的过期时间
    @Value("${app.over.minutes}")
    public void setMinutes(int minutes) {
        AuthUtils.minutes = minutes;
    }

    //计算当前用户是否过期
    private static boolean isOverTime(VmUser user) {
        long offMillis = System.currentTimeMillis() - user.getLastLogin().getTime();
        long minutes = offMillis / (1000 * 60);
        boolean over = minutes > AuthUtils.minutes;
        if (over) {
            USERS.remove(user);
        }
        return over;
    }

    //根据整数值获取角色列表
    public static List<Integer> getRoles(int value){
        List<Integer> result = new ArrayList<>();
        String strBinary = StringUtils.padLeft(Integer.toBinaryString(value), User.COUNT_TYPE, '0');
        int r = 0;
        for(char b : strBinary.toCharArray()){
            if (b == '1'){
                result.add(r);
            }
            r++;
        }
        return result;
    }
    //根据二进制字符串获取角色值
    public static int getAuthValue(String strBinary){
        return Integer.valueOf(strBinary, 2);
    }

    //获取默认的角色值
    public static int getDefaultAuthValue(){
        String strBinary = StringUtils.padRight("", User.COUNT_TYPE, '1');
        return Integer.valueOf(strBinary, 2);
    }
}
