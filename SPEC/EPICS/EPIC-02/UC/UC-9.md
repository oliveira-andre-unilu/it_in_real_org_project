# Use Case: Monitor Data Server Connection

**Use Case ID:** UC-9 
**Version:** 1.0
**Created:** 16/10/2025
**Last Updated:** 17/10/2025  
**Priority:** `Low`
**Status:** `Draft`
**Related US:** US-9 [link](../US/US-9.md)

**Primary Actor:** Admin
**Secondary Actors:** Data Server
**Stakeholders:**
- **Admin:** needs visibility on data availability
- **IT Team:** monitors server uptime and connectivity
- **Company Management:** depends on data accuracy and availability

**Brief Description:**
This use case describes how an admin monitors the real-time connection status to the Data Server to ensure the system is up-to-date.

**Trigger:**
Admin checks the connection status indicator or system shows a warning.

**Preconditions:**
- Admin is logged in
- Connection check mechanism is active

**Postconditions:**
- Connection status (green/red) displayed and updated in real time

### Main Success Scenario

| Step | Actor Action | System Response |
|------|---------------|-----------------|
| 1 | Admin opens dashboard | System shows Data Server connection status |
| 2 | Connection changes | System automatically updates the indicator |

### Extensions (Alternative Flows)

**2a. Data Server Unreachable** 
- **Condition:** Ping to server fails
- **Action:** System shows warning message and marks status red
- **Result:** Admin is informed and may contact IT support

### Special Requirements
**Performance:**
- Status check performed every 10 seconds

**Security:**
- All communication over HTTPS 

**Reliability:**
- System availability >= 99.5%  
- Good handling of temporary disconnections  

**Technical Constraints:**
- Ping mechanism for connectivity
- Real-time status updates

### Open Issues
- /

### Related Artifacts
- US-105