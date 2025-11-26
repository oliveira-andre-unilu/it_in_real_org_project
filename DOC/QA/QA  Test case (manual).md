<!-- Styling done by André Martins -->
<style>
.card-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin: 30px 0;
}

.card {
  background: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 12px;
  padding: 25px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  transition: transform 0.2s, box-shadow 0.2s;
}

.card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0,0,0,0.12);
}

.card h2 {
  margin-top: 0;
  color: #2c3e50;
  border-bottom: 2px solid #3498db;
  padding-bottom: 10px;
}

.card-content {
  color: #555;
  line-height: 1.6;
}

.card-link {
  display: inline-block;
  margin-top: 15px;
  padding: 8px 16px;
  background: #3498db;
  color: white;
  text-decoration: none;
  border-radius: 6px;
  font-size: 14px;
}

.card-link:hover {
  background: #2980b9;
}
</style>

# QA : Test case (manual)


<div class="card">

## Monitor employee shifts (time period selected) UC-1

<div class="card-content">

### Preconditions:
* User connected
### Steps:
* Open application
* Click button “Review work” to go to the review page
* Select the time period
### Expected Result:
* Employee’s working hours are displayed for the selected time and Employee’s information is displayed, within 2 seconds

</div>
</div>

***

<div class="card">

## Monitor employee shifts (no time perido selected) UC-1

<div class="card-content">

### Preconditions:
* User connected
### Steps:
* Open application
* Click button “Review work” to go to the review page
### Expected Result:
* Employee’s working hours of the month are displayed and Employee’s information is displayed, within 2 seconds

</div>
</div>

***

<div class="card">

## Monitor employee shifts (no working hours for this time period) UC-1

<div class="card-content">

### Preconditions:
* User connected
### Steps:
* Open application
* Click button “Review work” to go to the review page
* Select the time period
### Expected Result:
* Warning message: “No working hours for the selected time period.”

</div>
</div>

***

<div class="card">

## Start a shift UC-2

<div class="card-content">

### Preconditions:
* User connected
* Application has GPS allowed (optional)
### Steps:
* Open application
* Go to the shift page 
* Click button “Start shift”
### Expected Result:
* Shift has started within 2 seconds
* Timestamps and GPS saved
* Message “Shift started at hh:mm”

</div>
</div>

***

<div class="card">

## Start a shift (offline) UC-2

<div class="card-content">

### Preconditions:
* User connected
* Application has GPS allowed
* offline
### Steps:
* Open application
* Go to the shift page 
* Click button “Start shift”
### Expected Result:
* Application saved shift data locally
* Message : “Data will be synced when online
* Response within 2 seconds
* Data is sent to the server when online

</div>
</div>

***

<div class="card">

## Start a shift (no GPS) UC-2

<div class="card-content">

### Preconditions:
* User connected
* Application has NOT GPS allowed
### Steps:
* Open application
* Go to the shift page 
* Click button “Start shift”
### Expected Result:
* Shift has started within 2 seconds
* Timestamps only is saved and GPS value are null
* Message “Shift started at hh:mm”

</div>
</div>

***

<div class="card">

## Start a shift (Duplicate) UC-2 DON’T KNOW IF THIS POSSIBLE

<div class="card-content">

### Preconditions:
* User connected
* Has already an “active” shift
### Steps:
* Open application
* Go to the shift page 
* Click button “Start shift”
### Expected Result:
* Error message : “You already have an active shift”

</div>
</div>

***

<div class="card">

## End a shift (usual) UC-3

<div class="card-content">

### Preconditions:
* User connected
* Has an active shift
### Steps:
* Open application
* Go to the shift page 
* Click button “End shift”
### Expected Result:
* Shift has ended within 2 seconds
* Hours saved and total number of hours computed without errors
* Message “Shift ended total:  hh:mm”

</div>
</div>

***

<div class="card">

## End a shift (offline) UC-3

<div class="card-content">

### Preconditions:
* User connected
* Has an active shift
* offline
### Steps:
* Open application
* Go to the shift page 
* Click button “End shift”
### Expected Result:
* Shift has ended within 2 seconds
* Data stored locally, auto synchronization when online
* Message “Shift ended total:  hh:mm”

</div>
</div>

***

<div class="card">

## End a shift (no shift started) UC-3 (NOT POSSIBLE FOR THE MOMENT)

<div class="card-content">

### Preconditions:
* User connected
* Don’t have any active shift
### Steps:
* Open application
* Go to the shift page 
* Click button “End shift”
### Expected Result:
* Error message: “No active shift”

</div>
</div>

***

<div class="card">

## Employee Reminder (Part 1) UC-4

<div class="card-content">

### Preconditions:
* User is logged in
* Phone of the user connected to the internet
* Notifications for this application are enabled
* At least one available shift is associated with the reminder
* User has configured a reminder for its shift (example: 5min before the start of the shift)
* The specified time for the notification to be send is reached
### Steps:
* No extra steps
### Expected Result:
* Notification is send to the user (“Your shift starts in … minutes.” / “Your shift ends in … minutes.” ) within 2 seconds. 

</div>
</div>

***

<div class="card">

## Employee Reminder (Part 2) UC-4

<div class="card-content">

### Preconditions:
* User is logged in
* Phone of the user connected to the internet
* Notifications for this application are enabled
* At least one available shift is associated with the reminder
* User has configured a reminder for its shift (example: 5min before the start of the shift)
* The notification has been sent
### Steps:
* User opens the notification on its device
* User click on its notification
### Expected Result:
* Applocation opens directly to “Start a shift” / “End shift” screen. 

</div>
</div>

***