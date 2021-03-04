package com.rohit.assignment_10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactServiceTest
{
	
	public static void main(String[]args)
	{
		
	int ch=0, id, i=0;
	Scanner scan = new Scanner(System.in);
    List<Contact> contacts = new ArrayList<>();
    ContactService cs = new ContactService();
    List<String> l;
    List<Contact> temp;
   
    Contact c ;
    
    try
    {
        while(ch!=12)
        {
            System.out.println("1. Add a contact : ");
            System.out.println("2. Remove a contact");
            System.out.println("3. Search contact by name");
            System.out.println("4. Search by number");
            System.out.println("5. Add Contact number to existing contacts");
            System.out.println("6. Sort Contacts By Name");
            System.out.println("7. Read from file");
            System.out.println("8. Display");
            System.out.println("9. Serialize Objects");
            System.out.println("10.Deserialize Objects");
            System.out.println("11. Populate from DB");
            System.out.println("12. Exit");
            System.out.println("Enter choice  : ");
            ch  = scan.nextInt();
            
            switch(ch)
            {
                case 1: cs.addContact(new Contact(), contacts);
                        System.out.println("Added Successfully");
                        break;
                        
                case 2: System.out.println("Enter contact id to remove : ");
                        id  =scan.nextInt();
                        c = new Contact();
                        c.setContactID(id);
                        cs.RemoveContact(c, contacts);
                        break;
                      
                case 3: System.out.println("Enter name : ");        
                        scan.nextLine();
                        c = (Contact) cs.searchContactByName(scan.nextLine(), contacts);
                        System.out.println("CONTACT FOUND!!");
                        System.out.println("Contac Id : " + c.getContactID());
                        System.out.println("Contac Name : " + c.getContactName());
                        System.out.println("Contact Email : "+ c.getEmail());
                        break;
                        
                case 4: System.out.println("Enter Number : ");
                        scan.nextLine();
                        l = cs.searchContactByName(scan.nextLine(), contacts);
                        System.out.println("NUMBERS FOUND: ");
                        for(String x: l)
                            System.out.println(x);
                        break;
                      
                case 5: System.out.println("Enter contact id : ");
                        id = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Enter contact number : ");
                        cs.addContactNumber(id, scan.nextLine(), contacts);
                        break;
                        
                case 6: cs.sortContactsByName(contacts);
                        break;
                        
                        
                case 7: cs.readContactsFromFile(contacts, "e:/Contact.txt");
                        break;
                      
                case 8: cs.display(contacts);
                        break;
                        
                case 9: cs.serializeContactDetails(contacts , "file.txt");
                        break;
                        
                case 10: temp = cs.deserializeContact("file.txt");
                         System.out.println("Deserialized Objects :");
                         cs.display(temp);
                         break;
                         
                         
                case 11: if(cs.addContact(contacts, cs.populateContactFromDb()))  
                            System.out.println("Populated Succesfully!!");
                         break;
                         
                case 12: break;
            }
        }
    }
    catch(ContactExceptionClass cnfe)
    {
        System.out.println("Contact not Found");
    }
    catch(Exception ex)
    {
        System.out.println(ex);
    }
}

}
