import java.io.*;
import java.util.*;

class file_read{
	
	public static void main(String[] args){

		File x = new File("C:\\Users\\Arpit\\Desktop\\parallel project\\Banking Application\\src\\data.txt");

		if( x.exists() ){

			Scanner s;
			System.out.println(x.getName()+" exists!");

			try{
				s = new Scanner(new File("data1.txt"));

				while(s.hasNext()){
	
					String a = s.next();
					String b = s.next();
					String c = s.next();
					String d = s.next();
					String e = s.next();
					String f = s.next();
					String g = s.next();
					System.out.printf("%s %s %s %s %s %s %s\n",a,b,c,d,e,f,g);
	
			}

			}
			catch(Exception e){
				System.out.println("Could not find file");
			}

		}
		else
		{
			System.out.println("This file does not exist !");
		}

	}

}