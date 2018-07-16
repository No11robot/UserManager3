/*
 * 操作数据库的工具类
 */
package util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
public class SqlHelper {
	//定义需要变量
	//与特定数据库连接（会话）
	private static Connection ct=null;
	//表示预编译的SQL语句的对象
	private static PreparedStatement ps=null;
	//数据库结果集的数据表，执行SQL语句生成
	private static ResultSet rs=null;
	private static CallableStatement cs=null;
	//连接数据库参数
	private static String url="";
	private static String username="";
	private static String driver="";
	private static String pwd="";
	//读配置文件
	private static Properties pp=null;
	private static InputStream fis=null;
	public static PreparedStatement getPs() {
		return ps;
	}
	public static ResultSet getRs() {
		return rs;
	}
	public static Connection getCt() {
		return ct;
	}
	public static CallableStatement getCs() {
		return cs;
	}
	//加载驱动，只需一次
	static{
		try {
			//从dbinfo.properties文件中读取配置信息
			pp=new Properties();
			//fis=new FileInputStream("dbinfo.properties");
			//当web项目时，读文件要类加载器
			
			fis=SqlHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");
			pp.load(fis);
			url=pp.getProperty("url");
			username=pp.getProperty("username");
			driver=pp.getProperty("driver");
			pwd=pp.getProperty("pwd");
			Class.forName(driver);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fis=null;
		}
	}
	//得到链接
	public static Connection getConnection() {
		try {
			ct=DriverManager.getConnection(url, username, pwd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ct;
	}
	//分页问题
	public static ResultSet executeQuery2() {
		return null;
	}
	//统一select
	public static ResultSet executeQuery(String sql,String []parameters) {
		try {
			ct=getConnection();
			ps=ct.prepareStatement(sql);
			if(parameters!=null && !parameters.equals("")) {
				for(int i=0;i<parameters.length;i++) {
					ps.setString(i+1, parameters[i]);
				}
			}
			rs=ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//抛出运行异常，可以给调用者函数选择，可处理亦可放弃
			throw new RuntimeException(e.getMessage());
		}finally {
			
		}
		return rs;
	}
	//若有多个增删改，考虑事务
	public static void executeUpdate2(String sql[],String [][] parameters) {
		try {
			//核心
			//获得连接
			ct=getConnection();
			//因为此时用户可能传递多个sql语句
			ct.setAutoCommit(false);
			for(int i=0;i<sql.length;i++) {
				if(parameters[i]!=null) {
					ps=ct.prepareStatement(sql[i]);
					for(int j=0;j<parameters[i].length;j++) {
						ps.setString(j+1, parameters[i][j]);
					}
					ps.executeUpdate();
				}
			}
			//假如出错
			//int i=9/0;
			ct.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//回滚
			try {
				ct.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//抛出运行异常，可以给调用者函数选择，可处理亦可放弃
			throw new RuntimeException(e.getMessage());
		}finally {
			close(rs, ps, ct);
		}
	}
	//先写一个增删改
	public static void executeUpdate(String sql,String [] parameters) {
		//创建一个ps
		try {
			ct=getConnection();
			ps=ct.prepareStatement(sql);
			//给?赋值
			if(parameters!=null) {
				for(int i=0;i<parameters.length;i++) {
					ps.setString(i+1, parameters[i]);
				}
			}
			//执行
			ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//抛出运行异常，可以给调用者函数选择，可处理亦可放弃
			throw new RuntimeException(e.getMessage());
		}finally {
			//关闭资源
			close(rs, ps, ct);
		}
	}
	//关闭资源函数
	public static void close(ResultSet rs,Statement ps,Connection ct) {
		//关闭资源
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs=null;
		}
		if(ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ps=null;
		}
		if(ct!=null) {
			try {
				ct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ct=null;
		}
	}
	//调用存储过程
	//sql call 过程（?,?,?）
	public static void callpro1(String sql,String [] parameters) {
		ct=getConnection();
		try {
			cs=ct.prepareCall(sql);
			//?问号赋值
			if(parameters!=null) {
				for(int i=0;i<parameters.length;i++) {
					cs.setObject(i+1, parameters[i]);
				}
			}
			cs.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally {
			close(rs, cs, ct); 
		}
	}
	//调用存储过程，有返回Result
	public static CallableStatement callpro2
	(String sql,String [] inparameters,Integer [] outparmeters) {
		try {
			ct=getConnection();
			cs=ct.prepareCall(sql);
			if(inparameters!=null) {
				for(int i=0;i<inparameters.length;i++) {
					cs.setObject(i+1, inparameters[i]);
				}
			} 
			//给out参数赋值
			if(outparmeters!=null) {
				for(int i=0;i<inparameters.length;i++) {
					
					cs.registerOutParameter(inparameters.length+1+i, outparmeters[i]);
				}
				cs.execute();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally {
			//无需关闭
		}
		return cs;
	}
}

