import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

class LuhnValidator {

    boolean isValid(String candidate) {
        candidate = candidate.replace(" ", "");
        if (candidate.length() < 2) return false;
        if (invalidCharacters(candidate)) return false;

        final var reversedString = new StringBuilder(candidate).reverse().toString();
        return calculateLuhnChecksum(reversedString) % 10 == 0;
    }

    private static int calculateLuhnChecksum(String reversedString) {
        IntUnaryOperator getDoubledOrOriginalDigit = index -> {
            int numberValue = Character.digit(reversedString.charAt(index), 10);

            return index % 2 == 0 ? numberValue : (numberValue * 2 > 9 ? numberValue * 2 - 9 : numberValue * 2);
        };

        return IntStream.range(0, reversedString.length())
                .map(getDoubledOrOriginalDigit)
                .sum();
    }

    private boolean invalidCharacters(String candidate) {
        return candidate.chars().anyMatch(c -> !Character.isDigit((char) c));
    }

}
