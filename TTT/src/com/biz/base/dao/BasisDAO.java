package com.biz.base.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.biz.dao.AbstractBizDAO;
import com.biz.dao.status.BnuCountStatusBySEQDAO;
import com.biz.vo.IntVo;
import com.biz.vo.SeqResultVO;
import com.co.util.sql.dml.CreateTable;
import com.co.util.sql.dml.DropTable;
import com.co.util.sql.dml.InsertData;

public class BasisDAO extends AbstractBizDAO {
	
	private static String tableName="basis_data";
	private static String id[]  ={"seq",              "bnu",              "norder"};
	private static String type[]={"NUMBER  NOT NULL", "NUMBER  NOT NULL", "NUMBER  NOT NULL"};
	private static String pk[]  ={"seq", "bnu"};

	public int dropTable(){
		return this.excuteUpdateSQL(DropTable.getSQL(tableName));
	}

	public int createBasisTable() {
		return this.excuteUpdateSQL(CreateTable.getSQLWithPK(tableName, id, type, pk));
	}
	
	public int[] executeAllBatch(){
		return this.executeBatch();
	}

	public void insertBasisDataBatch(int seq, int no1, int no2, int no3, int no4, int no5, int no6, int bonus){
		logger.info(seq+" start");
		int nums[]={no1, no2, no3, no4, no5, no6, bonus};
		for(int i=0;i<nums.length;i++){
			insertBatchBasisData(seq, nums[i], i+1);
		}
	}

	private void insertBatchBasisData(int seq, int bnu, int odr){
		String[] value={seq+"", bnu+"", odr+""};
		this.addBatch(InsertData.getSQL(tableName, id, value));
	}
	
	public int getMaxSeq(){
		sb=new StringBuffer();
		sb.append("select max(seq) mseq from basis_data");
		
		String result=this.executeSingleRowFirstColumSelectSQL(sb.toString());
		IntVo vo =new IntVo(result);
		return vo.getInt();
	}
	
	public SeqResultVO getResultVo(int seq){
		sb=new StringBuffer();
		sb.append("select " + seq + " seq"+
				",(select bnu from basis_data where seq = " + seq + " and norder = 1) no1"+
				",(select bnu from basis_data where seq = " + seq + " and norder = 2) no2"+
				",(select bnu from basis_data where seq = " + seq + " and norder = 3) no3"+
				",(select bnu from basis_data where seq = " + seq + " and norder = 4) no4"+
				",(select bnu from basis_data where seq = " + seq + " and norder = 5) no5"+
				",(select bnu from basis_data where seq = " + seq + " and norder = 6) no6"+
				",(select bnu from basis_data where seq = " + seq + " and norder = 7) bonus"+
				" from dual ");
		
		ArrayList<HashMap<String, String>> result = this.excuteSelectSQL(sb.toString());
		if(result.size()<=0){
			return null;
		}
		HashMap<String, String> rmap = result.get(0);
		SeqResultVO vo =new SeqResultVO(rmap);
		return vo;
	}
	
