package com.dercg.rabbit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
//        System.out.println(Math.sqrt(5));

        System.out.println(iter(99));
        System.out.println(iter2(new BigDecimal(99), 20));
        System.out.println(method(99, 1e-11));
    }

    /**
     * 可以使用牛顿迭代法
     * 首先随便猜一个近似值x，然后不断令x等于x和a/x的平均数，迭代个六七次后x的值就已经相当精确了
     * (x+a/x)/2
     */
    public static double iter(double x) {

//        BigDecimal num2 = BigDecimal.valueOf(2);
//        int precision = 20;
//        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
//        BigDecimal deviation = v;
//        int cnt = 0;
//        while (cnt < precision) {
//            deviation = (deviation.add(v.divide(deviation, mc))).divide(num2, mc);
//            cnt++;
//        }
//        deviation = deviation.setScale(t, BigDecimal.ROUND_HALF_UP);
//        System.out.println("开根号值: "+deviation+"  ,循环次数: "+cnt);
//        return deviation;
        if (x < 0) {
            System.out.println("");
            return -1;
        }
        if (x == 0)
            return 0;

        double _avg = x;
        double last_avg = Double.MAX_VALUE;
        final double _JINGDU = 1e-6;

        int i = 0;
        while (Math.abs(_avg - last_avg) > _JINGDU) {
            last_avg = _avg;
            _avg = (_avg + x / _avg) / 2;
            i++;
//            System.out.println("( " + _avg + " + " + x+" / "+_avg + " ) > " + _JINGDU +" || "+(Math.abs(_avg - last_avg)));
        }
        System.out.println("开根号值: " + _avg + "  ,循环次数: " + i);
        return _avg;
    }

    /**
     * 可以使用牛顿迭代法
     * 首先随便猜一个近似值x，然后不断令x等于x和a/x的平均数，迭代个六七次后x的值就已经相当精确了
     * (x+a/x)/2
     */
    public static BigDecimal iter2(BigDecimal v, int t) {

        BigDecimal num2 = BigDecimal.valueOf(2);
        int precision = 20;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = v;
        int cnt = 0;
        while (cnt < precision) {
            deviation = (deviation.add(v.divide(deviation, mc))).divide(num2, mc);
            cnt++;
        }
        deviation = deviation.setScale(t, BigDecimal.ROUND_HALF_UP);
        System.out.println("开根号值: " + deviation + "  ,循环次数: " + cnt);
        return deviation;

    }

    public static double method(double target, double m) {
        double min, max, mid, newMid;
        min = 1;
        max = target;
        mid = (min + max) / 2;

        do {
            if (mid * mid < target) {
                min = mid;
            } else {
                max = mid;
            }
            newMid = mid;
            mid = (max + min) / 2;
        } while (Math.abs(mid - newMid) > m);

        return mid;
    }

/*    // 二分查找法
    public static double binarySearch(int v,double t){
        int i = 0;
        double max = v;
        double min = 0;
        double temp = v >> 1;
        while (abs(temp*temp-v) > t){
            if (temp*temp > v ){
                max = temp;
            } else if(temp*temp < v){
                min = temp;
            }
            temp = (min+max) >> 1;
            i++;
        }

        System.out.println("开根号值: "+temp+"  ,循环次数: "+i);
        return temp;
    }*/

//    public static double abs(double a){
//        return (a <= 0.0D) ? 0.0D - a : a ;
//    }
}
