import java.util.Scanner;

/**
 * ${PROJECT_NAME}.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 ${DATE}
 * @link <a href="https://github.com/Centnerman">My GitHub</a>
 */

/*
Требования:
Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b.
данные передаются в одну строку (смотри пример)! Решения, в которых каждое число и арифметическая операция передаются с новой строки считаются неверными.

Калькулятор умеет работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.

Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми.

Калькулятор умеет работать только с целыми числами.

Калькулятор умеет работать только с арабскими или римскими цифрами одновременно, при вводе пользователем строки вроде 3 + II
калькулятор должен выбросить исключение и прекратить свою работу.

При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.

При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.

При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение выбрасывает исключение и завершает свою работу.

Результатом операции деления является целое число, остаток отбрасывается.

Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль.
Результатом работы калькулятора с римскими числами могут быть только положительные числа, если результат работы меньше единицы, выбрасывается исключение
 */

public class Main {
    public static void main(String[] args) {
        String inputMathLine;
        boolean err = true;
        Scanner scanner = new Scanner(System.in);

        do {
            inputMathLine = scanner.nextLine();
            System.out.println(calc(inputMathLine));
        } while (err);
        scanner.close();
    }

    private static String calc(String input) {
        String parsedString;
        String firstOperand;
        String secondOperand;
        String operation;

        int firstArgument;
        int secondArgument;
        int result;

        boolean firstOperandType; // true - arabic, false - roman
        boolean secondOperandType;

        String[] lineParts = input.split(" ");

        if (lineParts.length <= 2) {
            throw new IllegalArgumentException(input + " cannot be parsed");
        }

        if (lineParts.length > 3) {
            throw new IllegalArgumentException(input + " incorrect task");
        }

        firstOperand = lineParts[0];
        operation = lineParts[1];
        secondOperand = lineParts[2];

        // Распознаем первый аргумент
        try {
            firstArgument = Integer.parseInt(firstOperand);
            firstOperandType = true;
        } catch (NumberFormatException e) {
            try {
                firstArgument = Roman.romanToArabic(firstOperand);
                firstOperandType = false;
            } catch (Exception e1) {
                throw new IllegalArgumentException(firstOperand + " cannot be recognized as Integer or Roman Numeral");
            }
        }

        // Распознаем второй аргумент
        try {
            secondArgument = Integer.parseInt(secondOperand);
            secondOperandType = true;
        } catch (NumberFormatException e) {
            try {
                secondArgument = Roman.romanToArabic(secondOperand);
                secondOperandType = false;
            } catch (Exception e1) {
                throw new IllegalArgumentException(secondOperand + " cannot be recognized as Integer or Roman Numeral");
            }
        }

        // Проверяем диапазон входных значений
        if (firstArgument < 1 | firstArgument > 10 | secondArgument < 1 | secondArgument > 10) {
            throw new IllegalArgumentException("operands must be between 1 and 10");
        }

        //  Проверяем совместимость типов
        if ((firstOperandType & !secondOperandType) | (!firstOperandType & secondOperandType)) {
            throw new IllegalArgumentException(" operands types are not compatible");
        }

        // Производим вычисления
        result = switch (operation) {
            case ("+") -> firstArgument + secondArgument;
            case ("-") -> firstArgument - secondArgument;
            case ("*") -> firstArgument * secondArgument;
            case ("/") -> firstArgument / secondArgument;
            default -> throw new IllegalArgumentException(" operand (" + operation + ") not recognized");
        };

        // Формируем результат исходя из начальных условий
        if (firstOperandType) {
            parsedString = String.valueOf(result);
        } else {
            if (result >= 1) {
                parsedString = Roman.arabicToRoman(result);
            } else {
                throw new IllegalArgumentException("Roman Numeral cannot be smaller than I");
            }
        }
        return parsedString;
    }

}