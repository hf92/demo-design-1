
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

 
public class SaveLoadFile {
	public void saveDoc(String filename, Survey survey) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(filename);  
		ObjectOutputStream outStream = new ObjectOutputStream(fileOut);  
		outStream.writeObject(survey);  
		outStream.close();  
		fileOut.close();  
	}
	
	public Survey modDoc(String filename) throws IOException, ClassNotFoundException {
		FileInputStream fileIn =new FileInputStream(filename);  
		ObjectInputStream in = new ObjectInputStream(fileIn);  
		Survey survey = (Survey) in.readObject();
		in.close();  
		fileIn.close(); 
		return survey;
	}
}

