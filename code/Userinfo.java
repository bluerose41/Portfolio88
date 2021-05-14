package custom.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserInfo implements Serializable {

	private String name;
	private String yomi;
	private String birthday;
	private String zip;
	private String address;
	private String tel;
	private String email;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYomi() {
		return yomi;
	}
	public void setYomi(String yomi) {
		this.yomi = yomi;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

 }
