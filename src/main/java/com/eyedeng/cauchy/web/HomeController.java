package com.eyedeng.cauchy.web;

import com.eyedeng.cauchy.dto.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HomeController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    public String home() {
        return "hello";
    }

    @GetMapping("/getGreet")
    public Greeting getGreet(@RequestParam(value = "myname", defaultValue = "world") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @PostMapping("/putGreet")
    public Greeting putGreet(@RequestBody Greeting greeting) {
        greeting.setName("吗？");
        return greeting;
    }
}
