## Use Case: Monitor Employee Shifts

**Use Case ID:** UC-100  
**Version:** 1.0  
**Created:** 18/10/2025  
**Last Updated:** 18/10/2025  
**Priority:** <!-- `Critical` | `High` | `Medium` | --> `Low`  
**Status:** `Draft` <!-- | `Reviewed` | `Approved` | `Implemented` -->

**Primary Actor:** Employer/Team Leader/Management  
**Secondary Actors:** Employee  
**Stakeholders:** Company

**Brief Description:**
Monitoring Employee Shifts allows those who manage their employees to review their working hours to facilitate attendance checking and shift compliance verification.

**Trigger:**
User (with proper privilege) reviews specific employee.

**Preconditions:**
- User must have proper privilege
- The application has a connection to the servers holding employee data

**Postconditions:**
- Employee's working hours are displayed
- Employee's information is displayed (name, id, employee_since, etc.)

### Main Success Scenario

| Step | Actor Action | System Response |
|------|--------------|-----------------|
| 1 | [Actor does something] | [System responds] |
| 2 | [Actor provides input] | [System processes and responds] |
| 3 | [Actor confirms] | [System completes the operation] |

### Extensions (Alternative Flows)

**2a. [Alternative Scenario Name]**
- **Condition:** [When does this alternative occur?]
- **Action:** [What does the system do differently?]
- **Result:** [Where does the flow resume?]

**3a. [Error Condition Handling]**
- **Condition:** [What error occurs?]
- **Action:** [How does the system handle it?]
- **Result:** [Does the use case continue or terminate?]

### Special Requirements
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