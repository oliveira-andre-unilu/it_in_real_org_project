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
  background-color: #384959;
  background-image: url("../../Assets/Main_logo.png");
  background-repeat: no-repeat;
  background-position: bottom left;
  background-size: 150px, cover;
}

h1, h2, h3 {
  font-weight: 700;
  color: #007BFF;
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
</style>

# Timelink - Specification
![bg blur:2px](../../Assets/old-clock.jpg)
Presented by **LAMTCo Solutions**

<!-- Welcoming people  -->

---

# Overview

- STILL TO DO
<!-- Some other notes -->

---

# Main components and data flow
 ![bg left:33%](../../Assets/second_presentation/architecture.jpg)
- The design has been defined in 4 different levels
- C1 - System level
- C2 - Container level
- C3 - Component
- C4 - Code level

