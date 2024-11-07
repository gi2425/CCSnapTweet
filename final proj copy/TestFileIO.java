
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestFileIO {
	public static void read() {
		try {
			FileReader fileIn = new FileReader("test_text");
			BufferedReader bufReader = new BufferedReader(fileIn);
			
			String line = bufReader.readLine();
			int count = 1;
			while (line != null) {
				System.out.println(count++ + ": " + line);
				line = bufReader.readLine();
			}
			bufReader.close();
			fileIn.close();
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void write() {
		try {
			FileWriter fileOut = new FileWriter("test_text");
			BufferedWriter bufWriter = new BufferedWriter(fileOut);
			
			for (int i = 1; i <= 10; i++) {
				bufWriter.write("This is line " + i + "\n");
			}
			
			bufWriter.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		TestFileIO.write();
		TestFileIO.read();
	}
}