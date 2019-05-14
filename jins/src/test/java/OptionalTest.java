import domain.Member;
import domain.Member2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalTest {

    public static void main(String [] args) {

        //        test1();

        //        test2();

//        test3();

//        test4(new Member());

        test5();

    }

    public static void test1(){
        List<String> test = null;

        if(test != null && !test.isEmpty()){
            for (String s : test){
                System.out.println(s);
            }
        }

        test = new ArrayList<>();
        test.add("test");

        if(!test.isEmpty()){
            for (String s : test){
                System.out.println(s);
            }
        }

        //        List<String> abc = ((List)((ArrayList)test).clone());
        //        List<String> abc = new ArrayList<>(test.size());
        List<String> abc = new ArrayList<>(test);
        //        abc.add("test2");
        Collections.copy(abc, test);
        List<String> bcd = Optional.ofNullable(test)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());

        if(!abc.isEmpty()){
            for (String s : abc){
                System.out.println(s);
            }
        }

        if(!bcd.isEmpty()){
            for (String s : abc){
                System.out.println(s);
            }
        }
    }

    public static void test2(){
        Optional<Member> myMember = Optional.empty();
        Member member = null;
        //        Member member = new Member();
        //        Optional<Member> maybeMember = Optional.of(member); // null이 아닌걸로 확신할 때

        Optional<Member> maybeMember = Optional.ofNullable(member); // null일지도 모를 때
        Optional<Member> maybeNotMember = Optional.ofNullable(null); // null일지도 모를 때

        Optional<Member2> member2 = Optional.empty();

        //        Optional.ofNullable(member).get();
        //        Optional.ofNullable(member).orElseGet(null);

        String str = "clp";
        if("CLP".equalsIgnoreCase(str)){
            System.out.println("CLP");
        }
        if("CLP".equals(str)){
            System.out.println("clp");
        }

        StringTokenizer stringTokenizer = new StringTokenizer("http://localhost.com", ".");
        while(stringTokenizer.hasMoreTokens()){
            System.out.println(stringTokenizer.nextToken().trim());
        }

    }

    public static void test3(){

        List<Member> list = new ArrayList<>();
        list.add(new Member(){{
            setAge("10");
            setName("name");
        }});
        String name = list.stream().filter(c -> c.getName().equals("name2")).map(Member::getAge).findFirst().orElse(null);
        System.out.println(name);

        String name2 = null;
        System.out.println(name2);

        if("null".equals(name)){
            System.out.println("name : " + name);
        }

        if(!Optional.ofNullable(name).isPresent()){
            System.out.println("name is null");
        }

    }

    public static void test4(Member member){
        Optional<Member> myMember = Optional.ofNullable(member);
        if(myMember.isPresent()){
            Optional<Member> myMember2 = Optional.ofNullable(myMember.get());
            if(myMember2.isPresent()){
//                Optional<Member2> myMember3 = Optional.ofNullable(myMember2);
            }
        }
    }

    public static void test5(){

        int a = 3; // 00000011
        int b = 7; // 00000111
        System.out.println(a & b); // 3
        System.out.println(a | b); // 7
        System.out.println(a ^ b); // 4
        System.out.println(~ a); // -4 -> 음수로 바꾸고 -1을 더한다.
        System.out.println(~ b); // -8 -> 음수로 바꾸고 -1을 더한다.
        System.out.println(a << 2); // 12 -> 왼쪽으로 2칸 이동 0으로 채워짐
        System.out.println(b >> 2); // 1 -> 오른쪽으로 2칸 이동 0에 도착하면 0만 나옴

    }



}
