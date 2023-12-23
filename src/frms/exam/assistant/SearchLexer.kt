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

/**
 * 检索分析
 * 一段连续的英文是要求题目存在此句
 * 不连续词句的需要顺序一致。
 * * Email : FrankMiles@qq.com
 * * Date  : 2023/12/23, 下午 03:05
 * @author Frms(Frank Miles)
 */
object SearchLexer
{
	@JvmStatic
	fun main(args: Array<String>)
	{

	}

	fun lexerStatement(command : String) : List<String>
	{
		val res = arrayListOf<String>()
		val tempString = StringBuilder()

		for((index, review) in command.withIndex())
		{
			if(review.isLetterOrDigit()) {
				tempString.append(review)
			} else if(tempString.isNotEmpty())
			{

			}
		}
		return res
	}
}