*Version: 1.0*
*Last Update: 27/09*

# Project Charter

## Project summary

This problem is not about creating something new, but it is about putting different already created tools together and make it as a new system.

During the following next paragraphs you will find a brief explanation explaining the issue that our system is ste to solve as well how we plan to solve it.

The current issue of that we are trying to solve is to find an easy way for employees, specially the ones that do have several working places that are constantly changing (Construction sector for example).

### Usage of an mobile app

The first major feature that our system shall have will be the usage of an mobile app that will allow all employees to set all of their working hours as well as where they have done these hours of work.

The users will also have a profile, allowing them to store all their current working places as well as all their hourly rate (only ste by the system administrator) and other personalized data.

### Usage of a central Data Server

The second major feature willbe a server that will serve as a master data collection center. All updates from all mobile apps will also be saved in this server. In addition, the system will also provide all users all the information that they need (such as current working places available).

As for the number of Data Servers that shall be put into productions, we have defined that each company shall have it's own Data Server. This will guarantee data protection at the core of the company. 

### Usage of an admin platform

The third major server will serve as non-mobile platform. This platform will be accessible via the browser (web application) where all administrator users will have the possibility to manage and control all the data from all users in the system.

In this platform, the users will have the possibility to visualize all the data with the help of several tables and graphs.

## Project goals

The main goals of this application are the following:

- Having an easy User interface for the mobile app, this specially since the application would be used by different types of users.

- The mobile app shall be independent of the Data Server. The users shall be able to add working schedules without any internet connection and, when the do have som internet connection, the data should be uploaded into the main server.

- The main Data server does not need to have any UI (apart from a PHPAdmin page eventually) and shall always guarantee a fast connection for all users.

- The administrator page shall will eventually have a more complex interface but it needs to several graphs that will demonstrate all the admin needs in a easy and direct way.

- All the other goals will be specified more specifically in the SPEC sheet

## Project deliverables

During the implementation of this Sofware system, we will deliver several business related Documents as well as all the script related deliverables.

### Documentation

When it comes to documentation, all different roles will have their contribution to it.

**Weekly meeting's minutes**

During all weekly meeting's, a respective report will be created writing the following:

- Author and date

- Attended people

- Agenda Items

- Items discussed 

- Action items

- Other related notes

**Project main Specification Sheet(s)**

All the main Project Specifications will be defined in one or several md files.

All the different specifications will be defined in different levels:

1. The main Project project spec sheet where all the different main goals of the system shall be defined.

2. At the secondary level, we will have all the technical SPEC's (such as performance related specifications) and other. These will be typically expressed as use-cases and user stories respectively. Each document type will have an exemplary template under **SPEC/US/Template.md** and **SPEC/UC/Template.md**

**Manual tests**

Some manual tests could be eventually implemented if there is no other way to test a specific functionalilty. A exemplary template will created under **DEV/TEST/MAN/Template.md**.

### Main project deliverables

The main project application Scope will be executed in three main parts that where discused at the beginning of this document. The mobile application will be a **React-Native** mobile app and will only depend on the device itself to run. This Deliverable root will be placed under **DEV/MOBILE**.

The Data server will be a Springboot (Java) application together wil an SQL database. This application will run on to of Docker Desktop with a respective container and volume. It will not have a direct user interface. This Deliverable root will be placed under **DEV/SERVER**.

The administrator User Interface will be the third main project deliverable. This application will run in each Administrator PC as **React-Native** application. This Deliverable root will be placed under **DEV/ADMIN**.

### Business Analyst and QA Engineer Respective folders

Both Business Analyst and well QA Engineer will have their respective folders under **/BA** as well as **/QA**. They must write all the documentation as md or latex files.

Additionally, teh BA will also have the **/ARCH** folder in order to store all UML and related achitechture files. The recommended language to implement all UML diagrams will be *PlantUml*.

## Scope

In order to avoid scope creep for this project we have decided to let some features as "Out of scope" for future implementation.

### In scope

- Build the mobile app with all the essential functionalities
- Build the Data Server completely with all basic functionalities for both Admin and Normal users
- Build a simple Admin page

### Out of scope 

- Fully implement all functionalities for both Admin and User apps.
- Data integration from Car GPS tracking applications such as (Name still to check)
- Data Exportation to formal formats (such as XML) to further integrating the system to other business management tools

## Benefits and costs

### Expected benefits

- In-life management of realized hours of all employees
- No more the need to write all hours created in a paper-format
- Easy control of project manual labor cost
- Easy control of employees theoretical and real number of hours realized (only possible in version 2.0).

### Estimated costs

As for the moment the only costs that will be present will be the test server that is used to test our first production Data Server and the server that where the main git repository is ran.

The other cost that must be put into account is the labor cost. We count to have this first version implemented during the next 5 months, all the labor costs will be determined at that point with a further analysis for the next version of teh software.

## Key project dates and milestones

- Project start: **30/09**
- Milestone 1 => Implement the main Spec of the system: **07/10**
- Milestone 2 => Implement all C1->C4 levels of architecture of the system: **14/10**
- Milestone 3 => Implement the first version of the Data Server system: **11/11**
- Milestone 4 => Implement the first version of the mobile app: **08/12**
- Milestone 5 => Implement the first version of the Admin app: **08/12**
- Milestone 6 => Having all major test implemented an working: **08/12**
- Delivery of the version: **15/12**

Our team will work in an agile way (SCRUM ideology) and for this reasons some milestone  might move or new ones might be created.

## Key Project Risks

As for the moment, the risk analysis did not take a major place but we already have an idea to what risks that our system might have:

- **Risk 1:** Companies will not be willing to change from their current to system to ours
- **Risk 2:** Data imports/exports might show a bigger difficulty than expected
- **Risk 3:** The project Scope being to big for the time that we have

## Key Stakeholders, Team Structure, Roles & Responsibilities

- **Stakeholders:** STILL TO BE VERIFIED

- **Project Lead:** André OLIVEIRA MARTINS 

- **Developer:** Tomás MARQUES DO POMAR

- **Developer:** Leonardo SOUSA COIMBRA

- **Business Analyst:** Elvin COCCO 

- **QA Engineer:** Manon BARTHELEMY