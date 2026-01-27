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
    private static final String SAVE_FILE = "tasks.txt";

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
        Storage storage = new Storage(SAVE_FILE);
        TaskList list = new TaskList(storage.load());
        Ui ui = new Ui();

        ui.showWelcome();

        while (true) {
            try {
                String input = ui.readCommand();

                boolean handled = false;

                if (input.equalsIgnoreCase("Bye")) {
                    handled = true;
                    ui.showBye();
                    break;
                }

                if (input.equalsIgnoreCase("list")) {
                    handled = true;
                    ui.showLine();
                    ui.showMessage("Here are the tasks in your list");
                    for (int i = 0; i < list.size(); i++) {
                        ui.showMessage((i + 1) + ". " + list.get(i).toString());
                    }

                    if (list.size() > 4) {
                        ui.showMessage("\nAre you that busy or do you just like to procrastinate?");
                    } else {
                        ui.showMessage("\nLight work... Hopefully right?");
                    }
                    ui.showLine();
                    continue;
                }

                if (input.toLowerCase().startsWith("mark")) {
                    handled = true;
                    String[] parts = input.split("\\s+");
                    if (parts.length != 2) {
                        ui.showLine();
                        ui.showMessage("I advise you to stop speaking gibberish");
                        ui.showLine();
                        continue;
                    }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (index < 0 || index >= list.size()) {
                            ui.showLine();
                            ui.showMessage("Getting overzealous now are we? Unless " +
                                    "you can read the future,");
                            ui.showMessage("I don't think you can finish that task...");
                            ui.showLine();
                        } else {
                            list.mark(index);
                            storage.save(list.getAll());
                            ui.showLine();
                            ui.showMessage("Was just wondering when you were going to complete that...");
                            ui.showMessage("I've marked this task as done:");
                            ui.showMessage(list.get(index).toString());
                            ui.showMessage("        Now you have " + list.size() + " tasks in the list.");
                            ui.showMessage("\nMove on, I'm not here to give out rewards");
                            ui.showLine();
                        }
                    } catch (NumberFormatException e) {
                        ui.showLine();
                        ui.showMessage("One of us just made an error and I'm sure it wasn't me");
                        ui.showLine();
                    }
                }

                if (input.toLowerCase().startsWith("unmark")) {
                    handled = true;
                    String[] parts = input.split("\\s+");
                    if (parts.length != 2) {
                        ui.showLine();
                        ui.showMessage("I advise you to stop speaking gibberish");
                        ui.showLine();
                        continue;
                    }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (index < 0 || index >= list.size()) {
                            ui.showLine();
                            ui.showMessage("You do realize that I can't organize " +
                                    "tasks that you have not told me about right?");
                            ui.showLine();
                        } else {
                            list.unmark(index);
                            storage.save(list.getAll());
                            ui.showLine();
                            ui.showMessage("Looks like someone is slow... I have marked it as undone for you:");
                            ui.showMessage(list.get(index).toString());
                            ui.showMessage("        Now you have " + list.size() + " tasks in the list.");
                            ui.showMessage("\nWhat are you waiting for? Go on then.");
                            ui.showLine();
                        }
                    } catch (NumberFormatException e) {
                        ui.showLine();
                        ui.showMessage("One of us just made an error and I'm sure it wasn't me");
                        ui.showLine();
                    }
                }

                if (input.toLowerCase().startsWith("delete")) {
                    handled = true;
                    String[] parts = input.split("\\s+");
                    if (parts.length != 2) {
                        ui.showLine();
                        ui.showMessage("I advise you to stop speaking gibberish");
                        ui.showLine();
                        continue;
                    }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (index < 0 || index >= list.size()) {
                            ui.showLine();
                            ui.showMessage("It appears that you are lacking a frontal lobe and have");
                            ui.showMessage("asked me to remove something that has not yet existed...");
                            ui.showLine();
                        } else {
                            ui.showLine();
                            ui.showMessage("Here I was beginning to think you have forgotten about this...");
                            ui.showMessage("I have removed the following task:");
                            ui.showMessage(list.get(index).toString());
                            list.remove(index);
                            storage.save(list.getAll());
                            ui.showMessage("        Now you have " + list.size() + " tasks in the list.");
                            ui.showMessage("\nMore free time for you then... Enjoy.");
                            ui.showLine();
                        }
                    } catch (NumberFormatException e) {
                        ui.showLine();
                        ui.showMessage("One of us just made an error and I'm sure it wasn't me");
                        ui.showLine();
                    }
                }

                if (input.toLowerCase().startsWith("todo ")) {
                    handled = true;

                    String description = input.substring(5).trim();

                    ToDo todo = new ToDo(description);
                    list.add(todo);
                    storage.save(list.getAll());
                    ui.showLine();
                    ui.showMessage("Added the following task:");
                    ui.showMessage(todo.toString());
                    ui.showMessage("Better get it done early... or you could just be lazy");
                    ui.showMessage("        Now you have " + list.size() + " tasks in the list.");
                    ui.showLine();
                }

                if (input.toLowerCase().startsWith("deadline ")) {
                    handled = true;

                    String rest = input.substring(9).trim();

                    int byIndex = rest.toLowerCase().lastIndexOf(" /by ");

                    if (byIndex == -1) {
                        ui.showLine();
                        ui.showMessage("I'm not omniscient you know? Give me more details.");
                        ui.showLine();
                    } else {
                        String description = rest.substring(0, byIndex).trim();
                        String dateString = rest.substring(byIndex + 5).trim();

                        try {
                            LocalDateTime date = parseDateTime(dateString);

                            Deadline deadline = new Deadline(description, date);
                            list.add(deadline);
                            storage.save(list.getAll());
                            ui.showLine();
                            ui.showMessage("Added the following task:");
                            ui.showMessage(deadline.toString());
                            ui.showMessage("It is called a Deadline for a reason, better hurry up");
                            ui.showMessage("        Now you have " + list.size() + " tasks in the list.");
                            ui.showLine();

                        } catch (DateTimeParseException e) {
                            ui.showLine();
                            ui.showMessage("Losing a few screws? That date doesn't exist in this universe.");
                            ui.showMessage("Use format YYYY-MM-DD or YYYY-MM-DD HH:MM");
                            ui.showLine();
                        }
                    }
                }

                if (input.toLowerCase().startsWith("event ")) {
                    handled = true;

                    String rest = input.substring(6).trim();

                    int fromIndex = rest.toLowerCase().lastIndexOf(" /from ");
                    int toIndex = rest.toLowerCase().lastIndexOf(" /to ");

                    if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                        ui.showLine();
                        ui.showMessage("Did you develop dementia? You missed out some crucial information!");
                        ui.showLine();
                    } else {
                        String description = rest.substring(0, fromIndex).trim();
                        String start = rest.substring(fromIndex + 7, toIndex).trim();
                        String end = rest.substring(toIndex + 5).trim();

                        try {
                            LocalDateTime startDate = parseDateTime(start);
                            LocalDateTime endDate = parseDateTime(end);

                            if (endDate.isBefore(startDate)) {
                                ui.showMessage("End date cannot be before start date.");
                                continue;
                            }

                            Event event = new Event(description, startDate, endDate);
                            list.add(event);
                            storage.save(list.getAll());
                            ui.showLine();
                            ui.showMessage("Added the following task:");
                            ui.showMessage(event.toString());
                            ui.showMessage("Sounds like fun... or a chore...");
                            ui.showMessage("        Now you have " + list.size() + " tasks in the list.");
                            ui.showLine();
                        } catch (DateTimeParseException e) {
                            ui.showLine();
                            ui.showMessage("Please abide by the standard format or else...");
                            ui.showMessage("Invalid date format. Use YYYY-MM-DD or YYYY-MM-DD HH:MM");
                            ui.showLine();
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
                ui.showLine();
                ui.showError(e.getMessage());
                ui.showMessage("Has your age finally caught up with you? I need a valid command.");
                ui.showLine();
            }
        }
    }
}

