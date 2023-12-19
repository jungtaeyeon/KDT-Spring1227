package com.example.demo.di;

public class WoodDuck extends Duck {
	WoodDuck(){
		flybeavior = new FlyNoWay();
		quackbehavior = new MuteQuack();
	}
	@Override
	public void display() {
	}

}
