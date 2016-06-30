/*
 *  @(#) FormulaAnalyzer.java
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
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class FormulaAnalyzer {
    private Stack tokens;
    private Set mapParam = new HashSet();
    private Set funcs = new HashSet();
    public void analyze(String rule) throws RuntimeException {
        this.tokens = new FormulaParser().parse(rule);
        for (int i = 0; i < tokens.size(); i++) {
            YyToken tok = (YyToken) tokens.get(i);
            switch (tok.index) {
                case YyToken.VARIABLE :
                    mapParam.add(tok.value);
                    break;
                case YyToken.FUNCTION :
                	funcs.add(tok.value);
                    break;
            }
        }
    }
    public java.util.Set getMapParam() {
        return mapParam;
    }
    public java.util.Set getFunctions() {
        return funcs;
    }
}
