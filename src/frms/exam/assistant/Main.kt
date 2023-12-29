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
