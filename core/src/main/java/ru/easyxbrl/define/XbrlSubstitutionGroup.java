package ru.easyxbrl.define;

public enum XbrlSubstitutionGroup {

	ITEM("{http://www.xbrl.org/2003/instance}item"),
	HYPERCUBE_ITEM("{http://xbrl.org/2005/xbrldt}hypercubeItem"),
	XBRLDT_ITEM("{http://xbrl.org/2005/xbrldt}item"),
	XBRLDT_DIMENSION_ITEM("{http://xbrl.org/2005/xbrldt}dimensionItem"),
	PART("{http://www.xbrl.org/2003/linkbase}part"),
	RESOURCE("{http://www.xbrl.org/2003/XLink}resource"),
	;
	
	private String itemType;
	
	XbrlSubstitutionGroup(String itemType) {
		this.itemType = itemType;
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public static XbrlSubstitutionGroup getItemByUri(String uri) {
		XbrlSubstitutionGroup out = null;
		for (XbrlSubstitutionGroup v:XbrlSubstitutionGroup.values()) {
			if (v.getItemType().equals(uri)) {
				out = v;
				break;
			}
		};
		return out;
	}
}
