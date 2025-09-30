---
marp: true
theme: uncover
paginate: true
class: invert
---
<style lang=css>
@import 'default';

section {
  font-family: "Segoe UI", "Helvetica Neue", Arial, sans-serif;
  font-size: 28px;
  line-height: 1.5;
  background-color: #0A192F;
  background-image: url("../../Assets/Main_logo.png"), url("../../Assets/simple-bg-2.jpg");
  /* filter: blur(0px), blur(8px);
  -webkit-filter: blur(0px), blur(8px); */
  background-repeat: no-repeat;
  background-position: bottom left;
  background-size: 150px, cover;
}

h1, h2, h3 {
  font-weight: 700;
  color: #007BFF;
}

p, li {
  color: #E6EDF3;
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
</style>

# Timelink
Presented by **LamCo Solutions**

---

## Project summary

- Easy way to for employees to mark their working hours.

- Usage of an mobile app

- Usage of a central Data Server

- Usage of an admin platform

---


## Project goals

- Simple User interface for the mobile app

- The mobile shall work offline 

- The administrator page with several graphs that will demonstrate all the admin needs in a easy and direct way

- All the other goals will be specified more specifically in the SPEC sheet


---

## Project Deliverables

![bg left:33% height:250](../../Assets/Icons/google-docs.png)

- Documentation:
  - All the meeting will have their respective minute
  - Main project specification sheets
  - Manual tests

---

## Mobile app

![bg right:33% height:350](../../Assets/Icons/mobile-development.png)

- app where all users will be able to insert all their working hours

- Based on a **React-Native** platform

---

## Data server

![bg left:33% height:350](../../Assets/Icons/server.png)

- Springboot-based application

- respective SQL database

- This server will mainly serve as an API for both client and administrator platforms

---

## Administrator platform

![bg right:33% height:350](../../Assets/Icons/working.png)

- A non-mobile application, also **react-native**

- Goal of the app will be to show the user the amount of hours per user and other parameters

---


## Scope
**In Scope**
- Build the mobile app with all the essential functionalities
- Build the Data Server completely with all basic functionalities for both Admin and Normal users
- Build a simple Admin page

---
## Scope

**Out of Scope**
- Fully implement all functionalities for both Admin and User apps.
- Data integration from Car GPS tracking applications such as (Name still to check)
- Data Exportation to formal formats (such as XML) to further integrating the system to other business management tools

---

## Benefits and Costs
**Benefits**  
- In-life management of realized hours of all employees
- No more the need to write all hours created in a paper-format
- Easy control of project manual labor cost
- Easy control of employees theoretical and real number of hours realized (only possible in version 2.0). 

---
## Benefits and costs

**Costs**  
- The necessity of a testing server (run beta production system) and git repository
- Labor cost

---

## Key Dates & Milestones
- Project start: **30/09**
- Milestone 1 => Implement the main Spec of the system: **07/10**
- Milestone 2 => Implement all C1->C4 levels of architecture of the system: **14/10**
- Milestone 3 => Implement the first version of the Data Server system: **11/11**
- Milestone 4 => Implement the first version of the mobile app: **08/12**
- Milestone 5 => Implement the first version of the Admin app: **08/12**
- Milestone 6 => Having all major test implemented an working: **08/12**
- Delivery of the version: **15/12** 

---

## Key Risks
- **Risk 1:** Companies will not be willing to change from their current to system to ours
- **Risk 2:** Data imports/exports might show a bigger difficulty than expected
- **Risk 3:** The project Scope being to big for the time that we have 

---

## Stakeholders & Team

- **Project Lead:** André OLIVEIRA MARTINS 

- **Developer:** Tomás MARQUES DO POMAR

- **Developer:** Leonardo SOUSA COIMBRA

- **Business Analyst:** Elvin COCCO 

- **QA Engineer:** Manon BARTHELEMY 

---

# Thank You
Questions?
