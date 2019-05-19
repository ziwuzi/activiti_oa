package controller;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import po.TestApply;
import service.TestService;
import service.impl.TestServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class TestController {
    TestService testService = new TestServiceImpl();

    @RequestMapping("/modifyapply")
    public String modifyApply(){
        return "activiti/modifyapply";
    }
    @RequestMapping(value="/starttest",method= RequestMethod.POST)
    @ResponseBody
    public String startTest(TestApply apply, HttpSession session){
        String userid=(String) session.getAttribute("username");
        Map<String,Object> variables=new HashMap<>();
        variables.put("applyuserid", userid);
        ProcessInstance ins=testService.startWorkflow(apply, userid, variables);
        System.out.println("流程id"+ins.getId()+"已启动");
        return JSON.toJSONString("success");
    }
}
