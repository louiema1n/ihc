package com.lm.ihc.mapper;

import com.lm.ihc.domain.Ihcs;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface IhcsMapper {

    @SelectProvider(type = IhcsDaoProvider.class, method = "select")
    @Results(value = {
            @Result(property = "userid", column = "userid"),
            @Result(property = "user", column = "userid", one = @One(select = "com.lm.ihc.mapper.UserMapper.selectById"))
    })
    List<Ihcs> selectAll(String begin, String end, int searchNo);

    @Insert("INSERT INTO ihcs(" +
            "number," +
            "son," +
            "total," +
            "item," +
            "time," +
            "remark," +
            "state," +
            "userid)" +
            "VALUES (" +
            "#{number}," +
            "#{son}," +
            "#{total}," +
            "#{item}," +
            "#{time}," +
            "#{remark}," +
            "#{state}," +
            "#{userid})")
    Integer insertOne(Ihcs ihcs);

    @UpdateProvider(type = IhcsDaoProvider.class, method = "upd")
    Integer updOne(Ihcs ihcs);

    class IhcsDaoProvider{
        public String upd(Ihcs ihcs) {
            String sql = "UPDATE ihcs SET ";
            Integer i = 0;
            String s = "";
            Timestamp ts = null;
            if ((i = ihcs.getNumber()) != 0) {
                sql += "number = " + i + ", ";
            }
            if ((i = ihcs.getSon()) != 0) {
                sql += "son = " + i + ", ";
            }
            if ((i = ihcs.getTotal()) != 0) {
                sql += "total = " + i + ", ";
            }
            if (!(s = ihcs.getItem()).equals("")) {
                sql += "item = '" + s + "', ";
            }
            if (!(ts = ihcs.getTime()).equals(null)) {
                sql += "time = '" + ts + "', ";
            }
            s= ihcs.getRemark();
            if (s != null) {
                sql += "remark = '" + s + "', ";
            }
            sql += "state = " + ihcs.getState();
//            sql = sql.substring(0, sql.lastIndexOf(","));
            sql += " where id = " + ihcs.getId();
            return sql;
        }

        public String select(String begin, String end, int searchNo) {
            String sql = "select * from ihcs where state = 1 ";
            if (begin != "" && end != "") {
                sql += "and time >= '" + begin + "' and time <= '" + end + "' ";
                if (searchNo != 0) {
                    sql += "and number like '%" + searchNo + "%'";
                }
            } else if (searchNo != 0) {
                sql += "and number like '%" + searchNo + "%'";
            }
            return sql;
        }
    }
}
