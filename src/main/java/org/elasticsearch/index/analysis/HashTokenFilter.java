/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

/**
 *
 */
public class HashTokenFilter extends TokenFilter {
    private CharTermAttribute charTermAtt;
    private OffsetAttribute offAtt;
    private int prevOff;

    public HashTokenFilter(TokenStream input) {
        super(input);
        charTermAtt =  addAttribute(CharTermAttribute.class);
        offAtt =  addAttribute(OffsetAttribute.class);
    }

    @Override
    public boolean incrementToken() throws IOException {
        if(input.incrementToken()) {
            //
            // Save the state for this token, since we want the position in
            // the next token.
            State s = captureState();
            if(input.incrementToken()) {

                //
                // Parse the position from the next token.  Wasteful, but what you
                // gonna do, unless we want to implement our own parseint.
                String ps = charTermAtt.toString();
                int posn;
                try {
                    posn = Integer.parseInt(ps);
                } catch(NumberFormatException ex) {
                    throw new IOException("Bad offset " + ps);
                }
                restoreState(s);

                //
                // A finger print extends from the previous position to this one.
                offAtt.setOffset(prevOff, posn);
                prevOff = posn;
                return true;
            }
        }
        return false;
    }
}
