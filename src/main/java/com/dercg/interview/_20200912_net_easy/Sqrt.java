package com.dercg.interview._20200912_net_easy;

/**
 * 开平方根函数，精确到小数点后10位
 * <p>
 * 常用方法两种：1、二分查找法   2、牛顿迭代法
 */
public class Sqrt {
    public static void main(String[] args) {

        System.out.println(iter(4, 1e-10));
    }

    // 1、二分查找法
    // target：开方数
    // m: 指定精度 0.0001，或 1e-4
    public static double binarySqrt(double target, double m) {
        double min, max, mid, newMid;
        min = 1;
        max = target;
        mid = (min + max) / 2;

        do {
            double item = mid * mid;
            if (item < target) {
                min = mid;
            } else {
                max = mid;
            }
            newMid = mid;
            mid = (max + min) / 2;
        } while (Math.abs(mid - newMid) > m);

        return mid;
    }

    // 2、牛顿迭代法
    // 首先随便猜一个近似值x，然后不断令x等于x和a/x的平均数，迭代个六七次后x的值就已经相当精确了
    // 牛顿迭代法函数 (x+a/x)/2
    // target：开方数
    // m: 指定精度 0.0001，或 1e-4
    public static double iter(double target, double m) {
        if (target < 0) {
            System.out.println("");
            return -1;
        }
        if (target == 0) return 0;

        double _avg = target;
        double last_avg = Double.MAX_VALUE;

        while (Math.abs(_avg - last_avg) > m) {
            last_avg = _avg;
            _avg = (_avg + target / _avg) / 2;
        }
        return _avg;

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
    }
}
