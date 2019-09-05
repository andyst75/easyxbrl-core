package ru.easyxbrl.define;

import java.util.HashMap;
import java.util.Map;

public enum XbrlArcrole {

	MEMBER("http://xbrl.org/int/dim/arcrole/domain-member"),
	DOMAIN("http://xbrl.org/int/dim/arcrole/dimension-domain"),
	DEFAULT("http://xbrl.org/int/dim/arcrole/dimension-default"),
	PARENT_CHILD("http://www.xbrl.org/2003/arcrole/parent-child"),
	HYPERCUBE("http://xbrl.org/int/dim/arcrole/hypercube-dimension"),
	LABEL("http://www.xbrl.org/2003/arcrole/concept-label"),
	ALL("http://xbrl.org/int/dim/arcrole/all"),
	NOT_ALL("http://xbrl.org/int/dim/arcrole/notAll"),
	REFERENCE("http://www.xbrl.org/2003/arcrole/concept-reference"),
	GENERAL("http://www.xbrl.org/2003/arcrole/general-special"),
	;
	
	private String type;
	private static final Map<String, XbrlArcrole> map = new HashMap<>();
	
	XbrlArcrole(String type) {
		this.type = type;
	}
	
	public String getItemType() {
		return type;
	}

	public static XbrlArcrole getItemByUri(String uri) {
		if (map.isEmpty()) {
			for (XbrlArcrole v:XbrlArcrole.values()) {
				map.put(v.getItemType(), v);
			}
		}
		if (!map.containsKey(uri)) System.out.println("Found new type arcrole: " + uri);
		return map.get(uri);
	}

}
