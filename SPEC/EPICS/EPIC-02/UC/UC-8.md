# Use Case: Manage Users (Add/Remove)

**Use Case ID:** UC-8   
**Version:** 1.0    
**Created:** 16/10/2025 
**Last Updated:** 17/10/2025    
**Priority:** `Medium`  
**Status:** `Draft` 
**Related US:** US-8 [link](../US/US-8.md)  

**Primary Actor:** Admin    
**Secondary Actors:** Authentication Service    
**Stakeholders:**   
- **Admin:** manages access and user roles  
- **IT/Security Team:** ensures controlled access   
- **Company Management:** maintains updated user records    

**Brief Description:**  
This use case describes how an admin adds or removes users to manage access and keep the system user list current.

**Trigger:**    
Admin navigates to the “User Management” section.

**Preconditions:**  

- Admin is logged in    
- Admin has permission to manage users  

**Postconditions:** 
- User list is updated and changes are reflected immediately 

### Main Success Scenario

| Step | Actor Action | System Response |
|------|---------------|-----------------|
| 1 | Admin selects “Add User” or “Remove User” | System displays user form |
| 2 | Admin enters or selects user details | System validates inputs |
| 3 | Admin confirms action | System applies change and displays confirmation |

### Extensions (Alternative Flows)

**3a. Invalid or Duplicate Entry**  

- **Condition:** User data invalid or duplicate detected    
- **Action:** System shows error message  
- **Result:** Admin corrects input and retries  

### Special Requirements    
**Performance:**    
- CRUD operations complete within 2 seconds  

**Security:**   
- Role-based access control  
- Input validation for user data  

**Reliability:**    
- Changes instantly applied in user list  

**Technical Constraints:**  
- CRUD endpoints integrated with Authentication Service  

### Open Issues 
- Define detailed role and permission levels  