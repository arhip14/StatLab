package org.example;

import java.text.DecimalFormat;
import java.util.*;

public class Calculations {
    private static final DecimalFormat df = new DecimalFormat("#.####");

    public static String getDiscreteReport(double[] data, TreeMap<Double, Integer> freq) {
        double mean = 0;
        for (double v : data) mean += v;
        mean /= data.length;

        double var = 0;
        for (double v : data) var += Math.pow(v - mean, 2);
        double varPop = var / data.length;
        double varSample = var / (data.length - 1);

        StringBuilder sb = new StringBuilder();
        sb.append("ХАРАКТЕРИСТИКИ (Дискретні):\n");
        sb.append("-----------------------------\n");
        sb.append(String.format("%-25s %s\n", "Середнє:", df.format(mean)));
        sb.append(String.format("%-25s %s\n", "Медіана:", df.format(calculateMedian(data))));
        sb.append(String.format("%-25s %s\n", "Мода:", findModes(freq)));
        sb.append(String.format("%-25s %s\n", "Дисперсія (виб):", df.format(varPop)));
        sb.append(String.format("%-25s %s\n", "Дисперсія (випр):", df.format(varSample)));
        sb.append(String.format("%-25s %s\n", "СКВ (виб):", df.format(Math.sqrt(varPop))));
        sb.append(String.format("%-25s %s\n", "СКВ (випр):", df.format(Math.sqrt(varSample))));
        sb.append(String.format("%-25s %s%%\n", "Коеф. варіації:", df.format(Math.sqrt(varSample) / mean * 100)));
        return sb.toString();
    }

    public static String getIntervalReport(double[] starts, double h, int[] ni, double[] mids, int n) {
        double mean = 0;
        for (int i = 0; i < mids.length; i++) mean += mids[i] * ni[i];
        mean /= n;

        // 2. Дисперсія
        double var = 0;
        for (int i = 0; i < mids.length; i++) var += Math.pow(mids[i] - mean, 2) * ni[i];
        double varPop = var / n;
        double varSample = var * ((double) n / (n - 1)) / n;
        double stdDevPop = Math.sqrt(varPop);

        int maxIdx = 0;
        for (int i = 0; i < ni.length; i++) if (ni[i] > ni[maxIdx]) maxIdx = i;
        int nMo = ni[maxIdx];
        int nPre = (maxIdx > 0) ? ni[maxIdx - 1] : 0;
        int nPost = (maxIdx < ni.length - 1) ? ni[maxIdx + 1] : 0;
        double mode = starts[maxIdx] + h * ((double) (nMo - nPre) / (2 * nMo - nPre - nPost));


        double halfN = n / 2.0;
        int sum = 0, medIdx = 0, sumPre = 0;
        for (int i = 0; i < ni.length; i++) {
            sum += ni[i];
            if (sum >= halfN) {
                medIdx = i;
                break;
            }
            sumPre += ni[i];
        }
        double median = starts[medIdx] + h * ((halfN - sumPre) / ni[medIdx]);

        double sum3 = 0;
        double sum4 = 0;

        for (int i = 0; i < mids.length; i++) {
            double diff = mids[i] - mean;
            sum3 += Math.pow(diff, 3) * ni[i];
            sum4 += Math.pow(diff, 4) * ni[i];
        }

        double mu3 = sum3 / n;
        double mu4 = sum4 / n;

        double skewness = mu3 / Math.pow(stdDevPop, 3);
        double kurtosis = (mu4 / Math.pow(stdDevPop, 4)) - 3;

        StringBuilder sb = new StringBuilder();
        sb.append("ХАРАКТЕРИСТИКИ (Інтервальні):\n");
        sb.append("-----------------------------\n");
        sb.append(String.format("%-25s %s\n", "Середнє:", df.format(mean)));
        sb.append(String.format("%-25s %s\n", "Медіана (набл):", df.format(median)));
        sb.append(String.format("%-25s %s\n", "Мода (набл):", df.format(mode)));
        sb.append(String.format("%-25s %s\n", "Дисперсія (виб):", df.format(varPop)));
        sb.append(String.format("%-25s %s\n", "Дисперсія (випр):", df.format(varSample)));
        sb.append(String.format("%-25s %s\n", "СКВ (виб):", df.format(stdDevPop)));
        sb.append(String.format("%-25s %s\n", "СКВ (випр):", df.format(Math.sqrt(varSample))));
        sb.append(String.format("%-25s %s%%\n", "Коеф. варіації:", df.format(Math.sqrt(varSample) / mean * 100)));
        sb.append("-----------------------------\n");
        sb.append(String.format("%-25s %s\n", "Асиметрія (A*):", df.format(skewness)));
        sb.append(String.format("%-25s %s\n", "Ексцес (E*):", df.format(kurtosis)));

        return sb.toString();
    }

    private static double calculateMedian(double[] data) {
        int n = data.length;
        if (n % 2 == 1) return data[n / 2];
        return (data[n / 2 - 1] + data[n / 2]) / 2.0;
    }

    private static List<Double> findModes(TreeMap<Double, Integer> freq) {
        int max = Collections.max(freq.values());
        List<Double> list = new ArrayList<>();
        freq.forEach((k, v) -> {
            if (v == max) list.add(k);
        });
        return list;
    }
}