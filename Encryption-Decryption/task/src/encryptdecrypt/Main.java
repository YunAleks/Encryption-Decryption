package encryptdecrypt;

import java.io.*;
import java.util.*;
abstract class Algorithms {
    public void doAlgorithms(String mode, String data, boolean dataOn, boolean fileOn, int key, String fileNameOut) throws IOException {
    }
}

class Shift extends Algorithms {

    @Override
    public void doAlgorithms(String mode, String data, boolean dataOn, boolean fileOn, int key, String fileNameOut) throws IOException {
        List<String> pass = new ArrayList<>();
        char[] textArray = data.toCharArray();
        switch (mode) {
            case "enc": {
                for (char s : textArray) {
                    if (s == ' ') {
                        pass.add(" ");
                    } else {
                        if (s >= 'a' && s <= 'z') {
                            s = (char) (s + key);
                            if (s > 'z') {
                                s = (char) (s % ('z' + 1) + 'a');
                                pass.add(String.valueOf(s));
                            } else {
                                pass.add(String.valueOf(s));
                            }
                        } else if (s >= 'A' && s <= 'Z') {
                            s = (char) (s + key);
                            if (s > 'Z') {
                                s = (char) (s % ('Z' + 1) + 'A');
                                pass.add(String.valueOf(s));
                            }else {
                                pass.add(String.valueOf(s));
                            }
                        } else {
                            pass.add(String.valueOf(s));
                        }
                    }
                }
                if (fileOn) {
                    File file = new File(fileNameOut);
                    System.out.println(fileNameOut);
                    FileWriter writer = new FileWriter(file); // overwrites the file
                    for (String i : pass) {
                        writer.write(i);
                    }
                    writer.close();
                } else {
                    for (String i : pass) {
                        System.out.print(i);
                    }
                }
            }
            break;
            case "dec": {
                for (char s : textArray) {
                    if (s == ' ') {
                        pass.add(" ");
                    } else {
                        if (s >= 'a' && s <= 'z') {
                            s = (char) (s - key);
                            if (s < 'a') {
                                s = (char) ('z' - ('a' - s - 1));
                                pass.add(String.valueOf(s));
                            } else {
                                pass.add(String.valueOf(s));
                            }
                        } else if (s >= 'A' && s <= 'Z') {
                            s = (char) (s - key);
                            if (s < 'A') {
                                s = (char) ('Z' - ('A' - s - 1));
                                pass.add(String.valueOf(s));
                            }else {
                                pass.add(String.valueOf(s));
                            }
                        } else {
                            pass.add(String.valueOf(s));
                        }
                    }
                }
                if (fileOn) {
                    File file = new File(fileNameOut);
                    System.out.println(fileNameOut);
                    FileWriter writer = new FileWriter(file); // overwrites the file
                    for (String i : pass) {
                        writer.write(i);
                    }
                    writer.close();
                } else {
                    for (String i : pass) {
                        System.out.print(i);
                    }
                }
            }
        }
    }
}

class Unicode extends Algorithms {

    @Override
    public void doAlgorithms (String mode, String data, boolean dataOn, boolean fileOn, int key, String fileNameOut) throws IOException {
        switch (mode) {
            case "enc": {
                char[] textArray = data.toCharArray();
               if (fileOn) {
                    File file = new File(fileNameOut);
                    FileWriter writer = new FileWriter(file); // overwrites the file
                    for (int i = 0; i < textArray.length; i++) {
                        textArray[i] = (char) (textArray[i] + key);
                    }
                    writer.write(textArray);
                    writer.close();
                } else {
                    for (int i = 0; i < textArray.length; i++) {
                        textArray[i] = (char) (textArray[i] + key);
                        System.out.print(textArray[i]);
                    }
                }
            }
            break;
            case "dec": {
                char[] textArray = data.toCharArray();
                    if (fileOn) {
                    File file = new File(fileNameOut);
                    System.out.println(fileNameOut);
                    FileWriter writer = new FileWriter(file); // overwrites the file
                    for (int i = 0; i < textArray.length; i++) {
                        textArray[i] = (char) (textArray[i] - key);
                    }
                    writer.write(textArray);
                    writer.close();
                } else {
                    for (int i = 0; i < textArray.length; i++) {
                        textArray[i] = (char) (textArray[i] - key);
                        System.out.print(textArray[i]);
                    }
                }
            }
            break;
        }
    }
}


public class Main {
    public static void main(String[] args) throws IOException {
        String mode = "enc";
        int key = 0;
        String data = " ";
        String fileNameIn;
        String fileNameOut = null;
        boolean dataOn = false;
        boolean fileOn = false;
        String textFromFile = null;
        Algorithms alg = null;
        String algorithm = "shift";

        List<String> line = Arrays.asList(args);
        for (int i = 0; i < args.length; i++) {
            if ("-mode".equals(line.get(i))) {
                mode = line.get(i + 1);
            }
            if ("-data".equals(line.get(i))) {
                data = line.get(i + 1);
                dataOn = true;
            }
            if ("-in".equals(line.get(i))) {
                fileNameIn = line.get(i + 1);
                File file = new File(fileNameIn);
                Scanner scanner = new Scanner(file);
                textFromFile = scanner.nextLine();
            }
            if ("-out".equals(line.get(i))) {
                fileNameOut = line.get(i + 1);

            }
            if ("-key".equals(line.get(i))) {
                key = Integer.parseInt(line.get(i + 1));
            }
            if ("-alg".equals(line.get(i))) {
                if ("shift".equals(line.get(i + 1))) {
                    algorithm = "shift";
                } else if ("unicode".equals(line.get(i + 1))) {
                    algorithm = "unicode";
                }
            }
        }
        if (!dataOn) {
            data = textFromFile;
            fileOn = true;
        }
        if ("unicode".equals(algorithm)) {
            alg = new Unicode();
            alg.doAlgorithms(mode, data, dataOn, fileOn, key, fileNameOut);
        } else  {
            alg = new Shift();
            alg.doAlgorithms(mode, data, dataOn, fileOn, key, fileNameOut);
        }
    }
}


