#include "mq135.h"

void readADC()
{
	analogValueRaw = analogRead(100);
	printf("Analog RAW value: %d", analogValueRaw);
}
void calculateCO2()
{
	adcVolt = ( (VCC / ADCSIZE) * analogValueRaw);//adc value converted in mV
	printf(" ADC voltage: %fmV",adcVolt);

	Rs = (((VCC / adcVolt) - 1) * RL);//sensing resistance
	printf(" RS: %f",Rs);
        
	ppm = (116.6020 * ( pow( (Rs/RO), (-2.7690) ) ) );
	printf("CO2 ppm: %f\n", ppm);
	ppm_global = ppm;
}

void readMQ135()
{
	readADC();
	calculateCO2();
}