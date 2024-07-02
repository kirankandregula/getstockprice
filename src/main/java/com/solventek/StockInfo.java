package com.solventek;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockInfo {
    @JsonProperty("StockPrice")
    private String stockPrice;

    @JsonProperty("PERatio")
    private String peRatio;

    @JsonProperty("52WeekHigh")
    private String week52High;

    @JsonProperty("52WeekLow")
    private String week52Low;

    @JsonProperty("MarketCap")
    private String marketCap;

    @JsonProperty("Sector")
    private String sector;

    @JsonProperty("CompanyName")
    private String companyName;
    
    @JsonProperty("error")
    private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(String stockPrice) {
		this.stockPrice = stockPrice;
	}

	public String getPeRatio() {
		return peRatio;
	}

	public void setPeRatio(String peRatio) {
		this.peRatio = peRatio;
	}

	public String getWeek52High() {
		return week52High;
	}

	public void setWeek52High(String week52High) {
		this.week52High = week52High;
	}

	public String getWeek52Low() {
		return week52Low;
	}

	public void setWeek52Low(String week52Low) {
		this.week52Low = week52Low;
	}

	public String getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public StockInfo(String stockPrice, String peRatio, String week52High, String week52Low, String marketCap,
			String sector, String companyName, String error) {
		super();
		this.stockPrice = stockPrice;
		this.peRatio = peRatio;
		this.week52High = week52High;
		this.week52Low = week52Low;
		this.marketCap = marketCap;
		this.sector = sector;
		this.companyName = companyName;
		this.error = error;
	}

	public StockInfo() {
		super();
	}


    
    

    // Constructors, getters, and setters
}
