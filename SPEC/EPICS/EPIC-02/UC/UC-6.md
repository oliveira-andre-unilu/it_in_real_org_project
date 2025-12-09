# Use Case: View Employee Working Hours and Locations

**Use Case ID:** UC-6   
**Version:** 1.0    
**Created:** 16/10/2025 
**Last Updated:** 17/10/2025    
**Priority:** `High`    
**Status:** `Draft` 
**Related US:** US-6 [link](../US/US-6.md)  

**Primary Actor:** Admin    
**Secondary Actors:** Data Server   
**Stakeholders:**   

- **Admin:** monitors attendance and work distribution  
- **Company Management:** requires up-to-date employee data 
- **IT Team:** ensures data integrity and system performance    

**Brief Description:**  
This use case describes how an admin views employee working hours and locations to monitor attendance and manage workload distribution.

**Trigger:**    
Admin selects the “Employee Overview” section.

**Preconditions:**  

- Admin is authenticated    
- Data Server connection is active  

**Postconditions:** 

- Employee working hours and locations are displayed accurately 
- Filters adjust the displayed data dynamically 

### Main Success Scenario

| Step | Actor Action | System Response |
|------|---------------|-----------------|
| 1 | Admin selects “Employee Overview” | System displays employee list with hours and locations |
| 2 | Admin applies filters (by date or employee) | System updates the data dynamically |
| 3 | Admin reviews updated results | Displayed data matches selected filters |

### Extensions (Alternative Flows)

**2a. No Data for Filter**  

- **Condition:** No results for selected filters    
- **Action:** System shows “No data available” message  
- **Result:** Admin can reset filters   

### Special Requirements    
**Performance:**    
- Data retrieval within 2 seconds   
- Filter update within 1 second 

**Security:**   
- Data transmitted securely over HTTPS

**Reliability:**    
- System availability 99.5% 
- Automatic error handling for missing data 

**Technical Constraints:**  
- Integration with Data Server API  
- Support for large datasets    

### Open Issues 
- Define page size for large employee lists 