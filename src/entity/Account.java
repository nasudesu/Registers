package entity;
import jakarta.persistence.*;


@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;

    @Convert(converter = BalanceConverter.class)
    private double balance;

    public Account() {
    }

    public Account(double balance) {
        this.balance = balance;
    }

    @Version
    private int version;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // toString method
    @Override
    public String toString() {
        return "Account{" +
                "number = " + number +
                ", balance = " + balance +
                '}';
    }
}
