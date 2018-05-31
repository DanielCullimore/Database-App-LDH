package DataEntries;

import java.util.Date;

public class ProfitEntry {

	public String getEmployeeUsername() {
		return EmployeeUsername;
	}
	public void setEmployeeUsername(String employeeUsername) {
		EmployeeUsername = employeeUsername;
	}
	public Date getStartContractDate() {
		return StartContractDate;
	}
	public void setStartContractDate(Date startContractDate) {
		StartContractDate = startContractDate;
	}
	public Date getEndContractDate() {
		return EndContractDate;
	}
	public void setEndContractDate(Date endContractDate) {
		EndContractDate = endContractDate;
	}
	
	public ProfitEntry(String employeeUsername, Date startContractDate, Date endContractDate) {
		super();
		EmployeeUsername = employeeUsername;
		StartContractDate = startContractDate;
		EndContractDate = endContractDate;
	}
	
	String EmployeeUsername;
	Date StartContractDate;
	Date EndContractDate;

}
