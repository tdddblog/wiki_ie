package ek.wiki.parser;


import java.io.FileReader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import ek.wiki.util.XmlStreamUtils;


public class WikiPageParser 
{
    private static final String PAGE_TAG = "page";
    private static final QName ATTR_TITLE = new QName("title");
    
    private XMLEventReader reader;
    private Page page;
    
    public WikiPageParser(String filePath) throws Exception
    {
        XMLInputFactory fac = XMLInputFactory.newFactory();
        reader = fac.createXMLEventReader(new FileReader(filePath));
    }
    
    
    public Page getPage()
    {
        return page;
    }
    
    
    public void close()
    {
        try
        {
            reader.close();
        }
        catch (Exception ex)
        {
            // Ignore
        }
    }


    public boolean nextPage() throws Exception
    {
        if(XmlStreamUtils.goToTag(reader, PAGE_TAG) == false)
        {
            return false;
        }
        
        return parsePage();
    }

    
	private boolean parsePage() throws Exception
	{
		page = new Page();
		
		while(reader.hasNext())
		{
			XMLEvent event = reader.nextEvent();
			if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(PAGE_TAG))
			{
				return true;
			}
			
			if(event.isStartElement())
			{
				StartElement el = event.asStartElement();
				String elName = el.getName().getLocalPart();
				
				switch(elName)
				{
				case "id":
					page.id = reader.getElementText();
					break;
				case "title":
					page.title = reader.getElementText();
					break;
                case "redirect":
                    Attribute attr = el.getAttributeByName(ATTR_TITLE);
                    page.redirectTitle = (attr == null) ? null : attr.getValue();
                    break;
				case "revision":
					parseRevision();
					break;
				}
			}
		}

		return false;
	}


	private boolean parseRevision() throws Exception
	{
		while(reader.hasNext())
		{
			XMLEvent event = reader.nextEvent();
			if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("revision"))
			{
				return true;
			}
			
			if(event.isStartElement())
			{
				StartElement el = event.asStartElement();
				String elName = el.getName().getLocalPart();
				
				switch(elName)
				{
				case "text":
					page.text = reader.getElementText();
					break;
				}
			}
		}

		return false;
	}

}
