package com.r3.providerUtility;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;

public class Test {

    public static void main(String[] args) {
        String[] KeysearchList = "UT LEBONHEUR PEDIATRIC SPECIALISTS OR UT LEBONHEUR PEDIATRIC SPECIALISTS, INC.".toLowerCase().split("\\s");
        String[] headerList = "Le Bonheur Children's Hospital".toLowerCase().split("\\s");

        for(String s1 : KeysearchList){
            for(String s2: headerList){
                if (compareStringsWithGivenPercentage(s1, s2, 80.0)) {
                    System.out.println("matching "+ s1);
                }else{
                    //System.out.println("not matching "+ s1);
                }
            }

        }



    }

    public static boolean compareStringsWithGivenPercentage(String str1, String str2, double requiredMatchPercentage) {
        String normalizedStr1 = str1.replaceAll(" ", "").toLowerCase();
        String normalizedStr2 = str2.replaceAll(" ", "").toLowerCase();

        double similarity = calculateJaroWinklerSimilarity(normalizedStr1, normalizedStr2);
        System.out.println("Similarity: " + similarity + "%");
        return similarity >= requiredMatchPercentage;
    }

    private static double calculateJaroWinklerSimilarity(String str1, String str2) {
        JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();
        return jaroWinklerSimilarity.apply(str1, str2) * 100;
    }
}
