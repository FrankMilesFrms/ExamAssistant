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

package frms.exam.assistant

import frms.covert.exam.assistant.QuestionsLexer
import frms.covert.exam.assistant.isChineseCharacters

/**
 * 问题回答
 * * Email : FrankMiles@qq.com
 * * Date  : 2023/12/24, 下午 02:42
 * @author Frms(Frank Miles)
 */
object AnswerSearch
{
	/**
	 * 回答，<拼音,正文>
	 */
	private val questions = arrayListOf<Pair<String, String>>()

	init
	{
		QuestionsLexer.rearrangeFile("/answer.txt")
		{ text ->
			QuestionsLexer.removeBlank(
				text,
				translatePinyin = QuestionsLexer::translateFirstPinyin
			).let { pingyin ->
				questions.add(pingyin.toString() to text.toString())
			}
		}
	}

	/**
	 * 检索答案
	 *
	 * @param command
	 * @return
	 */
	fun searchList(command: String): List<String>
	{
		val result = arrayListOf<String>()
		val words = command.lexerStatement()

		if(words.isEmpty()) {
			return result
		}
		questions.forEach {
			if(matchWords(words, it.first)) {
				result.add(it.second)
			} else if(command.hasChinese())
			{
				if(matchWords(words, it.second)) {
					result.add(it.second)
				}
			}
		}
		return result
	}

	fun searchString(command: String): String
	{
		val stringBuilder = StringBuilder()
		searchList(command).forEach {
			stringBuilder.append(it)
			stringBuilder.append("\n-----\n")
		}
		stringBuilder.append("-")
		return stringBuilder.toString()
	}

	private fun String.hasChinese(): Boolean
	{
		for(char in toCharArray()) {
			if(char.isChineseCharacters()) {
				return true
			}
		}
		return false
	}

	/**
	 * 匹配字符
	 */
	private fun matchWords(words: List<String>, text: String): Boolean
	{
		return matchWordsRecursion(commands = words, answer = text)
	}

	/**
	 * Match 字符
	 *
	 * @param answerIndex
	 * @param listIndex
	 * @param commands
	 * @param answer
	 * @return
	 */
	private fun matchWordsRecursion(
		answerIndex: Int = 0,
		listIndex: Int = 0,
		commands: List<String>,
		answer: String
	): Boolean
	{
		if(listIndex == commands.size) {
			return true
		}

		val start = answer.indexOf(
			commands[listIndex],
			startIndex = answerIndex,
			ignoreCase = true
		)

		if(start < 0) {
			return false
		}

		return matchWordsRecursion(
			start + commands[listIndex].length,
			listIndex + 1,
			commands,
			answer
		)
	}
	/**
	 * 获取检索字段
	 */
	private fun String.lexerStatement() : List<String>
	{
		val res = arrayListOf<String>()
		val tempString = StringBuilder()

		for(review in this)
		{
			if(review.isLetterOrDigit()) {
				tempString.append(review)
			} else if(tempString.isNotEmpty())
			{
				res.add(tempString.toString())
				tempString.clear()
			}
		}
		if(tempString.isNotEmpty()) {
			res.add(tempString.toString())
		}
		return res
	}
}