package io.renren.common.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author suntao
 * @description 放非对象
 * @date 2019/4/18
 */
public class ComFinger {
    //暂时不用
    private HttpServletRequest httpRequest;
    //http返回
    private HttpServletResponse httpResponse;

    private String ipadId;

    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setHttpResponse(HttpServletResponse httpResponse) {
        this.httpResponse = httpResponse;
    }


    public HttpServletResponse getHttpResponse() {
        return httpResponse;
    }

    public String getIpadId() {
        return ipadId;
    }

    public void setIpadId(String ipadId) {
        this.ipadId = ipadId;
    }
}
