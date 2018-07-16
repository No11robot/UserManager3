package domain;

public class User {
	
	private int id;
	private int grade;
	private String name;
	private String pwd;
	private String email;
	public User() {
		
	}
	public User(int id, int grade, String name, String pwd, String email) {
		super();
		this.id = id;
		this.grade = grade;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
