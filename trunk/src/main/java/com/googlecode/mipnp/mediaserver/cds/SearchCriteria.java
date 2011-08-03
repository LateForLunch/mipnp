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
 * SearchCriteria.java
 * Created on Jul 28, 2011, 7:16:49 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

import java.util.LinkedList;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
public class SearchCriteria {

    private String searchCriteriaStr;
    private boolean asterix;
    private LinkedList<String> rpn;

    public SearchCriteria(String searchCriteriaStr) {
        this.searchCriteriaStr = searchCriteriaStr.trim();
        this.asterix = this.searchCriteriaStr.equals("*");
        if (!asterix) {
            parseSearchCriteria();
        }
    }

    public boolean meetsCriteria(CdsObject obj) {
        if (asterix) {
            return true;
        }
        if (obj.isItem()) {
            return true;
        }
        return false;
    }

    private void parseSearchCriteria() {
        LinkedList<String> queue = new LinkedList<String>();
        LinkedList<String> stack = new LinkedList<String>();
        String split[] = searchCriteriaStr.split("\\s+");

        for (int i = 0; i < split.length; i++) {
            if (isQuotedVal(split[i])) {
                queue.offer(split[i]);
            } else if (isOp(split[i])) {
                String peek = stack.peek();
                while (getPrecedence(split[i]) <= getPrecedence(peek)) {
                    queue.offer(stack.pop());
                    peek = stack.peek();
                }
                stack.push(split[i]);
            } else if (split[i].equals("(")) {
                stack.push(split[i]);
            } else if (split[i].equals(")")) {
                String pop = stack.pop();
                while (!pop.equals("(")) {
                    queue.offer(pop);
                    pop = stack.pop();
                }
            }
        }

        while (!stack.isEmpty()) {
            queue.offer(stack.pop());
        }

        this.rpn = queue;
    }

    private boolean isOp(String str) {
        return (isLogOp(str) ||
                isBinOp(str) ||
                isExistsOp(str));
    }

    private boolean isLogOp(String str) {
        return (str.equals("and") || str.equals("or"));
    }

    private boolean isBinOp(String str) {
        return (isRelOp(str) || isStringOp(str));
    }

    private boolean isRelOp(String str) {
        return (str.equals("=") || str.equals("!=") ||
                str.equals("<") || str.equals("<=") ||
                str.equals(">") || str.equals(">="));
    }

    private boolean isStringOp(String str) {
        return (str.equals("contains") ||
                str.equals("doesNotContain") ||
                str.equals("derivedfrom"));
    }

    private boolean isExistsOp(String str) {
        return (str.equals("exists"));
    }

    private boolean isQuotedVal(String str) {
        return (str.startsWith("\"") && str.endsWith("\""));
    }

    private int getPrecedence(String op) {
        if (op.equals("(") || op.equals(")")) {
            return 4;
        } else if (isBinOp(op) || isExistsOp(op)) {
            return 3;
        } else if (op.equals("and")) {
            return 2;
        } else if (op.equals("or")) {
            return 1;
        } else {
            return -1;
        }
    }
}
