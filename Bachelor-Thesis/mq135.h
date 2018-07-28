#ifndef MQ135_H
#define MQ135_H

#include "gpio.h"
#include <wiringPi.h>
#include <mcp3004.h>

#define RL 4700 // load resistance 4.61kOhms
#define VCC 3300 // input voltage now 2.9V 2950mV
#define ADCSIZE 1024 // 10 bit ADC
#define RO 50254 // sensor resistance at 100ppm of NH3 in the clean air.

int analogValueRaw;//raw analog input value
uint8_t initializeFlag; //initialization flag for the init funcion
float Rs; //load resistance value
float adcVolt;//result in mV
float ppm; //calculated parts per million

float ppm_global;//global variable to store the ppm value.
extern void readADC();//read the ADC output value 
extern void calculateCO2();//calculate the CO2 ppm in the air
extern void readMQ135();




#endif