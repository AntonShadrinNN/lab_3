import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TimeZone;

public class DataPrinter {
    private String fName, sName, mName;
    private LocalDate birth;
    private final Scanner scanner;

    public DataPrinter(Scanner scanner){
        this.scanner = scanner;
        parse();
    }
    private void parse(){
        String[] data;
        try {
            data = scanner.nextLine().split(" ");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("You didn't write anything!");
        } catch (IllegalStateException e) {
            throw new IllegalStateException("You try to read a closed stream!");
        }
        this.sName = data[0];
        this.fName = data[1];
        this.mName = data[2];
        DateTimeFormatter birthFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            this.birth = LocalDate.parse(data[3], birthFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeException("Wrong date of birth given, expected dd.MM.yyyy");
        }
    }

    private String getPostfix(String age){
        int intAge = Integer.parseInt(age);
        int lastDigit = intAge % 10;
        if (lastDigit == 1 && intAge != 11) {
            return "год";
        } else if ((1 < lastDigit && lastDigit < 5) && (intAge < 10 || intAge >= 20)) {
            return "года";
        } else {
            return "лет";
        }
    }

    public void printData(){
        String initials = String.format("%s %c.%c.", sName, fName.charAt(0), mName.charAt(0));
        String sex = mName.charAt(mName.length() - 1) == 'а' ? "Ж" : "М";
        String age = Long.toString(ChronoUnit.YEARS.between(birth, LocalDate.now(TimeZone.getDefault().toZoneId())));
        String postfix;
        postfix = getPostfix(age);
        System.out.printf("%s %s %s %s", initials, sex, age, postfix);
    }
}
