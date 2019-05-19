package controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pagemodel.DataGrid;
import po.TbDaily;
import po.User;
import service.DailyService;
import service.LoginService;
import util.DateTool;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class DailyController {
	@Autowired
	DailyService dailyService;
	
	@RequestMapping("/my_daily")
	public String myDaily(){
		return "daily/my_daily";
	}

	@RequestMapping("/get_my_daily")
	@ResponseBody
	DataGrid<TbDaily> getMyDaily(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		User user=(User) session.getAttribute("user");
		List<TbDaily> dailyList = dailyService.getDailyList(user.getUid(),current,rowCount);
		int total = dailyService.getDailyCount(user.getUid());
		DataGrid<TbDaily> grid = new DataGrid<>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(total);
		grid.setRows(dailyList);
		return grid;
	}

	@RequestMapping("/to_add_daily")
	public String toAddDaily(){
		return "daily/add_daily";
	}

	@RequestMapping("/add_daily")
	@ResponseBody
	public String addDaily(@ModelAttribute("daily") TbDaily daily,HttpSession session){
		User user=(User) session.getAttribute("user");
		daily.setUserId(user.getUid());
		daily.setCreateTime(DateTool.parseDate2(new Date()));
		daily.setModifyTime(DateTool.parseDate2(new Date()));
		dailyService.addDaily(daily);
		return JSON.toJSONString("success");
	}

  }
