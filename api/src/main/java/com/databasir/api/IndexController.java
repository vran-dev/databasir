package com.databasir.api;

import com.databasir.common.JsonData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Tag(name = "IndexController", description = "测活 API")
public class IndexController {

    @GetMapping("/live")
    @ResponseBody
    @Operation(summary = "测活")
    public JsonData<String> live() {
        return JsonData.ok("ok");
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(summary = "404 统一跳转")
    public String handleResourceNotFoundException() {
        return "/index.html";
    }
}
