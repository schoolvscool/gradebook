import csstype.px
import react.FC
import react.Props
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
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

    h3 {
        +props.subject.displayName
    }

    label {
        +"Enter assignment submission"
    }
    br()
    input {
        type = InputType.text
        name = props.subject.displayName
        onChange = { event ->
            answer = event.target.value
        }
        onKeyDown = { event ->
            if (event.key == "Enter") {
                props.onAnswerSubmit(props.subject, answer)
            }
        }
    }
    input {
        css {
            margin = 10.px
        }
        type = InputType.button
        value = "Submit"
        onClick = {
            props.onAnswerSubmit(props.subject, answer)
        }
    }

}
