/*
 *  @(#) Replace
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

package share.formula.function;

import java.util.List;

import share.formula.Function;

public class Replace implements Function {
	public Object process(List param) throws RuntimeException {
		if (param.size() != 3)
			throw new RuntimeException("formula function invalid param size ");

		try {
			String v = (String) param.get(0);
			String s1 = (String) param.get(1);
			String s2 = (String) param.get(2);
			int i = v.indexOf(s1);
			int start = 0;
			while (i >= 0) {
				String h = v.substring(0, i);
				String t = v.substring(h.length() + s1.length());
				v = h + s2 + t;
				start = h.length() + s2.length();
				i = v.indexOf(s1, start);
			}
			return v;
		} catch (Exception e) {
			throw new RuntimeException("formula function : " + e);
		}
	}
}
