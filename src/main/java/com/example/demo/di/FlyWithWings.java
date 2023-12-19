package com.example.demo.di;
//동일 메소드를 호출해도 구현체 클래스에 따라
//어느 클래스엔 날고 어느 클래스엔 날지 못한다 - 다형성(폴리모피즘)
//전제조건 = 인스턴스화를 함에 있어 선업부의 타입과 생성부의 타입이 무조건 다를 때 다형성을 떠올리자
//List list = new Arraylist();
//list = new Vector();
//list = new LinkedList();
public class FlyWithWings implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println("나는 날 수 있어!");
	}

}
