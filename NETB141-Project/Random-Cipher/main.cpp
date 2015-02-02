#include <iostream>
#include <cstdlib>
#include "Rando.h"

using namespace std;

int main()
{
	srand(time(NULL));
	Rando klon;
	klon.cipherGenerator();//The program generates a cipher by replacing each Latin letter
	klon.setSentence(); //The program reads a message in English from the standard input
	klon.getSentence();
	klon.encode(); //encodes it using the random cipher
	klon.setSentence();//The program reads a ciphered message from the standard input
	klon.getSentence();
	klon.decode(); //decodes it using the same cipher
	
	return 0;
}
