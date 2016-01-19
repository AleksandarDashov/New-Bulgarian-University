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
    char* resource_name = "/images/jobslogo.gif";/* The resource we wish to download */
    char* file_name = "pic.gif"; /* The file name which we are going to write the data to */
    char buffer[BUFFER_SIZE];
    int n; //read return
    int open_file;
    switch (argc)
    {
        case 2:
            port_numb = argv[1];
            break;
        case 3:
            host = argv[1];
            port_numb = argv[2];
            break;
        default:
            fprintf(stderr, "usage: 11_0 [host[port]]\n");
            exit(1);
    }
    printf("After switch\n");


    sock = connectTCP(host, port_numb);
    //writes to a file descriptor the requested resource we wish to download
    (void) write(sock, resource_name, strlen(resource_name));
    //open a file in which we are going to write the image
    open_file = open("snimkaCOM.gif", O_WRONLY | O_CREAT | O_FSYNC);
    int htmlstart = 0;
    char *htmlcontent;

    while((n = read(sock, buffer, BUFFER_SIZE)) > 0)
    {
        if(htmlstart == 0)
          {
           /* Under certain conditions this will not work.
            * If the \r\n\r\n part is splitted into two messages
            * it will fail to detect the beginning of HTML content*/

           htmlcontent = strstr(buffer, "\r\n\r\n");
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
             //fprintf(stdout, "%s", htmlcontent);
             //fprintf(stderr, "<%s>", htmlcontent);
             //sleep(5);
             write(open_file ,htmlcontent, n);
          }
          memset(buffer, 0, n);
         }

    if(n < 0)
    {
        perror("Error receiving data");
    }
        //write(open_file, buffer, strlen(buffer));
        //printf(" %s\n",buffer);
    return 0;
}
