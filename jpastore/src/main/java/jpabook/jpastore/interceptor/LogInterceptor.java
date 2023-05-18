package jpabook.jpastore.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor implements HandlerInterceptor{

  public static final String LOG_ID = "logId";
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    String requestURL = request.getRequestURI();
    String uuid = UUID.randomUUID().toString();

    request.setAttribute(LOG_ID, uuid);

    // @RequestMapping: HandlerMethod
    // 정적 리소스: ResourceHttpRequestHandler

    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러 메서드의 모든 정보 포함
    }

    log.info("REQUEST [{}][{}][{}]", uuid, requestURL, handler);

    return true; // false 하면 여기서 중단.
  }
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    log.info("REQUEST [{}]", modelAndView);
  }
  
  
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    String requestURL = request.getRequestURI();
    String logId = (String) request.getAttribute(LOG_ID);
    log.info("RESPONSE [{}][{}][{}]", logId, requestURL, handler);
    if (ex != null) {
      log.error("afterComletion error!!", ex);
    }
  }
}
