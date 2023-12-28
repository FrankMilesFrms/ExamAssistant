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

import cn.hutool.core.io.FileUtil
import cn.hutool.extra.pinyin.PinyinUtil

/**
 * 处理题库
 * * Email : FrankMiles@qq.com
 * * Date  : 2023/12/22, 下午 11:04
 * @author Frms(Frank Miles)
 */
object QuestionsLexer
{

	fun translateFirstPinyin(char: Char): Char
	{
		return PinyinUtil.getFirstLetter(char).lowercaseChar()
	}
	
	/**
	 * 移除中文字符间的空白字符，并且剔除多余的换行
	 */
	fun removeBlank(
		stringBuilder: StringBuilder,
		translatePinyin: (Char) -> Char
	) : StringBuilder
	{
		val result = StringBuilder()
		var previewChar: Char
		var nextChar: Char
		for ((index, reviewChar) in stringBuilder.withIndex())
		{
			previewChar = if (index == 0) '你'  else stringBuilder[index - 1]
			nextChar    = if (index + 1 == stringBuilder.length) '你' else stringBuilder[index + 1]
			if(isRemovableBlank(reviewChar, previewChar, nextChar)
			) {
				continue
			} else
			{
				result.append(
					when(reviewChar)
					{
						'，' -> ','
						'\t' -> ' '
						'‘' -> '\''
						'’' -> '\''
						else          -> translatePinyin(reviewChar)
					}
				)
			}
		}
		return result
	}

	/**
	 * 是否为可以移除的空白字符
	 */
	private fun isRemovableBlank(
		reviewChar: Char,
		previewChar: Char,
		nextChar: Char
	): Boolean {
		return reviewChar.isWhitespace() && run {
			previewChar.isChineseCharactersOrSymbol() || nextChar.isChineseCharactersOrSymbol()
		}
	}

	/**
	 * 重新整理代码，整理规则：
	 * 以" ` "行作为代码分割
	 */
	fun rearrangeFile(string: String, question : (StringBuilder) -> Unit = {}): List<String>
	{
		val array = arrayListOf<String>()
		val stringBuilder = StringBuilder()
		val lines = FileUtil.readLines(this.javaClass.getResource(string), Charsets.UTF_8)
		for (line in lines)
		{
			if(line.contains("`").not()) {
				if(line.isNotBlank()) {
					stringBuilder.append(line).append('\n')
				}
			} else
			{
				if(stringBuilder.isNotBlank())
				{
					stringBuilder.deleteAt(stringBuilder.length -1)
					question(stringBuilder)
					array.add(stringBuilder.toString())
					stringBuilder.clear()
				}
			}
		}
		return array
	}
}