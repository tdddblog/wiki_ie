package tt;

import java.io.FileWriter;

import ek.wiki.parser.Page;
import ek.wiki.parser.WikiPageParser;


public class TestWikiPagesParser
{

    public static void main(String[] args) throws Exception
    {
        String dataFile = "/tmp/wiki/t1.xml";
        String outFile = "/tmp/wiki/mercury.txt";

        exportText(dataFile, "Mercury (planet)", outFile);
    }
    

    public static void exportText(String dataFile, String title, String outFile) throws Exception
    {
        WikiPageParser parser = new WikiPageParser(dataFile);
        
        while(parser.nextPage())
        {
            Page page = parser.getPage();
            if(page.title.equalsIgnoreCase(title))
            {
                FileWriter writer = new FileWriter(outFile);
                writer.write(page.text);
                writer.close();
                
                break;
            }
        }
        
        parser.close();
    }
    
    
    public static void listTitles(String filePath) throws Exception
    {
        WikiPageParser parser = new WikiPageParser(filePath);
        
        while(parser.nextPage())
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
                //System.out.println(page.text);
            }
            
            System.out.println();
        }

        parser.close();
    }

}
