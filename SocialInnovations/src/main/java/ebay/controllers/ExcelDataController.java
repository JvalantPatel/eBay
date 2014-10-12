package ebay.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ebay.dao.EmployeeDataDao;
import ebay.dao.EmployeeDataDaoImpl;
import ebay.data.Employee;
import ebay.data.FilterResponseData;
import ebay.data.LoginBean;
import ebay.data.Participant;
import ebay.data.UserSignUp;
import ebay.exception.UserDataException;
import ebay.utilities.ExcelReader;

@RestController
public class ExcelDataController {
	
	
	private EmployeeDataDao employeeDataDao;
	    
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
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
    	employeeDataDao = new EmployeeDataDaoImpl();
        if (!file.isEmpty()) {
            try {
            	
            	XSSFWorkbook book = new XSSFWorkbook(file.getInputStream());
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
//                stream.write(bytes);
//                stream.close();
            	ExcelReader reader = new ExcelReader();
            	Map<String, Object> resultMap = reader.readEventData(book);
				Map<String, Participant> pMap = (Map<String, Participant>) resultMap.get("participant");
        		Set<String> locationSet = (Set<String>) resultMap.get("location");
        		
        		if(pMap != null && locationSet != null){
        			System.out.println("calling dao");
        			for(String s: pMap.keySet()){
        				System.out.println(s);
        			}
        			
        			employeeDataDao.insertHistoricData(pMap, locationSet, "Intial Test", 2012);
        		}
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/createuser")
    public String addUser(@RequestBody UserSignUp userSignUp) {
      
    	employeeDataDao = new EmployeeDataDaoImpl();
    	try {
    		System.out.println(userSignUp);
			employeeDataDao.createUser(userSignUp);
		} catch (IOException | SQLException | UserDataException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "failed";
		}
		return "pass";    
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@RequestBody LoginBean loginBean) {
        Boolean userAuthorized=false;
    	employeeDataDao = new EmployeeDataDaoImpl();
    	try {
    		System.out.println(loginBean);
    		userAuthorized=employeeDataDao.authorizeUser(loginBean);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			
			ex.printStackTrace();
			return "failed";
		}
    	if(userAuthorized) return "pass";
    	else return "fail";
		
    }
    
    
    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public List<String> getAllEvents() {
        List<String> listEvent=null;
        employeeDataDao = new EmployeeDataDaoImpl();
    	try {
    		
    		listEvent=employeeDataDao.getAllEvents();
		} catch (Exception ex) {
			// TODO Auto-generated catch block			
			ex.printStackTrace();
		}
    	return listEvent;		
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "events/location")
    public List<String> getAllLocations() {
        List<String> listEvent=null;
        employeeDataDao = new EmployeeDataDaoImpl();
    	try {
    		
    		listEvent=employeeDataDao.getAllLocations();
		} catch (Exception ex) {
			// TODO Auto-generated catch block			
			ex.printStackTrace();
		}
    	return listEvent;		
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/events/year")
    public List<String> getAllYears() {
        List<String> listEvent=null;
        employeeDataDao = new EmployeeDataDaoImpl();
    	try {
    		
    		listEvent=employeeDataDao.getAllYears();
		} catch (Exception ex) {
			// TODO Auto-generated catch block			
			ex.printStackTrace();
		}
    	return listEvent;		
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/getList/byYear")
    public List<FilterResponseData> getParticipationListByYear(@RequestParam("year") String yearKey) {
        List<FilterResponseData> list=null;
        employeeDataDao = new EmployeeDataDaoImpl();
    	try {
    		if(yearKey == null || yearKey.equals("")){
    			yearKey = "all";
    		}
    		list=employeeDataDao.getParticipationListByCondition("year", yearKey);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	return list;		
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/getList/byEvent")
    public List<FilterResponseData> getParticipationListByEvent(@RequestParam("event") String eventName) {
        List<FilterResponseData> list=null;
        employeeDataDao = new EmployeeDataDaoImpl();
    	try {
    		if(eventName == null || eventName.equals("")){
    			eventName = "all";
    		}
    		list=employeeDataDao.getParticipationListByCondition("event", eventName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	return list;		
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/getList/byLocation")
    public List<FilterResponseData> getParticipationListByLocation(@RequestParam("location") String location) {
        List<FilterResponseData> list=null;
        employeeDataDao = new EmployeeDataDaoImpl();
    	try {
    		if(location == null || location.equals("")){
    			location = "all";
    		}
    		list=employeeDataDao.getParticipationListByCondition("location", location);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	return list;		
    }
    
   
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/uploadYearData", method=RequestMethod.POST)
    public @ResponseBody String uploadYearData(@RequestParam("year") String year,
            @RequestParam("file") MultipartFile file){
    	employeeDataDao = new EmployeeDataDaoImpl();
        if (!file.isEmpty()) {
            try {
            	
            	XSSFWorkbook book = new XSSFWorkbook(file.getInputStream());
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
//                stream.write(bytes);
//                stream.close();
            	ExcelReader reader = new ExcelReader();
            	Map<String, Object> resultMap = reader.readYearData(book);
            	
            	
    			Map<Integer, List<String>> eventUserMap = (Map<Integer, List<String>>) resultMap.get("eventUserMap"); 
    			Map<String, List<String>> eventLocationMap = (Map<String, List<String>>) resultMap.get("eventLocationMap");
    			Map<String, Integer> eventMap = (Map<String, Integer>) resultMap.get("eventMap");
				Map<String, Participant> pMap = (Map<String, Participant>) resultMap.get("participant");
        		
        		if(pMap != null && eventUserMap != null && eventLocationMap != null && eventMap != null){
        			System.out.println("calling dao");
        			String s = employeeDataDao.insertYearData(pMap, eventUserMap, eventLocationMap, eventMap, year);
        		}
                return "You successfully uploaded " + year + " into " + year + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + year + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + year + " because the file was empty.";
        }
    }
}