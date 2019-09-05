package ru.easyxbrl.define;

import java.util.HashMap;
import java.util.Map;

public enum XbrlType {

	STRING("{http://www.xbrl.org/2003/instance}stringItemType"),
	DATE("{http://www.xbrl.org/2003/instance}dateItemType"),
	DATETIME("{http://www.xbrl.org/2003/instance}dateTimeItemType"),
	MONETARY("{http://www.xbrl.org/2003/instance}monetaryItemType"),
	INTEGER("{http://www.xbrl.org/2003/instance}integerItemType"),
	DECIMAL("{http://www.xbrl.org/2003/instance}decimalItemType"),
	PERCENT("{http://www.xbrl.org/dtr/type/numeric}percentItemType"),
	ENUM("{http://xbrl.org/2014/extensible-enumerations}enumerationItemType"),
	DOMAIN_ITEM("{http://www.xbrl.org/dtr/type/non-numeric}domainItemType"),
	TEXT_BLOCK("{http://www.xbrl.org/dtr/type/non-numeric}textBlockItemType"),
	ESCAPED("{http://www.xbrl.org/dtr/type/non-numeric}escapedItemType"),
	W3_DATE("{http://www.w3.org/2001/XMLSchema}date"),
	W3_STRING("{http://www.w3.org/2001/XMLSchema}string"),
	PURE("{http://www.xbrl.org/2003/instance}pureItemType"),
	SHARES("{http://www.xbrl.org/2003/instance}sharesItemType"),
	PER_SHARE("{http://www.xbrl.org/dtr/type/numeric}perShareItemType"),
	;
	
	private String type;
	
	private static final Map<String, XbrlType> map = new HashMap<>();
	
	XbrlType(String type) {
		this.type = type;
	}
	
	public String getItemType() {
		return type;
	}

	public static XbrlType getItemByUri(String uri) {
		if (map.isEmpty()) {
			for (XbrlType v:XbrlType.values()) {
				map.put(v.getItemType(), v);
			}
		}
		
		return map.get(uri);
	}

}
