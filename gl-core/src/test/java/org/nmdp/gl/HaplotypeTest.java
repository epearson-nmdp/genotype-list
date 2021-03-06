/*

    gl-core  Core interfaces and classes for the gl project.
    Copyright (c) 2012-2015 National Marrow Donor Program (NMDP)

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.nmdp.gl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Unit test for Haplotype.
 */
public final class HaplotypeTest {
    private final Locus drb1 = new Locus("http://immunogenomics.org/locus/0", "HLA-DRB1");
    private final Locus drb5 = new Locus("http://immunogenomics.org/locus/1", "HLA-DRB5");
    private final Allele allele0 = new Allele("http://immunogenomics.org/allele/0", "DRB100001", "HLA-DRB1*01:01:01:01", drb1);
    private final Allele allele1 = new Allele("http://immunogenomics.org/allele/1", "DRB500002", "HLA-DRB5*02:01:01:01", drb5);
    private final AlleleList alleleList0 = new AlleleList("http://immunogenomics.org/allele-list/0", ImmutableList.of(allele0));
    private final AlleleList alleleList1 = new AlleleList("http://immunogenomics.org/allele-list/1", ImmutableList.of(allele1));
    private final List<AlleleList> empty = Collections.emptyList();
    private final List<AlleleList> single = ImmutableList.of(alleleList0);
    private final List<AlleleList> alleleLists = ImmutableList.of(alleleList0, alleleList1);

    @Test(expected=NullPointerException.class)
    public void testConstructorNullIdentifier() {
        new Haplotype(null, alleleLists);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullAlleleList() {
        new Haplotype("http://immunogenomics.org/haplotype/0", (AlleleList) null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullAlleleLists() {
        new Haplotype("http://immunogenomics.org/haplotype/0", (List<AlleleList>) null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorEmptyAlleleLists() {
        new Haplotype("http://immunogenomics.org/haplotype/0", empty);
    }

    @Test
    public void testConstructorAlleleList() {
        Haplotype haplotype = new Haplotype("http://immunogenomics.org/haplotype/0", alleleList0);
        assertNotNull(haplotype);
        assertEquals(alleleList0, haplotype.getAlleleLists().get(0));
    }

    @Test
    public void testConstructorSingletonAlleleList() {
        Haplotype haplotype = new Haplotype("http://immunogenomics.org/haplotype/0", single);
        assertNotNull(haplotype);
        assertEquals(single, haplotype.getAlleleLists());
    }

    @Test
    public void testId() {
        Haplotype haplotype = new Haplotype("http://immunogenomics.org/haplotype/0", alleleLists);
        assertEquals("http://immunogenomics.org/haplotype/0", haplotype.getId());
    }

    @Test
    public void testGlstring() {
        Haplotype haplotype = new Haplotype("http://immunogenomics.org/haplotype/0", alleleLists);
        assertEquals("HLA-DRB1*01:01:01:01~HLA-DRB5*02:01:01:01", haplotype.getGlstring());
    }

    @Test
    public void testAlleleLists() {
        Haplotype haplotype = new Haplotype("http://immunogenomics.org/haplotype/0", alleleLists);
        assertEquals(alleleLists, haplotype.getAlleleLists());
    }

    @Test
    public void testHashCode() {
        Haplotype a = new Haplotype("http://immunogenomics.org/haplotype/0", alleleLists);
        Haplotype sameA = new Haplotype("http://immunogenomics.org/haplotype/0", alleleLists);

        assertEquals(a.hashCode(), sameA.hashCode());
    }

    @Test
    public void testEquals() {
        Haplotype a = new Haplotype("http://immunogenomics.org/haplotype/0", alleleLists);
        Haplotype altA = new Haplotype("http://alt.immunogenomics.org/haplotype/0", alleleLists);
        Haplotype differentGlstring = new Haplotype("http://immunogenomics.org/haplotype/0", single);
        Haplotype sameA = new Haplotype("http://immunogenomics.org/haplotype/0", alleleLists);
        Haplotype b = new Haplotype("http://immunogenomics.org/haplotype/1", single);

        assertFalse(a.equals(null));
        assertFalse(a.equals(new Object()));
        assertTrue(a.equals(a));
        assertFalse(a.equals(b));
        assertFalse(a.equals(altA));
        assertFalse(a.equals(differentGlstring));
        assertTrue(a.equals(sameA));
    }
}
