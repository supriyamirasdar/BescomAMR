package in.nsoft.bescomamr;
//Nitish 9/01/2015

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class ReadServerResponse {
	boolean response = false;
	final String TAG="in.nsoft.gescomfa.ReadServerResponse";
	String rtrVal = "";
	StringBuilder sb = new StringBuilder();	
	

	public ArrayList<String> Readfilename(String result)
	{
		final ArrayList<String> arlst=new ArrayList<String>();
		try
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			DefaultHandler dh = new DefaultHandler()
			{
				public void startElement(String uri, String localName, String nodeName, Attributes atb)throws SAXException{
					sb.delete(0, sb.length());
				}
				public void endElement(String uri, String localName, String nodeName)throws SAXException{

					if(nodeName.equals("string")) 
					{						
						arlst.add(sb.toString());
					}

				}
				public void characters(char ch[], int start, int length)throws SAXException
				{
					sb.append(new String(ch, start, length));						
				}
			};
			sp.parse(new InputSource(new StringReader(result)) , dh);
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return arlst;
	}
	
	public String ReadString(String result)
	{	
		try
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			DefaultHandler dh = new DefaultHandler()
			{
				public void startElement(String uri, String localName, String nodeName, Attributes atb)throws SAXException{
					sb.delete(0, sb.length());
				}
				public void endElement(String uri, String localName, String nodeName)throws SAXException{

					if(nodeName.equals("string")) 
					{						
						rtrVal = sb.toString();
					}

				}
				public void characters(char ch[], int start, int length)throws SAXException
				{
					sb.append(new String(ch, start, length));						
				}
			};
			sp.parse(new InputSource(new StringReader(result)) , dh);
		}
		catch(Exception e)
		{
			Log.d(TAG, e.toString());
		}
		return rtrVal;
	}
	
	


}
