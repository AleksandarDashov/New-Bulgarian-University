
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

//internal headers
#include "gpio.h"
#include "dht11.h"
#include "mq135.h"
#include "esp8266.h"
//WiringPi library
#include <wiringPi.h>
#include <mcp3004.h>
#include <lcd.h>



    #define LCD_RS  3               //Register select pin
    #define LCD_E   0               //Enable Pin
    #define LCD_D4  6               //Data pin 4
    #define LCD_D5  1               //Data pin 5
    #define LCD_D6  5               //Data pin 6
    #define LCD_D7  4               //Data pin 7

void delay_seconds(int seconds)
{
    struct timespec sendpause;
    sendpause.tv_sec = seconds;
    sendpause.tv_nsec = 0;
    nanosleep(&sendpause, NULL);
}
void delay_milliseconds(int milliseconds)
{
    struct timespec sendpause;
    sendpause.tv_sec = 0;
    sendpause.tv_nsec = milliseconds;
    nanosleep(&sendpause, NULL);
}


void Initialization()
{
	//wiringPi library initialization
	if(wiringPiSetup() == -1)
    {
        printf("WiringPi library did NOT initialized successfully!");
        exit(1);
    }
	//GPIO manipulation memory initialization
	map_peripheral(&gpio);
	//ADC initialization
	mcp3004Setup(100, 0);//BASE pin (100 to 107, 100 is the first channel of the chip) int mcp3004Setup (const int pinBase, int spiChannel)
	//Wi-Fi module initialization
	int result = initESP8266();
    if(result != 0)
    {
        if(DEBUG)
        printf("Error initializing ESP8266 Wi-Fi Module!\n");

        exit(1);
    }
    else
    {
        if(DEBUG)
        printf("ESP8266 Wi-Fi Module initialized successfully!\n");

        //printf("Getting firmware version of ESP8266\n");
        //ATGetVersion(); 
        if(-1 == ATSetOperationMode(STATION_MODE) )
        {
            printf("ERROR setting operation mode!\n");
            exit(1);
        }
        if(DEBUG)
        printf("Getting operation mode of ESP8266, should be 1\n");

        if(-1 == ATGetOperationMode() )
        {
            printf("ERROR getting operation mode!\n");
        }

        if(DEBUG)
        printf("Connecting to Wi-Fi Router...\n");

        if(-1 == ATConnectAP(SSID, PASSWORD) )
        {
            printf("ERROR connecting to Wi-Fi router!\n");
            exit(1);
        }
        if(DEBUG)
        printf("Getting IP address of the network\n");

        if(-1 == ATGetIpAddress() )
        {
            printf("ERROR getting stations IP address!\n");
        }
        if(DEBUG)
        printf("Setting multiple connections of ESP8266 to enabled\n");

        if(-1 == ATSetMultipleConnection(MULTIPLE_CONNECTION_ENABLE) )
        {
            printf("ERROR setting multiple connections!\n");
            exit(1);
        }

        if(DEBUG)
        printf("Getting multiple connections of ESP8266, should be 1\n");

        if(-1 == ATGetMultipleConnection() )
        {
            printf("ERROR setting multiple connections!\n");
        }
    }
	//LCD initialization
	int lcd;
    //lcdInit(int rows, int cols, int bits, int rs, int enable, int d0, int d1, int d2, int d3, int d4, int d5, int d6, int d7)
    lcd = lcdInit (2, 16,4, LCD_RS, LCD_E ,LCD_D4 , LCD_D5, LCD_D6,LCD_D7,0,0,0,0); //TODO replace magic numbers
    if(-1 == lcd)
    {
        printf ("lcdInit failed! \n");
        return -1 ;
    }
}

void CyclicTask()
{
	while(1)
    { 
        char holder[] = "";
        char displayTemp[] = "";
        char displayHumid[] = "";
        char displayCO2[] = "";
        int temperatureLength = 0;
        int humidityLength = 0;
        int CO2Length = 0;

        readDHT11();
        while( (temperature_humidity_global[0] && temperature_humidity_global[1]) == 0 ) 
        {
            printf("Reading AGAIN!\n");
            readDHT11();
            delay_seconds(1);
        }
        printf("Temperature:%d\n", temperature_humidity_global[1]);//Temperature
        sprintf(holder, "%d",temperature_humidity_global[1]);
        temperatureLength = strlen(holder);
        temperatureLength += GET_COMMAND_SIZE;

        sprintf(displayTemp, "Temp:%dC",temperature_humidity_global[1]);
        lcdClear(lcd);
        lcdPosition(lcd,0,0);           //Position cursor on the first line in the first column
        lcdPuts(lcd, displayTemp);  //Print the text on the LCD at the current cursor postion
        ATSendData(temperature_humidity_global[1], temperatureLength, THING_SPEAK_TEMPERATURE_FIELD);//Send data to ThingSpeak

        delay_seconds(20);

        printf("Humidity:%d\n", temperature_humidity_global[0]);
        sprintf(holder, "%d",temperature_humidity_global[0]);
        humidityLength = strlen(holder);
        humidityLength += GET_COMMAND_SIZE;

        sprintf(displayHumid, "Humid:%d%%",temperature_humidity_global[0]);//NO NEED OF SECOND SPRINTF!!!!!!!!!!!!!!
        lcdPosition(lcd,0,1);           //Position cursor on the first line in the first column
        lcdPuts(lcd, displayHumid);
        //ATSendData(temperature_humidity_global[0], humidityLength, THING_SPEAK_HUMIDITY_FIELD);

        delay_seconds(20);

        readMQ135();
        printf("CO2 ppm: %f\n", ppm_global);
        sprintf(holder, "%f",ppm_global);
        CO2Length = strlen(holder);
        CO2Length += GET_COMMAND_SIZE + 4;

        sprintf(displayCO2, "CO2:%0.01fppm",ppm_global);
        lcdClear(lcd);
        lcdPosition(lcd,0,0);           //Position cursor on the first line in the first column
        lcdPuts(lcd, displayCO2);
        ATSendData(ppm_global, CO2Length, THING_SPEAK_CO2_FIELD);
        delay_seconds(20);
    }
}
int main(void)
{
    Initialization();
	CyclicTask();
	
    return 0;
}
