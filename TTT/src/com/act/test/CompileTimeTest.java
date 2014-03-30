package com.act.test;

import com.biz.checher.Checker;
import com.biz.picker.random.RandomPicker;

public class CompileTimeTest {
	public static void main(String[] args) {
		RandomPicker picker=new RandomPicker();
		Checker.simulating(picker);		
	}
	
}
