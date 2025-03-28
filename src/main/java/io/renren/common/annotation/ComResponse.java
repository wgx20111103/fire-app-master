package io.renren.common.annotation;

/**
 * @author suntao
 * @description
 * @date 2019/4/1
 */
public class ComResponse {
    public ComResponse() {
    }

    public ComResponse(Object respParams) {
        this.respParams = respParams;
    }

    public ComResponse(Object respParams, String operateLog) {
        this.respParams = respParams;
        this.operateLog = operateLog;
    }

    //返回参数
    private Object respParams;

    //操作日志
    public String operateLog;

    public Object getRespParams() {
        return respParams;
    }

    public void setRespParams(Object respParams) {
        this.respParams = respParams;
    }

    public String getOperateLog() {
        return operateLog;
    }

    public void setOperateLog(String operateLog) {
        this.operateLog = operateLog;
    }
}
