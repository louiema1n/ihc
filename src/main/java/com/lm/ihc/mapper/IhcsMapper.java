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
    List<Ihcs> selectAll(String begin, String end, String searchNo);

    @Insert("INSERT INTO ihcs(" +
            "number," +
            "son," +
            "total," +
            "item," +
            "time," +
            "remark," +
            "state," +
            "prj," +
            "userid)" +
            "VALUES (" +
            "#{number}," +
            "#{son}," +
            "#{total}," +
            "#{item}," +
            "#{time}," +
            "#{remark}," +
            "#{state}," +
            "#{prj}," +
            "#{userid})")
    Integer insertOne(Ihcs ihcs);

    @Insert("INSERT INTO ihcs(" +
            "number," +
            "son," +
            "total," +
            "item," +
            "time," +
            "remark," +
            "state," +
            "prj," +
            "results," +
            "ismatch," +
            "doctor," +
            "name," +
            "confirm)" +
            "VALUES (" +
            "#{number}," +
            "#{son}," +
            "#{total}," +
            "#{item}," +
            "#{time}," +
            "#{remark}," +
            "#{state}," +
            "#{prj}," +
            "#{results}," +
            "#{ismatch}," +
            "#{doctor}," +
            "#{name}," +
            "#{confirm})")
    Integer importOne(Ihcs ihcs);

    @UpdateProvider(type = IhcsDaoProvider.class, method = "upd")
    Integer updOne(Ihcs ihcs);

    class IhcsDaoProvider{
        public String upd(Ihcs ihcs) {
            String sql = "UPDATE ihcs SET ";
            Integer i = 0;
            String s = "";
            Timestamp ts = null;
            s = ihcs.getNumber();
            if (s != null) {
                sql += "number = '" + s + "', ";
            }
            if ((i = ihcs.getSon()) != 0) {
                sql += "son = " + i + ", ";
            }
            if ((i = ihcs.getTotal()) != 0) {
                sql += "total = " + i + ", ";
            }
            s = ihcs.getItem();
            if (s != null) {
                sql += "item = '" + s + "', ";
            }
            if (!(ts = ihcs.getTime()).equals(null)) {
                sql += "time = '" + ts + "', ";
            }
            s= ihcs.getRemark();
            if (s != null) {
                sql += "remark = '" + s + "', ";
            }
            sql += "state = " + ihcs.getState() + ", ";
            sql += "ismatch = " + ihcs.getIsmatch() + ", ";
            sql += "userid = " + ihcs.getUserid();
//            sql = sql.substring(0, sql.lastIndexOf(","));
            sql += " where id = " + ihcs.getId();
            return sql;
        }

        public String select(String begin, String end, String searchNo) {
            String sql = "select * from ihcs where state = 1 ";
            if (begin != "" && end != "") {
                sql += "and time >= '" + begin + "' and time <= '" + end + "' ";
                if (searchNo != null) {
                    sql += "and number like '%" + searchNo + "%'";
                }
            } else if (searchNo != null) {
                sql += "and number like '%" + searchNo + "%'";
            }
            return sql + " order by doctor asc , number asc ";
        }
    }
}
