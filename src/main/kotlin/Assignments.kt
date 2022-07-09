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
}

val Assignments = FC<AssignmentsProps> { props ->
    for (subjectEnum in Subject.values()) {
        Assignment {
            subject = subjectEnum
            onAnswerSubmit = props.onAnswerSubmit
        }
    }
}

external interface AssignmentProps : Props {
    var subject: Subject
    var onAnswerSubmit: (Subject, String) -> Unit
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
        input {
            css {

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
