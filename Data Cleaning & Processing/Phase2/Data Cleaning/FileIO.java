import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileIO 
{
	public static void main(String[] args) 
	{
		String dir = "/Users/pitergarcia/SkyDrive/workspace/bigdata/CleanedData1/";
		String dir2 = "/Users/pitergarcia/SkyDrive/workspace/bigdata/Cleaned/";
		File file = new File(dir);
		File file2 = new File(dir2);

		// Reading directory contents
		File[] files = file.listFiles();
		File[] files2 = file2.listFiles();

		for (int i = 2; i < files.length; i++) {
			System.out.println(files[i]);
			//			System.out.println(files2[i]);
			if( i == 0){

			}else{

				// Reading conetent
				BufferedReader reader = null;

				try {

					File folder = new File(files[i].getAbsolutePath());
					// Reading directory contents
					File[] fols = folder.listFiles();
					//					System.out.println(fols);

					for (int i1 = 0; i1 < fols.length; i1++){
						//						System.out.println(fols[i1]);

						String dest = files2[i] + "/"+ fols[i1].getName();

						FileInputStream fis = new FileInputStream(fols[i1]);
						BufferedReader in = new BufferedReader(new InputStreamReader(fis));

						FileWriter fstream = new FileWriter(dest, true);
						BufferedWriter out = new BufferedWriter(fstream);

						String aLine = null;
						while ((aLine = in.readLine()) != null) {

							String[] yrs = aLine.split(" ");
							if(!yrs[0].contains("B")){
								if( Integer.parseInt(yrs[0])>=1980)
								{
									//									System.out.println(aLine);
									out.write(aLine);
									out.newLine();

								}
							}
						}
						// do not forget to close the buffer reader
						in.close();

						// close buffer writer
						out.close();
					}

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