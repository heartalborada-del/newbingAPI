package io.github.heartalborada_del.newBingAPI.utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class utils {
    public static String getNowTime(){
        ZonedDateTime date = ZonedDateTime.now();
        return date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static String randomString(int length){
            String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random random=new Random();
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<length;i++){
                int number=random.nextInt(str.length()-1);
                sb.append(str.charAt(number));
            }
            return sb.toString();
    }
}
