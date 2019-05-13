import java.util.UUID;

public class UuidTest {

    public static void main(String [] args){
//        System.out.println(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        String str = "CBA21FE7597040DF83EEFF214D49DADB";
//        System.out.println(str.length());
        String str2 = "213 3815449549";
        str2 = str2.substring(4, str2.length()).replace("\r\n", "");
//        Integer.parseInt(str2);
        String abc = String.valueOf(Double.parseDouble(str2));
        System.out.println(abc);
    }

}
