import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;

@SpringBootTest
public class TestCase {

    public static void main(String [] args){

//        Money five = new Dollar(2).times(5);
//        Assert.assertEquals(new Dollar(10), five.times(2));

    }

    @Test
    public void test(){
//        Assert.assertTrue(new Dollar(5).equals(new Dollar(5)));
//        Assert.assertFalse(new Dollar(5).equals(new Dollar(6)));
    }

    @Test
    public void testEquality(){
        Assert.assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    }

    @Test
    public void testMultiplication(){
//        Money five = new Dollar(5);
        //Dollar product = five.times(2);
        //Assert.assertEquals(10, product.getAmount());
        //Assert.assertEquals(new Dollar(10), product);
        //product = five.times(3);
        //Assert.assertEquals(15, product.getAmount());
        //Assert.assertEquals(new Dollar(15), product);
//        Assert.assertEquals(new Dollar(10), five.times(2));
//        Assert.assertEquals(new Dollar(15), five.times(3));
//        Assert.assertTrue(Money.dollar(5).equals(new Dollar(5)));
//        Assert.assertFalse(Money.dollar(5).equals(new Dollar(6)));
//        Assert.assertTrue(Money.franc(5).equals(new Franc(5)));
//        Assert.assertFalse(Money.franc(5).equals(new Franc(6)));
//        Assert.assertFalse(Money.franc(5).equals(Money.dollar(5)));

        Assert.assertTrue(new Money(10, "CHF").equals(new Money(10, "CHF")));
        Assert.assertTrue(new Money(10, "CHF").times(2).equals(new Money(10, "CHF").times(2)));
        Assert.assertNotEquals(Money.dollar(10).currency(), Money.franc(10).currency());

    }

    @Test
    public void testSimpleAddition(){
        Money five = Money.dollar(5);
        Expression sum = five.plus(five);
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum, "USD");
        Assert.assertEquals(Money.dollar(10),reduced);
    }

    @Test
    public void testPlusReturnsSum(){
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum)result;
        Assert.assertEquals(five, sum.augend);
        Assert.assertEquals(five, sum.addend);
    }

    @Test
    public void testReduceSum(){
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        Assert.assertEquals(Money.dollar(7), result);
    }




}

class Money implements Expression{

    Expression plus(Money addend){
        return new Sum(this, addend);
    }

    Money(int amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    protected int amount;

    protected String currency;

    public boolean equals(Object object){
        Money money = (Money)object;
        return amount == money.amount
                && currency().equals(money.currency());
    }

    static Money dollar(int amount){
        return new Money(amount, "USD");
    }

    static Money franc(int amount){
        return new Money(amount, "CHF");
    }

    String currency(){
        return currency;
    }

    Money times(int multiplier){
        return new Money(amount * multiplier, currency);
    };

    public String toString(){
        return amount + " " + currency;
    }

//    Money plus(Money addend){
//        return new Money(amount + addend.amount, currency);
//    }

}

interface Expression{

}

class Bank{
    Money reduce(Expression source, String to){
        Sum sum = (Sum)source;
//        int amount = sum.augend.amount + sum.addend.amount;
//        return new Money(amount, to);
        return sum.reduce(to);
    }
}

class Sum implements Expression{

    Sum(Money augend, Money addend){
        this.augend = augend;
        this.addend = addend;
    }

    Money augend;
    Money addend;

    public Money reduce(String to){
        int amount = augend.amount + addend.amount;
        return new Money(amount, to);
    }
}