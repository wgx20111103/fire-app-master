package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("teacher")
public class Teacher {
    @TableId
    private long id;
    private String name;
    private String sex;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthdate;
    private String education;
    private String position;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date ruzhiDate;
    private String college;
    private String bz;


}


