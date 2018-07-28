#include "esp8266.h"


/*Initialization of the ESP8266
 *
 */
int initESP8266()
{
	ESP8266fd = serialOpen("/dev/ttyAMA0", 115200); //initialize the serial communication between the raspberry pi and the esp8266. 
    if(ESP8266fd == -1)
    {
    	if(DEBUG)
    	{
    		printf("Error opening /dev/ttyAMA0 errno: %d\n", errno);
    	}
        exit(1);
    }
    else
    {
    	if(DEBUG)
    	{
    		printf("ESP8266 file descriptor opened successfully!\n");
    	}
    	printf("Testing ESP8266\n");
        if(-1 == ATTest() )
        {
        	printf("ERROR in testing ESP8266\n");
        }

    }
    return 0;
}
/*getBlock 
 *Parameter: none
 *Return: The number of bytes read from the module
 */
int getBlock()
{
	int bytes;
    struct timespec pause;
    pause.tv_sec = 0;
    pause.tv_nsec = 100000000;//100 milliseconds
    nanosleep(&pause, NULL);
    memset(buffer, '\0', sizeof (buffer));//memset() fills the first sizeof(buffer) bytes of the memory area pointed to by buffer with the constant byte '\0'.
    ioctl(ESP8266fd, FIONREAD, &bytes);//TODO
    if (bytes == 0)
    {
    	return 0;
    }
    int count = read(ESP8266fd, buffer, BLOCKSIZE - 1);//read() attempts to read up to BLOCKSIZE - 1 bytes from file descriptor ESP8266fd into the buffer.   
    buffer[count] = 0;
    if (DEBUG) 
    {
    	//printf("Bytes read from file descriptor: %d\n", count);//On success, the number of bytes read is returned. On error -1
        printf("%s", buffer);
        fflush(stdout);
    }
    return count;
}
/*getBlocks 
 *Parameter: int num - number of seconds to wait for the answer, char target[] - the string we search for in the answer buffer
 *Return: The number of bytes read from the module
 */
int getBlocks(int num, char target[]) 
{
	int i;
	struct timespec pause;
	pause.tv_sec = 1;
	pause.tv_nsec = 0;
	for (i = 0; i < num; i++) 
	{
		nanosleep(&pause, NULL);
		getBlock();
		if (strstr(buffer, target)) //strstr - Returns a pointer to the first occurrence of str2 in str1, or a null pointer if str2 is not part of str1.
		{
			return i;
		}
	}
	return -1;
}

/*ATTest tests the setup function of the Wi-Fi module and reads back the anwer
 *Parameter: none
 *Return: The number of bytes read from the module
 */
int ATTest()
{
	dprintf(ESP8266fd, AT_STARTUP);//dprintf outputs to a file descriptor fd instead to a stdio stream
    return getBlocks(5, "OK");
}
/*ATVersion gets the firmware version
 *Parameter: none
 *Return: The number of bytes read from the module
 */
int ATGetVersion()
{
	dprintf(ESP8266fd, AT_VERSION_INFO);
    return getBlocks(5, "OK");
}
/*ATRestart performs software reset and reads back the anwer
 *Parameter: none
 *Return: The number of bytes read from the module
 */
int ATRestart()
{
	dprintf(ESP8266fd, AT_RESTART);
    return getBlock();
}
/*ATListNetworks lists all the available wi-fi networks
 *Parameter: none
 *Return: The number of bytes read from the module
 */
int ATListNetworks()
{
	dprintf(ESP8266fd, AT_LIST_NETWORKS);
	return getBlocks(20, "OK");
}
/*ATGetOperationMode gets the operating mod  1: station mode, 2: softAP mode, 3 : softAP + station mode
 *Parameter: none
 *Return: The number of bytes read from the module
 */
int ATGetOperationMode()
{
	dprintf(ESP8266fd, AT_GET_OPERATION_MODE);
	return getBlocks(5, "OK");
}

/*ATSetOperationMode sets the operating mod  1: station mode, 2: softAP mode, 3 : softAP + station mode
 *Parameter: mode - between 1-3 to select the operating mode
 *Return: The number of bytes read from the module
 *Note: Configuration changes will NOT be stored in flash
 */
int ATSetOperationMode(int mode)
{
	dprintf(ESP8266fd, "AT+CWMODE_CUR=%d\r\n", mode);
	return getBlock();
}
/*ATSetOperationMode sets the operating mod  1: station mode, 2: softAP mode, 3 : softAP + station mode
 *Parameter: mode - between 1-3 to select the operating mode
 *Return: The number of bytes read from the module
 *Note: Configuration changes will be stored in flash system parameter area.
 */
int ATSetOperationModeFlash(int mode)
{
	dprintf(ESP8266fd, "AT+CWMODE_DEF=%d\r\n", mode);
	return getBlock();
}

/*ATConnectAP connects to access point, for the current sesion 
 *Parameter: ssid of the network, password of the network
 *Return: The number of bytes read from the module
 *Note: Configuration changes will NOT be stored in flash.
 */
