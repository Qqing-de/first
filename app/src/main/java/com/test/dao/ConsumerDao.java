package com.test.dao;

import com.test.util.DBUtils;
import com.test.util.LongLog;

/**
 * 用户操作类
 * CURD操作
 */
public class ConsumerDao extends DBUtils {

    //登录
    public Consumer login(String uname,String password) {
        Consumer consumer=null;
        try {
            getConnection();
            String sql ="select * from consumer where uname=? and password=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,uname);
            pStmt.setString(2,password);
            rs = pStmt.executeQuery();
            while (rs.next()){
                consumer = new Consumer();
                consumer.setAccount(rs.getString("account"));
                consumer.setUname(uname);
                consumer.setPassword(password);
                consumer.setSex(rs.getString("sex"));
                consumer.setAge(rs.getInt("age"));
                consumer.setWeight(rs.getInt("weight"));
                consumer.setPhone(rs.getString("phone"));
                consumer.setMeasure(rs.getFloat("measure"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
     return consumer;
    }

    //注册
    public int resgister(Consumer consumer) {
        int res=0;
        try {
            getConnection();
            String sql ="INSERT INTO consumer (account,password,uname,phone) VALUES (?,?,?,?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,consumer.getAccount());
            pStmt.setString(2,consumer.getPassword());
            pStmt.setString(3,consumer.getUname());
            pStmt.setString(4,consumer.getPhone());
            res = pStmt.executeUpdate();
            LongLog.printMsg(String.valueOf(res)+"-------------res");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return res;
    }
    public int editMsg(Consumer consumer) {
        int res=0;
        try {
            getConnection();
            String sql ="UPDATE consumer SET sex=?,weight=?,phone=?,age=? WHERE uname =?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,consumer.getSex());
            pStmt.setInt(2,consumer.getWeight());
            pStmt.setString(3,consumer.getPhone());
            pStmt.setInt(4,consumer.getAge());
            pStmt.setString(5,consumer.getUname());
            res = pStmt.executeUpdate();
            LongLog.printMsg(String.valueOf(res)+"----------修改---res");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return res;
    }

    //查询用户存在
    public Consumer findByUname(String uname) {
        Consumer consumer=null;
        try {
            getConnection();
            String sql ="select * from consumer where uname=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,uname);
            rs = pStmt.executeQuery();
            while (rs.next()){
                consumer = new Consumer();
                consumer.setAccount(rs.getString("account"));
                consumer.setUname(uname);
                consumer.setPassword(rs.getString("password"));
                consumer.setSex(rs.getString("sex"));
                consumer.setAge(rs.getInt("age"));
                consumer.setWeight(rs.getInt("weight"));
                consumer.setPhone(rs.getString("phone"));
                consumer.setMeasure(rs.getFloat("measure"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return consumer;
    }
}
