import react.FC
import react.Props

external interface GradesProps : Props {
    var grades: Map<Subject, Double>
}

val Grades = FC<Props> {

}

external interface GradeProps : Props {
    var subject: Subject
    var grade: Double
}

val Grade = FC<GradeProps> { props ->

}