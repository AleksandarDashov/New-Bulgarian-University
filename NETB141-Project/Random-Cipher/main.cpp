#include <iostream>
#include <ctime>
#include <cstdlib>
#include "Rando.h"

using namespace std;



int main()
{
	srand(time(NULL));
	Rando klon;
	klon.cipherGenerator();
	klon.setSentence();
	klon.getSentence();
	klon.encode();
	klon.decode();


	return 0;
}
