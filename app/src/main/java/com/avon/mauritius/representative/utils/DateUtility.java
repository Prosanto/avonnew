package com.avon.mauritius.representative.utils;
import com.avon.mauritius.representative.myapplication.Myapplication;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtility {
    public static final int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * Parse string date to formatted date object
     *
     * @param dateString
     * @return parseDate - Date object or null
     */
    public static String formatDate(String dateString, String fromFormat, String toFormat) {
        try {
            Date date = new SimpleDateFormat(fromFormat).parse(dateString);
            SimpleDateFormat sdf = new SimpleDateFormat(toFormat);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return parseDate - Time object or null
     */
    public static String formatTime(String timeString, String fromFormat, String toFormat) {
        try {
            SimpleDateFormat date12Format = new SimpleDateFormat(fromFormat);
            SimpleDateFormat date24Format = new SimpleDateFormat(toFormat);
            return date24Format.format(date12Format.parse(timeString));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Format simple date formatted object to string
     *
     * @param date
     * @param dateFormat
     * @return formatDate - Formatted date string
     */
    public static String formatSDF(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatDate = sdf.format(date).trim();
        return formatDate;
    }

    /**
     * Format date object to string
     *
     * @param date
     * @param dateFormat
     * @return formatDate - formatted date string
     */
    public static String formatDate(Date date, String dateFormat) {
        Format formatter = new SimpleDateFormat(dateFormat);
        String formatDate = formatter.format(date).trim();
        return formatDate;
    }

    public static String dateToTime(Date date) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Tools.DD_MM_A);
        String dateToTime = DATE_FORMAT.format(date);
        return dateToTime;
    }


    public static long dateToMillisecond(String duration) {
        SimpleDateFormat sdf;
        Date date = null;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(duration);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentMillisec = date.getTime();
        return currentMillisec;

    }

    public static String millisecondToDate(Long duration) {
        // milliseconds
        long miliSec = duration;
        // Creating date format
        DateFormat simple = new SimpleDateFormat("dd MMMM yyyy");

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(miliSec);
        String finalResult = simple.format(result);

        return "" + finalResult;

    }


    public static String dateFromDayDifference(String currentDate, long oneday) {
        SimpleDateFormat sdf;
        Date date = null;
        String finalDateString;
        sdf = new SimpleDateFormat(Tools.YYYY_MM_DD_HH_MM_SS);
        try {
            date = sdf.parse(currentDate);
            long currentMillisec = date.getTime();
            Date resultExpectDate = new Date((currentMillisec + oneday));
            String finalDate = sdf.format(resultExpectDate).toString().trim();
            date = sdf.parse(finalDate);
            finalDateString = formatDate(date, Tools.YYYY_MM_DD_HH_MM_SS);
            return finalDateString;
        } catch (Exception e) {
            return "NULL";
        }

    }

    public static String getCurrentTime() {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getCurrentTimeHHmm() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getTodaysDate() {
        DateFormat df = new SimpleDateFormat(Tools.EEE_D_MMM_YYYY_HH_MM);
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getDayofWeek() {
        DateFormat df = new SimpleDateFormat(Tools.MMMM_yyyy);
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getDayofWeek(String input) {
        //2019-06-14
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputformat = new SimpleDateFormat(Tools.MMMM_yyyy);
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;

    }

    public static String getCurrentTimeForsend() {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date mDate = new Date();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        sdf.setTimeZone(tz);
        String date = sdf.format(mDate);
        Logger.debugLog("date", date);
//        Date currentDate = parseDate(date, dateFormat);


//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        s;
//        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getDateFortop(String input) {
        //2019-06-14
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputformat = new SimpleDateFormat(Tools.EEE_D_MMM_YYYY_HH_MM);
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;

    }

    public static String getCurrentday() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String timeconver24to12(String date) {
//        String dateCovner =date;
//        SimpleDateFormat hh_mm_ss = new SimpleDateFormat("HH:mm:ss");
//        SimpleDateFormat h_mm_a   = new SimpleDateFormat("hh:mm a");
//        try {
//            Date d1 = hh_mm_ss.parse(date);
//            dateCovner=h_mm_a.format(d1);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return date;

    }


    public static String converTdate(String input) {
        return input;
//        DateFormat df = new SimpleDateFormat("HH:mm");
//        DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
//        Date date = null;
//        String output = null;
//        try{
//            date= df.parse(input);
//            output = outputformat.format(date);
//            System.out.println(output);
//        }catch(ParseException pe){
//            pe.printStackTrace();
//        }
//        return output;
    }

    public static String getCurrentdayFor() {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getDateFormateforList(String input) {
        if (input == null || input.equalsIgnoreCase(""))
            return "";
        else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputformat = new SimpleDateFormat("dd MMM yyyy");
            Date date = null;
            String output = "";
            try {
                date = df.parse(input);
                output = outputformat.format(date);
                System.out.println(output);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            return output;
        }

    }

    public static String getFormattedDate(String input) {

        //2021-05-30
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            if (PersistentUser.getLanguage(Myapplication.getContext()).equalsIgnoreCase("en"))
                return new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(date);
            else if (PersistentUser.getLanguage(Myapplication.getContext()).equalsIgnoreCase("fr")) {
                return new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(date);
            } else {
                return new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(date);
            }

//            date = df.parse(input);
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            int day = cal.get(Calendar.DATE);
//            if (!((day > 10) && (day < 19)))
//                switch (day % 10) {
//                    case 1:
//                        return new SimpleDateFormat("d'st' 'of' MMMM yyyy").format(date);
//                    case 2:
//                        return new SimpleDateFormat("d'nd' 'of' MMMM yyyy").format(date);
//                    case 3:
//                        return new SimpleDateFormat("d'rd' 'of' MMMM yyyy").format(date);
//                    default:
//                        return new SimpleDateFormat("d'th' 'of' MMMM yyyy").format(date);
//                }
//            return new SimpleDateFormat("d'th' 'of' MMM yyyy").format(date);

        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return "";
    }

    public static String getFormattedDate2(String input) {

        //2021-05-30
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            if (PersistentUser.getLanguage(Myapplication.getContext()).equalsIgnoreCase("en"))
                return new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(date);
            else if (PersistentUser.getLanguage(Myapplication.getContext()).equalsIgnoreCase("fr")) {
                return new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(date);
            } else {
                return new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(date);
            }


//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            int day = cal.get(Calendar.DATE);
//            if (!((day > 10) && (day < 19)))
//                switch (day % 10) {
//                    case 1:
//                        return new SimpleDateFormat("d'st' MMMM yyyy").format(date);
//                    case 2:
//                        return new SimpleDateFormat("d'nd'  MMMM yyyy").format(date);
//                    case 3:
//                        return new SimpleDateFormat("d'rd' MMMM yyyy").format(date);
//                    default:
//                        return new SimpleDateFormat("d'th' MMMM yyyy").format(date);
//                }
//            return new SimpleDateFormat("d'th' MMM yyyy").format(date);

        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return "";
    }

    public static String getFormattedDate3(String input) {

        //2021-05-30
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            if (PersistentUser.getLanguage(Myapplication.getContext()).equalsIgnoreCase("en"))
                return new SimpleDateFormat("hh:mm:a", Locale.ENGLISH).format(date);
            else if (PersistentUser.getLanguage(Myapplication.getContext()).equalsIgnoreCase("fr")) {
                return new SimpleDateFormat("hh:mm:a", Locale.FRENCH).format(date);
            } else {
                return new SimpleDateFormat("hh:mm:a", Locale.ENGLISH).format(date);
            }


//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            int day = cal.get(Calendar.DATE);
//            if (!((day > 10) && (day < 19)))
//                switch (day % 10) {
//                    case 1:
//                        return new SimpleDateFormat("d'st' MMMM yyyy").format(date);
//                    case 2:
//                        return new SimpleDateFormat("d'nd'  MMMM yyyy").format(date);
//                    case 3:
//                        return new SimpleDateFormat("d'rd' MMMM yyyy").format(date);
//                    default:
//                        return new SimpleDateFormat("d'th' MMMM yyyy").format(date);
//                }
//            return new SimpleDateFormat("d'th' MMM yyyy").format(date);

        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return "";
    }


    public static String getDateFormateforList2(String input) {
        //2021-03-08 07:09:50
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat outputformat = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    public static String getCurrentConvert(String input) {
        //2021-01-20 20:00:00
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat outputformat = new SimpleDateFormat("EEE,dd");
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    public static String getCurrentConvertTime(String input) {
        //2021-01-20 20:00:00
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat outputformat = new SimpleDateFormat("hh:mm");
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    public static String getCurrentConvertEEEdd(String input) {
        //2021-01-20 20:00:00
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat outputformat = new SimpleDateFormat(Tools.EEE_DD_MMMM);
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    public static String getCurrentConvertFromMMDDYYY(String input) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        String output = null;
        try {
            date = df.parse(input);
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    public static String getAge(String dateString) {
        String[] parts = dateString.split("/");
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        int mYear = Integer.parseInt(part3);
        int mMonth = Integer.parseInt(part2);
        int mDay = Integer.parseInt(part1);
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(mYear, mMonth, mDay);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    public static String millisecondToMonth(long input) {
        DateFormat df = new SimpleDateFormat("MMM");
        //formatted value of current Date
        System.out.println("Milliseconds to Date: " + df.format(input));
        //Converting milliseconds to Date using Calendar
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(input);
        System.out.println("Milliseconds to Date using Calendar:"
                + df.format(cal.getTime()));
        String output = df.format(cal.getTime());

        return output;
    }


    public static String getTomorrowDate() {
        Calendar cal = Calendar.getInstance(); //current date and time
        cal.add(Calendar.DAY_OF_MONTH, 1); //add a day
        cal.set(Calendar.HOUR_OF_DAY, 23); //set hour to last hour
        cal.set(Calendar.MINUTE, 59); //set minutes to last minute
        cal.set(Calendar.SECOND, 59); //set seconds to last second
        cal.set(Calendar.MILLISECOND, 999); //set milliseconds to last millisecond
        long input = cal.getTimeInMillis();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //formatted value of current Date
        System.out.println("Milliseconds to Date: " + df.format(input));
        //Converting milliseconds to Date using Calendar
        cal.setTimeInMillis(input);
        System.out.println("Milliseconds to Date using Calendar:"
                + df.format(cal.getTime()));
        String tomorrowDate = df.format(cal.getTime());

        String dateFrom = DateUtility.getCurrentConvertEEEdd(tomorrowDate);
        String timeFrom = DateUtility.getCurrentConvertTime(tomorrowDate);
        String output = dateFrom + " | " + timeFrom;

        return output.toUpperCase();
    }

    public static double getNextMonth(long input) {
        double finalOutput = 60 * 60 * 24 * 30 * 2;
        DateFormat df = new SimpleDateFormat("MMM");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(input);
        String output = df.format(cal.getTime());
        if (output.equalsIgnoreCase("Feb")) {
            finalOutput = 60 * 60 * 24 * 28 * 2;
        } else if (output.equalsIgnoreCase("Mar")) {
            finalOutput = 60 * 60 * 24 * 31 * 2;
        }
        return finalOutput;
    }

    public static double getMonth(long input) {
        double finalOutput = 60 * 60 * 24 * 30 * 2;
        DateFormat df = new SimpleDateFormat("MMM");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(input);
        String output = df.format(cal.getTime());
        if (output.equalsIgnoreCase("Feb")) {
            finalOutput = 60 * 60 * 24 * 28;
        } else if (output.equalsIgnoreCase("Mar")) {
            finalOutput = 60 * 60 * 24 * 31;
        } else if (output.equalsIgnoreCase("Apr")) {
            finalOutput = 60 * 60 * 24 * 30;
        } else if (output.equalsIgnoreCase("May")) {
            finalOutput = 60 * 60 * 24 * 31;
        } else if (output.equalsIgnoreCase("Jun")) {
            finalOutput = 60 * 60 * 24 * 30;
        } else if (output.equalsIgnoreCase("Jul")) {
            finalOutput = 60 * 60 * 24 * 31;
        } else if (output.equalsIgnoreCase("Aug")) {
            finalOutput = 60 * 60 * 24 * 31;
        } else if (output.equalsIgnoreCase("Sep")) {
            finalOutput = 60 * 60 * 24 * 30;
        } else if (output.equalsIgnoreCase("Oct")) {
            finalOutput = 60 * 60 * 24 * 31;
        } else if (output.equalsIgnoreCase("Nov")) {
            finalOutput = 60 * 60 * 24 * 30;
        } else if (output.equalsIgnoreCase("Dec")) {
            finalOutput = 60 * 60 * 24 * 31;
        }
        return finalOutput;
    }
}
