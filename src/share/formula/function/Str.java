/*
 *  @(#) handle
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

import java.text.DecimalFormat;
import java.util.List;

import share.formula.Function;
import share.formula.util.ArrayParamUtil;

public class Str implements Function {

	public Object process(List param) throws RuntimeException {
		try {
			StringBuffer sb = new StringBuffer();
			Object o = param.get(0);
			String format = param.size() >= 1 ? (String) param.get(1) : null;
			if (o == null) {
				return "null";
			} else if (o instanceof List) {
				return ArrayParamUtil.str((List) o, format);
			} else if (o.getClass().isArray()) {
				return ArrayParamUtil.strArr(o, format);
			} else {
				if (format == null)
					return o.toString();
				else
					return new DecimalFormat(format).format(o);
			}
		} catch (Exception e) {
			throw new RuntimeException("formula function : " + e);
		}
	}
}
