package com.pucp.chanot.dao;

import com.pucp.chanot.entity.Corpus;
import com.pucp.chanot.entity.Users;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class DaoJaxb {

	public static void writeCorpusXml( String oFile, Corpus oCorpus, String folder, String user) throws Exception{
		Marshaller mars;
		String path = folder + "/files/output/" + user + oFile;
		JAXBContext context = JAXBContext.newInstance(Corpus.class);
		mars = context.createMarshaller();
		path = path.substring(0, path.length()-3) + "xml";
		mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream(path), "UTF-8");
		mars.marshal(oCorpus, writer);
                writer.close();
	}
        
        public static void writeCleanCorpusXml( String path, Corpus oCorpus) throws Exception{
		Marshaller mars;
		JAXBContext context = JAXBContext.newInstance(Corpus.class);
		mars = context.createMarshaller();
		mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream(path), "UTF-8");
		mars.marshal(oCorpus, writer);
                writer.close();
	}
	
	public static Corpus readCorpusXml(String oFile, String folder, String user) {
		Corpus oCorpus;
		try {
			String path = folder + "/files/output/" + user + oFile;
			path = path.substring(0, path.length()-3) + "xml";
			JAXBContext context = JAXBContext.newInstance(Corpus.class);
			Unmarshaller unmar = context.createUnmarshaller();
			oCorpus = (Corpus) unmar.unmarshal(new File(path));
		} catch (Exception e) {
			return null;
		}
		return oCorpus;
	}
        
        public static Corpus readCorpusXmlFromFile(File file) {
		Corpus oCorpus;
		try {		
			JAXBContext context = JAXBContext.newInstance(Corpus.class);
			Unmarshaller unmar = context.createUnmarshaller();
			oCorpus = (Corpus) unmar.unmarshal(file);
		} catch (Exception e) {
			return null;
		}
		return oCorpus;
	}
        
        public static Users readUsersXml(String folder) {
		Users oUsers;
		try {
			String path = folder + "/files/resources/users.xml" ;
			JAXBContext context = JAXBContext.newInstance(Users.class);
			Unmarshaller unmar = context.createUnmarshaller();
			oUsers = (Users) unmar.unmarshal(new File(path));
		} catch (Exception e) {
			return null;
		}
		return oUsers;
	}
        
        public static void writeUsersXml(Users oUsers, String folder) throws Exception{
		Marshaller mars;
		String path = folder + "/files/resources/users.xml" ;
		JAXBContext context = JAXBContext.newInstance(Users.class);
		mars = context.createMarshaller();
		mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream(path), "UTF-8");
		mars.marshal(oUsers, writer);
                writer.close();
	}
}
