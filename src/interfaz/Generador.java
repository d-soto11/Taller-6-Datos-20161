package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Generador {
	
	private static int FIRST = 5490;
	private static int LAST = 88700;

	public static void main(String[] args) throws Exception{
		BufferedReader firstNameBR = new BufferedReader(new FileReader(new File("./data/first.csv")));
		BufferedReader lastNameBR = new BufferedReader(new FileReader(new File("./data/last.csv")));

		PrintWriter centralCity = new PrintWriter(new FileWriter("./data/ciudadcentral.csv"));
		
		String first = firstNameBR.readLine();
		String last = lastNameBR.readLine();
		
		int i = 0;
		long tel = 7000000;
		while (i<1000000){
			System.out.println("Vamos en: "+i);
			int j = 0;
			lastNameBR.close();
			lastNameBR = new BufferedReader(new FileReader(new File("./data/last.csv")));
			last = lastNameBR.readLine();
			while (j<LAST){
				last = lastNameBR.readLine();
				int k = 0;
				firstNameBR.close();
				firstNameBR = new BufferedReader(new FileReader(new File("./data/first.csv")));
				first = firstNameBR.readLine();
				while (k<FIRST){
					first = firstNameBR.readLine();
					tel++;
					centralCity.println(first+","+last+","+tel);
//					System.out.println(first+","+last+","+tel);
					i++;
					if (i>=1000000)
						break;
					k++;
				}
				if (i>=1000000)
					break;
				j++;
			}
			
			
			
		}
		
		firstNameBR.close();
		lastNameBR.close();
		centralCity.flush();
		centralCity.close();
	}
}
