import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Iris {
    private static final DateTimeFormatter SAVE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final String SAVE_FILE = "tasks.txt";

//    private static void saveTasks(ArrayList<Task> list) {
//        try (PrintWriter pw = new PrintWriter(new FileWriter(SAVE_FILE))) {
//            for (Task t : list) {
//                pw.println(t.toSaveString());
//            }
//        } catch (IOException e) {
//            System.out.println("Error saving tasks.");
//        }
//    }

//    private static void loadTasks(ArrayList<Task> list) {
//        File file = new File(SAVE_FILE);
//        if (!file.exists()) {
//            return;
//        }
//
//        try (Scanner sc = new Scanner(file)) {
//            while (sc.hasNextLine()) {
//                String line = sc.nextLine();
//                Task t = parseTask(line);
//                if (t != null) {
//                    list.add(t);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("It appears there was an error when loading saved tasks...");
//        }
//    }
//
//    private static Task parseTask(String line) {
//        try {
//            String[] parts = line.split("\\|");
//
//            String type = parts[0].trim();
//            boolean done = parts[1].trim().equals("1");
//
//            Task t = null;
//
//            switch (type) {
//                case "TODO" -> t = new ToDo(parts[2].trim());
//                case "DEADLINE" -> {
//                    LocalDateTime by = LocalDateTime.parse(parts[3].trim(), SAVE_FMT);
//                    t = new Deadline(parts[2].trim(), by);
//                }
//                case "EVENT" -> {
//                    LocalDateTime from = LocalDateTime.parse(parts[3].trim(), SAVE_FMT);
//                    LocalDateTime to = LocalDateTime.parse(parts[4].trim(), SAVE_FMT);
//                    t = new Event(parts[2].trim(), from, to);
//                }
//            }
//
//            if (done && t != null) {
//                t.mark();
//            }
//
//            return t;
//
//        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
//            System.out.println("⚠ Warning: Skipped corrupted save line:");
//            System.out.println("    " + line);
//            return null;
//        }
//    }

    private static LocalDateTime parseDateTime(String input) {
        DateTimeFormatter dateTimeFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Try full date + time first
            return LocalDateTime.parse(input, dateTimeFmt);
        } catch (DateTimeParseException e) {
            try {
                // Fallback: parse only date, set time to 00:00
                LocalDate date = LocalDate.parse(input, dateFmt);
                return date.atStartOfDay();
            } catch (DateTimeParseException ex) {
                // If both fail, throw exception to caller
                throw new DateTimeParseException("Invalid date/time format", input, 0);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage(SAVE_FILE);
        ArrayList<Task> list = storage.load();

        System.out.println("""
                ____________________________________________________________
                Hello! I'm Iris
                What can I do for you?
                ____________________________________________________________
                """
        );

        while (true) {
            try {
                System.out.print("What would you like me to do?:\n");

                String input = scanner.nextLine().trim();

                boolean handled = false;

                if (input.equalsIgnoreCase("Bye")) {
                    handled = true;
                    System.out.println("""
                            ____________________________________________________________
                            Bye. Hope to see you again soon!
                            ____________________________________________________________"""
                    );
                    break;
                }

                if (input.equalsIgnoreCase("list")) {
                    handled = true;
                    System.out.println("____________________________________________________________");
                    System.out.println("Here are the tasks in your list");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + ". " + list.get(i).toString());
                    }

                    if (list.size() > 4) {
                        System.out.println("\nAre you that busy or do you just like to procrastinate?");
                    } else {
                        System.out.println("\nLight work... Hopefully right?");
                    }
                    System.out.println("____________________________________________________________");
                    continue;
                }

                if (input.toLowerCase().startsWith("mark")) {
                    handled = true;
                    String[] parts = input.split("\\s+");
                    if (parts.length != 2) {
                        System.out.println("____________________________________________________________");
                        System.out.println("I advise you to stop speaking gibberish");
                        System.out.println("____________________________________________________________");
                        continue;
                    }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (index < 0 || index >= list.size()) {
                            System.out.println("____________________________________________________________");
                            System.out.println("Getting overzealous now are we? Unless " +
                                    "you can read the future,");
                            System.out.println("I don't think you can finish that task...");
                            System.out.println("____________________________________________________________");
                        } else {
                            list.get(index).mark();
                            storage.save(list);
                            System.out.println("____________________________________________________________");
                            System.out.println("Was just wondering when you were going to complete that...");
                            System.out.println("I've marked this task as done:");
                            System.out.println(list.get(index).toString());
                            System.out.println("        Now you have " + list.size() + " tasks in the list.");
                            System.out.println("\nMove on, I'm not here to give out rewards");
                            System.out.println("____________________________________________________________");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println("One of us just made an error and I'm sure it wasn't me");
                        System.out.println("____________________________________________________________");
                    }
                }

                if (input.toLowerCase().startsWith("unmark")) {
                    handled = true;
                    String[] parts = input.split("\\s+");
                    if (parts.length != 2) {
                        System.out.println("____________________________________________________________");
                        System.out.println("I advise you to stop speaking gibberish");
                        System.out.println("____________________________________________________________");
                        continue;
                    }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (index < 0 || index >= list.size()) {
                            System.out.println("____________________________________________________________");
                            System.out.println("You do realize that I can't organize " +
                                    "tasks that you have not told me about right?");
                            System.out.println("____________________________________________________________");
                        } else {
                            list.get(index).unmark();
                            storage.save(list);
                            System.out.println("____________________________________________________________");
                            System.out.println("Looks like someone is slow... I have marked it as undone for you:");
                            System.out.println(list.get(index).toString());
                            System.out.println("        Now you have " + list.size() + " tasks in the list.");
                            System.out.println("\nWhat are you waiting for? Go on then.");
                            System.out.println("____________________________________________________________");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println("One of us just made an error and I'm sure it wasn't me");
                        System.out.println("____________________________________________________________");
                    }
                }

                if (input.toLowerCase().startsWith("delete")) {
                    handled = true;
                    String[] parts = input.split("\\s+");
                    if (parts.length != 2) {
                        System.out.println("____________________________________________________________");
                        System.out.println("I advise you to stop speaking gibberish");
                        System.out.println("____________________________________________________________");
                        continue;
                    }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (index < 0 || index >= list.size()) {
                            System.out.println("____________________________________________________________");
                            System.out.println("It appears that you are lacking a frontal lobe and have");
                            System.out.println("asked me to remove something that has not yet existed...");
                            System.out.println("____________________________________________________________");
                        } else {
                            System.out.println("____________________________________________________________");
                            System.out.println("Here I was beginning to think you have forgotten about this...");
                            System.out.println("I have removed the following task:");
                            System.out.println(list.get(index).toString());
                            list.remove(index);
                            storage.save(list);
                            System.out.println("        Now you have " + list.size() + " tasks in the list.");
                            System.out.println("\nMore free time for you then... Enjoy.");
                            System.out.println("____________________________________________________________");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println("One of us just made an error and I'm sure it wasn't me");
                        System.out.println("____________________________________________________________");
                    }
                }

                if (input.toLowerCase().startsWith("todo ")) {
                    handled = true;

                    String description = input.substring(5).trim();

                    ToDo todo = new ToDo(description);
                    list.add(todo);
                    storage.save(list);
                    System.out.println("____________________________________________________________");
                    System.out.println("Added the following task:");
                    System.out.println(todo);
                    System.out.println("Better get it done early... or you could just be lazy");
                    System.out.println("        Now you have " + list.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }

                if (input.toLowerCase().startsWith("deadline ")) {
                    handled = true;

                    String rest = input.substring(9).trim();

                    int byIndex = rest.toLowerCase().lastIndexOf(" /by ");

                    if (byIndex == -1) {
                        System.out.println("____________________________________________________________");
                        System.out.println("I'm not omniscient you know? Give me more details.");
                        System.out.println("____________________________________________________________");
                    } else {
                        String description = rest.substring(0, byIndex).trim();
                        String dateString = rest.substring(byIndex + 5).trim();

                        try {
                            LocalDateTime date = parseDateTime(dateString);

                            Deadline deadline = new Deadline(description, date);
                            list.add(deadline);
                            storage.save(list);
                            System.out.println("____________________________________________________________");
                            System.out.println("Added the following task:");
                            System.out.println(deadline);
                            System.out.println("It is called a Deadline for a reason, better hurry up");
                            System.out.println("        Now you have " + list.size() + " tasks in the list.");
                            System.out.println("____________________________________________________________");

                        } catch (DateTimeParseException e) {
                            System.out.println("____________________________________________________________");
                            System.out.println("Losing a few screws? That date doesn't exist in this universe.");
                            System.out.println("Use format YYYY-MM-DD or YYYY-MM-DD HH:MM");
                            System.out.println("____________________________________________________________");
                        }
                    }
                }

                if (input.toLowerCase().startsWith("event ")) {
                    handled = true;

                    String rest = input.substring(6).trim();

                    int fromIndex = rest.toLowerCase().lastIndexOf(" /from ");
                    int toIndex = rest.toLowerCase().lastIndexOf(" /to ");

                    if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                        System.out.println("____________________________________________________________");
                        System.out.println("Did you develop dementia? You missed out some crucial information!");
                        System.out.println("____________________________________________________________");
                    } else {
                        String description = rest.substring(0, fromIndex).trim();
                        String start = rest.substring(fromIndex + 7, toIndex).trim();
                        String end = rest.substring(toIndex + 5).trim();

                        try {
                            LocalDateTime startDate = parseDateTime(start);
                            LocalDateTime endDate = parseDateTime(end);

                            if (endDate.isBefore(startDate)) {
                                System.out.println("End date cannot be before start date.");
                                continue;
                            }

                            Event event = new Event(description, startDate, endDate);
                            list.add(event);
                            storage.save(list);
                            System.out.println("____________________________________________________________");
                            System.out.println("Added the following task:");
                            System.out.println(event);
                            System.out.println("Sounds like fun... or a chore...");
                            System.out.println("        Now you have " + list.size() + " tasks in the list.");
                            System.out.println("____________________________________________________________");
                        } catch (DateTimeParseException e) {
                            System.out.println("____________________________________________________________");
                            System.out.println("Please abide by the standard format or else...");
                            System.out.println("Invalid date format. Use YYYY-MM-DD or YYYY-MM-DD HH:MM");
                            System.out.println("____________________________________________________________");
                        }
                    }
                }

                if (!handled) {
                    throw new IrisException("""
                            NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNXOl'...''..   ......................          .,o0NNNNNNNNNNNNNNNNNNN
                            NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNKd,.  ....      .......................    .     .;xXNNNNNNNNNNNNNNNNN
                            NNNNNNNNNNNNNNNNNNNWNNNNNNNNNNNNNNNNNNNNNNNNNNNNWWWWWWWWNNNNNNNNNNNNNNNNWWNWWWNKl.    .............',''......    .......           .oKNWWWWWWWWWNNNNNW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNO:.    ......''....''......            ..             .:ONWWWWWWWWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXx,    .........''.. .... .                               'kNWWWWWWWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXko:.   ....'....   ...   .                       ..          'kNWWWWWWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXl.     .......       ..                           ...          'kWWWMWWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNx'    ........                                     ..            ;OWMMMMWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNo.   ......                                                      .:KMMMWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMXl    .......       ..',..............                             'kWMMMWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMWk'     ...........',:lddlllc;,,,;;;;;,'.....   ...     ...         .dWMMMWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMXo.   .......,;;:codxkO0K000Okxxxxxxxxxdolc:,''....... ..           .dWMMMWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMXo.     ....cdxkkO00KKKKKKKKK000OOO00OOOkxdolc:;,,''..              ;0WMMMWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMNx.        'dOOOOO00KKKKKKKK0000OOOOOOOkkxdolccc::;,..             .lNMMMMWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMNd'       ,dOOOOOO00KKKKK0000OOOOOOOOkkxdollcclcc;,..             .dNMMMWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMWx..     'okOOOOO00000000000OOOOkkkkkxddollclllc;'..            .cKWMMMWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMNo,.    .lkOOOO0000000000000OOOOOOOkkxddollllc:,'..            .dWMMMMWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMN0o..  'okOOOO00KKKKKK000000000OOOkkxxdooolc:;,'...           .dWWMMMMWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMXo. .cxxdolcllloodxkO00000000Okdoc:;,'',,'''''''..          .oXWMWWWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMMXl..od;',;;:;;:codxO00KKKKK0Oxoc;,........  ...''..       .':o0WMWWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMMO..lc;lxO000OOOO0000000000OOkxolcccllllc:,....'''.     .,;;::oXMMWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMMMK:.cox00OkkkkkkxkOO0000OOkxdol:::cllccccclc;'..','.   ':::;::l0WMWWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMMMMMNx:lkO0kxddoc:,;coxkO0OOOxoc:;;:odl,...'',:::;;,,,.  .;:::;:coKWMWWWWWWW
                            WWWWNNNNWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW0xkXWWWWWWWWWWWWWWWWWWWWWWWOloKMMMMMWOcdO0Okoldl;..;oodkO000Oxl:;;coOKkc;;::,,;::c:;;,'  .,:cc:;cOWMMWWWWWWW
                            WNOl;,',:dKWWWWWWWWWNkodKWWWWWWWWWWWWWWWWNc .kWMWWWWWWWWWWWWWWWWWWWWNc .dWMMMMW0odO0OkxxkkdloxkkO0KKKK0xc;:ldxOOOkdoocc:cccc:;,,'. .,:;;:ckNMMMWWWWWWW
                            Nd. ,od:..cKWX0kxkKXx.  ;ONWWWXOdlod0NMWMNc .xWKdlcld0WNOdlclxKWXxc:c' .dWMMMMMNkxOO0OOOOkkkkkOO0KKKKK0xc:cldkkOOkkxddooooolc;,,'. .;;',;oXMMMWWMWWWWW
                            O' .OWN0xoxKk,..'..::.  .dNWWKl.';. .xWMMNc .dd. ':' .dx;';;. ;kc .;c. .dWMMMMMWOxOOO0000000000KKK00KK0xlccloxkOOOOkkxxddddlc;,'.. .'',,:kWMMWMWWWWWWW
                            O. '0Xo,..;o,  ;c. .c; .dWWWWXd;;c,  cNMMNc .c, .OWk. ,dc;::. .c. ;KNc .dWMMMMMWKkkO0000KKKKKKKKKK00KK0xocccldkOOOkkkkxxxdol:,,''..',;:;oXMMMMWMMWWWWW
                            K; .oK0o. .c'  ,cc;cx:  oNWWWk. 'o:  :XMMNc .c; .o0o. ,, .co. .l, .dO;  oWMMMMMMN0OOO00KKKKKKKKKK0000K0ko::::ldkOOOkkkkxxdoc;,'''..,;:::dNMMMMMWWWWWWW
                            WO;..';'. ,kd'..;;,oKd. .lKWW0; .,,. ;KWWNl .dOc.....cOd'.',...dk;..''..dWMMMMMMWX0OOO000KK00KKK00OO0K0ko:;,;:lxkkOOOOkkxdoc;,''..';:;;;c0WMMMWWWWWWWW
                            WWXOdolodkKNNKxolox0NNOddkXWWWXkxkOOk0NWWWKkkKWNKOkOKNWNKOO0000XWN00KXKKNMMMMMMMMN0OkO000KK0000OOOOO0K0Odc,,,,;ldkkOOOOkxdoc;,,'..';:;,''dNMMMMWMWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMWWWMMMMWXOkOO00K00OOkxxkOO0KKOxl:,,,;:loxkkkkkxdl:;,,''.',,,''.cKMMMMMWWWWWW
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMWKOOOO000OkxxxkOOO0000kdc;,,;;:clloodddoc;;;;,'.',,'''.,kWMWMWWWWWWW
                            WWWWWNWWWWWWWN0OKNWWWWWWWWKOKNWWWWWX00NWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMWWMMMMMMN0OOOOOOkxdxOOOOOOOOkxo:,,'',,;;;;::cloc;,;;,..',,'''..lXMMWWWWWWWW
                            WKdc;;;lkXWNx,..cKMMNOdxXK: ,0MWWWNd',kWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWMMMMMMMMMMMMMMMXOkkOOkdoxkOO0OOkkxdol:;,,,,,,,,,,;;cdo;,,,'..',,''...'oXWMWWWWWWW
                            x' .lo;. :Ok'  ;kNMNk' .cd' .dkxkXNk::OWKkddxONWWWWWKxooddok0xdOWNkdxxoo0WXOdlxXMMMMWXOkkkdloxkOOO0OOOkddddoc::::;,'',;:oxl;,,'...,,,'''...'lKWMWWWWWW
                            , .xWWXc  ,;.  .oXMXl.  .c'  ',. ,ko .ox..,;''oXWWWk' .:;. ,l. :XK, .:. ;0d.. cNMMMMMWKkkkxooxkOkkOOOOOkkkkxolc;,''';:cldd:,'''..',,,,'''''.'c0WWWWWWW
                            . .OMWWo  :x' .xNWWWK; .d0; .xXc  cl  ld..,::ckNWMNc .dW0' 'c. ;XK; .xl .:'  ,0MMMMMMMWXOxxxxxkOOxddxkOOOkkxdlc:;,,;::lodl,,''..',,,,,''''''.';xXWWMWW
                            ; .oNWK: .d0, .kWWWWNc .o0; .kWo  cl  lOl:c;. ;KMMNl  lKk. 'l. 'OO, .kK;    .xWMMMMMMMMMN0kxxxkOOOkkkOOOOOOkkxdoc;;;:cllc,','..',,,,,,,'''''''.':lx0XW
                            O:..,;'..oXK, .xWWWWNd. .:' .xWd. cl..ld,.,;..cKWWWKc..''  'xl..',. .kWO.  .cNMMMMMMMMMMMWKOxxkOOOOOOOOOkkkkxdoc::::ccc:,'''..',,,,;;,,,'',''''....':d
                            WXOdllox0NWXxldKWWWWWNOdoxxdxKWKxd0KkkKNKkxxk0NWWWWWkc:c,. :KN0kk00O0XKd. ,d0MMMMMMMMMMMMMWN0kxkkOOOOkkxxdoolcccclllcc:;''''',;;;;;;;;,,,,,,,,''''...,
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNx;,,,;o0WWWWWWWWWNx,'c0WWMMWWWWWWWWMMMMMWXOkkkOOOOOkkxxddddddool::,'''',,;;;;;:;;;;,,,;;;;;;;,,,,;
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNXXXNWWWWWWWWWWWWNXNWWWWWWWWWWWWWWWWMMWWWN0kkkOO0000OOOkkxddoc:;,''',,;;;::::::::;;;::ccccc::::c:
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNXXK0OxddxkOOOOOOOOOkxdlc:;,,',,,;;:::::::::::::clooolccccc:,
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNX0Oxdoollccccodxkkkkkkkxdol:;,,,,,,;;;::::::ccccc:clodddolllc;,',
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNKOxolllllllccccl:;cldxdddoolc:::;;;;;;::::::cccccccccloddoollc;,,;;,
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNKOxolccllllllllccc:;;;;lxxddooooolllllccccccccccclllllllooolllc;,,,,,,;
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXkdooolllllllllllcc:,;:,:loxkkxxddddddddddooooolllllooooolllll:;,,;,',:ll
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNX0dlllllllllllllllll:;;:,,clloxkkkxxxxxxxxxxxxdddooodddddolccc;;,;,,,;cloll
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNXXNWWWWWWWWWWWWWWWWWWWWWWNOxdllloolllllllloooll;,::';lolcokkkkkkkkkkkkkkkxxxxxxxddolc:;,,;;,',:loollll
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXOkkk0NWWWWWWWWWWWWWWWWWWWNOdolllooollolllloooooc,;c,':lolcdkkkkkkkkkkOOOkkkkkkxxolc:;,,,,,,';cloollllll
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNKOkxdx0WWWWWWWWWWWWWWWWWWNOdllllllllloollllloooo:';c,'coollxkkOOOkkkOOOOOOOOkxdolc;,,,,,,,;:loolllclllll
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNKOOkxokXWWWWWWWWWWWWWWWWXkdolllllllooollllcloooo:';c''coollxkkOOOOkkOOOOOOkxdoc:;,,,,,,,cloooolllcclooll
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWN0OOkxod0WWWWWWWWWWWWWWWKxooooooolloooollolllodol:',c,'coolldkkO0OOkkkOOkxxol:,,,,'',;cloddoooooolloooloo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWN0OOkxoo0WWWWWWWWWWWWWWKocloloooollooolloooooooolc,,c,'coolclkkOOOkkkkxxdoc;,,,'',;cloooooooooooooooooooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNNNXXXXK0Okkxod0WWWWWWWWWWWWWWOlclllooooooooloooooodooooc,,c;':ooo::dkOOkkkxdol:;;,,',;cooooooolloooooodoooooooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNNXK000000OdddkkkdoxXWWWWWWWWWWWWWKocllloodooooolloooooodooool,,c;':ooo:;lkkkxxdoc:;,,,,;codddddooollodoooddooooooool
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXKK000000KKXKOdc:lddookNWWWWWWWWWWWWXd:clllooooooolllodoooooooool;'::';odoc,;dxxdoc;,,,,;cloodddddooololloodddooddddoooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWNKOOOO00000000Okxoc:clllxXWWWWWWWWWWWWOc;clllloooooollllooooooooool:':c',ldoc,,ldlc;;,,,:ldddooddddoooooolloddoooddddooooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWXOkkkkkkOOOO00000Odc::c:o0WWWWWWWWWMMNx;;ccccclooooloollloooooooooo:';c,,loolccc::;;,,:lddddoddddooooddoloddddoodooooooooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWX0OOOOOO000KKKXXKK0xc;;;;ckNMWWWWWWWMMNd,;c::llloooooddoloodoooodddoc,'c;':ool:;;;;;;coddddddddddooodddooodddoodddolooooooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWN0O0000000KKKKKKK00Oxc;;;,;dXWWWWWWWWWWNd,;::cloooolooddooodoolodddooo;':;.,:;,,;;;;codddoooddddoooddddoooddooodxddooddooooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWN0O00000000000000000ko:;;;;lKWWWWWWWWWWNd,;;:loooooloddoooddoloodddoooc';:'',;;,;:ldddddoloddddoodddddoodddoooddddooddddoooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWX0OO00000000KKKKKK0Od:;;;;cOWWWWWWWWWMXo,,;:loooooooddooddoolooddooddl''cc:;;;coddddddooodddoooddddddddddooddddoooddddooloo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWX000000000KK00000Oxl:;:::cOWWWWWWWWMWKc,,;:looolooodooddooolooodooddl,.;;;:oddooddddooddddooddolooddddoooddddoloddddoooodd
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWNKOOO000000000000Odc:::::l0WWWWWWWWMWk:',,:loooloollooddooloooolooddo;..;lddooodddoooddoooddddoloddddooodddddooddddooodddx
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWX0O00KKKKKKK0KK0Odc::::;;dXWMWWMMMMXo,'',:loooooollodooooodddolodddoc;:dddoooooooodddoodddddoodddoooooddooooodddoooodddxx
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWNKO0000KK000OOOOxoc:::;'.,kWMMWWWWWO;..,;clooddddooodooooddddoodddooooddddoodolloddoooddddooodddoolodddolloodddoooodddddd
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXOkO0000Okdoooooc:::;'..'kWWWWWWWNx'..,:cloodddoooodoooodddooodddoooddddoddoloddooodddddoodddooooddddolloddddooodddddddo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXOxkO000Odl::::::::;'...:OWWWWWWWXl. .,clooddddoooddoooodddoodddooododddddoooddooodddddoodddoooodddddlloddoooooddddddooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWN0xkO00Okdlcc:::;;;,...,lKWWWWMMW0:. .;looddddooodddooodddooodooloooodddddooddoooddoooodddooooodddddoloddoooloodddddollo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWKkkO00Okxolcccc:;,...',oKWWWWMWNk,  .:oooddddoodddooododoooddolloooddddoodddoodddoloodoooooooddooooloddoollloddddooolod
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXkxO00Okxdolllc:,..','',l0NWWWWKl.  .:oooddoooddddooddooloddoollooodddooddooddddoooddooooloddooooolooooolcloodxdooolodd
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWN0dxkOOkxdollc:;'',;''...;kNWWNx,.  .coooooloodddooodddolodoolllooddooodddodddddooodddoooodddooooooooollccloodddooooooo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNOdddxxdollc::;;;;,''.....'dXWKc.   'looddoooodddooddddoloooolloddddoodddoddddoooodddooooddddoloooooollcclooooooooddllo
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNx;,loolllcc:::;;,''.........oKx'    'lodddooddddoodddddoloooolloddddoddooddddoooddddooodddddoloooooollcclooooolloddolcl
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWNx,..;lcc::;;,,,'''''.........'l,     .cddddooddddooddddoolooolloodxddddooddddooooddoooodddddoolooooollc:coollllllodolc:l
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWO;.  .:::::;;;;,,,''''''''..          .:odddooddddooddddoloooolloddddddoooddooodddddoooddooooolooooollc::loocccllloollc:l
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWk,.  .';:::::;;;;;;,,,,,,'..           ;oddooodddoooddooolooollooddddddoodddooododdoooddooooolooooolllccloooc:cllloolc::c
                            WWWWWWWWWWWWWWWWWWWWWWWWWWWWWO;.   ..;:;;;;;;;;;;;;;,,.             ,oddooodddoloddoollooollooddddddodddoooooooooodddoooollodooollcclolooc;:cloollc::c
                            """);
                }

            } catch (IrisException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("Has your age finally caught up with you? I need a valid command.");
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}

