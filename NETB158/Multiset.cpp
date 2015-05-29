#include <iostream>
#include <cstdlib>
#include <ctime>
using namespace std;
class Multiset
{
	private:
		int array_size; //the array size
		int* array; //dynamic array of ints
	public:
		Multiset();//default constructor
		Multiset(const Multiset &obj); //copy constructor initialization method
		~Multiset(); //destructor
		
		Multiset& operator=(const Multiset& obj); //assiment operator
		Multiset& operator++(); //++prefix
		Multiset& operator--(); //--prefix
		Multiset operator++(int); //postfix++
		Multiset operator--(int); //postfix--
		Multiset& operator<<(const Multiset& obj);
		Multiset& operator>>(const Multiset& obj);
		
		
		Multiset return_caller(Multiset& gnom); //e)
		
		int size(); //will return the array size
		
		bool contains(int f);//will return true if the array contains the number
		int count(int f);//will count the number of occurences of a given number
		
		void add(int number);
		void remove();//will remove the last element
		void remove(int number);//will remove the number of elements specifyed in the parameter list from the back
		
		void print(); //will print the array
};
//operator >> overload function
Multiset& Multiset::operator >> (const Multiset& obj)
{
	
	for(int i = 0; i < obj.array_size; i++)
	{
		
		cin >> obj.array[i];
	}
}
//operator << overload function
Multiset& Multiset::operator << (const Multiset& obj)
{
	for(int i = 0; i < obj.array_size; i++)
	{
		cout << obj.array[i] << endl; //we simply print out the elements of the target object
	}
}
//the default constructor will initialize the dynamic array 
Multiset::Multiset()
{	
	cout << "Default Constructor" << endl;
	array_size = 10;
	array = new int[array_size];
	
	for(int i = 0; i < array_size; i++)
	{
		array[i] = rand() % 100;
	}
}
//the copy contructor will take as param the object we wish to copy from
Multiset::Multiset(const Multiset &obj)
{
	cout << "Copy Constructor" << endl;
	array_size = obj.array_size;
	array = new int[obj.array_size];
	for(int i = 0; i < array_size; i++)
	{
		array[i] = obj.array[i];
	}

}
//assiment operator 
Multiset& Multiset::operator=(const Multiset& obj)
{
	cout << "Assignment operator " << endl;
	array_size = obj.array_size;
	array = new int[obj.array_size];
	for(int i = 0; i < array_size; i++)
	{
		array[i] = obj.array[i];
	}
	
}

//++prefix operator overload, which is going to double all the objects elements and return the object
Multiset& Multiset::operator++()
{
	cout << "++prefix operator overload" << endl;
	for(int i = 0; i < this->array_size; i++)
	{
		this->array[i] = this->array[i] * 2;
		
	}
	return *this;
}
//--prefix operator overload in analogy with the ++prefix
Multiset& Multiset::operator--()
{
	cout << "--prefix operator overload" << endl;
	for(int i = 0; i < this->array_size; i++)
	{
		this->array[i] = this->array[i] / 2;
	}
	return *this;
	
}

//postfix++ operator overload, which is going to return the object and double all the objects elements
Multiset Multiset::operator++(int)
{
	cout << "postfix++ operator overload" << endl;
	Multiset temp = *this;// will call the copy constructor
	for(int i = 0; i < this->array_size; i++)
	{
		this->array[i] = this->array[i] * 2;
	}
	return temp;
}
//postfix-- operator overload, which is going to return the object and divide all the objects elements
Multiset Multiset::operator--(int)
{
	cout << "postfix-- operator overload" << endl;
	Multiset temp = *this;// will call the copy constructor
	for(int i = 0; i < this->array_size; i++)
	{
		this->array[i] = this->array[i] / 2;
	}
	return temp;
}
//the destructor will deallocate the pre allocated array
Multiset::~Multiset()
{
	cout << "Destructor" << endl;
	array_size = 0;
	delete[] array;
}
//(e) the fact that the copy constructor is used to copy a return value to the caller. 
Multiset Multiset::return_caller(Multiset& gnom)
{
	return gnom;
}

