package io.renren.modules.sys.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cla")
public class Cla {
    @TableId
    private long id;
    private String name;
    private String classNum;
    private String userNums;

}


