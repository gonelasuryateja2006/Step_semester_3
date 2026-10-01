import java.util.*;

public class OnlineExaminationSystem {

    static class Student {
        private final String id;
        private final String name;

        Student(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static abstract class Question {
        private final int number;
        private final String text;
        private final int marks;

        Question(int number, String text, int marks) {
            if (marks <= 0) {
                throw new IllegalArgumentException("Marks must be positive.");
            }

            this.number = number;
            this.text = text;
            this.marks = marks;
        }

        public abstract boolean isCorrect(String answer);
    }

    static class MultipleChoiceQuestion extends Question {
        private final String correctOption;

        MultipleChoiceQuestion(int number, String text,
                               int marks, String correctOption) {
            super(number, text, marks);
            this.correctOption = correctOption;
        }

        @Override
        public boolean isCorrect(String answer) {
            return answer != null
                    && correctOption.equalsIgnoreCase(answer.trim());
        }
    }

    static class TrueFalseQuestion extends Question {
        private final boolean correctAnswer;

        TrueFalseQuestion(int number, String text,
                          int marks, boolean correctAnswer) {
            super(number, text, marks);
            this.correctAnswer = correctAnswer;
        }

        @Override
        public boolean isCorrect(String answer) {
            return answer != null
                    && Boolean.toString(correctAnswer)
                    .equalsIgnoreCase(answer.trim());
        }
    }

    static class ShortAnswerQuestion extends Question {
        private final String correctAnswer;

        ShortAnswerQuestion(int number, String text,
                            int marks, String correctAnswer) {
            super(number, text, marks);
            this.correctAnswer = correctAnswer;
        }

        @Override
        public boolean isCorrect(String answer) {
            return answer != null
                    && correctAnswer.trim().equalsIgnoreCase(answer.trim());
        }
    }

    static class Answer {
        private final Question question;
        private final String response;

        Answer(Question question, String response) {
            this.question = question;
            this.response = response;
        }
    }

    static class Examination {
        private final String title;
        private final Map<Integer, Question> questions = new LinkedHashMap<>();
        private final Map<String, Attempt> attempts = new HashMap<>();

        Examination(String title, Question... questionList) {
            if (questionList.length == 0) {
                throw new IllegalArgumentException(
                        "An examination must contain questions.");
            }

            this.title = title;

            for (Question question : questionList) {
                if (questions.putIfAbsent(question.number, question) != null) {
                    throw new IllegalArgumentException(
                            "Duplicate question number.");
                }
            }
        }

        public Attempt start(Student student) {
            Attempt existing = attempts.get(student.id);

            if (existing != null) {
                if (existing.submitted) {
                    System.out.println(
                            "Cannot start: this student already submitted "
                                    + title + ".");
                    return null;
                }

                System.out.println("Resuming the existing attempt.");
                return existing;
            }

            Attempt attempt = new Attempt(student, this);
            attempts.put(student.id, attempt);

            System.out.println(title + " started by " + student.name + ".");
            return attempt;
        }
    }

    static class Attempt {
        private final Student student;
        private final Examination examination;
        private final Map<Integer, Answer> answers = new HashMap<>();
        private boolean submitted;
        private int totalScore;

        private Attempt(Student student, Examination examination) {
            this.student = student;
            this.examination = examination;
        }

        public void recordAnswer(int questionNumber, String response) {
            if (submitted) {
                System.out.println(
                        "Cannot change answers for a submitted examination.");
                return;
            }

            Question question = examination.questions.get(questionNumber);

            if (question == null) {
                System.out.println("Invalid question number.");
                return;
            }

            answers.put(questionNumber, new Answer(question, response));

            System.out.println(
                    "Answer recorded for Question " + questionNumber + ".");
        }

        public void submit() {
            if (submitted) {
                System.out.println("Examination is already submitted.");
                return;
            }

            submitted = true;

            System.out.println(examination.title + " submitted by "
                    + student.name + ".");

            int maximumScore = 0;

            for (Question question : examination.questions.values()) {
                Answer answer = answers.get(question.number);

                boolean correct = answer != null
                        && answer.question.isCorrect(answer.response);

                int earned = correct ? question.marks : 0;
                totalScore += earned;
                maximumScore += question.marks;

                System.out.printf("Question %d: %s (%d points).%n",
                        question.number,
                        correct ? "Correct" : "Incorrect",
                        earned);
            }

            System.out.println(
                    "Total score: " + totalScore + "/" + maximumScore + ".");
        }
    }

    public static void main(String[] args) {
        Student student = new Student("S1", "Student 1");

        Examination exam = new Examination(
                "Exam A",
                new MultipleChoiceQuestion(
                        1, "Which option is correct?", 5, "C"),
                new TrueFalseQuestion(
                        2, "Java supports multiple class inheritance.",
                        5, false)
        );

        Attempt attempt = exam.start(student);

        if (attempt != null) {
            attempt.recordAnswer(1, "C");
            attempt.recordAnswer(2, "True");
            attempt.submit();
            attempt.recordAnswer(1, "A");
        }
    }
}