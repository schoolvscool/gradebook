import csstype.px
import csstype.rgb
import react.FC
import react.Props
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.input

val subjects = listOf("Art", "English", "History", "Math", "Science", "Extra Credit")

val Gradebook = FC<Props> {
    h1 {
        css {
            padding = 10.px
            backgroundColor = rgb(52, 146, 235)
            color = rgb(255, 255, 255)
        }
        +"Cool School Gradebook"
    }
    for (subject in subjects) {
        h2 {
            +subject
        }
        input {
            css {
                marginTop = 5.px
                marginBottom = 5.px
                fontSize = 14.px
            }
            type = InputType.text
            value = name
            onChange = { event ->
                name = event.target.value
            }
        }
    }
}