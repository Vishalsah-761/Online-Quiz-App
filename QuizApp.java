import java.util.*;

class Question {
    String questionText;
    List<String> options;
    int correctAnswer; // index of correct option (0-based)

    public Question(String questionText, List<String> options, int correctAnswer) {
        this.questionText = questionText;
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
    }

    // Shuffle options and update correctAnswer index
    public void shuffleOptions() {
        String correctOption = options.get(correctAnswer);
        Collections.shuffle(options);
        correctAnswer = options.indexOf(correctOption);
    }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }

    public void displayQuestion() {
        System.out.println("\n" + questionText);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }
}

public class QuizApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create list of questions
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Which language is used for Android Development?",
                Arrays.asList("Python", "Java", "C++", "Ruby"), 1));
        questions.add(new Question("Which company developed Java?",
                Arrays.asList("Microsoft", "Sun Microsystems", "Google", "Apple"), 1));
        questions.add(new Question("What is the size of int in Java?",
                Arrays.asList("2 bytes", "4 bytes", "8 bytes", "Depends on OS"), 1));
        questions.add(new Question("Which keyword is used to inherit a class in Java?",
                Arrays.asList("super", "this", "extends", "implements"), 2));
        questions.add(new Question("Which collection doesn’t allow duplicate elements?",
                Arrays.asList("List", "Map", "Set", "Queue"), 2));

        // Shuffle questions
        Collections.shuffle(questions);

        int score = 0;
        List<String> wrongAnswers = new ArrayList<>();

        System.out.println("===== Welcome to the Java Quiz App =====");
        System.out.println("Note: You have 10 seconds to answer each question.");

        for (Question q : questions) {
            // Shuffle options for each question
            q.shuffleOptions();

            q.displayQuestion();

            long startTime = System.currentTimeMillis();
            System.out.print("Enter your choice (1-" + q.options.size() + "): ");

            int answer = -1;
            if (sc.hasNextInt()) {
                answer = sc.nextInt() - 1;
            }

            long endTime = System.currentTimeMillis();
            long elapsedSeconds = (endTime - startTime) / 1000;

            if (elapsedSeconds > 10) {
                System.out.println("Time's up! No marks for this question.");
                wrongAnswers.add(q.questionText + " | Correct Answer: " + q.options.get(q.correctAnswer));
            } else if (q.isCorrect(answer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! Correct answer: " + q.options.get(q.correctAnswer));
                wrongAnswers.add(q.questionText + " | Your Answer: " +
                        (answer >= 0 && answer < q.options.size() ? q.options.get(answer) : "No Answer")
                        + " | Correct Answer: " + q.options.get(q.correctAnswer));
            }
        }

        // Final Report
        System.out.println("\n===== Quiz Finished! =====");
        System.out.println("Your Score: " + score + "/" + questions.size());

        if (score == questions.size()) {
            System.out.println("Excellent! You got all correct!");
        } else if (score >= questions.size() / 2) {
            System.out.println("Good job! Keep practicing.");
        } else {
            System.out.println("Better luck next time. Review your Java basics!");
        }

        // Summary of wrong answers
        if (!wrongAnswers.isEmpty()) {
            System.out.println("\n===== Review Your Mistakes =====");
            for (String wrong : wrongAnswers) {
                System.out.println("- " + wrong);
            }
        }

        sc.close();
    }
}
