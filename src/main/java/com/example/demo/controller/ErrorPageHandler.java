package com.example.demo.controller;

//에러 페이지를 상태코드별로 처리할 수 있는 클래스 샘플입니다.

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
@Controller
public class ErrorPageHandler implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        // error로 들어온 에러의 status를 불러온다 (ex:404,500,403...)

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return "redirect:/error/error400.jsp";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "redirect:/error/error404.jsp";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "redirect:/error/error500.jsp";
            } else {
                return "redirect:/error/error.jsp";
            }
        }
        return "error";
    }
}
