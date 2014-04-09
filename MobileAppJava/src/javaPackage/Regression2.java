package javaPackage;

import org.apache.commons.math3.stat.regression.SimpleRegression;

/*
 * Mobile Application for ECE 4825 Senior Design Course
 * Written by William Broman
 * George Washington University Class of 2014
 * 1 March 2014
 */

public class Regression2  {
	
	double lLungDamage;
	double rLungDamage;
	double heartDamage;
	double liverDamage;
	double lIntestineDamage;
	double stomachDamage;
	double spleenDamage;
	double gallbladderDamage;
	double lKidneyDamage;
	double rKidneyDamage;
	double sIntestineDamage;
	double pancreasDamage;
	double venaCavaDamage;
	double dAortaDamage;
	double aAortaDamage;
	
	double x1,y1,x2,y2,x3,y3,x4,y4;
	
	public Regression2(double x1,double y1,double x2, double y2 /*,double x3,double y3,double x4,double y4*/)
	{
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		/*
		this.x3=x3;
		this.y3=y3;
		this.x4=x4;
		this.y4=y4;
		*/
	}
	
	public void computation(){
		//perform linear regression on variables to find the best fit line

		double[][] points = {{x1,y1}, {x2, y2}, {x3, y3}};
		SimpleRegression regression = new SimpleRegression(true);//true for enabling the intercept
		regression.addData(points);
		
		double slope1 = regression.getSlope();//defines slope
		
		double intercept1 = regression.getIntercept();
		
		double coords[][]=new double[64][2];//array for storing the coordinates of the result
		
		int index=0;//To keep a track of number of elements added to the list
		
		double m1;
		double m2;
		
		/*
		 * The for loop below cycles through the 8x8 array to test every point for whether it is on
		 * the same line as the best fit line between the coordinates given here by the Frame.java
		 * class, or ultimately the coordinates received via Bluetooth.
		 */
		
		for(int x=0;x<8;x++)
		{
		    for(int y=0;y<8;y++)
		    {
		        if( (x!=x1 && y!=y1) && (x!=x3 && y!=y3) )
		        {
    		        m1=computeSlope(x1,y1, x,y);
    		        m2=computeSlope(x3,y3, x,y);
    		        
    		        if(checkSlope(slope1,m1) || checkSlope(slope1,m2)) //slope1 is determined above
    		        {
    		            coords[index][0]=x;
    		            coords[index][1]=y;
    		            index++;
    		        }
		        }
		    }
		}
		
		
		//Add original points to coords
		coords[index][0]=x1;
		coords[index][1]=y1;
		index++;
		coords[index][0]=x3;
		coords[index][1]=y3;
		index++;
		
		coords=totalAffected(coords);
		//Update the coords array to accomodate the points to the right and left of every point	
		
        int lLungArray[] = {1,	1,	1,	1,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,  0,	0,	0,	0};
    	int rLungArray[] = {0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int heartArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int liverArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	1,	1,	1,	1,	0,	1,	1,	1,	1,	1,	1,	1,	0,	1,	1,	1,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int lIntestineArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	1,	1,	0,	0,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1};
    	int stomachArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	1,	1,	1,	1,	1,	1,	1,	0,	0,	1,	1,	1,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int spleenArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int gallbladderArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int lKidneyArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int rKidneyArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int sIntestineArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	1,	1,	1,	1,	1,	1,	0,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1,	1};
    	int pancreasArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	1,	1,	1,	1,	1,	1,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int venaCavaArray[] = {0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0};
    	int dAortaArray[] = {0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	1,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    	int aAortaArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0,	0,	0,	0,	1,	1,	0,	0,	0};
    	
