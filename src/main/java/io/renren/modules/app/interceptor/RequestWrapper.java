package io.renren.modules.app.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

/*** @description 包装HttpServletRequest，目的是让其输入流可重复读* **/
public class RequestWrapper extends HttpServletRequestWrapper {
    private static Logger log = LogManager.getLogger(RequestWrapper.class);
    /*** 存储body数据的容器*/
    private byte[] body;
    private Map<String, String[]> parameterMap;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);// 将body数据存储起来
        parameterMap = request.getParameterMap();
        String bodyStr = getBodyString(request);
        body = bodyStr.getBytes(Charset.defaultCharset());
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<String>(parameterMap.keySet());
        return vector.elements();
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss & sql过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @SneakyThrows
    @Override
    public String getParameter(String name) {
        String[] results = parameterMap.get(name);
        if (results == null || results.length <= 0)
            return null;
        else {
            return results[0];
        }
    }

    /**
     * 获取指定参数名的所有值的数组，如：checkbox的所有数据
     * <p>
     * 接收数组变量 ，如checkobx类型
     */
    @SneakyThrows
    @Override
    public String[] getParameterValues(String name) {
        String[] results = parameterMap.get(name);
        if (results == null || results.length <= 0)
            return null;
        else {
            return results;
        }
    }






    /*** 获取请求Body** @param request request* @return String*/
    public String getBodyString(final ServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    /*** 获取请求Body** @return String*/
    public String getBodyString() {
        InputStream inputStream = new ByteArrayInputStream(body);
        return inputStream2String(inputStream);
    }

    /*** 修改body 将json 重新设置成body* @param val*/
    public void setBody(String val) {
        body = val.getBytes(StandardCharsets.UTF_8);
    }

    /*** 将inputStream里的数据读取出来并转换成字符串** @param inputStream inputStream* @return String*/
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return sb.toString();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
}
