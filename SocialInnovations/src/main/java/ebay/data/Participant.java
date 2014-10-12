package ebay.data;

public class Participant{
	private String emailId = "";
	private String empId = "";
	private String fname = "";
	private String lname = "";
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	public boolean isEmpty(){
		return this.fname.equals("") || this.lname.equals("") || this.empId.equals("") || this.empId.equals("");
	}
	@Override
	public String toString() {
		return "Participant [emailId=" + emailId + ", empId=" + empId
				+ ", fname=" + fname + ", lname="
				+ lname + "]";
	}
	
}
