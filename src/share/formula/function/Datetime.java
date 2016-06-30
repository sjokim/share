/*
 *  @(#) Datetime
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

import java.text.SimpleDateFormat;
import java.util.List;

import share.formula.Function;

public class Datetime implements Function {

	public Object process(List param) throws RuntimeException {
		// if (param.size() != 1)
		// throw new RuntimeException("formula function invalid param size ");

		try {
			String pattern = (param.size() == 0 ? "yyyy-MM-dd HH:mm:ss.SSS" : (String) param.get(0));
			return new SimpleDateFormat(pattern).format(new Date());
		} catch (Exception e) {
			throw new RuntimeException("formula function : " + e);
		}
	}

}
