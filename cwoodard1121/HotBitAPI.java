package cwoodard1121;




import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;

public class HotbitAPI {

    String cookie;

    public HotbitAPI(String cookie) {
        this.cookie = cookie;
    }


    public BigDecimal toFloat(String input) {
        return new BigDecimal(input);
    }

    public String getMarketPrice(String ticker) {
        try {
            URL url_connect = new URL("https://api.hotbit.io/api/v1/market.last?market=" + ticker.toUpperCase() + "/USDT");
            HttpURLConnection connection = (HttpURLConnection) url_connect.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String l;
            StringBuilder builder = new StringBuilder();
            while((l = reader.readLine()) != null) {
                builder.append(l);
            }
            return (String) new JSONObject(builder.toString()).get("result");
        } catch (Exception e ) {
            System.err.println("request denied, ignoring.");
        }
        return null;
    }

    public int getDecimalPrecision(String ticker) {
        try {
            URL url_connect = new URL("https://api.hotbit.io/api/v1/market.last?market=" + ticker.toUpperCase() + "/USDT");
            HttpURLConnection connection = (HttpURLConnection) url_connect.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String l;
            StringBuilder builder = new StringBuilder();
            while((l = reader.readLine()) != null) {
                builder.append(l);
            }
            return ((String) new JSONObject(builder.toString()).get("result")).length() - new JSONObject(builder.toString()).getString("result").split("\\.")[0].length() - 1;
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return 2;
    }

    public boolean sellLimit(String ticker, String price, String amount) {
        String url_string = "https://www.hotbit.io/v1/order/create?platform=web";
        String params = "price=" + price + "&quantity=" + amount + "&market=" + ticker + "/USDT&side=SELL&type=LIMIT&hide=false&use_discount=false";
        try {
            URL url = new URL(url_string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("authority", "www.hotbit.io");
            connection.setRequestProperty("method", "POST");
            connection.setRequestProperty("path", "/v1/order/create?platform=web");
            connection.setRequestProperty("scheme", "https");
            connection.setRequestProperty("accept", "application/json, text/plain, */*");
            connection.setRequestProperty("accept-language", "en-US,en;q=0.9");
            connection.setRequestProperty("cookie", cookie);
            connection.setRequestProperty("dnt", "1");
            connection.setRequestProperty("origin", "https://www.hotbit.io");
            connection.setRequestProperty("referer", "https://www.hotbit.io/");
            connection.setRequestProperty("sec-ch-ua", "Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99");
            connection.setRequestProperty("sec-fetch-dest", "empty");
            connection.setRequestProperty("sec-ch-ua-mobile", "?0");
            connection.setRequestProperty("sec-fetch-mode", "cors");
            connection.setRequestProperty("sec-fetch-site", "same-origin");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
            connection.setRequestProperty("x-kl-ajax-req,uest", "Ajax_Request");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params);
            writer.flush();

            String l;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((l = reader.readLine()) != null) sb.append(l);
            JSONObject response = new JSONObject(sb.toString());
            String err = response.getString("Code");
            System.out.println(sb.toString());
            if(err.toLowerCase().contains("1100")) {
                System.out.println("order placed for: " + amount + " " + ticker + "s at: " + price + "/" + ticker);
                return true;
            } else {
                System.out.println("Error, aborting.");
                System.err.println("ERROR");
            }
            reader.close();
            writer.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean buyLimit(String ticker,String price, String amount) {
        String url_string = "https://www.hotbit.io/v1/order/create?platform=web";
        String params = "price=" + price + "&quantity=" + amount + "&market=" + ticker + "/USDT&side=BUY&type=LIMIT&hide=false&use_discount=false";
        try {
            URL url = new URL(url_string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("authority", "www.hotbit.io");
            connection.setRequestProperty("method", "POST");
            connection.setRequestProperty("path", "/v1/order/create?platform=web");
            connection.setRequestProperty("scheme", "https");
            connection.setRequestProperty("accept", "application/json, text/plain, */*");
            connection.setRequestProperty("accept-language", "en-US,en;q=0.9");
            connection.setRequestProperty("cookie", cookie);
            connection.setRequestProperty("dnt", "1");
            connection.setRequestProperty("origin", "https://www.hotbit.io");
            connection.setRequestProperty("referer", "https://www.hotbit.io/");
            connection.setRequestProperty("sec-ch-ua", "Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99");
            connection.setRequestProperty("sec-fetch-dest", "empty");
            connection.setRequestProperty("sec-ch-ua-mobile", "?0");
            connection.setRequestProperty("sec-fetch-mode", "cors");
            connection.setRequestProperty("sec-fetch-site", "same-origin");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
            connection.setRequestProperty("x-kl-ajax-req,uest", "Ajax_Request");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params);
            writer.flush();

            String l;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((l = reader.readLine()) != null) sb.append(l);
            JSONObject response = new JSONObject(sb.toString());
            String err = response.getString("Code");
            System.out.println(sb.toString());
            if(err.toLowerCase().contains("1100")) {
                System.out.println("order placed for: " + amount + " " + ticker + "s at: " + price + "/" + ticker);
                return true;
            } else {
                System.out.println("Error, aborting.");
                System.err.println("ERROR");
            }
            reader.close();
            writer.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
