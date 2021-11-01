package hello.core.singleton;

public class SingletonService {

    /**
     * static으로 SingletonService 객체 생성 -> 어디에서 요청이 들어오든, 항상 getInstance로 접근 => 항상 하나의 instance를 공유하는 것이 된다!
     */
    private static final SingletonService instance=new SingletonService(); //자기자신을 내부에 static으로 가짐 -> 전체에 이거 하나만 존재

    public static SingletonService getInstance(){
        return instance; //객체의 참조를 외부에서 가져갈 수 있는 유일한 방법! (SingletonService 생성은 막혀있다. 아래의 private에 의해!)
    }
    
    private SingletonService(){
        /**
         * 자기 자신을 private으로 선언해두었기때문에 외부에서 SingletonService 객체를 새로 생성하는것을 막을 수 있다. 
         * 외부에서 new SingletonService() => 에러발생
         */
    }
    
    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
