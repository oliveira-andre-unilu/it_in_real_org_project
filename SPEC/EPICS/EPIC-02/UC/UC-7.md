# Use Case: View Workload Charts by Location

**Use Case ID:** UC-7
**Version:** 1.0
**Created:** 16/10/2025
**Last Updated:** 17/10/2025
**Priority:** `Medium`
**Status:** `Draft`
**Related US:** US-7 [link](../US/US-7.md)

**Primary Actor:** Admin
**Secondary Actors:** Data Server
**Stakeholders:**
- **Admin:** analyzes workload distribution visually
- **Company Management:** requires visual insights for decision-making
- **IT Team:** maintains data consistency and responsiveness

**Brief Description:**
This use case describes how an admin views visual charts (bar/pie) of total hours worked per location for better workload analysis.

**Trigger:**
Admin selects the “View Charts” section.

**Preconditions:**
- Admin is logged in
- Employee work data is available

**Postconditions:**
- Charts display total working hours per location
- Charts update dynamically when filters change

### Main Success Scenario

| Step | Actor Action | System Response |
|------|---------------|-----------------|
| 1 | Admin navigates to “View Charts” | System generates visual charts for total hours per location |
| 2 | Admin applies filters | Charts update dynamically |

### Extensions (Alternative Flows)

**2a. No Data for Chart**
- **Condition:** No matching data for filter
- **Action:** System shows “No data available” message
- **Result:** Admin may adjust filters

### Special Requirements
**Performance:**
- Chart rendering < 2 seconds

**Security:**
- Data retrieved securely via API

**Reliability:**
- Charts reflect real-time data updates

**Technical Constraints:**
- Charts implemented with d3.js or equivalent
- Responsive design for desktop and tablet

### Open Issues
- Determine chart types (bar, pie, line)  

### Related Artifacts
- US-103