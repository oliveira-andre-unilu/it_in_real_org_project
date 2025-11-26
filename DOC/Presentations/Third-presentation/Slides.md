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
</style>

# Timelink - Development
![bg blur:2px](../../Assets/third_presentation/laptop-half-closed-dark-with-colourful-glow-glasses-paper-cup.jpg)

Presented by **LAMTCo Solutions**

<!-- Andre -->

---

![bg blur:2px](../../Assets/third_presentation/laptop-half-closed-dark-with-colourful-glow-glasses-paper-cup.jpg)
# Backend development

## Status

- First version fully developed

- First API/Data server fully running

---

## What main methods have been developed

- The api is currently running a swagger api specification tool that helps understand what the API does

![bg left height:450](./Assets/swagger.png)


---

![bg](../../Assets/third_presentation/pexels-photo-555709.jpeg)

# Manual Test cases

- Most of the manual test cases have been implemented for the current scope

---

![bg blur:2px](../../Assets/third_presentation/pexels-photo-555709.jpeg)

# Some examples

## Start a shift UC-2

<div class="columns">
<div>

- **Preconditions:**
    -  User connected
    -  Application has GPS allowed (optional)
- **Steps:**
    -  Open application
    -  Go to the shift page 
    -  Click button “Start shift”

</div>
<div>

- **Expected Result:**
    - Shift has started within 2 seconds
    - Timestamps and GPS saved
    - Message “Shift started at hh:mm”
</div>
</div>

---


## And there are many more

![bg right:33%](../Second-Presentation/Assets/Exhausted_man.jpg)

- There are around 12 Manual tests for the current project scope 

---

![bg](../../Assets/third_presentation/colin-lloyd-KQPRye6h2a8-unsplash.jpg)
# Project status

---

![bg blur:2px](../../Assets/third_presentation/colin-lloyd-KQPRye6h2a8-unsplash.jpg)

<div class="columns">
<div class="admonition tip">
    - Back-end has been completely developed<br>
    - All the manual tests have been implemented<br>
    - Both front-end as well as well unitary testing have started as well
</div>
<div class="admonition warning">
    - First version of the front-end<br>
    - All the unit tests and integration tests finished as well<br>
    - Preparing the final demo as well as the final presentation
</div>
</div>