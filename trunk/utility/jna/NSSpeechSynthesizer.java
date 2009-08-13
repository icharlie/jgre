/*
 * Copyright 2009 digman543
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * NSSpeechSynthesizer.java
 * 
 */
package gre.utility.jna;

import org.rococoa.NSClass;
import org.rococoa.NSObject;
import org.rococoa.Rococoa;

public interface NSSpeechSynthesizer extends NSObject {

  final _Class CLASS = Rococoa.createClass("NSSpeechSynthesizer", _Class.class);

  interface _Class extends NSClass {
  }
}
