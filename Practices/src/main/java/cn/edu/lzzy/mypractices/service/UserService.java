package cn.edu.lzzy.mypractices.service;

import cn.edu.lzzy.mypractices.entity.User;

import java.util.List;
import java.util.UUID;


public interface UserService {
    User check(String userName, String password);

    boolean isUserNameOccupied(String userName);

    User register(User user);

    User getById(UUID id);

    String getSalt(String userName);


    List<User> get();

    List<User> get(int page, int size);

    Integer count();

    List<User> search(String kw);


    List<User> getByApplyTeacher(boolean applying);

    User approveTeacher(UUID id);

    User declineTeacher(UUID id);

    User banUser(UUID id);

}
