package hello.core.singleton;

public class StatefulService { //ctrl+shift+t -> 새 테스트 생성

    //싱글톤 컨테이너: 상태가 유지되게 만들면 안된다. (여러 클라이언트가 하나의 객체를 공유하므로)
    //하나의 클라이언트가 특정 필드의 값을 수정할 수 있어서는 안된다!

//    private int price;

    public int order(String name, int price){
        System.out.println("name = "+name+" price = "+price);
//        this.price=price;
        return price;
    }

//    public int getPrice(){
//      return price;
//    }
}
