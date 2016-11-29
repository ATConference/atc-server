package kr.ac.sogang.creative.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class URLHelper {

    static final String BASE_URL = "http://assets.atcofficial.kr";

    public static String getURL(String type, String id, String idx, String ext) {
        return String.format("%s/%s/%s_%s.%s", BASE_URL, type, id, idx, ext);
    }

    public static String getURL(String type, String id, Integer idx, String ext) {
        return getURL(type, id, String.format("%02d", idx), ext);
    }

    public static List<String> getURL(String type, String id, Integer startIdx, Integer endIdx, String ext) {
        List<String> list = new ArrayList<>();
        IntStream.rangeClosed(startIdx, endIdx).forEach(idx -> list.add(getURL(type, id, idx, ext)));
        return list;
    }
}
