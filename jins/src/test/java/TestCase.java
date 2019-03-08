import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Hashtable;

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

    @Test
    public void testReduceMoney(){
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1), "USD");
        Assert.assertEquals(Money.dollar(1), result);
    }

    @Test
    public void testReduceMoneyDifferentCurrency(){
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2), "USD");
        Assert.assertEquals(Money.dollar(1), result);
    }

    @Test
    public void testArrayEquals(){
        Assert.assertEquals(new Object[] {"abc"}, new Object[]{"abc"});
        System.out.println(Arrays.deepEquals(new Object[] {"abc"}, new Object[]{"abc"}));
    }

    @Test
    public void testIdentityRate(){
        Assert.assertEquals(1, new Bank().rate("USD", "USD"));
    }

    @Test
    public void testMixedAddition(){
        Expression fiveBucks = Money.dollar(5);
        Expression tenFranks = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce((fiveBucks).plus(tenFranks), "USD");
        Assert.assertEquals(Money.dollar(10), result);
    }

    @Test
    public void testSumPlusMoney(){
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        Money result = bank.reduce(sum, "USD");
        Assert.assertEquals(Money.dollar(15), result);
    }

    @Test
    public void testSumTimes(){
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
        Money result = bank.reduce(sum, "USD");
        Assert.assertEquals(Money.dollar(20), result);
    }

    @Test
    public void testPlusSameCurrencyRetrunMoney(){
        Expression sum = Money.dollar(1).plus(Money.dollar(1));
        Assert.assertTrue(sum instanceof Money);
    }

}

class Money implements Expression{

    public Expression plus(Expression addend){
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

    public Expression times(int multiplier){
        return new Money(amount * multiplier, currency);
    }

    public String toString(){
        return amount + " " + currency;
    }

//    Money plus(Money addend){
//        return new Money(amount + addend.amount, currency);
//    }

    public Money reduce(Bank bank, String to){
        int rate = bank.rate(currency, to);
//        int rate = (currency.equals("CHF") && to.equals("USD")) ? 2 : 1;
        return new Money(amount / rate, to);
    }


}

interface Expression{
    Money reduce(Bank bank, String to);
    Expression plus(Expression addend);
    Expression times(int multiplier);
}

class Bank{
    private Hashtable rates = new Hashtable();

    Money reduce(Expression source, String to){
        return source.reduce(this, to);
    }

    int rate(String from, String to){
        if(from.equals(to)) return 1;
        Integer rate = (Integer)rates.get(new Pair(from, to));
        return rate.intValue();
    }

    void addRate(String from, String to, int rate){
        rates.put(new Pair(from, to), new Integer(rate));
    }
}

class Sum implements Expression{

    public Expression plus(Expression addend){
//        return null;
        return new Sum(this, addend);
    }

    Expression augend;
    Expression addend;

    Sum(Expression augend, Expression addend){
        this.augend = augend;
        this.addend = addend;
    }

//    Sum(Money augend, Money addend){
//        this.augend = augend;
//        this.addend = addend;
//    }

//    Money augend;
//    Money addend;

    public Money reduce(Bank bank, String to){
        int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
        return new Money(amount, to);
    }

    public Expression times(int multiplier){
        return new Sum(augend.times(multiplier),
                addend.times(multiplier));
    }
}

class Pair{
    private String from;
    private String to;

    Pair(String from, String to){
        this.from = from;
        this.to = to;
    }

    public boolean equals(Object object){
        Pair pair = (Pair)object;
        return from.equals(pair.from) && to.equals(pair.to);
    }

    public int hashCode(){
        return 0;
    }
}