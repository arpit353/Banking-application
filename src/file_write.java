import java.io.*;
import java.util.*;

class file_write{
	
	public static void main(String[] args){

		File x = new File("C:\\Users\\Arpit\\Desktop\\parallel project\\Banking Application\\src\\data.txt");

		if( x.exists() ){
			System.out.println(x.getName()+" exists!");

			final Formatter f;

			try{
				f = new Formatter("data.txt");
				System.out.println("You created a file");

				f.format("%s %s %s \n","Arpit","Khurana","Genius");
				f.format("%s %s %s \n","Arpit","Genius","Khurana");
				f.format("%s %s %s \n","Arpit","Khurana","Genius");

				f.close();
			}
			catch(Exception e){
				System.out.println("You got an error");
			}

		}
		else
		{
			System.out.println("This file does not exist !");

			final Formatter f;

			try{
				f = new Formatter("data.txt");
				System.out.println("You created a file");

				f.format("%s %s %s \n","Arpit","Khurana","Genius");
				f.format("%s %s %s \n","Arpit","Genius","Khurana");
				f.format("%s %s %s \n","Arpit","Khurana","Genius");

				f.close();
			}
			catch(Exception e){
				System.out.println("You got an error");
			}
		}

	}

}