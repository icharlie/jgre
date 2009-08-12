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
 * VocabularyEnum.java
 * 
 */
package gre.utility;

public enum VocabularyEnum {

  INDEX("index"), UNIT("unit"), ENG_1("eng_1"), CHINESE_1("chinese_1"), MEMO("memo"), ERROR("error");
  private String description;

  private VocabularyEnum(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}