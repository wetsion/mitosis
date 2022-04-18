package site.wetsion.framework.mitosis.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.wetsion.framework.mitosis.common.R;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * controller统一异常处理
 *
 * @author wetsion
 * @date 4/18/22
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public R wrapResult(Exception e) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log(requestAttributes.getRequest(), e);
        return R.sysError(e.getMessage());
    }

    private void log(HttpServletRequest request, Exception e) {
        if (Objects.isNull(request)) {
            log.error("[http] request error", e);
        } else {
            log.error("[http] request error, path: {}", request.getServletPath(), e);
        }
    }
}
