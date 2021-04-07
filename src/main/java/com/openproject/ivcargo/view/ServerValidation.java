package com.openproject.ivcargo.view;

public class ServerValidation
{
	String name;
	String contact;
	String email;
	String subject;
	String priority;
	boolean allTrue;
	Thread t=null;

	//Validates name field
	boolean serverValidateName(String name)
	{
		for(int i=0;i<name.length();i++)
		{
			//checks that is there any number in the name
			if(name.charAt(i)=='0'||name.charAt(i)=='1'||name.charAt(i)=='2'||name.charAt(i)=='3'||name.charAt(i)=='4'||name.charAt(i)=='5'||name.charAt(i)=='6'||name.charAt(i)=='7'||name.charAt(i)=='8'||name.charAt(i)=='9')
			{
				return false;//number detected
			}
		}

		return true;//name field correct
	}

	//Validates contact field
	boolean serverValidateContact(String contact)
	{
		for(int i=0;i<10;i++)
		{
			if(i==0)
			{
				//verifies initials on contact field
				//(19.06.2019) only 6, 7, 8 or 9 can be accepted as initials
				if(contact.charAt(i)=='6'||contact.charAt(i)=='7'||contact.charAt(i)=='8'||contact.charAt(i)=='9')
				{
				}
				else
				{
					return false;//initial incorrect
				}
			}
			else
			{
				//verifies for any number
				if(contact.charAt(i)=='0'||contact.charAt(i)=='1'||contact.charAt(i)=='2'||contact.charAt(i)=='3'||contact.charAt(i)=='4'||contact.charAt(i)=='5'||contact.charAt(i)=='6'||contact.charAt(i)=='7'||contact.charAt(i)=='8'||contact.charAt(i)=='9')
				{
				}
				else
				{
					return false;//any character in between incorrect
				}
			}
		}

		return true;
	}

	//Validates subject field
	boolean serverValidateSubject(String subject)
	{
		for(int i=0;i<subject.length();i++)
		{
			if(subject.charAt(i)>='0'&&subject.charAt(i)<='9'||subject.charAt(i)>='a'&&subject.charAt(i)<='z'||subject.charAt(i)>='A'&&subject.charAt(i)<='Z'||subject.charAt(i)==' '||subject.charAt(i)=='!')
			{
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	//Validates email field
	boolean serverValidateEmailId(String emailId)
	{
		boolean atTheRate=false;
		boolean dot=false;
		String email=emailId;
		for(int i=0;i<email.length();i++)
		{
			if(email.charAt(i)=='@')//checks for @ in email field
			{

				atTheRate=true;//@ is present
			}
			if(email.charAt(i)=='.')//checks for . in email field
			{
				dot=true;//. is present
			}
		}
		if(atTheRate&&dot)//checks if both(@ and .) are present
		{

			return true;//present
		}
		return false;//not present
	}

	//Validates priority field
	boolean serverValidatePriority(String priority)
	{
		if(priority.equals("Low")||priority.equals("Normal")||priority.equals("High")||priority.equals("Immediate"))
		{

			return true;
		}
		return false;
	}

	//Root Validation Function
	public void serverValid(final String name,final String contact,final String email,final String subject,final String priority)
	{

		t=new Thread() {
			@SuppressWarnings("unused")
			public void run()
			{
				boolean allTrue ;
				boolean nameField=false;
				boolean priorityField=false;
				boolean subjectField=false;
				boolean emailIdField=false;
				boolean contactField=false;
				nameField=serverValidateName(name);//for name
				priorityField=serverValidatePriority(priority);//for priority
				subjectField=serverValidateSubject(subject);//for subject
				emailIdField=serverValidateEmailId(email);//for email
				contactField=serverValidateContact(contact);//for contact
				if(nameField && priorityField && subjectField && emailIdField && contactField)
				{
					MainController.result= true;
					IndexController.result=true;
				}
				else
				{
					MainController.result= false;
					IndexController.result=false;
				}
			}
		};
		t.start();

	}
}
