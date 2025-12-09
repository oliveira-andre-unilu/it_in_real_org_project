## Use Case: Monitor Employee Shifts

**Use Case ID:** UC-1
**Version:** 1.0  
**Created:** 18/10/2025  
**Last Updated:** 25/10/2025  
**Priority:** <!-- `Critical` | `High` | `Medium` | --> `Low`  
**Status:** `Draft` <!-- | `Reviewed` | `Approved` | `Implemented` -->
**Related US:** US-1 [link](../US/US-1.md)

**Primary Actor:** Employee 
**Secondary Actors:** Employee  
**Stakeholders:** Company

**Brief Description:**
Monitoring user shifts in an easy and comprehensive way.

**Trigger:**
User (employee) trigger the review your work button.

**Preconditions:**
- The application is turned on and connected to the respective user

**Postconditions:**
- Employee's working hours are displayed
- Employee's information is displayed (name, id, employee_since, etc.)

### Main Success Scenario

| Step | Actor Action | System Response |
|------|--------------|-----------------|
| 1 | Employee clicks on the "Review work button" | Trigger the system to the respective page |
| 2 | Employee selects time that the time period | Trigger the system to generate all the visualizations during the selected time |

### Extensions (Alternative Flows)

**2a. User does not select a time period**
- **Condition:** User choses to not select any prefered time period
- **Action:** Triggers the system to automatically review the last month
- **Result:** Reviews his last month's work

**3a. User does not have any hours of work executed in the respective time**
- **Condition:** User choses a time period where he hasn't executed any hours of work
- **Action:** The system prompts a message saying that there are no hours executed in the selected time period
- **Result:** No visualizations will be shown

### Special Requirements
<!-- Still to finish from here once this is implemented -->
**Performance:**
- [Response time requirements]
- [Throughput requirements]

**Security:**
- [Authentication/authorization needs]
- [Data protection requirements]

**Reliability:**
- [Availability requirements]
- [Error recovery procedures]

**Technical Constraints:**
- [Integration requirements with Gitea, Marp, LaTeX, etc.]

### Open Issues
- [Unresolved questions]
- [Decisions pending]

### Related Artifacts
- [Links to user stories, diagrams, etc.]