//Supply add member function to add set elements;
void Multiset::add(int number)
{
	array_size = array_size + 1; //incrementing the size by 1
	int* temp = new int[array_size]; // making a new temp dynamic array of ints with the incremented size
	std::copy(this->array, this->array + array_size, temp); //copying the object's array into the temp array 
	temp[array_size - 1] = number; //inserting the new value into the temp array
	delete[] array; //deallocating the old array
	this->array = temp; //assigning the object's array to the temp array

}

//Supply remove member function to remove set elements
void Multiset::remove(int number)
{
	array_size = array_size - number;
	int* temp = new int[array_size];
	std::copy(this->array, this->array + array_size, temp);
	delete[] array;
	this->array = temp;
	
}
void Multiset::remove()
{
	array_size = array_size - 1;
	int* temp = new int[array_size];
	std::copy(this->array, this->array + array_size, temp);
	delete[] array;
	this->array = temp;
	
}
//returns the size of the objects array
int Multiset::size()
{
	return this->array_size;
}

//will count the number of occurences of a given number
int Multiset::count(int f)
{
	int counter = 0;
	for(int i = 0; i < this->array_size; i++)
	{
		if(this->array[i] == f)
		{
			counter++;
		}
	}
	return counter;
}
//will check if a given value is present in the array
bool Multiset::contains(int f)
{

	for(int i = 0; i < this->array_size; i++)
	{
		if(this->array[i] ==  f)
		{
			cout << "found the number" << endl;
			return true;
		}
		
	}
	
}
void Multiset::print()
{
	for(int i = 0; i < this->array_size; i++)
	{
		cout << this->array[i] << endl;
	}
	cout << "..end.." << endl;
}

int main()
{
	srand(time(0));
	
	/* Difference between initialization and assiment
	 *
	 * To initialize is to make ready for use.
	 * int a = 0; initialization of a to 0
	 * Giving the variable a first, useful value.
	 *
	 * Assignment is one way to do initialization.
	 * a = 1 assignment of a to 1
	 */
	cout << "***a)***" << endl;
	cout << "Initialization" << endl;
	//initialization
	Multiset a;
	Multiset b = a;
	a.print();
	b.print();
	
	
	//assignment. Without overloading the = operator this operationi is not compilable
	cout << "Assignment" << endl;
	Multiset s;
	Multiset t;
	s = t;
	s.print();
	t.print();
	
	 
	//the fact that the copy constructor is invoked if an object is passed by value to a function
	cout << "***c)***" << endl;
	Multiset z;
	Multiset x(z);
	z.print();
	x.print();
	 
	//the fact that the copy constructor is not invoked when a parameter is passed by reference
	cout << "***d)***" << endl;
	Multiset g;
	Multiset *h(&g);
	g.print();
	h->print();
	
	// the fact that the copy constructor is used to copy a return value to the caller. 
	cout << "***e)***" << endl;
	Multiset pretty;
	Multiset ugly;
	pretty.return_caller(ugly);//copy constructor called
	
	cout << "add(), remove() function demonstration" << endl;
	Multiset vader;
	vader.add(5);//successfully adding new elements at the end of the dynamic array
 	vader.add(1000);
 	vader.remove(); //will remove the last value;
 	vader.remove(2); //will remove the last 2 values
 	
 	vader.add(5);
 	vader.add(5);
 	cout << "vaders size:" << vader.size() << endl;
 	cout << "bool: " << vader.contains(5) << endl; //will search for the value int he brackets, in this case bool: 1
 	cout << "count: " << vader.count(5) << endl;//will count the number of occurences
	vader.print();
	
	
	cout << "++prefix, --prefix operator overload demonstration: " << endl;
	Multiset darth;
	darth.print();
	++darth;
	darth.print();
	--darth;
	darth.print();
	
	cout << "postfix++, postfix-- operator overload demonstration: " << endl;
	Multiset obi;
	obi.print();
	obi++;
	obi.print();
	obi--;
	obi.print();
	
	cout << "<< operator demonstration" << endl;
	Multiset kool;
	kool.print();
	obi << kool;
	
	cout << ">> operator demonstration" << endl;
	Multiset deni;
	obi >> deni;
	cout << "deni's content" << endl;
	obi << deni;
	cout << "b)" << endl;
	/*The fact that all constructed objects are automatically destroyed 
	 *When the whole program is executed the destructor deallocates all memory
	 *taken by initialization of the dynamic array, and the objects
	 *We can see that the destructor is called after all operations are executed.
	 *
	 */
	return 0;
}
