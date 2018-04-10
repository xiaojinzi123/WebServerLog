package com.xiaojinzi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 16/01/2018.
 */
@RequestMapping("network")
@Controller
public class NetworkController {

    @ResponseBody
    @RequestMapping(value = "log",method = RequestMethod.POST,produces = "application/json")
    public String log(HttpServletRequest request, String data) throws IOException {

        WebSocket.sendMessage(data);

        return "{\"message\":\"ok\"}";

    }

}
