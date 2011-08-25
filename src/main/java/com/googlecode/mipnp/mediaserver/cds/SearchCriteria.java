/*
 * MiPnP, a minimal Plug and Play Server.
 * Copyright (C) 2010, 2011  Jochem Van denbussche
 *
 * This file is part of MiPnP.
 *
 * MiPnP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MiPnP is distributed in the hope that it will be useful,
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
public class SearchCriteria implements CdsConstants {

    private boolean asterix;
    private LinkedList<SearchCriteriaToken> rpn;

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

        LinkedList<SearchCriteriaToken> input  = new LinkedList<SearchCriteriaToken>(rpn);
        LinkedList<SearchCriteriaToken> stack = new LinkedList<SearchCriteriaToken>();

        while (input.size() > 0) {
            SearchCriteriaToken token = input.poll();
            if (token.isOperator()) {
                SearchCriteriaToken arg2 = stack.pop();
                SearchCriteriaToken arg1 = stack.pop();
                arg1 = replaceValue(arg1, obj);
                arg2 = replaceValue(arg2, obj);
                SearchCriteriaToken result = new SearchCriteriaToken(
                        String.valueOf(token.evaluateOperator(arg1, arg2)));
                stack.push(result);
            } else {
                stack.push(token);
            }
        }

        return stack.pop().asBoolean();
    }

    private void parseSearchCriteria(String searchCriteria) {
        LinkedList<SearchCriteriaToken> queue = new LinkedList<SearchCriteriaToken>();
        LinkedList<SearchCriteriaToken> stack = new LinkedList<SearchCriteriaToken>();
        SearchCriteriaTokenizer tokenizer = new SearchCriteriaTokenizer(searchCriteria);

        while (tokenizer.hasMoreElements()) {
            SearchCriteriaToken token = tokenizer.nextElement();
            if (token.isValue()) {
                queue.offer(token);
            } else if (token.isOperator()) {
                SearchCriteriaToken peek = stack.peek();
                while (peek != null &&
                        peek.isOperator() &&
                        token.getPrecedence() <= peek.getPrecedence()) {
                    queue.offer(stack.pop());
                    peek = stack.peek();
                }
                stack.push(token);
            } else if (token.isLeftParenthesis()) {
                stack.push(token);
            } else if (token.isRightParenthesis()) {
                SearchCriteriaToken pop = stack.pop();
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

    private SearchCriteriaToken replaceValue(SearchCriteriaToken arg, CdsObject obj) {
        // TODO: add more properties
        if (arg.getToken().equals(PROPERTY_CLASS)) {
            return new SearchCriteriaToken("\"" + obj.getUpnpClass() + "\"");
        } else if (obj.containsProperty(arg.getToken())) {
            return new SearchCriteriaToken("\"" + obj.getProperty(arg.getToken()) + "\"");
        }
        return arg;
    }
}
