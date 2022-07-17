# ASM-Chart-Simulation
It's an app that gets ASM Chart and simulates it (showing registers' values) based on the cycle.  

### Supported Expressions
---
#### Register Operations
- Register <= Register
- Register <= Register + Register
- Register <= Register + Number
- Register <= Number + Register
- Register <= Register - Register
- Register <= Register - Number
- Register <= Number - Register
<br>

- Register = Register
- Register = Register + Register
- Register = Register + Number
- Register = Number + Register
- Register = Register - Register
- Register = Register - Number
- Register = Number - Register

#### Conditions
- Register || Register
- Register || Number
- Number || Register
- Number || Number
<br>

- Register && Register
- Register && Number
- Number && Register
- Number && Number
<br>

- Register | Register
- Register | Number
- Number | Register
- Number | Number
<br>

- Register & Register
- Register & Number
- Number & Register
- Number & Number
<br>

- |Register
- |Number
<br>

- &Register
- &Number
