#include "dht11.h"

void initDHT11()
{
	lststate = 1;
    counter = 0;
    j = 0;
    i = 0;
    farenheit = 0;
	for(i = 0; i < NUMBER_OF_DATA_BITS; i++)
    {
        dht11_val[i] = 0;
    }
    output_GPIO(DHT11PIN);
    low_GPIO(DHT11PIN);//MCU will set voltage level from high to low
    sleepMicro(FALLING_EDGE_INIT_DELAY);//and this process must take at least 18milliSeconds
    high_GPIO(DHT11PIN); //pulling the pin high for 40 microseconds
    sleepMicro(RISING_EDGE_INIT_DELAY);
    input_GPIO(DHT11PIN); //setting te pin as input in order to read the DHT11 output
}

void getData()
{
	for(i = 0; i < MAX_TIME; i++)
    {
        counter = 0;
        while(read_GPIO(DHT11PIN) == lststate)
        {
            counter++;
            sleepMicro(1);
            if(counter == 255)
            {
                break;
            }
        }
        lststate = read_GPIO(DHT11PIN);
        if(counter == 255)
        {
            break;
        }
        // top 3 transistions are ignored
        if( (i >= 4) && (i % 2 == 0) )
        {
            dht11_val[j/8] <<= 1;
            if(counter > 16)
            {
                dht11_val[j/8] |= 1;
            }
            j++;
        }
    }
}

void checkSum()
{
	// verify cheksum and print the verified data
    if  ( (j >= 40) 
            &&
          ( dht11_val[4] == ( (dht11_val[0] + dht11_val[1] + dht11_val[2] + dht11_val[3] ) & 0xFF) )
        )
    {
        farenheit = dht11_val[2] * 9./5. + 32;
        printf("Temperature = %d.%d *C (%.1f *F) Humidity = %d.%d %%\n",dht11_val[2], dht11_val[3], farenheit, dht11_val[0], dht11_val[1]);
        temperature_humidity_global[0] = dht11_val[0];//Humidity
        temperature_humidity_global[1] = dht11_val[2];//Temperature
    }
    else
    {
        printf("Invalid Data!!\n");
    }
    
}

void readDHT11()
{
	initDHT11();
	getData();
	checkSum();
}
