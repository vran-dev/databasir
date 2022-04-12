package com.databasir.api;

import com.databasir.common.JsonData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class IndexController {

    @GetMapping("/live")
    @ResponseBody
    public JsonData<String> live() {
        return JsonData.ok("ok");
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "/index.html";
    }
}
