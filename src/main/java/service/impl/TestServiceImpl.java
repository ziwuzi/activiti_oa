package service.impl;

import mapper.TestMapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import po.TestApply;
import service.TestService;

import java.util.Date;
import java.util.Map;

@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout=5)
@Service
public class TestServiceImpl implements TestService {
    TestMapper testMapper;
    @Autowired
    IdentityService identityservice;
    @Autowired
    RuntimeService runtimeservice;
    @Autowired
    TaskService taskservice;
    @Override
    public ProcessInstance startWorkflow(TestApply apply, String userId, Map<String, Object> variables) {
        apply.setApply_time(new Date().toString());
        apply.setUser_id(userId);
        testMapper.save(apply);
        identityservice.setAuthenticatedUserId(userId);
        ProcessInstance instance = runtimeservice.startProcessInstanceByKey("test",String.valueOf(apply.getId()),variables);
        String instanceId = instance.getId();
        apply.setProcess_instance_id(instanceId);
        testMapper.update(apply);
        return instance;
    }
}
