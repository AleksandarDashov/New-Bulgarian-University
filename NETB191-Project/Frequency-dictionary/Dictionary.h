#ifndef _DICTIONARY_H
#define _DICTIONARY_H

#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>

/* 
 * The purpose of this class is to read a file,
 * remove all special characters,
 * count the number of different words
 * and sort them by the number of occurrences
 */

class Dict
{
	
	public:
		vector<string> removeChar();// remove all special characters from the container vector.
		vector<string> removeRepetitions();// remove repetitive words from the contaioner vector.
		vector<string> getContainer(); // return the container vector itself.
		vector<string> readDic();// read words from a given file and it will push them in the container vector.
		vector<int> countWord();// count the number of occurrences for every word
		void sort();// sort the both container and word_counter vecotors
		
		int getContainerSize();// return the container size
		void print();
		
	private: 
		vector<string> container;// vector of string to hold the words from a file
		vector<int> word_counter;// vector of ints to count the words occurrences
		string wordHolder;
		string tempString;
		ifstream reader; //input file stream to READ data from a file
		ofstream writer;//output file stream to WRITE data to a file

};
#endif
