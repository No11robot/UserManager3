/*
 * �������ݿ�Ĺ�����
 */
package util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
public class SqlHelper {
	//������Ҫ����
	//���ض����ݿ����ӣ��Ự��
	private static Connection ct=null;
	//��ʾԤ�����SQL���Ķ���
	private static PreparedStatement ps=null;
	//���ݿ����������ݱ�ִ��SQL�������
	private static ResultSet rs=null;
	private static CallableStatement cs=null;
	//�������ݿ����
	private static String url="";
	private static String username="";
	private static String driver="";
	private static String pwd="";
	//�������ļ�
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
	//����������ֻ��һ��
	static{
		try {
			//��dbinfo.properties�ļ��ж�ȡ������Ϣ
			pp=new Properties();
			//fis=new FileInputStream("dbinfo.properties");
			//��web��Ŀʱ�����ļ�Ҫ�������
			
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
	//�õ�����
	public static Connection getConnection() {
		try {
			ct=DriverManager.getConnection(url, username, pwd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ct;
	}
	//��ҳ����
	public static ResultSet executeQuery2() {
		return null;
	}
	//ͳһselect
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
			//�׳������쳣�����Ը������ߺ���ѡ�񣬿ɴ�����ɷ���
			throw new RuntimeException(e.getMessage());
		}finally {
			
		}
		return rs;
	}
	//���ж����ɾ�ģ���������
	public static void executeUpdate2(String sql[],String [][] parameters) {
		try {
			//����
			//�������
			ct=getConnection();
			//��Ϊ��ʱ�û����ܴ��ݶ��sql���
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
			//�������
			//int i=9/0;
			ct.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//�ع�
			try {
				ct.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//�׳������쳣�����Ը������ߺ���ѡ�񣬿ɴ�����ɷ���
			throw new RuntimeException(e.getMessage());
		}finally {
			close(rs, ps, ct);
		}
	}
	//��дһ����ɾ��
	public static void executeUpdate(String sql,String [] parameters) {
		//����һ��ps
		try {
			ct=getConnection();
			ps=ct.prepareStatement(sql);
			//��?��ֵ
			if(parameters!=null) {
				for(int i=0;i<parameters.length;i++) {
					ps.setString(i+1, parameters[i]);
				}
			}
			//ִ��
			ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//�׳������쳣�����Ը������ߺ���ѡ�񣬿ɴ�����ɷ���
			throw new RuntimeException(e.getMessage());
		}finally {
			//�ر���Դ
			close(rs, ps, ct);
		}
	}
	//�ر���Դ����
	public static void close(ResultSet rs,Statement ps,Connection ct) {
		//�ر���Դ
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
	//���ô洢����
	//sql call ���̣�?,?,?��
	public static void callpro1(String sql,String [] parameters) {
		ct=getConnection();
		try {
			cs=ct.prepareCall(sql);
			//?�ʺŸ�ֵ
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
	//���ô洢���̣��з���Result
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
			//��out������ֵ
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
			//����ر�
		}
		return cs;
	}
}

