package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("student")
public class Student {
    @TableId
    private long id;
    private String name;
    private String sex;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthDate;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date ruxueDate;
    private String college;
    private String bz;
    private String claName;
}

