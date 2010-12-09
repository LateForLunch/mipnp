/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010  Jochem Van denbussche
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * CliMenu.java
 * Created on Nov 27, 2010, 1:41:06 PM
 */
package cli.menu;

import java.util.Scanner;

/**
 * This class can be used to create a CLI (command line interface) menu.
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class CliMenu {

    private String question;
    private String[] options;
    private int defaultValue;
    private Scanner scanner;

    /**
     * Creates a new menu with -1 as the default value.
     * @param question the question to ask the user
     * @param options the options the user get
     */
    public CliMenu(String question, String[] options) {
        this(question, options, -1);
    }

    /**
     * Creates a new menu.<br />
     * Use a negative defaultValue if you don't want a default value.
     * @param question the question to ask the user
     * @param options the options the user get
     * @param defaultValue the default value
     */
    public CliMenu(String question, String[] options, int defaultValue) {
        this.question = question;
        this.options = options;
        setDefaultValue(defaultValue);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Shows the menu and wait for the user to enter a valid answer.
     * @return the integer the user entered
     */
    public int show() {
        while (true) {
            for (int i = 0; i < options.length; i++) {
                System.out.format("[%d] %s\n", i+1, options[i]);
            }
            System.out.println();
            System.out.print(question);
            if (getDefaultValue() != -1) {
                System.out.format(" [%d]", getDefaultValue());
            }
            System.out.print(": ");

            String in = scanner.nextLine().trim();
            if (getDefaultValue() != -1 && in.isEmpty()) {
                return getDefaultValue();
            }
            int i = -1;
            try {
                i = Integer.parseInt(in);
            } catch (NumberFormatException ex) {
                System.out.println();
                continue;
            }
            if (isValid(i)) {
                return i;
            }
        }
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        if (isValid(defaultValue)) {
            this.defaultValue = defaultValue;
        } else {
            this.defaultValue = -1;
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private boolean isValid(int i) {
        if (i > 0 && i <= options.length) {
            return true;
        }
        return false;
    }

    /**
     * TEST
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new CliMenu("test", new String[] {"t1", "t2"}, 2).show());
    }
}
