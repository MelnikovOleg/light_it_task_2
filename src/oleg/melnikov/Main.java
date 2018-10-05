/*
Основная идея программы заключается в том, чтобы определять победителя в игре по набранным очкам.
Каждая карта даёт свои очки. Чем старше карта, тем больше баллов (шестёрка даёт 1 балл, туз - 9).
Козырные карты дают дополнительные 9 очков.
Именно такое минимальное количество очков нужно добавить к значению карты, чтобы она могла "побить" любую некозырную карту или козырь ниже по страшинству.
 */

package oleg.melnikov;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int players;
        int trump; //хранит "номер" козыря

        // формируем колоду из 36 карт
        String[][] deck = new String[10][4];
        createDeck(deck);

        System.out.println("Приложение для обработки колоды карт для игры 'Дурак'.\n" +
                "Программа случайным образом раздаст карты игрокам и определит, у кого набор карт сильнее путём подсчёта очков, которая даёт каждая карта.\nКозырная карта даёт дополнительные очки!");

        while (true) try {
            System.out.println("Введите количество игроков от 2 до 6 включительно");
            Scanner scanner = new Scanner(System.in);
            players = scanner.nextInt();
            if (players > 6 || players < 2) {
                System.out.println("Ошибка: количество игроков должно быть от 2 до 6 включительно!");
                continue;
            }
            break;
        } catch (Exception e) {
            System.out.println("Ошибка: некорректные данные. Попробуйте ещё раз");
            continue;
        }

        // Генерация козыря и вывод его на экран
        trump = (int) (Math.random() * 4);
        System.out.println("Козырь: " + deck[0][trump]);
        System.out.println();

        int[] results = new int[players]; // создаем массив для хранения результатов игры

        for (int i = 0; i < players; i++){
            System.out.print("Игрок " + (i + 1) + ": ");

            // Генерируем набор из 6 карт

            for (int j = 0; j < 6; j++){
                while (true){
                    int color = (int) (Math.random() * 4); // выбираем масть
                    int value = (int) (1 + Math.random() * 9); // выбираем значение карты

                    if (Objects.equals(deck[value][color], "0")) continue; // проверка карты (см. ниже)
                    else {
                        //добавляем очки карте
                        if (color == trump) results[i] += 9; // бонус за козырь
                        results[i] += value;
                        System.out.print(deck[value][color] + deck[0][color] + " ");
                        deck[value][color] = "0"; // ставим 0 в массиве колоды, чтоб избежать повторного использования карты
                        break;
                    }
                }
            }
            System.out.println(" (" + results[i] + " очк.)");
            System.out.println();
        }

        showTheWinner(results); // выводим результаты
    }

    public static String[][] createDeck(String[][] deck){
        /*
        Метод, который генерирует двумерный массив - т.н колоду карт.
        Первый ряд заполняем названиями мастей. Остальные ряды будут хранить значения самих карт от 6 до туза.
         */
        deck[0][0] = "Ч";
        deck[0][1] = "Б";
        deck[0][2] = "К";
        deck[0][3] = "П";

        for (int n = 0; n < 4; n++){
            deck [1][n] = "6";
            deck [2][n] = "7";
            deck [3][n] = "8";
            deck [4][n] = "9";
            deck [5][n] = "10";
            deck [6][n] = "В";
            deck [7][n] = "Д";
            deck [8][n] = "К";
            deck [9][n] = "Т";
        }

        return deck;
    }

    public static void showTheWinner(int[] res){
        int winner = 0;
        // Определяем победителя
        for (int i = 0; i < res.length; i++){
            if (res[i] > res[winner]) winner = i;
        }

        // Проверка на ничью
        for (int j = 0; j < res.length; j++){
            if (res[winner] == res[j] && j != winner){
                System.out.println("У игроков " + j + " и " + winner + " одинаковое количество очков. Ничья!" );
                System.exit(0);
            }
        }
        System.out.println("У игрока №" + (winner + 1) + " сильнейший набор карт!");
    }
}