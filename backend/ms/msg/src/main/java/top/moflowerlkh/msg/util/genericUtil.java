package top.moflowerlkh.msg.util;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class genericUtil {
    // todo need to add sign mechanism
    public static String generateRequestID(String bankAccount, String goods) {
        return "backAccount:" + bankAccount + "-" + "goods:" + goods + "-" + "timeStamp:" + new Date().getTime();
    }

    public static String generateRequestID(long bankAccount, long goods) {
        return generateRequestID(String.valueOf(bankAccount), String.valueOf(goods));
    }

    public static String prefixKey(String prefix, Object key) {
        return prefix + "." + String.valueOf(key);
    }

    public static String removePrefix(String prefix, String origin) {
        return origin.replace(prefix + ".", "");
    }

    public static String md5(List<String> args) {
        String arg = Strings.join(args, '.');
        return DigestUtils.md5DigestAsHex(arg.getBytes(StandardCharsets.UTF_8));
    }
}
