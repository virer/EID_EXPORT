package EID_EXPORT;

import java.awt.image.BufferedImage;

public class MyeID {
	private String firstName; 
	private String lastName;
	private String dob;
	private String gender;
	private String nat_id;
	private BufferedImage photo;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = String.valueOf(firstName);
	}
	public String getLastName() {
		return String.valueOf(lastName);
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNat_id() {
		return nat_id;
	}
	public void setNat_id(String nat_id) {
		this.nat_id = nat_id;
	}
	public BufferedImage getPhoto() {
		return photo;
	}
	public void setPhoto(BufferedImage photo) {
		this.photo = photo;
	}
	
	
}