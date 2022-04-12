package banking;

import org.sqlite.*;
import java.sql.*;
import java.util.*;

public class Main {
    public static LinkedList<String> cardNumbers = new LinkedList<>();
    public static LinkedList<String> pinCodes = new LinkedList<>();
    public static LinkedList<Integer> balances = new LinkedList<>();
    public static String data;


    public static void main(String[] args) {


        data = args[1];
        //data = "c:/sqlite/db.db";
        createSql(data);

        Scanner scanner = new Scanner(System.in);
        int menuNumber, menuSubNumber;
        String cardNumber, pinCode;
        printMenu();
        do {
            menuNumber = scanner.nextInt();

            if (menuNumber == 1) {
                creatAccount();
                printMenu();
            }


            if (menuNumber == 2) {

                System.out.println("Enter your card number:");
                cardNumber = scanner.next();
                System.out.println("Enter your PIN:");
                pinCode = scanner.next();

                System.out.println(cardNumber);
                System.out.println(pinCode);

                if (check(cardNumber, pinCode)) {
                    System.out.println("You have successfully logged in!");
                    printSubMenu();
                    do {
                        menuSubNumber = scanner.nextInt();
                        if (menuSubNumber == 1) {
                            System.out.println("Balance: " + balance(cardNumber));
                            printSubMenu();
                        }

                        if (menuSubNumber == 2) {
                            System.out.println("Enter income:");
                            addIncome(cardNumber, scanner.nextInt());
                            System.out.println("Income was added!");
                            printSubMenu();
                        }

                        if (menuSubNumber == 3) {
                            System.out.println("Transfer");
                            System.out.println("Enter card number:");
                            String toCard = scanner.next();
                            if (cardNumber.equals(toCard)) {
                                System.out.println("You can't transfer money to the same account!");
                            } else if (! toCard.equals(luhn(toCard.substring(0,toCard.length()-1)))) {
                                System.out.println("Probably you made a mistake in the card number. Please try again!");
                            } else if (! isCard(toCard)) {
                                System.out.println("Such a card does not exist.");
                            } else {
                                System.out.println("Enter how much money you want to transfer:");
                                int toAmount = scanner.nextInt();
                                if (toAmount > balance(cardNumber)) {
                                    System.out.println("Not enough money!");
                                } else {
                                    addIncome(cardNumber, -toAmount);
                                    addIncome(toCard, toAmount);
                                    System.out.println("Success!");
                                }
                            }
                            //addIncome(cardNumber, scanner.nextInt());
                            //System.out.println("Income was added!");

                            printSubMenu();
                        }



                        if (menuSubNumber == 4) {
                            deleteCard(cardNumber);
                            System.out.println("The account has been closed!");
                            printMenu();
                        }

                    } while ((menuSubNumber != 5) & (menuSubNumber != 0));
                    if (menuSubNumber == 0) {
                        menuNumber =0;
                    } else {
                        System.out.println("You have successfully logged out!");
                        printMenu();
                    }
                } else {
                    System.out.println("Wrong card number or PIN!");
                    printMenu();
                }





            }
        } while (menuNumber != 0);

        System.out.println("Bye!");

    }

    public static void printMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public static void printSubMenu() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    public static void creatAccount() {
        String cardNumber = "400000";
        String pinCode = "";

        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            cardNumber += random.nextInt(10);
        }
        cardNumber = luhn(cardNumber);

        for (int i = 0; i < 4; i++) {
            pinCode += random.nextInt(10);
        }

        cardNumbers.add(cardNumber);
        pinCodes.add(pinCode);
        balances.add(0);

        saveSql(cardNumber, pinCode);

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(pinCode);
    }

    public static boolean check(String cardNumber, String pinCode) {
        /*if (cardNumbers.contains(cardNumber)) {
            if (pinCodes.get(cardNumbers.indexOf(cardNumber)).equals(pinCode)) {
                return true;
            }
        }
        return false;*/
        boolean b = true;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite:" + data;
        dataSource.setUrl(url);
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM card WHERE number = " + cardNumber + " AND pin ='" + pinCode + "';");
            b = resultSet.next();
            conn.close();
        } catch (SQLException e) {

        }
        return b;
    }

    public static int balance(String cardNumber) {
        //return balances.get(cardNumbers.indexOf(cardNumber));
        int balance = 8;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite:" + data;
        dataSource.setUrl(url);
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM card WHERE number = " + cardNumber + ";");
            balance = resultSet.getInt("balance");
            conn.close();
        } catch (SQLException e) {

        }
        return balance;
    }

    public static String luhn(String s) {
        int l = 0;
        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                if (Integer.parseInt(s.substring(i,i + 1)) * 2 < 10) {
                    l += Integer.parseInt(s.substring(i,i + 1)) * 2;
                } else {
                    l += Integer.parseInt(s.substring(i,i + 1)) * 2 - 9;
                }
            } else {
                l += Integer.parseInt(s.substring(i,i + 1));
            }


        }
        if (l % 10 == 0) {
            s += "0";
        } else {
            s += (10 - l % 10);
        }
        return s;
    }

    public static void createSql(String s) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite:" + s;
        dataSource.setUrl(url);
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (id INTEGER, number TEXT, pin TEXT, balance INTEGER DEFAULT 0);");
            conn.close();
        } catch (SQLException e) {

        }
    }

    public static void saveSql(String cardNumber, String pin) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite:" + data;
        dataSource.setUrl(url);
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            //System.out.println("INSERT INTO card VALUES (8, '" + cardNumber + "', '" + pin + "', 0);");
            statement.executeUpdate("INSERT INTO card VALUES (8, '" + cardNumber + "', '" + pin + "', 0);");
            conn.close();
        } catch (SQLException e) {

        }
    }

    public static void addIncome(String cardNumber, int income) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite:" + data;
        dataSource.setUrl(url);
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            //System.out.println("UPDATE card SET balance = balance + " + String.valueOf(income) + " WHERE number = '" + cardNumber + "' ;");
            statement.executeUpdate("UPDATE card SET balance = balance + " + income + " WHERE number = " + cardNumber + ";");
            //System.out.println("UPDATE card SET balance = balance + " + income + ", WHERE number = '" + cardNumber + "' ;");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void deleteCard(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite:" + data;
        dataSource.setUrl(url);
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM card WHERE number = '" + cardNumber + "' ;");
            conn.close();
        } catch (SQLException e) {

        }
    }

    public static boolean isCard(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite:" + data;
        dataSource.setUrl(url);
        boolean b = true;
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM card WHERE number = " + cardNumber + ";");
            b = resultSet.next();
            conn.close();
        } catch (SQLException e) {

        }
        return b;
    }

    public static void changeBalance(String cardNumber, int amount) {

    }

}