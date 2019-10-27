package tt;

import ek.wiki.parser.Page;
import ek.wiki.parser.WikiPagesParser;


public class TestWikiPagesParser
{

    public static void main(String[] args) throws Exception
    {
        String filePath = "/tmp/wiki/multistream1.xml-p10p30302";

        WikiPagesParser parser = new WikiPagesParser(filePath);
        
        if(parser.nextPage())
        {
            Page page = parser.getPage();

            System.out.println("ID = " + page.id);
            System.out.println("Title = " + page.title);
            
            if(page.redirectTitle != null)
            {
                System.out.println("Redirect Title = " + page.redirectTitle);
            }
            else
            {
                System.out.println(page.text);
            }
        }

        parser.close();
    }

}
