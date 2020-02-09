package chapter1.part2;

//SmartDate
public class Exercise11 {
    private final int day;
    private final int month;
    private final int year;
    private int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Exercise11(int d, int m, int y) {
        if (m < 1 || m > 12 || y <= 0) {
            throw new RuntimeException("Invalid date");
        }
        if (isLeapYear(y)) {
            daysInMonth[1] = 29;
        }
        if (d < 1 || d > daysInMonth[m - 1]) {
            throw new RuntimeException("Invalid date");
        }
        day = d;
        month = m;
        year = y;
    }

    public int day() {
        return day;
    }

    public int month() {
        return month;
    }

    public int year() {
        return year;
    }

    public String toString() {
        return day + "/" + month + "/" + year;
    }

    // Exercise 12
    public String dayOfWeek() {
        //01/01/2001 to 31/12/2100. Assume the date we have is between these dates
        //01/01/2001 is Monday
        int totalDays = 0;
        for (int y = 2001; y < year; y++) {
            if (isLeapYear(y)) {
                totalDays += 366;
            } else {
                totalDays += 365;
            }
        }
        for (int m = 1; m < month; m++) {
            if (m == 2 && isLeapYear(year)) {
                daysInMonth[m - 1] = 29;
            }
            totalDays += daysInMonth[m - 1];
        }
        totalDays += day - 1;
        int dayOfWeek = totalDays % 7;
        return convertToWeekDay(dayOfWeek);
    }

    private boolean isLeapYear(int y) {
        if (y % 100 == 0) {
            if (y % 400 == 0) {
                return true;
            }
        } else if (y % 4 == 0) {
            return true;
        }
        return false;
    }

    private String convertToWeekDay(int i) {
        switch (i) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        int d = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        Exercise11 smartDate = new Exercise11(d, m, y);
        System.out.println(smartDate);
        System.out.println(smartDate.dayOfWeek());
    }
}
