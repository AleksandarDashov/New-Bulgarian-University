#include "gpio.h"

/*
 *Mapping memory areas with mmap function will provide our program a direct access to device memory
 *Parameters: structrure for the addresses of the specified mapped memory areas
 *
 */
uint8_t map_peripheral(struct bcm2835_peripheral *p)
{
    //open the /dev/mem folder with read/write
    if((p->mem_fd = open("/dev/mem", O_RDWR|O_SYNC)) < 0)
    {
        printf("Failed to open /dem/mem, did you sudo?\n");
        return -1;
    }
    p->map = mmap(NULL,BLOCK_SIZE,PROT_READ|PROT_WRITE,MAP_SHARED,p->mem_fd, GPIO_BASE);

    if(p->map == MAP_FAILED)
    {
        printf("mmap failed, MAP_FAILED\n");
        return -1;
    }
    close(p->mem_fd);
    p->addr = (volatile unsigned int *)p->map;

    return 0;
}

void unmap_peripheral(struct bcm2835_peripheral *p)
{
    munmap(p->map, BLOCK_SIZE);
    close(p->mem_fd);
}

//setting the pin as input
void input_GPIO(uint8_t gpio_numb)
{
    *(gpio.addr + ( (gpio_numb) / 10) ) &= ~(7 << ( ((gpio_numb) % 10) *3) );
}

//setting the pin as output
void output_GPIO(uint8_t gpio_numb)
{
    input_GPIO(gpio_numb);
    *(gpio.addr + ( (gpio_numb) / 10) ) |=  (1 << ( ( (gpio_numb) % 10 ) *3) );
}

//setting the pin to HIGH
void high_GPIO(uint8_t gpio_numb)
{
    *(gpio.addr + 7) = 1 << gpio_numb;
}

//setting the pin to LOW
void low_GPIO(uint8_t gpio_numb)
{
        *(gpio.addr + 10) = 1 << gpio_numb;
}

//reading the state of the pin, 1 if HIGH, 0 if LOW
uint8_t read_GPIO(uint8_t gpio_numb)
{
    if(  *(gpio.addr + 13) & (1 << gpio_numb) )
    {
        return 1;
    }
    else
    {
        return 0;
    }
}
//this function is going to delay time in microseconds
void sleepMicro(unsigned int time)
{
    struct timeval now;
    struct timeval period;
    struct timeval end;

    gettimeofday(&now, NULL);
    period.tv_sec = time / 1000000;
    period.tv_usec = time % 1000000;
    timeradd(&now, &period, &end);
    while(timercmp(&now, &end, <))
    {
        gettimeofday(&now, NULL);
    }
}