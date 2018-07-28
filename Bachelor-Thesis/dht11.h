#ifndef DHT11_H
#define DHT11_H

#include "gpio.h"


//DHT11 reading
#define MAX_TIME                85
#define DHT11PIN                4   //digital ouput pin of the DHT11 sensor
#define NUMBER_OF_DATA_BITS		5 // 5 bits of data 2 for temperature 2 for humidity and 1 for checksum 
#define FALLING_EDGE_INIT_DELAY 18000 //in microseconds (18 milliseconds)
#define RISING_EDGE_INIT_DELAY	40 //in microseconds

int dht11_val[NUMBER_OF_DATA_BITS];
uint8_t lststate;
uint8_t counter;
uint8_t j;
uint8_t i;
float farenheit;


int temperature_humidity_global[2];//global array to store the temperature and humidity values 


extern void initDHT11();//send initialization signal to the DHT11 sensor
extern void getData();//get the returned data from the DHT11 sensor
extern void checkSum();//check if the data is correct and if so print it
extern void readDHT11();

#endif