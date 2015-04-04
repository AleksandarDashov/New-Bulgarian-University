package homework1;


public class MergeSortAlgorithm {

    private double[] container;
    MergeSortAlgorithm()
    {
        container = null;
    }
    MergeSortAlgorithm(double[] arr)
    {
        container = arr;
    }
    // This function is going to split the array into subarrays in a recursive manner, divide and conquer technique  
     public void splitMerge()
     {
        //exit recursion case
        if(container.length <= 1)
        {
            return;
        }
         
        double[] leftArr = new double [(container.length)/ 2]; //left side of the container array with half of the container size
        double[] rightArr = new double[container.length - leftArr.length]; //right side of the container array with size: container size minus left side
        for(int i = 0; i < leftArr.length; i++)
        {
            leftArr[i] = container[i]; //assigning leftArr equal to the first half of the container array
        }
        for(int j = 0; j < rightArr.length; j++)
        {
            rightArr[j] = container[leftArr.length + j]; //assigning rightArr equal to the second half of container array 
        }
        
        MergeSortAlgorithm leftSort = new MergeSortAlgorithm(leftArr);
        MergeSortAlgorithm rightSort = new MergeSortAlgorithm(rightArr);
        leftSort.splitMerge(); //recursively splitting the already splitted leftside into two parts until one element left
        rightSort.splitMerge(); 
        mergeSort(leftArr, rightArr); //merge and sort the arrays
         
     }
    /* This function is going to merge two already sorted arrays 
     * parameters:
     * @leftSide
     * @rightSide
     */
    public void mergeSort(double[] leftSide, double[] rightSide)
    {
        int currLeftIndex = 0;// relatievly long names, just in java style
        int currRightIndex = 0;
        int nextPos = 0;
        
        /* this while will loop until "curr" indexes reach the end of both leftSide AND rightSide arrays
         * if the  left element is smaller than the  right element,
         * we put the  left element in the container array.
         * if the left element is NOT smaller than the right element,
         * we put the right element in the container array.
         */ 
        
        while(currLeftIndex < leftSide.length && currRightIndex < rightSide.length)
        {
            
             
            if(leftSide[currLeftIndex] < rightSide[currRightIndex])
            {
                container[nextPos] = leftSide[currLeftIndex]; 
                currLeftIndex++; //we move the left index + 1
            }
            else
            {
                container[nextPos] = rightSide[currRightIndex];
                currRightIndex++; // we move the right index + 1
            }
             
            nextPos++; //we move the container index + 1  
        }
         
        
        while(currLeftIndex < leftSide.length) //while the curr left index is smaller than the leftSide size, we append the current leftSide element in the container array
        {
            container[nextPos] = leftSide[currLeftIndex];
            currLeftIndex++;
            nextPos++;

         }
         
        while(currRightIndex < rightSide.length) //same goes here but with the right array
        {
            container[nextPos] = rightSide[currRightIndex];
            currRightIndex++;
            nextPos++;
        }
        
     }
    public void print() //simple print function to print the container array
    {
        for(int i = 0; i < container.length; i++)
        {
            System.out.print("Element at Index " + i + ": ");
            System.out.println(container[i]);
        }
    }
    
    
    public static void main(String[] args) {
        
        double[] array = new double[11];
        array[0] = -76.3;
        array[1] = 89.2;
        array[2] = 2.3;
        array[3] = 31.4;
        array[4] = 67.00;
        array[5] = 33.98;
        array[6] = 33.99;
        array[7] = 66.999;
        array[8] = 0.1;
        array[9] = 0.2;
        array[10] = 555.666;
       
        MergeSortAlgorithm obj1 = new MergeSortAlgorithm(array);
        obj1.splitMerge();
        obj1.print();
        
    }
    
}
