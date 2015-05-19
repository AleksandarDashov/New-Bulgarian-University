#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>

using namespace std;

#include "Dictionary.h"

//will return the container size
int Dict::getContainerSize()
{
	return container.size();
}

//will return the container vector itself
vector<string> Dict::getContainer()
{
	return container;
}

/* vector<string> readDic()
 * This function will ask the user to input a filename to read from
 * if the file is open ith no errors, proceed
 * until the end of the file is reached, we read every word and we transform it into lowercase
 * push (insert) every word in a container vector
 * return the vector containing all the words
 */

vector<string> Dict:: readDic()
{
	string filename;
	cin >> filename;
	reader.open(filename.c_str());
	
	if(reader.is_open()) //if the file exists and there are no errors while opening, proceed...
	{
		while(!reader.eof()) //while not the end of the document keep it rolling.
		{
			for(int i = 0; (reader >> tempString); i++)
			{
				std::transform(tempString.begin(), tempString.end(), tempString.begin(),::tolower); //transforming all the words to lowercase letters.
				container.push_back(tempString); //pushing the words in the container vector, so each element of the vector will be separate word.
			}
		}	
	}
	else
	{
		cout << "Error while opening the file..." << endl;
	}
	return container;
}

/* vector<string> removeChar()
 * This function will find and erase special characters in the container vector
 * that are specified in the char_holder string.
 * Return the container vector without the specified special characters.
 * If you wish to remove some other characters that are not specified,
 * just add them to the char_holder string.
 */

vector<string> Dict::removeChar()
 {
 	string char_holder = "В—,.?\";"; //char_holder a string variable that hold all special characters you wish to erase from the container vector, you can add some...
	for(int i = 0; i < getContainerSize(); i++)
	{
		wordHolder = container[i];
		for(int k = 0; k < char_holder.length();k++)
		{
			int founder = wordHolder.find(char_holder[k]);
			if(founder >= 0)
			{
				wordHolder = wordHolder.erase(founder, 1);
				container[i] = wordHolder;
			}
		}
	}
	return container;
 }

/* vector<int> countWord()
 * This function will count the number of occurrences for every word in the container vector
 * returns an int vector
 */

vector<int> Dict::countWord()
{
	word_counter.resize(getContainerSize());//setting word_counters size to be equal to the container size.
	vector<string> cloneContainer = getContainer();
	
	for(int i = 0; i < getContainerSize(); i++)
	{
		for(int k = 0; k < getContainerSize(); k++)
		{
			if(cloneContainer[i] == cloneContainer[k])
			{
				word_counter[i] += 1; 
			}		
		}
	}
	return word_counter;
}

/*vector<string> removeRepetitions()
 * This function will walkthrough the container vector
 * erase repetitive words
 * erase repetitive numbers from the word_count vector
 * return the container vector
 */

vector<string> Dict::removeRepetitions()
{
	
	for(int i = 0; i < getContainerSize(); i++)
	{
		for(int j = i + 1; j < getContainerSize(); j++)
		{
			if(container[i] == container[j])
			{
				container.erase(container.begin() + j);
				word_counter.erase(word_counter.begin() + j);
			}	
		}
	}
	return container;
}

/* void sort()
 * This function will sort both the container and the word_counter vectors, by descending order of their occurrences
 * if the current element is smaller than the next one,
 * SWAP
 * returns nothing because it's void
 */

void Dict::sort()
{
	int tempNumber;
	string tempString;
	
	for(int i = 0; i < word_counter.size(); i++)
	{
		for(int j = i + 1; j < word_counter.size(); j++)
		{
			if(word_counter[i] > word_counter[j])
			{
				continue;
			}
			else
			{
				tempNumber = word_counter[j];
				word_counter[j] = word_counter[i];
				word_counter[i] = tempNumber;
				
				tempString = container[j];
				container[j] = container[i];
				container[i] = tempString;
			}	 
		}	
	}
}

//function which will print the counter and the container vector.
void Dict::print()
{	
	for(int i = 0; i < getContainerSize(); i++)
	{
		cout << word_counter[i] << " ";
		cout << container[i] << endl;	
	}
}
