import csstype.Display
import csstype.pct
import csstype.px
import csstype.rgb
import kotlinx.browser.window
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.useState

val Gradebook = FC<Props> {
    val grades: MutableMap<Subject, Double> by useState(mutableMapOf(
        Subject.ART to 0.0,
        Subject.ENGLISH to 0.0,
        Subject.HISTORY to 0.0,
        Subject.MATH to 0.0,
        Subject.SCIENCE to 0.0,
        Subject.EXTRACREDIT to 0.0
    ))

    val checkAnswer = { subject: Subject, answer: String ->
        if (Answers[subject] == answer) {
            grades[subject] = 100.0
            window.alert("Correct! Your grade in ${subject.displayName} is ${grades[subject]}%!")
        } else {
            window.alert("Incorrect! Your grade in ${subject.displayName} is ${grades[subject]}%!")
        }
    }

    h1 {
        css {
            padding = 10.px
            backgroundColor = rgb(52, 146, 235)
            color = rgb(255, 255, 255)
        }
        +"Cool School Gradebook"
    }
    div {
        css {
            display = Display.flex
        }
        div {
            css {
                flex = 50.pct
                padding = 10.px
            }
            h2 {
                +"Assignments"
            }
            Assignments {
                onAnswerSubmit = checkAnswer
            }
        }
    }
}
