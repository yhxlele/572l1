package handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parserdef.Unit;

public class HandlerForUnit extends DefaultHandler {
	private List<Unit> unitList = null;
	private Unit unit = null;
	
	public List<Unit> getUnit() {
		return unitList;
	}
	
	boolean inUnit = false;
	boolean bName = false;
	boolean bId = false;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("Unit")) {
			inUnit = true;
			unit = new Unit();
			if (unitList == null) 
				unitList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("Name")) {
			if (inUnit)
				bName = true;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inUnit)
				bId = true;
		} 
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Unit")) {
        	if (unit.getName() != null)
        		unitList.add(unit);
            inUnit = false;
        }
    }
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
		if (bName) {
			unit.setName(new String(ch, start, length));
			bName = false;
		} else if (bId) {
			unit.setId(Integer.parseInt(new String(ch, start, length)));
			bId = false;
		}
	}
}
