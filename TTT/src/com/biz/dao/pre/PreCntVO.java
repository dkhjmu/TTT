package com.biz.dao.pre;

public class PreCntVO implements Comparable<PreCntVO>{
	int seq;
	int bnu;
	int cnt;
	int norder;
	
	public PreCntVO(int seq, int bnu, int cnt) {
		this.seq=seq;
		this.bnu=bnu;
		this.cnt=cnt;
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getBnu() {
		return bnu;
	}
	public void setBnu(int bnu) {
		this.bnu = bnu;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getNorder() {
		return norder;
	}
	public void setNorder(int norder) {
		this.norder = norder;
	}
	public String[] getValue(){
		String[] result = {seq+"", bnu+"", cnt+"", norder+""};
		return result;
	}
	
	@Override
	public int compareTo(PreCntVO o) {
		return o.getCnt()-this.cnt;
	}

	@Override
	public String toString() {
		return seq + "\t" + bnu + "\t" + cnt + "\t" + norder;
	}
	
	
}
