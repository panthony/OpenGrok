/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * See LICENSE.txt included in this distribution for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at LICENSE.txt.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

/*
 * Copyright (c) 2007, 2010, Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2012, 2013 Constantine A. Murenin &lt;C++@Cns.SU&gt;
 */

package org.opensolaris.opengrok.analysis.uue;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import org.opensolaris.opengrok.analysis.Definitions;
import org.opensolaris.opengrok.analysis.FileAnalyzer;
import org.opensolaris.opengrok.analysis.FileAnalyzer.Genre;
import org.opensolaris.opengrok.analysis.FileAnalyzerFactory;
import org.opensolaris.opengrok.configuration.Project;
import org.opensolaris.opengrok.history.Annotation;

/**
 * @author Constantine A. Murenin &lt;http://cnst.su/&gt;
 */

public class UuencodeAnalyzerFactory extends FileAnalyzerFactory {
    private static final String[] SUFFIXES = {
       /**
         * XXX:
         * FreeBSD and DragonFly .fnt files are uuencoded;
         * Minix3 .fnt files are binary. -- 2013-04 cnst
         */
        "UU", "UUE", /*"FNT",*/ "BASE64"
    };

    private static final String[] MAGICS = {
        "begin 4",
        "begin 6",
        "begin 7",
        "begin-b" /* XXX: Should be "begin-base64 ", but there seems to be a bug somewhere... */
    };

    public UuencodeAnalyzerFactory() {
        super(null, null, SUFFIXES, MAGICS, null, "text/plain", Genre.PLAIN);
    }

    @Override
    protected FileAnalyzer newAnalyzer() {
        return new UuencodeAnalyzer(this);
    }

    @Override
    public void writeXref(Reader in, Writer out, Definitions defs, Annotation annotation, Project project)
        throws IOException
    {
        UuencodeAnalyzer.writeXref(in, out, defs, annotation, project);
    }

}
