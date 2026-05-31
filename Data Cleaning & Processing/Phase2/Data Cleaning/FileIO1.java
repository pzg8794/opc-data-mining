import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileIO1 
{
	public static void main(String[] args) 
	{
		String dir = "/Users/pitergarcia/SkyDrive/workspace/bigdata/CleanedData1/";
		String dir2 = "/Users/pitergarcia/SkyDrive/workspace/bigdata/Cleaned/";
		String dir1 = "/Users/pitergarcia/SkyDrive/workspace/bigdata/Sizes/";
		File file = new File(dir);
		File file2 = new File(dir2);

		// Reading directory contents
		File[] files = file.listFiles();
		File[] files2 = file2.listFiles();

		
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i]);
			System.out.println(files2[i]);
						
			if( i == 0){

			}else{

				// Reading conetent
				BufferedReader reader = null;

				try {
					
					File folder1 = new File(files[i].getAbsolutePath());
					File folder2 = new File(files2[i].getAbsolutePath());
					// Reading directory contents
					File[] fols1 = folder1.listFiles();
					File[] fols2 = folder2.listFiles();
					//					System.out.println(fols);

					int size1 = 0;
					int size2 = 0;
					
					String dest = dir1 + "/" +files[i].getName()+"Size.txt";
					FileWriter fstream = null;
					try {
						fstream = new FileWriter(dest, true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					BufferedWriter out = new BufferedWriter(fstream);
//					out.write(files[i].getName());
					
					for (int i1 = 0; i1 < fols1.length; i1++){
												
						size1 += fols1[i1].length();
						size2 += fols2[i1].length();
//						System.out.print(size1);
//						System.out.println(" \t " + size2);
						if( size1 > 0  && size2 > 0)
							out.write(size1 + " \t " + size2 + "\n");
						

//						String aLine = null;
//						while ((aLine = in.readLine()) != null) {
//
//							String[] yrs = aLine.split(" ");
//							if(!yrs[0].contains("B")){
//								if( Integer.parseInt(yrs[0])>=1980)
//								{
//									//									System.out.println(aLine);
//
//								}
//							}
//						}
						
						
//						// do not forget to close the buffer reader
//						in.close();
//
//						// close buffer writer
						
					}
					out.close();
//					System.out.println(size2);
				

				}catch(Exception e) {
					e.printStackTrace();

				}finally {
					if(reader != null)
					{
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}


			}   
		}
	}
}