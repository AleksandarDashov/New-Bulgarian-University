/*proxy_client.c*/
/*gcc proxy_client.c passiveTCP.c passivesock.c errexit.c connectTCP.c connectsock.c -o proxy_client -lpthread
*/
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <errno.h>
#include <fcntl.h>

#define BUFFER_SIZE 4096

extern int errno;
int errexit(const char* format, ...);
int connectTCP(const char* host, const char* service);

int sock; /* Variable to store the socket file desctiptor */

int main(int argc, char* argv[])
{
    printf("\nArgc: %d\nArgv[0]: %s\nArgv[1]: %s\nArgv[2]: %s\n",argc,  argv[0], argv[1], argv[2]);

    char* host = "localhost"; /* Host name if not provided */
    char* port_numb = "8888"; /* Port number if not provided */
    char* resource_name = "/images/logo_bg.png";/* The  default resource we wish to download */
    char* file_name = "pic"; /* The file name which we are going to write the data to */
    char buffer[BUFFER_SIZE];/*Buffer for the data we read*/    
    int n; /*Read return*/
    int open_file;
    switch (argc)
    {
        case 2:
            port_numb = argv[1];
            break;
        case 3:
            port_numb = argv[1];
            resource_name = argv[2];//argument passed reasource we wish to download
            break;
        default:
            fprintf(stderr, "usage: 11_0 [host[port]]\n");
            exit(1);
    }
    printf("After switch\n");
    

    //connecting to the specified host on the specified port number
    sock = connectTCP(host, port_numb);
    //writes to a file descriptor the requested resource we wish to download
    if( (write(sock, resource_name, strlen(resource_name))) < 0)
    {
        errexit("Error while writing resources: %s\n", strerror(errno));
    }
    //open a file in which we are going to write the image
    if( (open_file = open(file_name, O_RDWR | O_CREAT | O_FSYNC, 0755)) < 0)
    {
        errexit("Error while opening file: %s\n", strerror(errno));
    }
    int htmlstart = 0;
    char *htmlcontent;

    while((n = read(sock, buffer, BUFFER_SIZE)) > 0)
    {
        if(htmlstart == 0)
        {
            //the beginning of the html
            htmlcontent = strstr(buffer, "\r\n\r\n");///first occurence of "\r\n\r\n" in buffer
            if(htmlcontent != NULL)
            {
                htmlstart = 1;
                htmlcontent += 4;
                n = n - (htmlcontent - buffer);
            }
        }
        else
        {
            htmlcontent = buffer;
        }
        if(htmlstart)
        {
            write(open_file ,htmlcontent, n);
        }
        memset(buffer, 0, n);
    }
    if(n < 0)
    {
        perror("Error receiving data");
    }
    close(sock);
    close(open_file);
    return 0;
}
