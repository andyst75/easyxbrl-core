package ru.easyxbrl.define;

public enum XbrlTypeBalance {

	DEBIT("debit"),
	CREDIT("credit"),
	;
	
	private String type;
	
	XbrlTypeBalance(String type) {
		this.type = type;
	}
	
	public String getItemType() {
		return type;
	}

	public static XbrlTypeBalance getItemByUri(String uri) {
		XbrlTypeBalance out = null;
		for (XbrlTypeBalance v:XbrlTypeBalance.values()) {
			if (v.getItemType().equals(uri)) {
				out = v;
				break;
			}
		};
		return out;
	}

}
