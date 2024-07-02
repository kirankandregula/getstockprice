package com.solventek;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class GetStockPriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetStockPriceApplication.class, args);
	}

}

//Your existing imports...

@RestController
class StockPriceController {

@GetMapping("/stockPrice/{symbol}")
public String getStockInfo(@PathVariable String symbol) {
   try {
       // Construct the URL with the provided symbol
       String url = "https://www.google.com/finance/quote/" + symbol + ":NSE";

       // Fetch HTML content from Google Finance
       Document doc = Jsoup.connect(url).get();

       // Extract various data points
       Element priceElement = doc.selectFirst(".YMlKec.fxKbKc");
       Elements peRatioElements = doc.select(".P6K39c"); // Corrected selector
       Element sectorElement = doc.selectFirst(".AuoNKc");
       Element zzDegeElement = doc.selectFirst(".zzDege"); // New class selector

       // Create a map to store the data
       Map<String, String> data = new HashMap<>();

       // Add stock price to the map
       if (priceElement != null) {
           data.put("StockPrice", priceElement.text().trim());
       }

       // Add PE ratio to the map
       if (peRatioElements != null && peRatioElements.size() >= 7) {
           Element peRatioElement = peRatioElements.get(5); // Get the 7th element
           data.put("PERatio", peRatioElement.text().trim());
       }

       // Add 52-week high and low to the map
       if (peRatioElements != null && peRatioElements.size() >= 7) {
           Element peRatioElement = peRatioElements.get(2); // Get the 3rd element
           String[] highLowValues = peRatioElement.text().trim().split(" - "); // Split high and low values
           if (highLowValues.length == 2) {
               data.put("52WeekHigh", highLowValues[0]);
               data.put("52WeekLow", highLowValues[1]);
           }
       }
       
       // Add MARKETCAP to the map
       if (peRatioElements != null && peRatioElements.size() >= 7) {
           Element marketCap = peRatioElements.get(3); // Get the 7th element
           data.put("MarketCap", marketCap.text().trim());
       }

       // Add sector to the map
       if (sectorElement != null) {
           data.put("Sector", sectorElement.text().trim());
       }

       // Add zzDege company name and market cap to the map
       if (zzDegeElement != null) {
           Element companyNameElement = zzDegeElement.selectFirst(".zzDege");
           data.put("CompanyName", companyNameElement.text().trim());

       }

       // Convert the map to JSON string
       ObjectMapper objectMapper = new ObjectMapper();
       return objectMapper.writeValueAsString(data);

   } catch (IOException e) {
       e.printStackTrace();
       return "Error fetching stock info";
   }
}

}
