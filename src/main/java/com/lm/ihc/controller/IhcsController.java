package com.lm.ihc.controller;

import com.lm.ihc.domain.Ihcs;
import com.lm.ihc.service.IhcsService;
import com.lm.ihc.service.UserService;
import com.lm.ihc.utils.IhcsUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/ihcs")
public class IhcsController {

    @Autowired
    private IhcsService ihcsService;

    @Autowired
    private UserService userService;

    @Value("${web.printTablePath}")
    private String printTablePath;

    @Value("${sub.name}")
    private String SUBNAME;

    @Value("${sub.code}")
    private String SUBCODE;

    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Ihcs> getAll(
            @RequestParam("begin") String begin,
            @RequestParam("end") String end,
            @RequestParam("searchNo") String searchNo) {
        return this.ihcsService.queryAll(begin, end, searchNo);
    }

    @CrossOrigin
    @RequestMapping(value = "/upd", method = RequestMethod.POST)
    public Integer add(@RequestBody Ihcs ihcs) {
        return this.ihcsService.addOne(ihcs);
    }

    @CrossOrigin
    @RequestMapping(value = "/upd", method = RequestMethod.PUT)
    public Integer upd(@RequestBody Ihcs ihcs) {
        return this.ihcsService.updOne(ihcs);
    }

    @CrossOrigin
    @RequestMapping(value = "/upd", method = RequestMethod.DELETE)
    public Integer del(@RequestBody Ihcs ihcs) {
        System.out.println(ihcs);
        return this.ihcsService.updOne(ihcs);
    }

    @CrossOrigin
    @RequestMapping(value = "/print", method = RequestMethod.POST)
    public String print(@RequestBody String printData) {
        // 格式化数据
        String result = printData.substring(11, printData.length() - 2);
        // 转换js中的CODE
        result = result.replaceAll("CODE", SUBCODE);
        // 转换js中的\r\n，Java中正则表达式\\\\代表\\,\\代表\
        result = result.replaceAll("\\\\r\\\\n", "\r\n");
        // 导出到指定文件
        // 生成文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + ".txt";
        File dist = new File(printTablePath + fileName);
        if (!dist.getParentFile().exists()) {
            // 创建
            dist.getParentFile().mkdirs();
        }
        try {
            // 解决中文编码问题
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(dist), "UTF-8");
            out.write(result);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return "成功打印标签。";
    }

    @CrossOrigin
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("fileIhcs") MultipartFile file) {
        try {
            // 创建Excel读取对象
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            // 获取sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 遍历行
            if (sheet != null) {
                // Ihcs集合
                List<Ihcs> ihcsList = new ArrayList<>();
                XSSFRow row;
                Ihcs ihcs;
                String prjName, testNo, userNick, results, name;
                int prjNameIndex = 0, testNoIndex = 0, timeIndex = 0, userNickIndex = 0, resultsIndex = 0, doctorIndex = 0, nameIndex = 0;
                int total = 0;  // 细项数
                boolean flag = false;
                boolean other = false;
                for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
                    // 获取row
                    row = sheet.getRow(i);

                    // 使用标题行获取相应的index
                    if (i == 3) {
                        // 获取相应index
                        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                            switch (row.getCell(j).getStringCellValue()) {
                                case "项目名称":
                                    prjNameIndex = j;
                                    break;
                                case "细项数":
                                    prjNameIndex = j;
                                    flag = true;
                                    break;
                                case "蜡块编号":
                                    testNoIndex = j;
                                    break;
                                case "确认加做时间":
                                    timeIndex = j;
                                    break;
                                case "确认加做人":
                                    userNickIndex = j;
                                    break;
                                case "诊断意见":
                                    resultsIndex = j;
                                    break;
                                case "项目明细":
                                    resultsIndex = j;
                                    other = true;
                                    break;
                                case "批准人":
                                    doctorIndex = j;
                                    break;
                                case "病理医生":
                                    doctorIndex = j;
                                    break;
                                case "病人姓名":
                                    nameIndex = j;
                                    break;
                            }
                        }
                        continue;
                    }
                    prjName = row.getCell(prjNameIndex).getStringCellValue();
                    if (prjName.lastIndexOf("免疫组化") >= 0
                            || prjName.lastIndexOf("免疫荧光") >= 0
                            || prjName.lastIndexOf("特殊染色") >= 0 || flag) {
                        // 创建对象
                        ihcs = new Ihcs();

                        ihcs.setPrj(prjName);   // 项目名称

                        ihcs.setDoctor(row.getCell(doctorIndex).getStringCellValue());   // 批准人-病理医生

                        ihcs.setName(row.getCell(nameIndex).getStringCellValue());   // 病人姓名

                        testNo = row.getCell(testNoIndex).getStringCellValue();    // 蜡块编号
                        // 格式化蜡块编号
                        ihcs.setNumber(IhcsUtil.getNumber(testNo));
                        ihcs.setSon(IhcsUtil.getSon(testNo));

                        ihcs.setTime(Timestamp.valueOf(row.getCell(timeIndex).getStringCellValue()));// 确认加做时间

                        userNick = row.getCell(userNickIndex).getStringCellValue();// 确认加做人
                        // 设置确认加做人
                        ihcs.setConfirm(userNick);

                        total = new IhcsUtil().formatTotal(prjName, "[^0-9]");
                        if (total == 0) {
                            total = new IhcsUtil().formatTotal(prjName, "[^一|二|三|四|五|六|七|八|九|十]");
                        }
                        total = (total == 16672 ? 2 : total);
                        ihcs.setTotal(total);   // 项目数
                        results = row.getCell(resultsIndex).getStringCellValue().trim();// 诊断意见
                        ihcs.setResults(results);
                        String formatResult = null;
                        if (other) {
                            formatResult = results;
                        } else {
                            switch (SUBNAME) {
                                case "guiyang":
                                    formatResult = IhcsUtil.getGYItems(results);
                                    break;
                                case "guangzhou":
                                    formatResult = IhcsUtil.getGZItems(results);
                                    break;
                            }
                        }
                        ihcs.setIsmatch(true);
                        if (formatResult == null || formatResult.equals("")) {
                            // 未匹配到
                            ihcs.setIsmatch(false);
                        } else {
                            String[] strings = formatResult.split("、");
                            if (strings.length != total) {
                                // 匹配不正确
                                ihcs.setIsmatch(false);
                            }
                        }
                        ihcs.setItem(formatResult);

                        // 默认正常
                        ihcs.setState(true);

                        // 添加到集合
                        ihcsList.add(ihcs);
                    }

                }
                // 导入数据库
                for (Ihcs ihc : ihcsList) {
                    this.ihcsService.impOne(ihc);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
