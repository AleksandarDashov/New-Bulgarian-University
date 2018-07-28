The purpose of the project is to create an embedded device that is able to diagnose the air quality in the room that the device is placed in. 
Gather data from sensors, send it through Wi-Fi and upload the data in a web page as well as alarm the user for critical levels of certain gases.
The overall functionality of the project is composed out of power supply unit that is within the microcontroller unit.  
The MCU is powered with +5V while the pins of the MCU have an output of +3V3. 
The power supply unit also powers all other modules with +3V3 except the Visualization block which is powered by +5V.
The MCU is connected with Temperature and Humidity sensor using only 1 pin. 
The Visualization block is connected with the MCU using 6 pins. 
The COM Module (Wi-Fi) is connected with the MCU using 2 pins via UART communication interface. 
And the ADC module is connected with the MCU using 4 pins and it is using SPI communication interface. 
The Gas Sensor is connected to the ADC with 1 pin

