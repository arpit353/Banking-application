import java.io.*;
import java.util.*;

class file_append{
	
	public static void main(String[] args){

		File x = new File("C:\\Users\\Arpit\\Desktop\\parallel project\\Banking Application\\src\\data1.txt");

		if( x.exists() ){
			System.out.println(x.getName()+" exists!");
			try{

				Scanner sc=new Scanner(System.in);

				System.out.println("Enter Your Name \n");  
				String name=sc.nextLine();  
				System.out.println("Enter Age Name \n");  
				int age=sc.nextInt();
				String temp=sc.nextLine();  
				System.out.println("Enter Aadhar Card Number \n");  
				String aadhar_number=sc.nextLine();
				System.out.println("Enter Mobile Number \n");  
				String mobile_number=sc.nextLine();
				System.out.println("Enter username \n");  
				String username=sc.nextLine();
				System.out.println("Enter password \n");  
				String password=sc.nextLine();
				System.out.println("Money you want to deposit \n");  
				double amount=sc.nextDouble();
				    
				sc.close();  

				FileWriter f = new FileWriter("data1.txt", true);
				Formatter form;
				form = new Formatter(f);
				System.out.println("You created a file");

				form.format("%s %d %s %s %s %s %f \n",name,age,aadhar_number,mobile_number,username,password,amount);
				form.close();

				f = new FileWriter("data.txt", true);
				form = new Formatter(f);
				form.format("%s %s %f \n",username,password,amount);
				form.close();
				
			}
			catch(Exception e){
				System.out.println("You got an error");
			}

		}
		
	}

}