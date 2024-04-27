package bullscows

fun getBullsAndCows(num: String, guess: String): Pair<Int, Int> {
    var bulls = 0
    var cows = 0
    guess.forEachIndexed { ind, n ->
        if (n == num[ind]) bulls++
        else if (n in num) cows++
    }
    return bulls to cows
}

fun generateNumber(length: Int, charactersLength: Int): String {
    val charactersList = ('0'..'9') + ('a' until 'a' + charactersLength)
    return charactersList.shuffled().take(length).joinToString("")
}

fun main() {
    println("Please, enter the secret code's length:")
    var input = readln()
    val length = input.toIntOrNull()
    if (length == null) {
        println("Error: $input isn't a valid number."); return
    }
    if (length == 0 || length > 36) {
        println("Error: can't generate a secret number with a length of $length.")
        return
    }

    println("Input the number of possible symbols in the code:")
    input = readln()
    val possibleCharacters = input.toIntOrNull()
    if (possibleCharacters == null) {
        println("Error: $input isn't a valid number."); return
    }
    if (possibleCharacters > 36) {
        println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).")
        return
    }
    if (possibleCharacters < length) {
        println("Error: it's not possible to generate a code with a length of $length with $possibleCharacters unique symbols.")
        return
    }

    val lettersPart = if (possibleCharacters > 11) ", a-${'a' + possibleCharacters - 11}" else ", a"
    println("The secret is prepared: ${"*".repeat(length)} (0-9$lettersPart).")

    val secret = generateNumber(length, possibleCharacters - 10)

    println("Okay, let's start a game!")
    var turn = 1
    while (true) {
        println("Turn ${turn++}:")
        val guess = readln()
        val result = getBullsAndCows(secret, guess)
        val gradeStrings = mutableListOf<String>()
        with(result) {
            if (first > 0) gradeStrings.add("$first bull${if (first == 1) "" else "s"}")
            if (second > 0) gradeStrings.add("$second cow${if (second == 1) "" else "s"}")
        }
        if (gradeStrings.isEmpty()) gradeStrings.add("None")
        println("Grade: ${gradeStrings.joinToString(" and ")}")
        if (result.first == length) {
            println("Congratulations! You guessed the secret code.")
            break
        }
    }
}