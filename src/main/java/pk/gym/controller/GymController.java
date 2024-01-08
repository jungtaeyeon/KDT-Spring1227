package pk.gym.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 다른 패키지도 ApplicationContext에서 관리를 해주는지.. 테스트! -> 결론은 안됨..
// @Configuration 으로 해결 해보자! -> RootConfig.java
@RestController
public class GymController {
  Logger logger = LoggerFactory.getLogger(GymController.class);
  @GetMapping("/gym/gymList")
  public String gymList(){
    logger.info("gymList");
    return "gymList";
  }
}
