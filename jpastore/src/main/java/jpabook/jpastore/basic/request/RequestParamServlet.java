package jpabook.jpastore.basic.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 1. 파라미터 전송기능
 * http://localhost:8080/request-param?username=hello&age=20
 * 
 */

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    System.out.println("[전체 파라미터 조회]");
    req.getParameterNames().asIterator()
    .forEachRemaining(paramName -> System.out.println(paramName + "=" + req.getParameter(paramName)));
    System.out.println();

    System.out.println("[단일 파라미터 조회]");
    String username = req.getParameter("username");
    String age = req.getParameter("age");
    System.out.println("username = "+username +"\t age = "+age);
    System.out.println();

    System.out.println("[중복 파라미터 조회]");
    String[] usernames = req.getParameterValues("username");
    for (String name : usernames) {
      System.out.println("username = "+ name);
    }

    res.getWriter().write("ok all done");
  }
  
}
