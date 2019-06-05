package controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pagemodel.DataGrid;
import po.TbAttence;
import po.TbDaily;
import po.User;
import service.AttenceService;
import service.DailyService;
import util.DateTool;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	@RequestMapping("attence/to_all_attence")
	public String toAllAttence(){
		return "attence/all_attence";
	}

	@RequestMapping("attence/all_attence")
	@ResponseBody
	public DataGrid<TbAttence> getAllAttence(@RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		DataGrid<TbAttence> grid;
		int firstRow = (current - 1) * rowCount;
		List<TbAttence> results = attenceService.getAllAttence(firstRow, rowCount);
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
	@ResponseBody
	public String upAttenceExcel(@RequestParam("upload") MultipartFile upload) throws IOException, InvalidFormatException {

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
			attence.setStartTime(row.getCell(2).getStringCellValue());
			attence.setOffTime(row.getCell(3).getStringCellValue());

			attences.add(attence);
		}

		attenceService.addAttence(attences);

		return "toAllAttence";
	}

  }
