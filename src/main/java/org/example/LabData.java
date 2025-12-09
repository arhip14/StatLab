package org.example;

import java.util.Arrays;

public class LabData {
    public static final double[] RAW_DATA = new double[]{
            135, 145, 155, 126, 158, 147, 174, 184, 150, 160,
            151, 161, 137, 166, 153, 128, 163, 180, 164, 154,
            156, 146, 165, 159, 136, 147, 181, 130, 148, 160,
            132, 161, 149, 166, 150, 163, 138, 179, 164, 151,
            152, 139, 156, 153, 168, 157, 177, 154, 133, 158,
            160, 146, 169, 176, 145, 166, 147, 162, 186, 140,
            141, 164, 148, 170, 175, 149, 163, 168, 150, 187,
            151, 171, 142, 152, 162, 170, 180, 153, 158, 189,
            159, 154, 182, 172, 146, 143, 164, 173, 148, 190,
            150, 173, 159, 195, 151, 168, 184, 144, 163, 154
    };

    public static double[] getSortedData() {
        double[] copy = Arrays.copyOf(RAW_DATA, RAW_DATA.length);
        Arrays.sort(copy);
        return copy;
    }
}