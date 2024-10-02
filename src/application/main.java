package application;
import jakarta.persistence.*;
import entity.*;
import dao.*;
public class main {
    public static void main(String[] args) {

        Dao dao = new Dao();

        Account account = new Account(123.45);
        Account account2 = new Account(678.90);


        dao.insertAccount(account);
        dao.insertAccount(account2);

        System.out.println(dao.getAccounts(1));
        System.out.println(dao.getAccounts(2));

        dao.transfer(1, 2, 50.0);

        System.out.println(dao.getAccounts(1));
        System.out.println(dao.getAccounts(2));

    }
}
