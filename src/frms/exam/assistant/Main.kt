package frms.covert.exam.assistant


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
fun main() {
	// 创建 JFrame 实例
	val frame = JFrame()

	frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	frame.setSize(300, 100)
	frame.layout = BorderLayout()

	val label = JLabel(" ♦ ")
	val textLabel = ResultTextArea()

	// 创建 搜索框
	val searchBar = MyTextField {
		println(it)
		textLabel.text = ""
		textLabel.text = it.repeat(2)
	}
	frame.add(searchBar, BorderLayout.SOUTH)

	frame.add(
		run {
			// 移动组件，右键隐蔽，左键搜索框隐藏
			label.foreground = Color.RED

			frame.setMovable(jComponent = label) {
				searchBar.isVisible = searchBar.isVisible.not()
				searchBar.text = "------"
				textLabel.isFocusable = false
				searchBar.requestFocus()
			}
			label
		},
		BorderLayout.NORTH
	)
	val scrollPane = JScrollPane()

	frame.add(run {
		scrollPane.setViewportView(textLabel)
		scrollPane.background = Color(0,0,0,0)
		scrollPane.border = EmptyBorder(0,0,0,0)
		scrollPane.verticalScrollBar = JScrollBar().apply {
			setUI(ScrollBarUI())
		}
		scrollPane
	}, BorderLayout.CENTER)


	// 隐藏标题
	frame.isUndecorated = true;
	frame.type = Window.Type.UTILITY
	frame.isAlwaysOnTop = true

	// 50ms刷新一次，确保不残留
	Timer(50) {
		frame.isAlwaysOnTop = true
		scrollPane.repaint()
		searchBar.repaint()
		textLabel.repaint()
		frame.repaint()
	}.start()

	frame.background = Color(0, 0,0,0)
	// 显示 JFrame
	frame.isVisible = true

	textLabel.isFocusable = false
	searchBar.requestFocus()

}

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
	val textField = JTextField("------")

	textField.border = EmptyBorder(0,0,0,0)
	textField.background = Color(0,0,0,0)
	textField.addActionListener {
		textField.getText()?.let {
			searchClick(it)
		}
	}


	return textField
}


fun JFrame.setMovable(jComponent: JComponent, onClick : () -> Unit)
{
	var isShowing = true
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

		override fun mouseClicked(e: MouseEvent?)
		{
			e?.let {
				if(e.button == 3) {
					isShowing = isShowing.not();
					opacity = if(isShowing) {
						0.1f
					} else {
						1f
					}
				} else if(e.button == 1) {
					onClick()
				}

			}
		}
	})

	jComponent.addMouseMotionListener(object : MouseMotionAdapter() {
		override fun mouseDragged(e: MouseEvent?)
		{
			if(isDragging && e!=null)
			{
				setLocation(
					location.x + e.x -position.first,
					location.y + e.y -position.second,
				)
			}
		}
	})
}
