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
 * AnalogyEnum.java
 * 
 */
package gre.utility;

public enum AnalogyEnum {

  INDEX("index"), UNIT("unit"), ENG_1("eng_1"), ENG_2("eng_2"), ENG_3("eng_3"), ENG_4("eng_4"), CHINESE_1("chinese_1"), CHINESE_2("chinese_2"), CHINESE_3("chinese_3"), CHINESE_4("chinese_4"), NOTE("note"), ERROR("error");
  private String description;

  private AnalogyEnum(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }
}
