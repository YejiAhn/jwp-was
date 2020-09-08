package utils;

import exception.InvalidSocketRequestException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class IOUtils {

    public static final String NEW_LINE = "\n";

    /**
     * @param BufferedReader는 Request Body를 시작하는 시점이어야
     * @param contentLength는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static String readHeader(BufferedReader bufferedReader) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = bufferedReader.readLine();
        sb.append(line).append(NEW_LINE);
        while (!"".equals(line) && !"\r\n".equals(line)) {
            if (line == null) {
                throw new InvalidSocketRequestException();
            }
            line = bufferedReader.readLine();
            sb.append(line).append(NEW_LINE);
        }
        return sb.toString();
    }

    public static Map<String, String> readParameters(String query) throws UnsupportedEncodingException {
        String[] infos = query.split("&");
        Map<String, String> queryParams = new HashMap<>();
        for (String info : infos) {
            String[] keyAndValue = info.split("=");
            //TODO: Decoding 관련 처리하기
            queryParams.put(keyAndValue[0], URLDecoder.decode(keyAndValue[1], StandardCharsets.UTF_8.toString()));
        }
        return queryParams;
    }
}
