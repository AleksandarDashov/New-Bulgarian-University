#include "Rando.h"
#include <iostream>
#include <algorithm>
#include <string>

using namespace std;

Rando::Rando()
{
	alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
}

string Rando::encode()
{
	for(int i = 0; i < sentence.size(); i++)
	{	
		for(int j = 0; j < alphabet.size(); j++)
		{
			
			if (sentence[i] == alphabet[j])
			{
				sentence[i] = cipher[j];
				
				break;
			}
		}	
	}
	cout << "Encoded string: " << sentence << endl;
	
	return sentence;
}

string Rando::decode()
{
	for(int i = 0; i < sentence.size(); i++)
	{
		for(int j = 0; j < cipher.size(); j++)
		{
			if (sentence[i] == cipher[j])
			{
				sentence[i] = alphabet[j];
				
				break;
			}
		}
	}
	cout << "Decoded string: " << sentence << endl;
	
	return sentence;
}
//returning the number of letters in the alphabet (used in the random char generator)
int Rando::getLettersSize()
{
	return letters.size();
}
//random character generator
char Rando::randLetter()
{
	int lettersLength = getLettersSize();
	
	char letterHold = letters[rand() % lettersLength];
	int found = letters.find(letterHold);
	letters = letters.erase(found, 1);
	
	return letterHold;
}
// setting user nput
string Rando::setSentence()
{
	string enterString; 
	cout << "Enter a string you wish to encode/decode: " << endl;
	std::getline(std::cin, enterString);
	sentence = enterString;
	
	return enterString;
}

string Rando::getSentence()
{
	cout << "You entered: " << sentence << endl;
	return sentence;
}
//substituting the alphabet string with random letters which generates the cipher string
string Rando::cipherGenerator()
{
	string alphabetSub = alphabet;
	
	for (int i = 0; i < alphabet.size(); i++)
	{
			
	    if(alphabetSub[i] == ' ') // if a character from the sentence is a space char
	    {
		    alphabetSub[i] = ' '; //leave all space character unchanged
	    }
	    else if(alphabetSub[i] != ' ')
	    {
		    alphabetSub[i] = randLetter(); //if it's not a space char replace it with a random letter
			
	    }	
	}
	cipher = alphabetSub;
	cout << "Alphabet: " << alphabet << endl;
	cout << "Cipher:   " << cipher << endl;

	return cipher;
}
