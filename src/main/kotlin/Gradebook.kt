import csstype.Display
import csstype.pct
import csstype.px
import csstype.rgb
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.useState

val expectedPoints = linkedMapOf(
    Subject.ART to 100.0,
    Subject.ENGLISH to 100.0,
    Subject.HISTORY to 100.0,
    Subject.MATH to 100.0,
    Subject.SCIENCE to 100.0,
    Subject.EXTRACREDIT to 25.0
)

val Gradebook = FC<Props> {
    var gradesMap: LinkedHashMap<Subject, Double> by useState(linkedMapOf(
        Subject.ART to 0.0,
        Subject.ENGLISH to 0.0,
        Subject.HISTORY to 0.0,
        Subject.MATH to 0.0,
        Subject.SCIENCE to 0.0,
        Subject.EXTRACREDIT to 0.0
    ))

    val checkAnswer = { subject: Subject, answer: String ->
        if (Answers[subject] == answer.lowercase()) {
            if (subject == Subject.EXTRACREDIT) {
                gradesMap[subject] = 25.0
            } else {
                gradesMap[subject] = 100.0
            }
            // We need to reassign gradesMap so the React knows to re-render the page
            gradesMap = LinkedHashMap(gradesMap)
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
        div {
            css {
                flex = 50.pct
                padding = 10.px
            }
            h2 {
                +"Grades"
            }
            Grades {
                grades = gradesMap
            }
        }
    }
}