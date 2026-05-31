import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.InputStreamReader;

public class HOC {

	public static void main(String[] args) {

		String Database   = "/home/piter/DCPProject/Database";
		String CleanedDB  = "/home/piter/DCPproject/CleanedDB";
		File[] CDBfolders =    new File(CleanedDB).listFiles();
		File[] DBfolders  = new File(Database).listFiles();

		System.out.println(Database);
		System.out.println("Number of FIles : "+ DBfolders.length);
		System.out.println(CleanedDB);

		// Reading conetent
		BufferedWriter out = null;
		BufferedReader in = null;

		// Reading directory contents
		if( DBfolders.length > 1){

			for (int i = 0; i < DBfolders.length; i++) {

				File[] CDBFfiles = CDBfolders[i].listFiles();
				File[] DBFfiles = DBfolders[i].listFiles();
				//System.out.println(files2[i]);

				if(! DBfolders[i].isFile()){

					System.out.println("Folder 1 " + DBfolders[i].getName());
					System.out.println("Folder 2 " + CDBfolders[i].getName());

					for( int i1 = 0 ; i1 < DBFfiles.length; i1++){

						try {
							File file = new File(DBFfiles[i1].getAbsolutePath());
							String dest = CDBfolders[i] + "/"+ file.getName();
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
