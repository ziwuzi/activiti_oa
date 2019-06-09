package mapper;

import org.apache.ibatis.annotations.Param;
import po.PurchaseApply;
import po.query.PurchaseQuery;

import java.util.List;

public interface PurchaseApplyMapper {
	void save(PurchaseApply apply);
	PurchaseApply get(int id);
	void update(PurchaseApply apply);

    List<PurchaseApply> getMyPurchase(@Param("userName") String userName);

	int getMyPurchaseCount(@Param("userName") String userName);
}
