package io.renren.common.annotation;


import java.util.HashMap;
import java.util.Map;

/**
 * @author suntao
 * @description 对统一入口使用入参
 * @date 2019/4/1
 */
public class ComRequest {
    public ComRequest() {
        this.param = new HashMap<String, Object>();
    }
    //入参
    private Map<String,Object> param;
    //code
    private int code;

    //token
    private String token;

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
