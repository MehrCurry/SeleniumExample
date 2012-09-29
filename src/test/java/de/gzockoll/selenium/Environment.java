package de.gzockoll.selenium;

public class Environment {
	private String adminUrl;

	private String pmiUrl;

	public Environment(String adminUrl, String pmiUrl) {
		super();
		this.adminUrl = adminUrl;
		this.pmiUrl = pmiUrl;
	}

	Environment() {
	}

	public String getAdminUrl() {
		return adminUrl;
	}

	public String getPmiUrl() {
		return pmiUrl;
	}

	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}

	public void setPmiUrl(String pmiUrl) {
		this.pmiUrl = pmiUrl;
	}

}
