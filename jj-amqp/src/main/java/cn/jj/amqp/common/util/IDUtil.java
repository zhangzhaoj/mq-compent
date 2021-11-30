package cn.jj.amqp.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * @author by wanghui03
 * @Classname IDUtil
 * @Description UUID生成工具类
 * @Date 2021/11/30 10:44
 */
public class IDUtil {
    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    private static final int STANDARD_BYTE_LENGTH = 8;

    private static final int MAX_RANDOM_NUMBERID = 100;


    /**
     * shortUUID
     * @return
     */
    public static String shortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < STANDARD_BYTE_LENGTH; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * shortNumberId(给排序使用，以及其他不非常严格要求情况下的自增数据生成)
     * @return
     */
    public static long shortNumberId(){
        StringBuilder sbr = new StringBuilder();
        LocalDateTime localDateTime = LocalDateTime.of(2018,1,1,0,0);
        Long standTimeMillis = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        sbr.append(System.currentTimeMillis()-standTimeMillis);
        sbr.append(RandomUtil.nextInt(MAX_RANDOM_NUMBERID));
        return Long.valueOf(sbr.toString()).longValue();
    }

    /**
     * 生成uuid
     * @return
     */
    public static String uuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
