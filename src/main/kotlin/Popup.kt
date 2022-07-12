import csstype.*
import org.w3c.dom.HTMLElement
import react.FC
import react.Props
import react.css.css
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.b
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span

data class PopupState(
    val correctAnswer: Boolean,
    val numTries: Int
)

external interface PopupProps : Props {
    var state: PopupState
    var handleClose: MouseEventHandler<HTMLElement>
}

val Popup = FC<PopupProps> {props ->
    div {
        css {
            position = Position.fixed
            backgroundColor = rgba(0, 0, 0, 0.31)
            width = 100.pct
            height = 100.vh
            top = 0.px
            left = 0.px
        }
        div {
            css {
                position = Position.relative
                width = 50.pct
                margin = Margin("auto")
                maxHeight = 70.vh
                marginTop = 100.vh - 85.vh - 20.px
                background = rgb(255, 255, 255)
                borderRadius = 4.px
                padding = 20.px
                border = Border(1.px, LineStyle.solid, rgb(153, 153, 153))
                overflow = Overflow.auto
            }
            span {
                css {
                    cursor = Cursor.pointer
                    position = Position.absolute
                    right = 0.px
                    top = 0.px
                    width = 25.px
                    height = 25.px
                    lineHeight = 20.px
                    textAlign = TextAlign.center
                    fontSize = 25.px
                }
                +"x"
                onClick = props.handleClose
            }
            div {
                b {
                    if (props.state.correctAnswer) {
                        +"Congratulations!"
                    } else {
                        +"Sorry!"
                    }
                }
                p {
                    if (props.state.correctAnswer) {
                        +"Your answer is correct!"
                    } else {
                        if (props.state.numTries <= 0) {
                            +"Your answer is incorrect. You have submitted the maximum allowed times, so future submissions will only receive partial credit."
                        } else {
                            +"Your answer is incorrect. You have ${props.state.numTries} tries remaining."
                        }
                    }
                }
            }
        }
    }
}