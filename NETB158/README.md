Define a C++ class Multiset that stores integers in a dynamically allocated array of integers. 
In a multiset, the order of elements does not matter, and elements can occur many times. 

Supply the "big three" memory management functions. Use this class to demonstrate 
(a) the difference between initialization 
Multiset a; 
Multiset b = a; 
and assignment 
Multiset s; 
Multiset t; 
s = t; 
(b) the fact that all constructed objects are automatically destroyed 
(c) the fact that the copy constructor is invoked if an object is passed by value to a function 
(d) the fact that the copy constructor is not invoked when a parameter is passed by reference 
(e) the fact that the copy constructor is used to copy a return value to the caller. 
Supply add and remove member functions to add and remove set elements; int size(),
bool contains(int f) and int count(int f) member functions.
The last function counts appearances of the number f in the multiset.
Overload the ++ operator (prefix and postfix forms) for doubling all elements, which contain only once. 
Also overload -- operator (prefix and postfix forms) in such a way that the set (a++)-- is the same as a/span>.
Overload the stream operators << and >>. Demonstrate all these functions and operators.
