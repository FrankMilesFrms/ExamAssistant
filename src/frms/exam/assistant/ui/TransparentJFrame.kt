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

package frms.exam.assistant.ui

import frms.exam.assistant.MainScreen
import java.awt.Color
import java.awt.Frame
import java.awt.Window
import javax.swing.JFrame

/**
 * 透明JFrame
 * * Email : FrankMiles@qq.com
 * * Date  : 2023/12/24, 下午 10:18
 * @author Frms(Frank Miles)
 */
class TransparentJFrame : JFrame()
{
	init
	{
		defaultCloseOperation = EXIT_ON_CLOSE
		// 隐藏标题
		isUndecorated = true;
		type = Type.UTILITY
		isAlwaysOnTop = true
		background = Color(0, 0,0,1)
		isResizable = true
	}

}