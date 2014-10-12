package ebay.controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebay.dao.EmployeeDataDao;
import ebay.dao.EmployeeDataDaoImpl;
import ebay.data.Employee;

@RestController
public class ExcelDataController {
	
	
	EmployeeDataDao employeeDataDao;
	
	public EmployeeDataDao getEmployeeDataDao() {
		return employeeDataDao;
	}

	public void setEmployeeDataDao(EmployeeDataDao employeeDataDao) {
		this.employeeDataDao = employeeDataDao;
	}

	
    
    @RequestMapping(method = RequestMethod.GET, value = "/employee")
    public Employee greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
    	Employee emp = null;
    	employeeDataDao = new EmployeeDataDaoImpl();
		try {
			emp = employeeDataDao.getEmployee();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return emp;
    }
    
}