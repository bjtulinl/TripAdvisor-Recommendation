package tut1.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;

public class Comments_reader extends JCasCollectionReader_ImplBase{

	String[] filelist;
	int current = 0;
	
	public static final String FILE_ADDRESS = "file address";
	@ConfigurationParameter(
		name = FILE_ADDRESS,
		description = "Set the file's address",
		mandatory = true)
	private String address;
	
	public static final String PARAM_TEXT_LANGUAGE = "Language"; 
	@ConfigurationParameter(
			   name = PARAM_TEXT_LANGUAGE,
			   description = "Sets the file's text language",
			   mandatory = true,
			   defaultValue = "en")
	private String language;
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		
		
		System.out.println("??");
		File file = new File(this.address);
		filelist = file.list();
		System.out.println(filelist.length);
	}
	
	@Override
	public void getNext(JCas aJCas) throws IOException, CollectionException {
		// TODO Auto-generated method stub
		aJCas.setDocumentLanguage(language);
		while(filelist[current].indexOf("DS_Store")>-1) {
			current++;
		}
		File file = new File(this.address+filelist[current]);
		System.out.println(filelist[current]+"  ss");
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream inputStream = new FileInputStream(file);
			inputStream.read(filecontent);
			inputStream.close();
		}catch(Exception e) {
			
		}
		aJCas.setDocumentText(new String(filecontent));
		current++;
	}

	public Progress[] getProgress() {
		return new Progress[] {new ProgressImpl(current+1,filelist.length,Progress.ENTITIES)};
	}
	
	public boolean hasNext() throws IOException, CollectionException{
		return current < filelist.length;
	}
	
}
