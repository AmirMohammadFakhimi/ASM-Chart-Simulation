# ASM-Chart-Simulation
It's an app that gets ASM Chart and simulates it (showing registers' values) based on the cycle.  

### Supported Expressions
---
Please note that all numbers (inputs and outputs) must be decimal, and default value of registers is zero. How to using this app is clear when you starting the app.  
But if you see any problem in using the app, any bug or any suggestion, please feel free to contact me: fakhimi.amirmohamad@gmail.com
Hope you will enjoy. 😉
### Supported Expressions
---
#### Register Operations
- Register<=Register
- Register<=Number
- Register<=Input
  - If you want to using this, you should enter "Input" same as "Input1", "Input2" or ...
- Register<=Register+Register
- Register<=Register+Number
- Register<=Number+Register
- Register<=Register-Register
- Register<=Register-Number
- Register<=Number-Register
- Register<=Register\*Register
- Register<=Register\*Number
- Register<=Number\*Register
- Register<=Register/Register
- Register<=Register/Number
- Register<=Number/Register
- Register<=Register%Register
- Register<=Register%Number
- Register<=Number%Register
<br>

- Register=Register
- Register=Number
- Register=Input
  - If you want to using this, you should enter "Input" same as "Input1", "Input2" or ...
- Register=Register+Register
- Register=Register+Number
- Register=Number+Register
- Register=Register-Register
- Register=Register-Number
- Register=Number-Register
- Register=Register\*Register
- Register=Register\*Number
- Register=Number\*Register
- Register=Register/Register
- Register=Register/Number
- Register=Number/Register
- Register<=Register%Register
- Register<=Register%Number
- Register<=Number%Register

#### Conditions
- Register==Register
- Register==Number
- Number==Register
<br>

- Register<Register
- Register<Number
- Number<Register
<br>

- Register<=Register
- Register<=Number
- Number<=Register
<br>

- Register&&Register
- Register&&Number
- Number&&Register
<br>

- Register||Register
- Register||Number
- Number||Register
<br>

- Register&Register
- Register&Number
- Number&Register
<br>

- Register|Register
- Register|Number
- Number|Register
<br>

- &Register
- |Register
<br>

- Input
### Simple Example
---
#### ASM Chart (© Amin Foshati class at Sharif University of Technology, Spring 2022):
![alt text](https://github.com/AmirMohammadFakhimi/ASM-Chart-Simulation/blob/fd36799f08b1e1a37234173fd6ba1c0298aba669/Sample%20ASM%20Chart.png?raw=true)
#### Inputs for ASM Chart:
15  
init, mul  
start 0 1  
|r1 0 1  
r1<=Input1 r2<=Input2 r3<=0 ready<=0  
r3<=r3+r2 r1<=r1-1  
ready<=1 r4<=r3  
2  
3  
0  
4  
6  
5  
1  
1  
0  
0  
... user inputs  
#### Terminal:
Please enter input number for "clock": 15  
Please enter names and Register Operations of your state boxes same as "state1 RO1 RO2 ..., state2 RO1 RO2 ...":  
\* Don't put space in names or ROs.  
init, mul  
Id of your state boxes are from 0 to 1, respectively.  
Please enter condition and outputs of your decision boxes same as "  
id1: condition1 output1 output2 ...  
id2: condition2 output1 output2 ...  
"  
\* Please note that I give you the id, and also outputs must be integers.  
\* When you enter "end" or press ENTER instead of condition, you will end the input.  
\* Don't put space in condition or outputs.  
2: start 0 1  
3: |r1 0 1  
4:   
Please enter Register Operations of your conditional boxes same as "  
id1: RO1 RO2 ...  
id2: RO1 RO2 ...  
"  
\* Please note that I give you the id.  
\* When you enter "end" instead of RO1, you will end the input.  
\* Don't put space in ROs.  
4: r1<=Input1 r2<=Input2 r3<=0 ready<=0  
5: r3<=r3+r2 r1<=r1-1  
6: ready<=1 r4<=r3  
7:   
Now you should choose destination of connections for each box with their id.  
State boxes  
0: 2  
1: 3  
Decision boxes  
Decision box 2:  
    output 0: 0  
    output 1: 4  
Decision box 3:  
    output 0: 6  
    output 1: 5  
Conditional boxes  
4: 1  
5: 1  
6: 0  
Please choose a state box (its id) to start with:  
0  
       clk |        r1    |        r2    |        r3    |     ready    |        r4    |Next ASM Block    |  
\____________________________________________________________________________________________________  
Please enter input number for "start": 0  
         1 |         0    |         0    |         0    |         0    |         0    |      init    |  
Please enter input number for "start": 1  
Please enter input number for "r1<=Input1": 4  
Please enter input number for "r2<=Input2": 2  
         2 |         4    |         2    |         0    |         0    |         0    |       mul    |  
         3 |         3    |         2    |         2    |         0    |         0    |       mul    |  
         4 |         2    |         2    |         4    |         0    |         0    |       mul    |  
         5 |         1    |         2    |         6    |         0    |         0    |       mul    |  
         6 |         0    |         2    |         8    |         0    |         0    |       mul    |  
         7 |         0    |         2    |         8    |         1    |         8    |      init    |  
Please enter input number for "start": 3  
There isn't matched output.  
Please enter input number for "start": 1  
Please enter input number for "r1<=Input1": 3  
Please enter input number for "r2<=Input2": 10  
         8 |         3    |        10    |         0    |         0    |         8    |       mul    |  
         9 |         2    |        10    |        10    |         0    |         8    |       mul    |  
        10 |         1    |        10    |        20    |         0    |         8    |       mul    |  
        11 |         0    |        10    |        30    |         0    |         8    |       mul    |  
        12 |         0    |        10    |        30    |         1    |        30    |      init    |  
Please enter input number for "start": 0  
        13 |         0    |        10    |        30    |         1    |        30    |      init    |  
Please enter input number for "start": 0  
        14 |         0    |        10    |        30    |         1    |        30    |      init    |  
Please enter input number for "start": 0  
        15 |         0    |        10    |        30    |         1    |        30    |      init    |  
  
Process finished with exit code 0  
