package com.rohit.assignment_10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



public class ContactService {
	
	void addContact(Contact contact,List<Contact> contacts)
    {
        Scanner s = new Scanner(System.in);
        int n;
        List<String> c = new ArrayList<>();   
        System.out.println("Enter contact id : ");
        contact.setContactID(s.nextInt());
        System.out.println("Enter contact name : ");
        contact.setContactName(s.nextLine());
        System.out.println("Enter contact email : ");
        contact.setEmail(s.nextLine());
        System.out.println("How many contact numbers to enter :");
        n = s.nextInt();
        System.out.println("Enter the "+n +" contact number s : ");
        s.nextLine();
        for(int i=0; i<n; i++)
            c.add(s.nextLine());
        contact.setContactNumber(c);
        
        //add to list
        contacts.add(contact);
    }//addContact
	
	void RemoveContact(Contact contact, List<Contact> contacts) throws ContactExceptionClass
	{
		for(Contact c : contacts)
            if(c.getContactID() == contact.getContactID())
            {
                contacts.remove(c);
                System.out.println("Contact Removed!!");
                return;
            }
        
        throw new ContactExceptionClass("Contact Not Found!!");
    }
    
	 List<String> searchContactByName(String name, List<Contact> contacts) throws ContactExceptionClass
	    {
		 List<String> searches = new ArrayList<>();
	        
	        for(Contact c : contacts)
	        {
	            List<String> l = c.getContactNumber();
	            for(String x : l)
	                if(x.contains(name))
	                    searches.add(x);
	        }
	        
	        if(searches.isEmpty())
	            throw new ContactExceptionClass("No numbers found!!");
	        
	        return searches;
	    }
	 
	 List<Contact> SearchContactByNumber(String number, List<Contact> contact) throws ContactExceptionClass
	 {
		 Contact temp = null;
		 List<Contact> result  = new ArrayList<>();
		 for(Contact c : contact)
		 {
			 for(String s : c.getContactNumber())
			 {
				 if(s.contains(number))
				 {
					 result.add(c);
				 }
			 }
		 }
		 
		 if(temp == null)
			 throw new ContactExceptionClass("Contact Not Found Exception");
		 
		return contact;
		 
	 }
	 
	 void addContactNumber(int contactId, String contactNo, List<Contact> contacts)
	    {
	        for(Contact c : contacts)
	        {
	            if(c.getContactID() == contactId)
	            {
	                List<String> n = c.getContactNumber();
	                n.add(contactNo);
	                c.setContactNumber(n);
	                System.out.println("Added Successfully!!");
	                return;
	            }
	        }
	    }
	 
	 void sortContactsByName(List<Contact> contacts)
	    {
		 
		  contacts.sort(Comparator.comparing(c -> c.getContactName()));
		  contacts.forEach(c-> System.out.println(c));
	    
	    }
	 
	 void readContactsFromFile(List<Contact> contacts, String fileName) throws NumberFormatException, IOException
	 {
		   
			String line = "";
			FileReader file = new FileReader(fileName);
			BufferedReader br = new BufferedReader(file);
			List<String> l = new ArrayList<>();
			Contact c;
			while((line = br.readLine()) != null)
			{
				 c = new Contact();
				String[] words = line.split(",");
				c.setContactID(Integer.parseInt((words[0])));
				c.setContactName(words[1]);
				c.setEmail(words[2]);
				{//contact number exists
	                for(int i=3; i<words.length; i++)
	                {
	                    l.add(words[i]);
	                    System.out.println(l.get(l.size()-1));
	                }
	            }
	            c.setContactNumber(l);
	           
			}
			
	        System.out.println("Read Successfully!!");
	 }
	 
	 public static File getSerializedDirectory(String str)
	    {
	        File serializedDir = new File(str);
	        if (!serializedDir.exists()) {
	            serializedDir.mkdir();
	        }
	        return serializedDir;   
	    }
	 
	 // serialize the data
	 void serializeContactDetails(List<Contact> contacts , String string)
	 {
		 try
         {
            FileOutputStream fileOut =
            new FileOutputStream(string+"//serializedData");
            ObjectOutputStream out =
                               new ObjectOutputStream(fileOut);
            out.writeObject(contacts);
            out.close();
             fileOut.close();
         }catch(IOException i)
         {
             i.printStackTrace();
         }
	 }
	 
	 // deserialize
	 List<Contact> deserializeContact(String filename)
	 {
		 Contact object1 = null;
			ArrayList<Contact> contact = new ArrayList<Contact>();  
	        // Deserialization 
	        try
	        {    
	            // Reading the object from a file 
	            FileInputStream file = new FileInputStream(filename); 
	            ObjectInputStream in = new ObjectInputStream(file); 
	              
	            // Method for deserialization of object 
	            object1 = (Contact)in.readObject(); 
	            contact.add(object1);  
	            in.close(); 
	            file.close(); 
	              
	        } 
	          
	        catch(IOException ex) 
	        { 
			
			 System.out.println("IOException is caught"); 
	        } 
	          
	        catch(ClassNotFoundException ex) 
	        { 
	            System.out.println("ClassNotFoundException is caught"); 
	        } 
	        return contact;
		 
	 }
	 
	 Set<Contact> populateContactFromDb() throws NumberFormatException, SQLException
	 {
		 Connection conn;
	        Statement stmt;
	        ResultSet rs;
	        Set<Contact> s = new HashSet<>();
	        Contact c;
	        String temp;
	        int cols, i;
	        List<Object> t = new ArrayList<>();
	        
	        conn = DBConnection.getcon();
	        
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery("select * from contact_tbl");
	        ResultSetMetaData rsmd = rs.getMetaData();
	        cols = rsmd.getColumnCount();
	        
	        while(rs.next())
	        {
	            c = new Contact();
	            c.setContactID(Integer.parseInt(rs.getString(1)));
	            c.setContactName(rs.getString(2));
	            c.setEmail(rs.getString(3));
	            temp = rs.getString(4);
	            if(temp!=null)
	                c.setContactNumber(Arrays.asList(temp.split(",")));
	            
	            s.add(c);
	        }
	        
	        conn.close();
	        stmt.close();
	        return s;
		 
	 }

	public void display(List<Contact> contacts) {
		// TODO Auto-generated method stub
		
	}
	
	

	public boolean addContact(List<Contact> contacts, Set<Contact> populateContactFromDb) {
		//existingContact.addAll(newContacts);
        return true;
	}
	    
	 
	 
	 
	 

}
