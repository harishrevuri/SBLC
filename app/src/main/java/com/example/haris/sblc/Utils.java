package com.example.haris.sblc;

import java.text.DecimalFormat;

public class Utils {
    private static DecimalFormat priceFormatter = new DecimalFormat("#,###,##0.00");

    public static String formatPrice(double amount) {
        return priceFormatter.format(amount);
    }
}
