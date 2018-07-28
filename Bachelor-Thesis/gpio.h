#ifndef GPIO_H
#define GPIO_H

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdint.h> //integer types
#include <sys/mman.h> //memory management declarations
#include <fcntl.h> // manipulate file descriptor
#include <unistd.h> //standard symbolic constants and types
#include <sys/time.h>
#include <sys/types.h>
#include <time.h>
#include <errno.h>
#include <string.h>

//GPIO functions
#define BCM2708_PERI_BASE       (0x3F000000)//Where the begginning adress of the adresable memory begins
#define GPIO_BASE               (BCM2708_PERI_BASE + 0x200000)//where the GPIOs memory begins
#define BLOCK_SIZE              (4096)

struct bcm2835_peripheral
{
    uint8_t mem_fd;
    void *map;
    volatile unsigned int *addr;//starting address of mapped area
}gpio;

//mapping memory, to "reaveal" the memory 
extern uint8_t map_peripheral(struct bcm2835_peripheral *p);
extern void unmap_peripheral(struct bcm2835_peripheral *p);

//GPIO manipulation functions
extern void input_GPIO(uint8_t gpio_numb);//setting the pin as input
extern void output_GPIO(uint8_t gpio_numb);//setting the pin as output
extern void high_GPIO(uint8_t gpio_numb);//setting the pin to HIGH
extern void low_GPIO(uint8_t gpio_numb);//setting the pin to LOW
extern uint8_t read_GPIO(uint8_t gpio_numb);//reading the state of the pin, 1 if HIGH, 0 if LOW

//time manipulation functions
extern void sleepMicro(unsigned int time);//this function is going to delay time in microseconds

#endif