#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>

using namespace std;

#include "Dictionary.h"

int main()
{
	Dict obj1;
	obj1.readDic();
	obj1.removeChar();
	obj1.countWord();
	obj1.removeRepetitions();
	obj1.sort();
	obj1.print();
	
	return 0;
}
