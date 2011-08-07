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
 * Token.java
 * Created on Aug 6, 2011, 5:15:46 PM
 */
package com.googlecode.mipnp.mediaserver.cds;

/**
 *
 * @author Jochem Van denbussche <jvandenbussche@gmail.com>
 */
class Token {

    private static final String OP_AND = "and";
    private static final String OP_OR = "or";
    private static final String OP_EQUALS = "=";
    private static final String OP_NOT_EQUAL = "!=";
    private static final String OP_LESS_THAN = "<";
    private static final String OP_LESS_THAN_OR_EQUAL = "<=";
    private static final String OP_GREATER_THAN = ">";
    private static final String OP_GREATER_THAN_OR_EQUAL = ">=";
    private static final String OP_CONTAINS = "contains";
    private static final String OP_DOES_NOT_CONTAIN = "doesNotContain";
    private static final String OP_DERIVED_FROM = "derivedfrom";
    private static final String OP_EXISTS = "exists";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";
    private static final String QUOTE = "\"";

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public boolean isOperator() {
        return (isLogicalOperator() ||
                isBinaryOperator() ||
                isExistsOperator());
    }

    public boolean isLogicalOperator() {
        return (token.equals(OP_AND) || token.equals(OP_OR));
    }

    public boolean isBinaryOperator() {
        return (isRelationalOperator() || isStringOperator());
    }

    public boolean isRelationalOperator() {
        return (token.equals(OP_EQUALS) || token.equals(OP_NOT_EQUAL) ||
                token.equals(OP_LESS_THAN) || token.equals(OP_LESS_THAN_OR_EQUAL) ||
                token.equals(OP_GREATER_THAN) || token.equals(OP_GREATER_THAN_OR_EQUAL));
    }

    public boolean isStringOperator() {
        return (token.equals(OP_CONTAINS) ||
                token.equals(OP_DOES_NOT_CONTAIN) ||
                token.equals(OP_DERIVED_FROM));
    }

    public boolean isExistsOperator() {
        return token.equals(OP_EXISTS);
    }

    public boolean isValue() {
        return (!isOperator() && !isParenthesis());
    }

    public boolean isQuotedValue() {
        return (token.startsWith(QUOTE) && token.endsWith(QUOTE));
    }

    public boolean isParenthesis() {
        return (isLeftParenthesis() || isRightParenthesis());
    }

    public boolean isLeftParenthesis() {
        return token.equals(LEFT_PARENTHESIS);
    }

    public boolean isRightParenthesis() {
        return token.equals(RIGHT_PARENTHESIS);
    }

    public int getPrecedence() {
        if (isParenthesis()) {
            return 4;
        } else if (isBinaryOperator() || isExistsOperator()) {
            return 3;
        } else if (token.equals(OP_AND)) {
            return 2;
        } else if (token.equals(OP_OR)) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getToken() {
        return token;
    }

    public String asString() {
        String str = token;
        if (isQuotedValue()) {
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }

    public int asInteger() throws NumberFormatException {
        return Integer.parseInt(asString());
    }

    public boolean asBoolean() {
        return Boolean.parseBoolean(asString());
    }

    public boolean evaluateOperator(Token arg1, Token arg2) {
        if (!isOperator()) {
            return false;
        }
        String s_arg1 = arg1.asString();
        String s_arg2 = arg2.asString();
        if (token.equals(OP_AND)) {
            return (arg1.asBoolean() && arg2.asBoolean());
        } else if (token.equals(OP_OR)) {
            return (arg1.asBoolean() || arg2.asBoolean());
        } else if (isRelationalOperator()) {
            try {
                // Try to compare as numbers
                int i_arg1 = arg1.asInteger();
                int i_arg2 = arg2.asInteger();
                if (token.equals(OP_EQUALS)) {
                    return (i_arg1 == i_arg2);
                } else if (token.equals(OP_NOT_EQUAL)) {
                    return (i_arg1 != i_arg2);
                } else if (token.equals(OP_LESS_THAN)) {
                    return (i_arg1 < i_arg2);
                } else if (token.equals(OP_LESS_THAN_OR_EQUAL)) {
                    return (i_arg1 <= i_arg2);
                } else if (token.equals(OP_GREATER_THAN)) {
                    return (i_arg1 > i_arg2);
                } else if (token.equals(OP_GREATER_THAN_OR_EQUAL)) {
                    return (i_arg1 >= i_arg2);
                }
            } catch (NumberFormatException ex) {
                // Compare as Strings
                if (token.equals(OP_EQUALS)) {
                    return s_arg1.equalsIgnoreCase(s_arg2);
                } else if (token.equals(OP_NOT_EQUAL)) {
                    return !s_arg1.equalsIgnoreCase(s_arg2);
                } else if (token.equals(OP_LESS_THAN)) {
                    return (s_arg1.compareToIgnoreCase(s_arg2) > 0);
                } else if (token.equals(OP_LESS_THAN_OR_EQUAL)) {
                    return (s_arg1.compareToIgnoreCase(s_arg2) >= 0);
                } else if (token.equals(OP_GREATER_THAN)) {
                    return (s_arg1.compareToIgnoreCase(s_arg2) < 0);
                } else if (token.equals(OP_GREATER_THAN_OR_EQUAL)) {
                    return (s_arg1.compareToIgnoreCase(s_arg2) <= 0);
                }
            }
        } else if (token.equals(OP_CONTAINS)) {
            return s_arg1.toLowerCase().contains(s_arg2.toLowerCase());
        } else if (token.equals(OP_DOES_NOT_CONTAIN)) {
            return !s_arg1.toLowerCase().contains(s_arg2.toLowerCase());
        } else if (token.equals(OP_DERIVED_FROM)) {
            return s_arg1.toLowerCase().startsWith(s_arg2.toLowerCase());
        } else if (token.equals(OP_EXISTS)) {
            return !(arg1.asBoolean() ^ arg2.asBoolean());
        }
        return false;
    }
}
