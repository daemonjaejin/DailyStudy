import domain.Member;
import domain.Member2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.TypeUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalTest {

    public static void main(String [] args) {

        //        test1();

        //        test2();

//        test3();

//        test4(new Member());

//        test5();

//        test6();

        test7();

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

    public static void test6(){

        List<Member> list = new ArrayList<>();
        list.add(new Member(){{
            setAge("10");
            setName("name");
        }});
        String name = list.stream().filter(c -> c.getName().equals("name2")).map(Member::getAge).findFirst().orElse(null);
        if(StringUtils.isEmpty(name)){
            System.out.println("empty");
        }else{
            System.out.println("no empty");
        }
        int seq=0;
        String filename="/path/to/file/filename.txt";
        String strNm = String.format("%s_%s_%s_%s_%s.%s", "metaId", "pocTypNm", "imgTypCd", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(System.currentTimeMillis()), seq==0 ? "01" : seq<10 ? "0"+seq : seq, FilenameUtils.getExtension(filename));
        /**
         * FilenameUtils.getExtension("");
         * 확장자 추출
         * */
        FilenameUtils.getExtension("");
        System.out.println(strNm);

        String preFix=FilenameUtils.getPrefix(filename);
        System.out.println("preFix : " + preFix);

        String path=FilenameUtils.getPath(filename);
        System.out.println(path);

        String fullPath=FilenameUtils.getFullPath(filename);
        System.out.println(fullPath);

        String name1=FilenameUtils.getName(filename);
        System.out.println(name1);

        String baseName=FilenameUtils.getBaseName(filename);
        System.out.println(baseName);

        String extension=FilenameUtils.getExtension(filename);
        System.out.println(extension);

        String targetValue = "a,b,c,d,e";
        String [] result = StringUtils.commaDelimitedListToStringArray(targetValue);

        for (String str : result){
            System.out.println(str);
        }

        String orginPathPlus = StringUtils.applyRelativePath("/a/b", "/c");
        System.out.println(orginPathPlus);

    }

    public static void test7(){


        SomeClass someClass = new SomeClass();
        Field field = ReflectionUtils.findField(SomeClass.class, "aField");
        ReflectionUtils.makeAccessible(field);
        String aField = (String)ReflectionUtils.getField(field, someClass);
        ReflectionUtils.setField(field, someClass, "test");

        // null && empty check
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(null));

        // null && empty && 공백이 아닌 문자가 하나 있는지 체크
        System.out.println(StringUtils.hasText("a"));

        // null && empty && 공백이 아닌 문자 길이가 있는지 체크
        System.out.println(StringUtils.hasLength("a"));

        String word = " a b c ";
        // 공백이 있는지 여부 확인
        System.out.println("공백 : " + StringUtils.containsWhitespace(word));

        // 앞뒤 공백 제거
        String word2 = StringUtils.trimWhitespace(word);
        System.out.println("공백여부2 : " + word2);

        // 모든 공백 제거
        String word3 = StringUtils.trimAllWhitespace(word);
        System.out.println("공백여부3 : " + word3);

        // 선행 공백 제거
        String word4 = StringUtils.trimLeadingWhitespace(word);
        System.out.println("공백여부4 : " + word4);

        // 후행 공백 제거
        String word5 = StringUtils.trimTrailingWhitespace(word);
        System.out.println("공백여부5 : " + word5);

        // 대소문자 구분없이 해당 prefix로 시작하는 여부
        System.out.println(StringUtils.startsWithIgnoreCase(word3, "a"));

        // 대소문자 구분없이 해당 suffix로 끝나는지 여부
        System.out.println(StringUtils.endsWithIgnoreCase(word3, "c"));

        // 해당하는 문자열 모두변경, newPattern이 null인 경우 변경하지 않음
        String word6 = StringUtils.replace(word, "f", "d");
        System.out.println("문자6 : " + word6);

        // 해당하는 문자열 모두 삭제
        String word7 = StringUtils.delete(word, "a");
        System.out.println("문자7 : " + word7);

        // 문자열 앞뒤로 ' 를 분여서 반환
        String word8 = StringUtils.quote(word);
        System.out.println("문자8 : " + word8);

        // 매개변수가 String 객체인 경우 quote 처리하여 반환
        Object obj9 = StringUtils.quoteIfString(word);
        System.out.println("obj9 : " + obj9);

        // 설정된 구분자 기준으로 마지막 문자열 반환
        String word10 = StringUtils.unqualify(word, 'a');
        System.out.println("문자10 : " + word10);

        // '.' 문자열을 기준으로 마지막 문자열 반환
        String word11 = StringUtils.unqualify("abc.def.ghi");
        System.out.println("문자11 : " + word11);

        System.out.println(StringUtils.capitalize(word3));

        System.out.println(StringUtils.uncapitalize(word3.toUpperCase()));

        // 오른쪽에 있는 타입이 왼쪽에 있는 타입의 하위 타입인지 체크
        System.out.println(TypeUtils.isAssignable(SomeClass.class, OtherClass.class)); // true

        // 오른쪽에 있는 타입이 왼쪽에 있는 타입의 하위 타입인지 체크
        System.out.println(TypeUtils.isAssignable(OtherClass.class, SomeClass.class)); // false

        System.out.println(someClass.getaField());

    }



}


class SomeClass{
    private String aField;

    public String getaField() {
        return aField;
    }

    public void setaField(String aField) {
        this.aField = aField;
    }
}

class OtherClass extends SomeClass{

}