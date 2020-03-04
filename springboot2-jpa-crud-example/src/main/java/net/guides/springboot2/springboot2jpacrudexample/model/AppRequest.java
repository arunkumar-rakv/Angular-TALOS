package net.guides.springboot2.springboot2jpacrudexample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "apprequest")
public class AppRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "appId")
	private String appId;
	
	@Column(name = "region")
	private String region;
	
	@Column(name = "environment")
	private String environment;
	
	@Column(name = "appName")
	private String appName;
	
	@Column(name = "asgDesired")
	private int asgDesired;
	
	@Column(name = "asgMax")
	private int asgMax;
	
	@Column(name = "asgMin")
	private int asgMin;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getAsgDesired() {
		return asgDesired;
	}
	public void setAsgDesired(int asgDesired) {
		this.asgDesired = asgDesired;
	}
	public int getAsgMax() {
		return asgMax;
	}
	public void setAsgMax(int asgMax) {
		this.asgMax = asgMax;
	}
	public int getAsgMin() {
		return asgMin;
	}
	public void setAsgMin(int asgMin) {
		this.asgMin = asgMin;
	}
	
	
}
