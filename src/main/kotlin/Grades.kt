import react.FC
import react.Props
import react.dom.html.ReactHTML.h3

external interface GradesProps : Props {
    var grades: LinkedHashMap<Subject, Double>
}

val Grades = FC<GradesProps> { props ->
    var totalGrade  = 0.0

    for (sub in props.grades.keys) {
        totalGrade += props.grades[sub]!!
        Grade {
            subject = sub
            grade = props.grades[sub]!!
        }
    }
    h3 {
        +"Final Grade: ${totalGrade / (props.grades.size - 1)}%"
    }
}

external interface GradeProps : Props {
    var subject: Subject
    var grade: Double
}

val Grade = FC<GradeProps> { props ->
    h3 {
        +"${props.subject.displayName}: ${(props.grade / expectedPoints[props.subject]!! * 100)}%"
    }
}