package com.lm.ihc.service;

import com.lm.ihc.domain.Ihcs;
import com.lm.ihc.mapper.IhcsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class IhcsService {

    @Autowired
    private IhcsMapper ihcsMapper;

    public List<Ihcs> queryAll(String begin, String end, int searchNo) {
        return this.ihcsMapper.selectAll(begin, end, searchNo);
    }

    public Integer addOne(Ihcs ihcs) {
        return this.ihcsMapper.insertOne(ihcs);
    }

    public Integer updOne(Ihcs ihcs) {
        return this.ihcsMapper.updOne(ihcs);
    }
}
