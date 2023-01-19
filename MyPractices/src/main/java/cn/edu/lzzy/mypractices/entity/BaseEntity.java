package cn.edu.lzzy.mypractices.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;
//映射为父类，该类不会映射到数据库表，但是其属性都将映射到其子类的数据库字段中。
@MappedSuperclass
public class BaseEntity {
    @Id
    //使用UUID2策略生成ID
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    //设置该列的数据库类型为16字节二进制字符串
    @Column(columnDefinition = "BINARY(16)")
    protected UUID id;

    protected BaseEntity() {
    }

    public UUID getId() {
        return this.id;
    }

    /**
     * 这里要添加setter，因为如果要在前端传id到后端的时候，
     * 需要set方法，否则得不到正确的id，比如发布文章时传递类别id
     **/
    public void setId(UUID id) {
        this.id = id;
    }
}
