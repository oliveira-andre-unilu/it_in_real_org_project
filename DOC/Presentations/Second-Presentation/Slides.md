---
marp: true
html: true
theme: uncover
paginate: true
class: invert
transition: slide-up
style: |
  @keyframes marp-outgoing-transition-slide-up {
    from { transform: translateY(0%); }
    to { transform: translateY(-100%); }
  }
  @keyframes marp-incoming-transition-slide-up {
    from { transform: translateY(100%); }
    to { transform: translateY(0%); }
  }
---
<style lang=css>
@import 'default';

section {
  position: relative;
  font-family: "Segoe UI", "Helvetica Neue", Arial, sans-serif;
  font-size: 28px;
  line-height: 1.5;
  background-color: #121212;
  background-image: url("../../Assets/Main_logo.png");
  background-repeat: no-repeat;
  background-position: bottom left;
  background-size: 150px, cover;
}

h1, h2, h3 {
  font-weight: 700;
  color: #88c0d0;
  width: fit-content;
  margin:auto;
  margin-bottom: 50px;
  background-color: rgba(0,0,0,0.5);
  padding: 5px;
  padding-left:20px;
  padding-right:20px;
  border-radius: 25px;
}

p, li {
  color: #EAEAEA;
}

strong {
  color: #00E5FF;
}

code {
  background: #161B22;
  color: #79C0FF;
  padding: 0.2em 0.4em;
  border-radius: 4px;
}


ul:not(ul ul) {
  display: inline-block;
  background-color: rgba(0,0,0,0.5);
  padding: 5px;
  padding-left: 50px;
  border-radius: 25px;
}

p:not(li p){
  display: inline-block;
  background-color: rgba(0,0,0,0.5);
  padding: 5px;
  border-radius: 25px;
  padding-left:20px;
  padding-right:20px;
}

.admonition {
  border-left: 6px solid #2196f3;
  background-color: #2e2e2eff;
  padding: 1em;
  border-radius: 8px;
  margin: 1em 0;
}
.admonition.warning {
  border-left-color: #ff9800;
  background-color: #2e2e2eff;
}
.admonition.tip {
  border-left-color: #6F8D6A;
  background-color: #2e2e2eff;
}
</style>

# Timelink - Specification
![bg blur:2px](../../Assets/old-clock.jpg)

Presented by **LAMTCo Solutions**

<!-- Andre -->

---

# Overview

![bg blur:2px](../../Assets/old-clock.jpg)

- Specification
- Main Design
- UI Concepts
- Identified Risks 

<!-- Andre -->

---
![bg blur:2px](./Assets/todo_list.jpg)

# Specification

## User Stories

<div class="admonition warning">
  All the use cases have been implemented but will not be shown in the presentation
</div>

<!-- Andre -->

---

# Timelink mobile

![bg blur:2px](./Assets/mobile_phone.jpg)

<div class="admonition">
  As a(n) Employee, I want to see a list of all my logged hours,  
  so that I can monitor attendance and verify shift compliance.
</div>

<div class="admonition ">
  As a(n) Employee, I want to start my shift by pressing a "Start Shift" button in the application, so that the system may begin logging my working hours accurately.
</div>

<!-- Leonardo -->

---

# Timelink mobile

![bg blur:2px](./Assets/mobile_phone.jpg)

<div class="admonition">
  As a(n) Employee, I want to end my shift by pressing a "End Shift" button in the application, so that the system may log the end of my shift accurately.
</div>

<div class="admonition ">
  As a(n) Employee, I want to receive a notification if I forget to start or end my shift, so that I don't miss logging my hours.
</div>

<!-- Elvin -->

---

# Timelink desktop

![bg blur:2px](./Assets/Linux.jpg)

<div class="admonition">
  As an admin, I want to view employee working hours and their working locations, so that I can monitor attendance and manage work distribution.
</div>

<div class="admonition ">
  As an admin, I want to see visual representations of total working hours per location, so that I can easily analyze workload distribution.
</div>

<!-- Tomas -->

---

# Timelink desktop

![bg blur:2px](./Assets/Linux.jpg)

<div class="admonition">
  As an admin, I want to add and remove users from the system, so that I can manage access and maintain an up-to-date user list.
</div>

<div class="admonition ">
  As an admin, I want to filter employee data by department or project, so that I can analyze work distribution more efficiently.
</div>

<!-- Manon -->

---


# Timelink desktop

![bg blur:2px](./Assets/Linux.jpg)

<div class="admonition">
  As an admin, I want to edit an existing working place and assign employees to them, so that I can maintain an up-to-date organizational structure.
</div>

<div class="admonition ">
  As an admin, I want to view an activity log of recent changes in the system, so that I can track who modified what and when.
</div>

<!-- Andre -->

---

# Main components and data flow
 ![bg left:33%](../../Assets/second_presentation/architecture.jpg)
