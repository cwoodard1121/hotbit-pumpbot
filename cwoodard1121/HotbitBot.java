package cwoodard1121;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class HotbitBot {
    public static void main(String[] args) throws IOException, InterruptedException {



        File j = new File("cookie.txt");
        StringBuilder b = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(j));
        String l;
        while((l = reader.readLine()) != null) b.append(l);
        HotbitAPI api = new HotbitAPI(b.toString());
        final boolean instantSell = true;
        String ticker = null;
        String balance = null;
        String percent = null;
        boolean autosell = false;
        String thresh = null;
        String pAboveM = null;
        String cookie = null;
        String line;
        Scanner s = new Scanner(System.in);
        System.out.println("Input balance");
        balance = s.nextLine();
        System.out.println("Input amount above market to buy (Using a percentage in decimal form:");
        System.out.println("Example: 0.35 | That means it will do 35% above market price. default is 0.35, reccomended.");
        pAboveM = s.nextLine();
        System.out.println("Input % of balance to use");
        percent = s.nextLine();
        System.out.println("Do you want to auto sell? y/n");
        autosell = s.nextLine().toLowerCase().contains("y");
        System.out.println("Enter minimum profit % to sell");
        thresh = s.nextLine();
        System.out.println("Input ticker");
        ticker = new BufferedReader(new InputStreamReader(System.in)).readLine();
        System.out.println(ticker);
        BigDecimal price = api.toFloat(api.getMarketPrice(ticker));
        System.out.println(price);
        int precision = api.getDecimalPrecision(ticker);
        final BigDecimal thresholdProfitPercent = new BigDecimal(thresh);
        final BigDecimal thresholdProfit = thresholdProfitPercent.divide(new BigDecimal("100.0"));
        final BigDecimal thirty_percent_solo = price.multiply(new BigDecimal(pAboveM)).setScale(precision,RoundingMode.CEILING);
        final BigDecimal percentageDecimal = new BigDecimal(percent).setScale(precision,RoundingMode.CEILING);
        BigDecimal thirty_percent = price.add(thirty_percent_solo).setScale(precision,RoundingMode.CEILING);
        final BigDecimal balancedecimal = new BigDecimal(balance).setScale(precision,RoundingMode.CEILING);
        thirty_percent = thirty_percent.setScale(precision, RoundingMode.CEILING);
        final BigDecimal thresholdProfitAsync = thirty_percent.multiply(thresholdProfit).add(thirty_percent);
        final BigDecimal total = balancedecimal.divide(thirty_percent,precision,RoundingMode.HALF_UP).setScale(precision,RoundingMode.CEILING);
        final BigDecimal total_percentage = total.multiply(percentageDecimal.divide(new BigDecimal("100.0"))).setScale(precision,RoundingMode.CEILING);
        boolean success = api.buyLimit(ticker,String.valueOf(thirty_percent),String.valueOf(total_percentage).split("\\.")[0]);
        if(success) {
            System.out.println("Selling at : " + thresholdProfitAsync + "per coin and we sold " + String.valueOf(total_percentage).split("\\.")[0] + " of them" );
            String ppp = api.getMarketPrice(ticker);
            int precisionS = api.getDecimalPrecision(ticker);
            System.out.println(thresholdProfit.toString());
            System.out.println(ppp);
            System.out.println(thresholdProfit.multiply(new BigDecimal(ppp)).setScale(precisionS,RoundingMode.CEILING));
            if(autosell) {
                price = new BigDecimal(ppp).add(thresholdProfit.multiply(new BigDecimal(ppp)).setScale(precisionS, RoundingMode.CEILING)).setScale(precisionS, RoundingMode.CEILING);
                api.sellLimit(ticker, String.valueOf(price.setScale(precisionS, RoundingMode.CEILING)), String.valueOf(total_percentage).split("\\.")[0]);
            }
            System.out.println("Order setup");

        } else {
            try {
                System.err.println("ABORTING..");
                System.err.println("We did not order the coins, some error happened. Check logs!");
                Thread.sleep(3000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
