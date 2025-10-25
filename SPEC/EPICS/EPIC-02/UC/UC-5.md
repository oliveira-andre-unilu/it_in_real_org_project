# Use Case: Secure Admin Login

**Use Case ID:** UC-5
**Version:** 1.0
**Created:** 16/10/2025
**Last Updated:** 17/10/2025
**Priority:** `High`
**Status:** `Draft`
**Related US:** US-5 [link](../US/US-5.md)

**Primary Actor:** Admin
**Secondary Actors:** Authentication Service
**Stakeholders:**
- **Admin:** needs secure access to the platform
- **IT/Security Team:** requires proper authentication and data protection
- **Company Management:** wants controlled access to administrative functions

**Brief Description:**
This use case describes how an admin securely logs in to the Admin Platform to access administrative functions, ensuring that only authorized users can enter the system.

**Trigger:**  
The admin attempts to access the Admin Platform.

**Preconditions:**
- Admin has valid credentials
- The Admin Platform is online and accessible

**Postconditions:**
- Admin is authenticated and redirected to the Admin Dashboard

### Main Success Scenario

| Step | Actor Action | System Response |
|------|---------------|-----------------|
| 1 | Admin opens the Admin Platform | System displays the login form |
| 2 | Admin enters username and password | System securely transmits and validates credentials |
| 3 | Admin is authenticated | System redirects to the Admin Dashboard |

### Extensions (Alternative Flows)

**2a. Invalid Login**
- **Condition:** Credentials are invalid
- **Action:** System displays an error and logs the attempt
- **Result:** Admin may retry login

### Special Requirements
**Performance:**
- Login validation within 2 seconds

**Security:**
- HTTPS for all connections
- Passwords hashed and encrypted
- JWT-based authentication
- Session timeout after inactivity

**Reliability:**
- Authentication service uptime >= 99.5%

**Technical Constraints:**
- Integration with Authentication Service via REST API

### Open Issues
- Define session timeout duration

### Related Artifacts
- US-101