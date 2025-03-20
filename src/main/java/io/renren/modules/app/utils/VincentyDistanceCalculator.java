package io.renren.modules.app.utils;


public class VincentyDistanceCalculator {

    private static final double EARTH_RADIUS = 6378137.0; // 地球平均半径，单位：米
    private static final double FLATTENING = 1 / 298.257223563; // 地球扁率
    private static final double FLATTENING_DENOMINATOR = 1 - FLATTENING;

    /**
     * 计算两个经纬度点之间的距离（单位：米）
     *
     * @param lat1 第一个点的纬度（度）
     * @param lon1 第一个点的经度（度）
     * @param lat2 第二个点的纬度（度）
     * @param lon2 第二个点的经度（度）
     * @return 两点之间的距离（米）
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double a = EARTH_RADIUS;
        double b = EARTH_RADIUS * FLATTENING_DENOMINATOR;
        double f = FLATTENING;

        double L = Math.toRadians(lon2 - lon1);
        double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
        double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));

        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);

        double lambda = L;
        double lambdaP;
        double iterLimit = 100;
        double sinLambda;
        double cosLambda;
        double sinSigma;
        double cosSigma;
        double sigma;
        double sinAlpha;
        double cosSqAlpha;
        double cos2SigmaM;
        double C;

        do {
            sinLambda = Math.sin(lambda);
            cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) +
                    (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
            if (sinSigma == 0) {
                return 0.0; // co-incident points
            }
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cosSqAlpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            if (Double.isNaN(cos2SigmaM)) {
                cos2SigmaM = 0; // equatorial line: cosSqAlpha=0 (§6)
            }
            C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * sinAlpha *
                    (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

        if (iterLimit == 0) {
            return Double.NaN; // formula failed to converge
        }

        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) -
                B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) *
                        (-3 + 4 * cos2SigmaM * cos2SigmaM)));

        double distance = b * A * (sigma - deltaSigma);

        return distance;
    }

    /**
     * 判断一个点是否在另一个点的指定半径范围内
     *
     * @param centerLat 中心点的纬度（度）
     * @param centerLon 中心点的经度（度）
     * @param pointLat  要检查的点的纬度（度）
     * @param pointLon  要检查的点的经度（度）
     * @param radius    半径（米）
     * @return 如果点在范围内，返回 true；否则返回 false
     */
    public static boolean isPointInRange(double centerLat, double centerLon, double pointLat, double pointLon, double radius) {
        double distance = calculateDistance(centerLat, centerLon, pointLat, pointLon);
        return distance <= radius;
    }

    public static void main(String[] args) {
        double centerLat = 39.9042; // 北京的纬度
        double centerLon = 116.4074; // 北京的经度
        double pointLat = 39.9142; // 北京附近的一个点的纬度
        double pointLon = 116.4174; // 北京附近的一个点的经度
        double radius = 1000; // 半径，单位：米

        double distance = calculateDistance(centerLat, centerLon, pointLat, pointLon);
        System.out.println("两点之间的距离: " + distance + " 米");

        boolean inRange = isPointInRange(centerLat, centerLon, pointLat, pointLon, radius);
        if (inRange) {
            System.out.println("点在指定半径范围内");
        } else {
            System.out.println("点不在指定半径范围内");
        }
    }
}