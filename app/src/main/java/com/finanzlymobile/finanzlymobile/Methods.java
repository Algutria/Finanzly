package com.finanzlymobile.finanzlymobile;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class Methods {
    public static int randomImage(ArrayList<Integer> images){
        int selectedImage;
        Random r = new Random();
        selectedImage = r.nextInt(images.size());
        return images.get(selectedImage);
    }

    public static boolean boardPresent(ArrayList<Board> boards, String name){
        for (int i = 0; i < boards.size() ; i++) {
            if(boards.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public static boolean boardPresentE(ArrayList<Board> boards, String nameE, String currentName){
        if (!currentName.equals(nameE)) {
            if (boardPresent(boards, nameE)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public static String numberToCurrency(double value){
        String formattedValue = "$" + NumberFormat.getNumberInstance().format(value);
        return formattedValue;
    }
}
