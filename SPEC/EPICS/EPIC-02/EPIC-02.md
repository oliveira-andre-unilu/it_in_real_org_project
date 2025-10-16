# Epic: Timelink Admin Platform

**Epic ID:** EPIC-002
**Status:** `Proposed`
**Priority:** `High`
**Theme:** Helping managers and CEO's manage the working hours of their employees

**Created:** 15/10/2025
**Last Updated:** 16/10/2025
**Target Release:** 08/12/2025

---

## Epic Overview

**Business Problem:**
Currently all managers do quite struggle to manage and monitor the working hours of their workers and working places respectively. It is really easy to make lose the amount of labor that has been relaised in a working place or individual in live and in an easy way.

**Business Value:**
The Admin Platform helps companies save time and reduce errors by showing all information in one clear system. It makes it easier to see all the data represented in several graphical representations.

**Success Metrics:**
- Reduce manual work for administrators by at least 60%
- 100% synchronization between Admin Platform and Data Server
- Reach 95% user satisfaction in the first release
- System uptime above 99.9% during testing

---

## Scope

### In Scope
- Build a web-based Admin Platform using React Native
- Connect the Admin Platform to the main Data Server (Spring Boot / SQL)
- Create login and role-based access (admin users only)
- Add dashboards with tables and graphs for all the employee data
- Allow basic CRUD functions (create, read, update, delete) for all employee data

### Out of Scope
- Full integration with external systems (like GPS tracking)
- Data export in XML or other formats
- Mobile version for admin users (web only for now)

---

## User Personas
- **Admin User:** Works in HR or management. Needs to see and manage all employee data, hours, and working places quickly.
- **Company Manager:** Supervises multiple workplaces and wants to be able to easily review statistics and reports.

---

## User Stories
| Story ID | Story Title | Status | Priority |
|----------|-------------|---------|----------|
| US-101 | As an admin, I want to log in securely to the Admin Platform | `Backlog` | `High` |
| US-102 | As an admin, I want to view employee working hours and working places | `Backlog` | `High` |
| US-103 | As an admin, I want to see graphs of total hours worked per working place | `Backlog` | `Medium` |
| US-104 | As an admin, I want to add and remove users from the system | `Backlog` | `Medium` |
| US-105 | As an admin, I want to see connection status with the Data Server | `Backlog` | `Low` |

---

## Technical Considerations

**Architecture Impact:**
- The Admin Platform will communicate directly with the Data Server using a REST Controller.
- Requires a new web frontend built with React Native for Web.
- Backend data comes from the existing Spring Boot server with SQL database.

**Dependencies:**
- Data Server must be ready before Admin Platform API integration.

**Risks & Mitigations:**
- **Risk 1:** Admin Platform may load slowly with big data sets – *Mitigation:* Optimize queries. 
- **Risk 2:** Inconsistent data between server and UI – *Mitigation:* Add sync checks and server-side validation.

**Non-Functional Requirements:**  
- **Performance:** Pages should load in under 2 seconds.
- **Security:** Only authorized admin users can log in.
- **Scalability:** Should support up to 100 admin users per company.
- **Reliability:** 99.9% uptime expected in testing phase.

---

## Definition of Ready
- [x] Business value clearly defined
- [x] Success metrics established
- [x] High-level scope approved
- [x] User stories identified and estimated
- [ ] Technical dependencies resolved
- [ ] UX/design completed (not started yet)  

---

## Definition of Done
- [ ] All user stories completed and accepted
- [ ] Success metrics validated
- [ ] Documentation updated
- [ ] Integration tests passing
- [ ] Performance requirements met
- [ ] Deployed to production
- [ ] Business stakeholders signed off

---

## Open Questions
- Is multi-language support required for version 1?

---

## Related Artifacts
- [Links to wireframes, technical designs, research, etc.]