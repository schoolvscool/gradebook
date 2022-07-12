import csstype.rem
import react.FC
import react.Props
import react.css.css
import react.dom.html.AutoComplete
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h4
import react.dom.html.ReactHTML.input
import react.useState

external interface AssignmentsProps : Props {
    var onAnswerSubmit: (Subject, String) -> Unit
    var pointsMap: Map<Subject, Double>
    var creditMap: Map<Subject, Double>
}

val Assignments = FC<AssignmentsProps> { props ->
    for (subjectEnum in Subject.values()) {
        Assignment {
            subject = subjectEnum
            onAnswerSubmit = props.onAnswerSubmit
            maximumPoints = props.pointsMap[subjectEnum]!!
            maximumCredit = props.creditMap[subjectEnum]!!
        }
    }
}

external interface AssignmentProps : Props {
    var subject: Subject
    var onAnswerSubmit: (Subject, String) -> Unit
    var maximumPoints: Double
    var maximumCredit: Double
}

val Assignment = FC<AssignmentProps> { props ->
    var answer by useState("")

    div {
        h4 {
            css {
                marginBottom = 0.4.rem
            }
            +props.subject.displayName
        }
        div {
            css {
                marginBottom = 0.4.rem
            }
            +"Points available: ${props.maximumCredit}/${props.maximumPoints}"
        }
        input {
            css {
                marginRight = 1.rem
            }
            onChange = { event ->
                answer = event.target.value
            }
            onKeyDown = { event ->
                if (event.key == "Enter") {
                    props.onAnswerSubmit(props.subject, answer)
                }
            }
            autoComplete = AutoComplete.off
        }
        input {
            type = InputType.submit
            onClick = {
                props.onAnswerSubmit(props.subject, answer)
            }
        }
    }
}
