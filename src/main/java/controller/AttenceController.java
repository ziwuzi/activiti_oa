package controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pagemodel.DataGrid;
import po.TbAttence;
import po.TbDaily;
import po.User;
import service.AttenceService;
import service.DailyService;
import util.DateTool;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
public class AttenceController {
	@Autowired
	AttenceService attenceService;
	
	@RequestMapping("attence/to_my_attence")
	public String toMyAttence(){
		return "attence/my_attence";
	}

	@RequestMapping("attence/my_attence")
	@ResponseBody
	public DataGrid<TbAttence> getMyAttence(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		User user = (User) session.getAttribute("user");
		DataGrid<TbAttence> grid;
		int firstRow = (current - 1) * rowCount;
		List<TbAttence> results = attenceService.getMyAttence(user.getUid(),firstRow, rowCount);
		int totalSize = attenceService.getMyAttenceCount(user.getUid());
		grid = new DataGrid<>(current, rowCount, totalSize, results);
		return grid;
	}

  }
