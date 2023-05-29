package jpabook.jpastore.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jpabook.jpastore.basic.HelloData;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet{

  // 스프링은 기본적으로 jackson이라는 파싱 라이브러리를 제공 - ObjectMapper 이용
  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    ServletInputStream inputStream = req.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    // 위 까진 텍스트와 동일

    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
    System.out.println("helloData.username = "+helloData.getUsername());
    System.out.println("helloData.age = " + helloData.getAge());
    
  }
  
}
