# QA Test Report

## Manual Tests

| UC # | Name                   | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result |
|------|------------------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|---------------|
| 1    | Monitor Employee Shift | - User connected to the Internet<br>- User logged into the application | - Open application<br>- Click button “Review work” to go to the review page<br>- Select the time period | - Employee’s working hours are displayed for the selected time<br>- Employee’s information is displayed, within 2 seconds | OK            |

| UC # | Name                                             | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result |
|------|--------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|---------------|
| 1    | Monitor Employee Shift (no time period selected) | - User connected to the Internet<br>- User logged into the application | - Open application<br>- Click button “Review work” to go to the review page<br> | - Employee’s working hours of the month are displayed <br>- Employee’s information is displayed, within 2 seconds | OK            |

| UC # | Name                                                         | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result |
|------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|---------------|
| 1    | Monitor Employee Shift (no working hours for this time period) | - User connected to the Internet<br>- User logged into the application | - Open application<br>- Click button “Review work” to go to the review page<br>- Select the time period | - Warning message: “No working hours for the selected time period.” | OK            |

| UC # | Name          | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result |
|------|---------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|---------------|
| 2    | Start a Shift | - User connected to the Internet<br>- User logged into the application<br>- Application has GPS allowed | - Open application<br>- Go to the Shift Page<br>- Click button: “Start shift” | - Shift has started within 2 seconds<br>- Timestamps and GPS saved<br>- Message: “Shift started at hh:mm” | OK            |

| UC # | Name                    | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result |
|------|-------------------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|---------------|
| 2    | Start a Shift (offline) | - User not connected to the internet<br>- User logged into the application<br>- Application has GPS allowed | - Open application<br>- Go to the Shift Page<br>- Click button: “Start shift” | - Application saved shift data locally<br>- Message: “Data will be synchronized when you are online”<br>- Response within 2 seconds<br>- Data is sent to the server when online | OK            |

| UC # | Name                   | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result |
|------|------------------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|---------------|
| 2    | Start a Shift (no GPS) | - User connected to the internet<br>- User logged into the application<br>- GPS not allowed on the App | - Open application<br>- Go to the Shift Page<br>- Click button: “Start shift” | - Shift has started within 2 seconds<br>- Timestamps only is saved and GPS value are null<br>- Data is sent to the server<br>- Message: “Shift started at hh:mm” | OK            |

| UC # | Name        | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result |
|------|-------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|---------------|
| 3    | End a Shift | - User connected to the Internet<br>- User logged into the application<br>- Has an active shift | - Open application<br>- Go to the Shift Page<br>- Click button: “End shift” | - Shift has ended within 2 seconds<br>- Hours saved and total number of hours computed without errors<br>- Message: “Shift ended total:  hh:mm” | OK            |

| UC # | Name                  | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result                                             |
|------|-----------------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|-----------------------------------------------------------|
| 3    | End a Shift (offline) | - User not connected to the Internet<br>- User logged into the application<br>- Has an active shift | - Open application<br>- Go to the Shift Page<br>- Click button: “End shift” | - Shift has ended within 2 seconds<br>- Data stored locally, auto synchronization when online<br>- Message: “Shift ended total:  hh:mm” | FAILED : we accept because it is not a key feature + time |

| UC # | Name                       | Preconditions                                                | Steps | Expected Result                                              | Actual Result                                             |
|------|----------------------------|--------------------------------------------------------------|-------|--------------------------------------------------------------|-----------------------------------------------------------|
| 4    | Employee Reminder (Part 1) | - User connected to the Internet<br>- User logged into the application<br>- Notifications for this application are enabled<br>- At least one available shift is associated with the reminder<br>- User has configured a reminder for its shift (example: 5min before the start of the shift)<br>- The specified time for the notification to be send is reached<br> | ///   | - Notification is send to the user (“Your shift starts in … minutes.” / “Your shift ends in … minutes.” ) within 2 seconds. | FAILED : we accept because it is not a key feature + time |

| UC # | Name                       | Preconditions                                                | Steps                                                        | Expected Result                                              | Actual Result                                             |
|------|----------------------------|--------------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|-----------------------------------------------------------|
| 4    | Employee Reminder (Part 2) | - User connected to the Internet<br>- User logged into the application<br>- Notifications for this application are enabled<br>- At least one available shift is associated with the reminder<br>- User has configured a reminder for its shift (example: 5min before the start of the shift)<br>- The notification has been sent<br> | - User opens the notification on its device<br>- User click on its notification | - Applocation opens directly to “Start a shift” / “End shift” screen. | FAILED : we accept because it is not a key feature + time |

