package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan  // 서블릿
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

/*
가급적이면 코딩을 적게 한다 - 적게하지만 유지보수 좋음 - 다형성을 활용
 * DemoApplication에서 main 실행하는 것으로 서버 기동됨
 * Spring Boot - 3.16버전 - Gradle{빌드도구-배포}
 * 설정파일은
 * 1) application.properties - Properties클래스 동일함
 * 2) application.yml - json형식 - 반복코드 생략함
 * 
 * server:
  port: 8000
  servlet:
    context-path: /  -> 루트패스 설정
    encoding:
      charset: UTF-8  -> 한글 인코딩
      enabled: true
      force: true
spring:
  output:
    ansi:
      enabled: always  -> 로그 출력 옵션
  mvc:
    view:
      prefix: /WEB-INF/views/  -> 출력 설정 - ModelAndView -> ViewResolver
      suffix: .jsp
  servlet:
    multipart:   -> 첨부파일 설정
      enabled: true
 */