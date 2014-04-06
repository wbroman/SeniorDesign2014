package seniordesign.mobileappandroid;

import android.app.Activity;

public class HjorthClass extends Activity{
public double [][]Active;
public int height;
public int width;
public double [][] d1;
//public double [][] d2;
public double [][] m0;
public double [][] threshold;
public double sum;
public double sum1;
public double mean;

//check length of height
public HjorthClass (double [][]Active)
{
	height = Active.length;
	width = Active[1].length;
	//d1 = new double[height][width];
	//d2 = new double[height][width];
	m0 = new double[height][width];
	threshold= new double[height][width];
	sum = 0;
	sum1 = 0;
	mean = 0;

	
	for(int i = 0; i<height; i++)// 1st differential subtracting from previous row
	{
		  for(int j =0; j < width; j++)	
		  {
			  sum1 = sum1 + Active[i][j];
		  }		
	}
	
	mean = sum1/height;
	
	/*for(int i = 0; i<d1[0].length; i++)// 2nd differential subtracting from the row before
	{
		if(i == 0)
		{
			d2[0][0] = d1[0][0]; //since no previous row -- the difference between the previous row(non-existant) and this row is assumed to be this value
			//d2[0][1] = d1[0][1];
		}
		else
		{
		  for(int j =0; j <width; j++)	
		  {
			  d2[i][j] = d1[i][j]- d1[i-1][j];
		  }
		}		
	}*/
	
	for(int i = 0; i<height; i++)// Original moving average - is used for Activity
	{
		if(i == 0)
		{
			m0[0][0] = mean*mean/2;
			//m0[0][1] = Active[0][1]/2;
		}
		else
		{
		  for(int j =0; j < width; j++)	
		  {
			  m0[i][j] = ((Active[i][j]-mean)*(Active[i][j]-mean))/2;
		  }
		}		
	}
	
	/*for(int i = 0; i<d1[0].length; i++)// 1st differential moving average will be used for mobility
	{
		if(i == 0)
		{
			m1[0][0] = d1[i][i]/2;
			m1[0][1] = d1[0][1]/2;
		}
		else
		{
		  for(int j =0; j < width; j++)	
		  {
			  m1[i][j] = (d1[i][j]- d1[i-1][j])/2;
		  }
		}		
	}*/
	
	/*for(int i = 0; i<d2[0].length; i++)// 2nd differential moving average - will be used for complexity
	{
		if(i == 0)
		{
			m2[0][0] = d2[i][i]/2;
			m2[0][1] = d2[0][1]/2;
		}
		else
		{
		  for(int j =0; j < width; j++)	
		  {
			  m2[i][j] = (d2[i][j]- d2[i-1][j])/2;
		  }
		}		
	}*/
	
	
	for(int i = 0; i<height; i++)// Threshold, if the value is of the moving average at each data point is greater than 10,000, program outputs 1, otherwise 0
	{
		  for(int j =0; j < width; j++)	
		  {
			if(m0[i][j] >= 10000 )
			{
			  threshold[i][j] = 1;
			}
			else
			{
			  threshold[i][j]=0;
			}
		  }	
	}	
	
	for(int i = 0; i<height; i++)// Sum of all the values
	{
		  for(int j =0; j < width; j++)	
		  {
			  sum = sum + threshold[i][j];
		  }
	}
}
	public boolean returnSum()
	{
		if(sum >= 300)
		{
			return true;
		}
		else
		return false;
	}
	
	public double returnVal()
	{
		return sum;
	}
	
}

