import java.io.*;
import java.util.*;
import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.InputStreamReader;

public class Hoc1 {

	public static void main(String[] args) {

		String DataBase   = "/home/piter/DCPproject/Database";
		String CleanedDB  =  "/home/piter/DCPproject/CleanedDB";

		File[] DBfolders   =  new File(DataBase).listFiles();
		File[] CDBfolders  = new File(CleanedDB).listFiles();

		System.out.println(DataBase);
		System.out.println("Number of Folders : "+ DBfolders.length);
		System.out.println(CleanedDB + "\n");

		// Reading directory contents
		if( DBfolders.length > 1){

			for (int i = 0; i < DBfolders.length; i++) {

				if(!DBfolders[i].isFile()){

					File[] DBFfiles  =  DBfolders[i].listFiles();
					File[] CDBFfiles = CDBfolders[i].listFiles();

					System.out.println("Folder 1 " + DBfolders[i].getName());
					System.out.println("Number of Files " + DBfolders[i].length());
					System.out.println("Folder 2 " + CDBfolders[i].getName() + "\n");

					for(int i1 = 0; i1 < DBFfiles.length; i1++){

						File file = new File(DBFfiles[i1].getAbsolutePath());
						//System.out.println("Testing File Size :" + file.length() + "\n");
						// Reading conetent
						BufferedReader in = null;
						BufferedWriter out = null;

						try {
							//System.out.println("Reading file " + file.getName() + " from Folder1 " + DBfolders[i].getName());
							//System.out.println("Size of file " + file.length() + "\n");

							String dest = CDBfolders[i] + "/"+ file.getName();
							FileInputStream fis = new FileInputStream(file);
							in = new BufferedReader(new InputStreamReader(fis));

							if( new File(dest).exists())
								new File(dest).delete();

							FileWriter fstream = new FileWriter(dest, false);
							out = new BufferedWriter(fstream);

							String aLine = null;
							int HOC=0;
							double avgTemp = 0, fTemp=0;

							Scanner fileScanner = new Scanner(fis);
							while (fileScanner.hasNextLine()) {
                                                	       	aLine = fileScanner.nextLine();

								String[] attr = aLine.split(" ");

								//for(int l=0; l< attr.length; l++){
								//	System.out.println(attr[l]);
								//}
								int counter = 0;
								double tp1=0, tp2 =0, tmp=0;
								Scanner lineScanner = new Scanner(aLine);
								while( lineScanner.hasNext()){

									tmp = lineScanner.nextDouble();

									//System.out.println(tp1);
									if(counter == 3)
										tp1 = tmp;

									if(counter == 4)
										tp2 = tmp;

									counter++;
								}

								if(!attr[0].contains("B")){

									if( Integer.parseInt(attr[0])>=1980){
										//System.out.println(aLine);
                                                                               if( tp1 != 0 && tp2 != 0){
											avgTemp = (tp1 + tp2)/2;
											fTemp = ((avgTemp * 9)/5) + 32;
										}

										if( fTemp < 39) // VERY COLD-> below 39
											HOC = 0;
										else if (fTemp > 38 && fTemp <= 58)// COLD     ->  39 to  58
											HOC = 1;
										else if( fTemp > 58 && fTemp <= 68)// COOL     ->  59 to  68
											HOC = 2;
										else if(fTemp  > 68 && fTemp <= 77)// CONFORT  ->  69 to  78
											HOC = 3;
										else if(fTemp  > 77 && fTemp <= 86)// NORMAL   ->  78 to  86
											HOC = 4;
										else if(fTemp  > 86 && fTemp <= 99)// HOT      ->  87 to  99
											HOC = 5;
										else if(fTemp  > 99 && fTemp <= 104)//VERY HOT -> 100 to 104
											HOC = 6;

										avgTemp = Math.round(avgTemp*100.0)/100.0;
										fTemp   = Math.round(fTemp*100.0)/100.0;
										out.write(aLine+ "\t" + avgTemp + "\t " + fTemp + "\t " + HOC);
										out.newLine();
									}
								}

							}
							// do not forget to close the buffer reader
							//in.close();
							// close buffer writer
							//out.close();

						}catch(Exception e) {
							e.printStackTrace();

						}finally {
							if(in != null){
								try {
									in.close();
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
}
