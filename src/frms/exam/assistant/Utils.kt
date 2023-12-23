/*
 * Copyright (C) 2023 Frank Miles - Frms
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package frms.covert.exam.assistant

/**
 *
 * * Email : FrankMiles@qq.com
 * * Date  : 2023/12/23, 上午 12:19
 * @author Frms(Frank Miles)
 */
/**
 * 是否为中文字符
 *
 */
fun Char.isChineseCharacters() : Boolean
{
	return run {
		this in '\u4e00'..'\u9fff'
	}
}

/**
 * 包含中文符号，注意，
 */
fun Char.isChineseCharactersOrSymbol(includeEnglishSymbol : Boolean = true) : Boolean
{
	return isChineseCharacters() || isLetterOrDigit().not()
}