package com.lm.ihc.controller;

import com.lm.ihc.domain.Ihcs;
import com.lm.ihc.service.IhcsService;
import com.lm.ihc.service.UserService;
import com.lm.ihc.utils.IhcsUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
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
                String prjName = null, testNo = null, userNick = null, results, name, batch = null;
                int prjNameIndex = 0,
                        testNoIndex = 0,
                        timeIndex = 0,
                        userNickIndex = 0,
                        resultsIndex = 0,
                        doctorIndex = 0,
                        nameIndex = 0,
                        itemTotalIndex = 0,
                        remarkIndex = 0,
                        batchIndex = 0;
                int total = 0;  // 细项数
                boolean other = false;
                // 判断是否页尾
                int rows1 = sheet.getPhysicalNumberOfRows();
                int rows2 = sheet.getLastRowNum();
                int rows;
                XSSFRow row2 = sheet.getRow(rows2 - 1);
                if (row2.getCell(2) == null) {
                    rows = rows2 - 1;
                } else {
                    rows = rows1;
                }
                for (int i = 3; i < rows; i++) {
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
                                    itemTotalIndex = j;
                                    break;
                                case "蜡块编号":
                                    testNoIndex = j;
                                    break;
                                case "病理号":
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
                                case "批次":
                                    batchIndex = j;
                                    break;
                                case "备注":
                                    remarkIndex = j;
                                    break;
                            }
                        }
                        continue;
                    }
                    // 创建对象
                    ihcs = new Ihcs();

                    try {
                        testNo = row.getCell(testNoIndex).getStringCellValue();    // 蜡块编号
                    } catch (IllegalStateException e) {
                        // 取消科学计数法
                        testNo = String.format("%.0f", row.getCell(testNoIndex).getNumericCellValue());    // 蜡块编号
                        System.out.println("第" + (i + 1) + "行[蜡块编号]数据格式错误，已尝试更换读取方式，成功！");
                    } catch (NullPointerException e) {
                        System.out.println("第" + (i + 1) + "行[蜡块编号]为空，直接跳到下一行！当前行不导入！[蜡块编号不能为空]");
                        break;
                    }
                    if (testNo == null || StringUtils.isEmpty(testNo) || testNo.equals(0)) {
                        System.out.println("第" + (i + 1) + "行[蜡块编号]为空，直接跳到下一行！当前行不导入！[蜡块编号不能为空]");
                        break;
                    }

                    if (prjNameIndex != 0) {
                        try {
                            prjName = row.getCell(prjNameIndex).getStringCellValue();
                        } catch (NullPointerException e) {
                            // 为空
                            System.out.println("第" + (i + 1) + "行[项目名称]为空，请注意修改！");
                        } catch (IllegalStateException e) {
                            // POI读取格式错误
                            prjName = String.valueOf(row.getCell(prjNameIndex).getNumericCellValue());
                            System.out.println("第" + i + "行[项目名称]数据格式错误，已尝试更换读取方式，成功！");
                        }
                    }
                    ihcs.setPrj(prjName);   // 项目名称

                    String doctor = null;
                    try {
                        doctor = row.getCell(doctorIndex).getStringCellValue();
                    } catch (NullPointerException e) {
                        System.out.println("第" + (i + 1) + "行[病理医生]为空，请注意修改！");
                    } catch (IllegalStateException e) {
                        doctor = String.valueOf(row.getCell(doctorIndex).getNumericCellValue());
                        System.out.println("第" + (i + 1) + "行[病理医生]数据格式错误，已尝试更换读取方式，成功！");
                    }
                    ihcs.setDoctor(doctor);   // 批准人-病理医生

                    String names = null;
                    try {
                        names = row.getCell(nameIndex).getStringCellValue();
                    } catch (NullPointerException e) {
                        System.out.println("第" + (i + 1) + "行[病人姓名]为空，请注意修改！");
                    } catch (IllegalStateException e) {
                        names = String.valueOf(row.getCell(nameIndex).getNumericCellValue());
                        System.out.println("第" + (i + 1) + "行[病人姓名]数据格式错误，已尝试更换读取方式，成功！");
                    }
                    ihcs.setName(names);   // 病人姓名

                    // 格式化蜡块编号
                    ihcs.setNumber(IhcsUtil.getNumber(testNo));
                    ihcs.setSon(IhcsUtil.getSon(testNo));

                    // 处理确认加做时间格式为2018-7-5
                    String time = null;
                    Timestamp timestamp = null;
                    try {
                        time = row.getCell(timeIndex).getStringCellValue();
                        timestamp = Timestamp.valueOf(time);
                    } catch (IllegalArgumentException e) {
                        timestamp = Timestamp.valueOf(time + " 00:00:00");
                        System.out.println("第" + (i + 1) + "行[确认加做时间]数据格式错误，已尝试更换读取方式，成功！");
                    } catch (NullPointerException e) {
                        timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
                        System.out.println("第" + (i + 1) + "行[确认加做时间]为空，已默认为当前系统时间！");
                    } catch (IllegalStateException e) {
                        timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
                        System.out.println("第" + (i + 1) + "行[确认加做时间]数据格式错误，已默认为当前系统时间！");
                    }
                    ihcs.setTime(timestamp);// 确认加做时间

                    try {
                        batch = row.getCell(batchIndex).getStringCellValue();// 批次
                    } catch (IllegalArgumentException e) {
                        batch = String.valueOf(row.getCell(batchIndex).getNumericCellValue());// 批次
                        System.out.println("第" + (i + 1) + "行[批次]数据格式错误，已尝试更换读取方式，成功！");
                    } catch (NullPointerException e) {
                        System.out.println("第" + (i + 1) + "行[批次]为空，请注意修改！");
                    }
                    // 设置确认加做人
                    ihcs.setBatch(batch);

                    // 模板取值
                    if (itemTotalIndex != 0 && row.getCell(itemTotalIndex) != null) {
                        total = (int) row.getCell(itemTotalIndex).getNumericCellValue();
                        ihcs.setPrj(String.valueOf(total));   // 项目名称
                    } else
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

                    try {
                        userNick = row.getCell(userNickIndex).getStringCellValue();// 确认加做人
                    } catch (IllegalArgumentException e) {
                        userNick = String.valueOf(row.getCell(userNickIndex).getNumericCellValue());// 确认加做人
                        System.out.println("第" + (i + 1) + "行[确认加做人]数据格式错误，已尝试更换读取方式，成功！");
                    } catch (NullPointerException e) {
                        System.out.println("第" + (i + 1) + "行[确认加做人]为空，请注意修改！");
                    }
                    // 设置确认加做人
                    ihcs.setConfirm(userNick);

                    String remark = null;
                    try {
                        remark = row.getCell(remarkIndex).getStringCellValue();// 备注
                    } catch (IllegalArgumentException e) {
                        remark = String.valueOf(row.getCell(remarkIndex).getNumericCellValue());// 备注
                        System.out.println("第" + (i + 1) + "行[备注]数据格式错误，已尝试更换读取方式，成功！");
                    } catch (NullPointerException e) {
                        System.out.println("第" + (i + 1) + "行[备注]为空，请注意修改！");
                    }
                    ihcs.setRemark(remark);

                    // 默认正常
                    ihcs.setState(true);

                    // 添加到集合
                    ihcsList.add(ihcs);

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
