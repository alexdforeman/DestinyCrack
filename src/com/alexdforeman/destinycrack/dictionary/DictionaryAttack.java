package com.alexdforeman.destinycrack.dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A simple script to try an attack vector.
 * Created by @alexdforeman.
 */
public class DictionaryAttack {

    private static List<String> loadDictionary(final String dictLocation_) throws IOException {

        final List<String> dictionary = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(dictLocation_));
        String line = bf.readLine();
        while(line != null) {
            dictionary.add(line);
            line = bf.readLine();
        }
        return dictionary;
    }

    private static List<String> filterLength(final List<String> originaList_, final int length) {

        final List<String> filteredList = new ArrayList<>();
        for (String s : originaList_) {
            if(s.length() == length) {
                filteredList.add(s);
            }
        }
        return filteredList;
    }

    private static List<String> getTupleCharsOnly(final List<String> originaList_, final int startIndex_, final int endIndex_) {

        final List<String> filteredList = new ArrayList<>();

        final int range = endIndex_ - startIndex_;
        System.out.println ("range = " + range);

        tryAgain: for (String s : originaList_) {
            final char c = s.charAt(startIndex_);
            for (int i = startIndex_; i <= endIndex_; i++) {
                if (s.charAt(i) != c) {
                    continue tryAgain;
                }
            }
            filteredList.add(s);
        }

        return filteredList;
    }


    private static List<String> noDuplicateChars(final List<String> originaList_, final List<Integer> ignore_) {

        final List<String> filteredList = new ArrayList<>();

        for (String s: originaList_) {

            boolean duplicate = false;

            for (int i = 0; i < s.length(); i++) {
                if (!ignore_.contains(i) && s.length() != i) {
                    char [] tmp = s.toCharArray();
                    Arrays.sort(tmp);
                    if (tmp[i] == tmp [i++]) {
                        duplicate = true;
                    }
                } else {
                    i++;
                }
            }
        }

        return filteredList;
    }

    public static void main(String... args) throws IOException{

        // Loads all words .
        List<String> dict = loadDictionary("/usr/share/dict/words");

        // Grab all 14 letter strings.
        dict = filterLength(dict, 14);

        // Grab all Strings with doubles at 7 and 8
        dict = getTupleCharsOnly(dict, 7, 8);
        System.out.println("Number of words 14 Letters long and have a double at 7 and 8: " + dict.size());

        // Tell the duplicates to be ignored at 7 and 8 (these are known)
        final List<Integer> ignoreList = new ArrayList<>();
        ignoreList.add(7);
        ignoreList.add(8);

        // Ensure string has no duplicates.
        dict = noDuplicateChars(dict, ignoreList);
        System.out.println("Number of words 14 Letters long and have a double at 7 and 8 and no other duplicates: " + dict.size());
        for (String s : dict) {
            System.out.println(s);
        }

        // Failed attempt.
    }
}
