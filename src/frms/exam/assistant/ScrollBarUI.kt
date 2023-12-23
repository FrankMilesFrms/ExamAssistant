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

import java.awt.*
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JScrollBar
import javax.swing.border.EmptyBorder
import javax.swing.plaf.basic.BasicScrollBarUI

/**
 *
 * * Email : FrankMiles@qq.com
 * * Date  : 2023/12/21, 上午 12:26
 * @author Frms(Frank Miles)
 */
class ScrollBarUI : BasicScrollBarUI()
{
	val TRANSPARENT = Color(0,0,0,0)
	init {
		trackColor = TRANSPARENT
		thumbDarkShadowColor = TRANSPARENT
		thumbLightShadowColor = TRANSPARENT
		thumbHighlightColor = TRANSPARENT
	}

	override fun configureScrollBarColors()
	{
		trackColor = TRANSPARENT
	}

	/**
	 * 设置滑动条宽度
	 */
	override fun getPreferredSize(c: JComponent?): Dimension
	{
		c?.let {
			c.preferredSize = Dimension(5, 0)
		}
		return super.getPreferredSize(c)
	}

	/**
	 * 背景
	 */
	override fun paintTrack(g: Graphics?, c: JComponent?, trackBounds: Rectangle?)
	{
		val graphics2D = g!! as Graphics2D
		graphics2D.paint = GradientPaint(
			0F, 0F, TRANSPARENT,
			trackBounds!!.width.toFloat(), 0F, TRANSPARENT
		)
		graphics2D.composite = AlphaComposite.getInstance(
			AlphaComposite.SRC_OVER,
			0f
		)

		trackBounds.apply{
			graphics2D.fillRect(x, y, width, height)
		}

		//绘制Track的边框
		/*       g2.setColor(new Color(175, 155, 95));
		 g2.drawRect(trackBounds.x, trackBounds.y, trackBounds.width - 1,
				trackBounds.height - 1);
				*/

		if (trackHighlight == DECREASE_HIGHLIGHT)
			this.paintDecreaseHighlight(g)

		if (trackHighlight == INCREASE_HIGHLIGHT)
			this.paintIncreaseHighlight(g)
	}

	override fun paintThumb(g: Graphics, c: JComponent, thumbBounds: Rectangle)
	{
		g.translate(thumbBounds.x, thumbBounds.y)
		// 条颜色
		g.color = TRANSPARENT
	}

	override fun createIncreaseButton(orientation: Int): JButton = JButton().apply {
		border= EmptyBorder(0,0,0,0)
		text = "<"
		background = TRANSPARENT
	}

	override fun createDecreaseButton(orientation: Int): JButton = JButton().apply {
		border= EmptyBorder(0,0,0,0)
		text = ">"
		background = TRANSPARENT
	}
}