import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.InputStreamReader;

public class PreparingD {

	public static void main(String[] args) {

		String gDrive     = "/home/piter/gDrive/CleanedData/CleanedData";
		String Database   = "/home/piter/DCPproject/Database";
		File[] gDfolders  =    new File(gDrive).listFiles();
		File[] DBfolders  = new File(Database).listFiles();

		System.out.println(gDrive);
		System.out.println("Number of FIles : "+ gDfolders.length);
		System.out.println(Database);

		// Reading conetent
		BufferedWriter out = null;
		BufferedReader in = null;

		// Reading directory contents
		if( gDfolders.length > 1){

			for (int i = 0; i < gDfolders.length; i++) {

				File[] gDfiles = gDfolders[i].listFiles();
				File[] DBfiles = DBfolders[i].listFiles();
				//System.out.println(files2[i]);

				if(! gDfolders[i].isFile()){

					System.out.println("Folder 1 " + gDfolders[i].getName());
					System.out.println("Folder 2 " + DBfolders[i].getName());

					for( int i1 = 0 ; i1 < gDfiles.length; i1++){

						try {
							File file = new File(gDfiles[i1].getAbsolutePath());
							String dest = DBfolders[i] + "/"+ file.getName();
							//String dest = files2[i].getAbsolutePath();

							FileInputStream fis = new FileInputStream(file);
						        in = new BufferedReader(new InputStreamReader(fis));

							FileWriter fstream = new FileWriter(dest, false);
							out = new BufferedWriter(fstream);

							String aLine = null;
							while ((aLine = in.readLine()) != null) {

								String[] yrs = aLine.split(" ");
								if(!yrs[0].contains("B")){

									if( Integer.parseInt(yrs[0])>=1980){
										//									System.out.println(aLine);
										out.write(aLine);
										out.newLine();

									}
								}
							}
							out.flush();

									// do not forget to close the buffer reader
									in.close();
									// close buffer writer
									out.close();

						}catch(IOException e){
							e.printStackTrace();

						}finally {

							//if(in != null){
						
							//}
						}
					}
				}
			}
		}
	}
}
