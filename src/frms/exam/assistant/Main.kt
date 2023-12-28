package frms.exam.assistant


import frms.covert.exam.assistant.ScrollBarUI
import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.EmptyBorder


/**
 *
 *<p>
 * Email : FrankMiles@qq.com
 * Date  : 2023/12/19, 下午 01:15
 *</p>
 * @author Frms(Frank Miles)
 */
//fun main()
//{
//	// 创建 JFrame 实例
//	val frame = JFrame()
//
//	frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
//	frame.setSize(300, 100)
//	frame.layout = BorderLayout()
//
//	val label = JLabel(" ♦ ")
//	val answerTextArea = ResultTextArea()
//
//	// 创建 搜索框
//	val searchBar = MyTextField {
//		answerTextArea.text = AnswerSearch.searchString(it)
//	}
//	frame.add(searchBar, BorderLayout.SOUTH)
//
//	frame.add(
//		run {
//			// 移动组件，右键隐蔽，左键搜索框隐藏
//			label.foreground = Color.RED
//
//			frame.setMovable(jComponent = label) {
//				searchBar.isVisible = searchBar.isVisible.not()
//				searchBar.text = "------"
//				answerTextArea.isFocusable = false
//				searchBar.requestFocus()
//			}
//			label
//		},
//		BorderLayout.NORTH
//	)
//	val scrollPane = JScrollPane()
//
//	frame.add(run {
//		scrollPane.setViewportView(answerTextArea)
//		scrollPane.background = Color(0,0,0,0)
//		scrollPane.border = EmptyBorder(0,0,0,0)
//		scrollPane.verticalScrollBar = JScrollBar().apply {
//			setUI(ScrollBarUI())
//		}
//		scrollPane
//	}, BorderLayout.CENTER)
//
//
//	// 隐藏标题
//	frame.isUndecorated = true;
//	frame.type = Window.Type.UTILITY
//	frame.isAlwaysOnTop = true
//
//	// 50ms刷新一次，确保不残留
//	Timer(50) {
//		frame.isAlwaysOnTop = true
//		scrollPane.repaint()
//		searchBar.repaint()
//		answerTextArea.repaint()
//		frame.repaint()
//	}.start()
//
//	frame.background = Color(0, 0,0,0)
//	// 显示 JFrame
//	frame.isVisible = true
//
//	answerTextArea.isFocusable = false
//	searchBar.requestFocus()
//
//}

fun ResultTextArea() : JTextArea
{
	val textArea = JTextArea()
	textArea.lineWrap = true
	textArea.background = Color(0, 0, 0, 0)
	return textArea
}

fun MyTextField(
	searchClick : (String) -> Unit
) : JTextField
{
	val textField = JTextField("--")

	textField.border = EmptyBorder(0,0,0,0)
	textField.background = Color(0,0,0,0)
	textField.addActionListener {
		textField.getText()?.let {
			searchClick(it)
		}
	}

	textField.isEditable = true
	return textField
}


/**
 * Set a movable frame
 *
 * @param jComponent
 * @param rightClick
 * @param onClick
 * @receiver
 * @receiver
 */
fun JFrame.setMovable(jComponent: JComponent, rightClick:() -> Unit, onClick : () -> Unit)
{
	var isDragging = false
	var position = 0 to 0
	jComponent.addMouseListener(object : MouseAdapter()
	{
		override fun mousePressed(e: MouseEvent?)
		{
			isDragging = true
			e?.let {
				position = it.x to it.y
			}
		}

		override fun mouseReleased(e: MouseEvent?)
		{
			isDragging = false
		}

		override fun mouseClicked(e: MouseEvent)
		{
			if(e.button == MouseEvent.BUTTON1) {
				onClick()
			} else if (e.button == MouseEvent.BUTTON3){
				rightClick()
			}
		}
	})

	jComponent.addMouseMotionListener(object : MouseMotionAdapter() {
		override fun mouseDragged(e: MouseEvent?)
		{
			if(isDragging && e!=null)
			{
				setLocation(
					location.x + e.x - position.first,
					location.y + e.y - position.second,
				)
			}
		}
	})
}
