import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsageTest {

    public static void main(String [] args){

        Guard guard = new Guard();
        guard.giveAccss();

        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            Field f = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
            unsafe.putInt(guard, unsafe.objectFieldOffset(f), 42);
            guard.giveAccss();
        }catch (Exception e){
            e.getStackTrace();
        }

    }

}

class Guard{
    private int ACCESS_ALLOWED = 1;

    public boolean giveAccss(){
        return 42 == ACCESS_ALLOWED; // 무조건 false를 리턴하는 메소드
    }
}
