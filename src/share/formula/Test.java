package share.formula;

import java.util.HashMap;
import java.util.Map;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Formula r = new Formula("max(a_b ==10?10:200, 1000) >100 ?'qqqqqqq' :
		// 'wwwwwwww'");
		Formula r0 = new Formula("'cnt='+count(cpu) +', min=' + min(cpu) + ' , max='+ max(cpu) + ', sum=' + sum(cpu) + ' , avg=' + avg(cpu)");
		Formula r1 = new Formula("'cnt='+count(cpu) +', min' + min(cpu) ");
		Formula r2 = new Formula("avg(cpu)   ");
		Formula r3 = new Formula("replace('abcaa','aa','xx') ");

		Map m = new HashMap();
		float[] cpu = new float[] { 11, 20, 30, 77 };
		m.put("cpu", cpu);
		m.put("a", 10);
		m.put("b", "20");
		System.out.println(r0.calculate(m));
		System.out.println(r1.calculate(m));
		System.out.println(r2.calculate(m));
		System.out.println(r3.calculate(m));
		// "".startsWith(prefix)

	}

}
