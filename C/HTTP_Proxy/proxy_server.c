#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <netinet/in.h>

#include <sys/socket.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/signal.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <sys/wait.h>

#include <pthread.h>
#define QLEN 32
#define BUFFER_SIZE 4096
#define STRING_SIZE 128
pthread_t pth_id[10];

void reaper(int sig)
{
	int	status;
	while (wait3(&status, WNOHANG, (struct rusage *)0) >= 0);

}
extern int errno;
int passiveTCP(const char* service, int queue_length);
int connectTCP(const char* host, const char* service);

int errexit(const char* format, ...);
int i;
char term_char = '\0';

void proxy_thread(void* slave_sockfd)
{
    printf("In Proxy Thread...\n");
    int fd = *(int *)slave_sockfd; //clients file descriptor
    char* host_name = "www.jobs.bg"; //default host to use
    char* port_number = "80"; //port number (80)
    char buffer[BUFFER_SIZE]; //buffer for the download proccess
    int n;
	///switch for other host_names



    int fileSock; //fd of the specified URL
    char get_command[STRING_SIZE] = "GET /";
    char request_resouce[STRING_SIZE] = "";
    memset(&request_resouce[0], term_char, STRING_SIZE);//clear the buffer
    //read
    read(fd, request_resouce, STRING_SIZE);
    printf("Requested resource: %s\n", request_resouce);
	//connecting to the specified URL by host_name and returning fd
    fileSock = connectTCP(host_name, port_number);
	if(fileSock < 0)
	{
		errexit("Connecting to URL failed: %s\n", strerror(errno));
	}
    strcat(get_command, request_resouce);
    strcat(get_command, " HTTP/1.0\r\nHost: ");//HTTP/1.0
	strcat(get_command, host_name);
	strcat(get_command, "\r\n\r\n");
    printf("GET : %s\n", get_command);
	//sending the get request
	//*****ASK QUESTIOOOON************
    int rc = write(fileSock, get_command, strlen(get_command));

    if(rc==-1)
	{
		errexit("XXXSending request failed: %s\n", strerror(errno));
	}
    while((n = read(fileSock, buffer, BUFFER_SIZE)) > 0)
    {
		printf("Num read n:%d\n", n);
        //send info to client
        write(fd,buffer,n);
        //clear the buffer, so the read function can fill it with new information
        memset(&buffer[0], term_char, BUFFER_SIZE);
    }
	//closing connection with fd
    close(fd);
    return;
}

int main(int argc, char* argv[])
{
    printf("\nArgc: %d\nArgv[0]: %s\nArgv[1]: %s\nArgv[2]: %s\n",argc,  argv[0], argv[1], argv[2]);
    struct sockaddr_in client_address; //address pf the client
    char* port_numb = "8888"; //port number //4040
    int master_sock; //master socket
    int slave_sock; //slave socket
    unsigned int address_length;//length of the clients address
    switch (argc)
    {
        case 1:
            break;
        case 2:
            port_numb = argv[1];
            break;
        default:
            errexit("usage: proxy [port]\n", "omg");
    }
    printf("After switch\n");
    master_sock = passiveTCP(port_numb, QLEN);
    printf("After passiveTCP\n");
    (void) signal(SIGCHLD, reaper);
    i = 0;



    while(1)
    {
        printf("Waiting for a client...\n");
        address_length = sizeof(client_address);
        //accept the clients request for connection
        slave_sock = accept(master_sock, (struct sockaddr *)&client_address, &address_length);
        if( slave_sock < 0)
        {
            errexit("Accept failed: %s\n", strerror(errno));
        }
		//remove else
		else
		{
			//if accept is successfull, we create a new thread for every client request
	        printf("Befor proxy thread creation...\n");
	        pthread_create(&pth_id[i], NULL,(void *) proxy_thread, (void *) &slave_sock);
	        i++;
		}

    }

    printf("Hello, im working fine!\n");
    return 0;
}
