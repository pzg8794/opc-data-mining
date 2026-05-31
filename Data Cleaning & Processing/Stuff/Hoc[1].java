import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hoc {

	public static void main(String[] args) {

		String dir2 = "/home/piter/DCPproject/CleanedDB";
		String dir  =  "/home/piter/DCPproject/Database";
		File file = new File(dir);
		File file2 = new File(dir2);
		System.out.println(file.getName());
		System.out.println("Size = "+ file.length());
		System.out.println(file2.getName());

		FileInputStream fis = null;
		FileWriter fstream = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		Scanner fileScanner = null;
		Scanner lineScanner = null;
		File[] files = null;
		File[] files2 = null;
		File[] fols = null;
		File folder = null;
		String dest = null;
		// Reading directory contents
		if( file.length() > 1){

			files  =  file.listFiles();
			files2 = file2.listFiles();

			for (int i = 0; i < files.length; i++) {

				System.out.println(files[i].getName());
				System.out.println(files2[i].getName());
				File fTmp =  null;

				try {
					folder = new File(files[i].getAbsolutePath());

					if( folder.length() > 1){
						// Reading directory contents
						fols = folder.listFiles();
					        //System.out.println(fols);

						for (int i1 = 0; i1 < fols.length; i1++){
							//fTmp = new File(fols[i1].getAbsolutePath());
							System.out.println(fols[i1]);

							dest = files2[i] + "/"+ fols[i1].getName();

							if(fols[i1].getName().equals("DS_Store")){
								i1++;
							}

							//if(fTmp.exists()){

								fis = new FileInputStream(fols[i1]);
								in = new BufferedReader(new InputStreamReader(fis));

								fstream = new FileWriter(dest, false);
								out = new BufferedWriter(fstream);

								String aLine = null;
								int HOC=0, count;
								double avgTemp = 0, fTemp=0;
								fileScanner = new Scanner(fols[i1]);

								//fstream.flush();

								if( fols[i1].getName().contains(".txt") ){

									while (fileScanner.hasNextLine()) {
                                                        			aLine = fileScanner.nextLine();

										String[] attr = aLine.split(" ");

										int counter = 0;
										double tp1=0, tp2 =0, tmp=0;
										lineScanner = new Scanner(aLine);
										while( lineScanner.hasNext()){
											tmp = lineScanner.nextDouble();

											//System.out.println(tp1);
											if(counter == 3)
												tp1 = tmp;

											if(counter == 4)
												tp2 = tmp;

											counter++;
										}
									//	lineScanner.close();

										if(!attr[0].contains("B")){

											if(Integer.parseInt(attr[0])>=1980){

                                                                                		if( tp1 != 0 && tp2 != 0){
													avgTemp = (tp1 + tp2)/2;
													fTemp = ((avgTemp * 9)/5) + 32;
												}

												if( fTemp < 70)
													HOC = 1;
												else if(fTemp >= 70 && fTemp<=75)
													HOC = 2;
												else if(fTemp > 75 && fTemp <= 95)
													HOC = 3;
												else
													HOC = 4;

												avgTemp = Math.round(avgTemp*100.0)/100.0;
												fTemp   = Math.round(fTemp*100.0)/100.0;
												out.write(aLine+ "\t" + avgTemp + "\t " + fTemp + "\t " + HOC);
												out.newLine();
											}
										}
									}
									//
									//if(in != null){
										//try {
											//in.close();
											//out.flush();
											//out.close();
										//} catch (IOException e) {
										//	e.printStackTrace();
										//}
									//}
								}
							//}
						}
					}

				}catch(Exception e) {
					e.printStackTrace();

				}finally {

					if( in != null){
						try{
							in.close();
							out.close();
							//fis.close();
							//fstream.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
					//out.flush();
				}
			//}
			}
		}
	}
}
