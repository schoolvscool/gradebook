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

// TODO: use this data class instead of individual maps
data class SubjectData(
    var grade: Double,
    var expectedPoints: Double,
    var maxCredit: Double,
    var numTries: Int
)

data class PopupData(
    val isOpen: Boolean,
    val state: PopupState
)

val expectedPoints = linkedMapOf(
    Subject.ART to 100.0,
    Subject.ENGLISH to 100.0,
    Subject.HISTORY to 100.0,
    Subject.MATH to 100.0,
    Subject.SCIENCE to 100.0,
    Subject.EXTRACREDIT to 25.0
)

val nggyu = Audio("nggyu.wav")

var allowedTriesMap = linkedMapOf(
    Subject.ART to 3,
    Subject.ENGLISH to 3,
    Subject.HISTORY to 3,
    Subject.MATH to 3,
    Subject.SCIENCE to 3,
    Subject.EXTRACREDIT to 3
)

var maximumCreditMap = linkedMapOf(
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

    var popupData: PopupData by useState(PopupData(false, PopupState(false, 3)))

    val checkAnswer = fun(subject: Subject, answer: String) {
        if (gradesMap[subject]!! > 0.0) {
            return
        }
        if (Answers[subject] == answer.lowercase()) {
            popupData = PopupData(true, PopupState(true, allowedTriesMap[subject]!!))
            if (subject == Subject.EXTRACREDIT) {
                nggyu.play()
            }
            gradesMap[subject] = maximumCreditMap[subject]!!
            // We need to reassign gradesMap so the React knows to re-render the page
            gradesMap = LinkedHashMap(gradesMap)
        } else {
            allowedTriesMap[subject] = allowedTriesMap[subject]!! - 1
            if (allowedTriesMap[subject]!! <= 0) {
                maximumCreditMap[subject] = maximumCreditMap[subject]!! - (0.1 * expectedPoints[subject]!!)
            }
            popupData = PopupData(true, PopupState(false, allowedTriesMap[subject]!!))
        }
    }

    var totalGrade = 0.0

    body {
        css {
            fontFamily = FontFamily.sansSerif
            padding = 1.rem
        }

        if (popupData.isOpen) {
            Popup {
                state = popupData.state
                handleClose = {
                    popupData = PopupData(false, popupData.state)
                }
            }
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
                    pointsMap = expectedPoints
                    creditMap = maximumCreditMap
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
