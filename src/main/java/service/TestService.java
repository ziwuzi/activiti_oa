package service;

import org.activiti.engine.runtime.ProcessInstance;
import po.LeaveApply;
import po.TestApply;

import java.util.Map;

public interface TestService {
    ProcessInstance startWorkflow(TestApply apply, String userId, Map<String,Object> variables);
}
