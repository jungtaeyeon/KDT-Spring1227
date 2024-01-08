package com.example.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"pk.gym.controller"})
@ComponentScan(basePackages = {"pk.gym.logic"})
@ComponentScan(basePackages = {"pk.gym.dao"})
@ComponentScan(basePackages = {"pk.gym.config"})
public class RootConfig {
  
}
