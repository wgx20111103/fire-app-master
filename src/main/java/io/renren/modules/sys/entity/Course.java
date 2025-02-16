package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("course")
public class Course {
    @TableId
    private long id;
    private String name;
    private String teacherNum;
    private Date skTime;
    private String skPlace;
    private String weeksNumber;
    private String kcType;
    private String faculty;
    private String score;
    private String bz;

}


