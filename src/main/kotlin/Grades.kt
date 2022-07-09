import csstype.Border
import csstype.LineStyle
import csstype.px
import csstype.rgb
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr

external interface GradesProps : Props {
    var grades: LinkedHashMap<Subject, Double>
}

val Grades = FC<GradesProps> { props ->
    for (sub in props.grades.keys) {
        Grade {
            subject = sub
            grade = props.grades[sub]!!
        }
    }
}

external interface GradeProps : Props {
    var subject: Subject
    var grade: Double
}

val Grade = FC<GradeProps> { props ->

    tr {
        css {
            nthChild("even") {
                backgroundColor = rgb(242, 242, 242)
            }
        }
        td {
            css {
                border = Border(1.px, LineStyle.solid, rgb(221, 221, 221))
                padding = 8.px
            }
            +props.subject.displayName
        }
        td {
            css {
                border = Border(1.px, LineStyle.solid, rgb(221, 221, 221))
                padding = 8.px
            }
            +"${(props.grade / expectedPoints[props.subject]!! * 100)}%"
        }
    }
}