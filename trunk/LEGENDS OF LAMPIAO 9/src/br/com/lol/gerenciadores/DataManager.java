package br.com.lol.gerenciadores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataManager {

	private File file;
	Properties properties;
	
	public DataManager(String filename) throws IOException{
		properties = new Properties();
		file = new File(filename);
		if(file.exists()){
			load();
		}
	}
	
	public void load() throws IOException{
		properties.load(new FileInputStream(file));
	}
	
	public void save() throws IOException{
		properties.store(new FileOutputStream(file), null);
	}
	
	public void write(String propertyName, String value){
		properties.setProperty(propertyName, value);
	}
	
	public void write(String propertyName, int value){
		properties.setProperty(propertyName, String.valueOf(value));
	}
	
	public void write(String propertyName, float value){
		properties.setProperty(propertyName, String.valueOf(value));
	}
	
	public void write(String propertyName, boolean value){
		properties.setProperty(propertyName, String.valueOf(value));
	}
	
	public String read(String propertyName, String field){
		return properties.getProperty(propertyName);
	}
	
	public int read(String propertyName, int field){
		return Integer.parseInt(properties.getProperty(propertyName));
	}
	
	public float read(String propertyName, float field){
		return Float.valueOf(properties.getProperty(propertyName));
	}
	
	public boolean read(String propertyName, boolean field){
		return Boolean.valueOf(properties.getProperty(propertyName));
	}
	
}