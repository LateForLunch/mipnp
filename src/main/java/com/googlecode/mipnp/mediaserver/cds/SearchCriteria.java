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

    private boolean asterix;
    private LinkedList<Token> rpn;

    public SearchCriteria(String searchCriteria) {
        searchCriteria = searchCriteria.trim();
        this.asterix = searchCriteria.equals("*");
        if (!asterix) {
            parseSearchCriteria(searchCriteria);
        }
    }

    public boolean meetsCriteria(CdsObject obj) {
        if (asterix) {
            return true;
        }
//        if (obj.isItem()) {
//            return true;
//        }
        LinkedList<Token> input  = new LinkedList<Token>(rpn);
        LinkedList<Token> stack = new LinkedList<Token>();

        while (input.size() > 0) {
            Token token = input.poll();
            if (token.isOperator()) {
                Token arg2 = stack.pop();
                Token arg1 = stack.pop();
                arg1 = replaceValue(arg1, obj);
                arg2 = replaceValue(arg2, obj);
                Token result = new Token(
                        String.valueOf(token.evaluateOperator(arg1, arg2)));
                stack.push(result);
            } else {
                // Token is a value
                stack.push(token);
            }
        }

        return stack.pop().asBoolean();
    }

    private void parseSearchCriteria(String searchCriteria) {
        LinkedList<Token> queue = new LinkedList<Token>();
        LinkedList<Token> stack = new LinkedList<Token>();
//        String split[] = searchCriteriaStr.split("\\s+"); // TODO: create own tokenizer
        SearchCriteriaTokenizer tokenizer = new SearchCriteriaTokenizer(searchCriteria);

        System.out.println("Parsing: " + searchCriteria);
//        for (int i = 0; i < split.length; i++) {
        while (tokenizer.hasMoreElements()) {
            Token token = tokenizer.nextElement();
            System.out.print("Token: " + token.getToken() + " is ");
            if (token.isValue()) {
                System.out.println("a value");
                queue.offer(token);
            } else if (token.isOperator()) {
                System.out.println("an operator");
                Token peek = stack.peek();
                while (peek != null &&
                        peek.isOperator() &&
                        token.getPrecedence() <= peek.getPrecedence()) {
                    queue.offer(stack.pop());
                    peek = stack.peek();
                }
                stack.push(token);
            } else if (token.isLeftParenthesis()) {
                System.out.println("a left parenthesis");
                stack.push(token);
            } else if (token.isRightParenthesis()) {
                System.out.println("a right parenthesis");
                Token pop = stack.pop();
                while (!pop.isLeftParenthesis()) {
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

    private Token replaceValue(Token arg, CdsObject obj) {
        if (arg.getToken().equals("upnp:class")) {
            return new Token("\"" + obj.getUpnpClass() + "\"");
        }
        // TODO: add more properties
        return arg;
    }
}
