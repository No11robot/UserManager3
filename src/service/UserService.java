package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.User;
import util.SqlHelper;

public class UserService {
	//����û�
	public boolean addUser(User user) {
		boolean b=true;
		String sql="insert into users values(users_seq.nextval,?,?,?,?)";
		String parameters[]= {user.getName(),user.getEmail(),user.getGrade()+"",user.getPwd()};
		try {
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			b=false;
		}
		return b;
	}
	//�޸��û�
	public boolean updUser(User user) {
		boolean b=true;
		String sql="update users set userName=?,Email=?,grade=?,pwd=? where id=?";
		String parameters[]= {user.getName(),user.getEmail(),user.getGrade()+"",user.getPwd(),user.getId()+""};
		try {
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			b=false;
		}
		return b;
	}
	//ͨ��id��ȡ�û�
	public User getUserById(String id) {
		User user=new User();
		String sql="select * from users where id=?";
		String parameters[]= {id};
		ResultSet rs=SqlHelper.executeQuery(sql, parameters);
		try {
			if(rs.next()) {
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGrade(rs.getInt(4));
				user.setPwd(rs.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return user;
	}
	//ɾ���û�
	public boolean delUser(String id) {
		boolean b=true;
		String sql="delete from users where id=?";
		String parameters[]= {id};
		try {
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			b=false;
		}
		return b;
	}
	//��ȡpageCount
	public int getPageCount(int pageSize) {
		int rowCount=0;
		String sql="select count(*) from users";
		ResultSet rs=SqlHelper.executeQuery(sql, null);
		try {
			rs.next();
			rowCount=rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return (rowCount-1)/pageSize+1;
	}
	//���շ�ҳ��ȡ�û�
	//��˾�� ResultSet-��User����-��ArrayList�����ϣ�
	public ArrayList getUserByPage(int pageNow,int pageSize) {
		ArrayList<User> al=new ArrayList<User>();
		//��ѯ���
		String sql="select * from "
				+ "(select t.*, rownum rn from "
				+ "(select * from users order by id)"
				+ "t where rownum<="+pageSize*pageNow+") "
						+ "where rn>="+(pageSize*(pageNow-1)+1);
		ResultSet rs=SqlHelper.executeQuery(sql, null);
		//���η�װ,��ResultSet-��User����-��ArrayList
		try {
			while(rs.next()) {
				User user=new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGrade(rs.getInt(4));
				//user.setPwd(rs.getString(5));
				al.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return al;
	}

	//�û���½
	public boolean checkUser(User user) {
		boolean b=false;
		String sql="select * from users where userName=? and pwd=?";
		String parameters[]= {user.getName(),user.getPwd()};
		ResultSet rs=SqlHelper.executeQuery(sql, parameters);
		//����rs�ж��û�
		try {
			if(rs.next()) {
				b=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return b;
	}
}
