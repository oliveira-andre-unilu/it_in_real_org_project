# QA Test Plan
## Objective

The objective of this test plan is to validate the functional quality, the reliability, and security of the employee shift management application, based on the results of:

* manual tests
* unit tests
* integration tests
and based on the risks identified in the Risk Register.

This plan aims to ensure that critical features are operational and that the main risks are either covered by testing or explicitly accepted.

## Scope of Testing
### Features tested

The tests cover the following use cases:

* UC-1: Monitor Employee Shift
  * Viewing working hours
  * Managing default periods
  * Cases where no data is available
* UC-2: Start a Shift
  * Starting online/offline
  * GPS management
* UC-3: End a Shift
  * Ending a shift online
  * Ending a shift offline (partially tested)
* UC-4: Employee Reminder
  * Sending notifications
  * Redirection from the notification

Backend testing covers:

* business services (Employee, Project, Timestamp, Customer, Auth),
* security and authorization rules,
* exceptions and data validation,
* controller integration testing (partially executed).

### Out of scope

* Large-scale performance
* Load testing
* Full regulatory compliance (GDPR)
* Advanced competition management (multi-admin)


## Testing strategy
### Types of tests

* Manual functional tests
* Unit tests
* Integration tests

### Risk-based prioritization

The following features are considered critical:

* Start and end of shift (UC-2, UC-3)
* Access rights management (Auth, Employee, Admin)
* Working time data integrity

The following features are secondary:

* Notifications (UC-4)
* Offline management of shift end

## Conclusion

The test plan shows that the application is functionally stable for its main uses.  
Critical features have been validated, and identified risks are either covered by testing or clearly accepted with full knowledge of the facts.  
However, improvements are needed in notification management, offline shift, and compliance rules before large-scale deployment.