package utils;

public enum Browser {
	
	CHROME("chrome"),
	MSEDGE("msedge"),
	FIREFOX("firefox");
	
	private static Browser currentBrowser = Browser.CHROME;
	
	private String browser = null;
	
	Browser(String value) {
		this.browser = value;
	}
	
	public String getValue() {
		return this.browser;
	}
	
	public static Browser getCurrentBrowser() {
		return currentBrowser;
	}

	public static void setCurrentBrowser(Browser currentBrowser) {
		Browser.currentBrowser = currentBrowser;
	}
	
}
