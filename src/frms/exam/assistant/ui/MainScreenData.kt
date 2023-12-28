package frms.exam.assistant.ui

import java.awt.Color

/**
 *JFrame 控制数据
 *<p>
 * Email : FrankMiles@qq.com
 * Date  : 2023/12/24, 下午 10:57
 *</p>
 * @author Frms(Frank Miles)
 */
data class MainScreenData(
	// 界面宽度, w
	var width: Int = 60,
	// 界面高度, h
	var height: Int = 80,
	// 一般字体颜色,tc
	var fontColor: String = "gray",
	// 一般字体大小,ts
	var fontSize: Int = 10,
	// 主按钮符号, ms
	var mainSymbol: String = "O",
	// 副按钮符号, ss
	var configSymbol: String = "·",
	// 主按钮颜色, mc
	var mainColor: String = "gray",
	// 副按钮颜色, sc
	var configColor: String = "gray"
) {
	override fun toString(): String
	{
		return """
			--界宽w=$width，高h=$height，字色tc=$fontColor，大小ts=$fontSize，主按钮ms='$mainSymbol'，色mc=$mainColor，副按钮ss='$configSymbol'，色sc=$configColor
			--表[Red,Yellow,White,Orange,Green,BLue,Black,gray]
			
		""".trimIndent()
	}
}

fun String.getColor(): Color = when(lowercase())
{
	"r" -> Color.RED
	"y" -> Color.YELLOW
	"w" -> Color.WHITE
	"o" -> Color.ORANGE
	"g" -> Color.GREEN
	"bl" -> Color.BLUE
	"b" -> Color.BLACK
	else -> Color.GRAY
}

/**
 * Create main screen data
 *
 * @param options
 * @return
 */
fun createMainScreenData(options: List<String>, mainScreenData: MainScreenData= MainScreenData()): MainScreenData
{
	if(options.size < 2 || options[0].lowercase() != "frms") {
		// 恢复默认设置
		if(options.size == 1 && options[0].lowercase() == "frms") {
			return MainScreenData()
		}
		return mainScreenData
	}
	var index = 1
	while(index < options.size)
	{
		when(
			options[index].lowercase().apply {
				index++
			}
		) {
			"w" -> {

				options[index].apply {
					if(isInt()) {
						mainScreenData.width = toInt()
					}
				}
			}
			"h" -> {
				options[index].apply {
					if(isInt()) {
						mainScreenData.height = toInt()
					}
				}
			}
			"tc" -> {
				mainScreenData.fontColor = options[index]
			}
			"ts" -> {
				options[index].apply {
					if(isInt()) {
						mainScreenData.fontSize = toInt()
					}
				}
			}
			"ms" -> {
				mainScreenData.mainSymbol =  options[index]
			}
			"ss" -> {
				mainScreenData.configSymbol = options[index]
			}
			"mc" -> {
				mainScreenData.mainColor = options[index]
			}
			"sc" -> {
				mainScreenData.configColor = options[index]
			}
			else -> {
				index--
			}
		}
		index++
	}

	return mainScreenData
}

fun String.isInt(): Boolean
{
	try {
		Integer.parseInt(this)
	} catch (e: NumberFormatException) {
		return false
	}
	return true
}