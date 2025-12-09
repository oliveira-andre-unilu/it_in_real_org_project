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
  background-color: rgba(0,0,0,0.8);
  padding: 5px;
  padding-left:20px;
  padding-right:20px;
  border-radius: 25px;
}

p, li {
  color: #EAEAEA;
}

strong {
  color: #a2ccf3ff;
}

code {
  background: #161B22;
  color: #79C0FF;
  padding: 0.2em 0.4em;
  border-radius: 4px;
}


ul:not(ul ul) {
  display: inline-block;
  background-color: rgba(0,0,0,0.8);
  padding: 5px;
  padding-left: 50px;
  border-radius: 25px;
}

p:not(li p){
  display: inline-block;
  background-color: rgba(0,0,0,0.8);
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
  border-left-color: #b3423eff;
  background-color: #2e2e2eff;
}
.admonition.tip {
  border-left-color: #6ec060ff;
  background-color: #2e2e2eff;
}

.columns {
display: grid;
grid-template-columns: repeat(2, minmax(0, 1fr));
gap: 1rem;
}

table {
  border-collapse: separate !important;
  border-spacing: 0 !important;
  border-radius: 20px !important;
  overflow: hidden !important;
}
td {
  border: 1px solid #ddd !important;
  padding: 8px !important;
  background: #2e2e2eff
}
th {
  border: 1px solid #ddd !important;
  padding: 8px !important;
  background: #121212
}
</style>

# Timelink - Final presentation/Demo
![bg blur:2px](../../Assets/final_presentation/main_bg.jpg)

Presented by **LAMTCo Solutions**

<!-- Andre -->

---

![bg blur:0px](../../Assets/final_presentation/update.jpg)
# Project status update

## Status

- First version fully developed

<!-- Andre -->


---

## Some metrics

**Milestone**     | **Planned man-days**  | **Actual man-days spent**
-----------   | ----------------- | ----------------------
**1 (SPEC)**     | 7                 | 5
**2 (Design)**    | 7                 | 3
**3 (Back-end)** | 14                | 21
**4 (Mobile-app)**| 14                | 22
**5 (Admin-app)** | 14                | /
**6 (Testing)**   | 14                | 18

![bg blur:0px](../../Assets/final_presentation/update.jpg)

<!-- Elvin -->


---

![bg blur:0px grayscale:0.75](../../Assets/final_presentation/scope.jpg)

# Updated scope

<div class="columns">
<div>

- **:white_check_mark: Completed**
    - Build the mobile app with all the essential functionalities
    - Build the Data Server completely with all basic functionalities for both Admin and Normal users

</div>
<div>

- **:x: Left-out for the first version**
    - Build a simple Admin page
</div>
</div>

<!-- Elvin -->

---


# Test Report

![bg blur:1px](../../Assets/final_presentation/test.jpg)

<!-- Manon -->

---

## Manual tests

![bg blur:2px](../../Assets/final_presentation/Maual%20labor.jpg)

- 12 manual test have been defined and tested

- **:white_check_mark:** 9 tests did pass with no issues what so ever

- **:x: 3 tess did unfortunately not pass**
    - 2 are related to reminder notifications
    - 1 is related with offline shift submitting

<!-- Manon -->

---

![bg blur:2px grayscale:0.5](../../Assets/final_presentation/machine_labor.jpg)

## Unit tests

- During the execution of the unit tests, several issues were discovered
  - mostly related to incorrect business logic in the backend layer 
- These issues demonstrate the usefulness of unit tests

- Most modules passed unit tests successfully, including:
  -  Service layer
  - Domain and DTO objects
  - Exception handling
  - Security configuration

<!-- Manon -->

---

![bg blur:2px grayscale:0.5](../../Assets/final_presentation/machine_labor.jpg)

### Percentage of tests that passed/failed
| **Test class** | **Number of tests executed** | **Passed** | **Failed** |
|:--------------:|--------------------------|:----------:|:----------:|
| Services       | 45                       | 44         | 1          |
| Controllers    | 19                       | 19          | 0          |
| Domain/Models  | 10                       | 10         | 0          |
| DTO            | 11                       | 11         | 0          |
| Exceptions     | 6                        | 6          | 0          |
| Security       | 22                       | 22         | 0          |
| Total          | 113                      | 112         | 1          |

<!-- Manon -->

---

![bg](../../Assets/final_presentation/demo.jpg)
# Time for the demo

<!-- Leonardo -->

---

![bg](../../Assets/final_presentation/conclusion.jpg)
# Final Conclusion

<!-- Andre -->

---

![bg](../../Assets/final_presentation/conclusion.jpg)

## Project overview

- :briefcase: **Main project deliverables**
  1. Brainstorming Ideas
  2. Defining Specifications
  3. Software Architecture Design
  4. UI Mockups Design
  5. Blueprint
  6. Back-end Development
  7. Front-end Development

<!-- Tomas -->

---

![bg blur:1px](../../Assets/final_presentation/main_bg.jpg)
## Results and highlights

- Prototype version 1 of Timelink (Proof of Concept)
- Solid base code for future extension:
  - Security
  - Back-end solution
  - Front-end solution

<!-- Andre -->

---

![bg blur:1px](../../Assets/final_presentation/main_bg.jpg)
## Challenges and resolutions

- A good portion of the initial scope had to be cut-off to guarantee the implementation within the delay.
- How to structure the design to make sur it is compliant and feasible
- Learn new programming languages
- Lack of design definition

<!-- Andre -->

---

![bg blur:1px](../../Assets/final_presentation/main_bg.jpg)
## Positive outcomes

- **Technical**
  - Expo and React Native
  - Swagger/OpenAPI Integration
  - React Native vs React
- **General**
  - I improved my ability to follow project tasks and manage my own contributions efficiently, such as keeping track of issues and handling data consistently.
  - My teamwork and collaboration skills developed further through coordinating with team members.

<!-- Tomas -->

---

![bg blur:2px](../../Assets/final_presentation/teacher.jpg)
## Lessons learned

- Design should be more emphasized
  - Meaning that it to should further specified
- Testing should have been started earlier
- Avoid scope creep, always count with things to go wrong

<!-- Andre -->

---
![bg blur:2px grayscale:0.3](../../Assets/final_presentation/Saluting.jpg)
# Hope you enjoyed working with us
By **LAMTCo Solutions**

<!-- Andre -->