- The architectural design has been defined in 4 different levels
- C1 - System level
- C2 - Container level
- C3 - Component level
- C4 - Code level

<!-- Andre -->

---

## C1 - System level

![height:400 ](./Assets/C1_arch.svg)

<!-- Andre 
The project consists on three main deliverables
- Database System -> The main brain of the system
- Timelink mobile -> Simple app where all users shall insert their working hours
- Timelink admin -> Administrator app for managers -->

---

## C2 - Container level

![height:400](./Assets/C2_architecture.svg)
<!-- Andre 
Explain each component together with each communication protocol -->

---
## Tipical data workflows

### Admin platform

![image](./Assets/system_architecture.svg)

<!-- Elvin -->

---
## Typical data workflows

### Mobile platform

![image](./Assets/system_architecture_2.svg)

<!-- Elvin -->

---
# UI designs/mockups

![bg blur:2px](../../Assets/city-bg2.jpg)

## Timelink admin


<!-- Tomas -->

---

# Timelink admin

### Welcome page

<div class="admonition">
  Some visual elements could undergo some changes
</div>

![bg left:33%](./Assets/UI_designs/UC-5_UC-9-1.svg)
<!-- Tomas -->

---

# Timelink admin

### Sign in page

![bg right:33%](./Assets/UI_designs/UC-5_UC-9-2.svg)

<!-- Tomas -->

---

# Timelink admin

### Application dashboard

![bg left:33%](./Assets/UI_designs/UC-5_UC-9-3.svg)

<!-- Tomas -->

---

# Timelink admin

![bg right:33%](./Assets/Exhausted_man.jpg)

And some other pages...

<!-- Tomas -->

---

![bg](./Assets/UI_designs/UC-6_UC-8_UC-10-1.svg)
![bg](./Assets/UI_designs/UC-6_UC-8_UC-10-2.svg)
![bg](./Assets/UI_designs/UC-12.svg)

<!-- Tomas -->

---

# UI designs/mockups

![bg blur:2px](../../Assets/city-bg2.jpg)

## Timelink mobile

<!-- Leonardo -->

---

# Timelink mobile

![bg left:50% height:650](../../../SPEC/EPICS/EPIC-01/VIS/UC-US%2001%20-%20Shift%20Monitoring/01.svg)

Shift history diagram

<!-- Leonardo -->

---

# Timelink mobile

Employee punch-in procedure

<!-- Leonardo -->

---

![bg height:650px](../../../SPEC/EPICS/EPIC-01/VIS/UC-US%2002%20-%20Employe%20Shift%20Punch-in%20(Start%20Shift)/01.svg)
![bg height:650px](../../../SPEC/EPICS/EPIC-01/VIS/UC-US%2002%20-%20Employe%20Shift%20Punch-in%20(Start%20Shift)/02.svg)
![bg height:650px](../../../SPEC/EPICS/EPIC-01/VIS/UC-US%2002%20-%20Employe%20Shift%20Punch-in%20(Start%20Shift)/03.svg)

<!-- Leonardo -->

---

# Timelink mobile

![bg left:65% height:650](../../../SPEC/EPICS/EPIC-01/VIS/UC-US%2003%20-%20Employe%20Shift%20Punch-out%20(End%20Shift)/01.svg)
![bg left:65% height:650](../../../SPEC/EPICS/EPIC-01/VIS/UC-US%2003%20-%20Employe%20Shift%20Punch-out%20(End%20Shift)/02.svg)

Employee punch-out procedure

<!-- Leonardo -->

---

# Risk management

![bg](./Assets/Danger.jpg)

<!-- Manon -->
---

![bg left:33% height:250](./Assets/Icons/warning.png)

# Identified risks

<div class="admonition warning">
  Regulatory non-compliance (GDPR)
</div>
<div class="admonition warning">
  Inconsistency between logs and actual actions
</div>
<div class="admonition warning">
  Data privacy violations
</div>

<!-- Manon
- Regulatory non-compliance (GDPR); No compliant measures (hashing, session timeout); High, legal risk, reputation risk
- Inconsistency between logs and actual actions;The logs do not accurately reflect the changes (delay or recording error).High -->

---

# Current state of the project

![bg grayscale:0.5](./Assets/Last_bg.jpg)

## What has been done

*Milestone 1 and 2 accomplished*

<div class="admonition tip">
  <li> The project specification has been defined
  <li> All the architecture has been defined at the container level
  <li> Some major risks have been identified<br>
</div>


---

# Current state of the project

![bg grayscale:0.5](./Assets/Last_bg.jpg)

## Our next steps

*Start of Milestones 3,4 and 5*

<div class="admonition">
  <li> The development will start soon
  <li> We plan to define the design at the class level together dynamically
  <li> Start risk mitigation for the current identified risks
</div>
