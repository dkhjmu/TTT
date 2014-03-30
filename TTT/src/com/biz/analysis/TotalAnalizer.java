package com.biz.analysis;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.biz.vo.PickerVo;

public class TotalAnalizer {
	
	public static void main(String[] args) {
		int[] target={1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
				31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
		};
//		int[] target={1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
//		};
		
		getGameAll(target);
	}
	
	public static ArrayList<PickerVo> getGameAll(int[] target){
		ArrayList<PickerVo> list=new ArrayList<PickerVo>();
		
		BigDecimal bd=new BigDecimal(0);
		BigDecimal bg1=new BigDecimal(1);
		System.out.println("start");
		for(int i1=0;i1<=target.length-6;i1++){
			PickerVo vo1 = new PickerVo();
			vo1.add(target[i1]);
			String ptn1="";
			ptn1=ptn1+target[i1]+"-";
			for(int i2=i1+1;i2<=target.length-5;i2++){
				PickerVo vo2 = new PickerVo(vo1);
				vo2.add(target[i2]);
				String ptn2=ptn1+target[i2]+"-";
				for(int i3=i2+1;i3<=target.length-4;i3++){
					PickerVo vo3 = new PickerVo(vo2);
					vo3.add(target[i3]);
					String ptn3=ptn2+target[i3]+"-";
					for(int i4=i3+1;i4<=target.length-3;i4++){
						PickerVo vo4 = new PickerVo(vo3);
						vo4.add(target[i4]);
						String ptn4=ptn3+target[i4]+"-";
						for(int i5=i4+1;i5<=target.length-2;i5++){
							PickerVo vo5 = new PickerVo(vo4);
							vo5.add(target[i5]);
							String ptn5=ptn4+target[i5]+"-";
							for(int i6=i5+1;i6<=target.length-1;i6++){
								PickerVo vo6 = new PickerVo(vo5);
								vo6.add(target[i6]);
								list.add(vo6);
								bd=bd.add(bg1);
								System.out.println(bd+"\t"+ptn5+target[i6]);
							}//for 6
							System.gc();;
						}//for 5
					}//for 4
				}//for 3
			}//for 2
		}//for 1
		
		
		System.out.println("end");
		
		return list;
	}

}
