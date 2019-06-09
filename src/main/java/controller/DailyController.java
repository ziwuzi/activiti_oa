package controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pagemodel.DataGrid;
import po.TbDaily;
import po.User;
import po.query.DailyData;
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
	
	@RequestMapping("daily/my_daily")
	public String myDaily(){
		return "daily/my_daily";
	}

	@RequestMapping("daily/get_my_daily")
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

	@RequestMapping("daily/all_daily")
	public String allDaily(){
		return "daily/all_daily";
	}

	@RequestMapping("daily/get_all_daily")
	@ResponseBody
	DataGrid<TbDaily> getAllDaily(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		User user=(User) session.getAttribute("user");
		List<TbDaily> dailyList = dailyService.getDailyList(current,rowCount);
		int total = dailyService.getDailyCount();
		DataGrid<TbDaily> grid = new DataGrid<>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(total);
		grid.setRows(dailyList);
		return grid;
	}

	@RequestMapping("daily/to_add_daily")
	public String toAddDaily(){
		return "daily/add_daily";
	}

	@RequestMapping("daily/add_daily")
	@ResponseBody
	public String addDaily(@ModelAttribute("daily") TbDaily daily,HttpSession session){
		User user=(User) session.getAttribute("user");
		daily.setUserId(user.getUid());
		daily.setCreateTime(DateTool.parseDate2(new Date()));
		daily.setModifyTime(DateTool.parseDate2(new Date()));
		dailyService.addDaily(daily);
		return JSON.toJSONString("success");
	}

	@RequestMapping("daily/to_daily_chart")
	public String toDailyChart(){
		return "daily/daily_chart";
	}

	@RequestMapping("daily/daily_chart")
	@ResponseBody
	public DataGrid<DailyData> getDailyChart(@RequestParam("start") String start, @RequestParam("end") String end,@RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		if(StringUtils.isEmpty(start)){
			start = "0000-00-00";
		}
		if(StringUtils.isEmpty(end)){
			end = "9999-99-99";
		}
		DataGrid<DailyData> grid = new DataGrid<>(current,rowCount,0,null);
		grid = dailyService.getDailyData(start,end,grid);
		return grid;
	}

	@RequestMapping("daily/comment/{id}")
	@ResponseBody
	public Object purchaseManagerComplete(@PathVariable("id") String id,@RequestParam("comment") String comment){
		TbDaily daily = dailyService.getDaily(Integer.valueOf(id));
		daily.setComment(comment);
		dailyService.update(daily);
		return JSON.toJSONString("success");
	}

  }
