package co.user.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/")
@Slf4j
public class PingController {
    @GetMapping(value = "/ping", produces = TEXT_PLAIN_VALUE)
    public String ping(){
        return LocalDateTime.now().toString();
    }
}
