package com.openproject.ivcargo.view;
/*
 * It is a single thread class(anonymous class)
 * which is used to return the id of the ticket
 * which is being filtered from the response
 * body got by the IndexController class.
 */
public class IdReturner
{
	String body;
	String id;
	Thread t;

	void getId(final String body)
	{
		this.body=body;
		this.id="";
		t=new Thread()
		{
			public void run()
			{
				String str_to_cmp="/api/v3/work_packages/";
				String gained="";
				int c=0;
				id="";
				boolean breakIt=false;
				for(int i=0;i<body.length()-3;i++)
				{
					if(breakIt) {break;}
					gained="";
					c=0;
					id="";
					if(body.charAt(i)=='h' & body.charAt(i+1)=='r' & body.charAt(i+2)=='e' & body.charAt(i+3)=='f')
					{
						i=i+7;
						while(c!=4)
						{
							gained+=body.charAt(i);
							if(body.charAt(i)=='/') 
							{
								c++;
							}
							i++;
						}
						while(c!=5)
						{
							if(gained.equals(str_to_cmp))
							{
								if(body.charAt(i)=='/') 
								{
									if(body.charAt(i+1)=='w')
									{
										breakIt=true;
									}
									break;
								}
								id+=body.charAt(i);
							}
							else
							{
								break;
							}
							i++;
						}
					}
				}
				IndexController.allMaps.put("id", id);
			}};t.start();

	}
}

