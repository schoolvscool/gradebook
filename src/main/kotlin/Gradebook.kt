import csstype.*
import org.w3c.dom.Audio
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.body
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import react.useState

val expectedPoints = linkedMapOf(
    Subject.ART to 100.0,
    Subject.ENGLISH to 100.0,
    Subject.HISTORY to 100.0,
    Subject.MATH to 100.0,
    Subject.SCIENCE to 100.0,
    Subject.EXTRACREDIT to 25.0
)

val nggyu = Audio("nggyu.wav")

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
                nggyu.play()
            } else {
                gradesMap[subject] = 100.0
            }
            // We need to reassign gradesMap so the React knows to re-render the page
            gradesMap = LinkedHashMap(gradesMap)
        }
    }

    var totalGrade = 0.0

    body {
        css {
            fontFamily = FontFamily.sansSerif
            padding = 1.rem
        }
        h1 {
            css {
                marginTop = 0.px
                marginBottom = 2.rem
                textAlign = TextAlign.center
                backgroundColor = rgb(52, 146, 235)
                color = rgb(255, 255, 255)
            }
            +"Cool School Gradebook"
        }
        div {
            css {
                display = Display.flex
                justifyContent = JustifyContent.spaceEvenly
            }
            section {
                css {
                    padding = 2.rem
                    backgroundColor = rgb(242, 242, 242)
                }
                h2 {
                    css {
                        marginTop = 0.px
                        marginBottom = 0.5.rem
                    }
                    +"Assignments Submission"
                }
                div {
                    +"Submit your answers for each subject to receive credit."
                }
                Assignments {
                    onAnswerSubmit = checkAnswer
                }
            }
            section {
                css {
                    padding = 2.rem
                    minWidth = 250.px
                }
                h2 {
                    css {
                        marginTop = 0.px
                        marginBottom = 0.5.rem
                    }
                    +"Grades"
                }
                div {
                    css {
                        display = Display.flex
                        flexDirection = FlexDirection.column
                    }
                    table {
                        css {
                            borderCollapse = BorderCollapse.collapse
                        }
                        tr {
                            css {
                                nthChild("even") {
                                    backgroundColor = rgb(242, 242, 242)
                                }
                            }
                            th {
                                css {
                                    border = Border(1.px, LineStyle.solid, rgb(221, 221, 221))
                                    padding = 8.px
                                }
                                +"Subject"
                            }
                            th {
                                css {
                                    border = Border(1.px, LineStyle.solid, rgb(221, 221, 221))
                                    padding = 8.px
                                }
                                +"Total grade"
                            }
                        }
                        Grades {
                            grades = gradesMap
                        }
                    }
                    div {
                        css {
                            fontSize = 1.5.rem
                            padding = 1.rem
                            marginTop = 4.rem
                        }
                        +"Final Grade: "
                        span {
                            css {
                                fontSize = 2.rem
                                fontWeight = FontWeight(600)
                            }

                            for (sub in gradesMap.keys) {
                                totalGrade += gradesMap[sub]!!
                            }
                            +"${totalGrade / (gradesMap.size - 1)}%"
                        }
                    }
                }
            }
        }
    }
}