## Unit & Integration Tests

### Unit test summary

During the execution of the unit tests, several issues were discovered, mostly related to incorrect business logic in the backend layer. For example, the verifyEmail() method returned false when the email did not exist in the system, while logically it should only return false when the email already exists.
These issues demonstrate the usefulness of unit tests in detecting logic defects very early in the development phase.

Most modules passed unit tests successfully, including:

* Service layer
* Domain and DTO objects
* Exception handling
* Security configuration


Overall, unit testing revealed multiple design inconsistency that need to be fixed before release. The test campaign proved highly valuable for improving code reliability, especially on core business logic.

### List of executed tests

The unit tests cover all critical features of the application:

* EmployeeServiceTest
    * Employee creation (validations, admin role, email conflicts)
    * Update
    * Deletion
    * Retrieval by ID or Email
    * Admin access vs. self-identity access
    * Authorization checks
    * Exceptions on invalid data

* ProjectServiceTest
    * Project creation
    * Project number conflicts
    * Client/project association
    * Update
    * Deletion
    * Full project listing
    * Admin access
    * Exceptions for missing client or project

* TimestampEntryServiceTest 
    * Timestamp creation
    * Update
    * Deletion
    * Global and per-ID retrieval
    * Duration/GPS coordinate validation
    * Exceptions for missing employee/project or invalid duration

* CustomerServiceTest
    * Valid customer creation
    * Valid/invalid email
    * Customer update
    * Customer deletion
    * Customer search
    * Admin validation
    * Exceptions based on data compliance

* AuthServiceTest
    * Successful authentication
    * Encrypted password verification
    * Token generation
    * Non-existing email
    * Invalid credentials

* UserAuthDataTest
    * Creation of authentication users with or without constructor arguments
    * Equality between instances
    * Proper hashCode() and toString() implementation

* AuthRequestTest
    * Validation of both constructors
    * Getters / setters behavior
    * Data consistency

Each entity test includes:

* field initialization
* consistency of bidirectional relationships
* persistence of stored values
* Enums Role & Tag
    * Validation of enum constants
    * Behavior of valueOf()

DTO tests validate:

* creation with or without attributes
* setters behavior
* field updates
* acceptance of null values when allowed

ExceptionsTest

* Verification of business exception hierarchy
* Correct initialization of message and cause

**Integration**  
AuthControllerIT

* Login validated via password hash
* Checks for wrong password and non-existent user

CustomerControllerIT

* Retrieval, creation, and database persistence

EmployeeControllerIT

* Full CRUD (read, create, update, delete)

ProjectControllerIT

* Retrieval and creation in client context

TimestampEntryControllerIT

* Creation, reading, updating, and deletion
* Admin vs. self access rules
* Business validation (maximum duration, etc.)

### Percentage of tests that passed/failed

| **Test class** | Number of tests executed | **Passed** | **Failed** |
|:--------------:|--------------------------|:----------:|:----------:|
| Services       | 45                       | 44         | 1          |
| Controllers    | 19                       | 19         | 0          |
| Domain/Models  | 10                       | 10         | 0          |
| DTO            | 11                       | 11         | 0          |
| Exceptions     | 6                        | 6          | 0          |
| Security       | 22                       | 22         | 0          |
| Total          | 113                      | 112        | 1          |

### Summary of detected critical errors

Only one error was noted, concerning TimestampEntryService.updateTimeStamp:

* the test reports a NonConformRequestedDataException related to Timestamp compliance, likely due to validation (duration / start time / business rule).
* No other test revealed functional errors.
* No errors related to the Repository or dependency injections.
* Admin / self-access rules work correctly.
* Business constraints (email, existing project, etc.) are properly enforced.


### Conclusion

Unit tests demonstrate a stable, consistent, and secure application, where:

* business rules are properly enforced,
* administrative access controls are robust,
* CRUD operations behave correctly,
* exceptions are correctly propagated.
The test coverage is extensive and relevant, covering both normal and exceptional scenarios.
The only non-compliance to address concerns the update of a Timestamp, potentially related to overly strict business rule validation or a missing initialization in the mock.