package net.guerlab.spring.cloud.commons.geo;

import java.math.BigDecimal;

public class Geohash {

    public static int BITS[] = {
            16, 8, 4, 2, 1
    };

    public static String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    public static int RIGHT = 0;

    public static int LEFT = 1;

    public static int TOP = 2;

    public static int BOTTOM = 3;

    public static int EVEN = 0;

    public static int ODD = 1;

    public static String[][] NEIGHBORS;

    public static String[][] BORDERS;

    static {
        NEIGHBORS = new String[4][2];
        BORDERS = new String[4][2];

        NEIGHBORS[BOTTOM][EVEN] = "bc01fg45238967deuvhjyznpkmstqrwx";
        NEIGHBORS[TOP][EVEN] = "238967debc01fg45kmstqrwxuvhjyznp";
        NEIGHBORS[LEFT][EVEN] = "p0r21436x8zb9dcf5h7kjnmqesgutwvy";
        NEIGHBORS[RIGHT][EVEN] = "14365h7k9dcfesgujnmqp0r2twvyx8zb";

        BORDERS[BOTTOM][EVEN] = "bcfguvyz";
        BORDERS[TOP][EVEN] = "0145hjnp";
        BORDERS[LEFT][EVEN] = "prxz";
        BORDERS[RIGHT][EVEN] = "028b";

        NEIGHBORS[BOTTOM][ODD] = NEIGHBORS[LEFT][EVEN];
        NEIGHBORS[TOP][ODD] = NEIGHBORS[RIGHT][EVEN];
        NEIGHBORS[LEFT][ODD] = NEIGHBORS[BOTTOM][EVEN];
        NEIGHBORS[RIGHT][ODD] = NEIGHBORS[TOP][EVEN];

        BORDERS[BOTTOM][ODD] = BORDERS[LEFT][EVEN];
        BORDERS[TOP][ODD] = BORDERS[RIGHT][EVEN];
        BORDERS[LEFT][ODD] = BORDERS[BOTTOM][EVEN];
        BORDERS[RIGHT][ODD] = BORDERS[TOP][EVEN];
    }

    private static void refineInterval(
            double[] interval,
            int cd,
            int mask) {
        if ((cd & mask) > 0) {
            interval[0] = (interval[0] + interval[1]) / 2.0;
        } else {
            interval[1] = (interval[0] + interval[1]) / 2.0;
        }
    }

    public static String calculateAdjacent(
            final String hash,
            int dir) {
        String srcHash = hash.toLowerCase();

        char lastChr = srcHash.charAt(srcHash.length() - 1);
        int type = srcHash.length() % 2 == 1 ? ODD : EVEN;

        String base = srcHash.substring(0, srcHash.length() - 1);

        if (BORDERS[dir][type].indexOf(lastChr) != -1) {
            base = calculateAdjacent(base, dir);
        }

        return base + BASE32.charAt(NEIGHBORS[dir][type].indexOf(lastChr));
    }

    /**
     * 获取相邻的geohash
     *
     * @param geohash
     *            geohash
     * @param length
     *            长度
     * @return 相邻的geohash
     */
    public static GeoHashExpand getGeoHashExpand(
            final String geohash,
            int length) {
        int maxLength = Math.min(geohash.length(), length);

        String hash = geohash.substring(0, maxLength);

        String geohashTop = calculateAdjacent(hash, TOP);
        String geohashBottom = calculateAdjacent(hash, BOTTOM);
        String geohashRight = calculateAdjacent(hash, RIGHT);
        String geohashLeft = calculateAdjacent(hash, LEFT);

        String geohashTopLeft = calculateAdjacent(geohashLeft, TOP);
        String geohashTopRight = calculateAdjacent(geohashRight, TOP);
        String geohashBottomRight = calculateAdjacent(geohashRight, BOTTOM);
        String geohashBottomLeft = calculateAdjacent(geohashLeft, BOTTOM);

        GeoHashExpand expand = new GeoHashExpand(hash, geohashTop, geohashBottom, geohashRight, geohashLeft,
                geohashTopLeft, geohashTopRight, geohashBottomRight, geohashBottomLeft);

        return expand;
    }

    /**
     * 解析
     *
     * @param geohash
     *            geohash
     * @return 地理对象
     */
    public static Geo decode(
            String geohash) {
        boolean even = true;
        double[] lat = new double[3];
        double[] lon = new double[3];

        lat[0] = -90.0;
        lat[1] = 90.0;
        lon[0] = -180.0;
        lon[1] = 180.0;

        for (int i = 0; i < geohash.length(); i++) {
            char c = geohash.charAt(i);
            int cd = BASE32.indexOf(c);
            for (int mask : BITS) {
                if (even) {
                    refineInterval(lon, cd, mask);
                } else {
                    refineInterval(lat, cd, mask);
                }
                even = !even;
            }
        }
        lat[2] = (lat[0] + lat[1]) / 2.0;
        lon[2] = (lon[0] + lon[1]) / 2.0;

        return new Geo(new BigDecimal(lat[2]), new BigDecimal(lon[2]));
    }

    /**
     * 编码geohash
     *
     * @param latitude
     *            经度
     * @param longitude
     *            经度
     * @return geohash
     */
    public static String encode(
            BigDecimal latitude,
            BigDecimal longitude) {
        return encode(latitude.doubleValue(), longitude.doubleValue());
    }

    /**
     * 编码geohash
     *
     * @param latitude
     *            经度
     * @param longitude
     *            经度
     * @return geohash
     */
    public static String encode(
            double latitude,
            double longitude) {
        boolean even = true;
        double lat[] = new double[3];
        double lon[] = new double[3];
        int bit = 0;
        int ch = 0;
        int precision = 12;
        String geohash = "";

        lat[0] = -90.0;
        lat[1] = 90.0;
        lon[0] = -180.0;
        lon[1] = 180.0;

        while (geohash.length() < precision) {
            if (even) {
                double mid = (lon[0] + lon[1]) / 2.0;
                if (longitude > mid) {
                    ch |= BITS[bit];
                    lon[0] = mid;
                } else {
                    lon[1] = mid;
                }
            } else {
                double mid = (lat[0] + lat[1]) / 2.0;
                if (latitude > mid) {
                    ch |= BITS[bit];
                    lat[0] = mid;
                } else {
                    lat[1] = mid;
                }
            }
            even = !even;
            if (bit < 4) {
                bit++;
            } else {
                geohash += BASE32.charAt(ch);
                bit = 0;
                ch = 0;
            }
        }
        return geohash;
    }
}
