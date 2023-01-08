package cn.edu.lzzy.mypractices.service;

import cn.edu.lzzy.mypractices.entity.User;
import cn.edu.lzzy.mypractices.repository.UserProjection;
import cn.edu.lzzy.mypractices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository){
        this.repository = repository;
    }


    @Override
    public User check(String userName, String password) {
        return repository.findUserByUserNameAndPassword(userName, password);
    }


    @Override
    public boolean isUserNameOccupied(String userName) {
        return repository.countByUserName(userName) > 0;
    }


    @Override
    public User register(User user) {
        return repository.save(user);
    }


    @Override
    public String getSalt(String userName) {

        List<UserProjection> users = repository.findUsersByUserName(userName);

        if (users == null || users.size() == 0){
            return "";
        }

        UserProjection user = users.get(0);
        if (user == null){
            return "";
        }
        String salt = user.getSalt();

        return salt == null ? "" : salt;
    }

    @Override
    public List<User> get() {
        return repository.findAllByOrderByUpdateTimeDesc();
    }

    @Override
    public List<User> get(int page, int size) {
        return repository.findAllByOrderByUpdateTimeDesc(PageRequest.of(page, size));
    }

    @Override
    public Integer count() {
        return repository.countAllBy();
    }

    @Override
    public List<User> search(String kw) {
        return repository.findByQuery(kw);
    }

    @Override
    public User getById(UUID id) {
        return repository.findById(id).orElse(null);
    }



    @Override
    public List<User> getByApplyTeacher(boolean applying) {
        return repository.findUsersByApplyTeacherOrderByUpdateTimeDesc(applying);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User approveTeacher(UUID id) {

        User user = repository.findById(id).orElse(null);
        if (user != null){
            user.setApplyTeacher(false);
            user.setType(User.TYPE_TEACHER);
            return repository.save(user);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User declineTeacher(UUID id) {
        User user = repository.findById(id).orElse(null);
        if (user != null){
            user.setApplyTeacher(false);
            return repository.save(user);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User banUser(UUID id) {
        User user = repository.findById(id).orElse(null);
        if (user != null && user.getType() != User.TYPE_ADMIN){
            user.setType(User.TYPE_BANNED);
            return repository.save(user);
        }
        return null;
    }

}
