package fileOptions;

import exceptions.QuitCycleException;

import java.util.Scanner;

public class ProtectedScanner {
    Scanner sc;
    private Boolean flag = Boolean.FALSE;

    public ProtectedScanner() {
        this.sc = new Scanner(System.in);
    }

    public String nextLine() throws QuitCycleException {
        try {
            if (sc.hasNextLine()) {
                return sc.nextLine();
            } else if (flag == Boolean.TRUE) {
                // EOF (Ctrl + D)
                throw new QuitCycleException();
            } else {
                System.exit(-11);
            }
        } catch (RuntimeException e) {
            System.out.println("Упс, ошибка");
            System.exit(10);
        } catch (QuitCycleException e) {
            // System.out.println("Ctrl + D");
            throw new QuitCycleException();
        }
        return "";
    }

    public void setTrue() {
        flag = Boolean.TRUE;
    }

    public void setFalse() {
        flag = Boolean.FALSE;
    }
}
