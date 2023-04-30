package com.test.dao;

import com.test.util.DBUtils;
import com.test.util.LongLog;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class GapResultDao extends DBUtils {
    //查找数据
    public List<GapResult> findResule(String uname) {
        GapResult gapResult=null;
        List<GapResult> gapResultList= new ArrayList<>();
        try {
            getConnection();
            LongLog.printMsg("GapResultDao获取MYSQL连接！");
            String sql ="select * from result where uname=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,uname);
            rs = pStmt.executeQuery();
            while (rs.next()){
//                LongLog.printMsg(rs+"---MYSQL 的 rs查询信息");
                gapResult = new GapResult();
                gapResult.setAccount(rs.getString("account"));
                gapResult.setUname(uname);
//                gapResult.setMtime(rs.getDate("mtime"));
                gapResult.setMstatus(rs.getString("mstatus"));
                gapResult.setmeasurement(rs.getFloat("measurement"));
                gapResultList.add(gapResult);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return gapResultList;
    }
}
