package com.zero.demo.conf;

//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * CountJobConf
 * 
 * @author Louisling
 * @since 2018-07-01
 */
@Component
public class CountJobConf {
    /**
     * Date sequence generator for a
     * <a href="http://www.manpagez.com/man/5/crontab/">Crontab pattern</a>,
     * allowing clients to specify a pattern that the sequence matches.
     *
     * <p>The pattern is a list of six single space-separated fields: representing
     * second, minute, hour, day, month, weekday. Month and weekday names can be
     * given as the first three letters of the English names.
     *
     * <p>Example patterns:
     * <ul>
     * <li>"0 0 * * * *" = the top of every hour of every day.</li>
     * <li>"*&#47;10 * * * * *" = every ten seconds.</li>
     * <li>"0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.</li>
     * <li>"0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.</li>
     * <li>"0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.</li>
     * <li>"0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays</li>
     * <li>"0 0 0 25 12 ?" = every Christmas Day at midnight</li>
     * </ul>
     * </p>
     */
    //@Scheduled(cron = "*/2 * * * * ?")
    public void totalDayHitAndDownload() {
        System.out.printf("%1$tF %1$tT %1$tL \n", System.currentTimeMillis());
    }

    /*   
      public static void main(String[] args) {
      //%1$tF: the first time argument, F: 2018-02-26 T: 17:52:03 L: millisecond: 324 A: Monday/Tuesday.. 
      //%1$s the first string argument %2$tT: the second time argument
      System.out.printf("%1$s %2$tT \n", "Now is ", new Date());
      System.out.printf("%1$tF %1$tT %1$tL %1$tA", System.currentTimeMillis());
    }   
    */
}
