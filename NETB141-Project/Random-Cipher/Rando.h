#ifndef RANDO_H
#define RANDO_H
#include <string>

using namespace std;

class Rando
{
	public:
		Rando();
		char randLetter();
		string cipherGenerator(); //substituting the alphabet with random letters
		string setSentence(); //set the sentence
		string getSentence(); //return the sentence
		string encode(); //encoding english message into ciphered message
		string decode(); //decoding ciphered message into english message
		int getLettersSize();
	private:
		string letters;
		string alphabet;
		string sentence;
		string cipher;
};

#endif