	public List<SeqResultVO> getAllResultVo(){
		int maxSeq = getMaxSeq();
		List<SeqResultVO> resultList = new ArrayList<SeqResultVO>();
		
		for(int i=1;i<=maxSeq;i++){
			resultList.add(getResultVo(i));
		}
		
		return resultList;
	}
	
	
	public static void main(String[] args) {
		BasisDAO dao=new BasisDAO();
		
		dao.dropTable();
		dao.createBasisTable();
				
		dao.insertBasisDataBatch(1,37,23,10,33,29,40,16);
		dao.insertBasisDataBatch(2,42,21,9,25,32,13,2);
		dao.insertBasisDataBatch(3,31,21,27,19,11,16,30);
		dao.insertBasisDataBatch(4,40,30,14,42,31,27,2);
		dao.insertBasisDataBatch(5,16,42,29,40,24,41,3);
		dao.insertBasisDataBatch(6,14,15,26,27,40,42,34);
		dao.insertBasisDataBatch(7,16,9,40,25,2,26,42);
		dao.insertBasisDataBatch(8,25,8,39,19,37,34,9);
		dao.insertBasisDataBatch(9,17,4,16,39,36,2,14);
		dao.insertBasisDataBatch(10,30,33,25,41,9,44,6);
		dao.insertBasisDataBatch(11,41,36,37,7,1,42,14);
		dao.insertBasisDataBatch(12,25,39,21,2,45,11,44);
		dao.insertBasisDataBatch(13,37,23,22,42,25,38,26);
		dao.insertBasisDataBatch(14,6,12,40,31,33,2,15);
		dao.insertBasisDataBatch(15,37,30,3,31,16,4,13);
		dao.insertBasisDataBatch(16,37,38,40,7,24,6,33);
		dao.insertBasisDataBatch(17,17,32,37,3,4,9,1);
		dao.insertBasisDataBatch(18,19,3,12,13,35,32,29);
		dao.insertBasisDataBatch(19,30,39,38,40,43,6,26);
		dao.insertBasisDataBatch(20,23,14,10,20,18,30,41);
		dao.insertBasisDataBatch(21,17,31,6,32,12,18,21);
		dao.insertBasisDataBatch(22,17,5,39,8,4,6,25);
		dao.insertBasisDataBatch(23,13,18,5,17,33,42,44);
		dao.insertBasisDataBatch(24,43,8,36,7,27,29,6);
		dao.insertBasisDataBatch(25,26,44,21,43,2,4,16);
		dao.insertBasisDataBatch(26,4,20,5,18,25,7,31);
		dao.insertBasisDataBatch(27,43,28,1,37,20,26,27);
		dao.insertBasisDataBatch(28,23,18,37,35,25,9,1);
		dao.insertBasisDataBatch(29,1,39,34,5,13,40,11);
		dao.insertBasisDataBatch(30,17,36,44,35,8,20,4);
		dao.insertBasisDataBatch(31,18,7,23,35,28,9,32);
		dao.insertBasisDataBatch(32,19,6,14,44,34,25,11);
		dao.insertBasisDataBatch(33,41,40,32,4,33,7,9);
		dao.insertBasisDataBatch(34,9,35,42,40,26,37,2);
		dao.insertBasisDataBatch(35,26,37,43,11,3,2,39);
		dao.insertBasisDataBatch(36,40,1,23,26,10,28,31);
		dao.insertBasisDataBatch(37,30,33,35,7,27,37,42);
		dao.insertBasisDataBatch(38,22,17,37,30,43,16,36);
		dao.insertBasisDataBatch(39,43,15,21,13,7,6,8);
		dao.insertBasisDataBatch(40,19,7,26,18,13,25,6);
		dao.insertBasisDataBatch(41,13,20,38,23,43,35,34);
		dao.insertBasisDataBatch(42,21,23,17,32,18,19,1);
		dao.insertBasisDataBatch(43,31,6,39,38,44,35,1);
		dao.insertBasisDataBatch(44,21,11,3,30,38,45,39);
		dao.insertBasisDataBatch(45,27,20,10,1,33,35,17);
		dao.insertBasisDataBatch(46,13,31,8,38,15,23,39);
		dao.insertBasisDataBatch(47,14,45,26,36,17,31,27);
		dao.insertBasisDataBatch(48,26,37,10,6,18,38,3);
		dao.insertBasisDataBatch(49,16,33,4,19,40,7,30);
		dao.insertBasisDataBatch(50,2,22,15,12,10,44,1);
		dao.insertBasisDataBatch(51,44,2,26,16,3,11,35);
		dao.insertBasisDataBatch(52,15,4,20,16,2,29,1);
		dao.insertBasisDataBatch(53,14,39,7,32,8,33,42);
		dao.insertBasisDataBatch(54,39,27,8,36,21,1,37);
		dao.insertBasisDataBatch(55,37,31,44,40,17,21,7);
		dao.insertBasisDataBatch(56,31,30,14,33,10,37,19);
		dao.insertBasisDataBatch(57,29,7,16,25,44,10,6);
		dao.insertBasisDataBatch(58,25,44,24,10,40,33,1);
		dao.insertBasisDataBatch(59,41,39,36,45,6,29,13);
		dao.insertBasisDataBatch(60,25,8,39,42,36,2,11);
		dao.insertBasisDataBatch(61,38,19,15,30,43,14,8);
		dao.insertBasisDataBatch(62,29,8,15,27,3,35,21);
		dao.insertBasisDataBatch(63,20,36,23,3,38,40,5);
		dao.insertBasisDataBatch(64,15,26,21,36,18,14,39);
		dao.insertBasisDataBatch(65,40,4,43,36,33,25,39);
		dao.insertBasisDataBatch(66,22,2,17,7,3,24,45);
		dao.insertBasisDataBatch(67,7,38,10,3,36,15,33);
		dao.insertBasisDataBatch(68,15,39,12,26,10,16,38);
		dao.insertBasisDataBatch(69,15,19,8,5,14,39,35);
		dao.insertBasisDataBatch(70,28,43,5,22,25,19,26);
		dao.insertBasisDataBatch(71,5,9,41,29,12,16,21);
		dao.insertBasisDataBatch(72,26,2,4,27,11,17,1);
		dao.insertBasisDataBatch(73,43,12,32,3,18,40,38);
		dao.insertBasisDataBatch(74,15,6,40,35,18,17,23);
		dao.insertBasisDataBatch(75,24,44,32,34,2,5,28);
		dao.insertBasisDataBatch(76,37,25,1,15,22,3,43);
		dao.insertBasisDataBatch(77,43,32,44,2,29,18,37);
		dao.insertBasisDataBatch(78,35,29,13,33,10,25,38);
		dao.insertBasisDataBatch(79,12,24,27,32,30,3,14);
		dao.insertBasisDataBatch(80,25,17,24,30,26,18,1);
		dao.insertBasisDataBatch(81,11,5,33,20,7,13,6);
		dao.insertBasisDataBatch(82,3,2,1,14,27,42,39);
		dao.insertBasisDataBatch(83,15,10,19,6,34,17,14);
		dao.insertBasisDataBatch(84,27,34,23,42,45,16,11);
		dao.insertBasisDataBatch(85,36,8,13,31,23,6,21);
		dao.insertBasisDataBatch(86,39,37,2,41,12,45,33);
		dao.insertBasisDataBatch(87,16,23,34,43,12,4,26);
		dao.insertBasisDataBatch(88,41,30,17,24,1,20,27);
		dao.insertBasisDataBatch(89,29,28,40,33,26,4,37);
		dao.insertBasisDataBatch(90,38,44,35,29,20,17,10);
		dao.insertBasisDataBatch(91,26,42,1,21,24,29,27);
		dao.insertBasisDataBatch(92,3,33,35,36,14,24,17);
		dao.insertBasisDataBatch(93,22,24,38,44,6,36,19);
		dao.insertBasisDataBatch(94,41,45,5,32,40,34,6);
		dao.insertBasisDataBatch(95,17,43,34,27,31,8,14);
		dao.insertBasisDataBatch(96,3,31,8,1,21,22,20);
		dao.insertBasisDataBatch(97,20,36,6,14,7,15,3);
		dao.insertBasisDataBatch(98,24,23,32,6,9,16,43);
		dao.insertBasisDataBatch(99,27,29,3,37,10,1,11);
		dao.insertBasisDataBatch(100,1,42,11,23,37,7,6);
		dao.insertBasisDataBatch(101,32,45,35,1,3,17,8);
		dao.insertBasisDataBatch(102,35,22,17,24,40,26,42);
		dao.insertBasisDataBatch(103,30,27,14,5,45,15,10);
		dao.insertBasisDataBatch(104,17,42,44,34,33,32,35);
		dao.insertBasisDataBatch(105,45,34,41,10,20,8,28);
		dao.insertBasisDataBatch(106,4,12,10,24,33,22,29);
		dao.insertBasisDataBatch(107,1,31,9,5,4,6,17);
		dao.insertBasisDataBatch(108,23,18,7,29,22,44,12);
		dao.insertBasisDataBatch(109,5,44,1,42,36,34,33);
		dao.insertBasisDataBatch(110,29,7,23,20,43,22,1);
		dao.insertBasisDataBatch(111,40,33,7,31,36,18,27);
		dao.insertBasisDataBatch(112,30,41,33,29,42,26,43);
		dao.insertBasisDataBatch(113,4,33,36,28,9,45,26);
		dao.insertBasisDataBatch(114,11,19,26,41,14,28,2);
		dao.insertBasisDataBatch(115,25,9,28,1,2,6,31);
		dao.insertBasisDataBatch(116,31,34,25,37,4,2,17);
		dao.insertBasisDataBatch(117,36,44,22,5,34,10,35);
		dao.insertBasisDataBatch(118,17,4,22,3,10,19,38);
		dao.insertBasisDataBatch(119,14,3,17,11,21,13,38);
		dao.insertBasisDataBatch(120,6,10,11,4,37,32,30);
		dao.insertBasisDataBatch(121,38,28,12,30,34,43,9);
		dao.insertBasisDataBatch(122,11,1,16,36,40,17,8);
		dao.insertBasisDataBatch(123,18,7,45,17,30,28,27);
		dao.insertBasisDataBatch(124,16,29,4,23,42,25,1);
		dao.insertBasisDataBatch(125,32,2,35,33,36,8,18);
		dao.insertBasisDataBatch(126,20,22,40,43,7,27,1);
		dao.insertBasisDataBatch(127,43,5,29,32,3,10,35);
		dao.insertBasisDataBatch(128,36,30,45,12,37,34,39);
		dao.insertBasisDataBatch(129,23,42,19,28,38,25,17);
		dao.insertBasisDataBatch(130,42,7,27,45,19,24,31);
		dao.insertBasisDataBatch(131,21,11,14,15,10,8,37);
		dao.insertBasisDataBatch(132,23,3,41,34,17,45,43);
		dao.insertBasisDataBatch(133,15,7,26,4,23,18,13);
		dao.insertBasisDataBatch(134,3,35,31,23,12,20,43);
		dao.insertBasisDataBatch(135,6,14,22,28,35,39,16);
		dao.insertBasisDataBatch(136,2,41,16,36,40,42,11);
		dao.insertBasisDataBatch(137,25,9,20,39,36,7,15);
		dao.insertBasisDataBatch(138,10,37,28,11,39,27,19);
		dao.insertBasisDataBatch(139,43,9,20,15,11,28,13);
		dao.insertBasisDataBatch(140,28,19,18,3,13,17,8);
		dao.insertBasisDataBatch(141,12,8,42,43,29,31,2);
		dao.insertBasisDataBatch(142,12,16,44,34,30,40,19);
		dao.insertBasisDataBatch(143,27,42,43,28,26,45,8);
		dao.insertBasisDataBatch(144,4,15,17,26,36,37,43);
		dao.insertBasisDataBatch(145,13,3,2,44,27,20,9);
		dao.insertBasisDataBatch(146,42,27,35,19,41,2,25);
		dao.insertBasisDataBatch(147,4,13,42,6,21,40,36);
		dao.insertBasisDataBatch(148,33,34,36,21,35,25,17);
		dao.insertBasisDataBatch(149,42,41,11,21,34,2,27);
		dao.insertBasisDataBatch(150,39,28,25,37,2,18,16);
		dao.insertBasisDataBatch(151,10,13,19,18,2,1,15);
		dao.insertBasisDataBatch(152,26,29,34,1,5,13,43);
		dao.insertBasisDataBatch(153,12,13,8,36,3,11,33);
		dao.insertBasisDataBatch(154,19,21,35,40,45,6,20);
		dao.insertBasisDataBatch(155,16,20,41,33,32,19,4);
		dao.insertBasisDataBatch(156,45,18,30,42,28,5,2);
		dao.insertBasisDataBatch(157,30,33,26,19,39,35,37);
		dao.insertBasisDataBatch(158,18,4,34,9,21,13,7);
		dao.insertBasisDataBatch(159,18,1,41,30,42,43,32);
		dao.insertBasisDataBatch(160,3,41,34,8,7,39,1);
		dao.insertBasisDataBatch(161,40,22,34,45,36,42,44);
		dao.insertBasisDataBatch(162,38,25,41,21,1,5,24);
		dao.insertBasisDataBatch(163,29,7,11,26,28,44,16);
		dao.insertBasisDataBatch(164,6,39,11,10,41,9,27);
		dao.insertBasisDataBatch(165,22,5,18,42,13,19,31);
		dao.insertBasisDataBatch(166,39,9,12,45,27,36,14);
		dao.insertBasisDataBatch(167,24,27,36,39,30,28,4);
		dao.insertBasisDataBatch(168,43,3,40,31,10,42,30);
		dao.insertBasisDataBatch(169,43,27,16,45,35,37,19);
		dao.insertBasisDataBatch(170,42,13,31,2,15,11,10);
		dao.insertBasisDataBatch(171,29,4,34,16,25,35,1);
		dao.insertBasisDataBatch(172,4,21,24,26,19,41,35);
		dao.insertBasisDataBatch(173,33,9,30,24,3,34,18);
		dao.insertBasisDataBatch(174,14,13,39,35,22,18,16);
		dao.insertBasisDataBatch(175,19,26,28,31,33,36,17);
		dao.insertBasisDataBatch(176,30,34,33,32,17,4,15);
		dao.insertBasisDataBatch(177,1,10,13,16,37,43,6);
		dao.insertBasisDataBatch(178,1,5,11,12,18,23,9);
		dao.insertBasisDataBatch(179,43,9,5,39,35,17,32);
		dao.insertBasisDataBatch(180,20,34,29,15,21,2,22);
		dao.insertBasisDataBatch(181,14,21,40,23,32,45,44);
		dao.insertBasisDataBatch(182,15,13,29,27,40,34,35);
		dao.insertBasisDataBatch(183,34,24,18,42,2,40,5);
		dao.insertBasisDataBatch(184,1,33,6,2,20,16,41);
		dao.insertBasisDataBatch(185,2,8,1,4,38,19,14);
		dao.insertBasisDataBatch(186,14,4,10,45,19,21,9);
		dao.insertBasisDataBatch(187,18,29,1,2,8,38,42);
		dao.insertBasisDataBatch(188,19,24,27,31,34,30,36);
		dao.insertBasisDataBatch(189,32,45,14,37,8,35,28);
		dao.insertBasisDataBatch(190,14,31,44,30,18,8,15);
		dao.insertBasisDataBatch(191,32,6,24,37,5,25,8);
		dao.insertBasisDataBatch(192,37,8,45,4,18,11,33);
		dao.insertBasisDataBatch(193,36,26,18,14,39,6,13);
		dao.insertBasisDataBatch(194,26,44,39,15,20,23,28);
		dao.insertBasisDataBatch(195,35,10,19,40,7,22,31);
		dao.insertBasisDataBatch(196,37,45,41,36,44,35,30);
		dao.insertBasisDataBatch(197,34,16,42,7,45,12,4);
		dao.insertBasisDataBatch(198,25,45,12,19,20,41,2);
		dao.insertBasisDataBatch(199,25,30,36,22,21,14,43);
		dao.insertBasisDataBatch(200,6,5,20,14,13,17,7);
		dao.insertBasisDataBatch(201,3,38,39,24,11,44,26);
		dao.insertBasisDataBatch(202,39,33,14,12,44,27,17);
		dao.insertBasisDataBatch(203,32,3,11,30,1,24,7);
		dao.insertBasisDataBatch(204,3,40,14,35,12,45,5);
		dao.insertBasisDataBatch(205,29,21,37,3,1,35,30);
		dao.insertBasisDataBatch(206,25,2,3,20,1,15,43);
		dao.insertBasisDataBatch(207,14,31,32,11,3,37,38);
		dao.insertBasisDataBatch(208,34,31,25,44,14,40,24);
		dao.insertBasisDataBatch(209,18,24,33,20,2,7,37);
		dao.insertBasisDataBatch(210,22,37,23,10,19,25,39);
		dao.insertBasisDataBatch(211,17,41,13,20,12,33,8);
		dao.insertBasisDataBatch(212,21,38,31,18,11,12,8);
		dao.insertBasisDataBatch(213,20,3,24,2,5,4,42);
		dao.insertBasisDataBatch(214,25,28,37,7,5,20,32);
		dao.insertBasisDataBatch(215,7,2,15,3,44,43,4);
		dao.insertBasisDataBatch(216,7,36,16,40,33,17,1);
		dao.insertBasisDataBatch(217,39,27,16,35,33,20,38);
		dao.insertBasisDataBatch(218,29,14,1,18,8,44,20);
		dao.insertBasisDataBatch(219,35,26,4,37,11,20,16);
		dao.insertBasisDataBatch(220,11,21,43,19,5,34,31);
		dao.insertBasisDataBatch(221,20,2,35,40,37,33,10);
		dao.insertBasisDataBatch(222,7,39,43,5,29,28,44);
		dao.insertBasisDataBatch(223,18,1,27,20,26,3,38);
		dao.insertBasisDataBatch(224,4,42,19,30,27,26,7);
		dao.insertBasisDataBatch(225,5,19,31,11,13,36,7);
		dao.insertBasisDataBatch(226,21,2,8,14,6,22,34);
		dao.insertBasisDataBatch(227,4,22,42,16,5,15,2);
		dao.insertBasisDataBatch(228,17,36,25,35,44,39,23);
		dao.insertBasisDataBatch(229,5,4,9,11,23,38,35);
		dao.insertBasisDataBatch(230,32,33,11,14,5,29,12);
		dao.insertBasisDataBatch(231,31,10,44,45,5,19,27);
		dao.insertBasisDataBatch(232,9,8,10,12,24,44,35);
		dao.insertBasisDataBatch(233,28,4,6,13,40,17,39);
		dao.insertBasisDataBatch(234,24,21,13,26,37,22,4);
		dao.insertBasisDataBatch(235,27,22,21,37,26,31,8);
		dao.insertBasisDataBatch(236,37,39,13,4,1,8,7);
		dao.insertBasisDataBatch(237,1,44,17,24,11,21,33);
		dao.insertBasisDataBatch(238,4,31,2,28,34,15,35);
		dao.insertBasisDataBatch(239,41,44,15,39,24,11,7);
		dao.insertBasisDataBatch(240,6,10,41,16,43,40,21);
		dao.insertBasisDataBatch(241,2,35,28,24,16,27,21);
		dao.insertBasisDataBatch(242,19,4,34,21,32,20,43);
		dao.insertBasisDataBatch(243,2,28,42,12,19,17,34);
		dao.insertBasisDataBatch(244,38,16,25,36,13,37,19);
		dao.insertBasisDataBatch(245,32,9,27,38,31,11,22);
		dao.insertBasisDataBatch(246,26,23,21,18,13,39,15);
		dao.insertBasisDataBatch(247,28,36,40,15,39,12,13);
		dao.insertBasisDataBatch(248,38,45,17,8,3,23,13);
		dao.insertBasisDataBatch(249,27,8,44,41,31,3,11);
		dao.insertBasisDataBatch(250,23,37,43,30,19,45,38);
		dao.insertBasisDataBatch(251,38,25,7,19,6,28,45);
		dao.insertBasisDataBatch(252,23,31,45,26,14,39,28);
		dao.insertBasisDataBatch(253,34,19,8,25,31,36,33);
		dao.insertBasisDataBatch(254,24,1,20,5,30,19,27);
		dao.insertBasisDataBatch(255,5,27,6,1,42,24,32);
		dao.insertBasisDataBatch(256,14,11,43,4,23,21,32);
		dao.insertBasisDataBatch(257,6,31,32,37,27,13,4);
		dao.insertBasisDataBatch(258,40,31,27,14,38,30,17);
		dao.insertBasisDataBatch(259,14,4,45,35,42,5,34);
		dao.insertBasisDataBatch(260,7,24,40,12,15,37,43);
		dao.insertBasisDataBatch(261,16,18,43,11,6,31,2);
		dao.insertBasisDataBatch(262,31,12,25,24,9,29,36);
		dao.insertBasisDataBatch(263,1,32,40,37,28,27,18);
		dao.insertBasisDataBatch(264,16,36,9,41,44,27,5);
		dao.insertBasisDataBatch(265,5,38,39,37,9,34,12);
		dao.insertBasisDataBatch(266,42,3,11,22,4,9,37);
		dao.insertBasisDataBatch(267,41,36,7,34,24,8,1);
		dao.insertBasisDataBatch(268,24,32,19,45,10,3,12);
		dao.insertBasisDataBatch(269,43,5,20,36,18,42,32);
		dao.insertBasisDataBatch(270,12,5,21,9,26,20,27);
		dao.insertBasisDataBatch(271,40,27,9,8,29,3,36);
		dao.insertBasisDataBatch(272,12,9,7,43,39,27,28);
		dao.insertBasisDataBatch(273,24,8,44,1,31,34,6);
		dao.insertBasisDataBatch(274,35,14,15,26,39,13,25);
		
		dao.insertBasisDataBatch(275,40,14,35,20,19,38,26);
		dao.insertBasisDataBatch(276,39,33,4,41,15,21,25);
		dao.insertBasisDataBatch(277,12,10,15,29,25,13,20);
		dao.insertBasisDataBatch(278,37,41,11,43,39,3,13);
		dao.insertBasisDataBatch(279,38,7,31,16,37,36,11);
		dao.insertBasisDataBatch(280,37,24,23,11,10,36,35);
		dao.insertBasisDataBatch(281,3,1,6,4,41,14,12);
		dao.insertBasisDataBatch(282,5,31,18,32,2,10,30);
		dao.insertBasisDataBatch(283,31,18,6,38,8,45,42);
		dao.insertBasisDataBatch(284,7,15,2,30,24,45,28);
		dao.insertBasisDataBatch(285,41,33,40,37,13,45,2);
		dao.insertBasisDataBatch(286,19,44,42,1,15,40,17);
		dao.insertBasisDataBatch(287,24,12,27,37,6,35,41);
		dao.insertBasisDataBatch(288,35,28,1,17,41,12,10);
		dao.insertBasisDataBatch(289,3,33,37,14,38,42,10);
		dao.insertBasisDataBatch(290,39,32,18,8,45,13,7);
		dao.insertBasisDataBatch(291,20,7,42,18,3,8,45);
		dao.insertBasisDataBatch(292,34,31,17,18,32,33,10);
		dao.insertBasisDataBatch(293,33,29,21,9,17,1,24);
		dao.insertBasisDataBatch(294,10,6,17,37,38,30,40);
		dao.insertBasisDataBatch(295,38,18,4,1,12,16,8);
		dao.insertBasisDataBatch(296,15,30,3,27,45,8,44);
		dao.insertBasisDataBatch(297,19,11,6,20,32,28,34);
		dao.insertBasisDataBatch(298,5,27,29,9,37,40,19);
		dao.insertBasisDataBatch(299,25,45,1,20,36,3,24);
		
		dao.insertBasisDataBatch(300,9,7,26,38,10,12,39);
		dao.insertBasisDataBatch(301,13,11,33,37,43,7,26);
		dao.insertBasisDataBatch(302,19,13,20,38,32,42,4);
		dao.insertBasisDataBatch(303,17,45,38,2,30,14,43);
		dao.insertBasisDataBatch(304,26,4,41,10,16,33,38);
		dao.insertBasisDataBatch(305,18,39,23,8,21,7,9);
		dao.insertBasisDataBatch(306,4,23,41,34,30,18,19);
		dao.insertBasisDataBatch(307,25,15,23,21,45,5,12);
		dao.insertBasisDataBatch(308,17,45,37,15,19,14,40);
		dao.insertBasisDataBatch(309,2,5,11,36,1,18,22);
		dao.insertBasisDataBatch(310,19,1,5,34,28,41,16);
		dao.insertBasisDataBatch(311,27,12,24,32,4,28,10);
		dao.insertBasisDataBatch(312,2,6,5,20,12,3,25);
		dao.insertBasisDataBatch(313,35,34,45,17,9,43,2);
		dao.insertBasisDataBatch(314,15,19,41,38,17,34,2);
		dao.insertBasisDataBatch(315,1,43,33,45,13,35,23);
		dao.insertBasisDataBatch(316,11,31,21,27,10,39,43);
		dao.insertBasisDataBatch(317,11,36,22,10,3,39,8);
		dao.insertBasisDataBatch(318,19,20,34,17,2,45,21);
		dao.insertBasisDataBatch(319,42,5,8,28,22,33,37);
		dao.insertBasisDataBatch(320,16,23,45,25,19,41,3);
		dao.insertBasisDataBatch(321,34,20,18,12,21,25,42);
		dao.insertBasisDataBatch(322,38,32,29,18,9,43,20);
		dao.insertBasisDataBatch(323,32,15,14,36,42,10,3);
		dao.insertBasisDataBatch(324,25,21,33,4,36,2,17);
		dao.insertBasisDataBatch(325,45,20,44,7,32,17,33);
		dao.insertBasisDataBatch(326,33,39,25,16,23,36,40);
		dao.insertBasisDataBatch(327,12,44,13,17,32,6,24);
		dao.insertBasisDataBatch(328,1,28,9,16,17,6,24);
		dao.insertBasisDataBatch(329,9,17,19,30,35,42,4);
		dao.insertBasisDataBatch(330,3,16,19,17,4,20,23);
		dao.insertBasisDataBatch(331,4,44,26,31,14,9,39);
		dao.insertBasisDataBatch(332,45,17,36,16,34,42,3);
		dao.insertBasisDataBatch(333,43,27,39,30,5,14,35);
		dao.insertBasisDataBatch(334,21,39,29,15,13,43,33);
		dao.insertBasisDataBatch(335,9,16,5,26,23,45,21);
		dao.insertBasisDataBatch(336,5,20,34,44,3,35,16);
		dao.insertBasisDataBatch(337,37,18,1,14,32,5,4);
		dao.insertBasisDataBatch(338,13,42,2,45,34,38,16);
		dao.insertBasisDataBatch(339,21,8,6,14,30,37,45);
		dao.insertBasisDataBatch(340,26,18,38,24,34,29,32);
		dao.insertBasisDataBatch(341,39,1,19,43,8,34,41);
		dao.insertBasisDataBatch(342,33,14,13,43,34,1,25);
		dao.insertBasisDataBatch(343,31,1,17,10,29,43,15);
		dao.insertBasisDataBatch(344,15,28,1,34,2,45,38);
		dao.insertBasisDataBatch(345,23,29,42,15,20,39,2);
		dao.insertBasisDataBatch(346,14,22,13,5,44,45,33);
		dao.insertBasisDataBatch(347,32,27,8,3,42,13,10);
		dao.insertBasisDataBatch(348,14,31,24,17,3,20,34);
		dao.insertBasisDataBatch(349,5,14,13,25,20,24,36);
		dao.insertBasisDataBatch(350,33,29,24,8,18,1,35);
		dao.insertBasisDataBatch(351,27,29,25,36,5,34,33);
		dao.insertBasisDataBatch(352,17,20,41,16,26,5,24);
		dao.insertBasisDataBatch(353,36,16,11,29,19,22,26);
		dao.insertBasisDataBatch(354,45,44,36,19,43,14,1);
		dao.insertBasisDataBatch(355,35,29,5,30,44,8,38);
		dao.insertBasisDataBatch(356,8,29,45,14,2,25,24);
		dao.insertBasisDataBatch(357,37,18,10,14,21,36,5);
		dao.insertBasisDataBatch(358,21,1,9,12,40,10,37);
		dao.insertBasisDataBatch(359,20,1,40,10,24,19,23);
		dao.insertBasisDataBatch(360,25,35,40,16,4,23,27);
		dao.insertBasisDataBatch(361,24,10,27,16,5,35,33);
		dao.insertBasisDataBatch(362,30,2,22,27,3,40,29);
		dao.insertBasisDataBatch(363,21,11,14,32,12,38,6);
		dao.insertBasisDataBatch(364,2,16,7,5,14,40,4);
		dao.insertBasisDataBatch(365,30,5,26,15,21,25,31);
		dao.insertBasisDataBatch(366,19,27,44,12,26,5,38);
		dao.insertBasisDataBatch(367,25,44,22,29,32,3,19);
		dao.insertBasisDataBatch(368,24,11,21,39,45,30,26);
		dao.insertBasisDataBatch(369,41,35,20,43,17,36,21);
		dao.insertBasisDataBatch(370,45,16,18,24,42,44,17);
		dao.insertBasisDataBatch(371,7,9,15,27,26,42,18);
		dao.insertBasisDataBatch(372,6,18,16,14,11,21,13);
		dao.insertBasisDataBatch(373,45,37,43,26,42,15,9);
		dao.insertBasisDataBatch(374,11,17,25,34,13,15,26);
		dao.insertBasisDataBatch(375,27,19,4,45,8,25,7);
		dao.insertBasisDataBatch(376,40,28,24,11,13,1,7);
		dao.insertBasisDataBatch(377,22,6,43,29,45,37,23);
		dao.insertBasisDataBatch(378,29,22,39,34,5,31,43);
		dao.insertBasisDataBatch(379,35,22,10,31,6,40,19);
		dao.insertBasisDataBatch(380,37,1,17,2,8,26,27);
		dao.insertBasisDataBatch(381,5,1,12,10,20,16,11);
		dao.insertBasisDataBatch(382,15,22,10,42,24,27,19);
		dao.insertBasisDataBatch(383,15,4,37,40,33,28,25);
		dao.insertBasisDataBatch(384,32,38,36,11,24,22,7);
		dao.insertBasisDataBatch(385,7,19,29,12,21,32,9);
		dao.insertBasisDataBatch(386,7,10,31,4,40,19,26);
		dao.insertBasisDataBatch(387,40,43,26,31,34,1,20);
		dao.insertBasisDataBatch(388,29,1,8,17,32,9,45);
		dao.insertBasisDataBatch(389,26,18,7,16,20,23,3);
		dao.insertBasisDataBatch(390,16,28,17,40,37,39,15);
		dao.insertBasisDataBatch(391,10,11,18,22,28,39,30);
		dao.insertBasisDataBatch(392,8,1,42,3,24,7,43);
		dao.insertBasisDataBatch(393,43,40,28,16,41,9,21);
		dao.insertBasisDataBatch(394,20,13,22,28,25,1,15);
		dao.insertBasisDataBatch(395,35,15,20,11,31,26,7);
		dao.insertBasisDataBatch(396,18,34,45,31,20,40,30);
		dao.insertBasisDataBatch(397,25,33,22,17,13,12,8);
		dao.insertBasisDataBatch(398,44,15,20,10,23,42,7);
		dao.insertBasisDataBatch(399,9,2,42,19,1,17,20);
		dao.insertBasisDataBatch(400,34,21,9,43,27,41,2);
		
//		dao.insertBasisDataBatch(401,18,43,31,12,6,38,9);
//		dao.insertBasisDataBatch(402,36,9,15,22,19,5,32);
//		dao.insertBasisDataBatch(403,28,24,10,14,22,37,26);
//		dao.insertBasisDataBatch(404,40,33,20,21,24,5,36);
//		
//		//출현순서대로 정리할것!
//		dao.insertBasisDataBatch(405,1,2,10,25,26,44,4);
//		dao.insertBasisDataBatch(406,7,12,21,24,27,36,45);
//		dao.insertBasisDataBatch(407,6,7,13,16,24,25,1);
//		dao.insertBasisDataBatch(408,9,20,21,22,30,37,16);
//		dao.insertBasisDataBatch(409,6,9,21,31,32,40,38);
//		dao.insertBasisDataBatch(410,1,3,18,32,40,41,16);
//		dao.insertBasisDataBatch(411,11,14,22,35,37,39,5);
//		dao.insertBasisDataBatch(412,4,7,39,41,42,45,40);
//		dao.insertBasisDataBatch(413,2,9,15,23,34,40,3);
//		dao.insertBasisDataBatch(414,2,14,15,22,23,44,43);
//		dao.insertBasisDataBatch(415,7,17,20,26,30,40,24);
//		dao.insertBasisDataBatch(416,5,6,8,11,22,26,44);
//		dao.insertBasisDataBatch(417,4,5,14,20,22,43,44);
//		dao.insertBasisDataBatch(418,11,13,15,26,28,34,31);
//		dao.insertBasisDataBatch(419,2,11,13,14,28,30,7);
//		dao.insertBasisDataBatch(420,4,9,10,29,31,34,27);
//		dao.insertBasisDataBatch(421,6,11,26,27,28,44,30);
//		dao.insertBasisDataBatch(422,8,15,19,21,34,44,12);
//		dao.insertBasisDataBatch(423,1,17,27,28,29,40,5);
//		dao.insertBasisDataBatch(424,10,11,26,31,34,44,30);
//		dao.insertBasisDataBatch(425,8,10,14,27,33,38,3);
//		dao.insertBasisDataBatch(426,4,17,18,27,39,43,19);
//		dao.insertBasisDataBatch(427,6,7,15,24,28,30,21);
//		dao.insertBasisDataBatch(428,12,16,19,22,37,40,8);
//		dao.insertBasisDataBatch(429,3,23,28,34,39,42,16);
//		dao.insertBasisDataBatch(430,1,3,16,18,30,34,44);
//		dao.insertBasisDataBatch(431,18,22,25,31,38,45,6);
//		dao.insertBasisDataBatch(432,2,3,5,11,27,39,33);
//		dao.insertBasisDataBatch(433,19,23,29,33,35,43,27);
//		dao.insertBasisDataBatch(434,3,13,20,24,33,37,35);
//		dao.insertBasisDataBatch(435,8,16,26,30,38,45,42);
//		dao.insertBasisDataBatch(436,9,14,20,22,33,34,28);
//		dao.insertBasisDataBatch(437,11,16,29,38,41,44,21);
//		dao.insertBasisDataBatch(438,6,12,20,26,29,38,45);
//		dao.insertBasisDataBatch(439,17,20,30,31,37,40,25);
//		dao.insertBasisDataBatch(440,10,22,28,34,36,44,2);
//		dao.insertBasisDataBatch(441,1,23,28,30,34,35,9);
//		dao.insertBasisDataBatch(442,25,27,29,36,38,40,41);
//		dao.insertBasisDataBatch(443,4,6,10,19,20,44,14);
//		dao.insertBasisDataBatch(444,11,13,23,35,43,45,17);
//		dao.insertBasisDataBatch(445,13,20,21,30,39,45,32);
//		dao.insertBasisDataBatch(446,1,11,12,14,26,35,6);
//		dao.insertBasisDataBatch(447,2,7,8,9,17,33,34);
//		dao.insertBasisDataBatch(448,3,7,13,27,40,41,36);
//		dao.insertBasisDataBatch(449,3,10,20,26,35,43,36);
//		dao.insertBasisDataBatch(450,6,14,19,21,23,31,13);
//		dao.insertBasisDataBatch(451,12,15,20,24,30,38,29);
//		dao.insertBasisDataBatch(452,8,10,18,30,32,34,27);
//		dao.insertBasisDataBatch(453,12,24,33,38,40,42,30);
//		dao.insertBasisDataBatch(454,13,25,27,34,38,41,10);
//		dao.insertBasisDataBatch(455,4,19,20,26,30,35,24);
//		dao.insertBasisDataBatch(456,1,7,12,18,23,27,44);
//		dao.insertBasisDataBatch(457,8,10,18,23,27,40,33);
//		dao.insertBasisDataBatch(458,4,9,10,32,36,40,18);
//		dao.insertBasisDataBatch(459,4,6,10,14,25,40,12);
//		dao.insertBasisDataBatch(460,8,11,28,30,43,45,41);
//		dao.insertBasisDataBatch(461,11,18,26,31,37,40,43);
//		dao.insertBasisDataBatch(462,3,20,24,32,37,45,4);
//		dao.insertBasisDataBatch(463,23,29,31,33,34,44,40);
//		dao.insertBasisDataBatch(464,6,12,15,34,42,44,4);
//		dao.insertBasisDataBatch(465,1,8,11,13,22,38,31);
//		dao.insertBasisDataBatch(466,4,10,13,23,32,44,20);
//		dao.insertBasisDataBatch(467,2,12,14,17,24,40,39);
//		dao.insertBasisDataBatch(468,8,13,15,28,37,43,17);
//		dao.insertBasisDataBatch(469,4,21,22,34,37,38,33);
//		dao.insertBasisDataBatch(470,10,16,20,39,41,42,27);
//		dao.insertBasisDataBatch(471,6,13,29,37,39,41,43);
//		dao.insertBasisDataBatch(472,16,25,26,31,36,43,44);
//		dao.insertBasisDataBatch(473,8,13,20,22,23,36,34);
//		dao.insertBasisDataBatch(474,4,13,18,31,33,45,43);
//		dao.insertBasisDataBatch(475,1,9,14,16,21,29,3);
//		dao.insertBasisDataBatch(476,9,12,13,15,37,38,27);
//		dao.insertBasisDataBatch(477,14,25,29,32,33,45,37);
//		dao.insertBasisDataBatch(478,18,29,30,37,39,43,8);
//		dao.insertBasisDataBatch(479,8,23,25,27,35,44,24);
//		dao.insertBasisDataBatch(480,3,5,10,17,30,31,16);
//		dao.insertBasisDataBatch(481,3,4,23,29,40,41,20);
//		dao.insertBasisDataBatch(482,1,10,16,24,25,35,43);
//		dao.insertBasisDataBatch(483,12,15,19,22,28,34,5);
//		dao.insertBasisDataBatch(484,1,3,27,28,32,45,11);
//		dao.insertBasisDataBatch(485,17,22,26,27,36,39,20);
//		dao.insertBasisDataBatch(486,1,2,23,25,38,40,43);
//		dao.insertBasisDataBatch(487,4,8,25,27,37,41,21);
//		dao.insertBasisDataBatch(488,2,8,17,30,31,38,25);
//		dao.insertBasisDataBatch(489,2,4,8,15,20,27,11);
//		dao.insertBasisDataBatch(490,2,7,26,29,40,43,42);
//		dao.insertBasisDataBatch(491,8,17,35,36,39,42,4);
//		dao.insertBasisDataBatch(492,22,27,31,35,37,40,42);
//		dao.insertBasisDataBatch(493,20,22,26,33,36,37,25);
//		dao.insertBasisDataBatch(494,5,7,8,15,30,43,22);
//		dao.insertBasisDataBatch(495,4,13,22,27,34,44,6);
//		dao.insertBasisDataBatch(496,4,13,20,29,36,41,39);
//		dao.insertBasisDataBatch(497,19,20,23,24,43,44,13);
//		dao.insertBasisDataBatch(498,13,14,24,32,39,41,3);
//		dao.insertBasisDataBatch(499,5,20,23,27,35,40,43);
//		dao.insertBasisDataBatch(500,3,4,12,20,24,34,41);
//		dao.insertBasisDataBatch(501,1,4,10,17,31,42,2);
//		dao.insertBasisDataBatch(502,6,22,28,32,34,40,26);
//		dao.insertBasisDataBatch(503,1,5,27,30,34,36,40);
//		dao.insertBasisDataBatch(504,6,14,22,26,43,44,31);
//		dao.insertBasisDataBatch(505,7,20,22,25,38,40,44);
//		dao.insertBasisDataBatch(506,6,9,11,22,24,30,31);
//		dao.insertBasisDataBatch(507,12,13,32,33,40,41,4);
//		dao.insertBasisDataBatch(508,5,27,31,34,35,43,37);
//		dao.insertBasisDataBatch(509,12,25,29,35,42,43,24);
//		dao.insertBasisDataBatch(510,12,29,32,33,39,40,42);
//		dao.insertBasisDataBatch(511,3,7,14,23,26,42,24);
//		dao.insertBasisDataBatch(512,4,5,9,13,26,27,1);
//		dao.insertBasisDataBatch(513,5,8,21,23,27,33,12);
//		dao.insertBasisDataBatch(514,1,15,20,26,35,42,9);
//		dao.insertBasisDataBatch(515,2,11,12,15,23,37,8);
//		dao.insertBasisDataBatch(516,2,8,23,41,43,44,30);
//		dao.insertBasisDataBatch(517,1,9,12,28,36,41,10);
//		dao.insertBasisDataBatch(518,14,23,30,32,34,38,6);
//		dao.insertBasisDataBatch(519,6,8,13,16,30,43,3);
//		dao.insertBasisDataBatch(520,4,22,27,28,38,40,1);
//		dao.insertBasisDataBatch(521,3,7,18,29,32,36,19);
//		dao.insertBasisDataBatch(522,4,5,13,14,37,41,11);
//		dao.insertBasisDataBatch(523,1,4,37,38,40,45,7);
//		dao.insertBasisDataBatch(524,10,11,29,38,41,45,21);
//		dao.insertBasisDataBatch(525,11,23,26,29,39,44,22);
//		dao.insertBasisDataBatch(526,7,14,17,20,35,39,31);
//		dao.insertBasisDataBatch(527,1,12,22,32,33,42,38);
//		dao.insertBasisDataBatch(528,5,17,25,31,39,40,10);
//		dao.insertBasisDataBatch(529,18,20,24,27,31,42,39);
//		dao.insertBasisDataBatch(530,16,23,27,29,33,41,22);
//		dao.insertBasisDataBatch(531,1,5,9,21,27,35,45);
//		dao.insertBasisDataBatch(532,16,17,23,24,29,44,3);
//		dao.insertBasisDataBatch(533,9,14,15,17,31,33,23);
//		dao.insertBasisDataBatch(534,10,24,26,29,37,38,32);
//		dao.insertBasisDataBatch(535,11,12,14,15,18,39,34);
//		dao.insertBasisDataBatch(536,7,8,18,32,37,43,12);
//		dao.insertBasisDataBatch(537,12,23,26,30,36,43,11);
//		dao.insertBasisDataBatch(538,6,10,18,31,32,34,11);
//		dao.insertBasisDataBatch(539,3,19,22,31,42,43,26);
//		dao.insertBasisDataBatch(540,3,12,13,15,34,36,14);
//		dao.insertBasisDataBatch(541,8,13,26,28,32,34,43);
//		dao.insertBasisDataBatch(542,5,6,19,26,41,45,34);

		
		
		
		dao.executeAllBatch();
		System.out.println("basis OK");
		
//		BnuCountStatusBySEQDAO dao2 = new BnuCountStatusBySEQDAO();
//		dao2.dropTable();
//		dao2.createTable();
//		dao2.insertBatch("2");
		
//		SeqGapCountStatusDAO dao3 = new SeqGapCountStatusDAO();
//		dao3.dropTable();
//		dao3.createTable();
//		dao3.insertBatch();
		
//		StatusCntSeqDAO dao4 = new StatusCntSeqDAO();
//		dao4.dropTable();
//		dao4.createTable();
//		dao4.insertBatch();
		
		
		System.out.println("ALL END!");
	}
	
}
