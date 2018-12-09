package com.nacy.baselibrary;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;

public class Utils {
    private static final String TAG = "Utils";

    public static void test(){
        Log.d(TAG, "--------------test: -------------------");
    }


    //冒泡排序
    public static <T extends Comparable<T>> T[] sort(T[] list) {
        T temp;
        for (int i = 0; i < list.length - 1; i++) {//外层循环控制排序趟数
            for (int j = 0; j < list.length - 1 - i; j++) {//内层循环控制每一趟排序多少次
                if (list[j].compareTo(list[j + 1]) > 0) {
                    temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
        return list;
    }

    //折半查找
    public static <T> int binarySearch(T[] x, T key, Comparator<T> comp) {
        int low = 0;
        int high = x.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = comp.compare(x[mid], key);
            if (cmp < 0) {
                low= mid + 1;
            }
            else if (cmp > 0) {
                high= mid - 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }


}
