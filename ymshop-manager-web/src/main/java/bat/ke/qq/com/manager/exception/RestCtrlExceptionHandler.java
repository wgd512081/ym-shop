package bat.ke.qq.com.manager.exception;

import bat.ke.qq.com.common.exception.YmshopUploadException;
import bat.ke.qq.com.common.exception.YmshopException;
import bat.ke.qq.com.common.pojo.Result;
import bat.ke.qq.com.common.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.BindException;

/**
 *
 * @author bat.ke.qq.com
 * @date 2018/03/24
 */
@ControllerAdvice
public class RestCtrlExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Result<Object> bindExceptionHandler(BindException e){
        String errorMsg="请求数据校验不合法: ";
        if(e!=null){
            errorMsg=e.getMessage();
            log.warn(errorMsg);
        }
        return new ResultUtil<>().setErrorMsg(errorMsg);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(YmshopException.class)
    @ResponseBody
    public Result<Object> handleXmallException(YmshopException e) {
        String errorMsg="Xmall exception: ";
        if (e!=null){
            errorMsg=e.getMsg();
            log.warn(e.getMessage());
        }
        return new ResultUtil<>().setErrorMsg(errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Result<Object> handleException(Exception e) {
        String errorMsg="Exception: ";
        if (e!=null){
            log.warn(e.getMessage());
            if(e.getMessage()!=null&&e.getMessage().contains("Maximum upload size")){
                errorMsg="上传文件大小超过5MB限制";
            } else if(e.getMessage().contains("XmallException")){
                errorMsg = e.getMessage();
                errorMsg = StringUtils.substringAfter(errorMsg,"XmallException:");
                errorMsg = StringUtils.substringBefore(errorMsg,"\n");
            } else{
                errorMsg=e.getMessage();
            }
        }
        return new ResultUtil<>().setErrorMsg(errorMsg);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(YmshopUploadException.class)
    @ResponseBody
    public Result<Object> handleUploadException(YmshopUploadException e) {
        String errorMsg="Xmall upload exception: ";
        if (e!=null){
            errorMsg=e.getMsg();
            log.warn(errorMsg);
        }
        return new ResultUtil<>().setErrorMsg(errorMsg);
    }
}
