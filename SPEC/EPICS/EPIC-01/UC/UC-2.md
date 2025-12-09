## Use Case: Employee Shift Punch-in (Start Shift)

**Use Case ID:** UC-2
**Version:** 1.0  
**Created:** 19/10/2025  
**Last Updated:** 19/10/2025  
**Priority:** <!-- `Critical` | --> `High` <!-- | `Medium` | `Low` -->  
**Status:** `Draft` <!-- | `Reviewed` | `Approved` | `Implemented` -->
**Related US:** US-2 [link](../US/US-2.md)

**Primary Actor:**
- Employee (Application User)

**Secondary Actors:**
- Employer
- Team Leader
- Management

**Stakeholders:**
- Company

**Brief Description:**
This use case describes how an employee starts logging their working hours by using a "Start Shift" function in the mobile application.  
The process records the current timestamp and the GPS location (Car GPS in the future) of the user. This data is sent to the central server after the start of a shift, as soon as there is a stable internet connection (in case of connectivity issue), or after marking the end of the shift.  
This feature ensures accurate time and location tracking and minimizes manual input or reporting errors.

**Trigger:**
The user opens the mobile application and in no more than 3 taps, presses "Start Shift" button to begin recording their working hours.

**Preconditions:**
- The user must be already logged into their account in the application.
- The application must have the appropriate permissions to access time and location data of the device and time servers (critical to prevent time manipulation).
- The server or local storage must be available to record the shift start data.

**Postconditions:**
- The system successfully records the shift start timestamp and GPS coordinates.
- The data is stored on the central (or queued locally for later synchronization).
- The applications displays a confirmation that the shift has started.

### Main Success Scenario

| Step | Actor Action | System Response |
|------|--------------|-----------------|
| 1 | User opens the mobile application. | The application loads the home/dashboard screen. |
| 2 (Optional) | User navigates to shifts of a specific site/location. | The application loads a screen with a "Start Shift" button for the specific site/location. |
| 3 | User taps the "Start Shift" button. | The system validates that no active shift exists for the user. |
| 4 | | The system records the current timestamp and GPS coordinates. |
| 5 | | The system stores the data in the local database and/or sends it to the central server. |
| 6 | | The application displays a confirmation message (example: "Shift started at 08:32"). |
| 7 | User taps "Ok" on the confirmation message. | The application updates the UI to show the ongoing shift status (example: "Shift started at 08:32 (Time worked: 02:45)"). |

### Extensions (Alternative Flows)

**2a. No Internet Connection**
- **Condition:** The application cannot reach the central server.
- **Action:** The system stores the shift start data locally.
- **Result:** Data will be automatically synced once the device reconnects to the internet.

**3a. GPS unavailable**
- **Condition:** The phone's GPS is disabled or unavailable.
- **Action:** The system records the timestamp only and logs a "Location Missing" flag or return location as "null".
- **Result:** The shift still starts successfully, but with a warning message.

**4a. Duplicate Shift Attempt**  
*NOTE: This should probably never occur but safety fallbacks should still be implemented!*
- **Condition:** User tries to start another shift while one is already active.
- **Action:** The system prevents the action and shows an error message (example: "Shift already active").
- **Result:** The application handles the issue in an automated manner without creating duplicate entries.

### Special Requirements
**Performance:**
- The system should record the shift start within **about 2 seconds** of user action.
- The "Start Shift" button should be accessible within **3 taps or fewer** from the launch of the application.

**Security:**
- User authentication via secure credentials or company SSO.
- Shift data must be encrypted during transmission.

**Reliability:**
- System should handle temporary network outages by caching data locally.
- The application should automatically retry synchronization when connection is restored.

**Technical Constraints:**
- Mobile app must be compatible with Android at launch (iOS can be released at a later stage).
- Integration of GPS should keep vehicle tracking system in mind.

### Open Issues
- Should location tracking be mandatory or optional due to privacy concerns?
- How will unsuccessful shift start be handled when the user performs the actions correctly but there is a failure in the system? (Avoid creating issues to employees)

### Related Artifacts
- User Story: US-101