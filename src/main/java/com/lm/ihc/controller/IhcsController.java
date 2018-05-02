package com.lm.ihc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm.ihc.domain.Ihcs;
import com.lm.ihc.service.IhcsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/ihcs")
public class IhcsController {

    @Autowired
    private IhcsService ihcsService;

    @Value("${web.printTablePath}")
    private String printTablePath;

    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Ihcs> getAll(
            @RequestParam("begin") String begin,
            @RequestParam("end") String end,
            @RequestParam("searchNo") int searchNo) {
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
            FileWriter fw = new FileWriter(dist);
            fw.write(result);
//            fw.write("12\r\n34");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return "成功打印标签。";
    }
}
