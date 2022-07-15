import csstype.*
import kotlinx.browser.window
import org.w3c.dom.Audio
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import react.useState
import kotlin.js.Date
import kotlin.math.ceil
import kotlin.math.floor

data class SubjectData(
    var grade: Double,
    var expectedPoints: Double,
    var maxCredit: Double,
    var numTries: Int,
    var showHint: Boolean,
    var showAnswer: Boolean
)

data class PopupData(
    val isOpen: Boolean,
    val state: PopupState
)

val nggyu = Audio("nggyu.wav")
val mozart = Audio("EineKleineNachtmusik_short.mp3")

const val timeLimit = 40 * 1000 * 60

var interval = 0
var countdownStarted = false

val Gradebook = FC<Props> {
    var subjectMap: LinkedHashMap<Subject, SubjectData> by useState(linkedMapOf(
        Subject.ART to SubjectData(0.0, 100.0, 100.0, 3, showHint = false, showAnswer = false),
        Subject.ENGLISH to SubjectData(0.0, 100.0, 100.0, 3, showHint = false, showAnswer = false),
        Subject.HISTORY to SubjectData(0.0, 100.0, 100.0, 3, showHint = false, showAnswer = false),
        Subject.MATH to SubjectData(0.0, 100.0, 100.0, 3, showHint = false, showAnswer = false),
        Subject.SCIENCE to SubjectData(0.0, 100.0, 100.0, 3, showHint = false, showAnswer = false),
        Subject.EXTRACREDIT to SubjectData(0.0, 25.0, 25.0, 3, showHint = false, showAnswer = false)
    ))

    var allAnswered: Boolean by useState(false)

    var popupData: PopupData by useState(PopupData(false, PopupState(false, 3)))

    var startTime: Date by useState(Date())
    var time: Date by useState(Date())
    var timeStarted: Boolean by useState(false)
    var timeEnded: Boolean by useState(false)


    val checkAnswer = fun(subject: Subject, answer: String) {
        if (subjectMap[subject]!!.grade > 0.0) {
            return
        }
        if (Answers[subject] == answer.lowercase()) {
            popupData = PopupData(true, PopupState(true, subjectMap[subject]!!.numTries))
            if (subject == Subject.EXTRACREDIT) {
                mozart.pause()
                nggyu.load()
                nggyu.play()
            }
            subjectMap[subject]!!.grade = subjectMap[subject]!!.maxCredit
            subjectMap[subject]!!.showHint = false
            subjectMap[subject]!!.showAnswer = true
            // We need to reassign subjectMap so the React knows to re-render the page
            subjectMap = LinkedHashMap(subjectMap)

            var unanswered = false
            for (subjectEnum in Subject.values()) {
                if (subjectMap[subjectEnum]!!.grade == 0.0) {
                    unanswered = true
                    break
                }
            }
            if (!unanswered) {
                allAnswered = true
            }
        } else {
            subjectMap[subject]!!.numTries = subjectMap[subject]!!.numTries - 1
            if (subjectMap[subject]!!.numTries <= 0) {
                subjectMap[subject]!!.maxCredit = subjectMap[subject]!!.maxCredit - (0.1 * subjectMap[subject]!!.expectedPoints)
                subjectMap[subject]!!.showHint = true
            }
            popupData = PopupData(true, PopupState(false, subjectMap[subject]!!.numTries))
            subjectMap = LinkedHashMap(subjectMap)
        }
    }

    if (popupData.isOpen && !timeEnded) {
        Popup {
            state = popupData.state
            handleClose = {
                popupData = PopupData(false, popupData.state)
            }
        }
    }

    if (timeEnded || allAnswered) {
        EndPopup {
            var totalGrade = 0.0
            for (sub in subjectMap.keys) {
                totalGrade += subjectMap[sub]!!.grade
            }
            finalGrade = totalGrade / (subjectMap.size - 1)
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
                subjectDataMap = subjectMap
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
                        grades = subjectMap
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

                        var totalGrade = 0.0
                        for (sub in subjectMap.keys) {
                            totalGrade += subjectMap[sub]!!.grade
                        }
                        +"${totalGrade / (subjectMap.size - 1)}%"
                    }
                }
            }
        }
        section {
            css {
                padding = 2.rem
            }
            h2 {
                +"Assignments due in"
            }
            div {
                css {
                    display = Display.flex
                    justifyContent = JustifyContent.spaceEvenly
                    fontSize = 2.rem
                }
                button {
                    +"Start"
                    onClick = {
                        startTime = Date()
                        time = Date()
                        timeStarted = true
                        interval = window.setInterval({ time = Date() }, 1000)
                    }
                    hidden = timeStarted
                }

                val elapsedTime = time.getTime() - startTime.getTime()
                val remainingTime = timeLimit - elapsedTime
                if (timeStarted && ceil(remainingTime / 1000) <= 0 && !timeEnded) {
                    window.clearInterval(interval)
                    timeEnded = true
                }

                val minutes = floor(remainingTime / 1000 / 60)
                if (minutes < 2 && !countdownStarted) {
                    nggyu.pause()
                    mozart.load()
                    mozart.play()
                    countdownStarted = true
                }
                val seconds = ceil(remainingTime / 1000 % 60)
                var minutesString = if (minutes >= 10) "$minutes" else "0${minutes}"
                if (minutes < 0) minutesString = "00"
                var secondsString = if (seconds >= 10) "$seconds" else "0${seconds}"
                if (seconds == 60.0) secondsString = "00"

                +"${minutesString}:${secondsString}"
            }
        }
    }
}