int ATConnectAP(char ssid[], char pass[])
{
	dprintf(ESP8266fd, "AT+CWJAP_CUR=\"%s\",\"%s\"\r\n",ssid, pass);
	return getBlocks(20, "OK");
}
/*ATConnectAP connects to access point, and sets that access point to default
 *Parameter: ssid of the network, password of the network
 *Return: The number of bytes read from the module
 *Note: Configuration changes will be stored in flash.
 */
int ATConnectAPFlash(char ssid[], char pass[])
{
	dprintf(ESP8266fd, "AT+CWJAP_DEF=\"%s\",\"%s\"\r\n",ssid, pass);
	return getBlocks(20, "OK");
}
/*ATGetIpAddress gets the local ip address of the connected network
 *Parameter: none
 *Return: The number of bytes read from the module
 *Note: Only after ESP8266 station is connected to an Access Point, station IP can be obtained and inquired.
 */
int ATGetIpAddress()
{
	dprintf(ESP8266fd, "AT+CIFSR\r\n");
	return getBlocks(10, "OK");
}
/*ATSetMultipleConnection sets multiple connection to enable or disable
 *Parameter: 0 disable multiple  connection, 1 enable multiple connection
 *Return: The number of bytes read from the module
 *Note:none
 */
int ATSetMultipleConnection(int mode)
{
	dprintf(ESP8266fd, "AT+CIPMUX=%d\r\n",mode);
	return getBlocks(10, "OK");
}

/*ATSetMultipleConnection gets multiple connection setting
 *Parameter:none
 *Return: The number of bytes read from the module
 *Note:none
 */
int ATGetMultipleConnection()
{
	dprintf(ESP8266fd, "AT+CIPMUX?\r\n");
	return getBlocks(10, "OK");
}
/*ATConnectThingsSpeakTCP connecctto the HTTP ThingsSpeeak server using TCP
 *Parameter:none
 *Return: The number of bytes read from the module
 *Note:none
 *///TOODOOO replace IP and first parameter with defines
int ATConnectThingsSpeakTCP()
{
	dprintf(ESP8266fd, "AT+CIPSTART=4,\"TCP\",\"184.106.153.149\",80\r\n");
	return getBlocks(20, "OK");
}
/*ATSendGET Sends the final GET command
 *Parameter:int val - value to be update, int field - field number to be uploaded the value.
 *Return:The number of bytes read from the module
 */
int ATSendDataSize(int channel, int size)
{

	dprintf(ESP8266fd, "AT+CIPSEND=%d,%d\r\n", channel, size);
	return getBlocks(20, "OK");
}
/*ATSendGET Sends the final GET command
 *Parameter:int val - value to be update, int field - field number to be uploaded the value.
 *Return:The number of bytes read from the module
 */
int ATSendGET(int val, int field)
{
	//char getCommand[] = "GET /update?api_key=AJ6DRLQWRTRV111N&field1=\r\n";

	dprintf(ESP8266fd, "GET /update?api_key=AJ6DRLQWRTRV111N&field%d=%d\r\n", field, val);
	return getBlocks(20, "OK");
}
/*ATCloseTCP Closes an already open TCP connection socket
 *Parameter:none
 *Return:The number of bytes read from the module
 */
int ATCloseTCP()
{	
	dprintf(ESP8266fd, AT_CLOSE_TCP_ALL_CHANNELS);
	return getBlocks(20, "OK");
}
/*ATSendData Sends data to the ThingSpeak server after calling all the necessary functions
 *Parameter:int val - value to be send, int size - the size of the GET command, int field - the field in which the value will be updated
 *Return: none
 */
void ATSendData(int val, int size, int field)
{
	if( (-1 == ATConnectThingsSpeakTCP() ) )
	{
		printf("Connecting to ThingSpeak FAILED\n");
	}
	else
	{
		printf("Successfull connection with ThingSpeak\n");
	}

	if( (-1 == ATSendDataSize(MULTIPLE_CONNECTION_CHANNEL,size) ) )
	{
		printf("Sending channel and data size FAILED\n");
	}
	else
	{
		printf("Successfully send channel and data size\n");
	}

	if( (-1 == ATSendGET(val, field) ))
	{
		printf("Updating ThingSpeak field failed\n");
	}
	else
	{
		printf("Succesfull update of ThingSpeak field\n");
	}
	if( (-1 == ATCloseTCP()) )
	{
		printf("Closing TCP socket FAILED\n");
	}
	else
	{
		printf("Successfully closed the TCP socket\n");
	}

}

/*ATSetPassthroughTransmission During passthrough transmission, if the TCP connection breaks,ESP8266 will keep trying to reconnect until "+++" is input to quit from transmission.
 *Parameter:mode 0 - normal mode, 1- passthrough mode
 *Return: The number of bytes read from the module
 *Note:none
 */
int ATSetPassthroughTransmission(int mode)
{
	dprintf(ESP8266fd, "AT+CIPMODE=%d\r\n", mode);
	return getBlocks(20, "OK");
}
