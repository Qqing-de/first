package com.test.util;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

//mysql连接数据库的辅助工具类
public class DBUtils {
    /**
     * 电脑的IP地址 192.168.56.1  qq的
     * 10.9.15.174 zj的
     * */
    private static final String url="jdbc:mysql://192.168.56.1:3306/xuetang?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
    private static final String user="root2";/*数据库用户名*/
    private static final String password="123456";/*进入数据库的密码*/
    public static Connection conn;    //连接对象
    public static Statement stmt;        //命令集
    public static PreparedStatement pStmt;  //预编译命令集
    public static ResultSet rs;                  //结果集
    private static final String TAG = "DBUtils";
    public static void getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //关闭数据库操作对象
    public static void closeAll(){
        try {
            if (rs!=null){
                rs.close();
                rs=null;
            }
            if (stmt!=null){
                stmt.close();
                stmt=null;
            }
            if (pStmt!=null){
                pStmt.close();
                pStmt=null;
            }
            if (conn!=null){
                conn.close();
                conn=null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}