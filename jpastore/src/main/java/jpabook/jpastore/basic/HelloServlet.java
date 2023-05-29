package jpabook.jpastore.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet{

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    System.out.println("HelloServlet.service()");
    System.out.println("request = "+req);
    System.out.println("response = "+res);
  }
  
}