    	 lLungDamage = organDamage(coords,index,lLungArray);//Compute the damage occured to left lung
    	 rLungDamage = organDamage(coords,index,rLungArray);
    	 heartDamage = organDamage(coords,index,heartArray);
    	 liverDamage = organDamage(coords,index,liverArray);
    	 lIntestineDamage = organDamage(coords,index,lIntestineArray);
    	 stomachDamage = organDamage(coords,index,stomachArray);
    	 spleenDamage = organDamage(coords,index,spleenArray);
    	 gallbladderDamage = organDamage(coords,index,gallbladderArray);
    	 lKidneyDamage = organDamage(coords,index,lKidneyArray);
    	 rKidneyDamage = organDamage(coords,index,rKidneyArray);
    	 sIntestineDamage = organDamage(coords,index,sIntestineArray);
    	 pancreasDamage = organDamage(coords,index,pancreasArray);
    	 venaCavaDamage = organDamage(coords,index,venaCavaArray);
    	 dAortaDamage = organDamage(coords,index,dAortaArray);
    	 aAortaDamage = organDamage(coords,index,aAortaArray);	
    	
    	System.out.println("Left Lung "+ lLungDamage +"% hit");
    	System.out.println("Right Lung "+ rLungDamage +"% hit");
    	System.out.println("Heart "+ heartDamage +"% hit");
    	System.out.println("Liver "+ liverDamage +"% hit");
    	System.out.println("Small Intestine "+ sIntestineDamage +"% hit");
    	System.out.println("Large Intestine "+ lIntestineDamage +"% hit");
    	System.out.println("Stomach "+ stomachDamage +"% hit");
    	System.out.println("Spleen "+ spleenDamage +"% hit");
    	System.out.println("Gallbladder "+ gallbladderDamage +"% hit");
    	System.out.println("Left Kidney "+ lKidneyDamage +"% hit");
    	System.out.println("Right Kidney "+ rKidneyDamage +"% hit");
    	System.out.println("Pancreas "+ pancreasDamage +"% hit");
    	System.out.println("Vena Cava "+ venaCavaDamage +"% hit");
    	System.out.println("Descending Aorta "+ dAortaDamage +"% hit");
    	System.out.println("Ascending Aorta "+ aAortaDamage +"% hit");
    		
}

public static boolean checkSlope(double slope1,double slope2)//Determines what points are in my range
{
    double error=(slope1-slope2)/slope1; //Checking difference between original slope given by impacts, and new slope
    //given by checking other points in the grid.
	
    return (error<=0.25);
	
}

public static double  computeSlope(double x1,double y1,double x2,double y2)
{
	SimpleRegression regression = new SimpleRegression(true);//true for enabling the intercept
	regression.addData(x1,y1);
	regression.addData(x2,y2);
	
	return regression.getSlope();
}

public static double[][] totalAffected(double oldCcoords[][]){
	
	int index1 = 0;
	double[][] finalCoords = new double[64][2];
	int a = 1; //Uses variable to declare how many points to the left and right should be included to account
			   //for size differences in vest. In the Senior Design application this function will be defaulted
			   //to 1, but in future versions of the application if they exist this can be adjusted in-app.
	
	for(int i=0;i<8;i++){
		finalCoords[index1][0] = oldCcoords[i][0]; //#1
		finalCoords[index1][1] = oldCcoords[i][1];
		index1++;
		
		finalCoords[index1][0] = oldCcoords[i][0] + a; //adds a to the x value, so if the 
		finalCoords[index1][1] = oldCcoords[i][0]; //value in #1 is 2, then here the value becomes 3
		index1++;
		
		finalCoords[index1][0] = oldCcoords[i][0] - a;
		finalCoords[index1][1] = oldCcoords[i][1];
		index1++;
	}
	
	return finalCoords;
	
}

public static int organSize(int[] organMap)
{
    int total=0;
    
    for(int i=0;i<64;i++){
        if(organMap[i]==1){
            total=total+organMap[i];
        }
    }
        
    return total;
}

public static double organDamage(double coordinates[][],int index1,int[] organMap)
{
    int counter=0;
    
   double x,y;
    
    for(int i=0;i<index1;i++)
    {
        x=coordinates[i][0];
        y=coordinates[i][1];
        
        if(organMap[(int) (x*8+y)]==1)//Organ damage at this point has occurred
            counter++;
            
    }
    
    double damage=counter*100.0/organSize(organMap);
    
    return damage;
    
}

}
