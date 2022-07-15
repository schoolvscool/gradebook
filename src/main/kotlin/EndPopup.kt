import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.b
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p

external interface EndPopupProps : Props {
    var finalGrade: Double
    var finalTime: String
}

val EndPopup = FC<EndPopupProps> { props ->
    div {
        css {
            position = Position.fixed
            backgroundColor = rgba(0, 0, 0, 0.31)
            width = 100.pct
            height = 100.vh
            top = 0.px
            left = 0.px
        }
        if (!endScreenShown) {
            mozart.pause()
            nggyu.load()
            nggyu.play()
            endScreenShown = true
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
            div {
                h2 {
                    +"School's out!"
                }
                div {
                    css {
                        fontSize = 1.5.rem
                        marginBottom = 0.4.rem
                    }
                    +"Your final grade: ${props.finalGrade}%"
                }
                div {
                    css {
                        fontSize = 1.5.rem
                        marginBottom = 0.4.rem
                    }
                    +"Your final time: ${props.finalTime}"
                }
            }
        }
    }
}