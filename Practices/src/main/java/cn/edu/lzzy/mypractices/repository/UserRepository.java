package cn.edu.lzzy.mypractices.repository;

import cn.edu.lzzy.mypractices.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByUserNameAndPassword(String userName, String password);
    Integer countByUserName(String userName);
    Integer countAllBy();


    List<User> findAllByOrderByUpdateTimeDesc();
    List<User> findAllByOrderByUpdateTimeDesc(Pageable pageable);
    List<User> findUsersByApplyTeacherOrderByUpdateTimeDesc(boolean applying);


    @Query("select u from User u where u.userName like CONCAT('%',:kw,'%') or u.nickName " +
            "like CONCAT('%',:kw,'%') or u.email like CONCAT('%',:kw,'%') or u.phone like CONCAT('%',:kw,'%')")
    List<User> findByQuery(@Param("kw") String kw);

    List<UserProjection> findUsersByUserName(String user);
}
