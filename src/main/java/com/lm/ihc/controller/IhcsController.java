package com.lm.ihc.controller;

import com.lm.ihc.domain.Ihcs;
import com.lm.ihc.service.IhcsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/ihcs")
public class IhcsController {

    @Autowired
    private IhcsService ihcsService;

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
}
