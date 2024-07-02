package com.solventek;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GetStockPriceHandler implements RequestHandler<Map<String, Object>, StockInfo> {

    @Override
    public StockInfo handleRequest(Map<String, Object> input, Context context) {
        StockInfo result = new StockInfo();

        // Log the input for debugging
        context.getLogger().log("Input: " + input);

        String body = (String) input.get("body");
        Map<String, String> bodyMap;
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            bodyMap = objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            result.setError("Error parsing input: " + e.getMessage());
            return result;
        }

        if (bodyMap == null || !bodyMap.containsKey("symbol")) {
            result.setError("Invalid input: 'symbol' key is required");
            return result;
        }

        String symbol = bodyMap.get("symbol");

        try {
            // Construct the URL with the provided symbol
            String url = "https://www.google.com/finance/quote/" + symbol + ":NSE";

            // Fetch HTML content from Google Finance
            Document doc = Jsoup.connect(url).get();

            // Extract various data points
            Element priceElement = doc.selectFirst(".YMlKec.fxKbKc");
            Elements peRatioElements = doc.select(".P6K39c");
            Element sectorElement = doc.selectFirst(".AuoNKc");
            Element zzDegeElement = doc.selectFirst(".zzDege");

            // Add stock price to the result
            if (priceElement != null) {
                result.setStockPrice(priceElement.text().trim());
            }

            // Add PE ratio to the result
            if (peRatioElements != null && peRatioElements.size() >= 7) {
                Element peRatioElement = peRatioElements.get(5);
                result.setPeRatio(peRatioElement.text().trim());

                Element week52HighLowElement = peRatioElements.get(2);
                String[] highLowValues = week52HighLowElement.text().trim().split(" - ");
                if (highLowValues.length == 2) {
                    result.setWeek52High(highLowValues[0]);
                    result.setWeek52Low(highLowValues[1]);
                }

                Element marketCapElement = peRatioElements.get(3);
                result.setMarketCap(marketCapElement.text().trim());
            }

            // Add sector to the result
            if (sectorElement != null) {
                result.setSector(sectorElement.text().trim());
            }

            // Add company name to the result
            if (zzDegeElement != null) {
                Element companyNameElement = zzDegeElement.selectFirst(".zzDege");
                if (companyNameElement != null) {
                    result.setCompanyName(companyNameElement.text().trim());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            result.setError("Error fetching stock info: " + e.getMessage());
        }

        return result;
    }
}
