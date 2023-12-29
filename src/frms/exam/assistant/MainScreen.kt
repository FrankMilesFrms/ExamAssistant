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

import frms.covert.exam.assistant.ScrollBarUI
import frms.exam.assistant.ui.MainScreenData
import frms.exam.assistant.ui.TransparentJFrame
import frms.exam.assistant.ui.createMainScreenData
import frms.exam.assistant.ui.getColor
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseEvent.BUTTON3
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder

/**
 * 主界面
 * * Email : FrankMiles@qq.com
 * * Date  : 2023/12/24, 下午 08:55
 * @author Frms(Frank Miles)
 */
object MainScreen
{
	private val mainJFrame = TransparentJFrame()
	private var mainConfig = MainScreenData()

	// 主按钮
	private val mainButton = JButton(" O ").apply {
		setButtonTransparent()
	}

	// 控制按钮
	private val subButton = JButton(" · ").apply {
		setButtonTransparent()
	}

	// 答案文本框
	private val answerTextArea = JTextArea().apply {
		lineWrap = true
		setTransparent()
	}

	// 搜索文本框
	private lateinit var searchJTextField: JTextField

	// 答案滑动条
	private val scrollPane = JScrollPane().apply {
		setViewportView(answerTextArea)
		setTransparent()
		border = EmptyBorder(0, 0, 0, 0)
		verticalScrollBar = JScrollBar().apply {
			setUI(ScrollBarUI())
		}
	}

	private var buttonBorderShow = false

	@JvmStatic
	fun main(args: Array<String>)
	{
		show()
		// 更新屏幕
		updateScreenData(mainConfig)		// 显示 JFrame

		mainJFrame.pack()
		mainJFrame.isVisible = true		// 显示后就请求焦点

		// 更改焦点
		answerTextArea.isFocusable = false
		searchJTextField.requestFocus()

		// 隐蔽性提示
		System.err.apply {
			println(this.javaClass)
			Thread.currentThread().stackTrace.forEach {
				println(it)
			}
		}
	}

	/**
	 * 显示界面
	 *
	 */
	private fun show()
	{
		// 背景边框是否显示
		var showBorder = false

		mainJFrame.add(JPanel().apply {

			layout = BorderLayout()
			setTransparent()
			layout = FlowLayout().apply {
				alignment = FlowLayout.LEFT
			}

			add(mainButton)
			add(subButton)
		}, BorderLayout.NORTH)


		subButton.addMouseListener(object : MouseAdapter()
		{
			override fun mouseClicked(e: MouseEvent?)
			{
				if (e?.button == BUTTON3)
				{
					buttonBorderShow = buttonBorderShow.not()
					mainButton.setButtonTransparent(buttonBorderShow)
					subButton.setButtonTransparent(buttonBorderShow)
				}
			}
		})

		subButton.addActionListener {
			showBorder = showBorder.not()
			if (showBorder)
			{
				answerTextArea.border = LineBorder(answerTextArea.foreground, 1)
			} else
			{
				answerTextArea.border = EmptyBorder(0, 0, 0, 0)
			}
		}


		// 题目搜索框
		val searchBar = MyTextField {
			AnswerSearch().searchString(it)
			{ options, result, isConfig ->
				if (isConfig)
				{
					updateScreenData(createMainScreenData(options, mainConfig).apply {
						answerTextArea.text = toString()
					})
				} else
				{
					answerTextArea.text = result
				}
			}
		}

		searchJTextField = searchBar

		mainJFrame.add(searchBar, BorderLayout.SOUTH)

		// 设置可以滑动
		mainJFrame.setMovable(jComponent = mainButton, rightClick = {				// 移动组件，隐藏
			changeAnswerViewStatus()
		}) {
			searchBar.isVisible = searchBar.isVisible.not()
			searchBar.text = "----"
			answerTextArea.isFocusable = false
			searchBar.requestFocus()
		}


		mainJFrame.add(scrollPane, BorderLayout.CENTER)


		// 50ms刷新一次，确保不残留
		Timer(50) {
			mainJFrame.isAlwaysOnTop = true
			scrollPane.repaint()
			searchBar.repaint()
			answerTextArea.repaint()
			mainJFrame.repaint()
		}.start()


		/*
		 *确保鼠标在控件上时，显示控件，当鼠标移出时，取消之。
		 */
		arrayOf(
			mainJFrame.rootPane, mainButton, subButton, answerTextArea, scrollPane, searchJTextField
		).forEach {
			it.mouseInsideListening { status ->
				changeAnswerViewStatus(autoChange = false, controlShow = status)
			}
		}
	}

	/**
	 * Change answer view status
	 * @param autoChange 启用自动判断是否显示
	 * @param controlShow 手动决定是否显示
	 */
	private fun changeAnswerViewStatus(
		autoChange: Boolean = true,
		controlShow: Boolean = false
	)
	{
		if (autoChange)
		{
			scrollPane.isVisible = scrollPane.isVisible.not().apply {
				if (not())
				{
					searchJTextField.isVisible = false
					// 防止匹配字符太多而无法输入
					if (answerTextArea.text.length > 300)
					{
						answerTextArea.text = ""
					}
				} else
				{
					searchJTextField.requestFocus()
				}
			}
		} else
		{
			scrollPane.isVisible = controlShow
			searchJTextField.isVisible = controlShow
			if (controlShow)
			{
				searchJTextField.requestFocus()
			}
		}

	}


	private fun JComponent.mouseInsideListening(inside: (Boolean) -> Unit)
	{
		addMouseListener(object : MouseAdapter()
		                 {
			                 override fun mouseEntered(e: MouseEvent?) =
				                 inside(true)

			                 override fun mouseExited(e: MouseEvent?) =
				                 inside(false)
		                 })
	}

	/**
	 * config Screen
	 *
	 */
	private fun updateScreenData(mainScreenData: MainScreenData = MainScreenData())
	{
		mainJFrame.minimumSize = Dimension(mainScreenData.width, mainScreenData.height)
		mainJFrame.size = Dimension(mainScreenData.width, mainScreenData.height) //		answerTextArea.repaint()

		mainJFrame.repaint()

		answerTextArea.foreground = mainScreenData.fontColor.getColor()

		searchJTextField.foreground = mainScreenData.fontColor.getColor()

		answerTextArea.font = answerTextArea.font.run {
			Font(name, style, mainScreenData.fontSize)
		}

		searchJTextField.font = searchJTextField.font.run {
			Font(name, style, mainScreenData.fontSize)
		}

		mainButton.text = " " + mainScreenData.mainSymbol + " "
		subButton.text = " " + mainScreenData.configSymbol + " "

		mainButton.foreground = mainScreenData.mainColor.getColor()
		subButton.foreground = mainScreenData.configColor.getColor()

	}

	private fun JButton.setButtonTransparent(
		includeBorder: Boolean = false
	)
	{
		margin = Insets(0, 0, 0, 0)
		isContentAreaFilled = false
		if (includeBorder)
		{
			border = LineBorder(Color.GRAY, 1)
		} else
		{
			border = EmptyBorder(0, 0, 0, 0)
		}
	}

	/**
	 * Set a JComponent of transparent
	 * 并非真透明。因为真透明控件会忽略控件。
	 */
	private fun JComponent.setTransparent()
	{
		background = Color(0, 0, 0, 1)
	}

}