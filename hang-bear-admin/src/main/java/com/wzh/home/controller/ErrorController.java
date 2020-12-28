package com.wzh.home.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/18 17:28
 */
@RestController
public class ErrorController extends BasicErrorController {
    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    //@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    //@Override
    //public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    //    Map<String,
    //                Object> errorAttributes = getErrorAttributes(request,
    //                                                     ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE,
    //                ErrorAttributeOptions.Include.STACK_TRACE, ErrorAttributeOptions.Include.BINDING_ERRORS));
    //    HttpStatus status = getStatus(request);
    //    // 获取错误信息
    //    String code = errorAttributes.get("status").toString();
    //    String message = errorAttributes.get("message").toString();
    //
    //    //ApiErrorResult apiErrorResult = new ApiErrorResult(false, code, message);
    //    String apiErrorResult = "";
    //    return new ResponseEntity<>(errorAttributes, status);
    //}
}