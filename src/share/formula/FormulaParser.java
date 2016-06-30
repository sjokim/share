/*
 *  @(#) FormulaParser.java
 *  Copyright 2003 the original author or authors. 
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 *  
 *   @author Paul Kim(sjokim@gmail.com)
 */

package share.formula;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

class FormulaParser {

	public Stack<YyToken> parse(String rule) throws RuntimeException {
		List<YyToken> tmp = step1(rule);
		return step2(tmp);
	}

	private List<YyToken> step1(String rule) throws RuntimeException {
		Stack<YyToken> stack = new Stack<YyToken>();

		try {
			YyLex yyLex = new YyLex(new StringReader(rule));
			YyToken t;
			List<YyToken> list = new ArrayList<YyToken>();
			while ((t = yyLex.yylex()) != null) {
				list.add(t);
				if (t.index == YyToken.FUNCTION) {
					t.value = strip((String) t.value, " (");

					t = new YyToken(YyToken.BRACKET_OPEN, "[");
					list.add(t);
					stack.push(t);
				} else if (t.index == YyToken.PHRENTHESIS_OPEN) {
					stack.push(t);
				} else if (t.index == YyToken.PHRENTHESIS_CLOSE) {
					YyToken tmp = (YyToken) stack.pop();
					if (tmp.index == YyToken.BRACKET_OPEN) {
						t.index = YyToken.BRACKET_CLOSE;
						t.value = "]";
					}
				}
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException("unknown rule error");
		}

	}

	private  String strip(String str, String striped) {
		StringBuffer sb = new StringBuffer(str.length());
		StringTokenizer niz = new StringTokenizer(str, striped);
		while (niz.hasMoreTokens()) {
			sb.append(niz.nextToken());
		}
		return sb.toString();
	}

	private Stack<YyToken> step2(List<YyToken> yyTokens) throws RuntimeException {
		Stack<YyToken> result = new Stack<YyToken>();
		Stack<YyToken> stack = new Stack<YyToken>();

		YyToken temp = null;

		int currentProcedence = 0;
		int stackProcedence = 0;

		for (int i = yyTokens.size() - 1; i >= 0; i--) {
			YyToken yyToken = (YyToken) yyTokens.get(i);

			if (yyToken.index == YyToken.BRACKET_OPEN) { // '['이면
				while ((temp = (YyToken) stack.pop()).index < 1000) {
					result.push(temp);
				}
				result.push(yyToken);
				result.push(yyTokens.get(i - 1));
				i--;
			} else if (yyToken.index == YyToken.BRACKET_CLOSE) { // ']'이면
				result.push(yyToken);
				stack.push(yyToken);
			} else if (yyToken.index == YyToken.PHRENTHESIS_CLOSE) { // ')'이면
				stack.push(yyToken);
			} else if (yyToken.index == YyToken.PHRENTHESIS_OPEN) { // '('이면
				while ((temp = (YyToken) stack.pop()).index != 51) {
					result.push(temp);
				}
			} else if (yyToken.index == YyToken.COMMA) { // ','이면
				while ((temp = (YyToken) stack.pop()).index < 1000) {
					result.push(temp);
				}
				result.push(yyToken);
				stack.push(yyToken);
			} else if (yyToken.index > 100) { // Operation이 아니면
				result.push(yyToken);
			} else { // 여기는 반드시 Operation만이 올수 있다.

				if (stack.empty()) {
					stack.push(yyToken);
				} else {
					currentProcedence = precedence(yyToken);
					do {
						temp = (YyToken) stack.pop();
						stackProcedence = precedence(temp);
						if (currentProcedence <= stackProcedence) {
							result.push(temp);
							if (stack.empty()) {
								stack.push(yyToken);
								break;
							}
						} else {
							stack.push(temp);
							stack.push(yyToken);
						}
					} while (currentProcedence <= stackProcedence);
				} // end of if
			} // end of if
		} // end of while

		while (!stack.empty()) {
			YyToken tok = (YyToken) stack.pop();
			if (tok.index == YyToken.PHRENTHESIS_OPEN || tok.index == YyToken.PHRENTHESIS_CLOSE)
				throw new RuntimeException("Unknown rule error");
			result.push(tok);
		}
		return result;
	}

	private int precedence(YyToken yyToken) {
		if (yyToken.index < 100) {
			return 10 - yyToken.index / 10;
		} else if (yyToken.index > 1000) {
			return 2;
		} else
			return 0;
	}
}
