package service;

import java.util.List;
import java.util.Map;
import org.activiti.engine.runtime.ProcessInstance;
import po.PurchaseApply;
import po.query.PurchaseQuery;

public interface PurchaseService {
	public ProcessInstance startWorkflow(PurchaseApply apply,String userid,Map<String,Object> variables);
	PurchaseApply getPurchase(int id);
	void updatePurchase(PurchaseApply a);

    List<PurchaseApply> getMyPurchase(String userName, int current, int rowCount);

	int getMyPurchaseCount(String userName);
}
