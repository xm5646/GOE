package com.project.goe.projectgeodbserver.controller;

import com.project.goe.projectgeodbserver.entity.CardInfo;
import com.project.goe.projectgeodbserver.entity.DrawCashRecord;
import com.project.goe.projectgeodbserver.service.CardInfoService;
import com.project.goe.projectgeodbserver.service.DrawCashService;
import com.project.goe.projectgeodbserver.util.FileUtil;
import com.project.goe.projectgeodbserver.util.TimeUtil;
import com.project.goe.projectgeodbserver.util.exceldemo.Person;
import com.project.goe.projectgeodbserver.viewentity.excel.ExcelDrawcash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
@CrossOrigin
public class ExcelDownloadController {

	@Autowired
	private CardInfoService cardInfoService;

	@Autowired
	private DrawCashService drawCashService;
	
	@RequestMapping("export")
    public void export(HttpServletResponse response){

        //模拟从数据库获取需要导出的数据
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person("路飞","1",new Date());
        Person person2 = new Person("娜美","2", TimeUtil.addDay(3));
        Person person3 = new Person("索隆","1", TimeUtil.addDay(10));
        Person person4 = new Person("小狸猫","1", TimeUtil.addDay(-10));
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        //导出操作
        FileUtil.exportExcel(personList,"花名册","草帽一伙",Person.class,"海贼王.xls",response);
    }
	
	@RequestMapping("exportDraw")
    public void exportDrawcash(HttpServletResponse response){
		List<ExcelDrawcash> excellist = new ArrayList<>();
		List<DrawCashRecord> drawList = drawCashService.findAll();
		Map<Long,CardInfo> cardMap = cardInfoService.findAllMap();
		if (drawList!=null && drawList.size()>0) {
			for (DrawCashRecord draw : drawList) {
				ExcelDrawcash edraw = new ExcelDrawcash();
				edraw.setDrawCommitTime(draw.getDrawCommitTime());
				edraw.setDrawId(draw.getDrawId());
				edraw.setDrawnumber(draw.getDrawnumber());
				edraw.setDrawStatus(draw.getDrawStatus());
				edraw.setFinalNumber(draw.getFinalNumber());
				edraw.setPayTime(draw.getPayTime());
				edraw.setPhone(draw.getPhone());
				edraw.setUserId(draw.getUserId());
				
				edraw.setCardInfoId(cardMap.get(draw.getCardInfoId()).getCardNumber());
				edraw.setBankName(cardMap.get(draw.getCardInfoId()).getBankName());
				edraw.setUserName(cardMap.get(draw.getCardInfoId()).getCardOwnerName());
				excellist.add(edraw);
			}
		}
		String time = TimeUtil.getDateFormatDay();
		//sheet页名称
		String tableHeard = "提现记录"+time;
		//表头
		String sheetname = "提现记录";
		//文件名
		String fileName = "提现记录"+time+".xls";
		//导出操作
        FileUtil.exportExcel(excellist,tableHeard,sheetname,ExcelDrawcash.class,fileName,response);
	}

    @RequestMapping("importExcel")
    public void importExcel(){
        String filePath = "F:\\海贼王.xls";
        //解析excel，
        List<Person> personList = FileUtil.importExcel(filePath,1,1,Person.class);
        //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        System.out.println("导入数据一共【"+personList.size()+"】行");

        //TODO 保存数据库
    }
}
