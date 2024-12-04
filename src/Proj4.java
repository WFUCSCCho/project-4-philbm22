import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestHashTable <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        ArrayList<Mountain> mainList = new ArrayList<>();
        for(int i = 0; i < numLines; i++) {
            String line = inputFileNameScanner.nextLine().strip();

            if (!line.isEmpty()) {
                String[] totalCommand = line.split(",");
                if (totalCommand.length >= 5) {
                    Mountain mountain = new Mountain(
                            totalCommand[0].trim(),
                            Integer.parseInt(totalCommand[1].trim()),
                            Integer.parseInt(totalCommand[2].trim()),
                            totalCommand[3].trim(),
                            totalCommand[4].trim()
                    );

                    mainList.add(mountain);
                }
            }
        }
       inputFileNameScanner.close();

       ArrayList<Mountain> sortedList = new ArrayList<>(mainList);
       ArrayList<Mountain> shuffledList = new ArrayList<>(mainList);
       ArrayList<Mountain> reversedList = new ArrayList<>(mainList);

       Collections.shuffle(shuffledList);
       Collections.sort(sortedList);
       Collections.sort(reversedList, Collections.reverseOrder());

       SeparateChainingHashTable<Mountain> shuffledHashTable = new SeparateChainingHashTable<>();
       SeparateChainingHashTable<Mountain> sortedHashTable = new SeparateChainingHashTable<>();
       SeparateChainingHashTable<Mountain> reversedHashTable = new SeparateChainingHashTable<>();

        FileWriter fileWriter = new FileWriter("analysis.txt",true);

       double sortedStart = System.nanoTime();
       for(Mountain mountain : sortedList) {
           sortedHashTable.insert(mountain);
       }
       for(Mountain mountain : sortedList){
           sortedHashTable.contains(mountain);
       }
       for(Mountain mountain : sortedList) {
           sortedHashTable.remove(mountain);
       }
       double sortedEnd = System.nanoTime();
       double sortedRuntime = (sortedEnd - sortedStart) / 1e9;
       fileWriter.write(args[1] + "," + "sorted" + "," + numLines + "," +  sortedRuntime + "\n");

       double shuffledStart = System.nanoTime();

       for(Mountain mountain : shuffledList) {
           shuffledHashTable.insert(mountain);
       }
       for(Mountain mountain : shuffledList){
           shuffledHashTable.contains(mountain);
       }
       for(Mountain mountain : shuffledList){
           shuffledHashTable.remove(mountain);
       }
       double shuffledEnd = System.nanoTime();
       double shuffledRuntime = (shuffledEnd - shuffledStart) / 1e9;
       fileWriter.write(args[1] + "," + "shuffled" + "," + numLines + "," +  shuffledRuntime + "\n");

       double reversedStart = System.nanoTime();
       for(Mountain mountain : reversedList) {
           reversedHashTable.insert(mountain);
       }
       for(Mountain mountain : reversedList){
           reversedHashTable.contains(mountain);
       }
       for(Mountain mountain : reversedList){
           reversedHashTable.remove(mountain);
       }
       double reversedEnd = System.nanoTime();
       double reversedRuntime = (reversedEnd - reversedStart) / 1e9;
        fileWriter.write(args[1] + "," + "reversed" + "," + numLines + "," +  reversedRuntime + "\n");

        fileWriter.close();

       System.out.println("Number of Lines: " + numLines + ". Runtime of Sorted List: " + sortedRuntime + " seconds");
       System.out.println("Number of Lines: " + numLines + ". Runtime of Shuffled List: " + shuffledRuntime + " seconds");
       System.out.println("Number of Lines: " + numLines + ". Runtime of Shuffled List: " + reversedRuntime + " seconds");
       }

    }

