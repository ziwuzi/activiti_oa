package controller;

import com.alibaba.fastjson.JSON;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pagemodel.DataGrid;
import po.TbAttence;
import po.User;
import po.query.AttenceData;
import service.AttenceService;
import util.DateTool;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
		List<TbAttence> results = attenceService.getMyAttence(user.getUid(),current, rowCount);
		int totalSize = attenceService.getMyAttenceCount(user.getUid());
		grid = new DataGrid<>(current, rowCount, totalSize, results);
		return grid;
	}

	@RequestMapping("attence/to_all_attence")
	public String toAllAttence(){
		return "attence/all_attence";
	}

	@RequestMapping("attence/all_attence")
	@ResponseBody
	public DataGrid<TbAttence> getAllAttence(@RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		DataGrid<TbAttence> grid;
		List<TbAttence> results = attenceService.getAllAttence(current, rowCount);
		int totalSize = attenceService.getAllAttenceCount();
		grid = new DataGrid<>(current, rowCount, totalSize, results);
		return grid;
	}

	/**
	 * 导入考勤信息
	 *
	 * @return
	 */
	@RequestMapping("attence/upload_attence")
	public String upAttenceExcel(@RequestParam("upload") MultipartFile upload) throws IOException, InvalidFormatException, ParseException {

		// 玄学加速
		Thread.currentThread().setPriority(10);

		// 读取前端上传的excel
		Workbook workbook = WorkbookFactory.create(upload.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);// 获取Excel第一个选项卡
		List<TbAttence> attences = new ArrayList<>();

		// 取出所有考勤数据并存入list中
		System.out.println("sheet.getLastRowNum()" + sheet.getLastRowNum());
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {// 遍历每一行
			Row row = sheet.getRow(rowIndex);

			String date = row.getCell(1).getStringCellValue();
			// 存入entity
			TbAttence attence = new TbAttence();
			attence.setUserId(Integer.valueOf(row.getCell(0).getStringCellValue()));
			attence.setDate(date);
			attence.setWeekday(DateTool.getWeekOfDate(DateTool.strintToDate1(date)));
			if(row.getCell(2) == null) {
				attence.setStartTime("");
			}
			else{
				attence.setStartTime(row.getCell(2).getStringCellValue());
			}
			if(row.getCell(3) == null) {
				attence.setOffTime("");
			}
			else{
				attence.setOffTime(row.getCell(3).getStringCellValue());
			}

			attences.add(attence);
		}

		attenceService.addAttence(attences);

		return "upload_success";
	}

	@RequestMapping("attence/to_attence_chart")
	public String toAttenceChart(){
		return "attence/attence_chart";
	}

	@RequestMapping("attence/attence_chart_all")
	@ResponseBody
	public Object getAttenceChartAll(@RequestParam("start") String start,@RequestParam("end") String end) throws ParseException {
		if(!StringUtils.isEmpty(start)) {
			start = DateTool.strintToDateString1(start);
		}
		else{
			start="00000000";
		}
		if(!StringUtils.isEmpty(end)) {
			end = DateTool.strintToDateString1(end);
		}
		else{
			end="99999999";
		}
		List<AttenceData> dataList = attenceService.getAllAttenceDatas(start,end);
		return JSON.toJSON(dataList);
	}

	@RequestMapping("attence/attence_chart_name")
	@ResponseBody
	public Object getAttenceChartName(@RequestParam("start") String start,@RequestParam("end") String end,@RequestParam("name") String name) throws ParseException {
		if(!StringUtils.isEmpty(end)) {
			end = DateTool.strintToDateString1(end);
		}
		else{
			end="99999999";
		}
		if(!StringUtils.isEmpty(start)) {
			start = DateTool.strintToDateString1(start);
		}
		else{
			start="00000000";
		}
		AttenceData dataList = attenceService.getUserAttenceData(start, end, name);
		if(dataList == null){
			return JSON.toJSONString("noName");
		}
		return JSON.toJSON(dataList);
	}

  }
