package ru.easyxbrl.define;

public enum XbrlPeriodType {

	DURATION("duration"),
	INSTANT("instant"),
	;
	
	private String type;
	
	XbrlPeriodType(String type) {
		this.type = type;
	}
	
	public String getItemType() {
		return type;
	}

	public static XbrlPeriodType getItemByUri(String uri) {
		XbrlPeriodType out = null;
		for (XbrlPeriodType v:XbrlPeriodType.values()) {
			if (v.getItemType().equals(uri)) {
				out = v;
				break;
			}
		};
		return out;
	}

}
