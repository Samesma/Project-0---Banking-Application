package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileIO<T> {
	
	private String filename;
	
	public FileIO(String filename) {
		this.filename = filename;
	}
	
	public void writeObjects(ArrayList<T> objList) {
		//try with resources
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));){
			out.writeObject(objList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<T> readObjects() throws IOException, FileNotFoundException, ClassNotFoundException {
		ArrayList<T> ret;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
		ret = (ArrayList<T>) in.readObject();
		return ret;
	}

	
}
